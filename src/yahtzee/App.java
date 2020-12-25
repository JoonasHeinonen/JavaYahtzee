package yahtzee;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
public class App extends JFrame {
	private static JButton diceButtonOne;
	private static JButton diceButtonTwo;
	private static JButton diceButtonThree;
	private static JButton diceButtonFour;
	private static JButton diceButtonFive;
	private static JButton recastButton;
	
	private JLabel throwText;
	private JPanel buttonPanel;
	private Board board;
	
	private static String name;
	private int[] values = {1, 2, 3, 4, 5};
	private static int amountOfThrows;
	private boolean defineVictory;
	private static boolean won = false;
	
	private int width = 500;
	private int height = 300;
	
	static String jdbcUrl  = "jdbc:mysql://localhost:3306/java_yahtzee";
	static String username = "root";
	static String password = "";
	
	private static final String CREATE_TABLE_SQL="CREATE TABLE java_yahtzee.game_records ("
            + "name VARCHAR(45) NOT NULL,"
            + "throws INT NOT NULL,"
            + "date_played DATE NOT NULL);";

	public App() {
		initUI();
	}
	
	public void diceButtonPressed(Dice dice, JButton button) {
		if (amountOfThrows > 0) {
			if (dice.getLocked() == false) {
				dice.setLocked(true);
				button.setText("X");
			} else if (dice.getLocked() == true) {
				dice.setLocked(false);
				button.setText("0");
			}
		} else {
			JOptionPane.showMessageDialog(null, "You must start the game by recasting the dices!", "Locking prevented", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static boolean checkValues(int[] array) {
        if (array == null || array.length == 1)
        	return true;

        int compare = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] != compare)
                return false;
        }

        return true;
    }
	
