package ballworld.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ballworld.factory.IPaintStrategyFac;
import ballworld.factory.IStrategyFac;
import ballworld.model.paint.strategy.BallPaintStrategy;
import ballworld.model.strategy.IUpdateStrategy;
import ballworld.model.strategy.MultiStrategy;
import ballworld.model.strategy.SwitcherStrategy;

/**
 * Model of ballworld program, a BallModel object holds some balls and has a
 * timer used to update balls. BallModel could add a ball dynamically and clear
 * all the balls it has. BallModel could generate a strategy factory and pass it
 * to the View. BallModel communicates with Controller and View through adapters
 * 
 * @author Xu Zhang
 * 
 */
public class BallModel {
	private Timer timer;
	private int timeSlice;
	private Dispatcher dispatcher;
	private SwitcherStrategy switcher;
	private IViewControlAdapter vControlAdapter;
	private IViewUpdateAdapter vUpdateAdapter;

	/**
	 * constructor of BallModel
	 * 
	 * @param vControlAdapter
	 *            the ControlAdpater that the Model will use to control View
	 * @param vUpdateAdapter
	 *            the UpdateAdpater that the model will use to update View
	 */
	public BallModel(IViewControlAdapter vControlAdapter,
			ViewUpdateAdapter vUpdateAdapter) {
		timeSlice = 20;
		dispatcher = new Dispatcher();
		switcher = new SwitcherStrategy();
		this.setvControlAdapter(vControlAdapter);
		this.setvUpdateAdapter(vUpdateAdapter);
	}

	/**
	 * initialize timer of ballModel and start it
	 */
	public void start() {
		timer = new Timer(timeSlice, new ActionListener() { // set the timer
					public void actionPerformed(ActionEvent e) {
						vUpdateAdapter.update();
					}
				});
		timer.start(); // start the timer
	}

	/**
	 * clear all the balls that the model have
	 */
	public void clearBalls() {
		dispatcher.deleteObservers(); // delete all the balls in the dispatcher
	}

	/**
	 * add a ball to Model
	 * 
	 * @param className
	 *            the name of the strategy that the ball will have
	 */
	public void addBall(String className, String paintSName) {
		IUpdateStrategy newStrategy = loadStrategy(className);
		IPaintStrategy newPaintStrategy = loadPaintStrategy(paintSName);
		Ball newBall = new Ball(newStrategy, vControlAdapter.getCanvas(),
				newPaintStrategy);
		dispatcher.addObserver(newBall);
	}

	/**
	 * load a ball with input strategy and add it to the dispatcher
	 * 
	 * @param strategy
	 *            the strategy that the ball will take
	 */
	public void loadBall(IUpdateStrategy strategy, IPaintStrategy paintStrategy) {
		Ball newBall = new Ball(strategy, vControlAdapter.getCanvas(),
				paintStrategy);
		dispatcher.addObserver(newBall);
	}

	/**
	 * update the status of all balls
	 * 
	 * @param g
	 *            an object that the balls need when updating their status, may
	 *            be a Graphics or IUpdateStrategy object
	 */
	public void update(final Object g) {

		if (g instanceof Graphics) { // if g is a Graphics object, then update
			dispatcher.notifyAll(new IBallCmd(){
				@Override
				public void apply(Ball context, Dispatcher disp) {
//					context.move(g);
					context.paintStrategy.init(context);
					context.paintStrategy.paint((Graphics) g, context);
					Point loc = context.getLoc();
					loc.x += context.getVelocity().x;
					loc.y += context.getVelocity().y;
					context.setLoc(loc);
					context.bounce();
					
					context.setxBoundary(context.getContainer().getWidth());
					context.setyBoundary(context.getContainer().getHeight());
					context.strategy.updateState(context, (Dispatcher) disp);
				}
			});
		
		} else if (g instanceof IUpdateStrategy) { // if g is a strategy, then
			// update the strategy of
			// the ball
			dispatcher.notifyAll(new IBallCmd() {
				@Override
				public void apply(Ball context, Dispatcher disp) {
					context.setStrategy((IUpdateStrategy) g);					
				}
			});
		}
		//dispatcher.notifyAll(g);
	}

	/**
	 * get the current strategy of switcher balls
	 * @return
	 */
	public SwitcherStrategy getSwitcherStrategy(){
		return (SwitcherStrategy)switcher.getStrategy();
	}
	
	/**
	 * update the current strategy of switchers
	 * @param strategy the new strategy
	 */
	public void siwtchSwitcherStrategy(IUpdateStrategy strategy) {
		switcher.setStrategy(strategy);
	}
	
