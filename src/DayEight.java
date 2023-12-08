import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class DayEightPartTwo implements Day {

	@Override
	public void run(Scanner in) {
		// map instructions to numbers
		String[] instructions = in.nextLine().replace('L', '0').replace('R', '1').split("");
		in.nextLine();
		
		HashMap<String, Node> nodes = new HashMap<String, Node>();

		// encode nodes 
		while(in.hasNextLine()) {
			String line = in.nextLine();
			
			String code = line.split("=")[0].trim();
			String left = line.split(",")[0].split("\\(")[1];
			String right = line.split(", ")[1].replace(")", "");

			nodes.put(code, new Node(code, new String[] {left, right}));
		}

		// list of start nodes
		List<Node> currentList = new ArrayList<Node>();
		
		for(Map.Entry<String, Node> entry : nodes.entrySet()) {
			if(entry.getKey().matches("(.*A)")) {
				currentList.add(entry.getValue());
			}
		}
		
		// counts each node takes to reach Z
		List<Integer> counts = new ArrayList<Integer>();

		for(Node current : currentList) {
			int c = 0;
			System.out.println(current.code);
			// loop over instructions until we reach end
			while(!current.code.matches("(.*Z)")) {
				for(String instruction : instructions) {
					// grab next node then increment count
					int i = Integer.parseInt(instruction);
					current = nodes.get(current.next[i]);
					c++;
				}
			}
			counts.add(c);
		}
		// find lcm from counts (each node is the same distance from Z each loop)
		long lcm = counts.get(0);
		for(int count : counts) {
			lcm = findLCM(lcm, count);
		}
		
		System.out.println(lcm);
	}
	
	static long findLCM(long n1, long n2) {
	    if (n1 == 0 || n2 == 0) {
	        return 0;
	    }
	    long absNumber1 = Math.abs(n1);
	    long absNumber2 = Math.abs(n2);
	    
	    long absHigherNumber = Math.max(absNumber1, absNumber2);
	    long absLowerNumber = Math.min(absNumber1, absNumber2);
	    long lcm = absHigherNumber;
	    
	    while (lcm % absLowerNumber != 0) {
	        lcm += absHigherNumber;
	    }
	    return lcm;
	}
	
	class Node {
		String code;
		String[] next;
		String endpoint;
		
		Node(String code, String[] next) {
			this.code = code;
			this.next = next;
			
			this.endpoint = "";
		}
		
		@Override
		public String toString() {
			return this.code + " = " + Arrays.toString(this.next) + " ---> " + endpoint;
		}
 	}
}

class DayEight implements Day {

	@Override
	public void run(Scanner in) {
		String[] instructions = in.nextLine().replace('L', '0').replace('R', '1').split("");
		in.nextLine();
		
		HashMap<String, String[]> nodes = new HashMap<String, String[]>();

		while(in.hasNextLine()) {
			String line = in.nextLine();
			
			String code = line.split("=")[0].trim();
			String left = line.split(",")[0].split("\\(")[1];
			String right = line.split(", ")[1].replace(")", "");

			nodes.put(code, new String[] {left, right});
		}

		String[] current = nodes.get("AAA");
		
		int count = 0;
		
		// loop over instructions until we reach end
		while(current != nodes.get("ZZZ")) {
			for(String instruction : instructions) {
				// grab next node then increment count
				int i = Integer.parseInt(instruction);
				current = nodes.get(current[i]);
				
				count++;
			}
		}

		
		System.out.println(count);
	}
}
