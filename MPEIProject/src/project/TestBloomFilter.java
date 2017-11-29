package project;

public class TestBloomFilter {
	
	public static void main(String[] args) {
		
		int k = 6;
		String[] paises = {"Angola","Portugal","Espanha","Franca","Alemanha","Luxemburgo","Russia"};
		String[] paises2 = {"Angola","Brasil","Luxemburgo","USA","Argentina","Colombia","Russia"};
		BloomFilter bloomfilter = new BloomFilter((int)1e6,k);
		
		System.out.println("--------- Inserting elements ---------");
		for(String pais : paises) {
			bloomfilter.insert(pais);
			System.out.println("Inserted country: "+pais);
		}
		
		System.out.println("\n--------- Checking elements ---------");
		for(String pais : paises2) {
			System.out.println("Country "+pais+(bloomfilter.isMember(pais)?" may be":" is not")+" in the set.");
		}
		
	}
	
}
