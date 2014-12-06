package ballworld.model.strategy;

import ballworld.model.Ball;
import ballworld.model.Dispatcher;

/**
 * a class that includes multiple strategies, implementing IUpdateStrategy
 * interface
 * 
 * @author Xu Zhang
 * 
 */
public class MultiStrategy implements IUpdateStrategy {
	private IUpdateStrategy s1;
	private IUpdateStrategy s2;

	public MultiStrategy(IUpdateStrategy s1, IUpdateStrategy s2) {
		setS1(s1);
		setS2(s2);
	}

	public IUpdateStrategy getS1() {
		return s1;
	}

	public void setS1(IUpdateStrategy s1) {
		this.s1 = s1;
	}

	public IUpdateStrategy getS2() {
		return s2;
	}

	public void setS2(IUpdateStrategy s2) {
		this.s2 = s2;
	}

	@Override
	public void updateState(Ball context, Dispatcher dispatcher) {
		// TODO Auto-generated method stub
		s1.updateState(context, null);
		s2.updateState(context, null);

	}
}
