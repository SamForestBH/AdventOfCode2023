package day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardsPart1 {
	
	public static String[] rankOrder = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	
	//Stores a single String representing the rank; implements comparable to sort by the above rank order. 
	public static class Card implements Comparable<Card> {
		
		private String rank;
		
		public String getRank()
		{
			return rank;
		}
		
		public Card(String r)
		{
			rank = r;
		}
		
		@Override
		public int compareTo(Card c) {
			return Arrays.asList(rankOrder).indexOf(this.getRank()) - Arrays.asList(rankOrder).indexOf(c.getRank());
		}
		
		@Override
		public boolean equals(Object c)
		{
			return (this.compareTo((Card) c) == 0);
		}

		@Override
		public String toString() {
			return rank;
		}
	}
	
	//Stores five card objects and one bet. Implements comparable to sort hands, low to high. 
	public static class Hand implements Comparable<Hand>{
		private Card[] cards;
		private long bet;
		
		public Card[] getCards()
		{
			return cards;
		}
		
		public long getBet()
		{
			return bet;
		}
		
		public Card getCard(int i)
		{
			return cards[i];
		}
		
		public Hand(Card[] c, long b)
		{
			cards = c;
			bet = b;
		}
		
		public Hand(String s)
		{
			String[] split = s.split("\\s+");
			cards = new Card[5];
			for(int i = 0; i < 5; i++)
			{
				cards[i] = new Card(split[0].substring(i, i+1));
			}
			bet = Long.parseLong(split[1]);
		}
		
		@Override
		public int compareTo(Hand that)
		{
			//Lists the counts for each unique card in this hand
			List<Card> thisHandUniques = new ArrayList<Card>();
			int[] thisCount = {0,0,0,0,0};
			for (Card c : this.getCards())
			{
				if (! thisHandUniques.contains(c))
				{
					thisHandUniques.add(c);
				}
				thisCount[thisHandUniques.indexOf(c)] ++;				
			}
			Arrays.sort(thisCount);
			//Lists the counts for each unique card in that hand
			List<Card> thatHandUniques = new ArrayList<Card>();
			int[] thatCount = {0,0,0,0,0};
			for (Card c : that.getCards())
			{
				if (! thatHandUniques.contains(c))
				{
					thatHandUniques.add(c);
				}
				thatCount[thatHandUniques.indexOf(c)] ++;				
			}
			Arrays.sort(thatCount);
			for (int i = 4; i >=0; i--)
			{
				System.out.println(thisCount[i] + ", " + thatCount[i]);
				if (thisCount[i] != thatCount[i])
				{
					System.out.println("Hand " + this + " comparing with hand " + that + ", unequal due to freq " + (5 - i));
					return thisCount[i] - thatCount[i];
				}
			}
			
			for (int i = 0; i < 5; i++)
			{
				if (! this.getCard(i).equals(that.getCard(i)))
				{
					return this.getCard(i).compareTo(that.getCard(i));
				}
			}
			return 0;
		}
		
		@Override
		public String toString() {
			String output = "";
			for(Card c : cards)
			{
				output += c;
			}
			return output;
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day7\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<Hand> hands = new ArrayList<Hand>();
		//Takes input file and creates a list of hands 
		while((line = br.readLine()) != null)
		{
			hands.add(new Hand(line));
		}
		Collections.sort(hands);
		long sum = 0;
		int i = 1;
		for(Hand h : hands)
		{
			System.out.println("Hand: " + h + ", Bet: " + h.getBet());
			sum += i * h.getBet(); 
			i++;
		}
		System.out.println(sum);
	}
}
