package rnd;

/**
 * Insert the type's description here. Creation date: (01.04.2005 17:13:26)
 * 
 * @author: Administrator
 */
public class Discret extends RandomGenerators {
	private double[] x;

	private double[] s;

	private double[] p;

	/**
	 * Discret constructor comment.
	 */
	public Discret() {
		super();
	}

	/**
	 * Discret constructor comment.
	 */
	public Discret(double[] anArrayX, double[] anArrayP) {
		this();
		x = anArrayX;
		setP(anArrayP);
	}

	/**
	 * Discret constructor comment.
	 */
	public Discret(double[] anArrayX, double[] anArrayP, boolean round) {
		this(anArrayX, anArrayP);
		setNextAsRound(round);
	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:13:26)
	 * 
	 * @return boolean
	 */
	public double basicNext() {
		double r = rnd.nextDouble();
		for (int i = 0; i < s.length; i++) {
			if (s[i] > r) {
				return x[i];
			}
			;
		}
		;
		return x[x.length-1];
	}
	public double basicNext(double r) {
		for (int i = 0; i < s.length; i++) {
			if (s[i] > r) {
				return x[i];
			}
			;
		}
		;
		return x[x.length-1];
	}

	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 20:54:51)
	 * 
	 * @return int
	 */
	public int getKParm() {
		return 0;
	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:34:19)
	 * 
	 * @return double[]
	 */
	public double[] getP() {
		return p;
	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:34:19)
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
			double[] anArrayP) {
		if (anArrayX.length != anArrayP.length) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Довжина масивів Х и P м.б. однаковою",
					"Дискретний розподіл",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;

		double sum = 0;
		for (int i = 0; i < anArrayP.length; i++) {
			sum = sum + anArrayP[i];
		}
		if (Math.abs(1 - sum) > 0.00001) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Сумма елементів масиву Р м.б. 1",
					"Дискретний розподіл",
					javax.swing.JOptionPane.ERROR_MESSAGE);
			return false;
		}
		;
		return true;

	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:13:26)
	 * 
	 * @return double
	 * @param aNumber
	 *            double
	 */
	public double probability(double aNumber) {
		if (aNumber < x[0]) {
			return 0;
		}

		if (aNumber >= x[x.length - 1]) {
			return 1;
		}

		for (int i = x.length - 1; i >= 0; i--) {
			if (aNumber >= x[i]) {
				return s[i];
			}
			;
		}
		;
		return 0;

	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:34:19)
	 * 
	 * @param newX
	 *            double[]
	 */
	public void setP(double[] anArrayP) {
		p = anArrayP;
		s = new double[p.length];
		s[0] = p[0];
		for (int i = 1; i < p.length; i++)
			s[i] = s[i - 1] + p[i];
	}

	/**
	 * Insert the method's description here. Creation date: (01.04.2005
	 * 17:34:19)
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
		if(x==null) return "Параметри не визначені";
		return "Дискретне(" + x[0] + "..." + x[x.length - 1] + ")";
	}
	
	public double average(){
		double m=0;
		for (int i = 0; i < x.length; i++) {
			m+=x[i]*p[i];
		} 
		return m;
		}
	public double max(){
		return x[x.length-1];
		}

	public boolean average(double m) {
		
		return false;
	}

	@Override
	public RandomDlg getDialog() {
		
		return new DiscretDlg();
	}
}	

