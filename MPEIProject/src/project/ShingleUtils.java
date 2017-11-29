package project;

import java.util.Set;
import java.util.TreeSet;


public class ShingleUtils {
	
	public static Set<String> splitToShingles(String text, int k) {
		
		Set<String> set = new TreeSet<>();
		for(int i = 0; i + k <= text.length(); i++) {
			set.add(text.substring(i,i+k));
		}
		return set;
	}
	
}
