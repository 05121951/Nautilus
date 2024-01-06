package virgo.tools.ui;

import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import virgo.tools.Logger;

public class NMMenuBar extends JMenuBar 
{

	private static final long serialVersionUID = 7436401461622818550L;
	private JMenu loggerMenu;
	private JMenuItem item;
	private NMActionListener listener;
	private Logger logger;
	
	public NMMenuBar(Logger aLogger)
	{
		logger = aLogger;
		loggerMenu = new JMenu("Logger Console");
		this.add(loggerMenu);
		loggerMenu.add(this.getItemFor("Start Server", "startServer", aLogger));
		loggerMenu.add(this.getItemFor("Stop Server", "stopServer", aLogger));
		loggerMenu.add(this.getItemFor("Hey Guys", "heyGuys", aLogger));
		loggerMenu.add(this.getItemFor("View Log", "inspectLogDB", aLogger));
	}
	
	public JMenuItem getItemFor(String label, String action, Logger aLogger)
	{
		item = new JMenuItem(label);
		HashMap<String, Object>eventE = new HashMap<String, Object>();
		eventE.put("action", action);
		eventE.put("logger", aLogger);
		listener = new NMActionListener(eventE, logger);
		item.addActionListener(listener);
		return item;
	}
}
