package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * SwitcherStrategy - holds the switchers whose strategies might be changed
 * @author Xu Zhang
 *
 */
public class SwitcherStrategy implements IUpdateStrategy{

	private IUpdateStrategy strategy;
	
	/**
	 * getter of strategy
	 * @return the current strategy
	 */
	public IUpdateStrategy getStrategy() {
		return strategy;
	}

	/**
	 * setter of strategy
	 * @param strategy
	 */
	public void setStrategy(IUpdateStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * constructor, new a straight ball by default
	 */
	public SwitcherStrategy() {
		strategy = new StraightStrategy();
	}
	
	/**
	 * update the status of strategy
	 */
	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		strategy.updateState(context, dispatcher);
	}
}
