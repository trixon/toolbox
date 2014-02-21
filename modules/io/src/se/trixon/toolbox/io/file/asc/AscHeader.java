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
    private String mNodata;
    private int mNrows = Integer.MIN_VALUE;
    private Path mPath;
    private final BufferedReader mReader;
    private double mXllcorner = Double.MIN_VALUE;
    private double mYllcorner = Double.MIN_VALUE;

    public AscHeader(BufferedReader reader, Path path) throws IOException, NumberFormatException {
        mReader = reader;
        mPath = path;

        mNcols = Integer.parseInt(getValue(KEY_NCOLS, mReader.readLine()));
        mNrows = Integer.parseInt(getValue(KEY_NROWS, mReader.readLine()));
        mXllcorner = Double.parseDouble(getValue(KEY_XLLCORNER, mReader.readLine()));
        mYllcorner = Double.parseDouble(getValue(KEY_YLLCORNER, mReader.readLine()));
        mCellSize = Double.parseDouble(getValue(KEY_CELL_SIZE, mReader.readLine()));
        mNodata = getValue(KEY_NODATA, mReader.readLine());
    }

    public double getCellSize() {
        return mCellSize;
    }

    public int getNcols() {
        return mNcols;
    }

    public String getNodata() {
        return mNodata;
    }

    public int getNrows() {
        return mNrows;
    }

    public Path getPath() {
        return mPath;
    }

    public double getXllcorner() {
        return mXllcorner;
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

    public void setNodata(String nodata) {
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
