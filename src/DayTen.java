import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class DayTenPartTwo implements Day {
	
	public static List<String> pipes = new ArrayList<String>(Arrays.asList("-", "|", "F", "7", "L", "J"));

	@Override
	public void run(Scanner in) {
		List<List<String>> input = new ArrayList<List<String>>();

		Position startPos = new Position();
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			
			if(line.contains("S")) {
				startPos.setPosition(line.indexOf("S"), input.size());
			}
			
			List<String> arr = Arrays.asList(line.split(""));
			input.add(arr);
		}
		
		// starting neighbor from start point
		Position p = findStartNeighbour(input, startPos);
		String symbol = input.get(p.y).get(p.x);
		
		int dx = startPos.x - p.x;
		int dy = startPos.y - p.y;
		
		int t = 0;

		List<List<Position>> pee = new ArrayList<List<Position>>();
		for(int i = 0; i < input.size(); i++) {
			pee.add(new ArrayList<Position>());
		}

		
		// traverse along path until we reach end
		while(!symbol.equals("S")) {
			// switch over x/y orientation
			if(symbol.equals("|") || symbol.equals("-") || symbol.equals("L") || symbol.equals("7")) {
				dx = dx * -1;
				dy = dy * -1;
			}
			// if a bend, swap x and y to switch axis
			if(symbol.equals("L") || symbol.equals("7") || symbol.equals("J") || symbol.equals("F")) {
				int c = dx;
				dx = dy;
				dy = c;
 			}
			// compute new pos and diff
			int nx = p.x + dx;
			int ny = p.y + dy;
			
			dx = p.x - nx;
			dy = p.y - ny;
			
			if(!symbol.equals("-")) pee.get(p.y).add(new Position(p.x, p.y));
			
			p.setPosition(nx, ny);
			
			symbol = input.get(p.y).get(p.x);
		}
		pee.get(startPos.y).add(startPos);
		int parity = 0;
		for(List<Position> line : pee) {
			line.sort(null);
			parity = 0;
			
			String prev = "";
			
			for(int j = 0; j < line.size() - 1; j++) {
				String currentSymbol = input.get(line.get(j).y).get(line.get(j).x);
				
				if(currentSymbol.equals("-")) {
					continue;
				}
				
				if(currentSymbol.equals("F") || currentSymbol.equals("L")) {
					prev = currentSymbol;
					continue;
				}
				else if((currentSymbol.equals("7") && prev.equals("L")) || (currentSymbol.equals("J") && prev.equals("F"))) {
					parity++;
					prev = "";
				} else {
					prev = "";
				}
				
				prev = currentSymbol;

				if(currentSymbol.equals("|")) parity++;
				
				if(parity % 2 == 1 && !prev.equals("")) {
					t += Math.abs(line.get(j).x - line.get(j + 1).x + 1);
				}

			}
			
		}
		System.out.println(t);
		
	}
	
	// find first eligible neighbor from start point
	Position findStartNeighbour(List<List<String>> input, Position startPos) {
		// form cardinals
		int[] x = { 0, 1, 0, -1 };
		int[] y = { -1, 0, 1, 0 };
		// regex for each cardinal
		List<String> pipeRegex = new ArrayList<String>(Arrays.asList("[|\\|F\\|7]", "[-\\|J\\|7]", "[|\\|J\\|L]", "[-\\|L\\|F]"));
		
		// loop over each cardinal and test 
		for(int i = 0; i < 4; i++) {
			int posX = startPos.x + x[i];
			int posY = startPos.y + y[i];

			if(posX < 0 || posY < 0) continue;
			
			Position pos = new Position(posX, posY);
			String s = input.get(pos.y).get(pos.x);
			
			if(s.matches(pipeRegex.get(i))) {
				return pos;
			}
		}
		return startPos;
	}
	
	class Position implements Comparable<Position> {
		int x;
		int y;
		
		Position() {
			this.x = 0;
			this.y = 0;
		}
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		void setPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d)", this.x, this.y);
		}

		@Override
		public int compareTo(DayTenPartTwo.Position o) {
			return Integer.compare(this.x, o.x);
		}
	}

}

public class DayTen implements Day {
	
	public static List<String> pipes = new ArrayList<String>(Arrays.asList("-", "|", "F", "7", "L", "J"));

	@Override
	public void run(Scanner in) {
		List<List<String>> input = new ArrayList<List<String>>();

		Position startPos = new Position();
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			
			if(line.contains("S")) {
				startPos.setPosition(line.indexOf("S"), input.size());
			}
			
			List<String> arr = Arrays.asList(line.split(""));
			input.add(arr);
		}
		
		// starting neighbor from start point
		Position p = findStartNeighbour(input, startPos);
		String symbol = input.get(p.y).get(p.x);
		
		int dx = startPos.x - p.x;
		int dy = startPos.y - p.y;
		
		int t = 0;
		
		// traverse along path until we reach end
		while(!symbol.equals("S")) {
			t++;
			// switch over x/y orientation
			if(symbol.equals("|") || symbol.equals("-") || symbol.equals("L") || symbol.equals("7")) {
				dx = dx * -1;
				dy = dy * -1;
			}
			// if a bend, swap x and y to switch axis
			if(symbol.equals("L") || symbol.equals("7") || symbol.equals("J") || symbol.equals("F")) {
				int c = dx;
				dx = dy;
				dy = c;
			}
			// compute new pos and diff
			int nx = p.x + dx;
			int ny = p.y + dy;
			
			dx = p.x - nx;
			dy = p.y - ny;
			
			p.setPosition(nx, ny);
			symbol = input.get(p.y).get(p.x);

		}
		
		System.out.println(t/2 + (t % 2 == 0 ? 0 : 1));
		
	}
	
	// find first eligible neighbor from start point
	Position findStartNeighbour(List<List<String>> input, Position startPos) {
		// form cardinals
		int[] x = { 0, 1, 0, -1 };
		int[] y = { -1, 0, 1, 0 };
		// regex for each cardinal
		List<String> pipeRegex = new ArrayList<String>(Arrays.asList("[|\\|F\\|7]", "[-\\|J\\|7]", "[|\\|J\\|L]", "[-\\|L\\|F]"));
		
		// loop over each cardinal and test 
		for(int i = 0; i < 4; i++) {
			int posX = startPos.x + x[i];
			int posY = startPos.y + y[i];

			if(posX < 0 || posY < 0) continue;
			
			Position pos = new Position(posX, posY);
			String s = input.get(pos.y).get(pos.x);
			
			if(s.matches(pipeRegex.get(i))) {
				return pos;
			}
		}
		return startPos;
	}
	
	class Position {
		int x;
		int y;
		
		Position() {
			this.x = 0;
			this.y = 0;
		}
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		void setPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}

}
