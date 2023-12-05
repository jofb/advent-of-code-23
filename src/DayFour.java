import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class DayFourPartTwo implements Day {

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		// create list of lines
		List<String> lines = new ArrayList<String>();
		while(in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		
		// initialize list of cards
		List<Integer> cardCopies = new ArrayList<Integer>(Collections.nCopies(lines.size(), 1));

		int cardNumber = 0;
		for(String line : lines) {
			String[] inputs = line.split(":")[1].split("\\|");
			
			// create array from first half
			List<String> wins = new ArrayList<>(Arrays.asList(inputs[0].split(" ")));
			wins.removeIf(String::isEmpty);
			wins = wins.stream().map(s -> " ".concat(s + " ")).collect(Collectors.toList());
			// and string from second half
			String nums = inputs[1].concat(" ");
			
			// iterate over and determine how many copies to create
			int copiesToMake = 0;
			for(String win : wins) {
				if(nums.contains(win)) copiesToMake++;
			}
			// create copies of cards as many times as needed
			// start at current card (cardNumber + 1), and go up to the amount of copies to make
			// for each one, add the current amount + the number of copies of the current card
			for(int j = cardNumber + 1; j <= cardNumber + copiesToMake; j++) {
				cardCopies.set(j, cardCopies.get(j) + cardCopies.get(cardNumber));
			}
			cardNumber++;
		}

		total = cardCopies.stream().reduce(0, (a, b) -> a + b);
		System.out.println(total);
	}

}

class DayFour implements Day {

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		while(in.hasNextLine()) {
			int cardTotal = 0;
			String line = in.nextLine();
			String[] inputs = line.split(":")[1].split("\\|");
			
			// create array from first half
			List<String> wins = new ArrayList<>(Arrays.asList(inputs[0].split(" ")));
			wins.removeIf(String::isEmpty);
			wins = wins.stream().map(s -> " ".concat(s + " ")).collect(Collectors.toList());
			// and string from second half
			String nums = inputs[1].concat(" ");
			// iterate over and determine total
			for(String win : wins) {
				if(nums.contains(win)) {
					cardTotal = cardTotal << 1;
					if(cardTotal == 0) cardTotal = 1; 					
				}
				
			}
			total += cardTotal;
		}
		System.out.println(total);
	}

}
