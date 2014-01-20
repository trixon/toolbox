package se.trixon.toolbox.branding;

import org.openide.modules.ModuleInstall;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        AboutInitializer.init();
    }
}
