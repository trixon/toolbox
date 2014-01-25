package se.trixon.toolbox.core.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class ToolInfoAction {

    @ActionID(category = "file",
            id = "se.trixon.toolbox.core.actions.ToolInfoAction")
    @ActionRegistration(iconBase = "se/trixon/toolbox/core/res/dialog-information.png",
            displayName = "#CTL_ToolInfoAction",
            iconInMenu = true)
    @ActionReferences({
        @ActionReference(path = "Menu/File", position = 10),
        @ActionReference(path = "Toolbars/File", position = 10),})
    public static final String KEY = "ToolInfoAction";
}
