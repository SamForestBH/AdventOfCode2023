package day02;

import java.io.*;
import java.math.*;

public class CubesPart1 {
	
	//Max counts
	static int maxRed = 12; 
	static int maxGreen = 13;
	static int maxBlue = 14;
	
	//Color, first letter, and length
	public static class Color{
		private int length;
		private String name;
		private char fl; //First letter
		
		public Color(String n) {
			name = n;
			fl = name.charAt(0);
			length = name.length();
		}
		
		public String getName()
		{
			return name;
		}
		
		public char getFirst()
		{
			return fl;
		}
		
		public int getLength()
		{
			return length;
		}
	}
	
	//List of colors. 
	public static Color[] colorlist = {new Color("red"), new Color("blue"), new Color("green")};
	
	//Based on first letter, retrieves color name. 
	public static String getName(char c)
	{
		for(Color color : colorlist)
		{
			if (color.getFirst() == c)
			{
				return color.getName();
			}
		}
		return "not in list";
	}
	
	//Based on first letter, retrieves color. 
	public static Color getColor(char c)
	{
		for(Color color : colorlist)
		{
			if (color.getFirst() == c)
			{
				return color;
			}
		}
		return null;
	}
	
	//One instance of drawing cubes. 
	public static class Pull{
		private int red = 0;
		private int green = 0;
		private int blue = 0;
		
		public Pull()
		{
			
		}
		
		public Pull(int r, int g, int b)
		{
			red = r;
			green = g;
			blue = b;
		}
		
		//Add with a string. 
		public boolean add(String color, int k)
		{
			if (color == "red")
			{
				red += k;
				return true;
			}
			if (color == "blue")
			{
				blue += k;
				return true;
			}
			if (color == "green")
			{
				green += k;
				return true;
			}
			return false;
		}
		
		//Add with a color. 
		public boolean add(Color color, int k)
		{
			return add(color.getName(), k);
		}
		
		//Check if the pull is legal. 
		public boolean isLegal()
		{
			if (red <= maxRed && green <= maxGreen && blue <= maxBlue)
				return true;
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day02\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		//Increment legal games and game count. 
		int legalCount = 0;
		int gamenum = 1;
		while((line = br.readLine())!= null)
		{
			line = line + ".";
			//Skip past game X
			int i = (int) (8 + Math.floor(Math.log10(gamenum)));
			while(i < line.length())
			{
				Pull pull = new Pull();
				while (true)
				{
					//Count of cube
					int j = i;
					while (Character.isDigit(line.charAt(i)))
					{
						i++;
					}
					System.out.println(line.substring(j, i));
					int count = Integer.parseInt(line.substring(j, i));
					i++;
					//Color of cube
					Color color = getColor(line.charAt(i));
					System.out.println(color.getName());
					//Add cube to pull
					pull.add(color, count);
					i += color.getLength();
					System.out.println(line.charAt(i));
					i += 2;
					//More cubes in pull? If not, move on. 
					if (line.charAt(i - 2) != ',' )
						break;
				}
				//If illegal, throw out. 
				if (!pull.isLegal())
				{
					System.out.println("Illegal Game removed: " + gamenum);
					break;
				}
				//If done, add to count. 
				System.out.println("Pull legal, resetting");
				if (line.charAt(i - 2) == '.')
				{
					legalCount += gamenum;
					System.out.println("Legal Game Added: " + gamenum);
				}
			}
			
			gamenum++;
		}
		System.out.println("Sum of legal games: " + legalCount);
		br.close();
	}
}
