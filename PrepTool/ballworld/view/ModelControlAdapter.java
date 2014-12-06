package ballworld.view;

import ballworld.factory.IPaintStrategyFac;
import ballworld.factory.IStrategyFac;
import ballworld.model.BallModel;

/**
 * ModelControlAdapter that implements IModelControlAdapter interface.
 * 
 * @author Xu Zhang
 * 
 */
public class ModelControlAdapter implements IModelControlAdapter {

	private static BallModel ballModel;

	@Override
	public Object addStrategy(String classname) {
		return (IStrategyFac) ballModel.makeStrategyFac(classname);
	}

	@Override
	public Object addPaintStrategy(String paintStrategyName) {
		return (IPaintStrategyFac) ballModel.makePaintStrategyFac(paintStrategyName);
	}
	
	@Override
	public void addBall(String className, String paintStrategyName) {
		ballModel.addBall(className, null);
	}

	@Override
	/**
	 * Add a ball to the system with a strategy as given by the given IStrategyFac
	 * @param selectedItem The fully qualified name of the desired strategy.
	 */
	public void makeBall(Object selectedItem, Object selectItem2) {
		IStrategyFac item = (IStrategyFac) selectedItem;
		IPaintStrategyFac paintItem = (IPaintStrategyFac) selectItem2; 
		if (null != item)
			ballModel.loadBall(item.make(), paintItem.make()); // Here, loadBall takes a strategy
												// object, but your method may
												// take the strategy factory
												// instead.
	}

	@Override
	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the
	 * two strategies made by the two given IStrategyFac objects. Returns
	 * null if either supplied factory is null. The toString() of the
	 * returned factory is the toString()'s of the two given factories,
	 * concatenated with "-".             * 
	 * @param selectedItem1 An IStrategyFac for a strategy
	 * @param selectedItem2 An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IStrategyFac combineStrategies(Object selectedItem1,
			Object selectedItem2) {
		IStrategyFac item1 = (IStrategyFac) selectedItem1;
		IStrategyFac item2 = (IStrategyFac) selectedItem2;
		return ballModel.combineStrategyFacs(item1, item2);
	}

	@Override
	public void switchStrategy(Object selectedItem) {
//		switchBallModel.update(((IStrategyFac) selectedItem).make());
		ballModel.siwtchSwitcherStrategy(((IStrategyFac) selectedItem).make());
	}

	@Override
	public void makeSwitchBall() {
//		IStrategyFac o = (IStrategyFac) ballModel
//				.makeStrategyFac("Straight");
//		IPaintStrategyFac o1 = (IPaintStrategyFac) ballModel.makePaintStrategyFac("Ball");
//		if (null == o || null == o1)
//			return; // just in case
//		ballModel.loadBall(o.make(), o1.make());
		ballModel.makeSwitchBall();
	}

	/**
	 * clear all the balls on the frame
	 */
	@Override
	public void clearBalls() {
		// TODO Auto-generated method stub
		ballModel.clearBalls();
	}

	/**
	 * getter of ballModel
	 * @return
	 */
	public static BallModel getBallModel() {
		return ballModel;
	}

	/**
	 * setter of ballModel
	 * @param ballModel
	 */
	public void setBallModel(BallModel ballModel) {
		ModelControlAdapter.ballModel = ballModel;
	}
}
