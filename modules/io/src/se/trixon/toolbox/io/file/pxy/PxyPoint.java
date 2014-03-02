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

import java.util.Locale;
import se.trixon.toolbox.io.Io;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class PxyPoint {

    private static String sLineEnding;
    private String mPointCode = "";
    private String mPointId = "";
    private String mRemark = "";
    private String mSpecialCode = "";
    private double mX;
    private double mY;
    private double mZ;

    public PxyPoint() {
    }

    public PxyPoint(String line) throws NumberFormatException {
        setPointId(line.substring(0, 12));
        mX = Double.parseDouble(line.substring(14, 26));
        mY = Double.parseDouble(line.substring(26, 38));
        mZ = Double.parseDouble(line.substring(38, 50));
        setPointCode(line.substring(51, 59));
        setSpecialCode(line.substring(60, 62));
        setRemark(line.substring(62, 73));
    }

    public PxyPoint(String pointId, double x, double y, double z, String pointCode) {
        setPointId(pointId);
        mX = x;
        mY = y;
        mZ = z;
        setPointCode(pointCode);
    }

    public static void setLineEnding(String lineEnding) {
        PxyPoint.sLineEnding = lineEnding;
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

    public double getX() {
        return mX;
    }

    public double getY() {
        return mY;
    }

    public double getZ() {
        return mZ;
    }

    public final void setPointCode(String pointCode) {
        mPointCode = Io.stripString(pointCode, 8);
    }

    public final void setPointId(String pointId) {
        mPointId = Io.stripString(pointId, 12);
    }

    public final void setRemark(String remark) {
        mRemark = Io.stripString(remark, 12);
    }

    public final void setSpecialCode(String specialCode) {
        mSpecialCode = Io.stripString(specialCode, 2);
    }

    public void setX(double x) {
        mX = x;
    }

    public void setY(double y) {
        mY = y;
    }

    public void setZ(double z) {
        mZ = z;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-12s", mPointId));
        builder.append("  ");
        builder.append(String.format(Locale.ENGLISH, "%12.4f", mX));
        builder.append(String.format(Locale.ENGLISH, "%12.4f", mY));
        builder.append(String.format(Locale.ENGLISH, "%12.4f", mZ));
        builder.append(" ");
        builder.append(String.format("%-8s", mPointCode));
        builder.append(" ");
        builder.append(String.format("%-2s", mSpecialCode));
        builder.append(String.format("%-12s", mRemark));
        builder.append(",").append(sLineEnding);

        return builder.toString();
    }
}
