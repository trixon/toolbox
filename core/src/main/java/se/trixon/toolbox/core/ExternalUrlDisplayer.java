/* 
 * Copyright 2017 Patrik Karlsson.
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
package se.trixon.toolbox.core;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.openide.awt.HtmlBrowser;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Patrik Karlsson
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