	private void initUI() {
		board = new Board();
		add(board);
		
		setSize(this.width, this.height);
		setBackground(Color.gray);
		setTitle("Java Yahtzee");

		Color darkBrown = new Color(150, 40, 20);
		buttonPanel = new JPanel();

		recastButton    = new JButton("Recast");
		diceButtonOne   = new JButton("0");
		diceButtonTwo   = new JButton("0");
		diceButtonThree = new JButton("0");
		diceButtonFour  = new JButton("0");
		diceButtonFive  = new JButton("0");
		throwText = new JLabel("Number of throws: " + amountOfThrows);
		throwText.setForeground(Color.WHITE);
		
		buttonPanel.add(recastButton);
		buttonPanel.add(recastButton);
		buttonPanel.add(diceButtonOne);
		buttonPanel.add(diceButtonTwo);
		buttonPanel.add(diceButtonThree);
		buttonPanel.add(diceButtonFour);
		buttonPanel.add(diceButtonFive);
		buttonPanel.add(throwText);
		buttonPanel.setBackground(darkBrown);
		buttonPanel.setLayout(new FlowLayout());
		
		board.add(buttonPanel);
		
		board.getDiceOne().setX((width / 2) - (90 * 2) - 40);
		board.getDiceOne().setY(100);
		board.getDiceTwo().setX((width / 2) - (90) - 40);
		board.getDiceTwo().setY(100);
		board.getDiceThree().setX((width / 2)  - 40);
		board.getDiceThree().setY(100);
		board.getDiceFour().setX((width / 2) + (90) - 40);
		board.getDiceFour().setY(100);
		board.getDiceFive().setX((width / 2) + (90 * 2) - 40);
		board.getDiceFive().setY(100);
		
		name = JOptionPane.showInputDialog("Enter your name: (Leave empty for no name)");			
		if  (name == null || name == "" || name.length() < 1) {
			name = "unknown player";
		}
		JOptionPane.showMessageDialog(null, "Welcome, " + name + "!");

		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		diceButtonOne.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				diceButtonPressed(board.getDiceOne(), diceButtonOne);
			}
		});
		
		diceButtonTwo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				diceButtonPressed(board.getDiceTwo(), diceButtonTwo);
			}
		});
		
		diceButtonThree.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				diceButtonPressed(board.getDiceThree(), diceButtonThree);
			}
		});
		
		diceButtonFour.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				diceButtonPressed(board.getDiceFour(), diceButtonFour);
			}
		});
		
		diceButtonFive.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				diceButtonPressed(board.getDiceFive(), diceButtonFive);
			}
		});
	
		recastButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				recastButtonPressed();
			}
			
			private void recastButtonPressed() {

				if (!won) {
					amountOfThrows = amountOfThrows + 1;
					
					if (amountOfThrows > 0) {
						if(board.getDiceOne().getLocked() == true && board.getDiceTwo().getLocked() == true && 
						   board.getDiceThree().getLocked() == true && board.getDiceFour().getLocked() == true && 
						   board.getDiceFive().getLocked() == true) {
							JOptionPane.showMessageDialog(null, "You should have at least one dice open!", "No dices open!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							board.getDiceOne().initDice();
							board.getDiceTwo().initDice();		
							board.getDiceThree().initDice();
							board.getDiceFour().initDice();
							board.getDiceFive().initDice();
							
							int diceOneValue = board.getDiceOne().getValue();
							int diceTwoValue = board.getDiceTwo().getValue();
							int diceThreeValue = board.getDiceThree().getValue();
							int diceFourValue = board.getDiceFour().getValue();
							int diceFiveValue = board.getDiceFive().getValue();
							
							values[0] = diceOneValue;
							values[1] = diceTwoValue;
							values[2] = diceThreeValue;
							values[3] = diceFourValue;
							values[4] = diceFiveValue;
							
							System.out.println("\nNumber of throws: " + amountOfThrows + "\n");
							throwText.setText("Number of throws: " + amountOfThrows);
							
							int i = 0;
							for (i = 0; i < values.length; i++) {
								System.out.print(" [" + values[i] + "] ");
							}
							i = 0;
							
							defineVictory = checkValues(values);
							
							if (defineVictory) {
								Connection recordConn = null;
								Statement recordStmt;
								Timestamp timestamp = new Timestamp(System.currentTimeMillis());
								
								try {
									recordConn = DriverManager.getConnection(jdbcUrl, username, password);
									recordStmt = recordConn.createStatement();
									recordStmt.executeUpdate("INSERT INTO game_records " + "VALUES ('" + name + "', '" + amountOfThrows + "', '" + timestamp + "')");
									System.out.println("Created new record!");
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								JOptionPane.showMessageDialog(null, "Congratulations, " + name + "!\nYou've got the Yahtzee!", "Yahtzee!", JOptionPane.PLAIN_MESSAGE);
								System.out.println("\nYahtzee!");
								won = true;
							} else {
								System.out.println("\nNot yet...");
							}
						}
					}
				} else if (won) {
					JOptionPane.showMessageDialog(null, "Restarting the game!", "Restart", JOptionPane.PLAIN_MESSAGE);
					amountOfThrows = 0;
					won = false;
					
					board.getDiceOne().setLocked(false);
					board.getDiceTwo().setLocked(false);
					board.getDiceThree().setLocked(false);
					board.getDiceFour().setLocked(false);
					board.getDiceFive().setLocked(false);
					
					diceButtonOne.setText("0");
					diceButtonTwo.setText("0");
					diceButtonThree.setText("0");
					diceButtonFour.setText("0");
					diceButtonFive.setText("0");
				}
			}
		});
	}
	
	public static void main(String[] args) {		
		Connection conn = null;
		Statement stmt  = null;
		
		try {
			// 1. Get a connection to database
			conn = DriverManager.getConnection(jdbcUrl, username, password);
			// 2. Create a statement
			stmt = conn.createStatement();
			// 3. Execute SQL query
			stmt.executeUpdate(CREATE_TABLE_SQL);
			// 4. Process the result set
			System.out.println("Table created!");
		} catch (SQLException e) {
			e.printStackTrace();
	    } finally {
		    try {
		        // Close connection
		        if (stmt != null) {
		        	stmt.close();
		        }
		        if (conn != null) {
		        	conn.close();
		        }
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
		}
		EventQueue.invokeLater(() -> {
			App app = new App();
			app.setVisible(true);
		});
	}
}
