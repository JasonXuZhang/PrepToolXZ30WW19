package ballworld.model.paint.strategy;

import java.awt.geom.AffineTransform;

import ballworld.model.paint.shape.RectangleShapeFactory;


/**
 * strategy that will paint a rectangle on the frame
 * @author Xu Zhang
 *
 */
public class RectanglePaintStrategy extends ShapePaintStrategy {

	/**
	 * constructor
	 */
	public RectanglePaintStrategy() {
		this(new AffineTransform(), 0, 0, 4.0/3.0, 2.0/3.0);
	}
	
	/**
	 * constructor
	 * @param at - the AffineTransform object
	 * @param x - the x-coordinate of the position of the shape
	 * @param y - the y-coordinate of the position of the shape
	 * @param width - the width of the shape
	 * @param height - the height of the shape
	 */
	public RectanglePaintStrategy(AffineTransform at, double x, double y,
			double width, double height) {
		super(at, RectangleShapeFactory.singleton
   				.makeShape(x, y, width, height));
   	}
}
