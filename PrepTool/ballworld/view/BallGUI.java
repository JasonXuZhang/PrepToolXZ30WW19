package ballworld.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JComboBox;

import java.awt.GridLayout;

import ballworld.factory.IPaintStrategyFac;
import ballworld.factory.IStrategyFac;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class BallGUI extends JFrame {

	private static final long serialVersionUID = 4020116467106070344L;

	private static int horizontalBoundary = 900;
	private static int verticalBoundary = 600;
	private IModelControlAdapter mControlAdapter;
	private static IModelUpdateAdapter mUpdateAdapter;

	private JPanel contentPane;
	private final static JPanel pnlText = new JPanel() {

		private static final long serialVersionUID = 2175232765305712810L;

		/**
		 * Overridden paintComponent method to paint a shape in the panel.
		 * 
		 * @param g
		 *            The Graphics object to paint on.
		 **/
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // Do everything normally done first, e.g.
										// clear the screen.
			g.setColor(Color.RED); // Set the color to use when drawing
			mUpdateAdapter.update(g);
		}
	};
	private final JPanel pnlControl = new JPanel();
	private final JTextField textField = new JTextField();
	private final JButton btnMakeBall = new JButton("Make Selected Ball");
	private final JButton btnClearAll = new JButton("Clear All");
	private final JButton btnAddToLists = new JButton("Add To Lists");
	private final JComboBox<IStrategyFac> comboBox = new JComboBox<IStrategyFac>();
	private final JComboBox<IStrategyFac> comboBox_1 = new JComboBox<IStrategyFac>();
	private final JButton btnCombine = new JButton("Combine!");
	private final JButton btnMakeSwitcher = new JButton("Make Switcher");
	private final JButton btnSwitch = new JButton("Switch!");
	private final JPanel pnlAddBall = new JPanel();
	private final JPanel pnlStrategies = new JPanel();
	private final JPanel pnlSwitchers = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JButton btnAdd = new JButton("Add");
	private final JTextField textField_1 = new JTextField();
	private final JComboBox<IPaintStrategyFac> comboBox_2 = new JComboBox<IPaintStrategyFac>();

	public static void start(BallGUI frame) {
		frame.setVisible(true);
	}

	public Component getMainPanel() {
		return BallGUI.pnlText;
	}

	/**
	 * Create the frame.
	 */
	public BallGUI(IModelControlAdapter mca, IModelUpdateAdapter mua) {
		textField_1.setColumns(10);
		textField_1.setText("Car");
		setmControlAdapter(mca);
		setmUpdateAdapter(mua);
		textField.setToolTipText("Name of a strategy, can be: Straight, Color, Breathing, Curve, or Wander");
		pnlAddBall.setBorder(new LineBorder(SystemColor.controlShadow, 3));
		pnlControl.add(pnlAddBall);
		pnlAddBall.add(textField);
		textField.setText("Color");
		textField.setColumns(10);
		btnAddToLists.setToolTipText("Add strategy to both droplists: Straight, Color, Curve, Wander, or Breathing");
		btnAddToLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the factory of the input strategy
				IStrategyFac o = (IStrategyFac) mControlAdapter.addStrategy(textField.getText());
				if (null == o) return; // just in case
				// add the new factory to the combo boxes
				comboBox.insertItemAt(o, 0);
				comboBox_1.insertItemAt(o, 0);
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
			}
		});
		pnlAddBall.add(btnAddToLists);
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, horizontalBoundary, verticalBoundary);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		pnlText.setBackground(new Color(224, 255, 255));

		contentPane.add(pnlText, BorderLayout.CENTER);
		pnlText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlControl.setBackground(SystemColor.menu);

		contentPane.add(pnlControl, BorderLayout.NORTH);
						pnlControl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
						pnlAddBall.setLayout(new GridLayout(0, 1, 0, 0));
						pnlStrategies.setBorder(new LineBorder(new Color(0, 102, 102), 3));
						
						pnlControl.add(pnlStrategies);
						pnlStrategies.setLayout(new GridLayout(4, 1, 0, 0));
						btnMakeBall.setToolTipText("Instantiate a Ball with the strategy selected in the upder droplist");
						pnlStrategies.add(btnMakeBall);
						btnMakeBall.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// add a ball using the strategy factory in the upper combo box
								getmControlAdapter().makeBall(comboBox.getSelectedItem(), comboBox_2.getSelectedItem());
							}
						});
						comboBox.setToolTipText("");
						pnlStrategies.add(comboBox);
						pnlStrategies.add(comboBox_1);
						btnCombine.setToolTipText("Combine the selected strategies from both droplists into a single strategy that is added to both droplists");
						btnCombine.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// combine the tow factories in the comboBoxes and get a new strategy factory
								IStrategyFac o = (IStrategyFac) mControlAdapter.combineStrategies(comboBox.getSelectedItem(), comboBox_1.getSelectedItem());
								// add the new combined strategy to the two combo boxes
								comboBox.insertItemAt(o, 0);
								comboBox_1.insertItemAt(o, 0);
								comboBox.setSelectedIndex(0);
								comboBox.setSelectedIndex(0);
							}
						});
						pnlStrategies.add(btnCombine);
		btnClearAll.setToolTipText("Clear all the balls from the screen");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// clear all the balls on the frame
				getmControlAdapter().clearBalls();
			}
		});
				pnlSwitchers.setBorder(new TitledBorder(null, "Switcher Controls", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				pnlControl.add(pnlSwitchers);
				pnlSwitchers.setLayout(new GridLayout(0, 1, 0, 0));
				btnMakeSwitcher.setToolTipText("Instantiate a ball that can switch strategies");
				btnMakeSwitcher.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// generate a switch ball
						mControlAdapter.makeSwitchBall();
					}
				});
				pnlSwitchers.add(btnMakeSwitcher);
				btnSwitch.setToolTipText("Switch the strategy on all swticher balls to the currently selected strategy in the uppder droplist");
				btnSwitch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//switch the strategy of all balls to the selected strategy in the comboBox
						getmControlAdapter().switchStrategy(comboBox.getSelectedItem()); 
					}
				});
				pnlSwitchers.add(btnSwitch);
				pnlControl.add(btnClearAll);
				panel_4.setBorder(new TitledBorder(null, "Paint Strategies", TitledBorder.CENTER, TitledBorder.TOP, null, null));
				pnlControl.add(panel_4);
				panel_4.setLayout(new GridLayout(3, 1, 0, 0));
				
				panel_4.add(textField_1);
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// get the factory of the input strategy
						IPaintStrategyFac o = (IPaintStrategyFac) mControlAdapter.addPaintStrategy(textField_1.getText());
						if (null == o) return; // just in case
						// add the new factory to the combo boxes
						comboBox_2.insertItemAt(o, 0);
						comboBox_2.setSelectedIndex(0);
					}
				});
				
				panel_4.add(btnAdd);
				
				panel_4.add(comboBox_2);
	}

	public IModelUpdateAdapter getmUpdateAdapter() {
		return mUpdateAdapter;
	}

	public void setmUpdateAdapter(IModelUpdateAdapter mUpdateAdapter) {
		BallGUI.mUpdateAdapter = mUpdateAdapter;
	}

	public IModelControlAdapter getmControlAdapter() {
		return mControlAdapter;
	}

	public void setmControlAdapter(IModelControlAdapter mControlAdapter) {
		this.mControlAdapter = mControlAdapter;
	}

}
