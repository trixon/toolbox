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

import se.trixon.toolbox.core.ui.MainApp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static final Logger LOGGER = Logger.getLogger(Installer.class.getName());

    @Override
    public void close() {
        LOGGER.log(Level.INFO, "Toolbox closed");
        super.close();
    }

    @Override
    public boolean closing() {
        LOGGER.log(Level.INFO, "Closing Toolbox");
        return super.closing();
    }

    @Override
    public void restored() {
        LOGGER.log(Level.INFO, "Opening Toolbox");
        new Thread(() -> {
            MainApp.main(new String[]{});
        }).start();
    }
}
