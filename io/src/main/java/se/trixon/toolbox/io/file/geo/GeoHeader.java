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
package se.trixon.toolbox.io.file.geo;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GeoHeader {

    private String mApplication = "";
    private String mAuthor = "";
    private String mCompany = "";
    private String mDocumentDescription = "Coordinate Document";
    private String mDocumentVersion = "SBG Object Text v2.01";
    private String mSerialNo = "";
    private String mDescription = "";
    private String mLineEnding = "\r\n";

    public GeoHeader() {
        mApplication = "Trixon Toolbox";
        mAuthor = System.getProperty("user.name");
        mCompany = "ACME";
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getApplication() {
        return mApplication;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getCompany() {
        return mCompany;
    }

    public String getDocumentDescription() {
        return mDocumentDescription;
    }

    public String getDocumentVersion() {
        return mDocumentVersion;
    }

    public String getSerialNo() {
        return mSerialNo;
    }

    public void setApplication(String application) {
        mApplication = application;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public void setDocumentDescription(String documentDescription) {
        mDocumentDescription = documentDescription;
    }

    public void setDocumentVersion(String documentVersion) {
        mDocumentVersion = documentVersion;
    }

    public void setSerialNo(String serialNo) {
        mSerialNo = serialNo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("FileHeader \"%s\",\"%s\"", mDocumentVersion, mDocumentDescription)).append(mLineEnding);
        builder.append("begin").append(mLineEnding);
        builder.append(String.format("\tFileInfo \"%s\",\"%s\"", "Application", mApplication)).append(mLineEnding);
        builder.append(String.format("\tFileInfo \"%s\",\"%s\"", "Author", mAuthor)).append(mLineEnding);
        builder.append(String.format("\tFileInfo \"%s\",\"%s\"", "Company", mCompany)).append(mLineEnding);
        builder.append(String.format("\tFileInfo \"%s\",\"%s\"", "SerialNo", mSerialNo)).append(mLineEnding);
        builder.append(String.format("\tFileInfo \"%s\",\"%s\"", "Description", mDescription)).append(mLineEnding);
        builder.append("end").append(mLineEnding);

        return builder.toString();
    }

}
