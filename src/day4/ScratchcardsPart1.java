package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
			Pattern nums = Pattern.compile("\\d+");
			
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
			return (int) Math.pow(2,countWinners());
		}
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day3\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		while((line = br.readLine()) != null)
		{
			
		}
	}
}
