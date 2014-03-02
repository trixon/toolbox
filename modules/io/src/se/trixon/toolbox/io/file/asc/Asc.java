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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Locale;
import javax.swing.filechooser.FileNameExtensionFilter;
import se.trixon.toolbox.io.file.CoordinateFile;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Asc extends CoordinateFile {

    private double[][] mData;
    private AscHeader mHeader;

    public static FileNameExtensionFilter getFileNameExtensionFilter() {
        return new FileNameExtensionFilter("*.asc", "asc");
    }

    public Asc() {
        mHeader = new AscHeader();
    }

    public Asc(Charset charset) {
        this();
        mCharset = charset;
    }

    public double[][] getData() {
        return mData;
    }

    public AscHeader getHeader() {
        return mHeader;
    }

    public boolean isValid() {
        return (mHeader != null
                && mHeader.getNcols() > 0
                && mHeader.getNrows() > 0
                && mHeader.getCellSize() > Double.MIN_VALUE
                && mHeader.getXllcorner() > Double.MIN_VALUE
                && mHeader.getYllcorner() > Double.MIN_VALUE);
    }

    @Override
    public void openWriter(File file) throws IOException {
        String llFormat = String.format(Locale.ENGLISH, "%%s %%.%df\r\n", mXYPrecision);

        StringBuilder builder = new StringBuilder();
        builder.append(String.format(Locale.ENGLISH, "ncols %d\r\n", mHeader.getNcols()));
        builder.append(String.format(Locale.ENGLISH, "nrows %d\r\n", mHeader.getNrows()));
        builder.append(String.format(Locale.ENGLISH, llFormat, "xllcorner", mHeader.getXllcorner()));
        builder.append(String.format(Locale.ENGLISH, llFormat, "yllcorner", mHeader.getYllcorner()));
        builder.append(String.format(Locale.ENGLISH, "cellsize %f\r\n", mHeader.getCellSize()));
        builder.append(String.format(Locale.ENGLISH, "nodata_value %f\r\n", mHeader.getNodata()));

        mWriter = Files.newBufferedWriter(file.toPath(), mCharset);
        mWriter.write(builder.toString());
    }

    public void read(File file, boolean... headerOnly) throws IOException, NumberFormatException {
        boolean quickRead = headerOnly.length > 0 && headerOnly[0] == true;

        if (file.isFile() && file.exists()) {
            mPath = file.toPath();
            mReader = Files.newBufferedReader(mPath, mCharset);
            mHeader = new AscHeader(mReader, mPath);
            if (!quickRead) {
                mData = new double[mHeader.getNrows()][mHeader.getNcols()];
                for (int row = 0; row < mHeader.getNrows(); row++) {
                    String[] rowData = mReader.readLine().split(" ");
                    for (int col = 0; col < mHeader.getNcols(); col++) {
                        mData[row][col] = Double.parseDouble(rowData[col]);
                    }
                }
            }
        } else {
            throw new IOException();
        }
    }

    public void readHeader(File file) throws IOException, NumberFormatException {
        read(file, true);
    }

    public void setData(double[][] data) {
        mData = data;
    }

    public void write(File file) throws IOException {
        openWriter(file);
        String format = String.format(Locale.ENGLISH, "%%.%df ", mValuePrecision);

        for (int row = 0; row < mData.length; row++) {
            StringBuilder builder = new StringBuilder();

            for (double value : mData[row]) {
                builder.append(String.format(Locale.ENGLISH, format, value));
            }

            builder.deleteCharAt(builder.length() - 1);
            builder.append("\r\n");
            mWriter.write(builder.toString());
        }
        closeWriter();
    }
}
