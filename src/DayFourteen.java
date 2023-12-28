import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayFourteen implements Day {

	@Override
	public void run(Scanner in) {

		List<String> lines = new ArrayList<String>();
		
		// first lets try row by row
		while(in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		
		for(int i = 0; i < lines.size() - 1; i++) {
			String line = lines.get(i);
			String nextLine = lines.get(i + 1);
		}
	}

}
