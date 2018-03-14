package rnd;

/**
 * Insert the type's description here. Creation date: (23.03.2005 10:33:36)
 * 
 * @author: Administrator
 */
public abstract class RandomGenerators implements Randomable {
	protected java.util.Random rnd;

	private boolean nextAsRound = false;

	/**
	 * У конструкторі перед створенням об'єкту rnd 
	 * створюється затримка до зміни значення системного таймеру.
	 * Це запобігає створенню генераторів, що мають однакове зерно.
	 */
	public RandomGenerators() {
		super();
		long t = System.currentTimeMillis();
		do {
		} while (t == System.currentTimeMillis());
		rnd = new java.util.Random();
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:21:36)
	 * 
	 * @return boolean
	 */
	public abstract double basicNext();
	public abstract double basicNext(double r);

	/**
	 * Insert the method's description here. Creation date: (22.01.2006
	 * 20:53:26)
	 * 
	 * @return int
	 */
	public abstract int getKParm();

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:09:56)
	 * 
	 * @return boolean
	 */
	public boolean isNextAsRound() {
		return nextAsRound;
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:22:40)
	 * 
	 * @return double
	 */
	public double next() {
		if (nextAsRound) {
			return Math.round(basicNext());
		}
		;
		return basicNext();
	}

	/**
	 * Insert the method's description here. Creation date: (28.03.2005
	 * 18:34:58)
	 * 
	 * @return double
	 */
	public double probability(double aNumber1, double aNumber2) {
		if (aNumber2 <= aNumber1) {
			return 0;
		}
		;
		return probability(aNumber2) - probability(aNumber1);
	}

	/**
	 * Insert the method's description here. Creation date: (23.03.2005
	 * 11:09:56)
	 * 
	 * @param newNextAsInteger
	 *            boolean
	 */
	public void setNextAsRound(boolean newNextAsRound) {
		nextAsRound = newNextAsRound;
	}

	public abstract RandomDlg  getDialog();
	
}
