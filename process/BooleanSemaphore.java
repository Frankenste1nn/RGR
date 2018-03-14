package process;

public class BooleanSemaphore {
	private boolean value;

	public synchronized  void setValue(boolean newValue) {
		if (newValue != value) {
			value = newValue;
			notify();
		}
	}

	public synchronized void waitForValue(boolean state) {
		while (value != state) {
			try {
					this.wait();
			} catch (java.lang.InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
