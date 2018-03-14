package process;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.BooleanSupplier;

import stat.DiscretHisto;
import stat.IHisto;
import widgets.trans.ITransMonitoring;

public class QueueForTransactions<T> implements Iterable<T>, ITransMonitoring {

	private Deque<T> deque = new ArrayDeque<>();

	private int maxSize = Integer.MAX_VALUE;

	private String nameForProtocol = "Черга";

	private widgets.Painter painter;

	private Vector<QueueOverflowListener> queueOverflowListeners;

	private double lastTime = 0;

	private DiscretHisto discretHisto = null;

	private Dispatcher dispatcher;

	private double sum;

	private double accumTime;

	public QueueForTransactions() {
		super();

	}

	public QueueForTransactions(String name) {
		this();
		nameForProtocol = name;
	}

	public QueueForTransactions(String name, Dispatcher dispatcher) {
		this(name);
		this.dispatcher = dispatcher;
	}

	public QueueForTransactions(String name, Dispatcher dispatcher, DiscretHisto discretHisto) {
		this(name, dispatcher);
		this.discretHisto = discretHisto;
	}

	public QueueForTransactions(String name, Dispatcher dispatcher, DiscretHisto discretHisto, int maxSize) {
		this(name, dispatcher, discretHisto);
		this.maxSize = maxSize;
	}

	public boolean add(T o) {
		return this.addLast(o);
	}

	public synchronized boolean addLast(T o) {
		boolean result = false;
		this.beforeAdd();
		if (deque.size() < maxSize) {
			result = deque.add(o);
			if (result)
				getDispatcher().printToProtocol("  " + nameForProtocol + " приймає танзакцію.");
		} else {
			QueueOverflowEvent evt = new QueueOverflowEvent(this, o);
			fireQueueOverflowEvent(evt);
			getDispatcher().printToProtocol("  " + nameForProtocol + " Танзакцію втрачено. Нема місця у черзі ");
		}
		this.afterAdd();
		return result;
	}

	public synchronized void addQueueOverflowListener(QueueOverflowListener listener) {
		if (queueOverflowListeners == null) {
			queueOverflowListeners = new java.util.Vector<QueueOverflowListener>();
		}
		queueOverflowListeners.addElement(listener);
	}

	protected void afterAdd() {
		drawDiagram();
		getDispatcher().printToProtocol("  " + nameForProtocol + ". Стало " + Integer.toString(deque.size()));
	}

	protected void afterRemove() {
		drawDiagram();
		getDispatcher().printToProtocol("  " + nameForProtocol + ". Стало " + Integer.toString(deque.size()));
	}

	protected void beforeAdd() {
		drawDiagram();
		accum();
	}

	protected void beforeRemove() {
		drawDiagram();
		accum();
	}

	private void accum() {
		double dt = getDispatcher().getCurrentTime() - lastTime;
		lastTime = getDispatcher().getCurrentTime();
		sum += (dt * this.size());
		accumTime += dt;
		if (discretHisto != null) {
			discretHisto.addFrequencyForValue(dt, deque.size());
		}
	}

	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 13:46:58)
	 */
	private void drawDiagram() {
		if (painter != null && painter.getDiagram() != null) {

			painter.drawToXY((float) getDispatcher().getCurrentTime(), (float) deque.size());
		}
	}

	protected Dispatcher getDispatcher() {
		if (dispatcher == null)
			try {
				throw new Exception("Диспетчер не определен для " + this.nameForProtocol);
			} catch (Exception e) {
				System.out.println("Диспетчер не определен для " + this.nameForProtocol);
				e.printStackTrace();
			}
		return dispatcher;
	}

	private void fireQueueOverflowEvent(QueueOverflowEvent evt) {
		Vector<QueueOverflowListener> targets = null;
		synchronized (this) {
			if (queueOverflowListeners != null) {
				targets = (Vector<QueueOverflowListener>) queueOverflowListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				QueueOverflowListener target = (QueueOverflowListener) targets.elementAt(i);
				target.onQueueOverflow(evt);
			}
		}

	}

	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:38:55)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getNameForProtocol() {
		return nameForProtocol;
	}

	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 13:42:30)
	 * 
	 * @return paint.Painter
	 */
	public widgets.Painter getPainter() {
		return painter;
	}

	public synchronized void init() {
		deque.clear();
		if (painter != null) {
			if (painter.getDiagram() != null)
				painter.placeToXY(0, 0);
		}
		lastTime = 0;
	}

	public synchronized boolean contains(Object o) {
		return deque.contains(o);
	}

	public synchronized boolean remove(Object o) {
		this.beforeRemove();
		boolean result = deque.remove(o);
		this.afterRemove();
		return result;
	}

	public synchronized T peekFirst() {
		return deque.peekFirst();
	}

	public synchronized T removeFirst() {
		this.beforeRemove();
		T o = deque.removeFirst();
		this.afterRemove();
		return o;
	}

	public synchronized void removeQueueOverflowListener(QueueOverflowListener listener) {
		if (queueOverflowListeners == null) {
			return;
		}
		queueOverflowListeners.removeElement(listener);
	}

	public int size() {
		return deque.size();
	}

	public void setMaxSize(int newMaxSize) {
		maxSize = newMaxSize;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:38:55)
	 * 
	 * @param newNameForProtocol
	 *            java.lang.String
	 */
	public void setNameForProtocol(java.lang.String newNameForProtocol) {
		nameForProtocol = newNameForProtocol;
	}

	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 13:42:30)
	 * 
	 * @param newPainter
	 *            paint.Painter
	 */
	public void setPainter(widgets.Painter newPainter) {
		painter = newPainter;
	}

	public IHisto getDiscretHisto() {
		return discretHisto;
	}

	public void setDiscretHisto(DiscretHisto aDiscretHisto) {
		discretHisto = aDiscretHisto;
	}

	public void setDispatcher(Dispatcher disp) {
		dispatcher = disp;
		dispatcher.addDispatcherFinishListener(new DispatcherFinishListener() {

			public void onDispatcherFinish() {
				accum();
				drawDiagram();
			}
		});

	}

	/* (non-Javadoc)
	 * @see process.ITransMonitoring#resetAccum()
	 */
	@Override
	public void resetAccum() {
		lastTime = getDispatcher().getCurrentTime();
		sum = 0;
		accumTime = 0;
	}

	/* (non-Javadoc)
	 * @see process.ITransMonitoring#getAccumAverage()
	 */
	@Override
	public double getAccumAverage() {
		this.accum();
		return sum / accumTime;
	}

	@Override
	public Iterator<T> iterator() {

		return deque.iterator();
	}

	/**
	 * 
	 * @return копію колекції, що зберігає елементи черги
	 */
	public Deque<T> getDeque() {
		Deque<T> copy = new ArrayDeque<>(deque.size());
		for (T x : deque)
			copy.add(x);
		return copy;
	}

	public BooleanSupplier notEmpty() {
		return () -> size() > 0;
	}

	public BooleanSupplier notFull() {
		return () -> size() < maxSize;
	}
}
