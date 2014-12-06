package ballworld.view;

/**
 * interface of an ModelControlAdapter through whom View could communicate with
 * Model
 * 
 * @author Xu Zhang
 * 
 */
public interface IModelControlAdapter {
	Object addStrategy(String classname);

	Object addPaintStrategy(String paintStrategyName);

	void makeBall(Object selectedItem, Object selectedItem2);

	Object combineStrategies(Object selectedItem1, Object selectedItem2);

	void switchStrategy(Object selectedItem);

	void makeSwitchBall();

	void clearBalls();

	void addBall(String className, String paintStrategyName);
}
