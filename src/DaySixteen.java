import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class DaySixteenPartTwo implements Day {
	
	List<List<Character>> grid;
	List<List<Character>> visualGrid;
	
	Map<String, List<String>> memoizationMap = new HashMap<String, List<String>>();

	@Override
	public void run(Scanner in) {
		// parse our lines
		grid = new ArrayList<List<Character>>();
		visualGrid = new ArrayList<List<Character>>();

		while(in.hasNextLine()) {
			List<Character> line = in.nextLine().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
			grid.add(line);

		}
		for(List<Character> list : grid) {
			visualGrid.add(new ArrayList<>(list));
		}
		
		int xMax = grid.get(0).size();
		int yMax = grid.size();

		int max = 0;
		
		// lazily iterate over every edge and test every possibility, finding largest each time
		for(int y = 0; y < yMax; y++) {
			int m = Math.max(simulateLaser(-1, y, 1, 0), simulateLaser(xMax, y, -1, 0));
			max = Math.max(m, max);
		}
		for(int x = 0; x < xMax; x++) {
			int m = Math.max(simulateLaser(x, -1, 0, 1), simulateLaser(x, yMax, 0, -1));
			max = Math.max(max, m);
		}

		System.out.println(max);		
		
	}
	
	int simulateLaser(int initx, int inity, int dx, int dy) {
		laser(initx, inity, dx, dy); 
		int energized = memoizationMap.size();
		visualGrid.clear();
		for(List<Character> list : grid) {
			visualGrid.add(new ArrayList<>(list));
		}
		
		memoizationMap.clear();
		return energized;
	}
	
	void laser(int px, int py, int dx, int dy) {
		while(true) {
			// move forward as needed
			px += dx;
			py += dy;
			
			// key and val used for memoization
			String key = String.format("%d %d", px, py);
			String val = String.format("%d %d", dx, dy);
		
			if(outOfBounds(px, py) || (memoizationMap.containsKey(key) && memoizationMap.get(key).contains(val))) break;

			char c = grid.get(py).get(px);

			// process character
			// angles
			// flip dx and dy, and polarity if needed
			if(c == '\\' || c == '/') {
				int temp = dx;
				dx = dy;
				dy = temp;
				if(c == '/') {
					dx *= -1;
					dy *= -1;
				}
			}
			// splitters
			// only process if we're hitting them head on
			else if(c == '-' && dx == 0) {
				// spawn a new laser from current px and py, with -dx (giong left)
				dx = 1;
				dy = 0;
				laser(px, py, -1 * dx, dy);
			}
			else if(c == '|' && dy == 0) {
				// spawn new laser from current px and py, with -dy (going up)
				dy = 1;
				dx = 0;
				laser(px, py, dx, -1 * dy);
			}
			// add node to memoization (visited) list
			if(!memoizationMap.containsKey(key)) memoizationMap.put(key, new ArrayList<String>());
			memoizationMap.get(key).add(val);
			
			// used only for visual
			visualGrid.get(py).set(px, '#');
		}
		
		return;
	}
	
	boolean outOfBounds(int x, int y) {
		return x < 0 || x >= grid.get(0).size() || y < 0 || y >= grid.size();
	}

}

public class DaySixteen implements Day {
	
	List<List<Character>> grid;
	List<List<Character>> visualGrid;
	
	Map<String, List<String>> memoizationMap = new HashMap<String, List<String>>();

	@Override
	public void run(Scanner in) {
		// parse our lines
		grid = new ArrayList<List<Character>>();
		visualGrid = new ArrayList<List<Character>>();

		while(in.hasNextLine()) {
			List<Character> line = in.nextLine().chars().mapToObj(c -> (char) c).collect(Collectors.toList());
			grid.add(line);

		}
		for(List<Character> list : grid) {
			visualGrid.add(new ArrayList<>(list));
		}

		laser(-1, 0, 1, 0);

		for(List<Character> list : visualGrid) {
			System.out.println(list);
		}
		
		int total = memoizationMap.size();

		
		System.out.println(total);		
		
	}
	
	void laser(int px, int py, int dx, int dy) {
		while(true) {
			// move forward as needed
			px += dx;
			py += dy;
			
			// key and val used for memoization
			String key = String.format("%d %d", px, py);
			String val = String.format("%d %d", dx, dy);
		
			if(outOfBounds(px, py) || (memoizationMap.containsKey(key) && memoizationMap.get(key).contains(val))) break;

			char c = grid.get(py).get(px);

			// process character
			// angles
			// flip dx and dy, and polarity if needed
			if(c == '\\' || c == '/') {
				int temp = dx;
				dx = dy;
				dy = temp;
				if(c == '/') {
					dx *= -1;
					dy *= -1;
				}
			}
			// splitters
			// only process if we're hitting them head on
			else if(c == '-' && dx == 0) {
				// spawn a new laser from current px and py, with -dx (giong left)
				dx = 1;
				dy = 0;
				laser(px, py, -1 * dx, dy);
			}
			else if(c == '|' && dy == 0) {
				// spawn new laser from current px and py, with -dy (going up)
				dy = 1;
				dx = 0;
				laser(px, py, dx, -1 * dy);
			}
			// add node to memoization (visited) list
			if(!memoizationMap.containsKey(key)) memoizationMap.put(key, new ArrayList<String>());
			memoizationMap.get(key).add(val);
			
			// used only for visual
			visualGrid.get(py).set(px, '#');
		}
		
		return;
	}
	
	boolean outOfBounds(int x, int y) {
		return x < 0 || x >= grid.get(0).size() || y < 0 || y >= grid.size();
	}

}
