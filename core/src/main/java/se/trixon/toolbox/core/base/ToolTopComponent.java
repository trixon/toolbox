/* 
 * Copyright 2018 Patrik Karlsson.
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
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.openide.windows.TopComponent;
import se.trixon.almond.nbp.dialogs.NbMessage;
import se.trixon.almond.util.AlmondOptions;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.icons.IconColor;
import se.trixon.toolbox.core.Toolbox;

/**
 *
 * @author Patrik Karlsson
 */
public abstract class ToolTopComponent extends TopComponent {

    protected static final int TOOLBAR_ICON_SIZE = 32;
    private String mStatus = "";
    protected ResourceBundle mBundle;
    protected InputOutput mInputOutput;
    protected StringBuilder mLogBuilder;
    protected String mHelpId = null;
    protected String mToolName;
    protected final IconColor mIconColor = AlmondOptions.getInstance().getIconColor();

    public void appendLog(String string) {
        mLogBuilder.append(string).append("\n");

        try (OutputWriter writer = mInputOutput.getOut()) {
            writer.append(string + "\n");
        }
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        if (mInputOutput != null) {
            mInputOutput.closeInputOutput();
        }
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        Toolbox.setStatusText(mStatus);
    }

    @Override
    protected void componentDeactivated() {
        super.componentDeactivated();
        mStatus = StatusDisplayer.getDefault().getStatusText();
        Toolbox.clearStatusText();
    }

    protected void displayHelp(final String helpId) {

        SwingUtilities.invokeLater(() -> {
            if (!new HelpCtx(helpId).display()) {
                NbMessage.error(Dict.Dialog.TITLE_HELP_NOT_FOUND.toString(), String.format(Dict.Dialog.MESSAGE_HELP_NOT_FOUND.toString(), helpId));
            }
        });
    }

    protected void logAppend(String string) {
        mLogBuilder.append(string).append("\n");

        try (OutputWriter writer = mInputOutput.getOut()) {
            writer.append(string + "\n");
        }
    }

    protected void logClear() {
        mLogBuilder = new StringBuilder();

        if (mInputOutput != null) {
            mInputOutput.closeInputOutput();
        }

        mInputOutput = IOProvider.getDefault().getIO(mToolName, false);
        mInputOutput.select();
    }
}
