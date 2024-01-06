package virgo.tools.ui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class NMPanel extends JPanel 
{

	private final static String newline = "\n";
	private static final long serialVersionUID = 8283369052129813437L;
	private JComponent logPane;
	private JTextArea logPaneTA;
	
	public NMPanel()
	{
		super(new GridLayout(1, 1));
        JTabbedPane tabbedPane = new JTabbedPane();
        logPane = buildLogPanel("About Nautilus Logger");
        tabbedPane.addTab("Logger Panel",logPane);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        this.add(tabbedPane);
	}
	
	protected JComponent buildLogPanel(String text) 
	 {
		 logPaneTA = new JTextArea();
		 logPaneTA.append(text + newline);
	     JScrollPane scrollPane = new JScrollPane(logPaneTA);
	     return scrollPane;
	 }
	
	public void logPaneAddText(String aString)
	{
		logPaneTA.append(aString + newline);
	}

}