	/**
	 * dynamicly load a ball whose name is class name
	 * 
	 * @param className
	 * @return
	 */
	private IUpdateStrategy loadStrategy(String className) {
		try {

			Object[] args = new Object[] {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(className)
					.getConstructors(); // get all the constructors

			java.lang.reflect.Constructor<?> c = null;
			for (int i = 0; i < cs.length; i++) {
				if (args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			return (IUpdateStrategy) c.newInstance(args);
		} catch (Exception ex) {
			System.err.println("Class " + className
					+ " failed to load. \nException = \n" + ex);
			ex.printStackTrace(); // print the stack trace to help in debugging.
			return null;
		}
	}

	private IPaintStrategy loadPaintStrategy(String className) {
		try {

			Object[] args = new Object[] {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(className)
					.getConstructors();

			java.lang.reflect.Constructor<?> c = null;
			for (int i = 0; i < cs.length; i++) {
				if (args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			return (IPaintStrategy) c.newInstance(args);
		} catch (Exception ex) {
			System.err.println("Class " + className
					+ " failed to load. \nException = \n" + ex);
			ex.printStackTrace(); // print the stack trace to help in debugging.
			return null;
		}
	}

	/**
	 * Returns an IStrategyFac that can instantiate the strategy specified by
	 * classname. Returns a factory for a beeping error strategy if classname is
	 * null. The toString() of the returned factory is the classname.
	 * 
	 * @param classname
	 *            Shortened name of desired strategy
	 * @return A factory to make that strategy
	 */
	public IStrategyFac makeStrategyFac(final String classname) {
		if (fixName(classname) == "errorName") {
			return _errorStrategyFac;
		}
		if (null == classname)
			return _errorStrategyFac;
		return new IStrategyFac() {
			/**
			 * Instantiate a strategy corresponding to the given class name.
			 * 
			 * @return An IUpdateStrategy instance
			 */
			public IUpdateStrategy make() {
				return loadStrategy(fixName(classname));
			}

			/**
			 * Return the given class name string
			 */
			public String toString() {
				return classname;
			}
		};
	}

	public IPaintStrategyFac makePaintStrategyFac(final String classname) {
		if (fixPaintName(classname) == "errorName") {
			return (IPaintStrategyFac) _errorStrategyFac;
		}
		if (classname == null)
			return (IPaintStrategyFac) _errorStrategyFac;

		return new IPaintStrategyFac() {

			public IPaintStrategy make() {
				// TODO Auto-generated method stub
				return loadPaintStrategy(fixPaintName(classname));
			}

			public String toString() {
				return classname;
			}
		};
	}

	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the two
	 * strategies made by the two given IStrategyFac objects. Returns null if
	 * either supplied factory is null. The toString() of the returned factory
	 * is the toString()'s of the two given factories, concatenated with "-". If
	 * either factory is null, then a factory for a beeping error strategy is
	 * returned.
	 * 
	 * @param stratFac1
	 *            An IStrategyFac for a strategy
	 * @param stratFac2
	 *            An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IStrategyFac combineStrategyFacs(final IStrategyFac stratFac1,
			final IStrategyFac stratFac2) {
		if (null == stratFac1 || null == stratFac2)
			return _errorStrategyFac;
		return new IStrategyFac() {
			/**
			 * Instantiate a new MultiStrategy with the strategies from the
			 * given strategy factories
			 * 
			 * @return A MultiStrategy instance
			 */
			public IUpdateStrategy make() {
				return (IUpdateStrategy) new MultiStrategy(stratFac1.make(),
						stratFac2.make());
			}

			/**
			 * Return a string that is the toString()'s of the given strategy
			 * factories concatenated with a "-"
			 */
			public String toString() {
				return stratFac1.toString() + "-" + stratFac2.toString();
			}
		};
	}

	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25
	 * updates. Either use the _errorStrategyFac variable directly if you need a
	 * factory that makes an error strategy, or call _errorStrategyFac.make() to
	 * create an instance of a beeping error strategy.
	 */
	private IStrategyFac _errorStrategyFac = new IStrategyFac() {
		@Override
		/**
		 * Make the beeping error strategy
		 * @return An instance of a beeping error strategy
		 */
		public IUpdateStrategy make() {
			return new IUpdateStrategy() {
				private int count = 0; // update counter

				@Override
				/**
				 * Beep the speaker every 25 updates
				 */
				public void updateState(Ball context, Dispatcher dispatcher) {
					if (25 < count++) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						count = 0;
					}
				}
			};
		}
	};

	/**
	 * fix the name input by user to a class name that the loadBall method can
	 * recognize
	 * 
	 * @param name
	 *            the name input by user
	 * @return class name of the input strategy
	 */
	public String fixName(String name) {
		switch (name) {
		case "Color":
		case "Straight":
		case "Breathing":
		case "Wander":
		case "Curve":
			// case "Spawn":
			return "ballworld.model.strategy." + name + "Strategy";
		default:
			return "errorName";
		}
	}

	private String fixPaintName(String name) {
		switch (name) {
		case "Car":
		case "Ball":
		case "Square":
		case "Rectangle":
		case "Ellipse":
		case "SoccerImage":
		case "PlanetImage":
		case "SharkImage":
		case "BirdSheepImage":
		case "MarioSonicImage":
			return "ballworld.model.paint.strategy." + name + "PaintStrategy";
		default:
			return "errorName";
		}
	}

	/**
	 * getter of vControAdapter
	 * 
	 * @return vControAdapter of BallModel
	 */
	public IViewControlAdapter getvControlAdapter() {
		return vControlAdapter;
	}

	/**
	 * setter of vControAdapter
	 * 
	 * @param vControlAdapter
	 *            the new vControAdapter
	 */
	public void setvControlAdapter(IViewControlAdapter vControlAdapter) {
		this.vControlAdapter = vControlAdapter;
	}

	/**
	 * the getter of vUpdateAdapter
	 * 
	 * @return vUpdateAdapter of BallModel
	 */
	public IViewUpdateAdapter getvUpdateAdapter() {
		return vUpdateAdapter;
	}

	/**
	 * setter of vUpdateAdapter
	 * 
	 * @param vUpdateAdapter
	 *            the new vUpdateAdapter
	 */
	public void setvUpdateAdapter(IViewUpdateAdapter vUpdateAdapter) {
		this.vUpdateAdapter = vUpdateAdapter;
	}

	public void makeSwitchBall() {
		this.loadBall(switcher, new BallPaintStrategy());		
	}

}
