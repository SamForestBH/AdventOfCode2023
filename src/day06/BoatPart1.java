package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class BoatPart1 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day06\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
		//Takes input file and creates time and distance array. 
		line = br.readLine();
		String[] tempa = line.split("\\s+");
		String[] stimes = Arrays.copyOfRange(tempa, 1, tempa.length);
		long[] times = new long[stimes.length];
		for(int i = 0; i < stimes.length; i++)
		{
			times[i] = Long.parseLong(stimes[i]);
		}
		line = br.readLine();
		tempa = line.split("\\s+");
		String[] sdistances = Arrays.copyOfRange(tempa, 1, tempa.length);
		long[] distances = new long[stimes.length];
		for(int i = 0; i < sdistances.length; i++)
		{
			distances[i] = Long.parseLong(sdistances[i]);
		}
		//Using quadratic formula, computes max and min times. 
		long product = 1;
		long[] wincounts = new long[times.length];
		for (int i = 0; i < times.length; i++)
		{
			long t1 = (long) Math.max(0,  Math.ceil((times[i] - Math.sqrt(times[i]*times[i] - 4*distances[i]))/2));
			//Offset if min time is a tie
			if (t1 * (times[i] - t1) == distances[i])
			{
				t1 ++;
			}
			//Offset if max time is a tie
			long t2 = (long) Math.min(times[i],  Math.floor((times[i] + Math.sqrt(times[i]*times[i] - 4*distances[i]))/2));
			if (t2 * (times[i] - t2) == distances[i])
			{
				t2 --;
			}
			System.out.println("Win by pressing between " + t1 + " and " + t2 + " for a total of " + (t2-t1) + " ways to win. ");
			product *= (t2 - t1 + 1);
		}
		System.out.println("Win product: " + product);
	}
}
