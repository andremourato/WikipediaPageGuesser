package project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.wikipedia.Wiki;

public abstract class WikiUtils {
	
	/*
	 * returns a valid random webpage
	 * */
	public static String randomSite(Wiki w) {
		String url = null;
		do {
			try {
				url = getURL(w.random(),w).toString();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}while(!isValidPage(url));
		
		return url;
	}
	
	public static URL getURL(String pageTitle, Wiki argWiki) throws MalformedURLException {
		return new URL("https://"+argWiki.getDomain()+"/wiki/"+pageTitle.replace(" ","_"));
	}
	
	/*
	 * Retrieves only the text from HTML code using the library JSoup
	 * More information about this library is available at jsoup.org
	 */
	public static String parseHTMLToString(String str) {
		return Jsoup.parse(str).wholeText();
	}
	
	/*
	 * Same as parseHTMLToString(String) but receives an URL as argument
	 */
	public static String parseURLContentToString(URL url) throws IOException {
		return Jsoup.parse(url,10000).wholeText();//timeout of 10 seconds
	}
	
	public static String getFormatedURLContent(URL url) throws IOException {
		return MinHash.formatString(parseURLContentToString(url));
	}
	
	/* Checks if a URL is valid 
	 * If isn't, method parseURLContentToString(URL)
	 * will throw an exception
	 * */
	public static boolean isValidPage(String urlInString) {
		try {
			parseURLContentToString(new URL(urlInString));
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static String[] generateWikiRandomSites(Wiki wiki, String[] randomWikiSites){
		/* Changes the elements of randomWikiSites first */
		/*In some cases the program was throwing exceptions
		 * because the url returned by the library were not valid
		 * so I decided to check before accepting a URL
		 * as a random wiki site.*/
		for(int i=0;i<randomWikiSites.length;i++){
			String raw = null;
			do{
				raw = randomSite(wiki);
			}while(!isValidPage(raw));
			randomWikiSites[i]=raw;
		}
		return randomWikiSites;
	}
	
	public static String getName(URL url) {
		String[] splited = url.toString().split("/");
		return splited[splited.length-1].replace("_"," ");
	}
	
}
