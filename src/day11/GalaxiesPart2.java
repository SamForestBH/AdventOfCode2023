package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GalaxiesPart2 {
	
	
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day11\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<String> spaceStrings = new ArrayList<String>();
		while((line = br.readLine()) != null)
		{
			spaceStrings.add(line);
		}
		//Track dimensions, and empty rows. 
		int numRows = spaceStrings.size();
		int numCols = spaceStrings.get(0).length();
		List<Integer> emptyRows = new ArrayList<Integer>();
		List<Integer> emptyCols = new ArrayList<Integer>();
		//Fill all columns as empty, to remove later
		for(int i = 0; i < numCols; i++)
		{
			emptyCols.add(i);
		}
		List<int[]> galaxies = new ArrayList<int[]>();
		int row = 0;
		for (String s : spaceStrings)
		{
			System.out.println(s);
			int offset = 0;
			//If there is no galaxy, row is empty
			if (! s.contains("#"))
			{
				emptyRows.add(row);
			}
			String strip = s;
			//For each galaxy, add to galaxy list and remove from empty column. 
			while(strip.contains("#"))
			{
				offset = offset + strip.indexOf("#") + 1;
				strip = s.substring(offset);
				int[] temp = {row, offset - 1};
				galaxies.add(temp);
				emptyCols.remove(Integer.valueOf(offset - 1));
			}
			row++;
		}
		int i = 0;
		for (int[] g : galaxies)
		{
			//System.out.print(galaxies.get(i)[0] + ", " + galaxies.get(i)[1] + "->");
			int r = g[0]; //initial row
			for (int er : emptyRows)
			{
				System.out.print(er + "; ");
				if (r > er)
				{
					galaxies.get(i)[0] ++;
				}
			}
			System.out.println();
			int c = g[1]; //initial col
			for (int ec : emptyCols)
			{
				System.out.print(ec + "; ");
				if (c > ec)
				{
					galaxies.get(i)[1] ++;
				}
			}
			System.out.println();
			//System.out.println(galaxies.get(i)[0] + ", " + galaxies.get(i)[1]);
			i++;
		}
		int totalDistance = 0;
		int numAdds = 0;
		for (int g1 = 0; g1 < galaxies.size(); g1++)
		{
			for (int g2 = g1 + 1; g2 < galaxies.size(); g2++)
			{
				numAdds ++;
				totalDistance += Math.abs(galaxies.get(g2)[0] - galaxies.get(g1)[0]) + Math.abs(galaxies.get(g2)[1] - galaxies.get(g1)[1]); 
			}
		}
		System.out.println(numAdds);
		System.out.println(totalDistance);
	}
}
