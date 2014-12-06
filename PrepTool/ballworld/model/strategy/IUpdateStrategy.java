package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * interface of all concrete strategies
 * @author Xu Zhang
 *
 */
public interface IUpdateStrategy {
	void updateState(Ball context, Dispatcher dispatcher);
}
