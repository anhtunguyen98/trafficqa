package lemurproject.indri.ui;

import javax.swing.table.AbstractTableModel;

class DocsTableModel
        extends AbstractTableModel {

    boolean showScores = false;

    private String[] noScoreColumnNames = {"Document", "Title"};

    private String[] scoreColumnNames = {"Score", "Document", "Title"};

    private String[] columnNames = this.noScoreColumnNames;

    private Object[][] noScoreData = new Object[0][0];

    private Object[][] scoreData = new Object[0][0];

    private Object[][] data = this.noScoreData;

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public int getRowCount() {
        return this.data.length;
    }

    public String getColumnName(int paramInt) {
        return this.columnNames[paramInt];
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        return this.data[paramInt1][paramInt2];
    }

    public Class getColumnClass(int paramInt) {
        Object localObject = getValueAt(0, paramInt);
        if (localObject != null) {
            return localObject.getClass();
        }
        String str = "";
        return str.getClass();
    }

    public void resize(int paramInt) {
        this.scoreData = new Object[paramInt][this.scoreColumnNames.length];
        this.noScoreData = new Object[paramInt][this.noScoreColumnNames.length];
        if (this.showScores) {
            this.data = this.scoreData;
            this.columnNames = this.scoreColumnNames;
        } else {
            this.data = this.noScoreData;
            this.columnNames = this.noScoreColumnNames;
        }
        fireTableRowsInserted(0, paramInt);
    }

    public void displayScores(boolean paramBoolean) {
        this.showScores = paramBoolean;
        if (this.showScores) {
            this.data = this.scoreData;
            this.columnNames = this.scoreColumnNames;
        } else {
            this.data = this.noScoreData;
            this.columnNames = this.noScoreColumnNames;
        }
        fireTableStructureChanged();
    }

    public void clear() {
        int i = this.data.length - 1;
        if (i >= 0) {
            this.scoreData = new Object[0][this.scoreColumnNames.length];
            this.noScoreData = new Object[0][this.noScoreColumnNames.length];
            if (this.showScores) {
                this.data = this.scoreData;
                this.columnNames = this.scoreColumnNames;
            } else {
                this.data = this.noScoreData;
                this.columnNames = this.noScoreColumnNames;
            }
            fireTableRowsDeleted(0, i);
        }
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        this.data[paramInt1][paramInt2] = paramObject;
        fireTableCellUpdated(paramInt1, paramInt2);
    }

    public void setValueAt(int paramInt, String paramString1, String paramString2) {
        this.data[paramInt][0] = paramString1;
        this.data[paramInt][1] = paramString2;
        fireTableRowsUpdated(paramInt, paramInt);
    }

    public void setValueAt(int paramInt, double paramDouble, String paramString1, String paramString2) {
        this.scoreData[paramInt][0] = new Double(paramDouble);
        this.scoreData[paramInt][1] = paramString1;
        this.scoreData[paramInt][2] = paramString2;

        this.noScoreData[paramInt][0] = paramString1;
        this.noScoreData[paramInt][1] = paramString2;

        fireTableRowsUpdated(paramInt, paramInt);
    }
}
