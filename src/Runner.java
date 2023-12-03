import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Runner {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("daytwo.txt"));
		Day day = new DayTwoPartTwo();
		day.run(in);
	}
}

interface Day {
	public void run(Scanner in);
}
