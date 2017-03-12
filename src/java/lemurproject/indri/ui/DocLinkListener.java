package lemurproject.indri.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

public class DocLinkListener
        implements HyperlinkListener {

    private JFrame f;
    private JDesktopPane deskTop;
    Image icon;
    int winCount;

    public DocLinkListener(Image paramImage) {
        this.winCount = 0;
        this.icon = paramImage;
        this.f = new JFrame("Web Links");
        this.deskTop = new JDesktopPane();
        this.deskTop.setPreferredSize(new Dimension(700, 500));
        this.f.setContentPane(this.deskTop);

        this.f.setIconImage(this.icon);
        this.f.pack();
        this.f.setDefaultCloseOperation(1);
    }

    public void hyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent) {
        if (paramHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL localURL = paramHyperlinkEvent.getURL();
            JInternalFrame localJInternalFrame = new JInternalFrame(paramHyperlinkEvent.getDescription(), true, true, true, true);

            JTextPane localJTextPane = new JTextPane();
            localJTextPane.setPreferredSize(new Dimension(500, 350));
            localJTextPane.setEditable(false);
            localJTextPane.addHyperlinkListener(this);
            JScrollPane localJScrollPane = new JScrollPane(localJTextPane, 22, 32);

            localJScrollPane.setPreferredSize(new Dimension(600, 400));
            JPanel localJPanel = new JPanel();
            localJPanel.setOpaque(true);
            localJPanel.add(localJScrollPane);
            try {
                localJTextPane.setPage(localURL);
            } catch (IOException localIOException) {
                return;
            }
            localJInternalFrame.getContentPane().add(localJPanel, "Center");
            localJInternalFrame.setFrameIcon(new ImageIcon(this.icon));
            localJInternalFrame.setDefaultCloseOperation(2);
            localJInternalFrame.pack();

            localJInternalFrame.setLocation(30 * this.winCount, 30 * this.winCount);
            this.winCount += 1;
            localJInternalFrame.setVisible(true);
            this.deskTop.add(localJInternalFrame);
            try {
                localJInternalFrame.setSelected(true);
            } catch (PropertyVetoException localPropertyVetoException) {
            }

            this.f.setVisible(true);
        }
    }
}
