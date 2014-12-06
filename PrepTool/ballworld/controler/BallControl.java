package ballworld.controler;

import java.awt.EventQueue;

import ballworld.model.BallModel;
import ballworld.model.ViewControlAdapter;
import ballworld.model.ViewUpdateAdapter;
import ballworld.view.BallGUI;
import ballworld.view.ModelControlAdapter;
import ballworld.view.ModelUpdateAdapter;

/**
 * The following different types of strategies are available for use:
 * 
 * Straight Curve Color Breathing Wander
 * 
 * The following paint strategies are available for use:
 * 
 * Ball Square Rectangle Ellipse SoccerImage PlanetImage SharkImage
 * BirdSheepImage MarioSonicImage
 * 
 * @author Xu Zhang
 * 
 */
public class BallControl {

	/**
	 * Launch the application.
	 */
	// declare and initialize adapters
	private static ViewControlAdapter vControlAdapter = new ViewControlAdapter();
	private static ViewUpdateAdapter vUpdateAdapter = new ViewUpdateAdapter();
	private static ModelControlAdapter mControlAdapter = new ModelControlAdapter();
	private static ModelUpdateAdapter mUpdateAdapter = new ModelUpdateAdapter();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BallGUI frame = new BallGUI(mControlAdapter, mUpdateAdapter);
					BallModel ballModel = new BallModel(vControlAdapter,
							vUpdateAdapter); // declare and initialize a model
					vControlAdapter.setCanvas(frame.getMainPanel()); // connect
																		// the
																		// view
																		// with
																		// models
					vUpdateAdapter.setCanvas(frame.getMainPanel());
					mControlAdapter.setBallModel(ballModel); // connect models
																// with view
					mUpdateAdapter.setBallModel(ballModel);
					BallGUI.start(frame); // start view
					ballModel.start(); // start model
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
