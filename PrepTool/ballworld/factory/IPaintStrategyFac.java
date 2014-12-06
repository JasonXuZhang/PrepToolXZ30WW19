package ballworld.factory;

import ballworld.model.IPaintStrategy;

/**
 * interface of paint strategy factory
 * @author Xu Zhang
 *
 */
public interface IPaintStrategyFac {
	/**
	 * get a paint strategy
	 * @return the paint strategy
	 */
	public IPaintStrategy make();
}
