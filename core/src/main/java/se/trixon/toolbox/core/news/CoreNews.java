/* 
 * Copyright 2016 Patrik Karlsson.
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
package se.trixon.toolbox.core.news;

import se.trixon.almond.news.NewsProvider;
import java.util.ResourceBundle;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.almond.SystemHelper;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
@ServiceProvider(service = NewsProvider.class)
public class CoreNews implements NewsProvider {

    @Override
    public String getName() {
        return "Toolbox";
    }

    @Override
    public ResourceBundle getNewsBundle() {
        return ResourceBundle.getBundle(SystemHelper.getPackageAsPath(this.getClass()) + "CoreNews");
    }
}
