package se.trixon.toolbox.core.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import se.trixon.toolbox.core.Toolbox;

@ActionID(
        category = "File",
        id = "se.trixon.toolbox.core.actions.ToolListAction"
)
@ActionRegistration(iconBase = "se/trixon/toolbox/core/res/format-list-ordered.png",
        displayName = "#CTL_ToolListAction",
        iconInMenu = true)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 30, separatorAfter = 31),
    @ActionReference(path = "Toolbars/File", position = 30),})

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public final class ToolListAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolbox.outln(Toolbox.LOG_TITLE, NbBundle.getMessage(ToolListAction.class, "CTL_ToolListAction"));
    }
}
