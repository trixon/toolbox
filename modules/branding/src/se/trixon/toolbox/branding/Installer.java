package se.trixon.toolbox.branding;

import java.util.ResourceBundle;
import javax.swing.JFrame;
import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        AboutInitializer.init();
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {
                ResourceBundle bundle = ResourceBundle.getBundle("se/trixon/toolbox/branding/about");
                JFrame mainFrame = (JFrame) WindowManager.getDefault().getMainWindow();
                mainFrame.setTitle(bundle.getString("application.title"));
            }
        });
    }
}
