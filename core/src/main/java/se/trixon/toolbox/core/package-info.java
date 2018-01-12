/* 
 * Copyright 2018 Patrik Karlsson.
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
@OptionsPanelController.ContainerRegistration(id = "Tools",
        position = 0,
        categoryName = "se.trixon.toolbox.core.Bundle#tools",
        iconBase = "se/trixon/toolbox/core/res/toolbox32.png",
        keywords = "se.trixon.toolbox.core.Bundle#OptionsCategory_Keywords_Tools",
        keywordsCategory = "Tools")
package se.trixon.toolbox.core;

import org.netbeans.spi.options.OptionsPanelController;
