import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class DaySevenPartTwo implements Day {

	static List<Character> ranks = new ArrayList<Character>(Arrays.asList('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'));

	@Override
	public void run(Scanner in) {
		List<Hand> hands = new ArrayList<Hand>();
		
		while(in.hasNextLine()) {
			String[] line = in.nextLine().split(" ");
			
			String cards = line[0];
			int bid = Integer.parseInt(line[1]);
			int priority = 0;

			// make a map of characters
			String[] chars = cards.split("");
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for(String c : chars) {
				if(!map.containsKey(c)) map.put(c, 0);
				map.put(c, map.get(c) + 1);
			}
			// need to check for a joker
			if(map.containsKey("J")) {
				int numJokers = map.get("J");
				// find the highest value and add number of jokers to it
				int maxValue = 0;
				String maxKey = "";
				for(Map.Entry<String, Integer> entry : map.entrySet()) {
					if(entry.getKey().equals("J")) continue;
					if(maxValue < entry.getValue()) {
						maxKey = entry.getKey();
						maxValue = entry.getValue();
					}
				}
				map.put(maxKey, maxValue + numJokers);
				map.remove("J");
			}
			// handle each case
			// can check size and then values inside for each case
			switch(map.size()) {
			case 1: // five of a kind
				priority = 1;
				break;
			case 2: // four of a kind & full house
				if(map.containsValue(4)) priority = 2;
				else priority = 3; 
				break;
			case 3: // three of a kind & two pair
				if(map.containsValue(3)) priority = 4;
				else priority = 5;
				break;
			case 4: // one pair
				priority = 6;
				break;
			case 5: // high card
				priority = 7;
				break;
			}
			Hand hand = new Hand(cards, priority, bid);
			hands.add(hand);
		}
		
		hands.sort(null);

		int total = 0;
		// compute bids based on rank (index)
		for(int i = 0; i < hands.size(); i++) {
			int totalBid = hands.get(i).bid * (hands.size() - i);
			total += totalBid;
		}
		
		System.out.println(total);
	}
	
	class Hand implements Comparable<Hand> {
		String cards;
		int priority;
		int bid;
		
		Hand(String cards, int priority, int bid) {
			this.cards = cards;
			this.priority = priority;
			this.bid = bid;
		}
		
		@Override
		public String toString() {
			return String.format("(Cards: %s | Bid: %d | Prio: %d)", this.cards, this.bid, this.priority);
		}

		@Override
		public int compareTo(Hand o) {
			int r1 = this.priority;
			int r2 = o.priority;
			
			// if equal, complete a contest
			if(r1 == r2) {
				for(int i = 0; i < this.cards.length(); i++) {
					// compare each character based on rank
					char c1 = this.cards.charAt(i);
					char c2 = o.cards.charAt(i);
					
					if(c1 == c2) continue;
					
					r1 = ranks.indexOf(c1);
					r2 = ranks.indexOf(c2);
					break;
				}
			}
			
			return Integer.compare(r1, r2);
		}
		
	}

}

class DaySeven implements Day {

	static List<Character> ranks = new ArrayList<Character>(Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'));

	@Override
	public void run(Scanner in) {
		List<Hand> hands = new ArrayList<Hand>();
		
		while(in.hasNextLine()) {
			String[] line = in.nextLine().split(" ");
			
			String cards = line[0];
			int bid = Integer.parseInt(line[1]);
			int priority = 0;

			// make a map of characters
			String[] chars = cards.split("");
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for(String c : chars) {
				if(!map.containsKey(c)) map.put(c, 0);
				map.put(c, map.get(c) + 1);
			}
			// handle each case
			// can check size and then values inside for each case
			switch(map.size()) {
			case 1: // five of a kind
				priority = 1;
				break;
			case 2: // four of a kind & full house
				if(map.containsValue(4)) priority = 2;
				else priority = 3; 
				break;
			case 3: // three of a kind & two pair
				if(map.containsValue(3)) priority = 4;
				else priority = 5;
				break;
			case 4: // one pair
				priority = 6;
				break;
			case 5: // high card
				priority = 7;
				break;
			}
			Hand hand = new Hand(cards, priority, bid);
			hands.add(hand);
		}
		
		hands.sort(null);

		int total = 0;
		// compute bids based on rank (index)
		for(int i = 0; i < hands.size(); i++) {
			int totalBid = hands.get(i).bid * (hands.size() - i);
			total += totalBid;
		}
		
		System.out.println(total);
	}
	
	class Hand implements Comparable<Hand> {
		String cards;
		int priority;
		int bid;
		
		Hand(String cards, int priority, int bid) {
			this.cards = cards;
			this.priority = priority;
			this.bid = bid;
		}
		
		@Override
		public String toString() {
			return String.format("(Cards: %s | Bid: %d | Prio: %d)", this.cards, this.bid, this.priority);
		}

		@Override
		public int compareTo(Hand o) {
			int r1 = this.priority;
			int r2 = o.priority;
			
			// if equal, complete a contest
			if(r1 == r2) {
				for(int i = 0; i < this.cards.length(); i++) {
					// compare each character based on rank
					char c1 = this.cards.charAt(i);
					char c2 = o.cards.charAt(i);
					
					if(c1 == c2) continue;
					
					r1 = ranks.indexOf(c1);
					r2 = ranks.indexOf(c2);
					break;
				}
			}
			
			return Integer.compare(r1, r2);
		}
		
	}

}
