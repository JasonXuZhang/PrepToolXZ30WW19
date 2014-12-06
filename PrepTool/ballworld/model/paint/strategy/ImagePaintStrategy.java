package ballworld.model.paint.strategy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;

import ballworld.model.Ball;

/**
 * ImagePaintStrategy is used to paint an image on the frame
 * @author Xu Zhang
 *
 */
public class ImagePaintStrategy extends APaintStrategy {

	private ImageObserver imageObs;
	private Image image;
	private double scaleFactor;
	private double fillFactor;
	protected AffineTransform localAT = new AffineTransform();

	/**
	 * constructor
	 * @param fileName the URL of the image
	 * @param fillFactor the fill factro
	 */
	public ImagePaintStrategy(String fileName, double fillFactor) {

		try {
			image = Toolkit.getDefaultToolkit().getImage(
					this.getClass().getResource(fileName));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: "
					+ fileName + "\n" + e);
		}

		this.fillFactor = fillFactor;

	}

	/**
	 * constructor
	 * @param at the AffineTransform object
	 * @param fileName URL of the image
	 * @param fillFactor factor
	 */
	public ImagePaintStrategy(AffineTransform at, String fileName,
			double fillFactor) {

		this.localAT = at;

		try {
			image = Toolkit.getDefaultToolkit().getImage(
					this.getClass().getResource(fileName));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: "
					+ fileName + "\n" + e);
		}

		this.fillFactor = fillFactor;
	}

	/**
	 * paint method
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at2) {
		
		localAT.setToScale(scaleFactor, scaleFactor);
		localAT.translate(-image.getWidth(imageObs) / 2.0,
				-image.getHeight(imageObs) / 2.0);
		localAT.preConcatenate(at);
		((Graphics2D) g).drawImage(image, localAT, imageObs);
	}

	/**
	 * init the status
	 */
	public void init(Ball host) {
		imageObs = host.getContainer();
		MediaTracker mt = new MediaTracker(host.getContainer());
		mt.addImage(image, 1);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out
					.println("ImagePaintStrategy.init(): Error waiting for image.  Exception = "
							+ e);
		}

		scaleFactor = 2.0 / (fillFactor
				* (image.getWidth(imageObs) + image.getHeight(imageObs)) / 2.0);
	}

	/**
	 * paintCfg is called to make sure that the image won't be turned over irregularly
	 */
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}
}
