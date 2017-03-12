package lemurproject.indri.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class OffsetAnnotationFileCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {

    private JPanel pnlEditor;
    private JTextField txtValue;
    private JButton btnBrowse;

    public OffsetAnnotationFileCellEditor() {
        this.pnlEditor = new JPanel();
        this.pnlEditor.setLayout(new java.awt.BorderLayout());
        this.txtValue = new JTextField();
        this.pnlEditor.add(this.txtValue, "Center");
        this.btnBrowse = new JButton("...");
        this.btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                OffsetAnnotationFileCellEditor.this.browseForFile();
            }
        });
        this.pnlEditor.add(this.btnBrowse, "East");
    }

    protected void browseForFile() {
        JFileChooser localJFileChooser = new JFileChooser();
        localJFileChooser.setFileSelectionMode(2);
        localJFileChooser.setMultiSelectionEnabled(false);
        int i = localJFileChooser.showOpenDialog(this.pnlEditor);
        if (i == 0) {
            File localFile1 = localJFileChooser.getSelectedFile();
            String str = localFile1.getAbsolutePath();

            if (!localFile1.exists()) {
                File localFile2 = localFile1.getParentFile();
                if (localFile2 != null) {
                    str = localFile2.getAbsolutePath();
                }
            }
            this.txtValue.setText(str);
        }
    }

    public Component getTableCellEditorComponent(JTable paramJTable, Object paramObject, boolean paramBoolean, int paramInt1, int paramInt2) {
        this.txtValue.setText((String) paramObject);
        return this.pnlEditor;
    }

    public Object getCellEditorValue() {
        return this.txtValue.getText();
    }
}
