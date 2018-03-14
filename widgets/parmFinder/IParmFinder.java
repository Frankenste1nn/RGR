package widgets.parmFinder;

public interface IParmFinder {

	public abstract void addFinderFinishListener(FinderFinishListener listener);

	public abstract void removeFinderFinishListener(
			FinderFinishListener listener);

	/**
	 * Insert the method's description here. Creation date: (25.03.2006
	 * 17:01:32)
	 * 
	 * @param newPainter
	 *            paint.Painter
	 */
	public abstract void setPainter(widgets.Painter newPainter);

	public abstract void setTimeArray(Double[] timeArray);

	public abstract void setValueArray(Double[] valueArray);
	public abstract double getResult();
	public abstract void startSearch();
}