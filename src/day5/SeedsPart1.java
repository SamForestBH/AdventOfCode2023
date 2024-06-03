package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SeedsPart1 {
	
	public static class Map
	{
		private long desStart;
		private long ranStart;
		private long ranLen;
		
		public long getDesStart()
		{
			return desStart;
		}
		
		public long getRanStart()
		{
			return ranStart;
		}
		
		public long getRanEnd()
		{
			return ranStart + ranLen;
		}
		
		public long getRanLen()
		{
			return ranLen;
		}
		
		public long getOffset()
		{
			return desStart - ranStart;
		}
		
		public Map(long ds, long rs, long rl)
		{
			desStart = ds;
			ranStart = rs;
			ranLen = rl;
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
				if( input >= m.getRanStart() && input < m.getRanEnd())
				{
					return input + m.getOffset();
				}
			}
			return input;
		}
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day5\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
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
		List<Mapping> allMaps = new ArrayList<Mapping>();
		List<String> newMaps = new ArrayList<String>();
		System.out.println("A:" + seeds[0]);
		while((line = br.readLine()) != null)
		{
			if(line.equals(""))
			{
				allMaps.add(new Mapping(newMaps));
				newMaps = new ArrayList<String>();
				br.readLine();
			}
			else
			{
				newMaps.add(line);
			}
		}
		allMaps.add(new Mapping(newMaps));
		for(Mapping m : allMaps)
		{
			for(int i = 0; i < seeds.length; i++)
			{
				System.out.print(seeds[i] + " => ");
				seeds[i] = m.output(seeds[i]);
				System.out.print(seeds[i] + "; ");
			}
			System.out.println();
		}
		long min = seeds[0];
		for (long seed : seeds)
		{
			if (seed < min)
				min = seed;
		}
		System.out.println(min);
	}
}
