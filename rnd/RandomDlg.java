package rnd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.util.function.Supplier;
import javax.swing.JCheckBox;

public  class RandomDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();//TODO
	protected boolean ok;
	private JButton okButton;
	private JButton cancelButton;
	protected RandomGenerators random;
	protected JCheckBox chckbxRound;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RandomDlg dialog = new RandomDlg();
///dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RandomDlg() {
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		fl_contentPanel.setVgap(1);
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onOK();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onCancel();

					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			chckbxRound = new JCheckBox("\u041E\u043A\u0440\u0443\u0433\u043B\u044F\u0442\u0438 \u0434\u043E \u0446\u0456\u043B\u043E\u0433\u043E");
			getContentPane().add(chckbxRound, BorderLayout.NORTH);
		}
	}

	protected void onCancel() {
		ok=false;
		setVisible(false);
		random=null;
		this.dispose();
		
	}

	protected void onOK() {
		ok=true;
		createRandom();
		setVisible(false);
		
	}
//This method must be overriding in subclasses 
	protected void createRandom() {
		random=null;
		
	}

	protected JPanel getContentPanel() {
		return contentPanel;
	}

	
	protected JButton getOkButton() {
		return okButton;
	}
	public JButton getCancelButton() {
		return cancelButton;
	}

	public RandomGenerators getRandom() {
		
		return random;
	}
	
	
	
}
