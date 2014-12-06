package ballworld.model.paint.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import ballworld.model.Ball;
import ballworld.model.IPaintStrategy;

/**
 * the paint strategy of a ball
 * @author Xu Zhang
 *
 */
public class BallPaintStrategy implements IPaintStrategy {

	private Ellipse2D.Double ball;

	private AffineTransform at;

	/**
	 * constructor
	 */
	public BallPaintStrategy() {
		ball = new Ellipse2D.Double();
		at = new AffineTransform();
	}

	/**
	 * constructor	
	 * @param at the AffineTransform object
	 * @param x the x-coordinate of the ball
	 * @param y the y-coordinate of the ball
	 * @param width the width of the ball
	 * @param height the height of the ball
	 */
	public BallPaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		this.at = at;
		ball.setFrame(x - width, y - width, 2 * width, 2 * width);
	}

	/**
	 * override the paint strategy
	 */
	@Override
	public void paint(Graphics g, Ball host) {
		int radius = host.getRadius();
		ball.setFrame(host.getLoc().x - radius, host.getLoc().y - radius,
				2 * radius, 2 * radius);
		g.setColor(host.getColor());
		((Graphics2D)g).fill(at.createTransformedShape(ball));
	}

	/**
	 * leave it empty for a ball paint strategy
	 */
	@Override
	public void init(Ball host) {

	}

}
