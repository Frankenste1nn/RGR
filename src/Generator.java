package src;

import process.Actor;
import process.DispatcherFinishException;
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
			d.setRandom(new Norm(2,0.4));
			//TODO SDELAT
			PC pc = new PC(true);
		}
	}

}
