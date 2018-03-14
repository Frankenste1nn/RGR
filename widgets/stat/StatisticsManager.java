package widgets.stat;

import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.GridBagLayout;

import widgets.Diagram;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import process.Dispatcher;
import process.IModelFactory;
import stat.IHisto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import java.awt.Rectangle;
import java.util.Map;

import javax.swing.SwingConstants;

public class StatisticsManager extends JPanel {

	private IModelFactory factory;
	private JComboBox comboBox;
	private JTextArea textArea;
	private Diagram diagram;
	private JButton btnStart;
	private Map<String, IHisto> resultsMap;
	private IHisto histo;

	/**
	 * Create the panel.
	 */
	public StatisticsManager() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 202, 56, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		diagram = new Diagram();
		diagram.setMinimumSize(new Dimension(23, 23));
		diagram.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_diagram = new GridBagConstraints();
		gbc_diagram.gridwidth = 2;
		gbc_diagram.anchor = GridBagConstraints.NORTH;
		gbc_diagram.weighty = 1.0;
		gbc_diagram.weightx = 0.5;
		gbc_diagram.insets = new Insets(5, 5, 5, 5);
		gbc_diagram.fill = GridBagConstraints.BOTH;
		gbc_diagram.gridx = 0;
		gbc_diagram.gridy = 0;
		add(diagram, gbc_diagram);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.anchor = GridBagConstraints.EAST;
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);

		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(200, 100));
		scrollPane.setViewportView(textArea);

		comboBox = new JComboBox();
		comboBox.addActionListener((e) -> onComboAction(e));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 5, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		add(comboBox, gbc_comboBox);
		
				btnStart = new JButton("Start");
				btnStart.setMaximumSize(new Dimension(70, 26));
				btnStart.setMinimumSize(new Dimension(70, 26));
				btnStart.setPreferredSize(new Dimension(70, 25));
				btnStart.setMargin(new Insets(2, 30, 2, 30));
				btnStart.setHorizontalTextPosition(SwingConstants.CENTER);
				btnStart.setBorderPainted(false);
				btnStart.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
				btnStart.addActionListener((e) -> onBtnStart(e));
				GridBagConstraints gbc_btnStart = new GridBagConstraints();
				gbc_btnStart.insets = new Insets(5, 5, 5, 5);
				gbc_btnStart.gridx = 1;
				gbc_btnStart.gridy = 2;
				add(btnStart, gbc_btnStart);

	}

	private void onComboAction(ActionEvent e) {
		diagram.clear();
		diagram.setTitleText((String)comboBox.getSelectedItem());
		if(histo!=null && histo.getTest()!=null)
			histo.getTest().setVisible(false);
		histo = resultsMap.get(comboBox.getSelectedItem());
		textArea.setText(histo.toString());
		histo.showRelFrec(diagram);

	}

	private void onBtnStart(ActionEvent e) {
		diagram.clear();
		btnStart.setEnabled(false);
		comboBox.setEnabled(false);
		Dispatcher dispatcher = new Dispatcher();
		IStatisticsable model = (IStatisticsable) getFactory().createModel(
				dispatcher);
		model.initForStatistics();
		dispatcher.addDispatcherFinishListener(() -> {
			resultsMap = model.getStatistics();
			String[] keyStrings = (String[]) resultsMap.keySet().toArray(
					new String[resultsMap.keySet().size()]);
			comboBox.setModel(new DefaultComboBoxModel(keyStrings));
			IHisto histo = resultsMap.get(keyStrings[0]);
			diagram.setTitleText(keyStrings[0]);
			textArea.setText(histo.toString());
			histo.showRelFrec(diagram);
			btnStart.setEnabled(true);
			comboBox.setEnabled(true);
		});
		dispatcher.start();
	}

	private void createComboModel(Map<String, IHisto> resMap) {
	}

	public IModelFactory getFactory() {
		if (factory == null)
			System.err.println(" Не визначено factory для "
					+ "StatisticsManager");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}

}
