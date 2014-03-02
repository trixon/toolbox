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
package se.trixon.toolbox.io.file.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Csv {

    private Charset mCharset = Charset.forName("windows-1252");
    private String mLineEnding = "\r\n";
    private final List<String> mLines = new LinkedList<>();
    private BufferedReader mReader;
    private BufferedWriter mWriter;

    public static FileNameExtensionFilter getFileNameExtensionFilter() {
        return new FileNameExtensionFilter("*.csv", "csv");
    }

    public Csv() {
    }

    public Csv(Charset charset) {
        this();
        mCharset = charset;
    }

    public void closeWriter() throws IOException {
        mWriter.close();
    }

    public String getLineEnding() {
        return mLineEnding;
    }

    public void openWriter(File file) throws IOException {
        mWriter = Files.newBufferedWriter(file.toPath(), mCharset);
    }

    public void read(File file) throws IOException {
        mReader = Files.newBufferedReader(file.toPath(), mCharset);
        String line;
        while ((line = mReader.readLine()) != null) {
            mLines.add(line);
        }

        mReader.close();
    }

    public void setLineEnding(String lineEnding) {
        mLineEnding = lineEnding;
    }

    public void write(String line) throws IOException {
        mWriter.write(line + mLineEnding);
    }

    public void write(File file) throws IOException {
        openWriter(file);

        for (String line : mLines) {
            mWriter.write(line + mLineEnding);
        }

        closeWriter();
    }
}
