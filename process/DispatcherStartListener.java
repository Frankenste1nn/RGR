package process;

/**
 * A "DispatcherStart" event gets fired whenever a "process.dispatcher" has
 * finished its work. You can register a DispatcherStartListener with a source
 * dispatcher so as to be notified of "dispatcherStart" event.
 */
public interface DispatcherStartListener extends java.util.EventListener {
	/**
	 * This method gets called when a "process.dispatcher" has started.
	 * 
	 * @param evt
	 *            a DispatcherStartEvent object describing the event source.
	 */

	void onDispatcherStart();
}
