package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class EnginePart1 {
	
	static List<List<Character>> engine = new ArrayList<List<Character>>();
	
	public static boolean symbolSurrounds(int row, int s, int e)
	{
		for (int r = row - 1; r <= row + 1; r++)
		{
			List<Character> line = engine.get(r);
			for(int c = s - 1; c <= e; c++)
			{
				char x = line.get(c);
				if (!(Character.isDigit(x) || x == '.'))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day3\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int i = 0;
		while((line = br.readLine()) != null)
		{
			//add line to engine, padded with .
			engine.add(new ArrayList<Character>());
			engine.get(i).add('.');
			for(int j = 0; j < line.length(); j++)
			{
				engine.get(i).add(line.charAt(j));
			}
			engine.get(i).add('.');
			i++;
		}
		//add dot line to top and bottom
		List<Character> dotLine = new ArrayList<Character>();
		for(i = 0; i < engine.get(0).size(); i++)
		{
			dotLine.add('.');
		}
		engine.add(0, dotLine);
		engine.add(dotLine);
		for(List<Character> l : engine)
		{
			for(Character c : l)
			{
				System.out.print(c);
			}
			System.out.println();
		}
		System.out.println("OK");
		int count = 0;
		int row = 0;
		int col = 0;
		int start = -1;
		int curVal = 0;
		for(List<Character> l : engine)
		{
			for(Character c:l)
			{
				//Check if we are at a number
				if(Character.isDigit(c))
				{
					//Start a new number, or add to old one
					if(start == -1)
						start = col;
					curVal = 10*curVal + Character.getNumericValue(c);
				}
				else
				{
					//End number, then add if surrounded by a symbol
					if(start != -1)
					{
						if(symbolSurrounds(row, start, col))
						{
							count += curVal;
						}
					}
					curVal = 0;
					start = -1;	
				}
				col ++;
			}
			col = 0;
			row ++;
		}
		System.out.println(count);
	}
}
