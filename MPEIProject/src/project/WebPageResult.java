package project;

import java.net.URL;

/*
 * Used to display the results of the search engine
 * Each WebPageResult corresponds to a result of the search
 */
public class WebPageResult implements Comparable<WebPageResult>{
	
	URL url; //The web page's URL
	String title; //The web page's title
	double jaccardSim; //The jaccard similarity between this web page and the user's input
	
	public double jaccardSim() {
		return jaccardSim;
	}
	
	public WebPageResult(URL url, String title, double jaccardSim) {
		this.url = url;
		this.title = title;
		this.jaccardSim = jaccardSim;
	}

	@Override
	public int compareTo(WebPageResult page) {
		if(jaccardSim > page.jaccardSim)
			return -1;
		else if(jaccardSim < page.jaccardSim)
			return 1;
		else 
			return 0;
	}

	@Override
	public String toString() {
		return "Name: "+title + "\nURL: " + url + "\nJaccardSim: " + jaccardSim;
	}
	
	
}
