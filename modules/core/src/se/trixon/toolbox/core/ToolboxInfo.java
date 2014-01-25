package se.trixon.toolbox.core;

import java.util.ResourceBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProvider(service = ToolProvider.class)
public class ToolboxInfo implements ToolProvider {

    @Override
    public String getToolCategory() {
        return "Core";
    }

    @Override
    public String getToolCopyright() {
        return "© 2014 Patrik Karlsson";
    }

    @Override
    public String getToolDescription() {
        return "Trixon Toolbox";
    }

    @Override
    public String getToolLicense() {
        return "Apache 2.0";
    }

    @Override
    public String getToolName() {
        return "Toolbox";
    }

    @Override
    public String getToolOptionsPath() {
        return null;
    }

    @Override
    public String getToolVersion() {
        String version = ResourceBundle.getBundle("se/trixon/toolbox/branding/about").getString("application.version");

        return version;
    }

}
