package src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import widgets.ChooseRandom;
import widgets.ChooseData;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import widgets.Diagram;
import java.awt.Font;
import javax.swing.event.CaretListener;

import process.Dispatcher;
import process.IModelFactory;
import rnd.Erlang;
import rnd.Negexp;
import rnd.Norm;

import javax.swing.event.CaretEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {
//test
	private JFrame frame;
	private Diagram diagram_Testing_Order;
	private Diagram diagram_Packing_Order;
	private JPanel panelTest;
	private Diagram diagram_Fixing_Order;
	private JCheckBox CheckBoxConcole;
	private JButton buttonTest;
	private ChooseData chsdtTesterCount;
	private ChooseData chooseData_Modelling_Time;
	private ChooseRandom chooseRandom_PC_Creation_time;
	private ChooseRandom chooseRandom_Packing_Time;
	private ChooseRandom chooseRandom_Fixing_Time;
	private ChooseRandom chooseRandom_Testing_Time;
	private ChooseData chooseData_fail_chance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 913, 474);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1030, 0};
		gridBagLayout.rowHeights = new int[]{783, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		frame.getContentPane().add(splitPane, gbc_splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new GridLayout(9, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		JPanel panelTZ = new JPanel();
		tabbedPane.addTab("Technical Task", null, panelTZ, null);
		GridBagLayout gbl_panelTZ = new GridBagLayout();
		gbl_panelTZ.columnWidths = new int[]{0};
		gbl_panelTZ.rowHeights = new int[]{0};
		gbl_panelTZ.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelTZ.rowWeights = new double[]{Double.MIN_VALUE};
		panelTZ.setLayout(gbl_panelTZ);
		
		panelTest = new JPanel();
		panelTest.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				getChooseData_Modelling_Time().select(0, 0);
			}
		});
		tabbedPane.addTab("Test", null, panelTest, null);
		GridBagLayout gbl_panelTest = new GridBagLayout();
		gbl_panelTest.columnWidths = new int[]{0, 0, 0};
		gbl_panelTest.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelTest.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelTest.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panelTest.setLayout(gbl_panelTest);
		
		diagram_Testing_Order = new Diagram();
		diagram_Testing_Order.setTitleText("Testing Order");
		GridBagConstraints gbc_diagram_Testing_Order = new GridBagConstraints();
		gbc_diagram_Testing_Order.fill = GridBagConstraints.HORIZONTAL;
		gbc_diagram_Testing_Order.gridwidth = 2;
		gbc_diagram_Testing_Order.insets = new Insets(0, 0, 5, 0);
		gbc_diagram_Testing_Order.gridx = 0;
		gbc_diagram_Testing_Order.gridy = 0;
		panelTest.add(diagram_Testing_Order, gbc_diagram_Testing_Order);
		
		diagram_Fixing_Order = new Diagram();
		diagram_Fixing_Order.setTitleText("Fixing Order");
		GridBagConstraints gbc_diagram_Fixing_Order = new GridBagConstraints();
		gbc_diagram_Fixing_Order.gridwidth = 2;
		gbc_diagram_Fixing_Order.insets = new Insets(0, 0, 5, 0);
		gbc_diagram_Fixing_Order.fill = GridBagConstraints.BOTH;
		gbc_diagram_Fixing_Order.gridx = 0;
		gbc_diagram_Fixing_Order.gridy = 1;
		panelTest.add(diagram_Fixing_Order, gbc_diagram_Fixing_Order);
		
		diagram_Packing_Order = new Diagram();
		diagram_Packing_Order.setTitleText("Packing Order");
		GridBagConstraints gbc_diagram_Packing_Order = new GridBagConstraints();
		gbc_diagram_Packing_Order.gridwidth = 2;
		gbc_diagram_Packing_Order.insets = new Insets(0, 0, 5, 0);
		gbc_diagram_Packing_Order.fill = GridBagConstraints.BOTH;
		gbc_diagram_Packing_Order.gridx = 0;
		gbc_diagram_Packing_Order.gridy = 2;
		panelTest.add(diagram_Packing_Order, gbc_diagram_Packing_Order);
		
		buttonTest = new JButton("Test");
		buttonTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startTest();
			}
		});
		GridBagConstraints gbc_buttonTest = new GridBagConstraints();
		gbc_buttonTest.insets = new Insets(0, 0, 0, 5);
		gbc_buttonTest.gridx = 0;
		gbc_buttonTest.gridy = 4;
		panelTest.add(buttonTest, gbc_buttonTest);
		
		CheckBoxConcole = new JCheckBox("Console Logging");
		GridBagConstraints gbc_CheckBoxConcole = new GridBagConstraints();
		gbc_CheckBoxConcole.gridx = 1;
		gbc_CheckBoxConcole.gridy = 4;
		panelTest.add(CheckBoxConcole, gbc_CheckBoxConcole);
		
		JPanel panelStat = new JPanel();
		tabbedPane.addTab("Stat", null, panelStat, null);
		GridBagLayout gbl_panelStat = new GridBagLayout();
		gbl_panelStat.columnWidths = new int[]{0};
		gbl_panelStat.rowHeights = new int[]{0};
		gbl_panelStat.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelStat.rowWeights = new double[]{Double.MIN_VALUE};
		panelStat.setLayout(gbl_panelStat);
		
		JPanel panelRegres = new JPanel();
		tabbedPane.addTab("Regres", null, panelRegres, null);
		GridBagLayout gbl_panelRegres = new GridBagLayout();
		gbl_panelRegres.columnWidths = new int[]{0};
		gbl_panelRegres.rowHeights = new int[]{0};
		gbl_panelRegres.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelRegres.rowWeights = new double[]{Double.MIN_VALUE};
		panelRegres.setLayout(gbl_panelRegres);
		
		JPanel panelTrans = new JPanel();
		tabbedPane.addTab("Transient", null, panelTrans, null);
		GridBagLayout gbl_panelTrans = new GridBagLayout();
		gbl_panelTrans.columnWidths = new int[]{0};
		gbl_panelTrans.rowHeights = new int[]{0};
		gbl_panelTrans.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelTrans.rowWeights = new double[]{Double.MIN_VALUE};
		panelTrans.setLayout(gbl_panelTrans);
		splitPane.setDividerLocation(300);
		
		chooseRandom_PC_Creation_time = new ChooseRandom();
		chooseRandom_PC_Creation_time.setFont(new Font("Tahoma", Font.PLAIN, 26));
		chooseRandom_PC_Creation_time.setTitle("PC Creation Time");
		panel.add(chooseRandom_PC_Creation_time);
		chooseRandom_PC_Creation_time.setRandom(new Negexp(1));
		chooseRandom_Testing_Time = new ChooseRandom();
		chooseRandom_Testing_Time.setFont(new Font("Tahoma", Font.PLAIN, 26));
		chooseRandom_Testing_Time.setTitle("Testing Time");
		panel.add(chooseRandom_Testing_Time);
		chooseRandom_Testing_Time.setRandom(new Erlang(4,2));
		chooseRandom_Fixing_Time = new ChooseRandom();
		chooseRandom_Fixing_Time.setFont(new Font("Tahoma", Font.PLAIN, 26));
		chooseRandom_Fixing_Time.setTitle("Fixing Time");
		panel.add(chooseRandom_Fixing_Time);
		chooseRandom_Fixing_Time.setRandom(new Norm(2,0.4));
		chooseRandom_Packing_Time = new ChooseRandom();
		chooseRandom_Packing_Time.setFont(new Font("Tahoma", Font.PLAIN, 26));
		chooseRandom_Packing_Time.setTitle("Packing Time");
		panel.add(chooseRandom_Packing_Time);
		chooseRandom_Packing_Time.setRandom(new Negexp(1));
		ChooseData chooseData_Testing_Places = new ChooseData();
		chooseData_Testing_Places.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				diagram_Testing_Order.setVerticalMaxText(chooseData_Testing_Places.getText());
			}
		});
		chooseData_Testing_Places.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseData_Testing_Places.setTitle("Testing Places");
		chooseData_Testing_Places.setText("10");
		panel.add(chooseData_Testing_Places);
		
		ChooseData chooseData_Box_Count = new ChooseData();
		chooseData_Box_Count.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				diagram_Packing_Order.setVerticalMaxText(chooseData_Box_Count.getText());
			}
		});
		chooseData_Box_Count.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseData_Box_Count.setTitle("Box Count");
		chooseData_Box_Count.setText("100");
		panel.add(chooseData_Box_Count);
		
		ChooseData chooseData_Fixing_Places = new ChooseData();
		chooseData_Fixing_Places.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				diagram_Fixing_Order.setVerticalMaxText(chooseData_Fixing_Places.getText());
			}
		});
		chooseData_Fixing_Places.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseData_Fixing_Places.setTitle("Fixing Places");
		chooseData_Fixing_Places.setText("8");
		panel.add(chooseData_Fixing_Places);
		
		chooseData_fail_chance = new ChooseData();
		chooseData_fail_chance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseData_fail_chance.setText("0.5");
		chooseData_fail_chance.setTitle("Fail Chance");
		panel.add(chooseData_fail_chance);
		
		chooseData_Modelling_Time = new ChooseData();
		chooseData_Modelling_Time.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				diagram_Fixing_Order.setHorizontalMaxText(chooseData_Modelling_Time.getText());
				diagram_Packing_Order.setHorizontalMaxText(chooseData_Modelling_Time.getText());
				diagram_Testing_Order.setHorizontalMaxText(chooseData_Modelling_Time.getText());
			}
		});
		chooseData_Modelling_Time.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chooseData_Modelling_Time.setTitle("Modelling Time");
		chooseData_Modelling_Time.setText("800");
		panel.add(chooseData_Modelling_Time);
		
		chsdtTesterCount = new ChooseData();
		chsdtTesterCount.setTitle("Tester count");
		chsdtTesterCount.setText("3");
		panel.add(chsdtTesterCount);
		
	}
	public Diagram getDiagram_Testing_Order() {
		return diagram_Testing_Order;
	}
	public Diagram getDiagram_Packing_Order() {
		return diagram_Packing_Order;
	}
	public JPanel getPanelTest() {
		return panelTest;
	}
	public Diagram getDiagram_Fixing_Order() {
		return diagram_Fixing_Order;
	}
	public JCheckBox getCheckBoxConcole() {
		return CheckBoxConcole;
	}
	public JButton getButtonTest() {
		return buttonTest;
	}
	public ChooseData getChsdtTesterCount() {
		return chsdtTesterCount;
	}
	public ChooseData getChooseData_Modelling_Time() {
		return chooseData_Modelling_Time;
	}

	private void startTest() {
		getDiagram_Fixing_Order().clear();
		getDiagram_Packing_Order().clear();
		getDiagram_Testing_Order().clear();
		
		Dispatcher dispatcher = new Dispatcher();
		IModelFactory factory = (d)->new Model(d, this);
		Model model = (Model)factory.createModel(dispatcher);
		
		getButtonTest().setEnabled(false);
		dispatcher.addDispatcherFinishListener(
				()->getButtonTest().setEnabled(true));
		model.initForTest();
		dispatcher.start();
	}
	public ChooseRandom getChooseRandom_PC_Creation_time() {
		return chooseRandom_PC_Creation_time;
	}
	public ChooseRandom getChooseRandom_Packing_Time() {
		return chooseRandom_Packing_Time;
	}
	public ChooseRandom getChooseRandom_Fixing_Time() {
		return chooseRandom_Fixing_Time;
	}
	public ChooseRandom getChooseRandom_Testing_Time() {
		return chooseRandom_Testing_Time;
	}
	public ChooseData getChooseData_fail_chance() {
		return chooseData_fail_chance;
	}
}
