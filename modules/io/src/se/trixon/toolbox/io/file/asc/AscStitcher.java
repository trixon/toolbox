/*
 * Copyright 2014 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.toolbox.io.file.asc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class AscStitcher {

    private Asc mAsc;
    private Asc[] mAscs;
    private int mCols;
    private double[][] mData;
    private final Set<File> mFiles = new HashSet<>();
    private double mMaxX = Double.MIN_VALUE;
    private double mMaxY = Double.MIN_VALUE;
    private double mMinX = Double.MAX_VALUE;
    private double mMinY = Double.MAX_VALUE;
    private double mCellSize;
    private int mRows;

    public boolean addFile(File file) {
        return mFiles.add(file);
    }

    public void addFiles(File[] files) {
        mFiles.addAll(Arrays.asList(files));
    }

    public Asc getAsc() throws IOException, Exception {
        if (!isValidForStitch()) {
            throw new Exception("Asc headers not equal.");
        }

        calcMinMaxXY();

        mAsc = new Asc();
        AscHeader header = mAsc.getHeader();
        double noData = mAscs[0].getHeader().getNodata();
        mCellSize = mAscs[0].getHeader().getCellSize();
        header.setCellSize(mCellSize);
        header.setNodata(noData);
        header.setXllcorner(mMinX);
        header.setYllcorner(mMinY);
        header.setNcols(mCols);
        header.setNrows(mRows);

        mData = new double[mRows][mCols];
        Arrays.fill(mData[0], noData);

        for (int row = 1; row < mRows; row++) {
            mData[row] = Arrays.copyOf(mData[0], mCols);
        }

        mergeData();
        mAsc.setData(mData);

        return mAsc;
    }

    private void calcMinMaxXY() {
        for (Asc asc : mAscs) {
            AscHeader header = asc.getHeader();
            double cellWidth = header.getCellSize() * header.getNcols();
            double cellHeight = header.getCellSize() * header.getNrows();
            mMaxX = Math.max(mMaxX, header.getXllcorner() + cellWidth);
            mMaxY = Math.max(mMaxY, header.getYllcorner() + cellHeight);

            mMinX = Math.min(mMinX, header.getXllcorner());
            mMinY = Math.min(mMinY, header.getYllcorner());

            double cellSize = mAscs[0].getHeader().getCellSize();
            mCols = (int) ((mMaxX - mMinX) / cellSize);
            mRows = (int) ((mMaxY - mMinY) / cellSize);
        }
    }

    private boolean isValidForStitch() throws IOException {
        mAscs = new Asc[mFiles.size()];

        int index = 0;
        for (File file : mFiles) {
            System.err.println("File " + file.getAbsolutePath());
            mAscs[index] = new Asc();
            mAscs[index].readHeader(file);
            index++;
        }

        if (mAscs.length == 0) {
            return true;
        }

        AscHeader checkHeader = mAscs[0].getHeader();

        for (int i = 1; i < mAscs.length; i++) {
            AscHeader ascHeader = mAscs[i].getHeader();
            if (ascHeader.getCellSize() != checkHeader.getCellSize()
                    || ascHeader.getNcols() != checkHeader.getNcols()
                    || ascHeader.getNrows() != checkHeader.getNrows()
                    || ascHeader.getNodata() != checkHeader.getNodata()) {
                return false;
            }
        }

        return true;
    }

    private void mergeData() throws IOException {
        for (File file : mFiles) {
            Asc asc = new Asc();
            asc.read(file);
            AscHeader smallHeader = asc.getHeader();
            double[][] smallData = asc.getData();

            System.err.println("mergeData");
            System.err.println(file.getAbsoluteFile());
            System.err.println("smallHeader.getYllcorner() " + smallHeader.getYllcorner());
            System.err.println("mMinY " + mMinY);
            double diff = smallHeader.getYllcorner() - mMinY;
            System.err.println("smallHeader.getYllcorner()-mMinY " + diff);

            int rowOffset = (int) ((smallHeader.getYllcorner() - mMinY) / mCellSize);
            int colOffset = (int) ((smallHeader.getXllcorner() - mMinX) / mCellSize);
//            int rowOffset = Math.abs((int) ((mMinY - smallHeader.getYllcorner()) / mCellSize));
            rowOffset = Math.abs(rowOffset - 1250);
            for (int row = 0; row < smallHeader.getNrows(); row++) {
                System.err.println(String.format("rowOffset=%d, row=%d ", rowOffset, row));
//                int rowOffset = (int) (mMaxY-(smallHeader.getYllcorner() ) / smallHeader.getCellSize());
                for (int col = 0; col < smallHeader.getNcols(); col++) {
                    mData[rowOffset + row][colOffset + col] = smallData[row][col];
                }
            }
        }
    }
}
