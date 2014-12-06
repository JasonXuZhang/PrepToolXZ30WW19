package ballworld.view;

import java.awt.Graphics;

import ballworld.model.BallModel;

/**
 * ModelUpdateAdapter that implements IModelUpdateAdapter interface.
 * 
 * @author Xu Zhang
 * 
 */
public class ModelUpdateAdapter implements IModelUpdateAdapter {

	private BallModel ballModel;
//	private BallModel switchBallModel;

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		ballModel.update(g);
//		switchBallModel.update(g);
	}

	public BallModel getBallModel() {
		return ballModel;
	}

	public void setBallModel(BallModel ballModel) {
		this.ballModel = ballModel;
	}
}
