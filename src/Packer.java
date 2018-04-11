package src;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import widgets.ChooseRandom;

public class Packer extends Actor {

	private ChooseRandom rnd;
	private double finishTime;
	private GUI gui;
	private QueueForTransactions<PC> p_queue;

	public Packer(String string, GUI gui, Model model) {
		// TODO Auto-generated constructor stub
		// TODO Auto-generated method stub
		setNameForProtocol(string);
		finishTime = gui.getChooseData_Modelling_Time().getDouble();
		rnd = gui.getChooseRandom_Fixing_Time();

		p_queue = model.getPQueue();
		this.gui = gui;
	}

	@Override
	protected void rule() throws DispatcherFinishException {
		BooleanSupplier queueSize = () -> p_queue.size() > 0;

		while (getDispatcher().getCurrentTime() <= finishTime) {
			waitForCondition(queueSize, "� ���� �� �'������� ����������");
			PC transaction = p_queue.removeFirst();
			holdForTime(rnd.next());
			transaction.setServiceDone(true);
			if (p_queue.size() >= gui.getChooseData_Box_Count().getInt()) {
				while (p_queue.size() != 0) {
					p_queue.removeFirst();
				}
				holdForTime(rnd.next());
			}
		}

	}
}