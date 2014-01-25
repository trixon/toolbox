package se.trixon.toolbox.core;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.openide.awt.HtmlBrowser;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProvider(service = HtmlBrowser.URLDisplayer.class, position = 0)
public class ExternalUrlDisplayer extends HtmlBrowser.URLDisplayer {

    @Override
    public void showURL(URL u) {
        try {
            Desktop.getDesktop().browse(u.toURI());
        } catch (URISyntaxException | IOException exception) {
        }
    }

}
