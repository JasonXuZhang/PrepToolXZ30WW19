package ballworld.model.paint.strategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import ballworld.model.Ball;

/**
 * MultiPaintStrategy - class that holds multiple paint strategies
 * @author Xu Zhang
 *
 */
public class MultiPaintStrategy extends APaintStrategy {

	private APaintStrategy[] elements; // all the paint strategies are stored in elements

	/**
	 * constructor
	 * @param elements
	 */
	public MultiPaintStrategy(APaintStrategy... elements) {
		this(new AffineTransform(), elements);
	}
	
	/**
	 * constructor
	 * @param at
	 * @param elements
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... elements) {
		super(at);
		this.elements = elements;
	}

	/**
	 * paintXfrm - call all the paintXfrm method of the elements
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at2) {
		for (int i = 0; i < elements.length; i++) {
			elements[i].paintXfrm(g, host, at2);
		}
	}

	/**
	 * init all elements' status
	 */
	@Override
	public void init(Ball host) {
		for (int i = 0; i < elements.length; i++) {
			elements[i].init(host);
		}
	}

	/**
	 * this method is called to make sure that the image will not be turned upside-down irregularly
	 */
	@Override
	protected void paintCfg(Graphics g, Ball host) {
		at.scale(0.4, 0.4);
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}

}
