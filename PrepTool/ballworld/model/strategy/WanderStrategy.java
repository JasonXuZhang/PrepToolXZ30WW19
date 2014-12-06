package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * a concrete strategy of a Wander Ball, implementing IUpdateStrategy interface
 * 
 * @author Xu Zhang
 * 
 */
public class WanderStrategy implements IUpdateStrategy {

	private int counter = 0;

	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		if (++counter >= 15) { // get a random velocity every 15 frames, then
								// update it to the ball
			context.setVelocity(context.craeteRandomVelocity());
			counter = 0;
		}
	}
}
