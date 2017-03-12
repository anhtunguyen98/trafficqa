package lemurproject.indri.ui;

import java.util.Vector;

public class OffsetAnnotationTableModel extends javax.swing.table.AbstractTableModel {

    private static String[] offsetColumnNames = {"Data File", "Offset Annotation File"};
    private Vector fileNames;
    private Vector offsetFileNames;

    public OffsetAnnotationTableModel() {
        this.fileNames = new Vector();
        this.offsetFileNames = new Vector();
    }

    public String getColumnName(int paramInt) {
        return offsetColumnNames[paramInt];
    }

    public boolean containsFilename(String paramString) {
        return this.fileNames.contains(paramString);
    }

    public void setFileNames(Vector paramVector) {
        this.fileNames.clear();
        this.offsetFileNames.clear();
        this.fileNames.addAll(paramVector);
        for (int i = 0; i < this.fileNames.size(); i++) {
            this.offsetFileNames.add("");
        }
        fireTableDataChanged();
    }

    public void addFilename(String paramString) {
        if (!this.fileNames.contains(paramString)) {
            this.fileNames.add(paramString);
            this.offsetFileNames.add("");
            fireTableDataChanged();
        }
    }

    public void removeFilename(String paramString) {
        int i = this.fileNames.indexOf(paramString);
        if (i > -1) {
            this.fileNames.remove(i);
            this.offsetFileNames.remove(i);
        }
    }

    public int getColumnCount() {
        return 2;
    }

    public int getRowCount() {
        return this.fileNames.size();
    }

    public boolean isCellEditable(int paramInt1, int paramInt2) {
        if ((paramInt1 < 0) || (paramInt1 > this.fileNames.size() - 1)) {
            return false;
        }
        if (paramInt2 != 1) {
            return false;
        }
        return true;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        if ((paramInt1 < 0) || (paramInt1 > this.fileNames.size() - 1)) {
            return;
        }
        if (paramInt2 != 1) {
            return;
        }
        this.offsetFileNames.setElementAt(paramObject.toString(), paramInt1);
    }

    public java.util.HashMap getAllValues() {
        java.util.HashMap localHashMap = new java.util.HashMap();
        int i = this.fileNames.size();
        for (int j = 0; j < i; j++) {
            String str1 = (String) this.fileNames.get(j);
            String str2 = (String) this.offsetFileNames.get(j);
            localHashMap.put(str1.trim(), str2.trim());
        }
        return localHashMap;
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        if ((paramInt1 < 0) || (paramInt1 > this.fileNames.size() - 1)) {
            return null;
        }
        if ((paramInt2 < 0) || (paramInt2 > 1)) {
            return null;
        }
        if (paramInt2 == 0) {
            return this.fileNames.get(paramInt1);
        }
        return this.offsetFileNames.get(paramInt1);
    }
}
