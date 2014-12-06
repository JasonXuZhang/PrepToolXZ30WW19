package ballworld.model.strategy;

import java.awt.Point;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;
import ballworld.model.IBallCmd;

public class SpawnStrategy implements IUpdateStrategy {

	private int count = 0; // tick counter that counts out the delay before
							// another ball can be spawned.
	private int delay = 100; // tick delay which increases at each spawn to keep
								// total spawn rate from exponentially
								// exploding.

	@Override
	public void updateState(final Ball context, Dispatcher dispatcher) {

		if (delay < count++) {
			dispatcher.notifyAll(new IBallCmd() {

				@Override
				public void apply(Ball other, Dispatcher disp) {

					if (count != 0 && context != other) {
						if ((context.getRadius() + other.getRadius()) > context
								.getLoc().distance(other.getLoc())) {

							disp.addObserver(new Ball(new Point(context
									.getLoc()), context.getRadius(), new Point(
									-context.getVelocity().x + 1, -context
											.getVelocity().y + 1), context
									.getColor(), context.getContainer(),
									new SpawnStrategy()));
							// context.getPaintStrategy()));
							count = 0;
							delay *= 5;
						}
					}
				}

			});
		}
	}
}