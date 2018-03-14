package process;

import java.util.EventListener;

public interface ITickEventListener extends EventListener{
	public void onTick(TickEvent e);
}
