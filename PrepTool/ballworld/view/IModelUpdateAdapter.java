package ballworld.view;

import java.awt.Graphics;

/**
 * interface of an ModelUpdateAdapter through whom Model could communicate with
 * View
 * 
 * @author Xu Zhang
 * 
 */
public interface IModelUpdateAdapter {
	void update(Graphics g);
}
