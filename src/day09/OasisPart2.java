package day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OasisPart2 {
	
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day9\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		long count = 0;
		while((line = br.readLine()) != null)
		{
			String[] tempa = line.split("\\s+");
			long[] vals = new long[tempa.length];
			for(int i = 0; i < tempa.length; i++)
			{
				vals[i] = Long.parseLong(tempa[i]);
			}
			boolean zeros = false;
			System.out.println("New line");
			int newRows = 0;
			int newStuff = 0;
			while (!zeros)
			{
				zeros = true;
				newStuff += (vals[0] * Math.pow(-1, newRows)); //Solution is alternating sum of lefthand column
				System.out.println("Newly added number is " + vals[0] * Math.pow(-1, newRows));
				System.out.println("NewStuff is now " + newStuff);
				long[] newVals = new long[vals.length - 1];
				for (int i = 0; i < newVals.length; i++)
				{					
					newVals[i] = vals[i+1] - vals[i];
					if (newVals[i] != 0)
					{
						zeros = false;
					}
				}
				vals = newVals;
				newRows++;
			}
			count += newStuff;
			System.out.println("NewStuff: " + newStuff + "; Count: " + count);
		}
		System.out.println(count);
	}

}
