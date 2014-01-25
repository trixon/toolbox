package se.trixon.toolbox.core.dialog;

import javax.swing.table.DefaultTableModel;
import se.trixon.almond.dictionary.Dict;
import se.trixon.toolbox.core.ToolProvider;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class ToolsListTableModel extends DefaultTableModel {

    public static final int COLUMN_CATEGORY = 3;
    public static final int COLUMN_COPYRIGHT = 4;
    public static final int COLUMN_DESCRIPTION = 1;
    public static final int COLUMN_LICENSE = 5;
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_VERSION = 2;

    public ToolsListTableModel() {
        columnIdentifiers.add(Dict.NAME.getString());
        columnIdentifiers.add(Dict.DESCRIPTION.getString());
        columnIdentifiers.add(Dict.VERSION.getString());
        columnIdentifiers.add(Dict.CATEGORY.getString());
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
                return toolProvider.getToolName();
            case COLUMN_VERSION:
                return toolProvider.getToolVersion();
            case COLUMN_COPYRIGHT:
                return toolProvider.getToolCopyright();
            case COLUMN_CATEGORY:
                return toolProvider.getToolCategory();
            case COLUMN_LICENSE:
                return toolProvider.getToolLicense();
            case COLUMN_DESCRIPTION:
                return toolProvider.getToolDescription();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
