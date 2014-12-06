package ballworld.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import ballworld.model.strategy.IUpdateStrategy;

/**
 * Ball is the only concrete ball class in the Ballworld program. Ball has all
 * the properties and methods of a ball such as its location, velocity, color,
 * radius, etc. Ball could load a strategy and using the strategy. Ball class
 * has some methods that could create some random values of its properties.
 * 
 * @author Xu Zhang
 * 
 */
public class Ball implements Observer {
	private Point loc; // location of the ball
	private Point velocity;
	private Color color;
	private int radius;
	private static int maxRadius = 30; // the max possible radius of a ball
	private static int xBoundary; // the x-coordinate boundary of the container
	private static int yBoundary; // the y-coordinate boundary of the container
	IUpdateStrategy strategy;
	IPaintStrategy paintStrategy;
	private Component container; // the container that the ball exists on

	/**
	 * default constructor
	 */
	public Ball() {
		radius = 10;
		color = createRandomColor();
		loc = createRandomPosition();
	}

	/**
	 * constructor, it receives the strategy that defines the ball's behavior
	 * and take a Component to help to update its behavior
	 * 
	 * @param strategy
	 *            the strategy that the ball behave
	 * @param container
	 *            the current container where the ball will drew
	 */
	public Ball(IUpdateStrategy strategy, Component container,
			IPaintStrategy paintStrategy) { // constructor
		setPaintStrategy(paintStrategy);
		setStrategy(strategy);
		setLoc(createRandomPosition()); // get a random position as the initial
										// position of the ball
		setRadius(createRandomRadius(maxRadius));

		this.container = container;
		setxBoundary(container.getWidth()); // get xBoundary and yBoundary using
											// container
		setyBoundary(container.getHeight());
		setVelocity(craeteRandomVelocity()); // get a random velocity as the
												// initial velocity of the ball
		setColor(createRandomColor()); // get a random color as the initial
										// color of the ball
	}

	/**
	 * constructor
	 * @param location location of the ball
	 * @param radius radius of the ball
	 * @param velocity velocity of the ball
	 * @param color color of the ball
	 * @param container container that the ball is on
	 * @param strategy the strategy of the ball
	 */
	public Ball(Point location, int radius, Point velocity, Color color,
			Component container, IUpdateStrategy strategy) {
		this.loc = location;
		this.radius = radius;
		this.color = color;
		this.container = container;
		this.strategy = strategy;
	}

	/**
	 * When the ball hits the wall, this method will ensure that the ball will
	 * bounce back from the wall
	 */
	public void bounce() {
		Point newLoc = loc;
		newLoc.x += velocity.x;
		newLoc.y += velocity.y; // update the position of the ball
		if (newLoc.x - radius <= 0) { // if the ball hits the left wall, change
										// direction
			this.loc.x = radius; // pull back the ball from the left wall to
									// make sure that it will not go inside the
									// wall
			getVelocity().x = 0 - getVelocity().x; // change the x direction
		}
		if (newLoc.x + radius >= xBoundary) { // if the ball hits the right
												// wall, change direction
			this.loc.x = xBoundary - radius;
			getVelocity().x = 0 - getVelocity().x;
		}
		if (newLoc.y - radius <= 0) { // if the ball hits the upper wall, change
										// direction
			this.loc.y = radius;
			getVelocity().y = 0 - getVelocity().y;
		}
		if (newLoc.y + radius >= yBoundary) { // if the ball hits the lower
												// wall, change the direction
			this.loc.y = yBoundary - radius;
			getVelocity().y = 0 - getVelocity().y;
		}
		setLoc(newLoc); // update the position
	}

	/**
	 * update the position and velocity of the ball. receive an object of the
	 * Observable and a Graphics object
	 */
	@Override
	public void update(Observable o, Object g) {
		// apply what the command object asks
		((IBallCmd) g).apply(this, (Dispatcher)o);
	}
	
	public void move(Object g){
		paintStrategy.init(this);
		paintStrategy.paint((Graphics) g, this);
		Point loc = this.getLoc();
		loc.x += this.getVelocity().x;
		loc.y += this.getVelocity().y;
		this.setLoc(loc);
		bounce();
	}

