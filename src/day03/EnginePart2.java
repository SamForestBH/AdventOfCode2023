package day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class EnginePart2 {
	
	static List<List<Character>> engine = new ArrayList<List<Character>>();
	
	//Return true if the string at row, from s to e, has a symbol surrounding it. 
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
	
	//Returns the integer whose value is located in line at index i. 
	public static int numContains(List<Character> line, int i)
	{
		int start = i;
		while(Character.isDigit(line.get(start)))
		{
			start --;
		}
		start ++;
		int end = i;
		while(Character.isDigit(line.get(end)))
		{
			end ++;
		}
		end --;
		int value = 0;
		for(int j = start; j <= end; j++)
		{
			value = 10*value + Character.getNumericValue(line.get(j));
		}
		return value;
	}
	
	//Returns gear ratio if the number is a gear (surrounded by exactly 2 numbers)
	//Otherwise, returns -1
	public static int gearRatio(int row, int i)
	{
		int nums = 0;
		int ratio = 1;
		//Digit above is number means one number, otherwise check for 2. 
		if (Character.isDigit(engine.get(row-1).get(i)))
		{
			nums++;
			ratio *= numContains(engine.get(row-1), i);
		}
		else
		{
			if (Character.isDigit(engine.get(row-1).get(i-1)))
			{
				nums ++;
				ratio *= numContains(engine.get(row-1), i-1);
			}
			if (Character.isDigit(engine.get(row-1).get(i+1)))
			{
				nums ++;
				ratio *= numContains(engine.get(row-1), i+1);
			}
		}
		//Same with below
		if (Character.isDigit(engine.get(row+1).get(i)))
		{
			nums++;
			ratio *= numContains(engine.get(row+1), i);
		}
		else
		{
			if (Character.isDigit(engine.get(row+1).get(i-1)))
			{
				nums ++;
				ratio *= numContains(engine.get(row+1), i-1);
			}
			if (Character.isDigit(engine.get(row+1).get(i+1)))
			{
				nums ++;
				ratio *= numContains(engine.get(row+1), i+1);
			}
		}
		//Check left and right
		if (Character.isDigit(engine.get(row).get(i-1)))
		{
			nums ++;
			ratio *= numContains(engine.get(row), i-1);
		}
		if (Character.isDigit(engine.get(row).get(i+1)))
		{
			nums ++;
			ratio *= numContains(engine.get(row), i+1);
		}
		//Only return ratio if is a gear. 
		if (nums == 2)
			return ratio;
		return -1;
	}

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day03\\input.txt");
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
		for(List<Character> l : engine)
		{
			for(Character c:l)
			{
				//Check if we are at a *
				if(c.equals('*'))
				{
					int ratio = gearRatio(row, col);
					if (ratio != -1)
						count += ratio;
				}
				col ++;
			}
			row ++;
			col = 0;
		}
		System.out.println(count);
	}
}