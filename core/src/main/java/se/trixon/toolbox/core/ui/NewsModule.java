/*
 * Copyright 2019 Patrik Karlström.
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
package se.trixon.toolbox.core.ui;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import javafx.scene.Node;
import javafx.scene.web.WebView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import se.trixon.almond.nbp.core.news.NewsBuilder;
import se.trixon.almond.nbp.core.news.NewsProvider;
import se.trixon.almond.util.Dict;
import se.trixon.almond.util.icons.material.MaterialIcon;
import static se.trixon.toolbox.api.TbToolbox.*;

public class NewsModule extends WorkbenchModule {

    private final StringBuilder mCssBuilder;
    private final WebView mWebView = new WebView();

    public NewsModule() {
        super(Dict.NEWS.toString(), MaterialIcon._Action.RECEIPT.getImageView(MODULE_ICON_SIZE).getImage());
        mCssBuilder = new StringBuilder("<html>");
        mCssBuilder.append("<head><style>");
        mCssBuilder.append("h1 { font-size: x-large; color: #D40000; margin-bottom: 0px; }");
        mCssBuilder.append("h2 { font-size: large; margin-bottom: 0px; }");
        mCssBuilder.append("body {margin-left: 16px; font-size: medium; background-color: #F5F5F5; }");
        mCssBuilder.append("p {margin-bottom: 4px;margin-top: 4px;}");
        mCssBuilder.append("ul { margin-left: 16px; }");
        mCssBuilder.append("li { }");
        mCssBuilder.append("</style></head>");

        Lookup.Result<NewsProvider> newsResult = Lookup.getDefault().lookupResult(NewsProvider.class);
        newsResult.addLookupListener((LookupEvent ev) -> {
            updateNews();
        });

        updateNews();
    }

    @Override
    public Node activate() {
        return mWebView;
    }

    private void updateNews() {
        StringBuilder builder = new StringBuilder(mCssBuilder);
        builder.append("<h1>").append(Dict.NEWS.toString()).append("</h1>");
        builder.append(new NewsBuilder().getNews());

        mWebView.getEngine().loadContent(builder.toString(), "text/html");
    }

}
