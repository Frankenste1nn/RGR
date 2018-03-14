package rnd;

/**
 * Insert the type's description here. Creation date: (27.03.2005 19:59:25)
 * 
 * @author: Administrator
 */
public class Uniform extends RandomGenerators {
	private double min = 0;

	private double max = 1;

	/**
	 * Uniform constructor comment.
	 */
	public Uniform() {
		super();
	}

	public Uniform(double newMin, double newMax) {
		super();
		min = newMin;
		max = newMax;
	}
	public double average(){
		return (max+min)/2;
		}

	/**
	 * Uniform constructor comment.
	 */
	public Uniform(double newMin, double newMax, boolean round) {
		super();
		min = newMin;
		max = newMax;
		setNextAsRound(round);
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 19:59:25)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		return min + (max - min) * rnd.nextDouble();
	}
	public double basicNext(double r) {
		return min + (max - min) * r;
	}
	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 21:01:27)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 0;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 18:48:57)
	 * 
	 * @return double
	 */
	public double getM() {
		return (min + max) / 2;
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:01:09)
	 * 
	 * @return double
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:00:38)
	 * 
	 * @return int
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:09:47)
	 * 
	 * @return boolean
	 */
	public static boolean isCorrectParameters(double min, double max) {
		if (min > max) {
			javax.swing.JOptionPane.showMessageDialog(null, "Мінімальний "
					+ min + " більше максимального " + max,
					"Рівномірний розподіл",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 18:34:58)
	 * 
	 * @return double
	 */
	public double probability(double aNumber) {
		if (aNumber <= min) {
			return 0;
		}
		;
		if (aNumber >= max) {
			return 1;
		}
		;
		return (aNumber - min) / (max - min);
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 18:48:57)
	 * 
	 * @param newM
	 *            double
	 */
	public void setM(double newM) {
		setMin(0);
		setMax(2 * newM);
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:01:09)
	 * 
	 * @param newMax
	 *            double
	 */
	public void setMax(double newMax) {
		max = newMax;
	}

	/**
	 * Insert the method's description here. Creation date: (27.03.2005
	 * 20:00:38)
	 * 
	 * @param newMin
	 *            double
	 */
	public void setMin(double newMin) {
		min = newMin;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString() {
		return "Рівномірне(" + min + ";" + max + ")";
	}
	public double max(){
		return max;
		}

	public boolean average(double m) {
		
		return false;
	}

	@Override
	public RandomDlg getDialog() {
		return new UniformDlg();
	}

}
