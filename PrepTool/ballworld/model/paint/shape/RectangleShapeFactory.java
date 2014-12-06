package ballworld.model.paint.shape;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * RectangleShapeFactory is used to create a Rectangle shape
 * @author Xu Zhang
 *
 */
public class RectangleShapeFactory implements IShapeFactory{
	
	public static RectangleShapeFactory singleton = new RectangleShapeFactory();
	
	/**
	 * constructor
	 */
	public RectangleShapeFactory(){
		
	}
	
	/**
	 * make a rectangle shape
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		
		return new Rectangle2D.Double(x,y,xScale, yScale);
	}
	
}
