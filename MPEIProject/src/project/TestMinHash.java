package project;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TestMinHash {
	
	public static void main(String[] args) {
		
		System.out.println("------- Similarity algorithm Strings Test -------");
		int numHash = 50;
		int k = 2;
		MinHash minhash = new MinHash(numHash);
		
		String text1 = "Universidade de Aveiro";
		String text2 = "Universidade do Porto";
		String text3 = "Universidade de Aveiro";
		String text4 = "string aveiro";
		
		Set<String> set1 = ShingleUtils.splitToShingles(text1, k);
		Set<String> set2 = ShingleUtils.splitToShingles(text2, k);
		Set<String> set3 = ShingleUtils.splitToShingles(text3, k);
		Set<String> set4 = ShingleUtils.splitToShingles(text4, k);
		
		System.out.println("Similarity 1: "+minhash.jaccardSimilarity(set1, set2));
		System.out.println("Similarity 2: "+minhash.jaccardSimilarity(set1, set3));
		System.out.println("Similarity 2: "+minhash.jaccardSimilarity(set1, set4));
		
		System.out.println("\n------- Random Strings Test -------");
		k = 2;
		int N = 100000;
		double threshold = 0.1;
		String mainGeneratedString = randomString(); //Will compare N strings to 'mainGeneratedString'
		int withinThreshold = 0; //Will count the number of strings that are within the threshold
		for(int i = 0; i < N; i++) {
			String random = randomString();
			double jacSim = minhash.jaccardSimilarity(ShingleUtils.splitToShingles(random,k),
				  			 	  					  ShingleUtils.splitToShingles(mainGeneratedString,k));
			//System.err.println(jacSim);
			if(jacSim >= threshold) withinThreshold++;
		}
		System.out.println("Number of string within the threshold: "+withinThreshold);
	}
	
	public static String randomString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 70; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
}
