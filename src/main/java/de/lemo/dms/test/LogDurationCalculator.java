package de.lemo.dms.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

public class LogDurationCalculator {
	
	
	/**
	 * Calculates and sets the duration-attributes for a List of log-objects. The list "values" has not to be sorted before.
	 *  
	 * @param values	List of Log-objects.
	 */
	public static void calculateDurations(List<ILogMining> values)
	{
		Collections.sort(values);
		HashMap<Long, ArrayList<ILogMining>> userHistories = new HashMap<Long, ArrayList<ILogMining>>();
		for(ILogMining log : values)
		{
			log.setDuration(-1L);
			
			if(userHistories.get(log.getUser().getId()) == null)
			{
				ArrayList<ILogMining> list = new ArrayList<ILogMining>();
				list.add(log);
				userHistories.put(log.getUser().getId(), list);
			}
			else
			{
				ILogMining prevLog = userHistories.get(log.getUser().getId()).get(userHistories.get(log.getUser().getId()).size()-1);
				long d = log.getTimestamp() - prevLog.getTimestamp();
				prevLog.setDuration(d);
				userHistories.get(log.getUser().getId()).add(log);
			}
		}

		System.out.println();
		
	}
	
	/**
	 * Calculates and sets the duration-attributes for a List of generated log-objects. It 
	 * adds occasional "log-outs" (logs with duration "-1"). The list "values" has not to be sorted before.
	 *  
	 * @param values	List of Log-objects.
	 */
	public static void calculateDurationsForGeneratedData(List<ILogMining> values)
	{
		Collections.sort(values);
		HashMap<Long, ArrayList<ILogMining>> userHistories = new HashMap<Long, ArrayList<ILogMining>>();
		for(ILogMining log : values)
		{
			log.setDuration(-1L);
			Random randy = new Random();
			
			if(userHistories.get(log.getUser().getId()) == null)
			{
				ArrayList<ILogMining> list = new ArrayList<ILogMining>();
				list.add(log);
				userHistories.put(log.getUser().getId(), list);
			}
			else
			{
				ILogMining prevLog = userHistories.get(log.getUser().getId()).get(userHistories.get(log.getUser().getId()).size()-1);
				long d = log.getTimestamp() - prevLog.getTimestamp();
				int r = randy.nextInt(3);
				if(r == 0) //Insert random "log-outs"
					prevLog.setDuration(-1L);
				else
					prevLog.setDuration(d);
				userHistories.get(log.getUser().getId()).add(log);
			}
		}

		System.out.println();
		
	}
	
	/**
	 * Sorts a list of log-objects and set the id-attributes accordingly.
	 * 
	 * @param values	List of Log-objects.
	 * @param offset	Offset for the ids. If the offset is "0", the oldest log-object gets the id "0".
	 */
	public static void createIds(List<?> values, int offset)
	{
		
		Collections.sort((ArrayList<ILogMining>)values);
		for(int i = 0; i < values.size(); i++)
		{
			((ArrayList<ILogMining>)values).get(i).setId(i + offset);
		}
			
	}

}