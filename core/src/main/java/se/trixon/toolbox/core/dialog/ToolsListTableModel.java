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
package se.trixon.toolbox.core.dialog;

import javax.swing.table.DefaultTableModel;
import org.openide.util.NbBundle;
import se.trixon.almond.util.Dict;
import se.trixon.toolbox.core.ToolProvider;
import se.trixon.toolbox.core.Toolbox;

/**
 *
 * @author Patrik Karlsson
 */
public class ToolsListTableModel extends DefaultTableModel {

    public static final int COLUMN_MODULE = 3;
    public static final int COLUMN_COPYRIGHT = 4;
    public static final int COLUMN_DESCRIPTION = 1;
    public static final int COLUMN_LICENSE = 5;
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_VERSION = 2;

    public ToolsListTableModel() {
        columnIdentifiers.add(Dict.NAME.getString());
        columnIdentifiers.add(Dict.DESCRIPTION.getString());
        columnIdentifiers.add(Dict.VERSION.getString());
        columnIdentifiers.add(NbBundle.getMessage(Toolbox.class, "toolModule"));
        columnIdentifiers.add(Dict.COPYRIGHT.getString());
        columnIdentifiers.add(Dict.LICENSE.getString());
    }

    public void addRow(ToolProvider toolProvider) {
        dataVector.add(toolProvider);
        fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }

    @Override
    public Object getValueAt(int row, int column) {
        ToolProvider toolProvider = (ToolProvider) dataVector.get(row);
        switch (column) {
            case COLUMN_NAME:
                return toolProvider.getName();
            case COLUMN_VERSION:
                return toolProvider.getVersion();
            case COLUMN_COPYRIGHT:
                return toolProvider.getCopyright();
            case COLUMN_MODULE:
                return toolProvider.getModuleName();
            case COLUMN_LICENSE:
                return toolProvider.getLicense();
            case COLUMN_DESCRIPTION:
                return toolProvider.getDescription();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
