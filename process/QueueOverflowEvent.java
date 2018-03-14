package process;

/**
 * Insert the type's description here. Creation date: (03.02.2006 21:44:57)
 * 
 * @author: Administrator
 */
public class QueueOverflowEvent extends java.util.EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Object transaction;

	/**
	 * QueueOverflow constructor comment.
	 * 
	 * @param target
	 *            java.lang.Object
	 * @param id
	 *            int
	 * @param arg
	 *            java.lang.Object
	 */
	public QueueOverflowEvent(Object source) {
		super(source);
	}

	/**
	 * QueueOverflow constructor comment.
	 * 
	 * @param target
	 *            java.lang.Object
	 * @param id
	 *            int
	 * @param arg
	 *            java.lang.Object
	 */
	public QueueOverflowEvent(Object source, Object aTransaction) {
		super(source);
		transaction = aTransaction;
	}

	/**
	 * Insert the method's description here. Creation date: (04.02.2006 9:46:34)
	 * 
	 * @return java.lang.Object
	 */
	public java.lang.Object getTransaction() {
		return transaction;
	}

	/**
	 * Insert the method's description here. Creation date: (04.02.2006 9:46:34)
	 * 
	 * @param newTransaction
	 *            java.lang.Object
	 */
	void setTransaction(java.lang.Object newTransaction) {
		transaction = newTransaction;
	}
}
