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

import java.awt.event.ActionEvent;
import java.util.MissingResourceException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import org.netbeans.api.options.OptionsDisplayer;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import se.trixon.toolbox.core.ToolProvider;
import se.trixon.toolbox.core.actions.ToolInfoAction;
import se.trixon.toolbox.core.actions.ToolOptionsAction;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public abstract class ToolController implements ToolProvider {

    private ActionMap mActionMap;
    private ToolTopComponent mToolTopComponent;

    public ToolController() {
    }

    public ToolController(ToolTopComponent toolTopComponent) {
        mToolTopComponent = toolTopComponent;
        mActionMap = toolTopComponent.getActionMap();

        init();
    }

    @Override
    public String getCategory() {
        return getResource("OpenIDE-Module-Display-Category");
    }

    @Override
    public String getDescription() {
        return getResource("OpenIDE-Module-Short-Description");
    }

    @Override
    public String getName() {
        return getResource("OpenIDE-Module-Name");
    }

    @Override
    public String getOptionsPath() {
        return null;
    }

    public String getResource(String key) {
        try {
            return NbBundle.getMessage(this.getClass(), key);
        } catch (MissingResourceException e) {
            return "";
        }
    }

    public ToolTopComponent getToolTopComponent() {
        return mToolTopComponent;
    }

    protected void setActiveInformation(boolean state) {

        if (state) {
            mActionMap.put(ToolInfoAction.KEY, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    showDescription();
                }
            });
        } else {
            mActionMap.remove(ToolInfoAction.KEY);
        }
    }

    protected void setActiveOptions(boolean state) {
        if (state) {
            mActionMap.put(ToolOptionsAction.KEY, new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    showOptions();
                }
            });
        } else {
            mActionMap.remove(ToolOptionsAction.KEY);
        }
    }

    private void init() {
        setActiveInformation(true);
        setActiveOptions(getOptionsPath() != null);
    }

    private void showDescription() {
        String message = String.format("%s %s\n%s\n\n%s", getName(), getVersion(), getDescription(), getCopyright());

        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        notifyDescriptor.setTitle(NbBundle.getMessage(ToolInfoAction.class, "CTL_ToolInfoAction"));
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }

    private void showOptions() {
        OptionsDisplayer.getDefault().open(getOptionsPath());
    }
}
