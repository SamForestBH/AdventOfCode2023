package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipesPart2 {
	
	public static class Droplet
	{
		private int row;
		private int col;
		private String fill;
		
		public int getRow()
		{
			return row;
		}
		
		public int getCol()
		{
			return col;
		}
		
		public String getFill()
		{
			return fill;
		}
		
		public String getCapitalFill()
		{
			return Character.toString((char)((int)fill.charAt(0) - 32));
		}
		
		public Droplet(String f, int r, int c)
		{
			row = r;
			col = c;
			fill = f;
		}
	}
	
	public static class Maze
	{
		private List<String> pipes;
		
		//(row, col) counting down and left. Even numbers are pipes and odds are between pipes. 
		private int[] curPos;
		private int[] lastPos;
		private String[][] fullMap;
		
		private long loopLength;
		
		//Moves lastPos to curPos, and puts P along the (odd) path. 
		public void moveTail(int x, int y)
		{
			lastPos[0] = curPos[0];
			lastPos[1] = curPos[1];
			int i = curPos[0];
			int j = curPos[1];
			fullMap[i][j] = "P";
			fullMap[i + x][j + y] = "P";
		}
		
		//Returns pipe at specified location. 
		
		public String pipeAt(int i, int j)
		{
			return pipes.get(i/2).substring(j/2, j/2+1);
		}
		
		public String pipeAt(int[] pos)
		{
			return pipes.get(pos[0]/2).substring(pos[1]/2, pos[1]/2+1);
		}
		
		//Returns pipe at current or last location, or a specified direction
		
		public String curPipe()
		{
			return this.pipeAt(curPos);
		}
		
		public String lastPipe()
		{
			return this.pipeAt(lastPos);
		}
		
		public String leftPipe()
		{
			if (curPos[1] == 0)
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0], curPos[1] - 2);
		}
		
		public String rightPipe()
		{
			if (curPos[1] == (pipes.get(1).length() - 1) * 2)
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0], curPos[1] + 2);
		}
		
		public String upPipe()
		{
			if (curPos[0] == 0)
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0] - 2, curPos[1]);
		}
		
		public String downPipe()
		{
			if (curPos[0] == 2 * (pipes.size() - 1))
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0] + 2, curPos[1]);
		}
		
		//Checks to see if current pipe connects to pipe in a given direction
		
		public boolean connectsLeft()
		{
			return (("SJ7-".contains(this.curPipe())) && ("SFL-".contains(this.leftPipe())));
		}
		
		public boolean connectsRight()
		{
			return (("SFL-".contains(this.curPipe())) && ("SJ7-".contains(this.rightPipe())));
		}
		
		public boolean connectsDown()
		{
			return (("SF7|".contains(this.curPipe())) && ("SJL|".contains(this.downPipe())));
		}
		
		public boolean connectsUp()
		{
			return (("SJL|".contains(this.curPipe())) && ("SF7|".contains(this.upPipe())));
		}
		
		//Finds the next way to go (connection that isn't last location) and moves there. 
		public void move()
		{
			//Look left
			if ((lastPos[1] != curPos[1] - 2) && this.connectsLeft())
			{
				this.moveTail(0, -1);
				curPos[1] -= 2;
			}
			//Look right
			else if ((lastPos[1] != curPos[1] + 2) && this.connectsRight())
			{
				this.moveTail(0, 1);
				curPos[1] += 2;
			}
			//Look down
			else if ((lastPos[0] != curPos[0] + 2) && this.connectsDown())
			{
				this.moveTail(1, 0);
				curPos[0] += 2;
			}
			//Look up
			else if ((lastPos[0] != curPos[0] - 2) && this.connectsUp())
			{
				this.moveTail(-1, 0);
				curPos[0] -= 2;
			}
		}
		
		public int[] getCurPos()
		{
			return curPos;
		}
		
		public String[][] getFullMap()
		{
			return fullMap;
		}
		
		//Traverses the loop, then divides length by 2 for max distance
		public long maxDis()
		{
			long count = 0;
			int[] startPos = {curPos[0], curPos[1]};
			do
			{
				move();	
				count ++;
			} while ((startPos[0] != curPos[0]) || (startPos[1] != curPos[1]));
			loopLength = count;
			return count/2;
		}
		
		//Fills in regions and returns the size of the internal region. 
		public long findInternalArea()
		{
			long numPainted = 0;
			long toReturn = -1;
			numPainted = fillWithPaint("a", curPos[0] - 1, curPos[1] - 1);
			if (numPainted >= 0)
			{
				//System.out.println(numPainted);
				toReturn = numPainted;
			}
			numPainted = fillWithPaint("b", curPos[0] - 1, curPos[1] + 1);
			if (numPainted >= 0)
			{
				//System.out.println(numPainted);
				toReturn = numPainted;
			}
			numPainted = fillWithPaint("c", curPos[0]+ 1, curPos[1] - 1);
			if (numPainted >= 0)
			{
				//System.out.println(numPainted);
				toReturn = numPainted;
			}
			numPainted = fillWithPaint("d", curPos[0] + 1, curPos[1] + 1);
			if (numPainted >= 0)
			{
				//System.out.println(numPainted);
				toReturn = numPainted;
			}
			return toReturn;
		}
		
		//Recursively pours paint of num beginning at (row, col). Returns count of filled even spots, or negative with errors
		public long fillWithPaint(String fill, int row, int col)
		{
			long painted = 0;
			String capFill;
			List<Droplet> drops = new ArrayList<Droplet>();
			drops.add(new Droplet(fill, row, col));
			while (! drops.isEmpty())
			{
				Droplet d = drops.getFirst();
				drops.remove(0);
				row = d.getRow();
				col = d.getCol();
				fill = d.getFill();
				capFill = d.getCapitalFill();
				if ((row < 0) || (col < 0) || (row >= fullMap.length) || (col >= fullMap[0].length))
				{
					System.out.println(fill + row + col);
					return -1;
				}
				//Return -2 if region is filled with a different color of paint
				if ( (! fullMap[row][col].equals(fill)) && (! fullMap[row][col].equals(Character.toString((char)((int)fill.charAt(0) - 32)))) &&
						(!fullMap[row][col].equals(".")) && (!fullMap[row][col].equals("P")))
				{
					return -2;
				}
				//Return 0 if otherwise full (with paint or pipe)
				if (fullMap[row][col].equals("."))
				{
					if ((row % 2 == 0) && (col % 2 == 0))
					{
						fullMap[row][col] = capFill;
						painted ++;
					}
					else
					{
						fullMap[row][col] = fill;
					}
					drops.add(new Droplet(fill, row-1, col));
					drops.add(new Droplet(fill, row+1, col));
					drops.add(new Droplet(fill, row, col-1));
					drops.add(new Droplet(fill, row, col+1));
				}
			}
			return painted;
		}
		
		public Maze(List<String> pstring)
		{
			pipes = pstring;
			curPos = new int[2];
			lastPos = new int[2];
			//Hunt for start position
			for(String s : pipes)
			{
				if (s.contains("S"))
				{
					int i = 2 * pipes.indexOf(s);
					int j = 2 * s.indexOf("S");
					curPos[0] = i;
					curPos[1] = j;
					//Choose last position as arbitrary location pointing to S. 
					if (this.connectsDown())
					{
						lastPos[0] = curPos[0] + 2;
						lastPos[1] = curPos[1];
					}
					else if (this.connectsUp())
					{
						lastPos[0] = curPos[0] - 2;
						lastPos[1] = curPos[1];
					}
					else if (this.connectsLeft())
					{
						lastPos[0] = curPos[0];
						lastPos[1] = curPos[1] - 2;
					}
					else if (this.connectsRight())
					{
						lastPos[0] = curPos[0];
						lastPos[1] = curPos[1] + 2;
					}
					break;
				}
			}
			//Create String 2D array with evenxeven as pipes.
			fullMap = new String[2*(pipes.size()) - 1][2*(pipes.get(1).length()) - 1];
			int i = 0;
			for (String[] sa : fullMap)
			{
				int j = 0;
				for (String s : sa)
				{
					fullMap[i][j] = ".";
					j++;
				}
				i++;
			}
			fullMap[curPos[0]][curPos[1]] = "P";
			for (String[] sa : fullMap)
			{
				for (String s : sa)
				{
					System.out.print(s);
				}
				System.out.println();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		//Reads line from input file
		File input = new File("day10\\input.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		List<String> pstring = new ArrayList<String>();
		while((line = br.readLine()) != null)
		{
			pstring.add(line);
		}
		Maze maze = new Maze(pstring);
		System.out.println(maze.maxDis());
		for (String[] sa : maze.getFullMap())
		{
			for (String s : sa)
			{
				System.out.print(s);
			}
			System.out.println();
		}
		System.out.println(maze.findInternalArea());
		for (String[] sa : maze.getFullMap())
		{
			for (String s : sa)
			{
				System.out.print(s);
			}
			System.out.println();
		}
	}
}
