package src;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class Checker extends Actor {

	private QueueForTransactions<PC> queue;
	private QueueForTransactions<PC> f_queue;
	private QueueForTransactions<PC> p_queue;
	private Randomable rnd;
	private double finishTime;

	public Checker(String name, GUI gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseData_Modelling_Time().getDouble();
		rnd = gui.getChooseRandom_Testing_Time();
		queue = model.getQueue();
	}

	public void setHistoForActorWaitingTime(Object histoWaitDevice) {

	}

	@Override
	protected void rule() throws DispatcherFinishException {
		BooleanSupplier queueSize = () -> queue.size() > 0;
		while (getDispatcher().getCurrentTime() <= finishTime) {
			waitForCondition(queueSize, "у черзі має з'явиться транзакція");
			PC transaction = queue.removeFirst();
			holdForTime(rnd.next());
			if(transaction.isBroken()) {
				f_queue.add(transaction);
			} else 
				p_queue.add(transaction);
		}
	}

}
