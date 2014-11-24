/*
 * Copyright 2014 Patrik Karlsson.
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
package se.trixon.toolbox.core.base;

import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import org.openide.awt.StatusDisplayer;
import org.openide.util.HelpCtx;
import org.openide.windows.TopComponent;
import se.trixon.almond.dialogs.Message;
import se.trixon.almond.dictionary.Dict;
import se.trixon.toolbox.core.Toolbox;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public abstract class ToolTopComponent extends TopComponent {

    protected static final int TOOLBAR_ICON_SIZE = 24;
    protected ResourceBundle mBundle;
    protected String mToolName;
    private String mStatus = "";

    @Override
    protected void componentDeactivated() {
        super.componentDeactivated();
        mStatus = StatusDisplayer.getDefault().getStatusText();
        Toolbox.clearStatusText();
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        Toolbox.setStatusText(mStatus);
    }

    protected void displayHelp(final String helpId) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                if (!new HelpCtx(helpId).display()) {
                    Message.error(Dict.HELP_NOT_FOUND_TITLE.getString(), String.format(Dict.HELP_NOT_FOUND_MESSAGE.getString(), helpId));
                }
            }
        });
    }
}
