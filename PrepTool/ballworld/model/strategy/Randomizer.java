package ballworld.model.strategy;

import java.awt.*;

/**
 * Utility class that supplies static class routines for generating various
 * random values
 */
public class Randomizer {

	/**
	 * Generates a random location point subject to the constraint that
	 * 0<=X<=maxX and 0<=Y<=maxY.
	 */
	static public Point randomLoc(Rectangle rect) {
		return (new Point(randomInt(0, rect.width), randomInt(0, rect.height)));
	}

	/**
	 * Generates a random location point subject to the constraint that
	 * 0<=X<=maxX and 0<=Y<=maxY.
	 */
	static public Point randomLoc(Dimension dim) {
		return (new Point(randomInt(0, dim.width), randomInt(0, dim.height)));
	}

	/**
	 * Returns a random integer greater than or equal to min and less than or
	 * equal to max.
	 */
	static public int randomInt(int min, int max) {
		return (int) Math.floor((Math.random() * (1 + max - min)) + min);
	}

	/**
	 * Returns a random double greater than or equal to min and less than or
	 * equal to max.
	 */
	static public double randomDouble(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	/**
	 * Returns a random velocity (as a Point) subject to the constraint that the
	 * absolute value of the vleocity (speed) is less than maxV.
	 */
	static public Point randomVel(Rectangle rect) {
		return (new Point(randomInt(-rect.width, rect.width), randomInt(
				-rect.height, rect.height)));
	}

	static public Dimension randomDim(Dimension maxDim) {
		int x = randomInt(maxDim.width / 2, maxDim.width);
		return new Dimension(x, x);
	}

	/**
	 * Generates a randomly located and sized rectangle
	 */
	static public Rectangle randomBounds(Rectangle rect, Dimension maxDim) {
		return new Rectangle(randomLoc(rect), randomDim(maxDim));
	}

	/**
	 * Generates a random color
	 */
	static public Color randomColor() {
		return new Color(randomInt(0, 255), randomInt(0, 255),
				randomInt(0, 255));
	}
}
