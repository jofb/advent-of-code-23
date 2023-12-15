import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DayElevenPartTwo implements Day {
	
	@Override
	public void run(Scanner in) {
		List<String> inputHorizontal = new ArrayList<String>();
		List<Position> everyPoint = new ArrayList<Position>();
		
		while(in.hasNextLine()) {
			inputHorizontal.add(in.nextLine());
		}
		
		List<String> inputVertical = new ArrayList<String>();
		
		// form vertical input for use in galaxy expansion testing
		for(int i = 0; i < inputHorizontal.get(0).length(); i++) {
			StringBuilder s = new StringBuilder();
			for(int j = 0; j < inputHorizontal.size(); j++) {
				String str = inputHorizontal.get(j);
				char c = str.charAt(i);
				if(c == '#') everyPoint.add(new Position(i, j));
				s.append(c);
			}
			inputVertical.add(s.toString());
		}
		
		List<Integer> rowIndexes = new ArrayList<Integer>();
		List<Integer> colIndexes = new ArrayList<Integer>();
		
		// list indices which should be expanded upon
		for(int i = 0; i < inputHorizontal.size(); i++) {
			if(inputHorizontal.get(i).matches("\\.+")) {
				rowIndexes.add(i);
			}
		}
		for(int i = 0; i < inputVertical.size(); i++) {
			if(inputVertical.get(i).matches("\\.+")) {
				colIndexes.add(i);
			}
		}

		long total = 0;
		
		int start = 0;
		// loop over every point, form pairs, then compute length of shortest line and add to total
		for(int i = start; i < everyPoint.size(); i++) {
			Position current = everyPoint.get(i);
			for(int j = i + 1; j < everyPoint.size(); j++) {
				Position next = everyPoint.get(j);
				long dist = Math.abs(current.x - next.x) + Math.abs(current.y - next.y);
				
				int expansion = 0;
				// galaxy expansion by checking if points are within range (pretty lazy)
				for(Integer n : colIndexes) {
					if((n > current.x && n < next.x) || (n > next.x && n < current.x)) expansion++;
				}
				for(Integer n : rowIndexes) {
					if((n > current.y && n < next.y) || (n > next.y && n < current.y)) expansion++;
				}
				
				dist += (expansion * 999999);
				
				total += dist;
			}
			start++;
		}
		System.out.println(total);
	}

	class Position {
		int x;
		int y;
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}
	
}
public class DayEleven implements Day {
	
	@Override
	public void run(Scanner in) {
		List<String> inputHorizontal = new ArrayList<String>();
		List<Position> everyPoint = new ArrayList<Position>();
		
		while(in.hasNextLine()) {
			inputHorizontal.add(in.nextLine());
		}
		
		List<String> inputVertical = new ArrayList<String>();
		
		// form vertical input for use in galaxy expansion testing
		for(int i = 0; i < inputHorizontal.get(0).length(); i++) {
			StringBuilder s = new StringBuilder();
			for(int j = 0; j < inputHorizontal.size(); j++) {
				String str = inputHorizontal.get(j);
				char c = str.charAt(i);
				if(c == '#') everyPoint.add(new Position(i, j));
				s.append(c);
			}
			inputVertical.add(s.toString());
		}
		
		List<Integer> rowIndexes = new ArrayList<Integer>();
		List<Integer> colIndexes = new ArrayList<Integer>();
		
		// list indices which should be expanded upon
		for(int i = 0; i < inputHorizontal.size(); i++) {
			if(inputHorizontal.get(i).matches("\\.+")) {
				rowIndexes.add(i);
			}
		}
		for(int i = 0; i < inputVertical.size(); i++) {
			if(inputVertical.get(i).matches("\\.+")) {
				colIndexes.add(i);
			}
		}

		int total = 0;
		
		int start = 0;
		// loop over every point, form pairs, then compute length of shortest line and add to total
		for(int i = start; i < everyPoint.size(); i++) {
			Position current = everyPoint.get(i);
			for(int j = i + 1; j < everyPoint.size(); j++) {
				Position next = everyPoint.get(j);
				long dist = Math.abs(current.x - next.x) + Math.abs(current.y - next.y);
				
				// galaxy expansion by checking if points are within range (pretty lazy)
				for(Integer n : colIndexes) {
					if((n > current.x && n < next.x) || (n > next.x && n < current.x)) dist++;
				}
				for(Integer n : rowIndexes) {
					if((n > current.y && n < next.y) || (n > next.y && n < current.y)) dist++;
				}
				
				total += dist;
			}
			start++;
		}
		System.out.println(total);
	}
	
	class Position {
		int x;
		int y;
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}
	
}
