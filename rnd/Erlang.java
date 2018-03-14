package rnd;

/**
 * Insert the type's description here. Creation date: (28.03.2005 21:44:50)
 * 
 * @author: Administrator
 */
public class Erlang extends RandomGenerators {
	private int k = 2;

	private double m = 1;

	/**
	 * Erlang constructor comment.
	 */
	public Erlang() {
		super();
	}

	/**
	 * Erlang constructor comment.
	 */
	public Erlang(double newM, int newK) {
		this();
		m = newM;
		k = newK;

	}

	/**
	 * Erlang constructor comment.
	 */
	public Erlang(double newM, int newK, boolean round) {
		this(newM, newK);
		setNextAsRound(round);

	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:44:50)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		double p = 1;
		double r;
		for (int i = 0; i < k; i++) {
			// Накапливаем произведение, но нули пропускаем
			while ((r = rnd.nextDouble()) == 0) {
			}
			;
			p = p * r;
		}
		return -m / k * (Math.log(p));
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:47:28)
	 * 
	 * @return int
	 */
	public int getK() {
		return k;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:47:28)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 2;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:47:56)
	 * 
	 * @return double
	 */
	public double getM() {
		return m;
	}

	/**
	 * Insert the method's description here. Creation date: (30.03.2005
	 * 19:40:24)
	 * 
	 * @return boolean
	 * @param m
	 *            double
	 * @param k
	 *            int
	 */
	public static boolean isCorrectParameters(double m, int k) {
		if (m <= 0) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Середнє значення інтервалу м.б. більше 0",
					"Розподіл Ерланга",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		if (k < 1) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Значення k м.б. більше 0", "Розподіл Ерланга",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:44:50)
	 * 
	 * @return double
	 * @param aNumber
	 *            double
	 */
	public double probability(double aNumber) {
		double s = 1;
		double sum = 1;
		double kLamda = k / m;
		for (int i = 1; i < k; i++) {
			s = s * kLamda * aNumber / i;
			sum = sum + s;
		}
		if (kLamda * aNumber > 700) {
			return 1;
		} else {
			return 1 - (sum * Math.exp(-kLamda * aNumber));
		}
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:47:28)
	 * 
	 * @param newK
	 *            int
	 */
	public void setK(int newK) {
		k = newK;
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 21:47:56)
	 * 
	 * @param newM
	 *            double
	 */
	public void setM(double newM) {
		m = newM;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString() {
		// Insert code to print the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		return "Ерланга(m=" + getM() + ";k=" + getK() + ")";
	}
	public double average(){
		return m;
		}
	public double max(){
		return (2+1.0/k)* m;
		}

	@Override
	public double basicNext(double r) {
		// No sens
		return 0;
	}

	public boolean average(double m) {
		this.m=m;
		return true;
	}

	@Override
	public RandomDlg getDialog() {
		return new ErlangDlg();
	}

	

}
