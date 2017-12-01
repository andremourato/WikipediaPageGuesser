package project;

import java.util.Random;

public class TestBloomFilter {
	
	public static void main(String[] args) {
		
		int k = 6;
		String[] countries = {"Angola","Portugal","Espanha","Franca","Alemanha","Luxemburgo","Russia"};
		String[] countries2 = {"Angola","Brasil","Luxemburgo","USA","Argentina","Colombia","Russia"};
		BloomFilter bloomfilter = new BloomFilter((int)1e6,k);
		
		System.out.println("--------- Inserting elements ---------");
		for(String pais : countries) {
			bloomfilter.insert(pais);
			System.out.println("Inserted country: "+pais);
		}
		
		System.out.println("\n--------- Checking elements ---------");
		for(String pais : countries2) {
			System.out.println("Country "+pais+(bloomfilter.isMember(pais)?" may be":" is not")+" in the set.");
		}
		
		System.out.println("\n-------- Random Strings Test --------");

		int N = 100000;
		for(int i = 0; i < N; i++) {
			String random = randomString();
			bloomfilter.insert(random);
			//These elements must all be in the set, since they were just inserted
			//System.err.printf("%40s%s%s\n",random,(bloomfilter.isMember(random)?" is":" is not")," in the set");
		}
		/* None of these strings must be in the set,
		 * since the probability of being generating
		 *  2 equal strings is nearly 0%*/
		int numFalsePositives = 0;
		for(int i = 0; i < N; i++) {
			String random = randomString();
			boolean exists = bloomfilter.isMember(random);
			if(exists)
				numFalsePositives++;
			//System.err.printf("%40s%s%s\n",random,(exists?" is":" is not")," in the set");
		}
		System.out.printf("Probability of false positive: %.3f%%",100*((double)numFalsePositives/(double)N));
		
	}
	
	public static String randomString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		//generates a random length between 20 and 40 characters
		int length = (int)(20*Math.random()+20); 
		for (int i = 0; i < length; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
}
