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
import se.trixon.toolbox.io.Io;
import se.trixon.toolbox.io.file.CoordinatePoint;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class GeoPoint extends CoordinatePoint {

    private List<GeoAttribute> mAttributes = new LinkedList<>();
    private String mPointCode = "";
    private String mPointId = "";
    private String mRemark = "";
    private String mSpecialCode = "";

    public GeoPoint() {
    }

    public String getPointCode() {
        return mPointCode;
    }

    public String getPointId() {
        return mPointId;
    }

    public String getRemark() {
        return mRemark;
    }

    public String getSpecialCode() {
        return mSpecialCode;
    }

    public void setAttributes(List<GeoAttribute> attributes) {
        mAttributes = attributes;
    }

    public void setPointCode(String pointCode) {
        mPointCode = Io.stripString(pointCode, 8);
    }

    public void setPointId(String pointId) {
        mPointId = Io.stripString(pointId, 12);
    }

    public void setRemark(String remark) {
        mRemark = Io.stripString(remark, 12);
    }

    public void setSpecialCode(String specialCode) {
        mSpecialCode = Io.stripString(specialCode, 2);
    }
}
