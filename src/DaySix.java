import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class DaySixPartTwo implements Day {

	@Override
    public void run(Scanner in) {
        // parse in times and distances
        String t = in.nextLine().split(":")[1].replaceAll(" ", "");
        String d = in.nextLine().split(":")[1].replaceAll(" ", "");
        
        long time = Long.parseLong(t);
        long distance = Long.parseLong(d);

        // solve quadratic, can only be real
        double discriminant = time * time - 4 * 1 * distance;
        double root1 = (-time + Math.sqrt(discriminant)) / (-2);
        double root2 = (-time - Math.sqrt(discriminant)) / (-2);
        
        int min = (int)root1;
        int max = (int)root2;
        if(time % 2 == 0) max--;

        int result = max - min;
        System.out.println(result);
    }
    
    List<Integer> filterList(String[] list) {
        List<Integer> toReturn = new ArrayList<Integer>();
        for(String s : list) {
            if(s.trim().isEmpty()) continue;
            toReturn.add(Integer.parseInt(s));
            
        }
        return toReturn;
    }

}

class DaySix implements Day {

	@Override
    public void run(Scanner in) {

        // parse in times and distances
        String[] t = in.nextLine().split(":")[1].trim().split(" ");
        String[] d = in.nextLine().split(":")[1].trim().split(" ");
        List<Integer> times = filterList(t);
        List<Integer> distances = filterList(d);
        
        List<Integer> results = new ArrayList<Integer>();
        
        // iterate over times and compute quadratic to determine max and min for each
        for(int i = 0; i < times.size(); i++) {
            int time = times.get(i);
            int distance = distances.get(i);
            // solve quadratic, can only be real
            double discriminant = time * time - 4 * 1 * distance;
            double root1 = (-time + Math.sqrt(discriminant)) / (-2);
            double root2 = (-time - Math.sqrt(discriminant)) / (-2);
            
            int min = (int)root1;
            int max = (int)root2;
            if(time % 2 == 0) max--;
            
            System.out.println(min);
            System.out.println(max);
            
            results.add(max - min);
        }
        
        // final result is multiplied results together
        int result = results.stream().reduce(1, (a, b) -> a * b);
        System.out.println(result);
    }
    
    List<Integer> filterList(String[] list) {
        List<Integer> toReturn = new ArrayList<Integer>();
        for(String s : list) {
            if(s.trim().isEmpty()) continue;
            toReturn.add(Integer.parseInt(s));
            
        }
        return toReturn;
    }

}
