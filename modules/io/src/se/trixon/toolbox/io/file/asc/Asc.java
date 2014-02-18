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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Asc {

    private AscHeader mAscHeader;
    private Charset mCharset = Charset.forName("US-ASCII");
    private Path mPath;
    private BufferedReader mReader;
    private double[][] mData;

    public static FileNameExtensionFilter getFileNameExtensionFilter() {
        return new FileNameExtensionFilter("*.asc", "asc");
    }

    public Asc() {
    }

    public Asc(Charset charset) {
        this();
        mCharset = charset;
    }

    public double[][] getData() {
        return mData;
    }

    public File getFile() {
        return mPath.toFile();
    }

    public AscHeader getHeader() {
        return mAscHeader;
    }

    public Path getPath() {
        return mPath;
    }

    public boolean isValid() {
        return (mAscHeader != null
                && mAscHeader.getNcols() > 0
                && mAscHeader.getNrows() > 0
                && mAscHeader.getCellSize() > Double.MIN_VALUE
                && mAscHeader.getXllcorner() > Double.MIN_VALUE
                && mAscHeader.getYllcorner() > Double.MIN_VALUE);
    }

    public void read(File file, boolean... headerOnly) throws IOException, NumberFormatException {
        boolean quickRead = headerOnly.length > 0 && headerOnly[0] == true;

        if (file.isFile() && file.exists()) {
            mReader = Files.newBufferedReader(file.toPath(), mCharset);
            mAscHeader = new AscHeader(mReader);
            if (!quickRead) {
                mData = new double[mAscHeader.getNcols()][mAscHeader.getNrows()];
                for (int i = 0; i < mAscHeader.getNrows(); i++) {
                    String[] rowData = mReader.readLine().split(" ");
                    for (int j = 0; j < mAscHeader.getNcols(); j++) {
                        mData[i][j] = Double.parseDouble(rowData[j]);
                    }
                }
            }
        } else {
            throw new IOException();
        }
    }
}
