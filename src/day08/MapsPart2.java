package day08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsPart2 {
	
	//Stores its own location as well as the left and right location
	public static class Map
	{
		
		private String location;
		private String left;
		private String right;
		
		public String getLocation()
		{
			return location;
		}
		
		public String getLeft()
		{
			return left;
		}
		
		public String getRight()
		{
			return right;
		}
		
		public Map(String line)
		{
			this(line.substring(0, 3), line.substring(7, 10), line.substring(12, 15));
		}
		
		public Map(String loc, String l, String r)
		{
			location = loc;
			left = l;
			right = r;
		}
		
		public String toString()
		{
			return location + " = (" + left + ", " + right + ")";
		}
		
		public boolean equals(Map m)
		{
			if (m == null)
			{
				return false;
			}
			
			return (this.location == m.getLocation());
		}
	}
	
	public static class Ghost
	{
		private Map initialMap;
		private Map currentMap;
		private Map firstZMap;
		
		private long stepsToFirstZ;
		private long cycleLength;
		private List<Long> stopSteps;
		private List<Map> stops;
		
		public Ghost()
		{
			
		}
		
		public Ghost(Map m)
		{
			initialMap = m;
			currentMap = m;
			firstZMap = null;
			
			stepsToFirstZ = -1;
			cycleLength = -1;
			stopSteps = new ArrayList<Long>();
			stops = new ArrayList<Map>();
		}
		
		public Map getInitialMap()
		{
			return initialMap;
		}
		
		public Map getCurrentMap()
		{
			return currentMap;
		}
		
		public Map getFirstZMap()
		{
			return firstZMap;
		}
		
		public long getOffset()
		{
			return stepsToFirstZ;
		}
		
		public long getCycleLength()
		{
			return cycleLength;
		}
		
		public List<Long> getStopSteps()
		{
			return stopSteps;
		}
		
		public List<Map> getStops()
		{
			return stops;
		}
		
		public void setStepsToFirstZ(long s)
		{
			stepsToFirstZ = s;
		}
		
		public void setCycleLength(long c)
		{
			cycleLength = c;
		}
		
		public void setCurrentMap(Map m)
		{
			currentMap = m;
		}
		
		public void setFirstZMap(Map m)
		{
			firstZMap = m;
		}
		
		public void addStop(long step, Map m)
		{
			stopSteps.add(step);
			stops.add(m);
		}
		
	}
		
	
	public static long gcd(long a, long b)
	{
		long big = Math.max(Math.abs(a), Math.abs(b));
		long small = Math.min(Math.abs(a), Math.abs(b));
		if (small == 0)
		{
			return big;
		}
		return gcd(big % small, small);
	}
	
	public static long gcd(long[] nums)
	{
		long gcd = nums[0];
		for (long l : nums)
		{
			gcd = gcd(l, gcd);
		}
		return gcd;
	}
	
	public static long lcm(long a, long b)
	{
		return a * b / gcd(a, b);
	}
	
	public static long lcm(long[] nums)
	{
		long lcm = nums[0];
		for (long l : nums)
		{
			lcm = lcm(l, lcm);
		}
		return lcm;
	}
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day08\\testFileB.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		String instructions = br.readLine();
		br.readLine();
		HashMap<String, Map> maps = new HashMap<String, Map>();
		//fill hashmap with the maps
		while((line = br.readLine()) != null)
		{
			Map newMap = new Map(line);
			maps.put(newMap.getLocation(), newMap);
		}
		//Create a Ghost at each location
		List<Ghost> ghosts = new ArrayList<Ghost>();
		for (String loc : maps.keySet())
		{
			if (loc.substring(2).equals("A"))
			{
				Map m = maps.get(loc);
				ghosts.add(new Ghost(m));
			}
		}
		int count = 1; //number of map moves
		int numCyclesCounted = 0; //number of maps that completed a full loop.  
		boolean testVar = false;
		while (numCyclesCounted < ghosts.size()) //Until all ghosts have recorded a full loop
		{
			char direction = instructions.charAt(count % instructions.length());
			System.out.println("Turning " + direction + ", count is " + count);
			for (int i = 0; i < ghosts.size(); i++)
			{
				//Advance each ghost to the next map
				Ghost g = ghosts.get(i);
				Map next;
				if (direction == 'L')
				{
					next = maps.get(g.getCurrentMap().getLeft());
				}
				else
				{
					next = maps.get(g.getCurrentMap().getRight());
				}
				g.setCurrentMap(next);
				//Check if each ghost is at an XXZ location
				if (g.getCurrentMap().getLocation().substring(2).equals("Z"))
				{
					testVar = true;
					//If already completed a cycle, do nothing. 
					if (g.getCycleLength() == -1)
					{
						//If completed a loop, set cycle size and increment track count
						if ((g.getFirstZMap() != null) && (g.getFirstZMap().equals(g.getCurrentMap())) && (g.getCycleLength() == -1))
						{
							g.setCycleLength(count - g.getOffset());
							numCyclesCounted ++;
						}
						else
						{
							//System.out.println("Ghost " + i + " is visiting location " + g.getCurrentMap() + " at time \" + count; home is " + g.getFirstZMap());
							//If first location, set offset and store first Z loc. 
							if (g.getFirstZMap() == null)
							{
								g.setFirstZMap(g.getCurrentMap());
								g.setStepsToFirstZ(count);
							}
							//Whether or not first location, add map and count to the list
							g.addStop(count, g.getCurrentMap());
						}
					}
				}
			}
			count ++;
		}
		long[] cycles = new long[ghosts.size()];
		long[] offsets = new long[ghosts.size()];
		for (int i = 0; i < ghosts.size(); i++)
		{
			Ghost g = ghosts.get(i);
			cycles[i] = g.getCycleLength();
			offsets[i] = g.getOffset();
			System.out.println("Ghost " + i + " has a cycle length of " + g.getCycleLength() + ", an offset of " + g.getOffset() + ", and visited for the first time at " + g.getStopSteps());
		}
		System.out.println("GCD: " + gcd(cycles));
		System.out.println("LCM: " + lcm(cycles));
	}
}
