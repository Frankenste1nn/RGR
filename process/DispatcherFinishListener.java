package process;


/**
 * A "DispatcherFinish" event gets fired whenever a "process.dispatcher" has
 * finished its work. You can register a DispatcherFinishListener with a source
 * dispatcher so as to be notified of "dispatcherFinish" event.
 */
 public interface DispatcherFinishListener extends java.util.EventListener {

	/**
	 * This method gets called when a "process.dispatcher" has finished its
	 * work..
	 * 
	 * @param evt
	 *            a DispatcherFinishEvent object describing the event source.
	 */

	void onDispatcherFinish();
}
