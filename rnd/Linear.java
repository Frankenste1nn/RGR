package rnd;

/**
 * Insert the type's description here. Creation date: (02.04.2005 9:48:08)
 * 
 * @author: Administrator
 */
public class Linear extends RandomGenerators {
	private double[] x;

	private double[] s;

	/**
	 * Linear constructor comment.
	 */
	public Linear() {
		super();
	}

	/**
	 * Linear constructor comment.
	 */
	public Linear(double[] anArrayX, double[] anArrayS) {
		this();
		x = anArrayX;
		s = anArrayS;
	}

	/**
	 * Linear constructor comment.
	 */
	public Linear(double[] anArrayX, double[] anArrayS, boolean round) {
		this(anArrayX, anArrayS);
		setNextAsRound(round);
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:48:08)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		double r = rnd.nextDouble();
		int index = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] > r) {
				index = i;
				break;
			}
			;
		}
		;
		return x[index - 1] + (x[index] - x[index - 1])
				/ (s[index] - s[index - 1]) * (r - s[index - 1]);
	}

	public double basicNext(double r) {

		int index = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] > r) {
				index = i;
				break;
			}
			;
		}
		;
		return x[index - 1] + (x[index] - x[index - 1])
				/ (s[index] - s[index - 1]) * (r - s[index - 1]);
	}

	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 20:55:22)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 0;
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:51:00)
	 * 
	 * @return double[]
	 */
	public double[] getS() {
		return s;
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:49:21)
	 * 
	 * @return double[]
	 */
	public double[] getX() {
		return x;
	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:16:35)
	 */
	public static boolean isCorrectParameters(double[] anArrayX,
			double[] anArrayS) {
		if (anArrayX.length != anArrayS.length) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Длина массивов Х и F д.б. одинаковой",
					"Произвольное распределение",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		boolean error = false;
		for (int i = 1; i < anArrayS.length; i++) {
			if (anArrayS[i] < anArrayS[i - 1]) {
				error = true;
				break;
			}
		}
		if (error) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Vассив вероятностей д.б. неубывающим",
					"Произвольное распределение",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for (int i = 1; i < anArrayX.length; i++) {
			if (anArrayX[i] <= anArrayX[i - 1]) {
				error = true;
				break;
			}
		}
		if (error) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Vассив значений переменной д.б. возрастающим",
					"Произвольное распределение",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (anArrayS[0] != 0) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Первый элемент массива вероятностей д.б. нулем",
					"Произвольное распределение",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		if (anArrayS[anArrayX.length - 1] != 1) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Последний элемент массива вероятностей д.б. единицей",
					"Произвольное распределение",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:48:08)
	 * 
	 * @return double
	 * @param aNumber
	 *            double
	 */
	public double probability(double aNumber) {
		if (aNumber <= x[0]) {
			return 0;
		}
		if (aNumber >= x[x.length - 1]) {
			return 1;
		}
		int index = x.length - 1;
		for (int i = 1; i < x.length; i++) {
			if (x[i] > aNumber) {
				index = i;
				break;
			}
			;
		}
		;
		return s[index - 1] + (s[index] - s[index - 1])
				/ (x[index] - x[index - 1]) * (aNumber - x[index - 1]);
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:51:00)
	 * 
	 * @param newS
	 *            double[]
	 */
	public void setS(double[] newS) {
		s = newS;
	}

	/**
	 * Insert the method's description here. Creation date: (02.04.2005 9:49:21)
	 * 
	 * @param newX
	 *            double[]
	 */
	public void setX(double[] newX) {
		x = newX;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString() {
		if (x == null)
			return "Параметри не визначені";
		return "Довільне(" + x[0] + "..." + x[x.length - 1] + ")";
	}

	public double average() {
		double m = 0;
		for (int i = 1; i < x.length; i++) {
			m += (x[i - 1] + x[i]) / 2 * (s[i] - s[i - 1]);
		}
		return m;
	}

	public double max() {
		return x[x.length - 1];
	}

	public boolean average(double m) {
		return false;
	}

	@Override
	public RandomDlg getDialog() {
		
		return new LinearDlg();
	}

}
