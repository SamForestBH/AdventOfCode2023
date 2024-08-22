package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipesPart1 {
	
	public static class Maze
	{
		private List<String> pipes;
		
		//(row, col) counting down and left
		private int[] curPos;
		private int[] lastPos;
		
		public void moveTail()
		{
			lastPos[0] = curPos[0];
			lastPos[1] = curPos[1];
		}
		
		public String pipeAt(int i, int j)
		{
			return pipes.get(i).substring(j, j+1);
		}
		
		public String pipeAt(int[] pos)
		{
			return pipes.get(pos[0]).substring(pos[1], pos[1]+1);
		}
		
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
			return this.pipeAt(curPos[0], curPos[1] - 1);
		}
		
		public String rightPipe()
		{
			if (curPos[1] == pipes.get(1).length() - 1)
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0], curPos[1] + 1);
		}
		
		public String upPipe()
		{
			if (curPos[0] == 0)
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0] - 1, curPos[1]);
		}
		
		public String downPipe()
		{
			if (curPos[0] == (pipes.size() - 1))
			{
				return "Outside of Map";
			}
			return this.pipeAt(curPos[0] + 1, curPos[1]);
		}
		
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
		
		public void move()
		{
			//Look left
			if ((lastPos[1] != curPos[1] - 1) && this.connectsLeft())
			{
				this.moveTail();
				curPos[1] -= 1;
			}
			//Look right
			else if ((lastPos[1] != curPos[1] + 1) && this.connectsRight())
			{
				this.moveTail();
				curPos[1] += 1;
			}
			//Look down
			else if ((lastPos[0] != curPos[0] + 1) && this.connectsDown())
			{
				this.moveTail();
				curPos[0] += 1;
			}
			//Look up
			else if ((lastPos[0] != curPos[0] - 1) && this.connectsUp())
			{
				this.moveTail();
				curPos[0] -= 1;
			}
		}
		
		public long maxDis()
		{
			long count = 0;
			int[] startPos = {curPos[0], curPos[1]};
			do
			{
				move();	
				count ++;
			} while ((startPos[0] != curPos[0]) || (startPos[1] != curPos[1]));
			return count/2;
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
					int i = pipes.indexOf(s);
					int j = s.indexOf("S");
					curPos[0] = i;
					curPos[1] = j;
					//Choose last position as random location pointing to S. 
					if (this.connectsDown())
					{
						lastPos[0] = curPos[0] + 1;
						lastPos[1] = curPos[1];
					}
					else if (this.connectsUp())
					{
						lastPos[0] = curPos[0] - 1;
						lastPos[1] = curPos[1];
					}
					else if (this.connectsLeft())
					{
						lastPos[0] = curPos[0];
						lastPos[1] = curPos[1] - 1;
					}
					else if (this.connectsRight())
					{
						lastPos[0] = curPos[0];
						lastPos[1] = curPos[1] + 1;
					}
					break;
				}
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
	}
}
