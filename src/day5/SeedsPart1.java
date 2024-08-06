package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SeedsPart1 {
	
	//Maps a range of numbers beginning at ranStart and continuing for length numbers. 
	public static class Map
	{
		private long desStart;
		private long ranStart;
		private long length;
		
		public long getDesStart()
		{
			return desStart;
		}
		
		public long getDesEnd()
		{
			return desStart + length;
		}
		
		public long getRanStart()
		{
			return ranStart;
		}
		
		public long getRanEnd()
		{
			return ranStart + length - 1;
		}
		
		public long getLength()
		{
			return length;
		}
		
		public long getOffset()
		{
			return desStart - ranStart;
		}
		
		public Map(long ds, long rs, long rl)
		{
			desStart = ds;
			ranStart = rs;
			length = rl;
		}
		
		public Map(long[] drr)
		{
			this(drr[0], drr[1], drr[2]);
		}
		
		public Map(String s)
		{
			this(mapSplitter(s));
		}
		
		private static long[] mapSplitter(String s)
		{
			String[] sNums = s.split("\\s");
			long[] drr = new long[3];
			int i = 0;
			for (String num : sNums)
			{
				drr[i] = Long.parseLong(num);
				i++;
			}
			return drr;
		}
	}
	
	//A list of maps that together map the entire range. 
	public static class Mapping
	{
		private List<Map> maps;
		
		public Mapping(List<Map> m, long ignore)
		{
			maps = m;
		}
		
		public Mapping(List<String> lines)
		{
			maps = new ArrayList<Map>();
			for (String l : lines)
			{
				maps.add(new Map(l));
			}
		}
		
		public long output(long input)
		{
			for(Map m : maps)
			{
				if( input >= m.getRanStart() && input <= m.getRanEnd())
				{
					return input + m.getOffset();
				}
			}
			return input;
		}
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day5\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
		//Makes an array of the initial seeds. 
		line = br.readLine();
		br.readLine();
		br.readLine();
		String[] splits = line.split("\\s");
		long[] seeds = new long[splits.length - 1];
		for(int i = 0; i < splits.length - 1; i++)
		{
			seeds[i] = Long.parseLong(splits[i+1]);
			System.out.println(seeds[i]);
		}
		List<Mapping> allMaps = new ArrayList<Mapping>(); //A List of mappings, each from one type to the next. 
		List<String> newMaps = new ArrayList<String>(); //The string that will convert to the new map. 
		System.out.println("A:" + seeds[0]);
		while((line = br.readLine()) != null)
		{
			if(line.equals("")) //End of current mapping, so add to the map list. 
			{
				allMaps.add(new Mapping(newMaps));
				newMaps = new ArrayList<String>();
				br.readLine();
			}
			else //Add another map to the mapping
			{
				newMaps.add(line);
			}
		}
		allMaps.add(new Mapping(newMaps)); //Add the last mapping
		for(Mapping m : allMaps) //Iterate through the mappings and push the objects through. 
		{
			for(int i = 0; i < seeds.length; i++)
			{
				System.out.print(seeds[i] + " => ");
				seeds[i] = m.output(seeds[i]);
				System.out.print(seeds[i] + "; ");
			}
			System.out.println();
		}
		long min = seeds[0]; //Find the smallest location
		for (long seed : seeds)
		{
			if (seed < min)
				min = seed;
		}
		System.out.println(min);
	}
}
