package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class BoatPart2 {

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
		String stime = "";
		for(int i = 0; i < stimes.length; i++)
		{
			stime += stimes[i];
		}
		long time = Long.parseLong(stime);
		line = br.readLine();
		tempa = line.split("\\s+");
		String[] sdistances = Arrays.copyOfRange(tempa, 1, tempa.length);
		String sdistance = "";
		for(int i = 0; i < sdistances.length; i++)
		{
			sdistance += sdistances[i];
		}
		long distance = Long.parseLong(sdistance);
		//Using quadratic formula, computes max and min times. 
		long t1 = (long) Math.max(0,  Math.ceil((time - Math.sqrt(time*time - 4*distance))/2));
		//Offset if min time is a tie
		if (t1 * (time - t1) == distance)
		{
			t1 ++;
		}
		//Offset if max time is a tie
		long t2 = (long) Math.min(time,  Math.floor((time + Math.sqrt(time*time - 4*distance))/2));
		if (t2 * (time - t2) == distance)
		{
			t2 --;
		}
		System.out.println("Win by pressing between " + t1 + " and " + t2 + " for a total of " + (t2-t1 + 1) + " ways to win. ");
	}
}
