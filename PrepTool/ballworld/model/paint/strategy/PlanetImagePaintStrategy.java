package ballworld.model.paint.strategy;


/**
 * strategy that will paint a planet on the frame
 * @author Xu Zhang
 *
 */
public class PlanetImagePaintStrategy extends ImagePaintStrategy{
	/**
	 * paint the earth image
	 */
	public PlanetImagePaintStrategy() {
		super("image/Earth.png", 0.5);
	}
}
