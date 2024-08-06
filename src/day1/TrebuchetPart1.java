package day1;

import java.io.*;

public class TrebuchetPart1 {

	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day1\\input.txt");
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

}
