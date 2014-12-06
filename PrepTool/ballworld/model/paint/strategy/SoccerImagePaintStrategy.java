package ballworld.model.paint.strategy;

/**
 * strategy that will paint a soccer image on the frame
 * @author Xu Zhang
 *
 */
public class SoccerImagePaintStrategy extends ImagePaintStrategy{
	
	/**
	 * load the soccer image
	 */
	public SoccerImagePaintStrategy() {
		super("image/FIFA_Soccer_Ball.png", 0.5);
	}

}
