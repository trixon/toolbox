/*
 * Copyright 2019 Patrik Karlström.
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
package se.trixon.toolbox.api;

import java.text.DateFormat;

/**
 *
 * @author Patrik Karlström
 */
public class TbToolbox {

    public static final int ICON_SIZE_PROFILE = 32;
    public static final int ICON_SIZE_TOOLBAR = 40;
    public static final int ICON_SIZE_DRAWER = ICON_SIZE_TOOLBAR / 2;
    public static final String LOG_TAG = "Toolbox";
    public static final int MODULE_ICON_SIZE = 32;

    public static void clearStatusText() {
        setStatusText("");
    }

    public static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
    }

    public static void setStatusText(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
