package ballworld.model.strategy;

import java.awt.Point;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * a concrete strategy of a Curve Ball, implementing IUpdateStrategy
 * interface
 * 
 * @author Xu Zhang
 * 
 */
public class CurveStrategy implements IUpdateStrategy {

	private double angle = Randomizer.randomDouble(.01, Math.PI / 4.0);
	private double cosA = Math.cos(angle);
	private double sinA = Math.sin(angle);

	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		Point v = context.getVelocity();
		v.setLocation(cosA * v.getX() + sinA * v.getY(), cosA * v.getY() - sinA
				* v.getX());
	}

}
