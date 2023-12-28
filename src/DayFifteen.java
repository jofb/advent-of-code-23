import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

class DayFifteenPartTwo implements Day {

	@Override
	public void run(Scanner in) {
		String[] codes = in.nextLine().split(",");
		
		int total = 0;
		
		// lens boxes mapping using an ordered hash map
		List<Map<String, Integer>> boxes = new ArrayList<Map<String, Integer>>();
		for(int i = 0; i < 256; i++) {
			boxes.add(new LinkedHashMap<String, Integer>());
		}
		
		for(String code : codes) {
			// only complete this on the label
			String[] operands = code.split("-|=");
			
			String label = operands[0];
			int boxNum = hash(label);

			if(code.contains("-")) {
				// go to box num and remove the code if it is there
				if(boxes.get(boxNum).containsKey(label)) boxes.get(boxNum).remove(label);
			} else {
				// put new strength into map (either replaces old or inserts new)
				int strength = Integer.parseInt(operands[1]);
				boxes.get(boxNum).put(label, strength);
			}
		}
		// iterate over boxes and compute powers to add to total
		for(int i = 0; i < boxes.size(); i++) {
			Map<String, Integer> box = boxes.get(i);
			int slot = 1;
			for(Entry<String, Integer> entry : box.entrySet()) {
				total += (i + 1) * slot * entry.getValue();
				slot++;
			}
		}
		System.out.println(total);
	}

	int hash(String label) {
		int current = 0;
		for(char c : label.toCharArray()) {
			current += (int) c;
			current *= 17;
			current = current % 256;
		}
		return current;
	}
}

public class DayFifteen implements Day {

	@Override
	public void run(Scanner in) {
		String input = in.nextLine();
		String[] codes = input.split(",");
		
		int total = 0;
		
		for(String code : codes) {
			int current = 0;
			// compute total using algorithm on each code
			System.out.println(code);
			for(char c : code.toCharArray()) {
				current += (int) c;
				current *= 17;
				current = current % 256;
			}
			total += current;
		}
		System.out.println(total);
	}

}
