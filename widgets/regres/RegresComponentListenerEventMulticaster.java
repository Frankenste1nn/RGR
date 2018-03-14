package widgets.regres;

/**
 * This is the event multicaster class to support the
 * experiment.regres.RegresComponentListenerEventMulticaster interface.
 */
public class RegresComponentListenerEventMulticaster extends
		java.awt.AWTEventMulticaster implements widgets.regres.RegresComponentListener {
	/**
	 * Constructor to support multicast events.
	 * 
	 * @param a
	 *            java.util.EventListener
	 * @param b
	 *            java.util.EventListener
	 */
	protected RegresComponentListenerEventMulticaster(
			java.util.EventListener a, java.util.EventListener b) {
		super(a, b);
	}

	/**
	 * Add new listener to support multicast events.
	 * 
	 * @return experiment.regres.RegresComponentListener
	 * @param a
	 *            experiment.regres.RegresComponentListener
	 * @param b
	 *            experiment.regres.RegresComponentListener
	 */
	public static widgets.regres.RegresComponentListener add(
			widgets.regres.RegresComponentListener a, widgets.regres.RegresComponentListener b) {
		return (widgets.regres.RegresComponentListener) addInternal(a, b);
	}

	/**
	 * Add new listener to support multicast events.
	 * 
	 * @return java.util.EventListener
	 * @param a
	 *            java.util.EventListener
	 * @param b
	 *            java.util.EventListener
	 */
	protected static java.util.EventListener addInternal(
			java.util.EventListener a, java.util.EventListener b) {
		if (a == null)
			return b;
		if (b == null)
			return a;
		return new RegresComponentListenerEventMulticaster(a, b);
	}

	/**
	 * 
	 * @param newEvent
	 *            java.util.EventObject
	 */
	public void JButtonStartAction_actionPerformed(
			java.util.EventObject newEvent) {
		((widgets.regres.RegresComponentListener) a)
				.JButtonStartAction_actionPerformed(newEvent);
		((widgets.regres.RegresComponentListener) b)
				.JButtonStartAction_actionPerformed(newEvent);
	}

	/**
	 * 
	 * @return java.util.EventListener
	 * @param oldl
	 *            experiment.regres.RegresComponentListener
	 */
	protected java.util.EventListener remove(widgets.regres.RegresComponentListener oldl) {
		if (oldl == a)
			return b;
		if (oldl == b)
			return a;
		java.util.EventListener a2 = removeInternal(a, oldl);
		java.util.EventListener b2 = removeInternal(b, oldl);
		if (a2 == a && b2 == b)
			return this;
		return addInternal(a2, b2);
	}

	/**
	 * Remove listener to support multicast events.
	 * 
	 * @return experiment.regres.RegresComponentListener
	 * @param l
	 *            experiment.regres.RegresComponentListener
	 * @param oldl
	 *            experiment.regres.RegresComponentListener
	 */
	public static widgets.regres.RegresComponentListener remove(
			widgets.regres.RegresComponentListener l,
			widgets.regres.RegresComponentListener oldl) {
		if (l == oldl || l == null)
			return null;
		if (l instanceof RegresComponentListenerEventMulticaster)
			return (widgets.regres.RegresComponentListener) ((widgets.regres.RegresComponentListenerEventMulticaster) l)
					.remove(oldl);
		return l;
	}
}
