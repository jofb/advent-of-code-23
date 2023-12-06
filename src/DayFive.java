import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class DayFivePartTwo implements Day {

	@Override
	public void run(Scanner in) {
		
		List<List<SeedMap>> allSeedMaps = new ArrayList<List<SeedMap>>();

		String[] seedPairs = in.nextLine().split(": ")[1].split(" ");
		in.nextLine();
		
		// encode seed pairs as ranges
		List<Range> seedRanges = new ArrayList<Range>();
		for(int i = 0; i <= seedPairs.length - 2; i+= 2) {
			long start = Long.parseLong(seedPairs[i]);
			long end = start + Long.parseLong(seedPairs[i + 1]);
			seedRanges.add(new Range(start, end));
		}

		// create list of lines for formatting
		List<String> lines = new ArrayList<String>();
		while(in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		lines.add("");
		
		// iterate over lines and format into seed maps
		List<String> maps = new ArrayList<String>();
		for(String line: lines) {

			if(line.contains(":")) continue;
			
			if(line != "") maps.add(line);
			else {
				List<SeedMap> seedMaps = new ArrayList<SeedMap>();
				// exclude last element from array, then add to list of lines
				for(String s : maps) {
					String[] split = s.split(" ");
					long dest = Long.parseLong(split[0]);
					long source = Long.parseLong(split[1]);
					long range = Long.parseLong(split[2]);

					seedMaps.add(new SeedMap(source, dest, range));
				}
				allSeedMaps.add(seedMaps);
				maps.clear();
			}
		}

		// iterate over each mapping layer, recursively chunking ranges to be mapped
		for(List<SeedMap> seedMaps : allSeedMaps) {
			List<Range> currentRangeList = new ArrayList<Range>();
			for(Range range : seedRanges) {
				currentRangeList.addAll(recursiveBoundarySplitter(range, seedMaps));
			}
			seedRanges = currentRangeList;
		}
		// find the lowest range
		seedRanges.sort(null);
		System.out.println(seedRanges.get(0).start);
		
	}
	
	List<Range> recursiveBoundarySplitter(Range range, List<SeedMap> seedMaps) {
		// list to return
		List<Range> list = new ArrayList<Range>();
		
		// iterate over each mapping and split ranges as necessary
		for(SeedMap sm : seedMaps) {
			// test each case
			long smStart = sm.source;
			long smEnd = sm.source + sm.range;

			if(range.start >= smStart && range.end <= smEnd) {
				// within range, map and add to list for return
				Range mapped = new Range(sm.map(range.start), sm.map(range.end));
				list.add(mapped);
				break;
			}
			// outside range with overlap cases
			else if(range.start < smEnd && range.start >= smStart && range.end > smEnd) {
				// split the ranges
				Range r1 = new Range(range.start, smEnd);
				Range r2 = new Range(smEnd, range.end);
				// map r1 and add to list
				Range mapped = new Range(sm.map(r1.start), sm.map(r1.end));
				list.add(mapped);
				
				// split boundaries on new range then add to list
				List<Range> nlist = recursiveBoundarySplitter(r2, seedMaps);
				list.addAll(nlist);
				break;
			}
			else if(range.end > smStart && range.start < smStart && range.end <= smEnd) {
				// split the ranges
				Range r1 = new Range(smStart, range.end);
				Range r2 = new Range(range.start, smStart);
				// map r1 and add to list
				Range mapped = new Range(sm.map(r1.start), sm.map(r1.end));
				list.add(mapped);
				
				// split boundaries on new range then add to list
				List<Range> nlist = recursiveBoundarySplitter(r2, seedMaps);
				list.addAll(nlist);
				break;
			}
		}
		// in the case that range has no overlap, just continue
		if(list.isEmpty()) list.add(range);
		return list;
	}
	
	class Range implements Comparable<Range> {
		
		long start;
		long end;
		
		Range(long start, long end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d)", this.start, this.end);
		}

		@Override
		public int compareTo(Range r) {
			return Long.compare(this.start, r.start);
		}
	}
	
}


class DayFive implements Day {

	@Override
	public void run(Scanner in) {

		String[] seeds = in.nextLine().split(": ")[1].split(" ");
		in.nextLine();
		
		List<String> maps = new ArrayList<String>();
		
		List<List<SeedMap>> allSeedMaps = new ArrayList<List<SeedMap>>();

		// create list of lines
		List<String> lines = new ArrayList<String>();
		while(in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		lines.add("");
		
		for(String line: lines) {

			if(line.contains(":")) continue;
			
			if(line != "") maps.add(line);
			else {
				List<SeedMap> seedMaps = new ArrayList<SeedMap>();
				// exclude last element from array, then add to list of lines
				for(String s : maps) {
					String[] split = s.split(" ");
					long dest = Long.parseLong(split[0]);
					long source = Long.parseLong(split[1]);
					long range = Long.parseLong(split[2]);

					seedMaps.add(new SeedMap(source, dest, range));
				}
				allSeedMaps.add(seedMaps);
				maps.clear();
			}
		}
		System.out.println(allSeedMaps);
		
		List<Long> locations = new ArrayList<Long>();
		
		for(String s : seeds) {
			long num = Long.parseLong(s);
			for(List<SeedMap> map : allSeedMaps) {
				for(SeedMap m : map) {
					if(!m.check(num)) continue;
					num = m.map(num);
					break;
				}	
			}
			locations.add(num);
		}
		
		long lowest = locations.stream().reduce(Long.MAX_VALUE, (a, b) -> a < b ? a : b);
		
		System.out.println(lowest);
		
	}
}

class SeedMap implements Comparable<SeedMap> {
	long source;
	long dest;
	long range;
	
	SeedMap(long source, long dest, long range) {
		this.source = source;
		this.dest = dest;
		this.range = range;
	}
	
	boolean check(long n) {
		return n >= source && n <= source + range;
	}
	
	long reverseMap(long n) {
		if(n >= dest && n <= dest + range) {
			long diff = n - dest;
			return diff + source;
		}
		return n;
	}

	long map(long n) {
		if(n >= source && n <= source + range) {
			long diff = n - source;
			return diff + dest;
		}
		return n;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d) -> (%d, %d)", source, range, dest, range);
	}

	@Override
	public int compareTo(SeedMap sm) {
		// TODO Auto-generated method stub
		return Long.compare(this.dest, sm.dest);
	}
}