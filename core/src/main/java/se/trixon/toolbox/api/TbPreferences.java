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

import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.view.PreferencesFxView;
import org.openide.util.Lookup;
import se.trixon.almond.util.Dict;
import se.trixon.toolbox.core.ui.PreferencesModule;

/**
 * Model object for TbPreferences.
 */
public class TbPreferences {

    private final TbGeneralPreferences mGeneralPreferences = new TbGeneralPreferences();
    private PreferencesFx mPreferencesFx;

    public static TbPreferences getInstance() {
        return Holder.INSTANCE;
    }

    private TbPreferences() {
        createPreferences();
    }

    public void createPreferences() {
        Category[] categories = Lookup.getDefault().lookupAll(TbToolPreference.class).stream()
                .sorted((TbToolPreference o1, TbToolPreference o2) -> o1.getCategory().getDescription().compareToIgnoreCase(o2.getCategory().getDescription()))
                .map(p -> p.getCategory())
                .toArray(Category[]::new);

        mPreferencesFx = PreferencesFx.of(PreferencesModule.class,
                mGeneralPreferences.getCategory(),
                Category.of(Dict.TOOLS.toString())
                        .expand()
                        .subCategories(categories)
        ).persistWindowState(false).saveSettings(true).debugHistoryMode(false).buttonsVisibility(true);
    }

    public void discardChanges() {
        mPreferencesFx.discardChanges();
    }

    public TbGeneralPreferences general() {
        return mGeneralPreferences;
    }

    public <T> T getForClass(Class c) {
        return (T) (Lookup.getDefault().lookup(c));
    }

    public PreferencesFxView getPreferencesFxView() {
        return mPreferencesFx.getView();
    }

    public void save() {
        mPreferencesFx.saveSettings();
    }

    private static class Holder {

        private static final TbPreferences INSTANCE = new TbPreferences();
    }
}
