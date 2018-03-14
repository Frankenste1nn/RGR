package process;

import java.util.EventObject;

/**
 * A "ChangeTime" event gets fired whenever a "process.dispatcher" changes a
 * "currentTime" property. You can register a ChangeTimeListener with a source
 * dispatcher so as to be notified of any "currentTime" updates.
 */
public interface ChangeTimeListener extends java.util.EventListener {

	/**
	 * This method gets called when a "currentTime" property is changed.
	 * 
	 * @param evt
	 *            A ChangeTimeEvent object describing the event source and the
	 *            property that has changed.
	 */

	void onChangeTime(EventObject evt);
}