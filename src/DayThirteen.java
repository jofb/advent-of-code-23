import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class DayThirteenPartTwo implements Day {

	@Override
	public void run(Scanner in) {

		int total = 0;
		
		// horizontal line groups
		List<Group> groups = new ArrayList<Group>();
		
		// parse into groups
		List<String> current = new ArrayList<String>();
		while(in.hasNextLine()) {
			String line = in.nextLine();
			if(line.isEmpty()) {
				groups.add(new Group(new ArrayList<String>(current)));
				current.clear();
				continue;
			}
			current.add(line);
		}
		groups.add(new Group(new ArrayList<String>(current)));
		
		// create list of vertical groups
		List<Group> flippedGroups = flipGroups(groups);
		
		// count totals of horizontal and vertical line groups
		for(int i = 0; i < groups.size(); i++) {
			total += countMirror(groups.get(i), 100);
			total += countMirror(flippedGroups.get(i), 1);
		}
		
		System.out.println(total);
	}
	
	// counts mirror totals 
	int countMirror(Group g, int factor) {
		int total = 0;
		int ignore = -1;
		// first find the original line
		// then ignore it, and restart the loop
		for(int i = 0; i < g.lines.size() - 1; i++) {
			if(g.lines.get(i).equals(g.lines.get(i + 1))) {
				// we're going to ignore this line in the future 
				if(validateMirror(g.lines, i, i + 1, true)) {
					ignore = i;
					break;
				}
			}
		}
		// loop over lines and validate any mirrors that appear
		for(int i = 0; i < g.lines.size() - 1; i++) {
			if(i == ignore) continue;
			// do the character checking here
			int result = equalsOrOffByOne(g.lines.get(i), g.lines.get(i + 1));
			if(result >= 0) {
				if(validateMirror(g.lines, i, i + 1, result == 0)) {
					total += (i + 1) * factor;
					break;
				}
			}
		}
		return total;
	}
	
	List<Group> flipGroups(List<Group> list) {
		List<Group> flippedGroups = new ArrayList<Group>();
		for(Group g : list) {
			List<String> lines = g.lines;
			int length = lines.get(0).length();
			String[] stringarr = new String[length];
			Arrays.fill(stringarr, "");
			List<String> l = Arrays.asList(stringarr);
			
			String s = "";
			for(String line : lines) {
				for(int i = 0; i < line.toCharArray().length; i++) {
					l.set(i, l.get(i) + line.charAt(i));
				}
			}
			flippedGroups.add(new Group(l));
		}
		
		return flippedGroups;
	}
	
	// checks two strings whether they are equal or off by one character
	// -1 is not equal 
	// 0 if equal but off by one
	// 1 if equal
	int equalsOrOffByOne(String s1, String s2) {
		boolean flipped = false;
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		
		for(int i = 0; i < c1.length; i++) {
			if(c1[i] == c2[i]) continue;
			if(!flipped) {
				flipped = true;
				continue;
			}
			return -1;
		}
		return 0 + (flipped? 0 : 1);
	}
	
	// validates a mirror against a list of strings
	boolean validateMirror(List<String> list, int lower, int upper, boolean alreadyFlipped) {
		// if we've already wiped a smudge away
		boolean flipped = alreadyFlipped;
		// the endpoint that we calculate mirror lengths to
		int end = (lower < Math.abs(upper - list.size()))? lower + 1 : Math.abs(upper - list.size());
		
		// loop over mirror points and validate that opposing sides are equal or off by one
		for(int j = 1; j < end; j++) {
			String s1 = list.get(lower - j);
			String s2 = list.get(upper + j);
			
			// if we've already wiped a smudge then the lines have to be equal
			int result = equalsOrOffByOne(s1, s2);
			if(result == -1) return false;
			if(result == 0 && flipped) return false;
			
			if(!flipped) flipped = (result == 0);
		}
		return true;
	}
	
	record Group(List<String> lines) {}
}
public class DayThirteen implements Day {

	@Override
	public void run(Scanner in) {
		
		List<Group> groups = new ArrayList<Group>();
		
		List<String> current = new ArrayList<String>();
		while(in.hasNextLine()) {
			String line = in.nextLine();
			if(line.isEmpty()) {
				groups.add(new Group(new ArrayList<String>(current)));
				current.clear();
				continue;
			}
			current.add(line);
		}
		groups.add(new Group(new ArrayList<String>(current)));
		
		List<Group> flippedGroups = flipGroups(groups);
		System.out.println(flippedGroups);
		
		int total = 0;
		total += countMirror(groups, 100);
		
		total += countMirror(flippedGroups, 1);
		
		
		
		System.out.println(total);
	}
	
	int countMirror(List<Group> list, int factor) {
		int total = 0;
		for(Group g : list) {
			for(int i = 0; i < g.lines.size() - 1; i++) {
				if(g.lines.get(i).equals(g.lines.get(i + 1))) {
					if(validateMirror(g.lines, i, i + 1)) {
						System.out.println("we have a match!");
						total += (i + 1) * factor;
						break;
					}
				}
			}
		}
		return total;
	}
	
	List<Group> flipGroups(List<Group> list) {
		List<Group> flippedGroups = new ArrayList<Group>();
		for(Group g : list) {
			List<String> lines = g.lines;
			int length = lines.get(0).length();
			String[] stringarr = new String[length];
			Arrays.fill(stringarr, "");
			List<String> l = Arrays.asList(stringarr);
			
			String s = "";
			for(String line : lines) {
				for(int i = 0; i < line.toCharArray().length; i++) {
					l.set(i, l.get(i) + line.charAt(i));
				}
			}
			flippedGroups.add(new Group(l));
		}
		
		return flippedGroups;
	}
	
	boolean validateMirror(List<String> list, int lower, int upper) {
		int end = (lower < Math.abs(upper - list.size()))? lower + 1 : Math.abs(upper - list.size());
		for(int j = 1; j < end; j++) {
			String s1 = list.get(lower - j);
			String s2 = list.get(upper + j);
			
			if(!s1.equals(s2)) {
				return false;
			}
		}
		System.out.println(end);
		return true;
	}
	
	record Group(List<String> lines) {}
}
