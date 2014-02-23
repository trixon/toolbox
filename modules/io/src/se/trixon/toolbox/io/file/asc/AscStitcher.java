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
        double noData = mAscs[0].getHeader().getNodata();
        mAsc.getHeader().setCellSize(mAscs[0].getHeader().getCellSize());
        mAsc.getHeader().setNodata(noData);
        mAsc.getHeader().setXllcorner(mMinX);
        mAsc.getHeader().setYllcorner(mMinY);
        mAsc.getHeader().setNcols(mCols);
        mAsc.getHeader().setNrows(mRows);

        mData = new double[mRows][mCols];
        Arrays.fill(mData[0], noData);

        for (int row = 1; row < mAsc.getHeader().getNrows(); row++) {
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
            double[][] fileData = asc.getData();
            for (int row = 0; row < asc.getHeader().getNrows(); row++) {
                for (int col = 0; col < asc.getHeader().getNcols(); col++) {
                    int colOffset = (int) ((asc.getHeader().getXllcorner() - mMinX) / asc.getHeader().getCellSize());
                    int rowOffset = (int) ((asc.getHeader().getYllcorner() - mMinY) / asc.getHeader().getCellSize());
                    mData[rowOffset + row][colOffset + col] = fileData[row][col];
                }
            }
        }
    }
}
