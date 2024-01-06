package virgo.tools;

import java.util.ArrayList;
import java.util.HashMap;

import virgo.tools.ipc.ServerThread;
import virgo.tools.logger.client.Agent;
import virgo.tools.logger.client.LogRecord;
import virgo.tools.logger.client.LoggerProtocol;
import virgo.tools.logger.client.StateNotifyer;
import virgo.tools.ui.NautilusMonitor;

public class Logger extends StateNotifyer implements Agent,Server
{
	/*
	 * Classe da rivedere,
	 */
		private String serverSocketPort = "8099";
		private NautilusMonitor monitor;
		private ServerThread logServer;
		private Thread serverThread;
		private LoggerProtocol lp;
		private HashMap<String,ArrayList<LogRecord>> logRecordsDB;
		@Override
		public void start(String portNode) 
		{
			/*
			 * La roba seguente è da aggiornare in funzione di StateNotifyer
			 * Remember il metodo setMessage di LogRecord (lr) invoca il metodo
			 * runToLogger che utilizza un Agent (writer) in questo caso il writer
			 * è il Logger stesso, quindi lr non è trasferita al LogServer, inoltre
			 * runToLogger esegue un clone di lr in modo che l'istanza comune
			 * alla classe possa essere riutilizzata da ogni altro metodo.
			 */
			logRecordsDB = new HashMap<String,ArrayList<LogRecord>>();
			
			String mthName = "start";
			
			if (portNode == null)
			{
				String msg = "starting Logger port 8099;;;";
				this.notify(this,mthName,msg,1);
			}
			else 
			{
				String msg = "starting Logger;"+portNode+";;";
				this.notify(this,mthName,msg,1);
				serverSocketPort = portNode;
			}
			monitor = new NautilusMonitor(this);
			monitor.setMonitorX(1020);
			monitor.setMonitorY(640);
			monitor.openWindow();
			lp = new LoggerProtocol(this);
			this.startServer();
		}

		public void startServer()
		{
			String mthName = "startServer";
			String msg = "starting log server;;;";
			this.notify(this,mthName,msg,0);
			logServer = new ServerThread(this);
			serverThread = new Thread(logServer);
			serverThread.start();
		}
		public void stopServer()
		{
			String mthName = "stopServer";
			String msg = "stopping log server;;;";
			logServer.setLogRunning(false);
			this.notify(this,mthName,msg,0);
		}
		public void heyGuys()
		{
			String mthName = "heyGuys";
			String msg = "polling Logger clients;;;";
			this.notify(this,mthName,msg,0);
		}
		public void inspectLogDB()
		{
			String mthName = "inspectLogDB";
			String msg = "view Logger DB;;;";
			this.notify(this,mthName,msg,0);
		}
		public String getServerSocketPort()
		{
			return serverSocketPort;
		}
		public void writeLog(LogRecord aLog)
		{
			this.addLogRecord(aLog);
		}
		public HashMap<String,ArrayList<LogRecord>> getLogRecordsDB()
		{
			return logRecordsDB;
		}
		public boolean addLogRecord(LogRecord aLR)
		{
			String key = aLR.getSourceClassName();
			if (logRecordsDB.containsKey(key))
			{
				logRecordsDB.get(key).add(aLR);
			}
			else
			{
				logRecordsDB.put(key, new ArrayList<LogRecord>());
				logRecordsDB.get(key).add(aLR);
			}
			if (this.getMonitor() != null)
			{
				this.getMonitor().displayLogRequest(aLR.toString());
			}
			else
			{
				String mthName = "addLogRecord";
				String msg = "Nautilus monitor not available for display LogRecord;;;";
				this.notify(this,mthName,msg,1);
			}
			return true;
		}
		public NautilusMonitor getMonitor()
		{
			return monitor;
		}
		public LoggerProtocol getLoggerProtocol()
		{
			return lp;
		}
		public void processRequest(LogRecord aLR)
		{
			LogRecord answer = this.getLoggerProtocol().parseNautilusRequest(aLR);
			if (answer != null)
				logServer.setAnswer(answer);
		}

		@Override
		public void initialize() 
		{
			// TODO Auto-generated method stub
			
		}
		public void notifyEndUserSession(LogRecord aLogRecord)
		{
			// TODO Auto-generated method stub
		}
		@Override
		public void writeOrSend(LogRecord  aLogRecord) 
		{
			this.writeLog(aLogRecord);
		}

}
