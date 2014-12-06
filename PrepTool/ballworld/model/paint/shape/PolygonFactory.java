package ballworld.model.paint.shape;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class PolygonFactory implements IShapeFactory {
	private AffineTransform at;
	double scaleFactor;	
	Polygon poly;
	
	public PolygonFactory(AffineTransform at, Double scaleFactor, Point[] pts){
		this.at = at;
		this.scaleFactor = scaleFactor;
		for (int i = 0; i < pts.length; i++) {
			poly.addPoint(pts[i].x, pts[i].y);
		}
	}
	
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		at.setToTranslation(x, y);
		at.scale(xScale * scaleFactor, yScale * scaleFactor); // optional
																// rotation can
																// be added as
																// well
		return at.createTransformedShape(poly);
	}

}