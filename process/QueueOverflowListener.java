package process;


/**
 * Insert the type's description here. Creation date: (03.02.2006 21:59:23)
 * 
 * @author: Administrator
 */
public interface QueueOverflowListener extends java.util.EventListener {
	/**
	 * Insert the method's description here. Creation date: (03.02.2006
	 * 22:00:50)
	 */
	void onQueueOverflow(QueueOverflowEvent evt);
}
