import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import freemarker.template.TemplateException;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;

public class GUIView extends JFrame {

	private JPanel contentPane;

	private Model model;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenuItem mntmLoadBallot = new JMenuItem("Load Ballot From '.ballot' file");
	private final JMenuItem mntmGenerate = new JMenuItem("Generate Ballot Webpage");
	private final JMenuItem mntmPreview = new JMenuItem("Preview");
	private final JSplitPane splitPane_1 = new JSplitPane();
	private final JPanel panelRaceList = new JPanel();
	private final JMenu mnAddRace = new JMenu("Add");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JMenuItem mntmRace = new JMenuItem("Add Race");
	private final JList list = new JList();
	private final DefaultListModel listModel = new DefaultListModel();
	private final DefaultListModel candidateListModel = new DefaultListModel();
	private final JLayeredPane layeredPane = new JLayeredPane();
	private JTextField textField_Title;
	private final JPanel panel_CandidateList = new JPanel();
	
	private final Object[] columnNames = {"First", "Last"};
	private final Object[][] data  = {{"Jason", "Zhang"}, {"Jim", "Green"}};
	private final JMenuBar menuBar_2 = new JMenuBar();
	private final JPanel panel_6 = new JPanel();
	private final JTextField textField_Candidate = new JTextField();
	private final JButton btn_AddCandidate = new JButton("Add");
	private final JList list_CandidateList = new JList();
	
	/**
	 * race editor variables
	 */
	private final JPanel panel_RaceEditor;
	private final JLabel lbl_Title;
	private final JLabel lbl_Description;
	private final JTextArea textArea_Description;
	private final JComboBox comboBox_Type;
	private final JPanel panel_CandidateEditor;
	private final JPanel panel = new JPanel();
	private final JButton btn_Confirm = new JButton("Confirm");
	private final JButton btn_Cancel = new JButton("Cancel");
	private final JMenu mnModify = new JMenu("Modify");
	private final JMenuItem mntmEdit = new JMenuItem("Edit Race");
	private final JMenuItem mntmDelete = new JMenuItem("Delete Race");
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final String[] raceTypeList = {"Choose from candidate list", "Choose Yes / No", "Choose For / Against"};
	private final String[] optionCountList = {"1", "2", "3", "4", "5"};
	private final JComboBox comboBox_Max = new JComboBox(optionCountList);
	private final JButton btnRemoveSelectedCandidate = new JButton("Remove Selected Candidate");
	private final JCheckBox chckbxAllowCustomizedOption = new JCheckBox("Allow Customized Candidate");
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JMenu mnBallot = new JMenu("Ballot");
	private final JMenuItem mntmSaveBallotTo = new JMenuItem("Save Ballot To '.ballot' file");
	private final JMenuItem mntmMoveUp = new JMenuItem("UP");
	private final JMenuItem mntmMovedown = new JMenuItem("Down");

	/**
	 * Create the frame.
	 */
	public GUIView() {
		textField_Candidate.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 540);

