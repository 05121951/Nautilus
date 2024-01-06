package virgo.tools.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import virgo.tools.Logger;

public class NautilusMonitor extends JFrame 
{

	private static final long serialVersionUID = 2901609062194247435L;
	private Logger logger;
	private int monitorX;
	private int monitorY;
	private NMPanel loggerPanel;
	
	public NautilusMonitor(Logger aLogger)
	{
		super("Nautilus Logger Monitor");
		logger = aLogger;
		this.setJMenuBar(new NMMenuBar(logger));
		loggerPanel = new NMPanel();
		this.add(loggerPanel, BorderLayout.CENTER);
	}
	public void openWindow()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(monitorX,monitorY);
		this.setVisible(true);
	}
	public int getMonitorX()
	{
		return monitorX;
	}
	public void setMonitorX(int x)
	{
		monitorX = x;
	}
	public int getMonitorY()
	{
		return monitorY;
	}
	public void setMonitorY(int y)
	{
		monitorY = y;
	}
	public NMPanel getPanel()
	{
		return loggerPanel;
	}
	public void displayLogRequest(String aLog)
	{
		this.getPanel().logPaneAddText(aLog);
	}
}
