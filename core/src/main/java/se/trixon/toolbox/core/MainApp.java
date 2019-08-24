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
package se.trixon.toolbox.core;

import de.codecentric.centerdevice.MenuToolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.controlsfx.control.action.Action;
import org.openide.LifecycleManager;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.fx.AlmondFx;

public class MainApp extends Application {

    public static final String APP_TITLE = "Trixon Toolbox";
    public static final int ICON_SIZE_PROFILE = 32;
    public static final int ICON_SIZE_TOOLBAR = 40;
    public static final int ICON_SIZE_DRAWER = ICON_SIZE_TOOLBAR / 2;
    public static final int MODULE_ICON_SIZE = 32;
    private static final boolean IS_MAC = SystemUtils.IS_OS_MAC;
    private Action mAboutAction;
    private final AlmondFx mAlmondFX = AlmondFx.getInstance();
//    private FbdModule mFbdModule;
    private Action mHelpAction;
//    private MapollageModule mMapollageModule;
    private Action mOptionsAction;
//    private ToolbarItem mOptionsToolbarItem;
//    private PreferencesModule mPreferencesModule;
    private Stage mStage;
//    private Workbench mWorkbench;

    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    public MainApp() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        mStage = stage;
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("logo.png")));
//        stage.getIcons().add(new Image("org/netbeans/core/startup/frame48.gif"));

        mAlmondFX.addStageWatcher(stage, MainApp.class);
        createUI();
        if (IS_MAC) {
            initMac();
        }
        mStage.setTitle(APP_TITLE);
        mStage.show();
        initAccelerators();
        //mWorkbench.openModule(mPreferencesModule);
    }

    private void initAccelerators() {

    }

    private void initMac() {
        MenuToolkit menuToolkit = MenuToolkit.toolkit();
        Menu applicationMenu = menuToolkit.createDefaultApplicationMenu(APP_TITLE);
        menuToolkit.setApplicationMenu(applicationMenu);

        applicationMenu.getItems().remove(0);
        MenuItem aboutMenuItem = new MenuItem(String.format(Dict.ABOUT_S.toString(), APP_TITLE));
        aboutMenuItem.setOnAction(mAboutAction);

        MenuItem settingsMenuItem = new MenuItem(Dict.PREFERENCES.toString());
        settingsMenuItem.setOnAction(mOptionsAction);
        settingsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.COMMA, KeyCombination.SHORTCUT_DOWN));

        applicationMenu.getItems().add(0, aboutMenuItem);
        applicationMenu.getItems().add(2, settingsMenuItem);

        int cnt = applicationMenu.getItems().size();
        applicationMenu.getItems().get(cnt - 1).setText(String.format("%s %s", Dict.QUIT.toString(), APP_TITLE));
    }

    private void createUI() {
//        mPreferencesModule = new PreferencesModule();
//        mFbdModule = new FbdModule();
//        mMapollageModule = new MapollageModule();
//        mWorkbench = Workbench.builder(mFbdModule, mMapollageModule, mPreferencesModule).build();
//
//        mWorkbench.getStylesheets().add(MainApp.class.getResource("customTheme.css").toExternalForm());
//        initToolbar();
//        initWorkbenchDrawer();

//        Scene scene = new Scene(mWorkbench);
        Scene scene = new Scene(new Pane());
//        scene.getStylesheets().add("css/modena_dark.css");
        mStage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        LOGGER.log(Level.INFO, "request platform shutdown");
        LifecycleManager.getDefault().exit();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "manual start");
        launch(args);
    }
}
