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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.netbeans.modules.autoupdate.ui.PluginManagerUI;
import org.netbeans.modules.autoupdate.ui.api.PluginManager;

public class FXMLController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private CheckMenuItem checkMenuItemFullScreen;
    @FXML
    private CheckMenuItem checkMenuItemMenubar;
    @FXML
    private Pane holder;
    private final SwingNode mPluginManagerUiNode = new SwingNode();

    private Stage mStage;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuHelp;
    @FXML
    private MenuItem menuItemAbout;
    @FXML
    private MenuItem menuItemPlugin;
    @FXML
    private MenuItem menuItemQuit;
    @FXML
    private Menu menuTools;
    @FXML
    private Menu menuView;

    public FXMLController() {
        initPluginManagerUi(mPluginManagerUiNode);
    }

    public void init(Stage stage) {
        mStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void initPluginManagerUi(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JButton button = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(() -> {
                        Stage stage = (Stage) swingNode.getScene().getWindow();
                        stage.close();
                    });
                }
            });

            PluginManagerUI pluginManagerUI = new PluginManagerUI(button);
            swingNode.setContent(pluginManagerUI);
            PluginManager p;
        });
    }

    @FXML
    private void onActionAbout(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(String.format("About %s", mStage.getTitle()));
        alert.setHeaderText(String.format("%s %s", mStage.getTitle(), "v0.0.1"));
        alert.setContentText("Manage NetBeans Plugins from Java FX\n\n"
                + "Licensed under the Apache License, Version 2.0\n"
                + "Copyright 2019 Patrik Karlström");
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
    }

    @FXML
    private void onActionFullscreen(ActionEvent event) {
        mStage.setFullScreen(!mStage.isFullScreen());
    }

    @FXML
    private void onActionMenubar(ActionEvent event) {
        menuBar.setVisible(!menuBar.isVisible());
    }

    @FXML
    private void onActionPlugin(ActionEvent event) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Plugins");

        alert.getDialogPane().setContent(mPluginManagerUiNode);
        alert.setResizable(true);
        alert.showAndWait();
    }

    @FXML
    private void onActionQuit(ActionEvent event) {
        mStage.fireEvent(new WindowEvent(mStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
