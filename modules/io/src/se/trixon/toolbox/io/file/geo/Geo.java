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
package se.trixon.toolbox.io.file.geo;

import java.util.LinkedList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import se.trixon.toolbox.io.file.CoordinateFile;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Geo extends CoordinateFile {

    private List<GeoAttribute> mAttributes = new LinkedList<>();
    private GeoHeader mHeader;
    private List<GeoLine> mLines = new LinkedList<>();
    private List<GeoPoint> mPoints = new LinkedList<>();

    public Geo() {
    }

    public static FileNameExtensionFilter getFileNameExtensionFilter() {
        return new FileNameExtensionFilter("*.geo", "geo");
    }

    public List<GeoAttribute> getAttributes() {
        return mAttributes;
    }

    public GeoHeader getHeader() {
        return mHeader;
    }

    public List<GeoLine> getLines() {
        return mLines;
    }

    public List<GeoPoint> getPoints() {
        return mPoints;
    }

    public void setAttributes(List<GeoAttribute> attributes) {
        mAttributes = attributes;
    }

    public void setHeader(GeoHeader header) {
        mHeader = header;
    }

    public void setLines(List<GeoLine> lines) {
        mLines = lines;
    }

    public void setPoints(List<GeoPoint> points) {
        mPoints = points;
    }
}