		setJMenuBar(menuBar);
		mntmGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose the directory to save the ballot web page");
				chooser.setCurrentDirectory(new File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				int option = chooser.showOpenDialog(new FileChooser());
		        if (option == JFileChooser.APPROVE_OPTION) {
			        if (chooser.getSelectedFile()!=null) {
			        	File file = chooser.getSelectedFile();
//				        model.saveBallotToFile(file.getAbsolutePath(), file.getName());
//			        	showMessage(file.getAbsoluteFile().toString());
						try {
							model.generateBallot(file.getAbsoluteFile().toString());
						} catch (IOException e1) {
							// TODO Auto-generated catch blocks
							e1.printStackTrace();
						} catch (TemplateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }
		        }
			}
		});
		
		menuBar.add(mnBallot);
		mnBallot.add(mntmLoadBallot);
		mntmSaveBallotTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
		        int option = chooser.showSaveDialog(new FileChooser());
		        if (option == JFileChooser.APPROVE_OPTION) {
			        if (chooser.getSelectedFile()!=null) {
			        	File file = chooser.getSelectedFile();
				        model.saveBallotToFile(file.getAbsolutePath(), file.getName());
			        }
		        }
			}
		});
		
		mnBallot.add(mntmSaveBallotTo);
		mntmLoadBallot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
		          JFileChooser chooser = new JFileChooser();
		          chooser.setMultiSelectionEnabled(false);
		          int option = chooser.showOpenDialog(new FileChooser());
		          if (option == JFileChooser.APPROVE_OPTION) {
		        	File file = chooser.getSelectedFile();
		            model.readBallotFromFile(file.getAbsolutePath(), file.getName());
		          }
			}
		});

		menuBar.add(mntmGenerate);

		menuBar.add(mntmPreview);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
				splitPane_1.setResizeWeight(0.3);
		
				contentPane.add(splitPane_1);
						panelRaceList.setBorder(new TitledBorder(null, "Race List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
						splitPane_1.setLeftComponent(panelRaceList);
						panelRaceList.setLayout(new BoxLayout(panelRaceList,
								BoxLayout.X_AXIS));
						list.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if (e.getValueIsAdjusting() == false) {
									int index = list.getSelectedIndex();
									if (index == -1) {
										// No selection
										setRaceEditorEnabled(false);
										setCandidateEditorEnabled(false);
									} else {
										Race curRace = model.getRaceByIndex(index);
										textField_Title.setText(curRace.title);
										textArea_Description.setText(curRace.description);
										String raceType = curRace.type;
										int typeIndex = getIndexByValue(raceTypeList, raceType);
										if (typeIndex == 0) {
											comboBox_Max.setSelectedIndex(getIndexByValue(optionCountList, "" + curRace.maxAnswer));
											chckbxAllowCustomizedOption.setSelected(curRace.allowCustomisedCandidate);
											if (comboBox_Type.isEnabled()) {
												comboBox_Max.setEnabled(true);
												chckbxAllowCustomizedOption.setEnabled(true);
											}
										} else {
											comboBox_Max.setSelectedIndex(0);
											comboBox_Max.setEnabled(false);
											chckbxAllowCustomizedOption.setSelected(false);
											chckbxAllowCustomizedOption.setEnabled(false);
										}
										
										candidateListModel.clear();
										for (String can : curRace.candidates)
											candidateListModel.addElement(can);
									}
								}
							}
						});
						panelRaceList.add(scrollPane);
						
						scrollPane.setViewportView(list);
						
						scrollPane.setColumnHeaderView(menuBar_2);
						mntmRace.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								textField_Title.setText("");
								textArea_Description.setText("");
								textField_Candidate.setText("");
								candidateListModel.clear();
								list.clearSelection();
								setRaceEditorEnabled(true);
								setCandidateEditorEnabled(true);
							}
						});
								menuBar_2.add(mnAddRace);
						
								mnAddRace.add(mntmRace);
								
								menuBar_2.add(mnModify);
								mntmEdit.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										int index = list.getSelectedIndex();
										if (index == -1)
											return;
										Race curRace = model.getRaceByIndex(index);
										textField_Title.setText(curRace.title);
										textArea_Description.setText(curRace.description);
										candidateListModel.clear();
										for (String can : curRace.candidates)
											candidateListModel.addElement(can);
										setRaceEditorEnabled(true);
										setCandidateEditorEnabled(true);
									}
								});
								
								mnModify.add(mntmEdit);
								mntmDelete.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										int index = list.getSelectedIndex();
										if (index == -1)
											return;
										listModel.remove(index);
										model.removeRaceByIndex(index);
										
										textField_Title.setText("");
										textArea_Description.setText("");
										textField_Candidate.setText("");
										candidateListModel.clear();
										list.clearSelection();
										setRaceEditorEnabled(false);
										setCandidateEditorEnabled(false);
									}
								});
								
								mnModify.add(mntmDelete);
								mntmMoveUp.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										int selectedIndex = list.getSelectedIndex();
										if (selectedIndex != -1)
											moveRace(selectedIndex, -1);
									}
								});
								
								menuBar_2.add(mntmMoveUp);
								mntmMovedown.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										int selectedIndex = list.getSelectedIndex();
										if (selectedIndex != -1)
											moveRace(selectedIndex, 1);
									}
								});
								
								menuBar_2.add(mntmMovedown);
																		
																		splitPane_1.setRightComponent(layeredPane);
																		layeredPane.setLayout(new BorderLayout(0, 0));
																		
																		JSplitPane splitPane_3 = new JSplitPane();
																		splitPane_3.setResizeWeight(0.3);
																		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
																		layeredPane.add(splitPane_3);
																		
																		panel_RaceEditor = new JPanel();
																		panel_RaceEditor.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Race Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		splitPane_3.setLeftComponent(panel_RaceEditor);
																		panel_RaceEditor.setLayout(new BorderLayout(0, 0));
																		
																		JPanel panel_1 = new JPanel();
																		panel_RaceEditor.add(panel_1, BorderLayout.NORTH);
																		panel_1.setLayout(new BorderLayout(0, 0));
																		
																		lbl_Title = new JLabel("Title:");
																		panel_1.add(lbl_Title, BorderLayout.WEST);
																		
																		textField_Title = new JTextField();
																		panel_1.add(textField_Title, BorderLayout.CENTER);
																		textField_Title.setColumns(10);
																		
																		JPanel panel_2 = new JPanel();
																		panel_RaceEditor.add(panel_2, BorderLayout.CENTER);
																		panel_2.setLayout(new BorderLayout(0, 0));
																		
																		lbl_Description = new JLabel("Description:");
																		panel_2.add(lbl_Description, BorderLayout.NORTH);
																		
																		JLabel lblNewLabel_2 = new JLabel("         ");
																		panel_2.add(lblNewLabel_2, BorderLayout.WEST);
																		
																		panel_2.add(scrollPane_1, BorderLayout.CENTER);
																		
																		textArea_Description = new JTextArea();
																		scrollPane_1.setViewportView(textArea_Description);
																		textArea_Description.setWrapStyleWord(true);
																		textArea_Description.setLineWrap(true);
																		
																		JPanel panel_3 = new JPanel();
																		panel_RaceEditor.add(panel_3, BorderLayout.SOUTH);
																		panel_3.setLayout(new BorderLayout(0, 0));
																		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Max", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		
																		panel_3.add(panel_4, BorderLayout.EAST);
																		panel_4.setLayout(new BorderLayout(0, 0));
																		comboBox_Max.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				JComboBox cb = (JComboBox)e.getSource();
																				String number = (String)cb.getSelectedItem();
																				int maxCount = Integer.parseInt(number.trim());
																				Race curRace  = model.getRaceByIndex(list.getSelectedIndex());
																				curRace.maxAnswer = maxCount;
																			}
																		});
																		
																		panel_4.add(comboBox_Max, BorderLayout.CENTER);
																		panel_5.setBorder(new TitledBorder(null, "Vote Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		
																		panel_3.add(panel_5, BorderLayout.CENTER);
																		panel_5.setLayout(new BorderLayout(0, 0));
																		
																		comboBox_Type = new JComboBox(raceTypeList);
																		comboBox_Type.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				JComboBox cb = (JComboBox)e.getSource();
																				int index = cb.getSelectedIndex();
																				String newType = raceTypeList[index];
																				if (index == 0) {
																					int raceIndex = list.getSelectedIndex();
																					candidateListModel.removeAllElements();
																					if (raceIndex == -1) {
																						// no selected race, set max to 1
																						comboBox_Max.setSelectedIndex(0);
																						// set check box unchecked
																						chckbxAllowCustomizedOption.setSelected(false);
																					} else {
																						// recover the edited content of the selected race
																						Race race = model.getRaceByIndex(raceIndex);
																						comboBox_Max.setSelectedIndex(getIndexByValue(optionCountList, ""+race.maxAnswer));
//																						for (String cand : race.candidates)
//																							candidateListModel.addElement(cand);
																						chckbxAllowCustomizedOption.setEnabled(race.allowCustomisedCandidate);
																					}
																					if (comboBox_Type.isEnabled()) {
																						comboBox_Max.setEnabled(true);
																						chckbxAllowCustomizedOption.setEnabled(true);
																						setCandidateEditorEnabled(true);
																					}
																				} else {
																					candidateListModel.removeAllElements();
																					if (index == 1) {
																						// if type is Yes-No, change the list to just yes / no
																						candidateListModel.addElement("Yes");
																						candidateListModel.addElement("No");
																					} else {
																						// For/Agianst
																						candidateListModel.addElement("For");
																						candidateListModel.addElement("Against");
																					}
																					comboBox_Max.setSelectedIndex(0);
																					comboBox_Max.setEnabled(false);
																					setCandidateEditorEnabled(false);
																					chckbxAllowCustomizedOption.setSelected(false);
																					chckbxAllowCustomizedOption.setEnabled(false);
																				}
																			}
																		});
																		panel_5.add(comboBox_Type);
																		
																		panel_3.add(chckbxAllowCustomizedOption, BorderLayout.SOUTH);
																		
																		panel_CandidateEditor = new JPanel();
																		panel_CandidateEditor.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Candidate Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		splitPane_3.setRightComponent(panel_CandidateEditor);
																		panel_CandidateEditor.setLayout(new BorderLayout(0, 0));
																		
																		panel_CandidateEditor.add(panel_CandidateList, BorderLayout.CENTER);
																		panel_CandidateList.setLayout(new BorderLayout(0, 0));
																		
																		panel_CandidateList.add(list_CandidateList);
																		btnRemoveSelectedCandidate.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				if (list_CandidateList.isEnabled()) {
																					int index = list_CandidateList.getSelectedIndex();
																					if (index != -1)
																						candidateListModel.remove(index);
																				}
																			}
																		});
																		
																		panel_CandidateList.add(btnRemoveSelectedCandidate, BorderLayout.SOUTH);
																		
																		panel_CandidateEditor.add(panel_6, BorderLayout.NORTH);
																		panel_6.setLayout(new BorderLayout(0, 0));
																		
																		panel_6.add(textField_Candidate, BorderLayout.CENTER);
																		btn_AddCandidate.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				String newCandidate = textField_Candidate.getText().trim();
																				candidateListModel.addElement(newCandidate);
																				textField_Candidate.setText("");
																			}
																		});
																		
																		panel_6.add(btn_AddCandidate, BorderLayout.EAST);
																		
																		layeredPane.add(panel, BorderLayout.SOUTH);
																		panel.setLayout(new BorderLayout(0, 0));
																		btn_Confirm.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				String title = textField_Title.getText().trim();
																				String description = textArea_Description.getText().trim();
																				String type = (String)comboBox_Type.getSelectedItem();
																				int maxCount = Integer.parseInt((String) comboBox_Max.getSelectedItem());
																				boolean allowCostomizedCandidate = chckbxAllowCustomizedOption.isSelected();
																				Race newRace = new Race();
																				newRace.setTitle(title);
																				newRace.setDescription(description);
																				newRace.setType(type);
																				newRace.setMaxAnswer(maxCount);
																				newRace.setAllowCustomisedCandidate(allowCostomizedCandidate);
																				
																				int n = candidateListModel.getSize();
																				for (int i = 0; i < n; i++) {
																					String can = (String) candidateListModel.get(i);
																					newRace.addCandidate(can);
																				}
																				
																				int index = list.getSelectedIndex();
																				if (index == -1)
																					addRaceToList(newRace, true);
																				else
																					replaceRaceByIndex(index, newRace);
																				
