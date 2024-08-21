package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsPart1 {
	
	//Stores its own location as well as the left and right location
	public static class Map{
		
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
	}
	
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day8\\input.txt");
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
		int count = 0;
		Map curLoc = maps.get("AAA"); //start at AAA
		while (! (curLoc.getLocation().equals("ZZZ"))) //Until you get to ZZZ
		{
			System.out.println(curLoc);
			String next;
			//Go to next location
			if (instructions.charAt(count % instructions.length()) == 'L')
			{
				next = curLoc.getLeft();
			}
			else
			{
				next = curLoc.getRight();
			}
			curLoc = maps.get(next);
			count ++;
		}
		System.out.println(count);
	}
}
