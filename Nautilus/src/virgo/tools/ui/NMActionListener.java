package virgo.tools.ui;

import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import virgo.tools.Logger;
import virgo.tools.logger.client.StateNotifyer;

public class NMActionListener implements ActionListener 
{

	private HashMap<String,Object> eventEE;
	private StateNotifyer notifyer = new StateNotifyer();
	private Logger logger;
	
	/*
	 * deve essere implementato il codice per il close-window
	 * il codice deve .... non si può fare Informer ha sempre il file
	 * aperto il nautilus.log deve essere relativo alla JVM che instanzia
	 * il nautilus-client pertanto la chiusura del file va fatta ad esempio
	 * da JSE
	 */
	NMActionListener(HashMap<String,Object> eventEnv, Logger aLogger)
	{
		eventEE = eventEnv;
		logger = aLogger;
	}
	public void actionPerformed(ActionEvent e)
	{
		String mthName = "actionPerformed";
		String action = (String)eventEE.get("action");
		Method actionTarget = this.findMethod(action);
		if (actionTarget == null)
		{
			String msg = "target method not found;"+action+";;";
			notifyer.notify(logger,mthName,msg,1);
			return;
		}
		String msg = "perform actionTarget;"+actionTarget.toString()+";;";
		notifyer.notify(logger,mthName,msg,1);
		Object[] args = new Object[1];
		args[0] = e;
		try {actionTarget.invoke(this, args);} 
		catch (IllegalAccessException e1) 
			{e1.printStackTrace();} 
		catch (IllegalArgumentException e1) 
			{e1.printStackTrace();} 
		catch (InvocationTargetException e1) 
			{e1.printStackTrace();}	
	}
	public void startServer(ActionEvent e)
	{
		Logger logger = (Logger)eventEE.get("logger");
		logger.startServer();
	}
	public void stopServer(ActionEvent e)
	{
		Logger logger = (Logger)eventEE.get("logger");
		logger.stopServer();
	}
	public void heyGuys(ActionEvent e)
	{
		Logger logger = (Logger)eventEE.get("logger");
		logger.heyGuys();
	}
	public void inspectLogDB(ActionEvent e)
	{
		Logger logger = (Logger)eventEE.get("logger");
		logger.inspectLogDB();
	}
	private Method findMethod(String methodName)
	{
		Method[] myMethods = this.getClass().getMethods();
		for (int i = 0; i < myMethods.length; i++)
		{
			if (myMethods[i].getName().equalsIgnoreCase(methodName))
				return myMethods[i];
		}
		return null;
	}
}
