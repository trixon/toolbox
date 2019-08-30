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

import java.io.File;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Patrik Karlström
 */
public enum TbToolboxOptions {

    INSTANCE;
    public static final String DEFAULT_DIR_OPEN = System.getProperty("user.home");
    public static final String DEFAULT_DIR_SAVE = System.getProperty("user.home");
    public static final String KEY_DIR_OPEN = "dirOpen";
    public static final String KEY_DIR_SAVE = "dirSave";
    private static Preferences mPreferences = NbPreferences.forModule(TbToolboxOptions.class);

    public static Preferences getPreferences() {
        return mPreferences;
    }

    public File getDirOpen() {
        return new File(mPreferences.get(KEY_DIR_OPEN, DEFAULT_DIR_OPEN));
    }

    public File getDirSave() {
        return new File(mPreferences.get(KEY_DIR_SAVE, DEFAULT_DIR_SAVE));
    }

    public void setDirOpen(File dir) {
        mPreferences.put(KEY_DIR_OPEN, dir.getAbsolutePath());
    }

    public void setDirSave(File dir) {
        mPreferences.put(KEY_DIR_SAVE, dir.getAbsolutePath());
    }

    public void setPreferences(Preferences preferences) {
        mPreferences = preferences;
    }
}
