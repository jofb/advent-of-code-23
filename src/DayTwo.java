import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DayTwoPartTwo implements Day {

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		HashMap<String, Integer> colours = new HashMap<String, Integer>();
		colours.put("red", 0);
		colours.put("green", 0);
		colours.put("blue", 0);
		
		while(in.hasNextLine()) {
			String input = in.nextLine();

			// parse the input
			String[] split = input.split(": ");

			Pattern p = Pattern.compile("[0-9]+ (red|green|blue)");
			Matcher m = p.matcher(split[1]);
			
			// loop over sets to find highest of each colour
			while(m.find()) {
				String[] set = m.group().split("(?![0-9]+) ");
				
				int num = Integer.parseInt(set[0]);
				String colour = set[1].trim();
				
				// compare to highest number
				if(num > colours.get(colour)) colours.put(colour, num);
			}
			
			int power = colours.get("red") * colours.get("green") * colours.get("blue");
			total += power;
			
			for(String key : colours.keySet()) {
				colours.put(key, 0);
			}
		}
		
		System.out.println(total);
	}
	
}

class DayTwo implements Day {
	
	final int RED_MAX = 12;
	final int GREEN_MAX = 13;
	final int BLUE_MAX = 14;
	
	HashMap<String, Integer> colours = new HashMap<String, Integer>();
	
	DayTwo() {
		colours.put("red", RED_MAX);
		colours.put("green", GREEN_MAX);
		colours.put("blue", BLUE_MAX);
	}
	

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		while(in.hasNextLine()) {
			boolean possible = true;
			
			String input = in.nextLine();

			// parse the input
			String[] split = input.split(": ");
			int id = Integer.parseInt(split[0].split("Game ")[1]);
			
			Pattern p = Pattern.compile("[0-9]+ (red|green|blue)");
			Matcher m = p.matcher(split[1]);
			
			// loop over sets while they are still possible
			while(m.find() && possible) {
				String[] set = m.group().split("(?![0-9]+) ");
				
				// compare number and colour to hashmap to check legibility
				int num = Integer.parseInt(set[0]);
				String colour = set[1].trim();
				
				if(num > colours.get(colour)) {
					possible = false;
				}
			}
			
			if(possible) total += id;
		}
		
		System.out.println(total);
	}
	
	
}
