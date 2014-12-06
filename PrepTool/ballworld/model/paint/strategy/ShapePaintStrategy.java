package ballworld.model.paint.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import ballworld.model.Ball;

/**
 * ShapePaintStrategy is used to paint a shape on the frame
 * @author Xu Zhang
 *
 */
public class ShapePaintStrategy extends APaintStrategy {
	private Shape shape;

	/**
	 * fill a shape
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		((Graphics2D) g).fill(at.createTransformedShape(shape));
	}
	
	/**
	 * constructor
	 * @param shape
	 */
	public ShapePaintStrategy(Shape shape) {
		this(new AffineTransform(), shape);
	}
	
	/**
	 * constructor
	 * @param at
	 * @param shape
	 */
	public ShapePaintStrategy(AffineTransform at, Shape shape) {
		super(at);
		this.shape = shape;
	}

	/**
	 * init method, empty
	 */
	@Override
	public void init(Ball host) {
		
	}
}
