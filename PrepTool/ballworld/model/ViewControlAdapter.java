package ballworld.model;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * ViewControlAdpater that implements IViewControlAdapter interface.
 * Override the getCanvas and setCanvas methods
 * 
 * @author Xu Zhang
 * 
 */
public class ViewControlAdapter implements IViewControlAdapter {
	private JPanel canvas = new JPanel();

	@Override
	public Component getCanvas() {
		// TODO Auto-generated method stub
		return this.canvas;
	}

	@Override
	public void setCanvas(Component canvas) {
		// TODO Auto-generated method stub
		this.canvas = (JPanel) canvas;
	}
}
