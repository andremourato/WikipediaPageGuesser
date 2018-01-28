# WikipediaPageGuesser

Wikipedia Page Guesser is divided into 2 parts.  
The first part consists in a game that will guess the Wikipedia page chosen by the user.  
The second part consists in the comparison of the content of a Wikipedia page written into a file. This will not be explained in this README.  
WikipediaPageGuesser uses a Java wiki bot framework. For more information on this framework please visit https://github.com/MER-C/wiki-java  
The java JTML Parser JSoup was also used. For more information visit https://jsoup.org/

# Modules #
  - Similarity Module
    - BloomFilter
    - MinHash
    - ShingleUtils
    - TestMinHash (used to test the MinHash)
    - TestBloomFilter (used to test the BloomFilter)
    
  - Wikipedia Page Content Module
    - SearchEngine
    - WebPageResult
    - WebPageUtils
    - WikiUtils

# Part One - Guess The Page #
1. Press the **Play** button  
- - - -  
<img src="https://user-images.githubusercontent.com/23279460/35401973-0b2db650-01f3-11e8-89e1-2aee6144e28b.png" width=510>  

- - - -  

The game will display the URL of 5 random Wikipedia pages.  
2. **Press one of the URLs.**   
- - - -  
<img src="https://user-images.githubusercontent.com/23279460/35406161-11bf69e8-0200-11e8-985d-f49b496ea6c4.png" width=510>

- - - -  
3. **Copy the content** of the selected page by pressing **CTRL+A** and **CTRL+C**  
- - - -  
<img src="https://user-images.githubusercontent.com/23279460/35406926-2347d32e-0202-11e8-988d-73b68d4e8df9.png" width=510>  

- - - -  
4. **Paste the copied content** into the text box in the game window  
- - - -  
<img src="https://user-images.githubusercontent.com/23279460/35407178-ffc70090-0202-11e8-98fc-6475e26aa156.png" width=510>  

- - - -  
5. Press the button **Guess**  
- - - -  
<img src="https://user-images.githubusercontent.com/23279460/35407293-61777464-0203-11e8-9164-47699bb7db3d.png" width=510>  

- - - -  
The game will guess and display the information about the page you chose.  

# How it works #
WikipediaPageGuesser will guess which page the user selected, not by detecting the button that was pressed,
but by using the MinHash algorithm to compare two strings of text: **the string pasted in the text box** and 
**the content fetched from the selected page** using the java wiki framework.  
This first part isn't focused on achieving the perfect similarity index, but in chosing the highest one from the 5 random pages.
