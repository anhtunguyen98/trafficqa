package lemurproject.indri.ui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import lemurproject.indri.QueryAnnotationNode;
import lemurproject.indri.QueryEnvironment;

public class RetUI extends JPanel implements java.awt.event.ActionListener {

    private final javax.swing.JFileChooser fc = new javax.swing.JFileChooser(
            System.getProperties().getProperty("user.dir"));

    private static final String aboutText = "Indri Retrieval UI 1.0\nCopyright (c) 2004 University of Massachusetts\n";

    private static final String helpFile = "properties/IndriRetrieval.html";

    private static final String iconFile = "properties/lemur_head_32.gif";

    private JFrame helpFrame;

    JButton go;

    JButton stop;

    JButton scoreDisplay;

    JLabel status;

    JLabel progress;

    JTextField query;

    JTextField numDocs;

    JTable answerAll;

    JFrame docTextFrame;

    JTextPane docTextPane;

    JFrame docHtmlFrame;

    JTextPane docHtmlPane;

    javax.swing.JTree docQueryTree;

    QueryEnvironment env;

    java.util.Map annotations = null;

    lemurproject.indri.QueryAnnotation results = null;

    lemurproject.indri.ScoredExtentResult[] scored = null;

    lemurproject.indri.ParsedDocument currentParsedDoc = null;

    int currentDocId = 0;

    String[] names = null;

    String[] titles = null;

    int[] docids = null;

    int currentDoc = -1;

    int maxDocs = 10;

    boolean envInit = false;

    private javax.swing.JList documents;

    private javax.swing.DefaultListModel documentsModel;

    private javax.swing.JList indexes;

    private javax.swing.DefaultListModel indexesModel;

    private static final Color lightYellow = new Color(255, 255, 224);

    private static final Color navyBlue = new Color(0, 0, 128);

    private static final Color linen = new Color(250, 240, 230);

    private static java.awt.Cursor wait = java.awt.Cursor.getPredefinedCursor(3);

    private static java.awt.Cursor def = java.awt.Cursor.getPredefinedCursor(0);

    String wordProg;

    String powerpointProg;

    String acroreadProg;

    boolean showScores = false;

    volatile Thread getDocTextThread;

    volatile Thread getDocHtmlThread;

    volatile Thread runQuestionThread;

    public RetUI() {
        this.env = new QueryEnvironment();
        this.indexesModel = new javax.swing.DefaultListModel();
        this.getDocTextThread = null;
        this.getDocHtmlThread = null;
        this.runQuestionThread = null;
    }

    public void init() {
        setPaths();

        makeHelp();

        javax.swing.Box localBox = javax.swing.Box.createVerticalBox();

        setLayout(new java.awt.BorderLayout());
        setBackground(lightYellow);
        setForeground(navyBlue);

        this.go = new JButton("Search");
        this.go.addActionListener(this);
        this.go.setToolTipText("Search the collection");
        this.stop = new JButton("Clear");
        this.stop.addActionListener(this);
        this.stop.setToolTipText("Clear the display");
        this.scoreDisplay = new JButton("Show Scores");
        this.scoreDisplay.addActionListener(this);
        this.scoreDisplay.setToolTipText("Toggle display of document scores");

        this.status = new JLabel("Open an index or server", null, 0);
        this.status.setForeground(Color.red);
        this.status.setBackground(lightYellow);
        this.progress = new JLabel("            ", null, 2);
        this.progress.setForeground(Color.red);
        this.progress.setBackground(lightYellow);

        this.query = new JTextField();
        this.query.setBackground(lightYellow);
        this.query.setForeground(navyBlue);
        this.query.addActionListener(this);
        this.query.setToolTipText("Enter a query");
        JPanel localJPanel1 = new JPanel();
        localJPanel1.setLayout(new java.awt.BorderLayout());
        localJPanel1.add(this.query, "Center");
        JLabel localJLabel = new JLabel("Enter your query:          ", null, 0);

        localJLabel.setForeground(navyBlue);
        localJLabel.setBackground(lightYellow);
        localJPanel1.add(localJLabel, "West");
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);
        localBox.add(localJPanel1);

