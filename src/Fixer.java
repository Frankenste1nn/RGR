package src;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;

public class Fixer extends Actor {
	private QueueForTransactions<PC> queue;
	private QueueForTransactions<PC> f_queue;
	private QueueForTransactions<PC> p_queue;
	public Fixer(String string, GUI gui, Model model) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void rule() throws DispatcherFinishException {
		// TODO Auto-generated method stub
		System.out.println(this.getNameForProtocol()+"я запрацював");
	}

}
