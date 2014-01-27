package se.trixon.toolbox.core;

import java.util.ResourceBundle;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.toolbox.core.base.ToolController;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProvider(service = ToolProvider.class)
public class ToolboxInfo extends ToolController {

    @Override
    public String getCopyright() {
        return "© 2014 Patrik Karlsson";
    }

    @Override
    public String getLicense() {
        return "Apache 2.0";
    }

    @Override
    public String getVersion() {
        String version = ResourceBundle.getBundle("se/trixon/toolbox/branding/about").getString("application.version");

        return version;
    }
}
