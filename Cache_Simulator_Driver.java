import java.util.Scanner;
/**
 * Driver for cache simulator.
 * @author Gajjan Jasani
 * @version 04.2016
 */
public class Cache_Simulator_Driver {
	/**The number of hits.*/
	static double hits = 0;
	/**The number of misses.*/
	static double misses = 0;
	
	/**
	 * Main method of driver. Reads data in from file and reads/writes to cache.
	 */
	public static void main(String[] args){
		
		int numOfSets = 0; //number of sets in the cache
		int associativity = 0; //set size
		int lineSize = 0; //size of each line in the cache
		int lineCounter = 0; //counter of which line is currently being read from the file
		int lineNum = 0; //index within the cache
		
		Cache_Simulator cs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {			
			long start = System.currentTimeMillis();
			long end = start + 3*1000; // 3 seconds * 1000 ms/sec
			while (System.currentTimeMillis() < end){
			while(sc.hasNextLine()){
				lineCounter++;
				String line = sc.nextLine();
				if (lineCounter == 1) { //Reads in the first line of the file and parses out the number of sets.
					numOfSets = Integer.parseInt(line.replaceAll("[^\\d.]", ""));
					if ((numOfSets & (numOfSets-1)) != 0){
						System.out.println("Number of sets is not a power of 2");
						System.exit(0);
					}
				} else if (lineCounter == 2) { //Reads in the second line of the file and parses out the associativity.
					associativity = Integer.parseInt(line.replaceAll("[^\\d.]", ""));
				} else if (lineCounter == 3) { //Reads in the third line of the file and parses out the line size.
					lineSize = Integer.parseInt(line.replaceAll("[^\\d.]", ""));
					if (lineSize < 4 || ((lineSize & (lineSize-1)) != 0) ){
						System.out.println("Line size is less then 4 or not a power of 2");
						System.exit(0);
					}
					cs = new Cache_Simulator(numOfSets, associativity, lineSize);
				} else { //Reads in the remaining data from the file and parses the address, address size, and read/write.
					String[] data = line.split(":");
					int address = hex_to_int(data[0]);
					int addressSize = Integer.parseInt(data[2].replaceAll("[^\\d.]", ""));
					if(address % addressSize != 0){
						System.out.println("Address is not aligned properly");
						System.exit(0);
					}
					
					String access = data[1];
					
					//Creates a new cache line and reads/writes appropriately.
					cs.lines[lineNum] = cs.new Line(address, access, addressSize);
					if (lineNum == 0){
						misses++;
						cs.lines[lineNum].result = "miss";
						cs.lines[lineNum].memRefs = 1;
					}
					boolean missCheck = true;
					for (int i = 0; i < lineNum; i++){
						if(cs.lines[lineNum].index == cs.lines[i].index &&
								cs.lines[lineNum].tag == cs.lines[i].tag ){
							hits++;
							cs.lines[lineNum].result = "hit";
							cs.lines[lineNum].memRefs = 0;
							missCheck = false;
							break;
						}
					}
					if(lineNum != 0){
						if(missCheck){
							misses++;
							cs.lines[lineNum].result = "miss";
							cs.lines[lineNum].memRefs = 1;
						} else {
							missCheck = true;
						}
					}
					lineNum++;
					cs.printResultsFormat();
					printSummary();
				}
			}
		}
//			cs.printResultsFormat();
			
		} catch (NumberFormatException e) {
			System.out.println("Number formate exception: "+e.getMessage());
		} finally {
			sc.close();
		}
	}
	
	/**
	 * Converts a hexidecimal string into an integer.
	 * @param s hexidecimal string to be converted.
	 * @return converted integer value.
	 */
	public static int hex_to_int(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
	
	/**
	 * Prints a summary of hits, misses, accesses, hit ratio, and miss ratio.
	 */
	public static void printSummary(){
		
		System.out.println("\n\nSimulation Summary Statistics");
		System.out.println("-----------------------------");
		System.out.println("Total hits\t\t: "+hits);
		System.out.println("Total misses\t: "+misses);
		double accesses = hits+misses;
		System.out.println("Total accesses\t: "+ accesses);
		System.out.println("Hit ratio\t\t: "+(hits/accesses));
		System.out.println("Miss ratio\t: "+(misses/accesses));
		System.out.println("\n");
		
	}

}
