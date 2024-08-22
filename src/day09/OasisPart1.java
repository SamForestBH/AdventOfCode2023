package day09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OasisPart1 {
	
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day9\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		long count = 0;
		while((line = br.readLine()) != null)
		{
			//Fill vals with numbers
			String[] tempa = line.split("\\s+");
			long[] vals = new long[tempa.length];
			for(int i = 0; i < tempa.length; i++)
			{
				vals[i] = Long.parseLong(tempa[i]);
			}
			boolean zeros = false;
			//until the row is numbers
			while (!zeros)
			{
				zeros = true;
				count += vals[vals.length - 1]; //solution is simply sum of lefthand column
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
			}
		}
		System.out.println(count);
	}
}
