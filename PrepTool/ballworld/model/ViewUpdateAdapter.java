package ballworld.model;

import java.awt.Component;

/**
 * ViewUpdateAdapter that implements IViewUpdateAdapter interface
 * @author Xu Zhang
 *
 */
public class ViewUpdateAdapter implements IViewUpdateAdapter {
	private static Component canvas;

	@Override
	public void update() {
		// TODO Auto-generated method stub
		canvas.repaint();
	}

	public static Component getCanvas() {
		return canvas;
	}

	public void setCanvas(Component canvas) {
		ViewUpdateAdapter.canvas = canvas;
	}
}
