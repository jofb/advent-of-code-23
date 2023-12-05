import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DayThree implements Day {

	@Override
	public void run(Scanner in) {

		int total = 0;
		
		List<String> lines = new ArrayList<String>();
		ArrayList<int[]> numbers = new ArrayList<>();
		
		Pattern numPattern = Pattern.compile("\\d+");
		Pattern symbolPattern = Pattern.compile("[^\\w\\.]");

		while(in.hasNextLine()) {
			String line = in.nextLine();
			lines.add(line);
			
			int[] arr = new int[line.length()];
			
			Matcher m = numPattern.matcher(line); 
			// loop over numbers and add to global array
			while(m.find()) {
				for(int i = 0; i < m.group().length(); i++) {
					arr[m.start() + i] = Integer.parseInt(m.group());
				}
			}
			numbers.add(arr);
		}
		
		// iterate over lines, find symbols and surrounding area to assess numbers
		for(int i = 0; i < lines.size() - 1; i++) {
			// list of lines to check
			ArrayList<Integer> linesToCheck = new ArrayList<Integer>();
			if(i > 0) linesToCheck.add(i - 1);
			linesToCheck.add(i);
			if(i < lines.size() - 1) linesToCheck.add(i + 1);
			
			// get the line and symbols
			String line = lines.get(i);
			Matcher m = symbolPattern.matcher(line);
			
			while(m.find()) {
				// check each line in list
				for(Integer j : linesToCheck) {
					String checkedLine = lines.get(j);
					// form the substring of surrounding area
					int start = m.start() - 1;
					int end = m.start() + 2;
					String sub = checkedLine.substring(start, end);
					
					// check for numbers
					Matcher numMatcher = numPattern.matcher(sub);
					while(numMatcher.find()) {
						// get number from global array and add to total
						int numPosition = m.start() + numMatcher.start() - 1;
						int num = numbers.get(j)[numPosition];
						total += num;
					}
				}
				
			}
		}
		System.out.println(total);

	}

}

class DayThreePartTwo implements Day {

	@Override
	public void run(Scanner in) {

		int total = 0;
		
		List<String> lines = new ArrayList<String>();
		ArrayList<int[]> numbers = new ArrayList<>();
		
		Pattern numPattern = Pattern.compile("\\d+");
		Pattern symbolPattern = Pattern.compile("\\*");

		while(in.hasNextLine()) {
			String line = in.nextLine();
			lines.add(line);
			
			int[] arr = new int[line.length()];
			
			Matcher m = numPattern.matcher(line); 
			// loop over numbers and add to global array
			while(m.find()) {
				for(int i = 0; i < m.group().length(); i++) {
					arr[m.start() + i] = Integer.parseInt(m.group());
				}
			}
			numbers.add(arr);
		}
		
		// iterate over lines, find symbols and surrounding area to assess numbers
		for(int i = 0; i < lines.size() - 1; i++) {
			// list of lines to check
			ArrayList<Integer> linesToCheck = new ArrayList<Integer>();
			if(i > 0) linesToCheck.add(i - 1);
			linesToCheck.add(i);
			if(i < lines.size() - 1) linesToCheck.add(i + 1);
			
			// get the line and symbols
			String line = lines.get(i);
			Matcher m = symbolPattern.matcher(line);
			
			while(m.find()) {
				List<Integer> found = new ArrayList<Integer>();
				// check each line in list
				for(Integer j : linesToCheck) {
					String checkedLine = lines.get(j);
					// form the substring of surrounding area
					int start = m.start() - 1;
					int end = m.start() + 2;
					String sub = checkedLine.substring(start, end);
					
					// check for numbers
					Matcher numMatcher = numPattern.matcher(sub);
					
					while(numMatcher.find()) {
						// get number from global array and add to total
						int numPosition = m.start() + numMatcher.start() - 1;
						int num = numbers.get(j)[numPosition];
						found.add(num);
						if(found.size() == 2) {
							total += found.get(0) * found.get(1);
							System.out.println(found.toString());
							continue;
						}
					}
				}
				
			}
		}
		System.out.println(total);

	}

}
