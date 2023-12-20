import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
//			System.out.println(Arrays.toString(nums));
			
			List<Integer> areas = new ArrayList<Integer>();
			areas.add(0);
			int index = 0;
			List<boolean[]> bb = new ArrayList<boolean[]>();
			List<List<Boolean>> bnb = new ArrayList<List<Boolean>>();
			boolean[] bbb = new boolean[springs.length()];
			for(char c : springs.toCharArray()) {
				if(c != '.') {
					areas.set(index, areas.get(index) + 1);
					continue;
				}
				areas.add(0);
//				bb.add(boolean[])
				index++;
				
			}
			int j = 0;

			List<List<Boolean>> helpme = new ArrayList<List<Boolean>>();
			List<Integer> indices = new ArrayList<Integer>();
			for(int i = 0; i < springs.toCharArray().length; i++) {
				if(springs.toCharArray()[i] == '#') indices.add(i);
			}
			helpme.add(new ArrayList<Boolean>());
			for(char c : springs.toCharArray()) {
				if(c != '.') {
					if(helpme.size() < index) helpme.add(new ArrayList<Boolean>());
					helpme.get(j).add(c == '#');
					continue;
				}
				helpme.add(new ArrayList<Boolean>());
				j++;
			}
			helpme = helpme.stream().filter(n -> !n.isEmpty()).collect(Collectors.toList());
			areas = areas.stream().filter(n -> n != 0).collect(Collectors.toList());
			
			List<boolean[]> ahhh = new ArrayList<boolean[]>();
			for(List<Boolean> h : helpme) {
				boolean[] arr = new boolean[h.size()];
				for(int i = 0; i < h.size(); i++) {
					arr[i] = h.get(i);
				}
				ahhh.add(arr);
			}
			
			for(boolean[] boe : ahhh) {
//				System.out.println(Arrays.toString(boe));	
			}
			
//			int[][] series = generateSeries(10, 6);
//			for(int[] arr : series) {
//				System.out.println(Arrays.toString(arr));
//			}
//			int t = recursemedaddy(0, areas, nums, ahhh);
			List<String> list = recursememummy(springs, "", nums);
