package widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class ChooseData extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title = "Title";

	/**
	 * This method initializes
	 * 
	 */
	public ChooseData() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(143, 45));
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setBackground(new Color(238, 238, 238));
		this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createTitledBorder(
						javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.RAISED), title,
						javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP,
						new java.awt.Font(Font.DIALOG, Font.PLAIN, 12), null),
				javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
		// .setBorder(javax.swing.BorderFactory
		// .createCompoundBorder(
		// javax.swing.BorderFactory
		// .createTitledBorder(
		// javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
		// title,
		// javax.swing.border.TitledBorder.CENTER,
		// javax.swing.border.TitledBorder.BELOW_TOP,
		// new Font("Dialog", Font.PLAIN, 12),
		// null),
		// javax.swing.BorderFactory
		// .createBevelBorder(javax.swing.border.SoftBevelBorder.LOWERED)));

	}

	// .createBevelBorder(javax.swing.border.SoftBevelBorder.RAISED),
	// javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createTitledBorder(
						javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.RAISED), title,
						javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP,
						new java.awt.Font(Font.DIALOG, Font.PLAIN, 12), null),
				javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
	}

	// public void setTitle(String title) {
	// try {
	// this.title = title;
	// CompoundBorder b1 = (CompoundBorder) this.getBorder();
	// TitledBorder b2 = (TitledBorder) b1.getOutsideBorder();
	// b2.setTitle(title);
	//
	// } catch (Exception e) {
	//
	// }
	// }

	public double getDouble() {
		if (getText().equals(""))
			return 0;
		return Double.parseDouble(getText());
	}

	public void setDouble(double d) {
		setText(String.valueOf(d));
	}

	public double getFloat() {
		if (getText().equals(""))
			return 0;
		return Float.parseFloat(getText());
	}

	public void setFloat(float d) {
		setText(String.valueOf(d));
	}

	public int getInt() {
		return (int) Double.parseDouble(getText());
	}

	public void setInt(int n) {
		setText(String.valueOf(n));
	}

	public double[] getDoubleArray() throws NumberFormatException {
		StringTokenizer st = new StringTokenizer(getText());
		double[] array = new double[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {

			try {
				array[i] = Double.parseDouble(st.nextToken());
			} catch (NumberFormatException e) {
				// System.out.println(getText()
				// + " It is impossible to convert into NumberArray");
				// e.printStackTrace();
				array[i] = 0;
				throw new NumberFormatException(getText());
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

} // @jve:decl-index=0:visual-constraint="27,49"
