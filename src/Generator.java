package src;

import process.Actor;
import process.DispatcherFinishException;
import rnd.Negexp;
import rnd.Norm;
import widgets.ChooseRandom;

public class Generator extends Actor{
	ChooseRandom rnd;
	double finishTime;
	Model model;
	GUI gui;
	public Generator(String string, GUI gui, Model model) {
		setNameForProtocol(string);
		this.model = model;
		finishTime = gui.getChooseData_Modelling_Time().getDouble();
		rnd = gui.getChooseRandom_PC_Creation_time();
		this.gui=gui;
	}

	@Override
	protected void rule() throws DispatcherFinishException {
		while(getDispatcher().getCurrentTime() <= finishTime) {
			holdForTime(rnd.next());
			getDispatcher().printToProtocol(" " + getNameForProtocol() + 
					" created transaction.");
			ChooseRandom d = new ChooseRandom();
			
			double n = Math.random();
			System.out.println("Pc fail chance "+(n));
			PC tr = new PC(!(n > gui.getChooseData_fail_chance().getDouble()), model);
			dispatcher.addStartingActor(tr);
		}
	}

}
