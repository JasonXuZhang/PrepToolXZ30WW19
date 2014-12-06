package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * a concrete strategy of a Breathing Ball, implementing IUpdateStrategy
 * interface
 * 
 * @author Xu Zhang
 * 
 */
public class BreathingStrategy implements IUpdateStrategy {
	private int increment = 2;

	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		int radius = context.getRadius(); // get radius from the super class
		radius += increment; // increase the radius of the radius
		if (radius > 30) { // if the radius is too large, set the increment to a
							// negative value
			radius = 30;
			increment = 0 - increment;
		}
		else if (radius < 5) { // if the radius is too small, set the increment to a
							// positive value
			radius = 5;
			increment = 0 - increment;
		}
		context.setRadius(radius); // update the radius
	}

}
