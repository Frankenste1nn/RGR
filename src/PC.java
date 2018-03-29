package src;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;

public class PC extends Actor{
	private boolean broken;
	private int fixed;
	private QueueForTransactions<PC> queue;
	private double createTime;
	private DiscretHisto histoQueue;
	private Histo histoService;
	private boolean serviceDone;
	
	public PC(boolean broken, Model model) {
		fixed = 0;
		this.broken = broken;
		this.queue = model.getQueue();
		this.histoQueue = model.getDiscretHistoQueue();
		this.histoService = (Histo)model.getHistoWaitDevice();
	}
	public boolean isBroken() {
		return broken;
	}
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
	public double getCreateTime() {
		return createTime;
	}

	public void setServiceDone(boolean b) {
		this.serviceDone = b;
	}

	@Override
	public String toString() {
		return "Transaction " + createTime;
	}

	@Override
	protected void rule() throws DispatcherFinishException {
		createTime = dispatcher.getCurrentTime();
		nameForProtocol = "���������� " + createTime;
		queue.add(this);
		waitForCondition(() -> !queue.contains(this), "����� ������� �� ��������������");
		histoQueue.add(dispatcher.getCurrentTime() - createTime);
		waitForCondition(() -> serviceDone, "����� ��������� ��������������");
		histoService.add(dispatcher.getCurrentTime() - createTime);
	}


}
