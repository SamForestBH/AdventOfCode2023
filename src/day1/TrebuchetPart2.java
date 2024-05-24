package day1;

import java.io.*;

public class TrebuchetPart2 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("src\\day1\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		int count = 0;
		while((line = br.readLine()) != null)
		{
			int first = -1;
			int last = -1;
			//Read forwards looking for int
			int i = 0;
			while (first == -1)
			{
				Character c = line.charAt(i);
				if (Character.isDigit(c))
					first = Character.getNumericValue(c);
				else if (numAt(line, i) != -1)
					first = numAt(line, i);
				else
					i++;
			}
			//Read backwards looking for int
			i = line.length() - 1;
			while(last == -1)
			{
				Character c = line.charAt(i);
				if (Character.isDigit(c))
					last = Character.getNumericValue(c);
				else if (numAt(line, i) != -1)
					last = numAt(line, i);
				else
					i--;
			}
			//Add to count, then reset
			count += first * 10 + last;
			first = -1;
			last = -1;
		}
		System.out.println(count);
		br.close();
	}
	
	//Returns an int if the substring of line beginning at index i spells a number. Otherwise, returns -1. 
	public static int numAt(String line, int i)
	{
		//Confirms in target range
		if (i < 0 || i >= line.length())
			return -1;
		//Checks length three numbers
		if (line.length() - i >= 3)
		{
			if (line.substring(i, i+3).equals("one"))
				return 1;
			if (line.substring(i, i+3).equals("two"))
				return 2;
			if (line.substring(i, i+3).equals("six"))
				return 6;
		}
		//Checks length four numbers
		if (line.length() - i >= 4)
		{
			if (line.substring(i, i+4).equals("four"))
				return 4;
			if (line.substring(i, i+4).equals("five"))
				return 5;
			if (line.substring(i, i+4).equals("nine"))
				return 9;
			if (line.substring(i, i+4).equals("zero"))
				return 0;
		}
		//Checks length five numbers
		if (line.length() - i >= 5)
		{
			if (line.substring(i, i+5).equals("three"))
				return 3;
			if (line.substring(i, i+5).equals("seven"))
				return 7;
			if (line.substring(i, i+5).equals("eight"))
				return 8;
		}
		//Failure
		return -1;
	}

}
