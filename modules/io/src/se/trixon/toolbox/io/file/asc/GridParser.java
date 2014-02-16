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
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GridParser {

    public static final String KEY_CELL_SIZE = "cellsize";
    public static final String KEY_NCOLS = "ncols";
    public static final String KEY_NODATA = "NODATA_value";
    public static final String KEY_NROWS = "nrows";
    public static final String KEY_XLLCORNER = "xllcorner";
    public static final String KEY_YLLCORNER = "yllcorner";
    private double mCellSize = Double.MIN_VALUE;
    private Charset mCharset = Charset.forName("US-ASCII");
    private int mNcols;
    private String mNodata;
    private int mNrows;
    private File mOutFile;
    private Path mPath;
    private BufferedReader mReader;
    private int mStep = 1;
    private BufferedWriter mWriter;
    private double mXllcorner = Double.MIN_VALUE;
    private double mYllcorner = Double.MIN_VALUE;

    public GridParser(Path path) throws IOException {
        setPath(path);
    }

    public GridParser(File file) throws IOException {
        setPath(file);
    }

    public void convert() throws IOException {
        File inFile = mPath.toFile();
        mOutFile = new File(inFile.getParent(), inFile.getName() + ".pxy");
        mWriter = Files.newBufferedWriter(mOutFile.toPath(), mCharset);
        String outLine;

//        outLine = "XYZ-COORD-FILE  ,V1.00,2011-11-04,                                        ,";
//        writeLine(outLine);
//        outLine = "                                        ,                                 ,";
//        writeLine(outLine);
        System.out.println("start");
        int pointId = 0;
        double x;
        double y;
        for (int i = mNrows; i > 0; i--) {
            y = mYllcorner + (i - 1) * mCellSize;

            String line = mReader.readLine();
            String[] z = line.split(" ");

            for (int j = 0; j < mNcols; j++) {
                pointId++;
                x = mXllcorner + j * mCellSize;
                String string = String.format("%d;%f;%f;%s", pointId, y, x, z[j]);
                writeLine(string);
            }
        }

        mWriter.close();
        System.out.println("done");

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

    public void init() throws IOException, NumberFormatException {

        mReader = Files.newBufferedReader(mPath, mCharset);
        mNcols = Integer.parseInt(getValue(KEY_NCOLS, mReader.readLine()));
        mNrows = Integer.parseInt(getValue(KEY_NROWS, mReader.readLine()));
        mXllcorner = Double.parseDouble(getValue(KEY_XLLCORNER, mReader.readLine()));
        mYllcorner = Double.parseDouble(getValue(KEY_YLLCORNER, mReader.readLine()));
        mCellSize = Double.parseDouble(getValue(KEY_CELL_SIZE, mReader.readLine()));
        mNodata = getValue(KEY_NODATA, mReader.readLine());

    }
//            System.err.format("IOException: %s%n", ex);

    public boolean isValid() {
        return (mNcols > 0 && mNrows > 0 && mCellSize > Double.MIN_VALUE && mXllcorner > Double.MIN_VALUE && mYllcorner > Double.MIN_VALUE);
    }

    public void setPath(Path path) throws IOException {
        File file = path.toFile();
        if (file.isFile() && file.exists()) {
            mPath = path;
            init();
        } else {
            throw new IOException();
        }
    }

    public void setPath(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            mPath = file.toPath();
            init();
        } else {
            throw new IOException();
        }
    }

    public void setStep(int step) {
        this.mStep = step;
    }

    private String getValue(String key, String line) {
        key = key.toLowerCase();
        line = line.toLowerCase();
        String result = "";

        if (line.contains(key)) {
            result = line.replace(key, "").trim();
        }

        return result;
    }

    private void writeLine(String line) throws IOException {
        mWriter.write(line, 0, line.length());
        mWriter.newLine();
    }
}
