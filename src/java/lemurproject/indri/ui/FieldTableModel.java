package lemurproject.indri.ui;

import java.util.Vector;

public class FieldTableModel extends javax.swing.table.AbstractTableModel {

    private static String[] fieldColumnNames = {"Field Name", "Is Numeric?"};

    Vector fieldNames;

    Vector isNumeric;
    javax.swing.table.TableColumnModel columnHeader = null;

    public FieldTableModel() {
        this.fieldNames = new Vector();
        this.isNumeric = new Vector();
        this.columnHeader = new javax.swing.table.DefaultTableColumnModel();
    }

    public String getColumnName(int paramInt) {
        return fieldColumnNames[paramInt];
    }

    public int getColumnCount() {
        return 2;
    }

    public int getRowCount() {
        return this.fieldNames.size();
    }

    public void addNewField() {
        this.fieldNames.add("");
        this.isNumeric.add(new Boolean(false));
        fireTableDataChanged();
    }

    public void removeField(int paramInt) {
        if ((paramInt < 0) || (paramInt > this.fieldNames.size() - 1)) {
            return;
        }
        this.fieldNames.remove(paramInt);
        this.isNumeric.remove(paramInt);
        fireTableDataChanged();
    }

    public boolean isCellEditable(int paramInt1, int paramInt2) {
        return true;
    }

    public Class getColumnClass(int paramInt) {
        if (paramInt == 1) {
            return Boolean.class;
        }
        return String.class;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        if ((paramInt1 < 0) || (paramInt1 > this.fieldNames.size() - 1)) {
            return;
        }
        if (paramInt2 == 0) {
            this.fieldNames.setElementAt(paramObject.toString(), paramInt1);
        } else {
            this.isNumeric.setElementAt(new Boolean(((Boolean) paramObject).booleanValue()), paramInt1);
        }
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        if ((paramInt1 < 0) || (paramInt1 > this.fieldNames.size() - 1)) {
            return null;
        }
        if ((paramInt2 < 0) || (paramInt2 > 1)) {
            return null;
        }
        if (paramInt2 == 0) {
            return this.fieldNames.get(paramInt1);
        }
        return this.isNumeric.get(paramInt1);
    }
}
