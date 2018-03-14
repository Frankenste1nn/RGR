package process;

import stat.Histo;
import widgets.Painter;
import widgets.trans.ITransMonitoring;

public class Store implements ITransMonitoring {
	private double size = 0;

	private Dispatcher dispatcher;

	private String nameForProtocol ;

	private Painter painter;

	protected Histo histo;

	protected double lastTime;

	private double sum;

	private double accumTime;

	public Store() {
		 super();
		 nameForProtocol = "Накпичувач";
	}
	public Store(String name) {
		 this();
		 nameForProtocol = name;
	}
	public Store(String name, Dispatcher disp) {
		 this(name);
		 dispatcher = disp;
	}
	public Store(String name, Dispatcher disp, Painter painter) {
		 this(name, disp);
		 this.painter = painter;
	}
	public Store(String name, Dispatcher disp, Histo histo) {
		 this(name, disp);
		 this.histo = histo;
	}
	public Store(String name, Dispatcher disp, Painter painter, Histo histo) {
		 this(name, disp,painter);
		 this.histo = histo; 
	}

	public void add(double z) {
		beforeAdd();
		size+=z;
		afterAdd();
	}

	protected void afterAdd() {
		drawDiagram();
		dispatcher.printToProtocol("  "+nameForProtocol + ". Стало "
				+ Double.toString(getSize()));
	}

	protected void afterRemove() {
		drawDiagram();
		dispatcher.printToProtocol("  "+nameForProtocol + ". Стало "
				+ Double.toString(getSize()));
	}

	protected void beforeAdd() {
		accum();
		drawDiagram();
	}

	protected void beforeRemove() {
		accum();
		drawDiagram();
	}

	private void accum() {
		double dt = getDispatcher().getCurrentTime() - lastTime;
		lastTime = getDispatcher().getCurrentTime();
		sum += (dt * this.getSize());
		accumTime += dt;
		if (histo!=null)histo.addFrequencyForValue(dt,this.getSize());
	}

		
	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 13:46:58)
	 */
	private void drawDiagram() {
		if (painter != null && painter.getDiagram() != null) {
			painter.drawToXY((float) dispatcher.getCurrentTime(), (float)getSize());
		}
	}

	public process.Dispatcher getDispatcher() {
		if(dispatcher==null)
			try {
				throw new Exception("Диспетчер не определен для "+this.nameForProtocol);
			} catch (Exception e) {
				System.out.println("Диспетчер не определен для "+this.nameForProtocol);
				e.printStackTrace();
			}
		return dispatcher;
	}

	public double getSize() {
		return size;
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
		size=0;
		lastTime=0;
		if (painter != null) {
			if (painter.getDiagram() != null)
				painter.placeToXY(0, 0);
		}
	}

	public double remove(double z) {
		if (z>size) z=size;
		this.beforeRemove();
		size-=z;
		this.afterRemove();
		return z;
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

	public Histo getHisto() {
		return histo;
	}

	public void setHisto(Histo histo) {
		this.histo = histo;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
//		dispatcher.addDispatcherStartListener(new DispatcherStartListener(){
//
//			public void onDispatcherStart(EventObject evt) {
//				Store.this.init();
//				
//			}
//			
//		});
	}

	/**
	 * Метод забезпечує реалізацію інтерфейсу ItransProcessable у моделі. У
	 * методі приватним атрибутам класу, що використовуються при обчисленні
	 * інтервального середнього для розміру купи грунту, встановлюються
	 * початкові значення
	 */
	public void resetAccum() {
		lastTime = getDispatcher().getCurrentTime();
		sum = 0;
		accumTime = 0;
	}

	/**
	 * Метод розширює одноіменний метод суперкласу і забезпечує виклик методу
	 * accumSum() перед зміною розміру купи грунту.
	 */
	public double getAccumAverage() {
		accum();
		return sum / accumTime;
	}
}
