import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class DayFourteenPartTwo implements Day {
		
	enum Cardinality { NORTH, WEST, SOUTH, EAST };
	
	final long MAX = 1000000000;

	@Override
	public void run(Scanner in) {
		
		// parse our lines
		List<List<Character>> grid = new ArrayList<List<Character>>();

		while(in.hasNextLine()) {
			List<Character> line = in.nextLine().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
			grid.add(line);
		}
		// keep track of grid states to find loop
		List<String> gridStates = new ArrayList<String>();

		long loop = 0;
		// find the point the grid starts looping
		for(long i = 0; i < MAX; i++) {
			grid = collapse(grid, Cardinality.NORTH);
			grid = collapse(grid, Cardinality.WEST);
			grid = collapse(grid, Cardinality.SOUTH);
			grid = collapse(grid, Cardinality.EAST);
			
			// if the grid state is already in our list we can determine a point it loops and exit
			String gridstring = grid.toString();
			if(gridStates.contains(gridstring)) {
				loop = i - gridStates.indexOf(gridstring);
				break;
			}
			gridStates.add(gridstring);
		}
		// then just loop until its done
		for(int i = 0; i < loop - 1; i++) {
			grid = collapse(grid, Cardinality.NORTH);
			grid = collapse(grid, Cardinality.WEST);
			grid = collapse(grid, Cardinality.SOUTH);
			grid = collapse(grid, Cardinality.EAST);
		}
		
		// and compute total
		long total = 0;
		// count total
		for(int i = 0; i < grid.size(); i++) {
			total += (grid.size() - i) * grid.get(i).stream().filter(c -> c == 'O').count();
		}
		System.out.println(total);
	}
	
	// collapses a grid on a given cardinality
	List<List<Character>> collapse(List<List<Character>> grid, Cardinality card) {
		List<List<Character>> collapsedGrid = grid;
		
		// changes behaviour of collapsing function
		int dim1, dim2;
		boolean northSouth = (card == Cardinality.NORTH || card == Cardinality.SOUTH);
		
		if(northSouth) {
			dim1 = grid.get(0).size();
			dim2 = grid.size();
		} else {
			dim1 = grid.size();
			dim2 = grid.get(0).size();
		}
		// loop over dimensions and determine where to push rocks
		for(int i = 0; i < dim1; i++) {
			String line;
			// if its north south we want the vertical lines of the grid, else just grab the line
			if(northSouth) {
				int index = i;
				line = grid.stream()
						.map(arr -> arr.get(index))
						.reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
						.toString();
			} else {
				line = grid.get(i).stream()
						.reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
						.toString();
			}
			
			// split on square rocks
			String[] split = line.split("(?<=#)");

			String ns = "";
			for(String s : split) {
				// count number of rocks and square rocks in order to push to sides
				int rocks = (int) s.chars().filter(ch -> ch == 'O').count();
				int squareRock = (s.charAt(s.length() - 1) == '#')? 1 : 0;
				// we either push the rocks left to right or right to left based on cardinality
				if(card == Cardinality.NORTH || card == Cardinality.WEST) {
					ns = ns + "O".repeat(rocks) + ".".repeat(s.length() - rocks - squareRock) + "#".repeat(squareRock);	
				} else {
					ns = ns + ".".repeat(s.length() - rocks - squareRock) + "O".repeat(rocks) + "#".repeat(squareRock);
				}
			}
			
			// finally map the new string onto the collapsed grid based on cardinality
			for(int j = 0; j < dim2; j++) {
				if(northSouth) collapsedGrid.get(j).set(i, ns.charAt(j));
				else collapsedGrid.get(i).set(j, ns.charAt(j));	
			}
		}
		return collapsedGrid;
	}
	
}
public class DayFourteen implements Day {

	@Override
	public void run(Scanner in) {
		
		// parse our lines
		List<char[]> lines = new ArrayList<char[]>();

		while(in.hasNextLine()) {
			lines.add(in.nextLine().toCharArray());
		}
		
		// keep incremental count of empty spaces in each column
		int[] counts = new int[lines.get(0).length];
		
		int total = 0;
		
		// loop over our lines and count weight
		for(int i = 0; i < lines.size(); i++) {
			char[] arr = lines.get(i);
			for(int j = 0; j < arr.length; j++) {
				char c = arr[j];
				// if its empty space, increment count for next total on that column
				if(c == '.') counts[j] = counts[j] + 1;
				// if its a square rock, we can reset the count for that column
				else if(c == '#') counts[j] = 0;
				// if its a round rock, compute its weight using row number and current count of empty space above it
				else if(c == 'O') total += lines.size() - (i - counts[j]);
			}
		}
		System.out.println(total);
	}

}
