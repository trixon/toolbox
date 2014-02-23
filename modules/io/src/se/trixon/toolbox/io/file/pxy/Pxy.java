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
package se.trixon.toolbox.io.file.pxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import se.trixon.toolbox.io.Io;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Pxy {

    private Charset mCharset = Charset.forName("windows-1252");
    private String mDate = "";
    private String mDescription = "";
    private String mIdText = "XYZ-COORD-FILE";
    private final List<PxyPoint> mPoints = new LinkedList<>();
    private BufferedReader mReader;
    private String mReserved1 = "";
    private String mReserved2 = "";
    private String mVersion = "V1.00";
    private BufferedWriter mWriter;

    public static FileNameExtensionFilter getFileNameExtensionFilter() {
        return new FileNameExtensionFilter("*.pxy", "pxy");
    }

    public Pxy() {
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    public Pxy(Charset charset) {
        this();
        mCharset = charset;
    }

    public void addPoint(PxyPoint pxyPoint) {
        mPoints.add(pxyPoint);
    }

    public void closeWriter() throws IOException {
        mWriter.close();
    }

    public String getDate() {
        return mDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIdText() {
        return mIdText;
    }

    public String getReserved1() {
        return mReserved1;
    }

    public String getReserved2() {
        return mReserved2;
    }

    public String getVersion() {
        return mVersion;
    }

    public void openWriter(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-16s,", mIdText));
        builder.append(String.format("%-5s,", mVersion));
        builder.append(String.format("%-10s,", mDate));
        builder.append(String.format("%-40s", mReserved1));
        builder.append(",\r\n");
        builder.append(String.format("%-40s,", mDescription));
        builder.append(String.format("%-33s", mReserved2));
        builder.append(",\r\n");

        mWriter = Files.newBufferedWriter(file.toPath(), mCharset);
        mWriter.write(builder.toString());
    }

    public void read(File file) throws IOException {
        mReader = Files.newBufferedReader(file.toPath(), mCharset);
        String line = mReader.readLine();
        setIdText(line.substring(0, 16));
        setVersion(line.substring(17, 22));
        setDate(line.substring(23, 33));
        setReserved1(line.substring(34, 73));

        line = mReader.readLine();
        setDescription(line.substring(0, 38));
        setReserved2(line.substring(41, 73));

        while ((line = mReader.readLine()) != null) {
            mPoints.add(new PxyPoint(line));
        }

        mReader.close();
    }

    public void setDate(String date) {
        date = Io.stripString(date, 10);
        if (!date.isEmpty()) {
            mDate = date;
        }
    }

    public void setDescription(String description) {
        mDescription = Io.stripString(description, 39);
    }

    public void setIdText(String idText) {
        mIdText = Io.stripString(idText, 16);
    }

    public void setReserved1(String reserved1) {
        mReserved1 = Io.stripString(reserved1, 39);
    }

    public void setReserved2(String reserved2) {
        mReserved2 = Io.stripString(reserved2, 33);
    }

    public void setVersion(String version) {
        mVersion = Io.stripString(version, 5);
    }

    public void write(PxyPoint pxyPoint) throws IOException {
        mWriter.write(pxyPoint.toString());
    }

    public void write(File file) throws IOException {
        openWriter(file);

        for (PxyPoint pxyPoint : mPoints) {
            mWriter.write(pxyPoint.toString());
        }

        closeWriter();
    }
}