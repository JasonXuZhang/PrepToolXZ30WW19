package ballworld.model.paint.strategy;

import java.awt.geom.AffineTransform;

/**
 * composite of paint strategies
 * @author Xu Zhang
 *
 */
public class CarPaintStrategy extends MultiPaintStrategy {
	/**
	 * Constructor of the CarPaintStrategy
	 * @param at the AffineTransform Object
	 */
	public CarPaintStrategy(AffineTransform at) {
		// add the components of the car into the array
		super(at, new EllipsePaintStrategy(at, 2, 2, 3.0 / 3.0, 3.0 / 3.0),
				new EllipsePaintStrategy(at, 0, 2, 3.0 / 3.0, 3.0 / 3.0),
				new RectanglePaintStrategy(at, 0, 1, 12.0 / 3.0, 3.0 / 3.0),
				new RectanglePaintStrategy(at, 0, 0, 9.0 / 3.0, 3.0 / 3.0));
	}

	public CarPaintStrategy() {
		this(new AffineTransform());
	}
}
