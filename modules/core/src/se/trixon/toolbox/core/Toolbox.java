package se.trixon.toolbox.core;

import org.openide.awt.StatusDisplayer;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Toolbox {

    public static final String LOG_TAG = "Toolbox";

    public static void setStatusText(String text, int importance) {
        StatusDisplayer.getDefault().setStatusText(text, importance);
    }
}
