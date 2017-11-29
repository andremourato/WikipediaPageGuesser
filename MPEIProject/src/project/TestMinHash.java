package project;

import java.util.Set;

public class TestMinHash {
	
	public static void main(String[] args) {
		
		int numHash = 50;
		int k = 2;
		MinHash minhash = new MinHash(numHash);
		
		String text1 = "Universidade de Aveiro";
		String text2 = "Universidade do Porto";
		String text3 = "Universidade de Aveiro";
		
		Set<String> set1 = ShingleUtils.splitToShingles(text1, k);
		Set<String> set2 = ShingleUtils.splitToShingles(text2, k);
		Set<String> set3 = ShingleUtils.splitToShingles(text3, k);
		
		System.out.println("Index number 1: "+minhash.jaccardSimilarity(set1, set2));
		System.out.println("Index number 2: "+minhash.jaccardSimilarity(set1, set3));
		
		
	}
	
}
