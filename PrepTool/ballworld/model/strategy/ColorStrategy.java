package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * a concrete strategy of a ColorChanging Ball, implementing IUpdateStrategy
 * interface
 * 
 * @author Xu Zhang
 * 
 */
public class ColorStrategy implements IUpdateStrategy {

	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		context.setColor(context.createRandomColor()); // set the color to
														// another random color
	}

}