	/**
	 * paint the ball
	 * 
	 * @param g
	 *            the Graphics object
	 */
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(loc.x - radius, loc.y - radius, radius * 2, radius * 2);
	}

	/**
	 * create a random position within the range of the container
	 * 
	 * @return the random point
	 */
	public Point createRandomPosition() {
		int radius = getRadius();
		int x = this.getxBoundary() - this.getRadius();
		int y = this.getyBoundary() - this.getRadius();
		int newX = (int) (Math.random() * (radius - x)) + x;
		int newY = (int) (Math.random() * (radius - y)) + y;
		return new Point(newX, newY);
	}

	/**
	 * create a random velocity
	 * 
	 * @return the random velocity as a point
	 */
	public Point craeteRandomVelocity() {
		int newX = (int) (1 + (20 - 1) * Math.random()) - 10;
		int newY = (int) (1 + (20 - 1) * Math.random()) - 10;
		return new Point(newX, newY);
	}

	/**
	 * create a random color
	 * 
	 * @return the random color
	 */
	public Color createRandomColor() {
		return new Color(randomInt(0, 255), randomInt(0, 255),
				randomInt(0, 255));
	}

	/**
	 * create a random radius
	 * 
	 * @param maxRadius
	 *            the max value of the radius of the ball
	 * @return the random radius
	 */
	public int createRandomRadius(int maxRadius) {
		int min = 10;
		return (int) Math.floor((Math.random() * (1 + maxRadius - min)) + min);
	}

	/**
	 * create a random integer within a max and a min value
	 * 
	 * @param min
	 *            the min value of the random integer
	 * @param max
	 *            the max value of the random integer
	 * @return the new random integer
	 */
	public int randomInt(int min, int max) {
		return (int) Math.floor((Math.random() * (1 + max - min)) + min);
	}

	/**
	 * getter of radius
	 * 
	 * @return the radius of the ball
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * setter of radius
	 * 
	 * @param radius
	 *            the new value of radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * getter of loc
	 * 
	 * @return the location of the ball
	 */
	public Point getLoc() {
		return loc;
	}

	/**
	 * setter of loc
	 * 
	 * @param loc
	 *            the new value of loc
	 */
	public void setLoc(Point loc) {
		this.loc = loc;
	}

	/**
	 * getter of velocity
	 * 
	 * @return the current velocity of the ball
	 */
	public Point getVelocity() {
		return velocity;
	}

	/**
	 * setter of velocity
	 * 
	 * @param velocity
	 *            the new value of velocity
	 */
	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}

	/**
	 * getter of xBoudnary
	 * 
	 * @return xBoundary
	 */
	public int getxBoundary() {
		return xBoundary;
	}

	/**
	 * setter of xBoudnary
	 * 
	 * @param xBoundary
	 *            xBoundary
	 */
	public void setxBoundary(int xBoundary) {
		Ball.xBoundary = xBoundary;
	}

	/**
	 * getter of yBoudary
	 * 
	 * @return yBoundary
	 */
	public int getyBoundary() {
		return yBoundary;
	}

	/**
	 * setter of yBoudary
	 * 
	 * @param yBoundary
	 *            yBoudary
	 */
	public void setyBoundary(int yBoundary) {
		Ball.yBoundary = yBoundary;
	}

	/**
	 * getter of color
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * setter of color
	 * 
	 * @param color
	 *            new value of color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * getter of the current IUpdateStrategy of the ball
	 * 
	 * @return the IUpdateStrategy
	 */
	public IUpdateStrategy getStrategy() {
		return strategy;
	}

	/**
	 * setter of strategy
	 * 
	 * @param strategy
	 *            new value of strategy
	 */
	public void setStrategy(IUpdateStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * getter of container
	 * 
	 * @return container
	 */
	public Component getContainer() {
		return container;
	}

	/**
	 * setter of container
	 * 
	 * @param container
	 *            new container
	 */
	public void setContainer(Component container) {
		this.container = container;
	}

	/**
	 * getter of paintStrategy
	 * @return
	 */
	public IPaintStrategy getPaintStrategy() {
		return paintStrategy;
	}

	/**
	 * setter of paintStrategy
	 * @param paintStrategy
	 */
	public void setPaintStrategy(IPaintStrategy paintStrategy) {
		this.paintStrategy = paintStrategy;
	}
}
