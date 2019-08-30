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
package se.trixon.toolbox.core.ui;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.icons.material.MaterialIcon;
import se.trixon.toolbox.api.TbPreferences;
import static se.trixon.toolbox.api.TbToolbox.*;

public class PreferencesModule extends WorkbenchModule {

    public PreferencesModule() {
        super(Dict.OPTIONS.toString(), MaterialIcon._Action.SETTINGS.getImageView(MODULE_ICON_SIZE).getImage());

        ToolbarItem saveToolbarItem = new ToolbarItem(new MaterialDesignIconView(MaterialDesignIcon.CONTENT_SAVE), event -> TbPreferences.getInstance().save());
        ToolbarItem discardToolbarItem = new ToolbarItem(new MaterialDesignIconView(MaterialDesignIcon.DELETE),
                event -> getWorkbench().showConfirmationDialog("Discard Changes",
                        "Are you sure you want to discard all changes since you last saved?",
                        buttonType -> {
                            if (ButtonType.YES.equals(buttonType)) {
                                TbPreferences.getInstance().discardChanges();
                            }
                        })
        );

        getToolbarControlsLeft().addAll(saveToolbarItem, discardToolbarItem);
    }

    @Override
    public Node activate() {
        return TbPreferences.getInstance().getPreferencesFxView();
    }

    @Override
    public boolean destroy() {
        TbPreferences.getInstance().save();
        return true;
    }
}
