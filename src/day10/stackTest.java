package day10;

public class stackTest {

	
	private static int test;
	
	public static void recurse()
	{
		test ++;
		System.out.println(test);
		recurse();
	}
	
	public static void main(String args[])
	{
		recurse();
	}
}
