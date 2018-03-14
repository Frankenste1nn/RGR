package rnd;

/**
 * Insert the type's description here. Creation date: (23.03.2005 11:31:22)
 * 
 * @author: Administrator
 */
public class Negexp extends RandomGenerators {
	private double m = 1;

	/**
	 * Negexp constructor comment.
	 */
	public Negexp() {
		super();

	}

	/**
	 * Negexp constructor comment.
	 */
	public Negexp(double newM) {
		this();
		m = newM;

	}

	/**
	 * Negexp constructor comment.
	 */
	public Negexp(double newM, boolean round) {
		this(newM);
		setNextAsRound(round);
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:31:22)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		double r;
		do {
			r = rnd.nextDouble();
		} while (r == 0);
		return -Math.log(r) * m;
	}
	public double basicNext(double r) {
		
		return -Math.log(r) * m;
	}
	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 20:55:53)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 1;
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 12:18:44)
	 * 
	 * @return double
	 */
	public double getM() {
		return m;
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:34:09)
	 * 
	 * @return double
	 */
	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:09:47)
	 * 
	 * @return boolean
	 */
	public static boolean isCorrectParameters(double m) {
		if (m <= 0) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Середнє значення интервалу має бути більше 0",
					"Експоненційне розподілення",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 18:42:49)
	 * 
	 * @return double
	 * @param aNumber
	 *            double
	 */
	public double probability(double aNumber) {
		if (aNumber <= 0) {
			return 0;
		}
		;
		return 1 - Math.exp(-aNumber / m);
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:34:09)
	 * 
	 * @param newLamda
	 *            double
	 */
	public void setM(double newM) {
		m = newM;
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:48:22)
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return "Експоненціальне(m=" + getM() + ")";
	}
	public double average(){
		return m;
		}
	public double max(){
		return 3* m;
		}

	public boolean average(double m) {
		this.m=m;
		return true;
	}	
	@Override
	public RandomDlg getDialog() {
		return new NegexpDlg();
	}

}
