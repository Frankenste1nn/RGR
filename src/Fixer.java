package src;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Norm;
import widgets.ChooseRandom;

public class Fixer extends Actor {
	private QueueForTransactions<PC> queue;
	private QueueForTransactions<PC> f_queue;
	private QueueForTransactions<PC> p_queue;
	private double finishTime;
	private ChooseRandom rnd;
	private GUI gui;

	public Fixer(String string, GUI gui, Model model) {
		// TODO Auto-generated constructor stub
		setNameForProtocol(string);
		finishTime = gui.getChooseData_Modelling_Time().getDouble();
		rnd = gui.getChooseRandom_Fixing_Time();
		queue = model.getQueue();
		f_queue = model.getFQueue();
		p_queue = model.getPQueue();
		this.gui = gui;
	}

	@Override
	protected void rule() throws DispatcherFinishException {
		// TODO Auto-generated method stub
		BooleanSupplier queueSize = () -> f_queue.size() > 0;
		while (getDispatcher().getCurrentTime() <= finishTime) {
			waitForCondition(queueSize, "у черзі має з'явиться транзакція");
			PC transaction = f_queue.removeFirst();
			holdForTime(rnd.next());

			ChooseRandom d = new ChooseRandom();

			double n = Math.random();

			if (!(n > gui.getChooseData_fail_chance().getDouble())) {

			} else {
				BooleanSupplier queueReady = () -> p_queue.size() < gui.getChooseData_Box_Count().getInt();
				waitForCondition(queueReady, "packing queue is full watiting....");
				p_queue.add(transaction);
			}
		}
	}

}
