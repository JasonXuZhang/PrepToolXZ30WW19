import java.awt.EventQueue;

public class Controller {
	private static Model model;
	private static GUIView view;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					view = new GUIView();
					model = new Model();
					view.setModel(model);
					model.setView(view);
					view.init();
					view.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
