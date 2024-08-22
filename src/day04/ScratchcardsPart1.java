package day04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ScratchcardsPart1 {
	
	public static class Scratchcard
	{
		private int cardNum;
		private List<Integer> winners;
		private List<Integer> numbers;
		
		public Scratchcard()
		{
			cardNum = 0;
			winners = new ArrayList<Integer>();
			numbers = new ArrayList<Integer>();
		}
		
		public Scratchcard(int c, List<Integer> w, List<Integer> n)
		{
			cardNum = c;
			winners = w;
			numbers = n;
		}
		
		public Scratchcard(String line)
		{
			String[] splitA = line.split("\\||:");
			String cn = splitA[0].split("\\s")[splitA[0].split("\\s").length - 1];
			cardNum = Integer.parseInt(cn);
			String[] winSplit = splitA[1].split("\\s");
			winners = new ArrayList<Integer>();
			numbers = new ArrayList<Integer>();
			for (String s : winSplit)
			{
				System.out.println("Adding: " + s);
				if (!(s.isEmpty()))
					winners.add(Integer.parseInt(s));
			}
			String[] numSplit = splitA[2].split("\\s");
			for (String s : numSplit)
			{
				if (!(s.isEmpty()))
					numbers.add(Integer.parseInt(s));
			}
		}
		
		public int getCardNum()
		{
			return cardNum;
		}
		
		public List<Integer> getWinners()
		{
			return winners;
		}
		
		public List<Integer> getNumbers()
		{
			return numbers;
		}
		
		public int countWinners()
		{
			int count = 0;
			for (Integer n : numbers)
			{
				if (winners.contains(n))
				{
					count ++;
				}
			}
			return count;
		}
		
		public int getScore()
		{
			if (countWinners() == 0)
			{
				return 0;
			}
			return (int) Math.pow(2,countWinners() - 1);
		}
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day04\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
		while((line = br.readLine()) != null)
		{
			Scratchcard sc = new Scratchcard(line);
			count += sc.getScore();
		}
		System.out.println(count);
	}
}
