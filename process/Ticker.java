package process;

import java.util.concurrent.CopyOnWriteArrayList;



public class Ticker extends Actor {
	int mlsTickInterval;
	double modelingTimeTickInterval;
	double finishTime;
	CopyOnWriteArrayList<ITickEventListener> tickEventListeners;

	public Ticker(int mlsTickInterval, double modelingTimeTickInterval,
			double finishTime) {
		super();
		this.mlsTickInterval = mlsTickInterval;
		this.modelingTimeTickInterval = modelingTimeTickInterval;
		this.finishTime = finishTime;
	}

	@Override
	protected void rule() {
		while( getDispatcher().getCurrentTime()<=finishTime){
			fireTickEvent(new TickEvent(this));
			try {
				Thread.sleep(mlsTickInterval);
			} catch (InterruptedException e) {
				return;
			}
			holdForTime(modelingTimeTickInterval);
		}
		
	}
	public void addTickEventListener(ITickEventListener lsr){
		if(tickEventListeners==null)
			tickEventListeners=new CopyOnWriteArrayList<>();
		tickEventListeners.add(lsr);
	}
	
	public void removeTeeEventListener(ITickEventListener lsr){
		if(tickEventListeners!=null)
			tickEventListeners.remove(lsr);
	}
	private void fireTickEvent(TickEvent tickEvent) {
		if(tickEventListeners!=null)
			for(ITickEventListener lsr:tickEventListeners){
				lsr.onTick(tickEvent);
			}
	}

}
