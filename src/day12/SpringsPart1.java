package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringsPart1 {
	
	public static long possibilities(String schematics, List<Integer> config)
	{
		//First, return 2^n (for n ?) if config states zero springs
		if (config.isEmpty())
		{
			int count = schematics.length() - schematics.replace("?", "").length();
			return (long) Math.pow(2, count);
		}
		long poss = 0;
		Pattern pattern = Pattern.compile("#+");
		List<String> trials = new ArrayList<String>();
		trials.add(schematics);
		while (! trials.isEmpty())
		{
			String full = trials.get(0);
			String current = full;			
			List<Integer> knownConfig = new ArrayList<Integer>();
			//If there are no springs, no need to fill anything
			int stopIndex;
			//Trim off everything from first ? on. 
			if (current.contains("?"))
			{
				stopIndex = current.indexOf("?");
			}
			else
			{
				stopIndex = current.length();
			}
			current = current.substring(0, stopIndex);
			//Loop through until the first question mark to build config so far 
			while (! current.equals(""))
			{
				//System.out.println(current);
				//If there's another spring, add it to the list, then snip off the spring region and everything beforehand
				if (current.contains("#"))
				{
					int startLoc = current.indexOf("#");
					Matcher matcher = pattern.matcher(current);
					matcher.find();
					int endLoc = matcher.end();
					knownConfig.add(endLoc - startLoc);
					//System.out.println("Adding " + (endLoc - startLoc));
					if (endLoc < current.length())
					{
						current = current.substring(endLoc + 1);
					}
					else
					{
						current = "";
					}
				}
				else
				{
					current = "";
				}
			}
			//If no ?, check for perfect match and bump count if needed. 
			if (! full.contains("?"))
			{
				if (config.equals(knownConfig))
				{
					poss ++;
				}
			}
			else
			{
				//If there are any ?, check if the list is good enough so far. 
				
				//First, check to make sure list is not larger. 
				if (knownConfig.size() <= config.size())
				{
					//Next, check that everything excpet the last one is equal.
					int i = 0;
					boolean AOK = true;
					while (i < knownConfig.size() - 1)
					{
						if (! knownConfig.get(i).equals(config.get(i)))
						{
							AOK = false;
						}	
						i++;
					}
					//Finally, make sure the last one is smaller (or there are no springs yet) 
					if ((knownConfig.size() == 0) || (AOK && (knownConfig.get(i) <= config.get(i))))
					{
						//If everything checks out, add the next two to the list to check. 
						int qLoc = full.indexOf("?");
						trials.add(full.substring(0, qLoc) + "." + full.substring(qLoc + 1));
						trials.add(full.substring(0, qLoc) + "#" + full.substring(qLoc + 1));
					}
				}
			}
			//Remove current trial from list
			trials.remove(0);
		}
		
		return poss;
	}
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day12\\testable.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		long count = 0;
		while((line = br.readLine()) != null)
		{
			String[] splits = line.split("[,\\s+]");
			String[] numStrings = Arrays.copyOfRange(splits, 1, splits.length);
			List<Integer> config = new ArrayList<Integer>();
			for (String s : numStrings)
			{
				config.add(Integer.parseInt(s));
			}
			count += possibilities(splits[0], config);
		}
		System.out.println(count);
	}
}
