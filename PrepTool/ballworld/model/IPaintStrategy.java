package ballworld.model;

import java.awt.Graphics;

/**
 * interface of all paint strategies
 * @author Xu Zhang
 *
 */
public interface IPaintStrategy {
	
	/**
	 * paint the host ball on the graphics g
	 * @param g the Graphics object on which the ball will be painted 
	 * @param host the ball that will be painted
	 */
	void paint(Graphics g, Ball host);

	/**
	 * init the status
	 * @param host
	 */
	void init(Ball host);
}
