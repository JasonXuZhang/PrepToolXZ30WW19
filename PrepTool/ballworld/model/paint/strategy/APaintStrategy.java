package ballworld.model.paint.strategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import ballworld.model.Ball;
import ballworld.model.IPaintStrategy;

/**
 * APaintStrategy - abstract class of all paint strategies, defining paint method for all paint strategies
 * @author Xu Zhang
 *
 */
public abstract class APaintStrategy implements IPaintStrategy{
	protected AffineTransform at;

	/**
	 * the paint method that every concrete paint strategies will use
	 */
	@Override
	public void paint(Graphics g, Ball host) {
		double scale = host.getRadius();
		at.setToTranslation(host.getLoc().x, host.getLoc().y);
		at.scale(scale, scale);
		at.rotate(Math.atan2(host.getVelocity().y, host.getVelocity().x));
		g.setColor(host.getColor());
		paintCfg(g, host);
		paintXfrm(g, host, at);
	}
	
	/**
	 * constructor
	 * @param at the AffienTransform object
	 */
	public APaintStrategy(AffineTransform at) {
		this.at = at;
	}
	
	/**
	 * constructor
	 */
	public APaintStrategy(){
		this.at = new AffineTransform();
	}

	/**
	 * abstract method, the concrete method will be defined in concrete strategies 
	 * @param g the Graphics object that the shape will be painted on
	 * @param host the ball
	 * @param at the AffineTransform object
	 */
	public abstract void paintXfrm(Graphics g, Ball host, AffineTransform at);

	/**
	 * paintCfg is called when special config work should be done before painting she shape
	 * @param g the Graphics ojbect
	 * @param host the ball
	 */
	protected void paintCfg(Graphics g, Ball host) {
		
	}
}
