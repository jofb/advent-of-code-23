import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DayOne implements Day {
	
	private String[] nums = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

	@Override
	public void run(Scanner in) {
		int total = 0;
		while(in.hasNext()) {
			String input = in.next();

			Pattern p = Pattern.compile("one|two|three|four|five|six|seven|eight|nine|[0-9]");
			Matcher m = p.matcher(input);
			
			List<String> matches = new ArrayList<String>();
			
			while(m.find()) {
				matches.add(m.group());
				// reset region for overlapping cases (oneight, etc)
				m.region(m.start() + 1, input.length());
			}

			String first = stringToNum(matches.get(0));
			String last = stringToNum(matches.get(matches.size() - 1));

			total += Integer.parseInt(first + last);
		}
		
		System.out.println(total);
	}
	
	// decodes text to number
	private String stringToNum(String s) {
		String num = s;

		for(int i = 0; i < nums.length; i++) {
			if(nums[i].equals(s)) {
				num = Integer.toString(i + 1);
			}
		}
		return num;
	}
	
}