//																				candidateListModel.clear();
																				setRaceEditorEnabled(false);
																				setCandidateEditorEnabled(false);
																			}
																		});
																		
																		panel.add(btn_Confirm);
																		btn_Cancel.addActionListener(new ActionListener() {
																			public void actionPerformed(ActionEvent e) {
																				textField_Title.setText("");
																				textArea_Description.setText("");
																				textField_Candidate.setText("");
																				candidateListModel.clear();
																				setRaceEditorEnabled(false);
																				setCandidateEditorEnabled(false);
																			}
																		});
																		
																		panel.add(btn_Cancel, BorderLayout.WEST);
	}
	
	protected int getIndexByValue(String[] arr, String value) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(value))
				return i;
		}
		return -1;
	}

	public void init() {
		list.setModel(listModel);
		candidateListModel.clear();
		list_CandidateList.setModel(candidateListModel);
		
		Race r1 = new Race();
		r1.setTitle("Straight Party");
		r1.setDescription("this is the first question");
		r1.addCandidate("1");
		r1.addCandidate("2");
		Race r2 = new Race();
		r2.setTitle("Race 2");
		r2.setDescription("this is the second question");
		r2.addCandidate("1");
		r2.addCandidate("2");
		
		addRaceToList(r1, true);
		addRaceToList(r2, true);
		
		
		setRaceEditorEnabled(false);
		setCandidateEditorEnabled(false);
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public void addRaceToList(Race race, boolean addRaceToModel) {
		if (listModel == null) {
			model.print("Error: listModel is null");
			return;
		}
//		System.out.println("adding: " + race.title);
		listModel.addElement(race.title);
		if (addRaceToModel)
			model.addRace(race);
	}
	
	public void replaceRaceByIndex(int index, Race race) {
		listModel.set(index, race.title);
		model.replaceRaceByIndex(index, race);
	}
	
	public void setRaceEditorEnabled(boolean enabled) {
		panel_RaceEditor.setEnabled(enabled);
		lbl_Title.setEnabled(enabled);
		textField_Title.setEditable(enabled);
		lbl_Description.setEnabled(enabled);
		textArea_Description.setEditable(enabled);
		comboBox_Type.setEnabled(enabled);
		btn_Confirm.setEnabled(enabled);
		btn_Cancel.setEnabled(enabled);
		comboBox_Max.setEnabled(enabled);
		chckbxAllowCustomizedOption.setEnabled(enabled);
	}
	
	public void setCandidateEditorEnabled(boolean enabled) {
		panel_CandidateEditor.setEnabled(enabled);
		textField_Candidate.setEnabled(enabled);
		btn_AddCandidate.setEnabled(enabled);
		btnRemoveSelectedCandidate.setEnabled(enabled);
	}

	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void updateRaceList() {
		listModel.clear();
		for (Race race : model.getRaceList()) {
			this.addRaceToList(race, false);
		}
	}
	
	public void moveRace(int selectedIndex, int step) {
		int index = model.moveRaceByOneStep(selectedIndex, step);
		this.updateRaceList();
		list.setSelectedIndex(index);
	}
}
