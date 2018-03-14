package rnd;

/**
 * Insert the type's description here. Creation date: (16.04.2005 18:52:10)
 * 
 * @author: Administrator
 */
public interface Randomable {
	/**
	 * Insert the method's description here. Creation date: (16.04.2005
	 * 18:53:04)
	 * 
	 * @return double
	 */
	double next();

	/**
	 * Insert the method's description here. Creation date: (16.04.2005
	 * 18:54:33)
	 * 
	 * @return double
	 * @param param
	 *            double
	 */
	double probability(double aNumber);

	/**
	 * Insert the method's description here. Creation date: (16.04.2005
	 * 18:54:33)
	 * 
	 * @return double
	 * @param param
	 *            double
	 */
	double probability(double aNumber1, double aNumber2);
	/**
	 * Повертає середнє значення випадкової величини
	 * @return
	 */
	double average();
	/**
	 * Set середнє значення випадкової величини
	 * @return
	 */
	boolean average(double  m);
	double max();
}
