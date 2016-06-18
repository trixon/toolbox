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
package se.trixon.toolbox.core;

import java.awt.Frame;
import java.text.DateFormat;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.WindowManager;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Toolbox {

    public static final String LOG_TAG = "Toolbox";

    public static Frame getFrame() {
        return WindowManager.getDefault().getMainWindow();
    }

    public static void clearStatusText() {
        setStatusText("");
    }

    public static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
    }

    public static void setStatusText(String text, int importance) {
        StatusDisplayer.getDefault().setStatusText(text, importance);
    }

    public static void setStatusText(String text) {
        setStatusText(text, StatusDisplayer.IMPORTANCE_ANNOTATION);
    }
}
