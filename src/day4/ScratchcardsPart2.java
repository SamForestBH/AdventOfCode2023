package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ScratchcardsPart2 {
	
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
		File input = new File("src\\day4\\input.txt");
		BufferedReader br1 = new BufferedReader(new FileReader(input));
		String line;
		int numLines = 0;
		while((line = br1.readLine()) != null)
		{
			numLines ++;
		}
		int count = 0;
		int[] copies = new int[numLines + 1];
		Arrays.fill(copies, 1);
		BufferedReader br = new BufferedReader(new FileReader(input));
		while((line = br.readLine()) != null)
		{
			Scratchcard sc = new Scratchcard(line);
			int cardNum = sc.getCardNum();
			count += copies[cardNum];
			System.out.println("Card " + cardNum + " has " + copies[cardNum] + " copies and the count is " + count + ". ");
			int newCards = sc.countWinners();
			for (int i = cardNum + 1; i <= cardNum + newCards; i++)
			{
				copies[i] += copies[cardNum];
			}
		}
		System.out.println(count);
	}
}
