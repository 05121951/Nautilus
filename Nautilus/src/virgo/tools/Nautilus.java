/*
 * Copyright (c) 1976 2023, Luciano Dell'Olivo. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*/
package virgo.tools;

public class Nautilus 
{
	private static Logger logger;

	public static void main(String[] args) 
	{
		printArgs(args);
		logger = new Logger();
		if (args.length > 0)
			logger.start(args[0]);
		else
			logger.start(null);
	}
	
	private static void printArgs(String[] args)
	{
		if (args.length > 0)
		{
			System.out.println("Nautilus main args:");
			for ( int i = 0; i < args.length; i++)
			{
				System.out.println(args[i]);
			}
		}
	}
	public static Logger getLogger()
	{
		return logger;
	}
}