//			System.out.println(list);
			int t = list.size();
			for(String p : list) {
				System.out.println(p + " " + indices);
				for(Integer i : indices) {
					if(p.charAt(i) != '+') {
						t--;
						break;
					}
				}
			}
			// loop over list
			// check characters at indexes that are #'s in original line
			// if they are not pluses, this one doesnt count
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
//		System.out.println(list + " | " + current + " | " + Arrays.toString(arr));
		return list;
	}
	
	int recursemedaddy(int depth, List<Integer> areas, int[] nums, List<boolean[]> boolarr) {
		int total = 0;
		int count = 0;
		depth++;
		for(Integer a : areas) {

			count++;
			
			int subtotal = 0;
			List<Integer> group = new ArrayList<Integer>();
			for(int i = 0; i < nums.length; i++) {
				int num = nums[i];
				subtotal += num;
				if(i != 0) subtotal++;
				
				if(subtotal > a) break;
				
				int[] g = Arrays.copyOfRange(nums, 0, i + 1);
				int[] g2 = Arrays.copyOfRange(nums, i + 1, nums.length);
				
//				int t = computePossibilities(g, a);
				// TODO find all the #'s
				// for now we just use this..
//				boolean[] boolarr = new boolean[a];
//				boolarr[0] = true;
				
				int t = computePossibilitiesRecursive(g, a, boolarr.get(count - 1), 0);

				if(g2.length == 0) {
					total += t;
					break;
				}
				// the potential to add
//				int t = 2;
				t *= recursemedaddy(depth, areas.subList(count, areas.size()), g2, boolarr.subList(count, boolarr.size()));
				total += t;
				// test with g, recurse on g2
			}
//			total += recursemedaddy(areas.subList(count, areas.size()), nums);
		}
//		System.out.println(total);
		return total;
	}
	
	int[][] generateSeries(int width, int height) {
		int[][] series = new int[height][width];
		Arrays.fill(series[0], 1);
		for(int i = 1; i < height; i++) {
			series[i] = new int[width];
			series[i][0] = 1;
			
			for(int j = 1; j < width; j++) {
				series[i][j] = series[i][j - 1] + series[i - 1][j];
			}
		}
//		System.out.println("series:");
//		for(int[] p : series) {
//			System.out.println(Arrays.toString(p));
//		}
		return series;
	}

	int computePossibilities(int[] arr, int length) {
		int total = 0;
		// refer to pascals triangle array
		// first use num of elements + 1 ( to ignore first row)
		// then use min (sum of elements + arr.length - 1)
		int min = Arrays.stream(arr).sum() + arr.length - 1;
		
		int[][] triangle = generateSeries(length - min + 1, arr.length + 1);
//		System.out.println(triangle[arr.length][length - min]);
//		System.out.println("possibilities: " + Arrays.toString(arr) + " in " + length + " is " + triangle[arr.length][length - min]);
		return triangle[arr.length][length - min];
	}
	
	int computePossibilitiesRecursive(int[] arr, int length, boolean[] boolarr, int prevLength) {
		int total = 0;
		
//		System.out.println(Arrays.toString(arr) + " : " + length + " : " + total + " : " + prevLength);

		if(arr.length == 1) {
			total = length - arr[0] + 1;
			// check that nothing after this is true still
//			boolean[] pb = Arrays.copyOf(boolarr, prevLength + arr[0] + 1);
//			System.out.println(Arrays.toString(pb));
//			System.out.println(prevLength + arr[0] + ", " + prevLength + length);
//			System.out.print(prevLength + arr[0] + ", " + boolarr.length);
//			System.out.println("  " + total);
			for(int j = prevLength + arr[0]; j < boolarr.length; j++) {
//				System.out.println(Arrays.toString(boolarr) + ", " + arr[0]);
				if(boolarr[j]) total--;
			}

//			System.out.println(total);
			return (total < 0)? 0 : total;
//			return Math.abs(total);
		}
		
		for(int i = 0; i < length; i++) {
			int n = arr[0];
			boolean[] newboolarr = Arrays.copyOf(boolarr, boolarr.length);

			// if our lengths are too great return 0
			int[] rest = Arrays.copyOfRange(arr, 1, arr.length);
			int newlength = length - n - 1 - i; // shouild be length - n - 1 - i
			if(newlength < 0) return total;
			for(int j = prevLength + i; j < i + prevLength + n; j++) {
//				System.out.println(prevLength + ", " + i + ", " + n);
				// need to flip each one from current index (i + prev) to n + i + prev
				if(newboolarr[j]) newboolarr[j] = false;
			}
			for(int j = 0; j < prevLength; j++) {
//				System.out.println(newboolarr[j]);
				if(newboolarr[j]) return 0;
			}
			
//			System.out.println(Arrays.toString(arr) + ": " + Arrays.toString(rest) + ", " + newlength + ", " + length);

			// termination case
			if(rest.length == 0) {
//				int t = length - n + 1;
//				total += t;
				break;
			}
			int c = computePossibilitiesRecursive(rest, newlength, newboolarr, prevLength + n + 1 + i);
			total += c;
			System.out.println(Arrays.toString(rest) + " : " + Arrays.toString(newboolarr) + " : " + c + " : " + (prevLength + n + 1 + i));

			
//			total += Math.min(total, c) 
		}
		// loop from 0 to length
		
		// take first element of arr and put in space
		
		// take rest of elements from arr and recurse
		
		// when there are no elements left 

		
		return total;
	}
	
	boolean validateConfig(int[] arr, List<Integer> areas) {
		int c = 0;
		for(int i = 0; i < arr.length; i++) {
			int num = arr[i];
			int area = areas.get(c); 
			if(c + arr.length - i > areas.size()) return false;
			if(num > area) { 
				i--;
				if(c == areas.size() - 1) return false;
			} 
			c++;
		}
		return true;
	}

}
