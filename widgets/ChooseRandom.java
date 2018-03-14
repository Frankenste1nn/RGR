package widgets;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import rnd.RandomDlg;
import rnd.RandomGenerators;
import rnd.Randomable;
import rnd.Rozpodil;

public class ChooseRandom extends JPanel implements Randomable {
	private JTextField textField;
	private JComboBox comboBox;
	private String title = "Закон розподілу";
	RandomGenerators random;

	/**
	 * Create the panel.
	 */
	public ChooseRandom() {
//		EtchedBorder b1 = new EtchedBorder(EtchedBorder.LOWERED, null, null);
//		TitledBorder b2 = new TitledBorder(new LineBorder(new Color(0, 0, 0)),
//				title, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(
//						0, 0, 0));

		setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Title", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0))));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 32, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				doShowCombo();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				comboBox.showPopup();
			}
		});

		btnNewButton.setMinimumSize(new Dimension(32, 9));
		btnNewButton.setMaximumSize(new Dimension(32, 9));
		btnNewButton.setPreferredSize(new Dimension(32, 9));
		btnNewButton.setIcon(new ImageIcon(ChooseRandom.class
				.getResource("/widgets/open.gif")));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new CardLayout(0, 0));

		textField = new JTextField();
		textField.setEditable(false);
		panel.add(textField, "name_615738474738625");
		textField.setColumns(10);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onComboAction();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(Rozpodil.values()));

		comboBox.setVisible(false);
		panel.add(comboBox, "name_615732298282443");
	}

	protected void onComboAction() {
		String string = "rnd." + comboBox.getSelectedItem().toString();
		try {
			Class<? extends RandomGenerators> clazz;
			clazz = (Class<? extends RandomGenerators>) Class.forName(string);
			// Створюємо об'єкт потрібного класу і викликаємо відповідний діалог
			// для налаштувань параметрів розподділу
			RandomGenerators r = (RandomGenerators) clazz.newInstance();
			RandomDlg dlg = r.getDialog();
			dlg.setLocationRelativeTo(this);
			Dimension d = dlg.getSize();
			dlg.setVisible(true);
			r = dlg.getRandom();
			dlg.dispose();
			if (r != null)
				setRandom(r);

		} catch (ClassNotFoundException e1) {
			System.out.println("ClassNotFoundException ");
			return;
		} catch (InstantiationException e1) {
			System.out.println("InstantiationException");
			return;
		} catch (IllegalAccessException e1) {
			System.out.println("IllegalAccessException ");
			return;
		}
		comboBox.setVisible(false);
		if (random != null)
			textField.setText(random.toString());
		else
			textField.setText("");
		textField.setVisible(true);

	}

	protected void doShowCombo() {
		textField.setVisible(false);
		comboBox.setVisible(true);

	}

//	public JTextField getTextField() {
//		return textField;
//	}

//	public JComboBox getComboBox() {
//		return comboBox;
//	}

	public void setTitle(String title) {
		this.title = title;
		((TitledBorder) ((CompoundBorder) this.getBorder()).getInsideBorder())
				.setTitle(title);
	}

	public RandomGenerators getRandom() {
		return random;
	}

	public void setRandom(RandomGenerators random) {
		this.random = random;
		if (random != null)
			textField.setText(random.toString());
	}

	@Override
	public double next() {

		return random.next();
	}

	@Override
	public double probability(double aNumber) {

		return random.probability(aNumber);
	}

	@Override
	public double probability(double aNumber1, double aNumber2) {

		return random.probability(aNumber1, aNumber2);
	}

	@Override
	public double average() {

		return random.average();
	}

	@Override
	public boolean average(double m) {

		return random.average(m);
	}

	@Override
	public double max() {

		return random.max();
	}

}
