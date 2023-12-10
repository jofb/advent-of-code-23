import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class DayNinePartTwo implements Day {

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			// map line to array of ints
			int[] numbers = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
			
			// compute next value with recursion
			int next = numbers[0] - predictPattern(numbers);
			System.out.println(next);
			
			total += next;
		}
		
		System.out.println(total);
	}
	
	// iterate over array of ints and predict next number in sequence
	public int predictPattern(int[] numbers) {
		// used to check if the array is zeroed out
		boolean zeroes = true;
		
		int[] diffs = new int[numbers.length - 1];
		
		for(int i = 0; i < numbers.length - 1; i++) {
			// find current and next then compute difference
			int current = numbers[i];
			int next = numbers[i + 1];
			
			int diff = next - current;
			diffs[i] = diff;
		
			if(zeroes && diff != 0) zeroes = false;
		}
		// return 0 if needed (termination), or continue with recursion
		if(zeroes) return 0;
		return diffs[0] - predictPattern(diffs);
	}

}
public class DayNine implements Day {

	@Override
	public void run(Scanner in) {
		int total = 0;
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			// map line to array of ints
			int[] numbers = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
			
			// compute next value with recursion
			int next = numbers[numbers.length -1] + predictPattern(numbers);
			System.out.println(next);
			
			total += next;
		}
		
		System.out.println(total);
	}
	
	// iterate over array of ints and predict next number in sequence
	public int predictPattern(int[] numbers) {
		// used to check if the array is zeroed out
		boolean zeroes = true;
		
		int[] diffs = new int[numbers.length - 1];
		
		for(int i = 0; i < numbers.length - 1; i++) {
			// find current and next then compute difference
			int current = numbers[i];
			int next = numbers[i + 1];
			
			int diff = next - current;
			diffs[i] = diff;
		
			if(zeroes && diff != 0) zeroes = false;
		}
		// return 0 if needed (termination), or continue with recursion
		if(zeroes) return 0;
		return diffs[diffs.length - 1] + predictPattern(diffs);
	}

}
