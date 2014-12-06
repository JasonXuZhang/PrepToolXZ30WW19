package ballworld.model.paint.shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * EllipseShapeFactory is used to make a ellipes shape
 * @author Xu Zhang
 *
 */
public class EllipseShapeFactory extends java.lang.Object implements
		IShapeFactory {
	public static EllipseShapeFactory singleton = new EllipseShapeFactory();

	private EllipseShapeFactory() {
		
	}

	/**
	 * Instantiates an ellipse
	 * 
	 * @param x - x-coordinate of the center of the ellipse 
	 * @param y - y-coordinate of the center of the ellipse
	 * @param xScale - The x-radius of the ellipse 
	 * @param yScale - The y-radius of the ellipse
	 * @return An Ellipse2D.Double object.
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		// TODO Auto-generated method stub
		return new Ellipse2D.Double(x, y, xScale, yScale);
	}

}
