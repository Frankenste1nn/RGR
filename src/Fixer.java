package src;

import process.Actor;
import process.DispatcherFinishException;

public class Fixer extends Actor {

	public Fixer(String string, GUI gui, Model model) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void rule() throws DispatcherFinishException {
		// TODO Auto-generated method stub
		System.out.println(this.getNameForProtocol()+"я запрацював");
	}

}
