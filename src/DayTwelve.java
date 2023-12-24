import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

class DayTwelvePartTwo implements Day {
	
	final int MAX_VALUE = 5;

	Map<Input, Long> testee = new HashMap<Input, Long>();

	@Override
	public void run(Scanner in) {
		
		long total = 0;

		while(in.hasNextLine()) {
			// parse line
			String[] line = in.nextLine().split(" ");
			
			String springs = line[0];
			int[] nums = Arrays.stream(line[1].split(",")).mapToInt(Integer::parseInt).toArray();

			// dupe nums
			int[] newnums = new int[nums.length * MAX_VALUE];
			for(int i = 0; i < newnums.length; i++) {
				newnums[i] = nums[i % nums.length];
			}
			// dupe springs
			String newsprings = "";
			for(int i = 0; i < MAX_VALUE; i++) {
				newsprings = newsprings + springs + '?';
			}
			newsprings = newsprings.substring(0, newsprings.length() - 1);

			List<Integer> nnewnums = Arrays.stream(newnums).boxed().toList();
			long ppp = thatnewnewshit(newsprings, nnewnums);
			total += ppp;

		}
		
		System.out.println(total);
	}
	
	/// adapted from https://github.com/ash42/adventofcode/blob/main/adventofcode2023/src/nl/michielgraat/adventofcode2023/day12/Day12.java#L20
	long thatnewnewshit(String line, List<Integer> nums) {
		Input input = new Input(line, nums);
		
		if(testee.containsKey(input)) {
			return testee.get(input);
		}
		
		if(line.isBlank()) {
			return nums.isEmpty() ? 1 : 0;
		}
		char c = line.charAt(0);
		long count = 0;
		
		if(c == '.') {
			count = thatnewnewshit(line.substring(1), nums);
		} else if(c == '?') {
			count = thatnewnewshit("." + line.substring(1), nums) + thatnewnewshit("#" + line.substring(1), nums);
		} else {
			if(nums.isEmpty()) {
				count = 0;
			} else {
				int n = nums.get(0);
				if(n <= line.length() && line.chars().limit(n).allMatch(ch -> ch == '#' || ch == '?')) {
					List<Integer> newnums = nums.subList(1, nums.size());
					
					if(n == line.length()) {
						count = newnums.isEmpty() ? 1 : 0;
					} else if(line.charAt(n) == '.') {
						count = thatnewnewshit(line.substring(n + 1), newnums);
					} else if(line.charAt(n) == '?') {
						count = thatnewnewshit('.' + line.substring(n + 1), newnums);
					} else {
						count = 0;
					}
				} else {
					count = 0;
				}	
			}
			
		}
		testee.put(input, count);
		return count;
	}
	
	public record Input(String line, List<Integer> nums) { }
	
}
public class DayTwelve implements Day {

	@Override
	public void run(Scanner in) {
		
		int total = 0;

		while(in.hasNextLine()) {
			// parse line
			String[] line = in.nextLine().split(" ");
			
			String springs = line[0];
			int[] nums = Arrays.stream(line[1].split(",")).mapToInt(Integer::parseInt).toArray();
			
			System.out.println("\n");
			System.out.println(" " + springs + "  " + Arrays.toString(nums));

			List<Integer> indices = new ArrayList<Integer>();
			for(int i = 0; i < springs.toCharArray().length; i++) {
				if(springs.toCharArray()[i] == '#') indices.add(i);
			}

			List<String> list = recursememummy(springs, "", nums);
			
			int t = list.size();
			// compare configs to valid configs and discard if needed
			for(String p : list) {
				System.out.println(p + " " + indices);
				for(Integer i : indices) {
					if(p.charAt(i) != '+') {
						t--;
						break;
					}
				}
			}
			System.out.println(t);
			total += t;	
		}
		
		System.out.println(total);
	}
	
	List<String> recursememummy(String line, String current, int[] arr) {
		List<String> list = new ArrayList<String>();
		// number to place next
		int num = arr[0];
		// current count of sequential valid places
		int count = 0;
		
		char[] chararr = line.toCharArray();
		String s = current;
		
		for(int i = current.length(); i < chararr.length; i++) {
			char c = chararr[i];
			// if num cant fit within array, just exit
			if(num + i - count > chararr.length) break;
			
			// add as many dots as we have in count then reset count
			if(c == '.') {
				s = s + c;
				for(int j = 0; j < count; j++) s = s + c;
				count = 0;
				continue;
			};
			
			count++;
			
			// check if we can place then place number and continue with rest of numbers
			if(count >= num) {
				// we can place
				String newString = s;
				// forming the string
				for(int j = 0; j < num; j++) {
					newString = newString + '+';
				}
				// if we're at end of string dont need to add another dot
				if(chararr.length != num + i - count + 1) newString = newString + '.';
				
				// add dot for next loop
				s = s + '.';
				
				count = 0;
				i = s.length() - 1;
				
				// termination case, add the new string then continue on loop
				if(arr.length == 1)  {
					list.add((newString + line.substring(newString.length())).replaceAll("[\\?#]", "."));
					continue;
				} 
				// now we can recurse on rest of array and line
				int[] rest = Arrays.copyOfRange(arr, 1, arr.length);
				list.addAll(recursememummy(line, newString, rest));

			}
		}
		return list;
	}

}
