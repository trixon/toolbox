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
package se.trixon.toolbox.core.base;

import java.awt.event.ActionEvent;
import java.util.MissingResourceException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.modules.Modules;
import org.openide.util.NbBundle;
import se.trixon.almond.util.SystemHelper;
import se.trixon.toolbox.core.ToolProvider;
import se.trixon.toolbox.core.actions.ToolInfoAction;
import se.trixon.toolbox.core.actions.ToolOptionsAction;

/**
 *
 * @author Patrik Karlsson
 */
public class ToolController implements ToolProvider {

    private ActionMap mActionMap;
    private ToolTopComponent mToolTopComponent;

    public ToolController() {
    }

    public ToolController(ToolTopComponent toolTopComponent) {
        mToolTopComponent = toolTopComponent;
//        mActionMap = toolTopComponent.getActionMap();

        init();
    }

    @Override
    public String getCategory() {
        return getResource("Tool-Category");
    }

    @Override
    public String getCopyright() {
        return getResource("Tool-Copyright");
    }

    @Override
    public String getCredit() {
        return getResource("Tool-Credit");
    }

    @Override
    public String getDescription() {
        return getResource("Tool-Description");
    }

    @Override
    public String getLicense() {
        return getResource("Tool-License");
    }

    @Override
    public String getModuleName() {
        return Modules.getDefault().ownerOf(this.getClass()).getDisplayName();
    }

    @Override
    public String getName() {
        return getResource("Tool-Name");
    }

//    @Override
//    public ResourceBundle getNewsBundle() {
//        return null;
//    }
    @Override
    public String getOptionsPath() {
        return null;
    }

    public String getPackageAsPath() {
        return SystemHelper.getPackageAsPath(this.getClass());
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

    @Override
    public String getVersion() {
        return getResource("Tool-Version");
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
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" ").append(getVersion()).append("\n");
        builder.append(getDescription()).append("\n\n");
        builder.append(getCopyright());
        if (!getCredit().isEmpty()) {
            builder.append("\n\n").append(getCredit());
        }

        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(builder.toString(), NotifyDescriptor.INFORMATION_MESSAGE);
        notifyDescriptor.setTitle(NbBundle.getMessage(ToolInfoAction.class, "CTL_ToolInfoAction"));
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }

    private void showOptions() {
//        OptionsDisplayer.getDefault().open(getOptionsPath());
//TODO
        System.err.println("showOptions");
    }
}
