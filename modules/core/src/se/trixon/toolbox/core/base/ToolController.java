package se.trixon.toolbox.core.base;

import java.awt.event.ActionEvent;
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
        setActiveOptions(getToolOptionsPath() != null);
    }

    private void showDescription() {
        String message = String.format("%s %s\n%s\n\n%s", getToolName(), getToolVersion(), getToolDescription(), getToolCopyright());

        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        notifyDescriptor.setTitle(NbBundle.getMessage(ToolInfoAction.class, "CTL_ToolInfoAction"));
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }

    private void showOptions() {
        OptionsDisplayer.getDefault().open(getToolOptionsPath());
    }
}
