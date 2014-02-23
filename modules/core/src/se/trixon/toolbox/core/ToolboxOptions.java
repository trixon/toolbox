package se.trixon.toolbox.core;

import java.io.File;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public enum ToolboxOptions {

    INSTANCE;
    public static final String DEFAULT_DIR_OPEN = System.getProperty("user.home");
    public static final String DEFAULT_DIR_SAVE = System.getProperty("user.home");
    public static final String KEY_DIR_OPEN = "dirOpen";
    public static final String KEY_DIR_SAVE = "dirSave";
    private static Preferences mPreferences = NbPreferences.forModule(ToolboxOptions.class);

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