        localJPanel1 = new JPanel();
        localJPanel1.setLayout(new java.awt.BorderLayout());
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);

        localJLabel = new JLabel("Number of documents:  ", null, 0);
        localJLabel.setForeground(navyBlue);
        localJLabel.setBackground(lightYellow);

        localJPanel1.add(localJLabel, "West");
        this.numDocs = new JTextField();
        this.numDocs.setBackground(lightYellow);
        this.numDocs.setForeground(navyBlue);
        this.numDocs.setText("" + this.maxDocs);
        this.numDocs.setToolTipText("Enter maximum number of documents to retrieve");
        localJPanel1.add(this.numDocs, "Center");

        localJLabel = new JLabel("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", null, 0);
        localJLabel.setForeground(lightYellow);
        localJLabel.setBackground(lightYellow);
        localJPanel1.add(localJLabel, "East");
        localBox.add(localJPanel1);

        localJPanel1 = new JPanel();
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);

        localJPanel1.setLayout(new java.awt.BorderLayout());
        localJLabel = new JLabel("Database(s):                  ", null, 0);
        localJLabel.setForeground(navyBlue);
        localJLabel.setBackground(lightYellow);
        localJPanel1.add(localJLabel, "West");

        this.indexes = new javax.swing.JList(this.indexesModel);
        this.indexes.setSelectionMode(2);
        this.indexes.setVisibleRowCount(3);
        this.indexes.setForeground(navyBlue);
        this.indexes.setBackground(lightYellow);
        JScrollPane localJScrollPane1 = new JScrollPane(this.indexes);
        localJScrollPane1.getViewport().setBackground(lightYellow);
        localJScrollPane1.setPreferredSize(new java.awt.Dimension(400, 60));
        localJPanel1.add(localJScrollPane1, "Center");
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);
        localBox.add(localJPanel1);

        localJPanel1 = new JPanel();
        localJPanel1.add(this.go);
        localJPanel1.add(this.stop);
        localJPanel1.add(this.scoreDisplay);
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);
        JPanel localJPanel2 = new JPanel();
        localJPanel2.setLayout(new java.awt.BorderLayout());
        localJPanel2.add(this.progress, "North");
        localJPanel2.add(localJPanel1, "Center");
        localJPanel2.add(this.status, "South");
        localJPanel2.setForeground(navyBlue);
        localJPanel2.setBackground(lightYellow);
        localBox.add(localJPanel2);

        localJPanel1 = new JPanel();
        localJPanel1.setLayout(new java.awt.BorderLayout());
        localJPanel1.setForeground(navyBlue);
        localJPanel1.setBackground(lightYellow);
        makeDocTextFrame();
        makeDocHtmlFrame();
        this.answerAll = makeDocsTable();
        this.answerAll.setBackground(linen);
        this.answerAll.setForeground(Color.black);
        this.answerAll.setPreferredScrollableViewportSize(new java.awt.Dimension(600, 400));
        JScrollPane localJScrollPane2 = new JScrollPane(this.answerAll);
        localJScrollPane2.setDoubleBuffered(true);
        localJScrollPane2.getViewport().setBackground(linen);
        localJPanel1.add(localJScrollPane2, "Center");
        localBox.add(localJPanel1);
        add(localBox, "Center");
        this.query.setEnabled(this.envInit);
        this.go.setEnabled(this.envInit);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent paramActionEvent) {
        error("                  ");
        String str = paramActionEvent.getActionCommand();
        if ((str.equals("Search")) || (paramActionEvent.getSource() == this.query)) {
            runQuestion();
        } else if (str.equals("Clear")) {
            clearAll();
            this.query.requestFocus();
        } else if (paramActionEvent.getSource() == this.scoreDisplay) {

            this.showScores = (!this.showScores);
            DocsTableModel localDocsTableModel = (DocsTableModel) this.answerAll.getModel();
            localDocsTableModel.displayScores(this.showScores);
            javax.swing.table.TableColumn localTableColumn;
            if (this.showScores) {
                this.scoreDisplay.setText("Hide Scores");
                localTableColumn = this.answerAll.getColumnModel().getColumn(0);
                localTableColumn.setPreferredWidth(20);
                localTableColumn = this.answerAll.getColumnModel().getColumn(1);
                localTableColumn.setPreferredWidth(30);
                localTableColumn = this.answerAll.getColumnModel().getColumn(2);
                localTableColumn.setPreferredWidth(300);
            } else {
                this.scoreDisplay.setText("Show Scores");
                localTableColumn = this.answerAll.getColumnModel().getColumn(0);
                localTableColumn.setPreferredWidth(50);
                localTableColumn = this.answerAll.getColumnModel().getColumn(1);
                localTableColumn.setPreferredWidth(300);
            }
        } else if (str.equals("Add Index")) {
            this.status.setText("Opening...");
            openIndex();
            this.query.setEnabled(this.envInit);
            this.go.setEnabled(this.envInit);
            this.query.requestFocus();
            this.status.setText("");
        } else if (str.equals("Add Server")) {
            this.status.setText("Opening...");
            openServer();
            this.query.setEnabled(this.envInit);
            this.go.setEnabled(this.envInit);
            this.query.requestFocus();
            this.status.setText("");
        } else if (str.equals("Remove Selected Index")) {
            clearAll();
            this.status.setText("Removing...");
            removeIndex();
            this.query.setEnabled(this.envInit);
            this.go.setEnabled(this.envInit);
            this.query.requestFocus();
            this.status.setText("");
        } else if (str.equals("About")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Indri Retrieval UI 1.0\nCopyright (c) 2004 University of Massachusetts\n", "About", 1,
                    createImageIcon("properties/lemur_head_32.gif"));
        } else if (str.equals("Help")) {
            if (!this.helpFrame.isShowing()) {
                this.helpFrame.setLocationRelativeTo(this.query);
                this.helpFrame.setVisible(true);
                this.helpFrame.toFront();
            }
        } else if (str.equals("View as HTML")) {
            getDocHtml();
        } else if (str.equals("View Original")) {
            spawnViewer();
        } else if (str.equals("Exit")) {
            System.exit(0);
        }
    }

    private void clearAll() {
        this.query.setText("");
        this.status.setText("");
        DocsTableModel localDocsTableModel = (DocsTableModel) this.answerAll.getModel();
        localDocsTableModel.clear();

        this.docTextPane.setText("");
        this.docTextFrame.setTitle("Document");
        this.docTextFrame.setVisible(false);
        this.docHtmlPane.setText("");
        this.docHtmlFrame.setTitle("Html");
        this.docHtmlFrame.setVisible(false);

        this.docQueryTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Query")));
    }

    public void removeIndex() {
        int i = this.indexes.getSelectedIndex();
        if (i == -1) {
            return;
        }
        String str = (String) this.indexesModel.get(i);
        try {
            if (str.startsWith("Server: ")) {
                str = str.substring(8);
                this.env.removeServer(str);
            } else {
                this.env.removeIndex(str);
            }
        } catch (Exception localException) {
        }

        this.indexesModel.remove(i);
    }

    public void openIndex() {
        this.fc.setFileSelectionMode(2);
        int i = this.fc.showOpenDialog(this);
        if (i == 0) {
            java.io.File localFile = this.fc.getSelectedFile();
            String str = localFile.getAbsolutePath();

            if (!localFile.exists()) {
                str = localFile.getParentFile().getAbsolutePath();
            }
            this.indexesModel.addElement(str);
            try {
                this.env.addIndex(str);
                this.envInit = true;
            } catch (Exception localException) {
                this.indexesModel.removeElement(this.indexesModel.lastElement());
                error(localException.toString());
            }
        }
    }

    public void openServer() {
        String str = javax.swing.JOptionPane.showInputDialog("Enter a server name,"
                + " with optional port number (host[:portnum]).");

        if (str == null) {
            return;
        }
        this.indexesModel.addElement("Server: " + str);
        try {
            this.env.addServer(str);
            this.envInit = true;
        } catch (Exception localException) {
            this.indexesModel.removeElement(this.indexesModel.lastElement());
            error(localException.toString());
        }
    }

    public void runQuestion() {
        Runnable local1 = new Runnable() {
            public void run() {
                String str = RetUI.this.query.getText();

                if (str.equals("")) {
                    return;
                }
                RetUI.this.status.setText("working...");
                Thread localThread = RetUI.this.blinker("working", "done.");
                DocsTableModel localDocsTableModel = (DocsTableModel) RetUI.this.answerAll.getModel();
                localDocsTableModel.clear();

                RetUI.this.docTextPane.setText("");
                RetUI.this.docTextFrame.setTitle("Document");

                RetUI.this.docQueryTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Query")));
                try {
                    RetUI.this.maxDocs = Integer.parseInt(RetUI.this.numDocs.getText());
                } catch (NumberFormatException localNumberFormatException) {
                }
                try {
                    try {
                        RetUI.this.results = RetUI.this.env.runAnnotatedQuery(str, RetUI.this.maxDocs);
                    } catch (Exception localException1) {
                        RetUI.this.error("No results: " + localException1.toString());
                        RetUI.this.blinking = false;
                        localThread.interrupt();
                        return;
                    }

                    RetUI.this.scored = RetUI.this.results.getResults();
                    try {
                        RetUI.this.names = RetUI.this.env.documentMetadata(RetUI.this.scored, "docno");
                    } catch (Exception localException2) {
                        RetUI.this.names = new String[RetUI.this.scored.length];

                        RetUI.this.error("No docs: " + localException2.toString());
                    }
                    try {
                        RetUI.this.titles = RetUI.this.env.documentMetadata(RetUI.this.scored, "title");
                    } catch (Exception localException3) {
                        RetUI.this.titles = new String[RetUI.this.scored.length];
                        for (int j = 0; j < RetUI.this.titles.length; j++) {
                            RetUI.this.titles[j] = "";
                        }
                        RetUI.this.error("No titles: " + localException3.toString());
                    }

                    RetUI.this.docids = new int[RetUI.this.scored.length];
                    for (int i = 0; i < RetUI.this.scored.length; i++) {
                        RetUI.this.docids[i] = RetUI.this.scored[i].document;
                    }
                    localDocsTableModel.resize(RetUI.this.scored.length);

                    for (int i = 0; i < RetUI.this.scored.length; i++) {
                        localDocsTableModel.setValueAt(i, RetUI.this.scored[i].score, RetUI.this.trim(RetUI.this.names[i]), RetUI.this.titles[i]);
                    }

                    QueryAnnotationNode localQueryAnnotationNode = RetUI.this.results.getQueryTree();
                    RetUI.this.annotations = RetUI.this.results.getAnnotations();

                    DefaultTreeModel localDefaultTreeModel = RetUI.this.makeTreeModel(localQueryAnnotationNode);
                    RetUI.this.docQueryTree.setModel(localDefaultTreeModel);
                } catch (Exception localException4) {
                    RetUI.this.error(localException4.toString());
                }
                RetUI.this.blinking = false;
                localThread.interrupt();
            }
        };
        Thread localThread = new Thread(local1);
        localThread.start();
    }

    private void setPaths() {
        this.wordProg = GetPaths.getPath("winword.exe");
        this.powerpointProg = GetPaths.getPath("powerpnt.exe");
        this.acroreadProg = GetPaths.getPath("acrobat.exe");
        if (this.acroreadProg == null) {
            this.acroreadProg = GetPaths.getPath("acrord32.exe");
        }
    }

    private void makeHelp() {
        java.net.URL localURL = RetUI.class.getResource("properties/IndriRetrieval.html");
        java.awt.Image localImage = createImageIcon("properties/lemur_head_32.gif").getImage();
        JTextPane localJTextPane = new JTextPane();

        this.helpFrame = new JFrame("Indri Retrieval UI Help");
        localJTextPane.setPreferredSize(new java.awt.Dimension(650, 400));
        localJTextPane.setEditable(false);
        localJTextPane.addHyperlinkListener(new DocLinkListener(localImage));
        JScrollPane localJScrollPane = new JScrollPane(localJTextPane);
        try {
            localJTextPane.setPage(localURL);
        } catch (java.io.IOException localIOException) {
            localJTextPane.setText("Help file unavailable.");
        }

        this.helpFrame.getContentPane().add(localJScrollPane, "Center");
        this.helpFrame.setDefaultCloseOperation(1);
        this.helpFrame.setIconImage(localImage);
        this.helpFrame.pack();
    }

    private void makeDocTextFrame() {
        JMenuBar localJMenuBar = new JMenuBar();
        localJMenuBar.setForeground(navyBlue);
        localJMenuBar.setBackground(lightYellow);
        JMenu localJMenu = new JMenu("File");
        localJMenu.setForeground(navyBlue);
        localJMenu.setBackground(lightYellow);
        localJMenu.add(makeMenuItem("View as HTML"));

        localJMenu.add(makeMenuItem("View Original"));
        localJMenuBar.add(localJMenu);

        this.docTextFrame = new JFrame("Document");
        this.docTextFrame.setJMenuBar(localJMenuBar);
        this.docTextFrame.setIconImage(createImageIcon("properties/lemur_head_32.gif").getImage());
        this.docTextPane = new JTextPane();
        this.docTextPane.setEditable(false);
        this.docTextPane.setBackground(linen);
        this.docTextPane.setForeground(Color.black);
        this.docTextPane.setPreferredSize(new java.awt.Dimension(550, 350));

        JScrollPane localJScrollPane1 = new JScrollPane(this.docTextPane);
        localJScrollPane1.setDoubleBuffered(true);
        DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode("Query");
        this.docQueryTree = new javax.swing.JTree(localDefaultMutableTreeNode);
        this.docQueryTree.addTreeSelectionListener(new QueryTreeListener());
        this.docQueryTree.setVisibleRowCount(8);
        JScrollPane localJScrollPane2 = new JScrollPane(this.docQueryTree);
        localJScrollPane2.setDoubleBuffered(true);
        this.docTextFrame.getContentPane().add(localJScrollPane2, "First");
        this.docTextFrame.getContentPane().add(localJScrollPane1, "Center");
        this.docTextFrame.pack();
        this.docTextFrame.setDefaultCloseOperation(1);
    }

    private void makeDocHtmlFrame() {
        this.docHtmlFrame = new JFrame("Html");
        this.docHtmlFrame.setIconImage(createImageIcon("properties/lemur_head_32.gif").getImage());
        this.docHtmlPane = new JTextPane();
        this.docHtmlPane.setEditable(false);
        this.docHtmlPane.setBackground(linen);
        this.docHtmlPane.setForeground(Color.black);
        this.docHtmlPane.setPreferredSize(new java.awt.Dimension(550, 350));

        JScrollPane localJScrollPane = new JScrollPane(this.docHtmlPane);
        localJScrollPane.setDoubleBuffered(true);
        this.docHtmlFrame.getContentPane().add(localJScrollPane, "Center");
        this.docHtmlFrame.pack();
        this.docHtmlFrame.setDefaultCloseOperation(1);
    }

    private void spawnViewer() {
        int i = this.answerAll.getSelectionModel().getMinSelectionIndex();

        if (i == -1) {
            return;
        }
        javax.swing.table.TableModel localTableModel = this.answerAll.getModel();
        String str1 = this.names[i];

        if (str1.equals("")) {
            return;
        }
        java.io.File localFile = new java.io.File(str1);

        if (!localFile.exists()) {
            return;
        }
        str1 = str1.toLowerCase();
        String str2 = null;
        if (str1.endsWith(".doc")) {
            str2 = this.wordProg;
        } else if (str1.endsWith(".ppt")) {
            str2 = this.powerpointProg;
        } else if (str1.endsWith(".pdf")) {
            str2 = this.acroreadProg;
        }
        if (str2 == null) {
            return;
        }
        String[] arrayOfString = new String[2];
        arrayOfString[0] = str2;
        arrayOfString[1] = str1;
        Runtime localRuntime = Runtime.getRuntime();

        int j = -1;
        try {
            Process localProcess = localRuntime.exec(arrayOfString);
        } catch (Exception localException) {
            error(localException.toString());
        }
    }

    public void altgetDocText() {
        final int i = this.answerAll.getSelectionModel().getMinSelectionIndex();

        if (i == -1) {
            return;
        }
        while (this.getDocTextThread != null) {
            try {
                this.getDocTextThread.interrupt();
                Thread.sleep(200L);
            } catch (InterruptedException localInterruptedException) {
            }
        }

        Runnable local2 = new Runnable() {
            public void run() {
                try {
                    RetUI.this.setCursor(RetUI.wait);
                    RetUI.this.docTextFrame.setCursor(RetUI.wait);
                    RetUI.this.docTextPane.setCursor(RetUI.wait);

                    javax.swing.table.TableModel localTableModel = RetUI.this.answerAll.getModel();

                    String str1 = RetUI.this.names[i];
                    RetUI.this.status.setText("Getting " + str1);
                    String str2 = (String) localTableModel.getValueAt(i, 1);
                    if (str2.equals("")) {
                        RetUI.this.docTextFrame.setTitle(str1);
                    } else {
                        RetUI.this.docTextFrame.setTitle(str2);
                    }
                    RetUI.this.currentDocId = RetUI.this.docids[i];

                    int[] arrayOfInt = new int[1];
                    arrayOfInt[0] = RetUI.this.currentDocId;

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }

                    lemurproject.indri.ParsedDocument[] arrayOfParsedDocument = RetUI.this.env.documents(arrayOfInt);

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    RetUI.this.currentParsedDoc = arrayOfParsedDocument[0];
                    String str3 = RetUI.this.currentParsedDoc.text;

                    RetUI.this.docTextPane.setContentType("text/plain");
                    RetUI.this.docTextPane.setText(str3);

                    RetUI.this.docTextPane.setCaretPosition(0);

                    if (!RetUI.this.docTextFrame.isShowing()) {
                        RetUI.this.docTextFrame.setLocationRelativeTo(RetUI.this.query);
                        RetUI.this.docTextFrame.setVisible(true);
                    }

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }

                    DefaultTreeModel localDefaultTreeModel = (DefaultTreeModel) RetUI.this.docQueryTree.getModel();
                    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) localDefaultTreeModel.getRoot();

                    RetUI.this.highlight(localDefaultMutableTreeNode);
                    RetUI.this.status.setText(" ");
                } catch (InterruptedException localInterruptedException) {
                    RetUI.this.status.setText(" ");
                } catch (OutOfMemoryError localOutOfMemoryError) {
                    RetUI.this.status.setText("Out of Memory. Unable to open document");
                } catch (Exception localException) {
                    RetUI.this.status.setText("Unable to fetch ParsedDocument: " + localException);
                }

                RetUI.this.setCursor(RetUI.def);
                RetUI.this.docTextFrame.setCursor(RetUI.def);
                RetUI.this.docTextPane.setCursor(RetUI.def);
                RetUI.this.getDocTextThread = null;
            }
        };
        this.getDocTextThread = new Thread(local2);
        this.getDocTextThread.start();
    }

    public void getDocText() {
        error("                  ");

        final int i = this.answerAll.getSelectionModel().getMinSelectionIndex();

        if (i == -1) {
            return;
        }
        while (this.getDocTextThread != null) {
            try {
                this.getDocTextThread.interrupt();
                Thread.sleep(200L);
            } catch (InterruptedException localInterruptedException) {
            }
        }

        Runnable local3 = new Runnable() {
            public void run() {
                try {
                    RetUI.this.setCursor(RetUI.wait);
                    RetUI.this.docTextFrame.setCursor(RetUI.wait);
                    RetUI.this.docTextPane.setCursor(RetUI.wait);

                    javax.swing.table.TableModel localTableModel = RetUI.this.answerAll.getModel();
                    String str1 = RetUI.this.names[i];
                    RetUI.this.status.setText("Getting " + str1);
                    String str2 = (String) localTableModel.getValueAt(i, 1);
                    if (str2.equals("")) {
                        RetUI.this.docTextFrame.setTitle(str1);
                    } else {
                        RetUI.this.docTextFrame.setTitle(str2);
                    }
                    RetUI.this.currentDocId = RetUI.this.docids[i];

                    int[] arrayOfInt = new int[1];
                    arrayOfInt[0] = RetUI.this.currentDocId;

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    lemurproject.indri.ParsedDocument[] arrayOfParsedDocument = null;
                    try {
                        arrayOfParsedDocument = RetUI.this.env.documents(arrayOfInt);
                    } catch (Exception localException) {
                        RetUI.this.error(localException.toString());
                        RetUI.this.status.setText(" ");
                        RetUI.this.setCursor(RetUI.def);
                        RetUI.this.docTextFrame.setCursor(RetUI.def);
                        RetUI.this.docTextPane.setCursor(RetUI.def);
                        RetUI.this.getDocTextThread = null;
                        return;
                    }

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    RetUI.this.currentParsedDoc = arrayOfParsedDocument[0];
                    String str3 = RetUI.this.currentParsedDoc.text;

                    StringBuffer localStringBuffer = new StringBuffer();
                    localStringBuffer.ensureCapacity(str3.length());

                    for (int i = 0; i < str3.length(); i++) {
                        char c = str3.charAt(i);

                        if (c == '“') {
                            localStringBuffer.append(" ''");
                        } else if (c == '”') {
                            localStringBuffer.append("'' ");
                        } else if (c == 'Σ') {

                            localStringBuffer.append(" ");
                            localStringBuffer.append(c);
                        } else if (c > 'ÿ') {
                            localStringBuffer.append("  ");
                            localStringBuffer.append(c);

                        } else if ((c == 'Ì') || (c == '®')) {
                            localStringBuffer.append(" ");
                            localStringBuffer.append(c);

                        } else if (c == '\r') {
                            localStringBuffer.append(' ');
                        } else {
                            localStringBuffer.append(c);
                        }
                        if ((i % 1000 == 0) && (Thread.interrupted())) {
                            throw new InterruptedException();
                        }
                    }
                    str3 = localStringBuffer.toString();

                    RetUI.this.docTextPane.setContentType("text/plain");
                    RetUI.this.docTextPane.setText(str3);

                    RetUI.this.docTextPane.setCaretPosition(0);

                    if (!RetUI.this.docTextFrame.isShowing()) {
                        RetUI.this.docTextFrame.setLocationRelativeTo(RetUI.this.query);
                        RetUI.this.docTextFrame.setVisible(true);
                    }

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }

                    DefaultTreeModel localDefaultTreeModel = (DefaultTreeModel) RetUI.this.docQueryTree.getModel();
                    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) localDefaultTreeModel.getRoot();

                    RetUI.this.highlight(localDefaultMutableTreeNode);
                    RetUI.this.status.setText(" ");
                } catch (InterruptedException localInterruptedException) {
                    RetUI.this.status.setText(" ");
                } catch (OutOfMemoryError localOutOfMemoryError) {
                    RetUI.this.status.setText("Out of Memory. Unable to open document");
                }

                RetUI.this.setCursor(RetUI.def);
                RetUI.this.docTextFrame.setCursor(RetUI.def);
                RetUI.this.docTextPane.setCursor(RetUI.def);
                RetUI.this.getDocTextThread = null;
            }
        };
        this.getDocTextThread = new Thread(local3);
        this.getDocTextThread.start();
    }

    public void getDocHtml() {
        final int i = this.answerAll.getSelectionModel().getMinSelectionIndex();

        if (i == -1) {
            return;
        }
        Runnable local4 = new Runnable() {
            public void run() {
                RetUI.this.setCursor(RetUI.wait);

                javax.swing.table.TableModel localTableModel = RetUI.this.answerAll.getModel();
                String str1 = RetUI.this.names[i];
                RetUI.this.status.setText("Getting " + str1);
                String str2 = (String) localTableModel.getValueAt(i, 1);
                if (str2.equals("")) {
                    RetUI.this.docHtmlFrame.setTitle(str1);
                } else {
                    RetUI.this.docHtmlFrame.setTitle(str2);
                }
                RetUI.this.currentDocId = RetUI.this.docids[i];

                int[] arrayOfInt = new int[1];
                arrayOfInt[0] = RetUI.this.currentDocId;

                lemurproject.indri.ParsedDocument[] arrayOfParsedDocument = null;
                try {
                    arrayOfParsedDocument = RetUI.this.env.documents(arrayOfInt);
                } catch (Exception localException) {
                    RetUI.this.error(localException.toString());
                    RetUI.this.status.setText(" ");
                    RetUI.this.setCursor(RetUI.def);
                    return;
                }

                RetUI.this.currentParsedDoc = arrayOfParsedDocument[0];

                String str3 = RetUI.this.currentParsedDoc.content;

                RetUI.this.docHtmlPane.setContentType("text/html");

                RetUI.this.docHtmlPane.getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE);

                RetUI.this.docHtmlPane.setText(str3);

                RetUI.this.docHtmlPane.setCaretPosition(0);
                if (!RetUI.this.docHtmlFrame.isShowing()) {
                    RetUI.this.docHtmlFrame.setLocationRelativeTo(RetUI.this.query);
                    RetUI.this.docHtmlFrame.setVisible(true);
                }
                RetUI.this.status.setText(" ");
                RetUI.this.setCursor(RetUI.def);
            }
        };
        Thread localThread = new Thread(local4);
        localThread.start();
    }

    private void highlight(DefaultMutableTreeNode paramDefaultMutableTreeNode)
            throws InterruptedException {
        javax.swing.text.StyledDocument localStyledDocument = this.docTextPane.getStyledDocument();

        javax.swing.text.SimpleAttributeSet localSimpleAttributeSet = new javax.swing.text.SimpleAttributeSet();

        DefaultTreeModel localDefaultTreeModel = (DefaultTreeModel) this.docQueryTree.getModel();
        DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode) localDefaultTreeModel.getRoot();

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        clearHighlight(localStyledDocument, localSimpleAttributeSet);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        javax.swing.text.StyleConstants.setForeground(localSimpleAttributeSet, linen);
        javax.swing.text.StyleConstants.setBackground(localSimpleAttributeSet, Color.red);
        int i = 0;

        if (paramDefaultMutableTreeNode == localDefaultMutableTreeNode1) {
            java.util.Enumeration localEnumeration = localDefaultMutableTreeNode1.breadthFirstEnumeration();
            while (localEnumeration.hasMoreElements()) {
                DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode) localEnumeration.nextElement();
                UIQueryNode localUIQueryNode = (UIQueryNode) localDefaultMutableTreeNode2.getUserObject();
                QueryAnnotationNode localQueryAnnotationNode = localUIQueryNode.getNode();

                if (localQueryAnnotationNode.type.equals("RawScorerNode")) {
                    highlight(localUIQueryNode, localStyledDocument, localSimpleAttributeSet);
                }
            }
        } else {
            highlight((UIQueryNode) paramDefaultMutableTreeNode.getUserObject(), localStyledDocument, localSimpleAttributeSet);
        }
    }

    private void clearHighlight(javax.swing.text.StyledDocument paramStyledDocument, javax.swing.text.MutableAttributeSet paramMutableAttributeSet) {
        paramStyledDocument.setCharacterAttributes(paramStyledDocument.getStartPosition().getOffset(), paramStyledDocument
                .getEndPosition().getOffset(), paramMutableAttributeSet, true);
    }

    private void highlight(UIQueryNode paramUIQueryNode, javax.swing.text.StyledDocument paramStyledDocument, javax.swing.text.MutableAttributeSet paramMutableAttributeSet)
            throws InterruptedException {
        boolean bool = true;

        QueryAnnotationNode localQueryAnnotationNode = paramUIQueryNode.getNode();
        String str = localQueryAnnotationNode.name;
        lemurproject.indri.ScoredExtentResult[] arrayOfScoredExtentResult = (lemurproject.indri.ScoredExtentResult[]) this.annotations.get(str);

        if (arrayOfScoredExtentResult != null) {
            for (int i = 0; i < arrayOfScoredExtentResult.length; i++) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                if (this.currentDocId == arrayOfScoredExtentResult[i].document) {
                    int j = arrayOfScoredExtentResult[i].begin;
                    int k = arrayOfScoredExtentResult[i].end - 1;
                    if ((j < this.currentParsedDoc.positions.length) && (k < this.currentParsedDoc.positions.length)) {
                        int m = this.currentParsedDoc.positions[j].begin;
                        int n = this.currentParsedDoc.positions[k].end;
                        paramStyledDocument.setCharacterAttributes(m, n - m, paramMutableAttributeSet, bool);
                    }
                }
            }
        }
    }

    private DefaultTreeModel makeTreeModel(QueryAnnotationNode paramQueryAnnotationNode) {
        DefaultMutableTreeNode localDefaultMutableTreeNode = makeChildNode(paramQueryAnnotationNode);
        DefaultTreeModel localDefaultTreeModel = new DefaultTreeModel(localDefaultMutableTreeNode);
        return localDefaultTreeModel;
    }

    private DefaultMutableTreeNode makeChildNode(QueryAnnotationNode paramQueryAnnotationNode) {
        UIQueryNode localUIQueryNode = new UIQueryNode(paramQueryAnnotationNode);
        DefaultMutableTreeNode localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(localUIQueryNode);

        for (int i = 0; i < paramQueryAnnotationNode.children.length; i++) {
            DefaultMutableTreeNode localDefaultMutableTreeNode2 = makeChildNode(paramQueryAnnotationNode.children[i]);
            localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
        }
        return localDefaultMutableTreeNode1;
    }

    private class QueryTreeListener
            implements javax.swing.event.TreeSelectionListener {

        private QueryTreeListener() {
        }

        public void valueChanged(javax.swing.event.TreeSelectionEvent paramTreeSelectionEvent) {
            javax.swing.tree.TreePath localTreePath = paramTreeSelectionEvent.getPath();
            if (paramTreeSelectionEvent.isAddedPath(localTreePath)) {
                DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode) localTreePath.getLastPathComponent();
                RetUI.this.docQueryTree.scrollPathToVisible(localTreePath);
                try {
                    RetUI.this.highlight(localDefaultMutableTreeNode);
                } catch (InterruptedException localInterruptedException) {
                }
            }
        }
    }

    private class DocTableListener
            implements javax.swing.event.ListSelectionListener {

        private DocTableListener() {
        }

        public void valueChanged(javax.swing.event.ListSelectionEvent paramListSelectionEvent) {
            if (!paramListSelectionEvent.getValueIsAdjusting()) {
                RetUI.this.getDocText();
            }
        }
    }

    private class UIQueryNode {

        QueryAnnotationNode node;

        public UIQueryNode(QueryAnnotationNode paramQueryAnnotationNode) {
            this.node = paramQueryAnnotationNode;
        }

        public String toString() {
            String str = this.node.queryText + " (" + this.node.type + ")";
            return str;
        }

        public QueryAnnotationNode getNode() {
            return this.node;
        }
    }

    private volatile boolean blinking = false;

    private Thread blinker(final String paramString1, final String paramString2) {
        Thread localThread = new Thread(new Runnable() {
            public void run() {
                String str1 = paramString1;
                String str2 = paramString2;
                String str3 = "       ";
                int i = 0;
                try {
                    while (RetUI.this.blinking) {
                        Thread.sleep(500L);
                        if (i % 2 == 0) {
                            RetUI.this.status.setText(str3);
                        } else {
                            RetUI.this.status.setText(str1);
                        }
                        i++;
                    }
                    RetUI.this.status.setText(str2);
                } catch (InterruptedException localInterruptedException) {
                    RetUI.this.status.setText(str2);
                }
            }
        });
        this.blinking = true;
        localThread.start();
        return localThread;
    }

    public void error(String paramString) {
        this.progress.setText(paramString);
    }

    private String trim(String paramString) {
        java.io.File localFile = new java.io.File(paramString);
        return localFile.getName();
    }

    private JTable makeDocsTable() {
        DocsTableModel localDocsTableModel = new DocsTableModel();
        JTable local6 = new JTable(localDocsTableModel) {
            public String getToolTipText(java.awt.event.MouseEvent paramAnonymousMouseEvent) {
                String str = null;
                java.awt.Point localPoint = paramAnonymousMouseEvent.getPoint();
                int i = rowAtPoint(localPoint);
                int j = columnAtPoint(localPoint);
                int k = convertColumnIndexToModel(j);
                int m = RetUI.this.showScores ? 1 : 0;
                if ((k == -1) || (i == -1)) {
                    str = null;
                } else if (k == m) {
                    str = RetUI.this.names[i];
                } else {
                    str = (String) getValueAt(i, j);
                }
                return str;
            }
        };
        local6.setSelectionMode(0);
        local6.getSelectionModel().addListSelectionListener(new DocTableListener());

        javax.swing.table.TableColumn localTableColumn = local6.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(50);

        localTableColumn = local6.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(300);

        return local6;
    }

    private javax.swing.JMenuItem makeMenuItem(String paramString) {
        javax.swing.JMenuItem localJMenuItem = new javax.swing.JMenuItem(paramString);
        localJMenuItem.setForeground(navyBlue);
        localJMenuItem.setBackground(lightYellow);
        localJMenuItem.addActionListener(this);
        return localJMenuItem;
    }

    protected static javax.swing.ImageIcon createImageIcon(String paramString) {
        java.net.URL localURL = RetUI.class.getResource(paramString);
        if (localURL != null) {
            return new javax.swing.ImageIcon(localURL);
        }
        return null;
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception localException) {
        }
        RetUI localRetUI = new RetUI();
        JFrame localJFrame = new JFrame("Indri -- Retrieval");
        localJFrame.setIconImage(createImageIcon("properties/lemur_head_32.gif").getImage());
        localJFrame.setDefaultCloseOperation(3);
        localRetUI.init();
        localRetUI.setOpaque(true);
        localJFrame.getContentPane().add(localRetUI, "Center");
        localJFrame.setForeground(navyBlue);
        localJFrame.setBackground(lightYellow);
        localJFrame.getContentPane().setForeground(navyBlue);
        localJFrame.getContentPane().setBackground(lightYellow);

        JMenuBar localJMenuBar = new JMenuBar();
        localJMenuBar.setForeground(navyBlue);
        localJMenuBar.setBackground(lightYellow);
        JMenu localJMenu1 = new JMenu("File");
        localJMenu1.setForeground(navyBlue);
        localJMenu1.setBackground(lightYellow);
        localJMenu1.add(localRetUI.makeMenuItem("Add Index"));
        localJMenu1.add(localRetUI.makeMenuItem("Add Server"));

        localJMenu1.add(localRetUI.makeMenuItem("Remove Selected Index"));
        localJMenu1.add(localRetUI.makeMenuItem("Exit"));
        localJMenuBar.add(localJMenu1);
        JMenu localJMenu2 = new JMenu("Help");
        localJMenu2.setForeground(navyBlue);
        localJMenu2.setBackground(lightYellow);
        localJMenu2.add(localRetUI.makeMenuItem("Help"));
        localJMenu2.add(localRetUI.makeMenuItem("About"));
        localJMenuBar.add(javax.swing.Box.createHorizontalGlue());
        localJMenuBar.add(localJMenu2);
        localJFrame.setJMenuBar(localJMenuBar);
        localJFrame.pack();
        localJFrame.setVisible(true);
    }

    public static void main(String[] paramArrayOfString) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
}
