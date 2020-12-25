package yahtzee;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

public class Dice {
	private Image dice;
	private int x;
	private int y;
	private int value;
	private int h;
	private int w;
	private boolean locked = false;

	public Dice() {
		System.out.println("Created new dice object with the value of " + this + ".");
	}

	public void initDice() {
		ImageIcon ii;

		if (!locked) {
			this.value = castNewValue();

			switch (this.value) {
			case 1:
				ii = new ImageIcon("src/yahtzee/images/1.png");
				dice = ii.getImage();
				break;
			case 2:
				ii = new ImageIcon("src/yahtzee/images/2.png");
				dice = ii.getImage();
				break;
			case 3:
				ii = new ImageIcon("src/yahtzee/images/3.png");
				dice = ii.getImage();
				break;
			case 4:
				ii = new ImageIcon("src/yahtzee/images/4.png");
				dice = ii.getImage();
				break;
			case 5:
				ii = new ImageIcon("src/yahtzee/images/5.png");
				dice = ii.getImage();
				break;
			case 6:
				ii = new ImageIcon("src/yahtzee/images/6.png");
				dice = ii.getImage();
				break;
			default:
				System.out.println("The program is broken...");
			}
		}

		w = dice.getWidth(null);
		h = dice.getHeight(null);
	}

	public int castNewValue() {
		Random rand = new Random();
		int min = 1;
		int max = 7;
		int r = rand.nextInt(max - min) + min;

		return r;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setLocked(boolean l) {
		this.locked = l;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public Image getDice() {
		return dice;
	}
	
	public boolean getLocked() {
		return locked;
	}

	public int getValue() {
		return value;
	}
}