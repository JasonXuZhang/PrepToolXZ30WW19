package ballworld.model.paint.strategy;

/**
 * strategy that will paint a shark image on the frame
 * @author Xu Zhang
 *
 */
public class SharkImagePaintStrategy extends ImagePaintStrategy{

	/**
	 * load the shark image
	 */
	public SharkImagePaintStrategy() {
		super("image/BlackTipReef.gif", 0.5);
	}

}
