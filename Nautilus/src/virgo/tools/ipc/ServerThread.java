package virgo.tools.ipc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInput;
import java.net.ServerSocket;
import java.net.Socket;

import virgo.tools.logger.client.LogRecord;
import virgo.tools.logger.client.StateNotifyer;
import virgo.tools.Logger;

public class ServerThread extends StateNotifyer implements Runnable 
{
	private boolean logRunning;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private ServerSocket logSocket;
	private Socket currentSocket;
	private Logger logger;
	/*
	 * answer LogRecord di risposta
	 */
	private LogRecord answer;
	/*
	 * receivedLog LogRecord ricevuto sul socket in ascolto
	 */
	LogRecord receivedLog; 

	public ServerThread(Logger aLogger)
	{
		logger = aLogger;
	}
	public void run() 
	{
		String mthName = "run";
		try
		{
			logSocket = new ServerSocket(Integer.valueOf(logger.getServerSocketPort()));
			this.setLogRunning(true);
		}
		catch (IOException ioe)
		{
			String msg = "ServerSocket error;;;" + ioe;
			this.notify(this.getLogger(),mthName,msg,0);
			this.setLogRunning(false);
		}
		while (this.logRunning())
		{
			try 
			{ 
			    currentSocket = logSocket.accept();
			    InputStream o = currentSocket.getInputStream();
		        ObjectInput s = new ObjectInputStream(o);
		        receivedLog = (LogRecord) s.readObject();
		        String msg = "logRunning while loop on logSocket accept;;;";
		        this.notify(this.getLogger(),mthName,msg,1);
		        this.processLogRequest();
			}
			catch (Exception e)
			{
				String msg = "logRunning while loop error;;;" + e;
				this.notify(this.getLogger(),mthName,msg,0);
			}
		}
	}
	public void setLogRunning(boolean onOff)
	{
		logRunning = onOff;
	}
	public boolean logRunning()
	{
		return logRunning;
	}
	public void setAnswer(LogRecord loggerAnswer)
	{
		answer = loggerAnswer;
		this.writeAnswer();
	}
	public LogRecord getAnswer()
	{
		return answer;
	}
	public void processLogRequest()
	{
		this.getLogger().processRequest(receivedLog);
	}
	public void setSocketOut(PrintWriter aStream)
	{
		socketOut = aStream;
	}
	public PrintWriter getSocketOut()
	{
		return socketOut;
	}
	public void setSocketIn(BufferedReader aReader)
	{
		socketIn = aReader;
	}
	public BufferedReader getSocketIn()
	{
		return socketIn;
	}
	public Logger getLogger()
	{
		return logger;
	}
	private void writeAnswer()
	{
		String mthName = "writeAnswer";
		try
		{
			OutputStream aStream = currentSocket.getOutputStream();
			ObjectOutput s = new ObjectOutputStream(aStream);
			s.writeObject(this.getAnswer());
		}
		catch (IOException ioe)
		{
			String msg = "writeObject error;;;" + ioe;
			this.notify(this.getLogger(),mthName,msg,0);
		}
	}
}
