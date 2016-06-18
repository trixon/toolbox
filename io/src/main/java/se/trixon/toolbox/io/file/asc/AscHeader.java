/* 
 * Copyright 2015 Patrik Karlsson.
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

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class AscHeader {

    public static final String KEY_CELL_SIZE = "cellsize";
    public static final String KEY_NCOLS = "ncols";
    public static final String KEY_NODATA = "NODATA_value";
    public static final String KEY_NROWS = "nrows";
    public static final String KEY_XLLCORNER = "xllcorner";
    public static final String KEY_YLLCORNER = "yllcorner";
    private double mCellSize = Double.MIN_VALUE;
    private int mNcols = Integer.MIN_VALUE;
    private double mNodata;
    private int mNrows = Integer.MIN_VALUE;
    private Path mPath;
    private final Path2D.Double mPath2D = new Path2D.Double();
    private BufferedReader mReader = null;
    private int mXPrecision;
    private double mXllcorner = Double.MIN_VALUE;
    private int mYPrecision;
    private double mYllcorner = Double.MIN_VALUE;

    public AscHeader() {
    }

    public AscHeader(BufferedReader reader, Path path) throws IOException, NumberFormatException {
        mReader = reader;
        mPath = path;

        mNcols = Integer.parseInt(getValue(KEY_NCOLS, mReader.readLine()));
        mNrows = Integer.parseInt(getValue(KEY_NROWS, mReader.readLine()));
        String xll = mReader.readLine();
        mXllcorner = Double.parseDouble(getValue(KEY_XLLCORNER, xll));
        String yll = mReader.readLine();
        mYllcorner = Double.parseDouble(getValue(KEY_YLLCORNER, yll));
        mCellSize = Double.parseDouble(getValue(KEY_CELL_SIZE, mReader.readLine()));
        mNodata = Double.parseDouble(getValue(KEY_NODATA, mReader.readLine()));

        mXPrecision = getNumOfDecimals(xll);
        mYPrecision = getNumOfDecimals(yll);

        mPath2D.moveTo(mXllcorner, mYllcorner);
        mPath2D.lineTo(mXllcorner + mCellSize * mNcols, mYllcorner);
        mPath2D.lineTo(mXllcorner + mCellSize * mNcols, mYllcorner + mCellSize * mNrows);
        mPath2D.lineTo(mXllcorner, mYllcorner + mCellSize * mNrows);
    }

    public Rectangle2D getBounds2D() {
        return mPath2D.getBounds2D();
    }

    public double getCellSize() {
        return mCellSize;
    }

    public int getNcols() {
        return mNcols;
    }

    public double getNodata() {
        return mNodata;
    }

    public int getNrows() {
        return mNrows;
    }

    public Path getPath() {
        return mPath;
    }

    public Path2D.Double getPath2D() {
        return mPath2D;
    }

    public int getXPrecision() {
        return mXPrecision;
    }

    public int getXYPrecision() {
        return Math.min(mXPrecision, mYPrecision);
    }

    public double getXllcorner() {
        return mXllcorner;
    }

    public int getYPrecision() {
        return mYPrecision;
    }

    public double getYllcorner() {
        return mYllcorner;
    }

    public void setCellSize(double cellSize) {
        mCellSize = cellSize;
    }

    public void setNcols(int ncols) {
        mNcols = ncols;
    }

    public void setNodata(double nodata) {
        mNodata = nodata;
    }

    public void setNrows(int nrows) {
        mNrows = nrows;
    }

    public void setPath(Path path) {
        mPath = path;
    }

    public void setXllcorner(double xllcorner) {
        mXllcorner = xllcorner;
    }

    public void setYllcorner(double yllcorner) {
        mYllcorner = yllcorner;
    }

    @Override
    public String toString() {
        return "AscHeader{" + "mCellSize=" + mCellSize + ", mNcols=" + mNcols + ", mNodata=" + mNodata + ", mNrows=" + mNrows + ", mPath=" + mPath + ", mXllcorner=" + mXllcorner + ", mYllcorner=" + mYllcorner + '}';
    }

    private int getNumOfDecimals(String string) {
        string = string.split(" ")[1];
        int index = string.indexOf(".");
        int numOfDecimals = 0;

        if (index > -1) {
            numOfDecimals = string.length() - index - 1;
        }

        return numOfDecimals;
    }

    private String getValue(String key, String line) {
        String result = null;
        key = key.toLowerCase();
        try {
            line = line.toLowerCase();
            if (line.contains(key)) {
                result = line.replace(key, "").trim();
            }

        } catch (NullPointerException e) {
        }

        return result;
    }
}
