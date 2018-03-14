package widgets;

import java.awt.Insets;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class ChooseDataH extends JSplitPane {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JTextField jTextField = null;

	

	/**
	 * @return the text
	 */
	public String getText() {
		return getJTextField().getText();
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		getJTextField().setText(text);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return jLabel.getText();
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		jLabel.setText(title);
	}

	public double getResizeWeight(){
		return super.getResizeWeight();
	}
	
	public void  setResizeWeight(double d){
		super.setResizeWeight(d);
	}
	
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setHorizontalAlignment(JTextField.CENTER);
			jTextField.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		}
		return jTextField;
	}

	/**
	 * @return the jLabel
	 */
	public JLabel getjLabel() {
		return jLabel;
	}

	/**
	 * @param args
	 */


	/**
	 * This is the default constructor
	 */
	public ChooseDataH() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setText("Title");
		jLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.setSize(194, 36);
		this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		this.setDividerSize(4);
		this.setRightComponent(getJTextField());
		this.setLeftComponent(jLabel);
		setOneTouchExpandable(true);
		setResizeWeight(0.75);
	}
	public double getDouble() {
		if (getText().equals(""))
			return 0;
			return Double.parseDouble(getText());
	}

	public int getInt() {
		return (int) Double.parseDouble(getText());
	}

	public double[] getDoubleArray() {
		StringTokenizer st = new StringTokenizer(getText());
		double[] array = new double[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {

			try {
				array[i] = Double.parseDouble(st.nextToken());
			} catch (NumberFormatException e) {
				System.out.println(getText()
						+ " It is impossible to convert into NumberArray");
				e.printStackTrace();
				array[i] = 0;
			}

			i++;
		}

		return array;
	}

	public int[] getIntArray() {
		double[] doubleArray = getDoubleArray();
		int[] intArray = new int[doubleArray.length];
		for (int i = 0; i < intArray.length; i++)
			intArray[i] = (int) doubleArray[i];
		return intArray;
	}

	public void setMargin(Insets insets) {
		getJTextField().setMargin(insets);
		
	}

	public void setEditable(boolean b) {
		getJTextField().setEditable(b);
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
