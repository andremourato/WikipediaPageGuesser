package project;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.wikipedia.Wiki;

public class SearchEngine {

	private HashMap<String, List<WebPageResult>> history = new HashMap<>();
	private BloomFilter bf;
	private MinHash minhash;
	private Wiki wiki;
	private String[] randomWikiSites = new String[5];
	private String lastTextInput = null;
	
	/*
	 * Recommended values:
	 * bloomFilterSize - 1e6
	 * bloomFilterNumHash - 6
	 * numHashFunctions - 70
	 */
	
	@SuppressWarnings("deprecation")
	public SearchEngine(int bloomFilterSize, int bloomFilterNumHash, int numHashFunctions) {
		bf = new BloomFilter(bloomFilterSize,bloomFilterNumHash);
		minhash = new MinHash(numHashFunctions);
		wiki = new Wiki("pt.wikipedia.org");
	}
	
	/*
	 * Compares the inputText to the content of every webpage
	 * Choses the greatest value of the jaccard similarity
	 */
	public boolean guess(String inputText) throws IOException, NoSuchElementException{
		/*Checks if the input has already been searched.
		 *If both of these conditions are met, it means that
		 * the string has been previously searched. 'false' is returned
		 * The second condition must be checked because there may
		 * be false positives from the bloomfilter.
		 * The bloom filter isMember condition has been placed first
		 * since if it return false we have a guarantee that the element
		 * doesn't exist and do not require to invoke the second method (containsKey)*/
		if(bf.isMember(inputText) && history.containsKey(inputText)) {
			return false;
		}

		bf.insert(inputText); //Inserts inputText in the bloomfilter' set
		/*
		 * method search() will return the results in the following format:
		 * 	array[0] = page title
		 *  array[1] = parsed section name
		 *  array[2] = snippet of page text
		 *  
		/*If the previous condition is not met, it means that inputText hasn't been searched*/
		List<WebPageResult> resultList = new ArrayList<>();
		double threshold = 0.21;
		boolean validContentInserted = false;
		
		//Applies the similarity algorithm to every result webpage
		// randomWikiSites[] keeps track of the page titles
		for(int i=0;i<randomWikiSites.length;i++) {
			URL pageURL = new URL(randomWikiSites[i]);
			double jaccardSim = calculateJaccardSimilarity(
					inputText,
					WikiUtils.parseURLContentToString(pageURL)
			);
			/* If none of the jaccard similarities are greater or equal to the threshold,
			 * it most likely means that the user didn't input the content
			 * of one of the 5 pages that are displayed
			 * HE TRIED TO TRICK US!*/
			if(jaccardSim >= threshold) {
				validContentInserted = true;
			}
			resultList.add(new WebPageResult(
					pageURL,
					WikiUtils.getName(pageURL),
					jaccardSim
					));
		}
		if(!validContentInserted) throw new NoSuchElementException();
		//Sorts the list by jaccard similarity
		resultList = resultList.stream().sorted().collect(Collectors.toList());
		history.put(inputText,resultList); //Adds the search to the history
		lastTextInput = inputText;
		return true;
	}
	
	public String[] generateWikiRandomSites() throws IOException {
		return WikiUtils.generateWikiRandomSites(wiki, randomWikiSites);
	}
	
	/*
	 * Receives raw text and cleans it before calculating the jaccard similarity
	 */
	private double calculateJaccardSimilarity(String str1, String str2) {
		return minhash.jaccardSimilarity(
				ShingleUtils.splitToShingles(MinHash.formatString(str1), 10),
				ShingleUtils.splitToShingles(MinHash.formatString(str2), 10)
				);		
	}
	
	/*
	 * Gets the list of results from the last input
	 * Returns null if no input has been given yet
	 */
	public List<WebPageResult> getLastResults(){
		if(lastTextInput == null) return null;
		return history.get(lastTextInput);
	}
	
	/*
	 * Gets the last guessed page
	 * returns null if no input has been given yet
	 */
	
	public WebPageResult getGuessedPage() {
		if(lastTextInput == null) return null;
		return history.get(lastTextInput).get(0);		
	}
	
}
