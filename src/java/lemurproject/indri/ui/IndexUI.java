package lemurproject.indri.ui;

import java.io.File;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lemurproject.indri.IndexEnvironment;
import lemurproject.indri.Specification;

public class IndexUI extends JPanel implements java.awt.event.ActionListener,
        java.awt.event.ItemListener, javax.swing.event.CaretListener,
        javax.swing.event.TableModelListener {

    private JLabel status;
    private JTextArea messages;
    private javax.swing.JTabbedPane tabbedPane;
    private JCheckBox doRecurse;
    private JCheckBox doStem;
    private JComboBox stemmers;
    private JComboBox memoryLim;
    private JComboBox docFormat;
    private JButton browse;
    private JButton cfbrowse;
    private JButton stopBrowse;
    private JButton cfRemove;
    private JButton go;
    private JButton stop;
    private JTextField iname;
    private JTextField stopwordlist;
    private JTextField colFields;
    private JTextField filterString;
    private javax.swing.JList collectionFiles;
    private javax.swing.DefaultListModel cfModel;
    private final JFileChooser fc = new JFileChooser(
            System.getProperties().getProperty("user.dir"));

    private String stopwords;

    private static final String helpFile = "properties/IndriIndex.html";

    private static final String iconFile = "properties/lemur_head_32.gif";

    private static final String logoFile = null;

    private static final String[] formats = {"trecweb", "trectext", "html", "doc", "ppt", "pdf", "txt"};

    private static final String[] lims = {"  64 MB", "  96 MB", " 128 MB",
        " 256 MB", " 512 MB", " 768 MB", "1024 MB"};

    private static final String[] sTypes = {"krovetz", "porter"};

    boolean appendIndex = false;

    private javax.swing.JMenuBar menuBar;

    private javax.swing.JMenu fileMenu;

    private javax.swing.JMenu helpMenu;

    private JMenuItem fOpen;

    private JMenuItem fSave;
    private JMenuItem fPrefs;
    private JMenuItem fQuit;
    private JMenuItem hHelp;
    private JMenuItem hAbout;
    private javax.swing.ImageIcon indriIcon;
    private static final String aboutText = "Indri Indexer UI 1.0\nCopyright (c)"
            + " 2004 University of Massachusetts";
    private JFrame helpFrame;
    private JPanel indexFieldPanel;
    private JTable fieldTable;
    private FieldTableModel fieldTableModel;
    private JButton btnAddField;
    private JButton btnRemoveField;
    private JTable offsetAnnotationFileTable;
    private OffsetAnnotationTableModel offsetAnnotationFilesTableModel;
    private JTextField txtPathToHarvestLinks;
    private JButton btnHarvestLinks;
    private static String[] fieldColumnTooltips = {"The field name to index",
        "Is the field numeric?"};
    private static String[] annotationsColumnTooltips = {"The datafile for the annotations",
        "The path to the annotation file(s)"};

    private Vector dataFilesOffsetFiles = null;

    public IndexUI() {
        super(new java.awt.BorderLayout());
        initGUI();
    }

    private void initGUI() {
        this.indriIcon = createImageIcon("properties/lemur_head_32.gif");
        javax.swing.Icon localIcon = null;

        makeHelp();
        this.tabbedPane = new javax.swing.JTabbedPane();

        JPanel localJPanel1 = new JPanel();
        java.awt.GridBagLayout localGridBagLayout = new java.awt.GridBagLayout();
        java.awt.GridBagConstraints localGridBagConstraints1 = new java.awt.GridBagConstraints();
        localGridBagConstraints1.anchor = 22;
        localJPanel1.setLayout(localGridBagLayout);

        this.browse = new JButton("Browse...");
        this.browse.addActionListener(this);
        this.browse.setToolTipText("Browse to a directory and enter a basename for the index");

        this.iname = new JTextField("", 25);
        this.iname.setToolTipText("Enter a basename for the index or browse to a directory");

        this.iname.addCaretListener(this);
        JLabel localJLabel1 = new JLabel("Index Name: ", 11);
        localJLabel1.setLabelFor(this.iname);
        localGridBagConstraints1.insets = new java.awt.Insets(10, 10, 0, 0);
        localGridBagConstraints1.gridx = 0;
        localGridBagConstraints1.gridy = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.iname, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 2;
        localJPanel1.add(this.browse, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        this.cfModel = new javax.swing.DefaultListModel();
        this.collectionFiles = new javax.swing.JList(this.cfModel);
        this.collectionFiles.setSelectionMode(2);
        this.collectionFiles.setVisibleRowCount(5);
        this.collectionFiles.setToolTipText("Browse to a directory and select input files or directories.");

        JScrollPane localJScrollPane1 = new JScrollPane(this.collectionFiles);
        localJScrollPane1.setPreferredSize(new java.awt.Dimension(400, 100));

        this.cfbrowse = new JButton("Browse...");
        this.cfbrowse.addActionListener(this);
        this.cfbrowse.setToolTipText("Browse to a directory and select input files or directories.");

        this.cfRemove = new JButton("Remove");
        this.cfRemove.addActionListener(this);
        this.cfRemove.setToolTipText("Remove selected files from the list.");

        localGridBagConstraints1.gridy = 1;
        localJLabel1 = new JLabel("Data File(s): ", 11);
        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 1;
        localJPanel1.add(localJScrollPane1, localGridBagConstraints1);

        this.doRecurse = new JCheckBox("Recurse into subdirectories");
        this.doRecurse.setToolTipText("<html>When checked and a directory is"
                + " in the<br>data files list, recursively add all<br>data files"
                + " in that directory and all of<br>its subdirectories into the data<br>files list.</html>");

        JPanel localJPanel2 = new JPanel(new java.awt.BorderLayout());
        localJPanel2.add(this.cfbrowse, "North");

        localJPanel2.add(this.cfRemove, "South");
        localGridBagConstraints1.gridx = 2;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(localJPanel2, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        localGridBagConstraints1.gridy = 2;
        localJLabel1 = new JLabel("Filename filter: ", 11);
        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        this.filterString = new JTextField("", 25);
        localJLabel1.setLabelFor(this.filterString);
        this.filterString.setToolTipText("Specify a filename filter, eg *.sgml");
        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.filterString, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;
        localGridBagConstraints1.gridx = 2;
        localJPanel1.add(this.doRecurse, localGridBagConstraints1);

        localGridBagConstraints1.gridy = 3;

        this.colFields = new JTextField("docno", 25);
        this.colFields.setToolTipText("<html>Comma delimited list of field names,"
                + "<br>without spaces to index as metadata.</html>");

        localJLabel1 = new JLabel("Collection Fields: ", 11);
        localJLabel1.setLabelFor(this.colFields);
        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.colFields, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        localGridBagConstraints1.gridy = 4;

        localGridBagConstraints1.anchor = 22;

        localGridBagConstraints1.gridy = 5;
        this.docFormat = new JComboBox(formats);
        this.docFormat.setToolTipText("Select format of input files");
        this.docFormat.addActionListener(this);
        localJLabel1 = new JLabel("Document format: ", 11);
        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.docFormat, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        localGridBagConstraints1.gridy = 6;

        this.stopwordlist = new JTextField(this.stopwords, 25);
        this.stopwordlist.setToolTipText("<html>Enter a stopword list or browse"
                + " to<br>select. Clear this field if you do<br>not want to stop this index.</html>");

        this.stopBrowse = new JButton("Browse...");
        this.stopBrowse.addActionListener(this);
        this.stopBrowse.setToolTipText("Browse to a directory and select a stoplist.");

        localJLabel1 = new JLabel("Stopword list: ", 11);
        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);

        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.stopwordlist, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 2;
        localJPanel1.add(this.stopBrowse, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        localGridBagConstraints1.gridy = 7;

        this.doStem = new JCheckBox("Stem collection", true);
        this.doStem.addItemListener(this);
        this.doStem.setToolTipText("<html>Select to enable stemming"
                + " (conflation<br>of morphological variants) for this index</html>");

        this.stemmers = new JComboBox(sTypes);
        this.stemmers.setToolTipText("Select stemming algorithm.");
        this.stemmers.addActionListener(this);

        localGridBagConstraints1.gridx = 0;
        localJPanel1.add(this.doStem, localGridBagConstraints1);

        localGridBagConstraints1.gridx = 1;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(this.stemmers, localGridBagConstraints1);
        localGridBagConstraints1.anchor = 22;

        this.memoryLim = new JComboBox(lims);
        this.memoryLim.setToolTipText("<html>How much memory to use while "
                + "indexing.<br>A rule of thumb is no more than 3/4 of<br>your physical memory</html>");

        this.memoryLim.setSelectedIndex(4);
        localJLabel1 = new JLabel("Memory limit: ", 11);
        localGridBagConstraints1.gridx = 2;
        localGridBagConstraints1.anchor = 21;
        localJPanel1.add(localJLabel1, localGridBagConstraints1);
        localGridBagConstraints1.gridx = 3;
        localGridBagConstraints1.anchor = 22;
        localJPanel1.add(this.memoryLim, localGridBagConstraints1);

        this.tabbedPane.addTab("Index", localIcon, localJPanel1, "Index Options");

        this.indexFieldPanel = makePanel();
        this.indexFieldPanel.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints localGridBagConstraints2 = new java.awt.GridBagConstraints();

        JPanel localJPanel3 = makePanel();

        localJPanel3.setLayout(new java.awt.BorderLayout());
        this.fieldTableModel = new FieldTableModel();
        this.fieldTable = new JTable(this.fieldTableModel) {
            protected javax.swing.table.JTableHeader createDefaultTableHeader() {
                return new javax.swing.table.JTableHeader(this.columnModel) {
                    public String getToolTipText(java.awt.event.MouseEvent paramAnonymous2MouseEvent) {
                        Object localObject = null;
                        java.awt.Point localPoint = paramAnonymous2MouseEvent.getPoint();
                        int i = this.columnModel.getColumnIndexAtX(localPoint.x);
                        int j = this.columnModel.getColumn(i).getModelIndex();
                        return IndexUI.fieldColumnTooltips[j];
                    }

                };
            }
        };
        this.fieldTable.getModel().addTableModelListener(this);
        this.fieldTable.getColumnModel().setColumnSelectionAllowed(false);
        this.fieldTable.setPreferredScrollableViewportSize(
                new java.awt.Dimension(this.fieldTable.getPreferredScrollableViewportSize().width, 100));
        JScrollPane localJScrollPane2 = new JScrollPane(this.fieldTable);
        localJPanel3.add(localJScrollPane2, "Center");

        JPanel localJPanel4 = new JPanel();
        this.btnAddField = new JButton("Add Field");
        this.btnAddField.setToolTipText("Adds a new (blank) field for indexing");
        this.btnAddField.addActionListener(this);
        localJPanel4.add(this.btnAddField);
        this.btnRemoveField = new JButton("Remove Field");
        this.btnRemoveField.setToolTipText("Removes the currently selected field item");
        this.btnRemoveField.addActionListener(this);
        localJPanel4.add(this.btnRemoveField);
        localJPanel3.add(localJPanel4, "South");

        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.gridx = 0;
        localGridBagConstraints2.gridy = 0;
        this.indexFieldPanel.add(localJPanel3, localGridBagConstraints2);

        JPanel localJPanel5 = makePanel();
        localJPanel5.setLayout(new java.awt.BorderLayout());
        this.offsetAnnotationFilesTableModel = new OffsetAnnotationTableModel();
        this.offsetAnnotationFileTable = new JTable(this.offsetAnnotationFilesTableModel) {
            protected javax.swing.table.JTableHeader createDefaultTableHeader() {
                return new javax.swing.table.JTableHeader(this.columnModel) {
                    public String getToolTipText(java.awt.event.MouseEvent paramAnonymous2MouseEvent) {
                        Object localObject = null;
                        java.awt.Point localPoint = paramAnonymous2MouseEvent.getPoint();
                        int i = this.columnModel.getColumnIndexAtX(localPoint.x);
                        int j = this.columnModel.getColumn(i).getModelIndex();
                        return IndexUI.annotationsColumnTooltips[j];
                    }
                };
            }
        };
        this.offsetAnnotationFileTable.getModel().addTableModelListener(this);
        javax.swing.table.TableColumn localTableColumn = this.offsetAnnotationFileTable
                .getColumnModel().getColumn(1);
        localTableColumn.setCellEditor(new OffsetAnnotationFileCellEditor());
        this.offsetAnnotationFileTable.getColumnModel().setColumnSelectionAllowed(false);
        this.offsetAnnotationFileTable.setPreferredScrollableViewportSize(
                new java.awt.Dimension(
                        this.offsetAnnotationFileTable.getPreferredScrollableViewportSize().width, 100));
        int i = this.offsetAnnotationFileTable.getPreferredScrollableViewportSize().width / 2;
        for (int j = 0; j < this.offsetAnnotationFileTable.getColumnCount(); j++) {
            Object localObject = this.offsetAnnotationFileTable.getColumnModel().getColumn(j);
            ((javax.swing.table.TableColumn) localObject).setPreferredWidth(i);
            ((javax.swing.table.TableColumn) localObject).setWidth(i);
        }
        this.offsetAnnotationFileTable.setAutoResizeMode(0);

        JScrollPane localJScrollPane3 = new JScrollPane(this.offsetAnnotationFileTable);
        localJPanel5.add(localJScrollPane3, "Center");

        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.gridx = 0;
        localGridBagConstraints2.gridy = 1;

        this.indexFieldPanel.add(localJPanel5, localGridBagConstraints2);

        Object localObject = makePanel();
        ((JPanel) localObject).setLayout(new java.awt.BorderLayout());
        JLabel localJLabel2 = new JLabel("Path to Anchor Text:");
        ((JPanel) localObject).add(localJLabel2, "West");
        this.txtPathToHarvestLinks = new JTextField();
        this.txtPathToHarvestLinks.setToolTipText("<html><i>(optional)</i> Path"
                + " to the sorted output<br>from the HarvestLinks program to<br>include"
                + " anchor text links (trecweb only)<br><i>(leave blank for none)</i></html>");

        ((JPanel) localObject).add(this.txtPathToHarvestLinks, "Center");
        this.btnHarvestLinks = new JButton("Browse");
        this.btnHarvestLinks.addActionListener(this);
        ((JPanel) localObject).add(this.btnHarvestLinks, "East");
        localGridBagConstraints2.fill = 1;
        localGridBagConstraints2.gridx = 0;
        localGridBagConstraints2.gridy = 2;
        this.indexFieldPanel.add((java.awt.Component) localObject, localGridBagConstraints2);

        this.tabbedPane.addTab("Fields", this.indexFieldPanel);

        JPanel localJPanel6 = makePanel();
        this.messages = new JTextArea(10, 40);
        this.messages.setEditable(false);

        JScrollPane localJScrollPane4 = new JScrollPane(this.messages);
        localJPanel6.add(localJScrollPane4);
        this.tabbedPane.addTab("Status", localIcon, localJPanel6, "Status Messages");

        JPanel localJPanel7 = new JPanel();
        this.go = new JButton("Build Index");
        this.go.setEnabled(false);
        this.go.addActionListener(this);
        this.stop = new JButton("Quit");
        this.stop.addActionListener(this);
        localJPanel7.add(this.go);
        localJPanel7.add(this.stop);
        this.status = new JLabel("Ready...", this.indriIcon, 2);
        add(this.tabbedPane, "North");
        add(localJPanel7, "Center");
        add(this.status, "South");
    }

    private javax.swing.JMenuBar makeMenuBar() {
        this.menuBar = new javax.swing.JMenuBar();
        this.fileMenu = new javax.swing.JMenu("File");
        this.helpMenu = new javax.swing.JMenu("Help");
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(javax.swing.Box.createHorizontalGlue());
        this.menuBar.add(this.helpMenu);
        this.fQuit = makeMenuItem("Quit");
        this.fileMenu.add(this.fQuit);
        this.hHelp = makeMenuItem("Help");
        this.helpMenu.add(this.hHelp);
        this.hAbout = makeMenuItem("About");
        this.helpMenu.add(this.hAbout);
        return this.menuBar;
    }

    private JMenuItem makeMenuItem(String paramString) {
        JMenuItem localJMenuItem = new JMenuItem(paramString);
        localJMenuItem.addActionListener(this);
        return localJMenuItem;
    }

    private JPanel makePanel() {
        JPanel localJPanel = new JPanel(new java.awt.BorderLayout());
        return localJPanel;
    }

    protected static javax.swing.ImageIcon createImageIcon(String paramString) {
        java.net.URL localURL = IndexUI.class.getResource(paramString);
        if (localURL != null) {
            return new javax.swing.ImageIcon(localURL);
        }
        return null;
    }

    public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
        JOptionPane.showMessageDialog(null, "field table", "", JOptionPane.INFORMATION_MESSAGE);
        Object localObject1 = paramActionEvent.getSource();
        final Object localObject3;
        if (localObject1 == this.browse) {
            this.fc.setFileSelectionMode(2);
            int i = this.fc.showOpenDialog(this);
            if (i == 0) {
                localObject3 = this.fc.getSelectedFile();
                this.iname.setText(((File) localObject3).getAbsolutePath());
            }
            this.fc.setFileSelectionMode(0);
        } else {
            String str2;
            Object localObject6;
            if (localObject1 == this.cfbrowse) {
                this.fc.setFileSelectionMode(2);

                this.fc.setMultiSelectionEnabled(true);

                final String str1 = this.filterString.getText();
                Object localObject5;
                if (str1.length() > 0) {
                    localObject3 = encodeRegexp(str1);
                    localObject5 = new javax.swing.filechooser.FileFilter() {
                        public boolean accept(File paramAnonymousFile) {
                            if (paramAnonymousFile.isDirectory()) {
                                return true;
                            }
                            String str = paramAnonymousFile.getName();
                            return str.matches((String) localObject3);
                        }

                        public String getDescription() {
                            return str1 + " files";
                        }
                    };
                    this.fc.addChoosableFileFilter((javax.swing.filechooser.FileFilter) localObject5);
                }

                int m = this.fc.showOpenDialog(this);
                if (m == 0) {
                    File[] files = this.fc.getSelectedFiles();
                    for (int i2 = 0; i2 < files.length; i2++) {
                        File file = files[i2];
                        localObject6 = file.getAbsolutePath();

                        if (!file.exists()) {
                            localObject6 = file.getParentFile().getAbsolutePath();
                        }
                        this.cfModel.addElement(localObject6);

                        this.offsetAnnotationFilesTableModel.addFilename((String) localObject6);
                    }
                }

                this.fc.setMultiSelectionEnabled(false);
                this.fc.setFileSelectionMode(0);

                this.fc.resetChoosableFileFilters();
            } else {
                Object localObject4;
                if (localObject1 == this.stopBrowse) {
                    int j = this.fc.showOpenDialog(this);
                    if (j == 0) {
                        localObject4 = this.fc.getSelectedFile();
                        this.stopwordlist.setText(((File) localObject4).getAbsolutePath());
                        this.stopwords = this.stopwordlist.getText();
                    }
                } else if (localObject1 != this.stemmers) {
                    if (localObject1 == this.docFormat) {
                        if (this.docFormat.getSelectedItem().equals("trecweb")) {
                            this.txtPathToHarvestLinks.setEnabled(true);
                        } else {
                            this.txtPathToHarvestLinks.setEnabled(false);
                        }
                    } else {
                        Object localObject2;
                        if (localObject1 == this.go) {

                            if (!safeToBuildIndex()) {
                                this.status.setText("Unable to build " + this.iname.getText());
                                return;
                            }

                            this.tabbedPane.setSelectedIndex(2);

                            localObject2 = new Runnable() {
                                public void run() {
                                    IndexUI.this.buildIndex();
                                    IndexUI.this.ensureMessagesVisible();
                                }
                            };
                            localObject4 = new Thread((Runnable) localObject2);
                            ((Thread) localObject4).start();
                        } else if (localObject1 == this.stop) {
                            System.exit(0);
                        } else if (localObject1 == this.fQuit) {
                            System.exit(0);
                        } else if (localObject1 == this.cfRemove) {
                            Object[] objs = this.collectionFiles.getSelectedValues();
                            for (int n = 0; n < objs.length; n++) {
                                this.cfModel.removeElement(objs[n]);
                            }
                        } else if (localObject1 == this.hHelp) {
                            if (!this.helpFrame.isShowing()) {
                                this.helpFrame.setLocationRelativeTo(this.tabbedPane);
                                this.helpFrame.setVisible(true);
                                this.helpFrame.toFront();
                            }
                        } else if (localObject1 == this.hAbout) {
                            javax.swing.JOptionPane.showMessageDialog(this, "Indri Indexer UI"
                                    + " 1.0\nCopyright (c) 2004 University of Massachusetts", "About", 1,
                                    createImageIcon("properties/lemur_head_32.gif"));
                        } else if (localObject1 == this.btnAddField) {
                            this.fieldTableModel.addNewField();
                        } else if (localObject1 == this.btnRemoveField) {
                            int k = this.fieldTable.getSelectedRow();
                            if (k > -1) {
                                this.fieldTableModel.removeField(k);
                            } else if ((localObject1 == this.btnHarvestLinks)
                                    && (this.docFormat.getSelectedItem().equals("trecweb"))) {
                                JFileChooser localJFileChooser = new JFileChooser();
                                localJFileChooser.setFileSelectionMode(2);
                                localJFileChooser.setMultiSelectionEnabled(false);
                                int i1 = localJFileChooser.showOpenDialog(this);
                                if (i1 == 0) {
                                    File localFile = localJFileChooser.getSelectedFile();
                                    str2 = localFile.getAbsolutePath();

                                    if (!localFile.exists()) {
                                        localObject6 = localFile.getParentFile();
                                        if (localObject6 != null) {
                                            str2 = ((File) localObject6).getAbsolutePath();
                                        }
                                    }
                                    this.txtPathToHarvestLinks.setText(str2);
                                }
                            }
                        }
                    }
                }
            }
        }

        boolean k = (this.cfModel.getSize() > 0) && (this.iname.getText().length() > 0);
        this.go.setEnabled(k);
    }

    public void tableChanged(javax.swing.event.TableModelEvent paramTableModelEvent) {
        if (paramTableModelEvent.getSource() != this.offsetAnnotationFilesTableModel) {

            if (paramTableModelEvent.getSource() != this.fieldTableModel) {
            }
        }
    }

    public void itemStateChanged(java.awt.event.ItemEvent paramItemEvent) {
        java.awt.ItemSelectable localItemSelectable = paramItemEvent.getItemSelectable();
        boolean bool = paramItemEvent.getStateChange() == 1;
        if (localItemSelectable == this.doStem) {
            this.stemmers.setEnabled(bool);
        }
    }

    public void caretUpdate(javax.swing.event.CaretEvent paramCaretEvent) {
        boolean bool = (this.cfModel.getSize() > 0) && (this.iname.getText().length() > 0);
        this.go.setEnabled(bool);
    }

    private void makeHelp() {
        java.net.URL localURL = IndexUI.class.getResource("properties/IndriIndex.html");
        javax.swing.JTextPane localJTextPane = new javax.swing.JTextPane();

        this.helpFrame = new JFrame("Indri Index Builder Help");
        localJTextPane.setPreferredSize(new java.awt.Dimension(650, 400));
        localJTextPane.setEditable(false);
        localJTextPane.addHyperlinkListener(new DocLinkListener(this.indriIcon.getImage()));
        JScrollPane localJScrollPane = new JScrollPane(localJTextPane);
        try {
            localJTextPane.setPage(localURL);
        } catch (java.io.IOException localIOException) {
            localJTextPane.setText("Help file unavailable.");
        }

        this.helpFrame.getContentPane().add(localJScrollPane, "Center");
        this.helpFrame.setDefaultCloseOperation(1);
        this.helpFrame.setIconImage(this.indriIcon.getImage());
        this.helpFrame.pack();
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception localException) {
        }

        JFrame localJFrame = new JFrame("Indri Index Builder");
        localJFrame.setDefaultCloseOperation(3);
        localJFrame.setIconImage(createImageIcon("properties/lemur_head_32.gif").getImage());

        IndexUI localIndexUI = new IndexUI();
        localIndexUI.setOpaque(true);
        localJFrame.getContentPane().add(localIndexUI, "Center");
        localJFrame.setJMenuBar(localIndexUI.makeMenuBar());

        localJFrame.pack();
        localJFrame.setVisible(true);
    }

    private String encodeRegexp(String paramString) {
        String str = "^" + paramString + "$";
        str = str.replaceAll("\\.", "\\.");
        str = str.replaceAll("\\*", ".*");
        str = str.replaceAll("\\?", ".?");
        return str;
    }

    private boolean safeToBuildIndex() {
        this.appendIndex = false;

        File localFile1 = new File(this.iname.getText());
        String str = localFile1.getAbsolutePath();
        localFile1 = new File(str);

        File localFile2 = localFile1.getParentFile();
        if (!localFile2.exists()) {
            this.messages.append("Unable to build " + str + "Directory " + localFile2
                    .getAbsolutePath() + " does not exist.\n");

            return false;
        }

        File localFile3 = new File(str, "manifest");
        if (localFile3.exists()) {

            int i = javax.swing.JOptionPane.showConfirmDialog(this, localFile3
                    .getAbsolutePath() + " exists. Choose YES to overwrite, NO to"
                    + " append to this index, CANCEL to do nothing", "Overwrite or Append existing index", 1, 2);

            if (i == 2) {
                this.messages.append("Not building index " + str + "\n");
                return false;
            }
            if (i == 1) {
                this.messages.append("Appending new files to index " + str + "\n");

                this.appendIndex = true;
                return true;
            }
            if (i == 0) {
                this.messages.append("Overwriting index " + str + "\n");
                deleteDirectory(localFile1);
                return true;
            }
            this.messages.append("Not building index " + str + "\n");
            return false;
        }

        return true;
    }

    private void deleteDirectory(File paramFile) {
        File[] arrayOfFile = paramFile.listFiles();
        for (int i = 0; i < arrayOfFile.length; i++) {
            File localFile = arrayOfFile[i];
            if (localFile.isDirectory()) {
                deleteDirectory(localFile);
            }
            this.messages.append("Deleting: " + localFile.getAbsolutePath() + "\n");
            localFile.delete();
        }
    }

    private void ensureMessagesVisible() {
        int i = this.messages.getText().length();
        try {
            this.messages.scrollRectToVisible(this.messages.modelToView(i));
        } catch (javax.swing.text.BadLocationException localBadLocationException) {
        }
    }

    private void buildIndex() {
        java.awt.Cursor localCursor1 = java.awt.Cursor.getPredefinedCursor(3);
        java.awt.Cursor localCursor2 = java.awt.Cursor.getPredefinedCursor(0);
        setCursor(localCursor1);
        this.messages.setCursor(localCursor1);

        int i = 0;

        File localFile = new File(this.iname.getText());
        this.iname.setText(localFile.getAbsolutePath());
        this.messages.append("Building " + this.iname.getText() + "...\n");
        this.status.setText("Building " + this.iname.getText() + "...");
        Thread localThread = blinker(this.status.getText(), "Finished building " + this.iname.getText());

        IndexEnvironment localIndexEnvironment = new IndexEnvironment();
        UIIndexStatus localUIIndexStatus = new UIIndexStatus();

        try {
            localIndexEnvironment.setMemory(encodeMem());

            Vector localVector1 = new Vector();
            Vector localVector2 = new Vector();
            int j = this.fieldTable.getModel().getRowCount();
            for (int k = 0; k < j; k++) {
                String str1 = ((String) this.fieldTable.getModel().getValueAt(k, 0)).trim();
                System.out.println(str1);
                Object localObject1 = (Boolean) this.fieldTable.getModel().getValueAt(k, 1);
                if ((str1.length() > 0) && (!localVector1.contains(str1))) {
                    localVector1.add(str1);
                    if (((Boolean) localObject1).booleanValue()) {
                        localVector2.add(str1);
                    }
                }
            }

            String[] arrayOfString1 = new String[localVector1.size()];
            for (int m = 0; m < localVector1.size(); m++) {
                arrayOfString1[m] = ((String) localVector1.get(m));
            }
            localIndexEnvironment.setIndexedFields(arrayOfString1);

            for (int m = 0; m < localVector2.size(); m++) {
                Object localObject1 = (String) localVector2.get(m);
                localIndexEnvironment.setNumericField((String) localObject1, true, "NumericFieldAnnotator");
            }

            String[] arrayOfString2 = this.colFields.getText().split(",");
            Object localObject1 = new String[0];

            localIndexEnvironment.setMetadataIndexedFields(arrayOfString2, arrayOfString2);
            String str2 = this.stopwordlist.getText();
            if (!str2.equals("")) {
                Object localObject2 = new Vector();
                try {
                    java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(
                            new java.io.FileReader(str2));
                    Object localObject3 = null;
                    while ((localObject3 = localBufferedReader.readLine()) != null) {
                        ((Vector) localObject2).add(((String) localObject3).trim());
                    }
                    localBufferedReader.close();
                } catch (java.io.IOException localIOException) {
                    showException(localIOException);
                }
                localObject1 = (String[]) ((Vector) localObject2).toArray((Object[]) localObject1);
                localIndexEnvironment.setStopwords((String[]) localObject1);
            }

            if (this.doStem.isSelected()) {
                Object localObject2 = (String) this.stemmers.getSelectedItem();
                System.out.println("doStem: " + localObject2);
                localIndexEnvironment.setStemmer((String) localObject2);
            }

            Object localObject2 = (String) this.docFormat.getSelectedItem();

            Specification localSpecification = localIndexEnvironment.getFileClassSpec((String) localObject2);
            Object localObject3 = new Vector();
            Vector localVector3 = null;
            if (localSpecification.include.length > 0) {
                localVector3 = new Vector();
            }

            System.out.println("index");
            for (int n = 0; n < localSpecification.index.length; n++) {
                ((Vector) localObject3).add(localSpecification.index[n]);
                System.out.println(localSpecification.index[n]);
            }
            for (int n = 0; n < arrayOfString1.length; n++) {
                if (((Vector) localObject3).indexOf(arrayOfString1[n]) == -1) {
                    ((Vector) localObject3).add(arrayOfString1[n]);
                }
                if ((localVector3 != null) && (localVector3.indexOf(arrayOfString1[n]) == -1)) {
                    localVector3.add(arrayOfString1[n]);
                }
            }
            if (((Vector) localObject3).size() > localSpecification.index.length) {
                localSpecification.index = new String[((Vector) localObject3).size()];
                ((Vector) localObject3).copyInto(localSpecification.index);
            }

            ((Vector) localObject3).clear();
            for (int n = 0; n < localSpecification.metadata.length; n++) {
                ((Vector) localObject3).add(localSpecification.metadata[n]);
            }
            for (int n = 0; n < arrayOfString2.length; n++) {
                if (((Vector) localObject3).indexOf(arrayOfString2[n]) == -1) {
                    ((Vector) localObject3).add(arrayOfString2[n]);
                }
                if ((localVector3 != null) && (localVector3.indexOf(arrayOfString2[n]) == -1)) {
                    localVector3.add(arrayOfString2[n]);
                }
            }
            if (((Vector) localObject3).size() > localSpecification.metadata.length) {
                localSpecification.metadata = new String[((Vector) localObject3).size()];
                ((Vector) localObject3).copyInto(localSpecification.metadata);
            }

            if ((localVector3 != null) && (localVector3.size() > localSpecification.include.length)) {
                localSpecification.include = new String[localVector3.size()];
                localVector3.copyInto(localSpecification.include);
            }

            localIndexEnvironment.addFileClass(localSpecification);

            if (((String) localObject2).equals("trecweb")) {
                Object localObject4 = this.txtPathToHarvestLinks.getText().trim();
                if (((String) localObject4).length() > 0) {
                    localIndexEnvironment.setAnchorTextPath((String) localObject4);
                }
            }

            String[] localObject4 = formatDataFiles();
            String[] arrayOfString3 = new String[0];
            String[] arrayOfString4 = (String[]) this.dataFilesOffsetFiles.toArray(arrayOfString3);

            if (this.appendIndex) {
                localIndexEnvironment.open(this.iname.getText(), localUIIndexStatus);
            } else {
                localIndexEnvironment.create(this.iname.getText(), localUIIndexStatus);
            }

            this.fQuit.setEnabled(false);
            this.stop.setEnabled(false);

            for (int i1 = 0; i1 < localObject4.length; i1++) {
                String str3 = localObject4[i1];

                if ((arrayOfString4.length > i1) && (arrayOfString4[i1].length() > 0)) {
                    localIndexEnvironment.setOffsetAnnotationsPath(arrayOfString4[i1]);
                }

                localIndexEnvironment.addFile(str3, (String) localObject2);
                i = localIndexEnvironment.documentsIndexed();
                ensureMessagesVisible();
            }
            localIndexEnvironment.close();
        } catch (Exception localException) {
            this.messages.append("ERROR building " + this.iname.getText() + "\n" + localException + "\n");
        }

        this.fQuit.setEnabled(true);
        this.stop.setEnabled(true);
        this.blinking = false;
        localThread.interrupt();
        setCursor(localCursor2);
        this.messages.setCursor(localCursor2);
        this.status.setText("Finished building " + this.iname.getText());
        this.messages.append("Finished building " + this.iname.getText() + "\n");
        this.messages.append("Total documents indexed: " + i + "\n\n");
        ensureMessagesVisible();
    }

    private String[] formatDataFiles() {
        Vector localVector = new Vector();
        String[] arrayOfString = new String[0];

        this.dataFilesOffsetFiles = new Vector();

        java.io.FileFilter local5 = null;
        String str1 = this.filterString.getText();
        if (str1.length() > 0) {
            final Object localObject = encodeRegexp(str1);
            local5 = new java.io.FileFilter() {
                public boolean accept(File paramAnonymousFile) {
                    String str = paramAnonymousFile.getName();

                    return (paramAnonymousFile.isDirectory()) || (str.matches((String) localObject));
                }
            };
        }
        final Object localObject = this.cfModel.elements();
        java.util.HashMap localHashMap = this.offsetAnnotationFilesTableModel.getAllValues();

        while (((java.util.Enumeration) localObject).hasMoreElements()) {
            String str2 = (String) ((java.util.Enumeration) localObject).nextElement();
            File localFile = new File(str2);

            String str3 = "";
            if (localHashMap.containsKey(str2)) {
                str3 = (String) localHashMap.get(str2);
            }
            formatDataFiles(localFile, local5, localVector, str3);
        }
        arrayOfString = (String[]) localVector.toArray(arrayOfString);
        return arrayOfString;
    }

    private void formatDataFiles(File paramFile, java.io.FileFilter paramFileFilter, Vector paramVector,
            String paramString) {
        if (paramFile.isDirectory()) {
            File[] arrayOfFile = paramFile.listFiles(paramFileFilter);
            for (int i = 0; i < arrayOfFile.length; i++) {
                if (arrayOfFile[i].isDirectory()) {
                    if (this.doRecurse.isSelected()) {
                        formatDataFiles(arrayOfFile[i], paramFileFilter, paramVector, paramString);
                    }
                } else {
                    paramVector.add(arrayOfFile[i].getAbsolutePath());
                    if (this.dataFilesOffsetFiles != null) {
                        this.dataFilesOffsetFiles.add(paramString);
                    }
                }
            }
        } else {
            paramVector.add(paramFile.getAbsolutePath());
            if (this.dataFilesOffsetFiles != null) {
                this.dataFilesOffsetFiles.add(paramString);
            }
        }
    }

    private long encodeMem() {
        String str = ((String) this.memoryLim.getSelectedItem()).trim();
        int i = str.indexOf(' ');
        str = str.substring(0, i) + "000000";
        long l = 0L;
        try {
            l = Long.parseLong(str);
        } catch (Exception localException) {
        }
        return l;
    }

    private void showException(Exception paramException) {
        this.messages.append("\nERROR:\n");
        java.io.StringWriter localStringWriter = new java.io.StringWriter();
        java.io.PrintWriter localPrintWriter = new java.io.PrintWriter(localStringWriter);
        paramException.printStackTrace(localPrintWriter);
        localPrintWriter.close();
        this.messages.append(localStringWriter.toString());
        ensureMessagesVisible();
    }

    private volatile boolean blinking = false;

    private Thread blinker(final String paramString1, final String paramString2) {
        Thread localThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str1 = paramString1;
                String str2 = paramString2;
                String str3 = "";
                int i = 0;
                try {
                    while (IndexUI.this.blinking) {
                        Thread.sleep(500L);
                        if (i % 2 == 0) {
                            IndexUI.this.status.setText(str3);
                        } else {
                            IndexUI.this.status.setText(str1);
                        }
                        i++;
                    }
                } catch (InterruptedException localInterruptedException) {
                    IndexUI.this.status.setText(str2);
                }
            }
        });
        this.blinking = true;
        localThread.start();
        return localThread;
    }

    class UIIndexStatus extends lemurproject.indri.IndexStatus {

        UIIndexStatus() {
        }

        @Override
        public void status(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3) {
            if (paramInt1 == lemurproject.indri.IndexStatus.action_code.FileOpen.swigValue()) {
                IndexUI.this.messages.append("Documents: " + paramInt2 + "\n");
                IndexUI.this.messages.append("Opened " + paramString1 + "\n");
            } else if (paramInt1 == lemurproject.indri.IndexStatus.action_code.FileSkip.swigValue()) {
                IndexUI.this.messages.append("Skipped " + paramString1 + "\n");
            } else if (paramInt1 == lemurproject.indri.IndexStatus.action_code.FileError.swigValue()) {
                IndexUI.this.messages.append("Error in " + paramString1 + " : " + paramString2 + "\n");
            } else if ((paramInt1 == lemurproject.indri.IndexStatus.action_code.DocumentCount.swigValue())
                    && (paramInt2 % 500 == 0)) {
                IndexUI.this.messages.append("Documents: " + paramInt2 + "\n");
            }
            int i = IndexUI.this.messages.getText().length();
            try {
                IndexUI.this.messages.scrollRectToVisible(IndexUI.this.messages.modelToView(i));
            } catch (javax.swing.text.BadLocationException localBadLocationException) {
            }
        }
    }

    public static void main(String[] paramArrayOfString) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new IndexUI().setVisible(true);
            }
        });
    }
}
