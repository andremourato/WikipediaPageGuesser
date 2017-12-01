package project;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class SearchEngineApp {

	private JFrame frame;
	private SearchEngine searchEngine;
	private JTextField pageContentField;
	private JTextPane allResultsField;
	private JTextPane guessedPageTitle;
	private JTextPane guessedPageURL;
	private JTextPane guessedPageSim;
	private JTable occurrencesText;
	
	/*Option buttons in the Game Screen*/
	private JLabel option1;
	private JLabel option2;
	private JLabel option3;
	private JLabel option4;
	private JLabel option5;
	
	/*Levels*/
	private final String MAIN_SCREEN = "name_497874984089806";
	private final String GAME_SCREEN = "name_657072334260825";
	private final String RESULT_SCREEN = "name_497882824621463";
	private final String ALLRESULTS_SCREEN = "name_657911603915068";
	private final String OCCURRENCES_SCREEN = "name_115368346727133";
	
	/*Flag*/
	private boolean hasGuessed = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SearchEngineApp window = new SearchEngineApp();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public SearchEngineApp() {
		searchEngine = new SearchEngine((int)1e6,6,70);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(new Color(255, 255, 255));
		frame.setTitle("Projeto MPEI - Search Engine");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		JPanel mainScreen = new JPanel();
		mainScreen.setBackground(new Color(255, 255, 255));
		mainScreen.setForeground(new Color(0, 0, 0));
		frame.getContentPane().add(mainScreen, "name_497874984089806");
		mainScreen.setLayout(null);

		JLabel lblNewLabel = new JLabel("Projeto MPEI");
		lblNewLabel.setForeground(new Color(72, 209, 204));
		lblNewLabel.setFont(new Font("Impact", Font.PLAIN, 80));
		lblNewLabel.setBounds(185, 161, 439, 131);
		mainScreen.add(lblNewLabel);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(new Color(255, 218, 185));
		btnPlay.setFont(new Font("Arial", Font.PLAIN, 20));
		btnPlay.setBounds(236, 305, 130, 40);
		mainScreen.add(btnPlay);
		
		JLabel nameLabelAndre = new JLabel("Andr\u00E9 Mourato 84745");
		nameLabelAndre.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabelAndre.setBounds(34, 473, 215, 34);
		mainScreen.add(nameLabelAndre);
		
		JLabel nameLabelGasalho = new JLabel("S\u00E9rgio Gasalho 84760");
		nameLabelGasalho.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabelGasalho.setBounds(34, 500, 215, 34);
		mainScreen.add(nameLabelGasalho);
		
		JButton btnCountPages = new JButton("Counter");
		btnCountPages.setFont(new Font("Arial", Font.PLAIN, 20));
		btnCountPages.setBackground(new Color(255, 218, 185));
		btnCountPages.setBounds(405, 305, 130, 40);
		mainScreen.add(btnCountPages);
		
		/*
		 * Result screen
		 */
		
		JPanel resultScreen = new JPanel();
		resultScreen.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(resultScreen, RESULT_SCREEN);
		resultScreen.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("You chose");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(64, 224, 208));
		lblNewLabel_1.setFont(new Font("Impact", Font.PLAIN, 50));
		lblNewLabel_1.setBounds(262, 110, 253, 43);
		resultScreen.add(lblNewLabel_1);
		
		JButton btnPlayAgain = new JButton("Play Again");
		btnPlayAgain.setFont(new Font("Arial", Font.PLAIN, 20));
		btnPlayAgain.setBackground(new Color(255, 218, 185));
		btnPlayAgain.setBounds(217, 437, 159, 40);
		resultScreen.add(btnPlayAgain);
		
		guessedPageTitle = new JTextPane();
		guessedPageTitle.setText("title");
		guessedPageTitle.setEditable(false);
		guessedPageTitle.setFont(new Font("Arial", Font.PLAIN, 20));
		guessedPageTitle.setBackground(Color.WHITE);
		guessedPageTitle.setBounds(344, 200, 325, 48);
		resultScreen.add(guessedPageTitle);
		
		JButton btnSeeAllResults = new JButton("See all results");
		btnSeeAllResults.setFont(new Font("Arial", Font.PLAIN, 20));
		btnSeeAllResults.setBackground(new Color(255, 218, 185));
		btnSeeAllResults.setBounds(409, 437, 159, 40);
		resultScreen.add(btnSeeAllResults);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setForeground(new Color(64, 224, 208));
		lblTitle.setFont(new Font("Impact", Font.PLAIN, 30));
		lblTitle.setBounds(157, 195, 166, 43);
		resultScreen.add(lblTitle);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUrl.setForeground(new Color(64, 224, 208));
		lblUrl.setFont(new Font("Impact", Font.PLAIN, 30));
		lblUrl.setBounds(70, 243, 253, 43);
		resultScreen.add(lblUrl);
		
		JLabel lblJaccard = new JLabel("Jaccard Similarity");
		lblJaccard.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJaccard.setForeground(new Color(64, 224, 208));
		lblJaccard.setFont(new Font("Impact", Font.PLAIN, 30));
		lblJaccard.setBounds(70, 291, 253, 43);
		resultScreen.add(lblJaccard);
		
		guessedPageURL = new JTextPane();
		guessedPageURL.setText("url");
		guessedPageURL.setFont(new Font("Arial", Font.PLAIN, 20));
		guessedPageURL.setEditable(false);
		guessedPageURL.setBackground(Color.WHITE);
		guessedPageURL.setBounds(344, 248, 446, 48);
		resultScreen.add(guessedPageURL);
		
		guessedPageSim = new JTextPane();
		guessedPageSim.setText("jaccard");
		guessedPageSim.setFont(new Font("Arial", Font.PLAIN, 20));
		guessedPageSim.setEditable(false);
		guessedPageSim.setBackground(Color.WHITE);
		guessedPageSim.setBounds(344, 294, 325, 48);
		resultScreen.add(guessedPageSim);
		
		JPanel gameScreen = new JPanel();
		gameScreen.setBackground(Color.WHITE);
		frame.getContentPane().add(gameScreen, GAME_SCREEN);
		gameScreen.setLayout(null);
		
		JLabel lblPickOneOf = new JLabel("Pick one of these URLs and copy its content");
		lblPickOneOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblPickOneOf.setForeground(new Color(64, 224, 208));
		lblPickOneOf.setFont(new Font("Impact", Font.PLAIN, 40));
		lblPickOneOf.setBounds(24, 56, 742, 50);
		gameScreen.add(lblPickOneOf);
		
		option1 = new JLabel();
		option1.setHorizontalAlignment(SwingConstants.CENTER);
		option1.setText("text1");
		option1.setFont(new Font("Arial", Font.PLAIN, 20));
		option1.setBounds(117, 154, 565, 34);
		option1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(option1.getText());
			}
		});
		gameScreen.add(option1);
		
		option2 = new JLabel();
		option2.setHorizontalAlignment(SwingConstants.CENTER);
		option2.setText("text2");
		option2.setFont(new Font("Arial", Font.PLAIN, 20));
		option2.setBounds(117, 201, 565, 34);
		option2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(option2.getText());
			}
		});
		gameScreen.add(option2);
		
		option3 = new JLabel();
		option3.setHorizontalAlignment(SwingConstants.CENTER);
		option3.setText("text3");
		option3.setFont(new Font("Arial", Font.PLAIN, 20));
		option3.setBounds(117, 248, 565, 34);
		option3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(option3.getText());
			}
		});
		gameScreen.add(option3);
		
		option4 = new JLabel();
		option4.setHorizontalAlignment(SwingConstants.CENTER);
		option4.setText("text4");
		option4.setFont(new Font("Arial", Font.PLAIN, 20));
		option4.setBounds(117, 295, 565, 34);
		option4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(option4.getText());
			}
		});
		gameScreen.add(option4);
		
		option5 = new JLabel();
		option5.setHorizontalAlignment(SwingConstants.CENTER);
		option5.setText("text5");
		option5.setFont(new Font("Arial", Font.PLAIN, 20));
		option5.setBounds(117, 342, 565, 34);
		option5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openBrowser(option5.getText());
			}
		});
		gameScreen.add(option5);
		
		pageContentField = new JTextField();
		pageContentField.setFont(new Font("Arial", Font.PLAIN, 15));
		pageContentField.setBounds(140, 401, 519, 34);
		gameScreen.add(pageContentField);
		pageContentField.setColumns(10);
		
		JButton btnGuess = new JButton("Guess");
		btnGuess.setFont(new Font("Arial", Font.PLAIN, 20));
		btnGuess.setBackground(new Color(255, 218, 185));
		btnGuess.setBounds(316, 468, 159, 40);
		gameScreen.add(btnGuess);
		
		JPanel allResultsScreen = new JPanel();
		allResultsScreen.setBackground(Color.WHITE);
		frame.getContentPane().add(allResultsScreen, ALLRESULTS_SCREEN);
		allResultsScreen.setLayout(null);
		
		JLabel lblAllResults = new JLabel("All Results");
		lblAllResults.setHorizontalAlignment(SwingConstants.LEFT);
		lblAllResults.setForeground(new Color(64, 224, 208));
		lblAllResults.setFont(new Font("Impact", Font.PLAIN, 50));
		lblAllResults.setBounds(111, 65, 253, 43);
		allResultsScreen.add(lblAllResults);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 20));
		btnBack.setBackground(new Color(255, 218, 185));
		btnBack.setBounds(308, 466, 159, 40);
		allResultsScreen.add(btnBack);
		
		allResultsField = new JTextPane();
		allResultsField.setFont(new Font("Arial", Font.PLAIN, 20));
		allResultsField.setEditable(false);
		allResultsField.setBounds(111, 158, 579, 275);
		allResultsScreen.add(allResultsField);
		
		JScrollPane scrollPane = new JScrollPane(allResultsField);
		scrollPane.setBounds(80, 132, 640, 300);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allResultsScreen.add(scrollPane);
		
		JPanel occurrencesScreen = new JPanel();
		occurrencesScreen.setBackground(Color.WHITE);
		frame.getContentPane().add(occurrencesScreen, OCCURRENCES_SCREEN);
		occurrencesScreen.setLayout(null);

		JLabel lblNumberOfOccurrences = new JLabel("Number of Occurrences");
		lblNumberOfOccurrences.setBounds(87, 57, 587, 43);
		lblNumberOfOccurrences.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfOccurrences.setForeground(new Color(64, 224, 208));
		lblNumberOfOccurrences.setFont(new Font("Impact", Font.PLAIN, 50));
		occurrencesScreen.add(lblNumberOfOccurrences);
		
		occurrencesText = new JTable();
		try {
			occurrencesText = WebPageUtils.loadTable(
								WebPageUtils.loadPageOccurrencesFromFile("page_content.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		occurrencesText.setEnabled(false);
		occurrencesText.setBounds(87, 168, 620, 200);
		occurrencesText.setFont(new Font("Arial", Font.PLAIN, 18));
		occurrencesText.setFillsViewportHeight(true);
		occurrencesScreen.add(occurrencesText);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Arial", Font.PLAIN, 20));
		refreshButton.setBackground(new Color(255, 218, 185));
		refreshButton.setBounds(179, 440, 159, 40);
		occurrencesScreen.add(refreshButton);
		
		JButton backButtonOcurrences = new JButton("Back");
		backButtonOcurrences.setFont(new Font("Arial", Font.PLAIN, 20));
		backButtonOcurrences.setBackground(new Color(255, 218, 185));
		backButtonOcurrences.setBounds(422, 439, 159, 40);
		occurrencesScreen.add(backButtonOcurrences);
		
		/*
		 * Action Listeners
		 */
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen(GAME_SCREEN);
			}
		});
		btnPlayAgain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen(GAME_SCREEN);
			}
		});
		btnGuess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeScreen(RESULT_SCREEN);
			}
		});
		btnSeeAllResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen(ALLRESULTS_SCREEN);
			}
		});
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen(RESULT_SCREEN);
			}
		});
		btnCountPages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeScreen(OCCURRENCES_SCREEN);
			}
		});
		backButtonOcurrences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeScreen(MAIN_SCREEN);
			}
		});
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args0) {
				try {
					updateTable(WebPageUtils.loadTable(
							WebPageUtils.loadPageOccurrencesFromFile("page_content.txt")));
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
		
	}
	
	private void generateRandomPages() {
		
		try {
			String[] randPages = searchEngine.generateWikiRandomSites();
			option1.setText(randPages[0]);
			option2.setText(randPages[1]);
			option3.setText(randPages[2]);
			option4.setText(randPages[3]);
			option5.setText(randPages[4]);
			
		} catch (IOException e) {
			/*
			 * Will attempt to regenerate pages if it fails
			 */
			generateRandomPages();
		}
	}
	
	private void openBrowser(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
			System.exit(1);
		}
	}
	
	private void updateTable(JTable table) throws IOException {
		occurrencesText.setModel(new DefaultTableModel(
				WebPageUtils.loadData(
						WebPageUtils.loadPageOccurrencesFromFile("page_content.txt")),
						new String[] { "Page Title","Number of Occurrences" }));
		((AbstractTableModel) occurrencesText.getModel()).fireTableDataChanged();
		occurrencesText.repaint();
	}
	
	private void changeScreen(String name) {
		/* If it changes to the game screen, it will generate 5 random webpages */
		if(name.equals(GAME_SCREEN)) {
			clearInputTextField();
			hasGuessed = false;
			generateRandomPages();
		}/* Changed to Result Screen. Must guess the webpage */
		/*If this page is reached from all results page,
		 *  there is no need to run the algorithm again*/
		else if(name.contentEquals(RESULT_SCREEN) && !hasGuessed) {
			 //If the input was already guessed
			if(!guess(pageContentField.getText())) return;
			hasGuessed = true;
		}
		else if(name.equals(ALLRESULTS_SCREEN)) {
			showAllResults();
		}
		else if(name.equals(OCCURRENCES_SCREEN)){
			
		}
		/*Changes to the given screen*/
		((CardLayout)frame.getContentPane().getLayout())
		.show(frame.getContentPane(), name);
	}
	
	private void showAllResults() {
		
		List<WebPageResult> lastResults = searchEngine.getLastResults();
		String str = "";
		int num = 1;
		for(WebPageResult page : lastResults) {
			str += "Result "+(num++)+"\n"+page+"\n\n";
		}
		allResultsField.setText(str);
		
	}
	
	public void clearInputTextField() {
		pageContentField.setText("");
	}
	
	private boolean guess(String text) {
		if(text.length() == 0) {
			JOptionPane.showMessageDialog(null, "Invalid input.");
			return false;
		}
		try {
			if(!searchEngine.guess(text)) {
				JOptionPane.showMessageDialog(null, "Input content has already been guessed!");
				return false;
			}
		}catch(NoSuchElementException e){
			JOptionPane.showMessageDialog(null, "You did not copy the content of one of these webpages.\nYou tried to trick me!");
			changeScreen(GAME_SCREEN);
			return false;
		} catch (HeadlessException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		WebPageResult guessed = searchEngine.getGuessedPage();
		guessedPageTitle.setText(guessed.title);
		guessedPageURL.setText(guessed.url.toString());
		guessedPageSim.setText(guessed.jaccardSim+"");
		return true;
	}
}
