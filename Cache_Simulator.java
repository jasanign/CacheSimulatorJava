/**
 * Models a cache.
 * @author Gajjan Jasani
 * @version 04.2016
 */
public class Cache_Simulator {
	/**The number of sets within the cache.*/
	private int numOfSets;
	/**The associativity(set size)*/
	private int associativity;
	/**The size of each line in the cache.*/
	private int lineSize;
	/**An array holding cache data.*/
	public Line[] lines  = new Line[50];
	
	/**Constructor that initializes each field.*/
	public Cache_Simulator(int numOfSets, int associativity, int lineSize){
		
		this.numOfSets = numOfSets;
		this.associativity = associativity;
		this.lineSize = lineSize;
//		for (int i = 0; i < numOfSets*associativity; i++){
//			lines[i] = null;
//		}
		
	}
	
	/**
	 * Prints results of each access.
	 */
	public void printResultsFormat(){
		
		System.out.println("Cache Configuration");
		System.out.println("\n\t"+numOfSets+" "+associativity+"-way set associative entries");
		System.out.println("\tof line size "+lineSize+" bytes\n\n");
		System.out.println("Results for Each Reference\n");
		System.out.println("Access\tAddress\t  Tag  \tIndex\tOffset\tResult\tMemrefs");
		System.out.println("------\t--------\t-------\t-----\t------\t------\t-------");
		for (int i = 0; i < lines.length; i++){
			if(lines[i] == null){
				continue;
			}
			System.out.println(" "+lines[i].access+"\t "+lines[i].mainAddress+"\t\t"+lines[i].tag+"\t\t"+
					lines[i].index+"\t\t"+lines[i].offset+"\t"+lines[i].result+"\t\t"+lines[i].memRefs);
		}
	}
	
	/**
	 * Models a single line in a cache.
	 * @author Gajjan Jasani
	 * @author Jonathan Dillingham
	 * @version 04.2016
	 */
	public class Line {
		
		/**offset of memory address*/
		private int offset;
		/**bits for the offset*/
		private int offsetBits;
		/**index of memory address*/
		public int index;
		/**bits for the index*/
		private int indexBits;
		/**tag of memory address*/
		public int tag;
		/**hexidecimal memory address*/
		private String mainAddress;
		/**int memory address*/
		private int address;
		/**memory access*/
		private String access;
		/**result of memory access*/
		public String result;
		/**number of memory references*/
		public int memRefs = 0;
		/**size of the memory address*/
		private int addressSize;
		
		/**
		 * Constructor for a Line.
		 * @param address memory address as an integer
		 * @param access result of memory access
		 * @param addressSize size of the memory address
		 */
		public Line (int address, String access, int addressSize){
			
			this.address = address;
			this.mainAddress = intToHex(address);
			if(access.toUpperCase().equals("R")){
				this.access = "read";
			} else if(access.toUpperCase().equals("W")){
				this.access = "write";
			}
			this.addressSize = addressSize;
			offsetFinder(this.address);
			indexFinder(this.address);
			tagFinder();
		}
		
		/**
		 * finds the offset of the memory address.
		 * @param address memory address
		 */
		private void offsetFinder(int address){
			this.offsetBits = log2(lineSize);
			this.offset = address & (lineSize-1);
			this.address = address >>> offsetBits;
		}
		
		/**
		 * finds the index of the memory address.
		 * @param address memory address
		 */
		private void indexFinder(int address){
			this.indexBits = log2(numOfSets);
			this.index = address & (numOfSets-1);
			this.address = address >>> indexBits;
			
		}
		
		/**
		 * sets the tag of the memory address
		 */
		private void tagFinder(){
			//int tag = (addressSize * 8) - (index + offset);
			this.tag = this.address; 
		}
		
		/** returns Log2 value  */
		private int log2 (int x) {
			return (int)(Math.log(x)/Math.log(2));
		}
		
		/**
		 * Converts integer to hexidecimal value.
		 * @param input integer value to be converted
		 * @return hexidecimal value as a string
		 */
		private String intToHex(int input){
			String hex_string;
			hex_string=Integer.toHexString(input);
			while(hex_string.length()<7){
				hex_string = " " + hex_string;
			}
			return hex_string;			
		}		
	}
}
