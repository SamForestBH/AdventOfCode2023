package day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SeedsPart2 {
	
	//A range of numbers to represent an item such as a seed or a location. Can be mapped into a list of ItemRanges. 
	public static class ItemRange
	{
		private long start;
		private long length;
		
		public long getStart()
		{
			return start;
		}
		
		public long getLength()
		{
			return length;
		}
		
		public long getEnd()
		{
			return start + length - 1;
		}
		
		//Sets the new start as specified without altering the end. 
		public void clipToStart(long newStart)
		{
			length = this.getEnd() - newStart + 1;
			start = newStart;
		}
		
		public ItemRange(long s, long l)
		{
			start = s;
			length = l;
		}
		
		public ItemRange(long s, long e, int flag)
		{
			start = s;
			length = e - s + 1;
		}
	}
	
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
		
		public long output(long input)
		{
			//System.out.println("Shifting " + input + " by " + this.getOffset());
			return input + this.getOffset();
		}
		
		//True iff any part of ir is within the map. 
		public boolean canMap(ItemRange ir)
		{
			return (ir.getEnd() >= this.getRanStart() && ir.getStart() <= this.getRanEnd());
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
		
		//returns an array of longs from a string containing three longs; used for map constructor directly from a string. 
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
	//Can map a single long to its longs output or an ItemRange or an ItemRange to a new list of ItemRange outputs. 
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
		
		//Checks each map to see which one maps input; if none, leave as is. 
		public long output(long input)
		{
			for(Map m : maps)
			{
				if( input >= m.getRanStart() && input < m.getRanEnd())
				{
					return m.output(input);
				}
			}
			return input;
		}
		
		//Takes an ItemRange and pushes it through the mapping, returning a list of ItemRange objects as the output. 
		public List<ItemRange> output(ItemRange range)
		{
			List<ItemRange> newStuff = new ArrayList<ItemRange>();
			List<Long> unmappedRange = new ArrayList<Long>(); //start and end of unmapped ranges. 
			unmappedRange.add(range.getStart());
			unmappedRange.add(range.getEnd());
			for(Map m : maps)
			{
				System.out.println("Map maps " + m.getRanStart() + " to " + m.getDesStart() + ", length " + m.getLength() + ". ");
				if (m.canMap(range))
				{
					long newStart = Math.max(range.getStart(), m.getRanStart());
					long newEnd = Math.min(range.getEnd(), m.getRanEnd());
					//Add start and end padding the mapped region, unless mapped region hits endpoints
					if (unmappedRange.contains(newStart))
					{
						unmappedRange.remove(newStart);
					}
					else
					{
						unmappedRange.add(newStart - 1);
					}
					if (unmappedRange.contains(newEnd))
					{
						unmappedRange.remove(newEnd);
					}
					else
					{
						unmappedRange.add(newEnd + 1);
					}
					//Adds the mapped region to the newStuff
					System.out.println("Shifting range " + newStart + " to " + newEnd + " to new coordinates " + m.output(newStart) + " to " + m.output(newEnd) + ". ");
					newStuff.add(new ItemRange(m.output(newStart), m.output(newEnd), 1));
				}
			}
			Collections.sort(unmappedRange);
			System.out.println(unmappedRange);
			//Adds all parts of range that were not mapped as new ItemRange objects. 
			for(int i = 0; i < unmappedRange.size(); i += 2)
			{
				newStuff.add(new ItemRange(unmappedRange.get(i), unmappedRange.get(i + 1),1));
			}
			return newStuff;
		}
		
		//Takes a list of ItemRange objects and pushes them all through the mapping using the above function. 
		//Returns a single list appending all ranges. 
		public List<ItemRange> output(List<ItemRange> list)
		{
			//System.out.println("Processing range list; first range from " + list.getFirst().getStart() + " to " + list.getFirst().getEnd());
			List<ItemRange> outList = new ArrayList<ItemRange>();
			for(ItemRange ir : list)
			{
				outList.addAll(this.output(ir));
			}
			return outList;
		}
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day05\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
		//Makes an ItemRange of the initial seeds. 
		line = br.readLine();
		br.readLine();
		br.readLine();
		String[] splits = line.split("\\s");
		List<ItemRange> seeds = new ArrayList<ItemRange>();
		for(int i = 1; i < splits.length - 1; i+= 2)
		{
			seeds.add(new ItemRange(Long.parseLong(splits[i]),Long.parseLong(splits[i+1])));
		}
		List<Mapping> allMaps = new ArrayList<Mapping>(); //A List of mappings, each from one type to the next. 
		List<String> newMaps = new ArrayList<String>(); //The string that will convert to the new map. 
		System.out.println("A:" + seeds.get(0).getStart());
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
//		for (ItemRange seed : seeds)
//		{
//			System.out.println("Range from " + seed.getStart() + " to " + seed.getEnd() + ". ");
//		}
//		seeds = allMaps.getFirst().output(seeds);
		for (ItemRange seed : seeds)
		{
			System.out.println("Range from " + seed.getStart() + " to " + seed.getEnd() + ". ");
		}
		for(Mapping m : allMaps) //Iterate through the mappings and push the objects through. 
		{
			seeds = m.output(seeds);
			for (ItemRange seed : seeds)
			{
				System.out.println("Range from " + seed.getStart() + " to " + seed.getEnd() + ". ");
			}
		}
		long min = seeds.getFirst().getStart(); //Find the smallest location
		for (ItemRange seed : seeds)
		{
			if (seed.getStart() < min)
				min = seed.getStart();
		}
		System.out.println(min);
	}
}
