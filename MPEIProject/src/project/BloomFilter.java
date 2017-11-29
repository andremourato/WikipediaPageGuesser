package project;

public class BloomFilter {
	
	private boolean[] set; //Set of 1's and 0's that will contain the presence of the strings
	private int numHash;
	
	public BloomFilter(int N, int numHash){
		set = new boolean[N];
		this.numHash = numHash;
	}
	
	//Inserts a string in the set using 'k' hashfunctions
	public void insert(String elem) {
		
		String str = elem;
		
		for(int i = 1; i <= numHash; i++) {
			str += i;
			int hash = Math.abs(str.hashCode());
			hash = (hash % set.length) + 1;
			set[hash] = true;
		}
	}
	
	/*Checks if a string is a member
	*There are false positives
	*There are no false negatives
	That means the output of isMember(String,int) is either false or maybe
	*/
	public boolean isMember(String elem) {
		
		boolean member = true;
		String str = elem;
		
		for(int i = 1; i <= numHash; i++) {
			str += i;
			int hash = Math.abs(str.hashCode());
			hash = (hash % set.length) + 1;
			if(!set[hash]) {
				member = false;
				break;
			}
		}
		return member;
	}
}
