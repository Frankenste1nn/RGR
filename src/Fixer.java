package src;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import widgets.ChooseRandom;

public class Fixer extends Actor {
	private QueueForTransactions<PC> queue;
	private QueueForTransactions<PC> f_queue;
	private QueueForTransactions<PC> p_queue;
	private double finishTime;
	private ChooseRandom rnd;
	public Fixer(String string, GUI gui, Model model) {
		// TODO Auto-generated constructor stub
		//setNameForProtocol(name);
		finishTime = gui.getChooseData_Modelling_Time().getDouble();
		rnd = gui.getChooseRandom_Fixing_Time();
		queue = model.getQueue();
		f_queue = model.getFQueue();
		p_queue = model.getPQueue();
	}
	
	@Override
	protected void rule() throws DispatcherFinishException {
		// TODO Auto-generated method stub
		System.out.println(this.getNameForProtocol()+"я запрацював");
	}

}
