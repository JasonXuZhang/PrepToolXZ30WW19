package ballworld.model;

import java.awt.Component;

/**
 * interface of an ViewControlAdpater through whom the Model could communicate
 * with View
 * 
 * @author Xu Zhang
 * 
 */
public interface IViewControlAdapter {
	public Component getCanvas();

	public void setCanvas(Component canvas);
}
