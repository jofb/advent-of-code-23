import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Runner {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("dayfive.txt"));
		Day day = new DayFivePartTwo();
		day.run(in);
	}
}

interface Day {
	public void run(Scanner in);
}
