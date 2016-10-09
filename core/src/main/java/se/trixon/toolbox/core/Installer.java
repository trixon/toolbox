/*
 * Copyright 2016 Patrik Karlsson.
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

import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import org.openide.modules.OnStart;
import org.openide.util.NbPreferences;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import se.trixon.almond.nbp.ActionHelper;
import se.trixon.almond.nbp.NbLog;
import se.trixon.almond.util.AlmondOptions;
import se.trixon.almond.util.icons.IconColor;
import se.trixon.almond.util.icons.material.MaterialIcon;
import se.trixon.toolbox.core.startpage.StartPageTopComponent;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@OnStart
public class Installer implements Runnable {

    private final Preferences mPreferences;

    public Installer() {
        mPreferences = NbPreferences.forModule(StartPageTopComponent.class);
    }

    @Override
    public void run() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("se/trixon/toolbox/core/about/about");
            JFrame mainFrame = (JFrame) WindowManager.getDefault().getMainWindow();
            mainFrame.setTitle(bundle.getString("application.title"));
//                openWindow("output");
            if (mPreferences.getBoolean(StartPageTopComponent.KEY_SHOW_START_PAGE_ON_STARTUP, true)) {
                openWindow("StartPageTopComponent");
            }

            IconColor iconColor = AlmondOptions.getInstance().getIconColor();
            ActionHelper.setIconSmall("Actions/File", "se.trixon.almond.nbp.actions.QuitAction", MaterialIcon._Navigation.CLOSE.get(16, iconColor));
            ActionHelper.setIconLarge("Actions/File", "se.trixon.almond.nbp.actions.QuitAction", MaterialIcon._Navigation.CLOSE.get(24, iconColor));
            NbLog.select();
            NbLog.v(Toolbox.LOG_TAG, "Loaded and ready...");
        });
    }

    private void openWindow(String id) {
        TopComponent topComponent = WindowManager.getDefault().findTopComponent(id);
        if (topComponent != null) {
            topComponent.open();
        }
    }
}
