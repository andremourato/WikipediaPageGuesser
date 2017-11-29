package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JTable;

import org.wikipedia.Wiki;

public abstract class WebPageUtils{
	
	private static String DELIMITADOR = ":::";
	
	public String getDelimitador() {
		return DELIMITADOR;
	}
	
	/*
	 * Each line of the file has the following format: PageTitle:::Content
	 * Returns Map<String,Integer> in the following format: (PageTitle,PageContent)
	 */
	public static Map<String, Integer> loadPageOccurrencesFromFile(String file) throws IOException{
		Map<String, Integer> occurrences = new TreeMap<>();
		List<String> lines = Files.readAllLines(Paths.get(file), Charset.defaultCharset());
		//Keeps track of the contents that have been read
		List<String> currentContentsRead = new ArrayList<>();
		for(int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] lineSplit = line.split(DELIMITADOR);
			/*
			 * lineSplit[0] = page title
			 * lineSplit[1] = page content
			 */
			/* hasPageContent works as a map.contains method.
			 * Checks if the given page content has already been read
			 */
			if(hasPageContent(currentContentsRead,lineSplit[1]))
				occurrences.put(lineSplit[0], occurrences.get(lineSplit[0])+1);
			else
				occurrences.put(lineSplit[0], 1);

			currentContentsRead.add(line); //The line has been read
		}
		return occurrences;
	}
	
	public static String occurrencesMapToString(Map<String,Integer> map) {
		String str = "";
		for(String key : map.keySet()) {
			str += String.format("%30s%5s\n",key,map.get(key));
		}
		return str;
	}
	
	public static JTable loadTable(Map<String,Integer> map) {
		Set<String> keys = map.keySet();
		Object[][] data = new String[keys.size()][2];
		Object[] sections = { "Page Title","Number of Occurrences" };
		int y = 0;
		for(String key : keys) {
			data[y][0] = key;
			data[y++][1] = String.valueOf(map.get(key));
		}		
		return new JTable(data, sections);
		
	}
	
	
	/*
	 * A version of contains() but uses the value of jaccard similarity
	 * and determines if an element exists by comparing with the threshold
	 */
	private static boolean hasPageContent(List<String> list, String content) {
		MinHash minhash = new MinHash(70);
		// there is a perfect match between page contents
		//therefore the threshold can be 1.0
		double threshold = 1.0;
		int k = 10;
		Set<String> contentSet = ShingleUtils.splitToShingles(MinHash.formatString(content), k);
		for(String line : list) {
			String value = line.split(DELIMITADOR)[1];
			double jacSim = minhash.jaccardSimilarity(
						ShingleUtils.splitToShingles(MinHash.formatString(value), k),
						contentSet
					);
			if(jacSim >= threshold)
				return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static String[] generateRandomPageContent(int N) {
		String[] randomURLS = new String[N];
		Wiki wiki = new Wiki("pt.wikipedia.org");
		for(int i = 0; i < N; i++) {
			randomURLS[i] = WikiUtils.randomSite(wiki);
		}
		return randomURLS;
	}
	
	public static void appendToFile(String[] pageURLS, String filename) throws IOException, URISyntaxException {
		List<String> lines = Files.readAllLines(Paths.get(filename),Charset.defaultCharset());
		PrintWriter pwf = new PrintWriter(new File(filename));
		for(String line : lines) {
			pwf.println(line);
		}
		for(String pageURL : pageURLS) {
			URL url = new URL(pageURL);
			//Stores each line in the following format: PageTitle:::PageContent
			pwf.println(WikiUtils.getName(url)+DELIMITADOR+WikiUtils.getFormatedURLContent(url));
		}
		pwf.close();
	}
	
	public static void exportToFile(String[] pageURLS, String filename) throws IOException {
		PrintWriter pwf = new PrintWriter(new FileOutputStream(new File(filename)),true);
		for(String pageURL : pageURLS) {
			URL url = new URL(pageURL);
			//Stores each line in the following format: PageTitle:::PageContent
			pwf.println(WikiUtils.getName(url)+DELIMITADOR+WikiUtils.getFormatedURLContent(url));
		}
		pwf.close();
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException, FileNotFoundException {
		String filename = "generated_page_content.txt";
		exportToFile(generateRandomPageContent(10000),filename);
		try {
			appendToFile(new String[] {"https://pt.wikipedia.org/wiki/Portugal","https://pt.wikipedia.org/wiki/Portugal"},filename);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
