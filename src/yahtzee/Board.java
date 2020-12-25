package yahtzee;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private Timer timer;
	private final int DELAY = 10;
	private Dice diceOne;
	private Dice diceTwo;
	private Dice diceThree;
	private Dice diceFour;
	private Dice diceFive;

	public Board() {
		initBoard();
	}

	private void initBoard() {
		Color darkGreen = new Color(0, 130, 0);
		setBackground(darkGreen);
		setFocusable(true);
		
		diceOne   = new Dice();
		diceTwo	  = new Dice();
		diceThree = new Dice();
		diceFour  = new Dice();
		diceFive  = new Dice();
		
		diceOne.initDice();
		diceTwo.initDice();
		diceThree.initDice();
		diceFour.initDice();
		diceFive.initDice();
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(diceOne.getDice(), diceOne.getX(), 
				diceOne.getY(), this);
		g2d.drawImage(diceTwo.getDice(), diceTwo.getX(), 
				diceTwo.getY(), this);
		g2d.drawImage(diceThree.getDice(), diceThree.getX(), 
				diceThree.getY(), this);
		g2d.drawImage(diceFour.getDice(), diceFour.getX(), 
				diceFour.getY(), this);
		g2d.drawImage(diceFive.getDice(), diceFive.getX(), 
				diceFive.getY(), this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}

	private void step() {
		repaint(diceOne.getX()-1, diceOne.getY()-1,
				diceOne.getWidth()+2, diceOne.getHeight()+2);
		repaint(diceTwo.getX()-1, diceTwo.getY()-1,
				diceTwo.getWidth()+2, diceTwo.getHeight()+2);
		repaint(diceThree.getX()-1, diceThree.getY()-1,
				diceThree.getWidth()+2, diceThree.getHeight()+2);
		repaint(diceFour.getX()-1, diceFour.getY()-1,
				diceFour.getWidth()+2, diceFour.getHeight()+2);
		repaint(diceFive.getX()-1, diceFive.getY()-1,
				diceFive.getWidth()+2, diceFive.getHeight()+2);
	}
	
	public Dice getDiceOne() {
		return diceOne;
	}
	
	public Dice getDiceTwo() {
		return diceTwo;
	}
	
	public Dice getDiceThree() {
		return diceThree;
	}
	
	public Dice getDiceFour() {
		return diceFour;
	}
	
	public Dice getDiceFive() {
		return diceFive;
	}
}
