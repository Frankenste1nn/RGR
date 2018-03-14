package qusystem;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;
import widgets.trans.ITransMonitoring;

public class FinishDevice extends Actor {
	protected Object transaction;

	protected QueueForTransactions inputQueue;

	protected double finishTime ;
	
	private Randomable random;
	
	
	protected BooleanSupplier isTransaction = () -> inputQueue.size() > 0;

	protected Randomable getRandom() {
		if(random==null)
			System.out.println("Не визначено random для FinishDevicer (GetPutDevice ).");
		return random;
	}

	public void setRandom(Randomable random) {
		this.random = random;
	}

	public FinishDevice() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (04.02.2006
	 * 18:43:02)
	 * 
	 * @return double
	 */
	public double getFinishTime() {
		return finishTime;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:21:15)
	 * 
	 * @return qusystem.QueueForTransactions
	 */
	public ITransMonitoring getInputQueue() {
		if(inputQueue==null)
			System.out.println("Не визначено inputQueue для FinishDevice (GetPutDevice ).");

		return inputQueue;
	}

	public Object getTransaction() {
		return transaction;
	}

	public void rule() {
		while (getDispatcher().getCurrentTime()<=finishTime) {
			try {
				waitForCondition(isTransaction,"поки у " + inputQueue.getNameForProtocol()
								+ " з'явиться транзакція");
				} catch (DispatcherFinishException e) {
				return;
			}
			if (getDispatcher().getCurrentTime()>finishTime) break;
			transaction = inputQueue.removeFirst();
			beforeHold();
			holdForTime(getRandom().next());
			afterHold();
		}
	}
	/**
	 * Метод вызывается перед задержкой потока на некоторое время. может быть
	 * доопределен в подклассах. В нем можно выполнить текущую обработку перед
	 * приостановкой актера
	 */
	protected void beforeHold() {
	}

	/**
	 * Метод вызывается после задержки потока на некоторое время. В нем можно
	 * выполнить текущую обработку данных перед возобновлении активной фазы
	 * "актера"
	 */
	protected void afterHold() {
	}

	
	/**
	 * Insert the method's description here. Creation date: (04.02.2006
	 * 18:43:02)
	 * 
	 * @param newFinishTime
	 *            double
	 */
	public void setFinishTime(double newFinishTime) {
		finishTime = newFinishTime;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:21:15)
	 * 
	 * @param newInputQueue
	 *            qusystem.QueueForTransactions
	 */
	public void setInputQueue(QueueForTransactions newInputQueue) {
		inputQueue = newInputQueue;
	}
	

}
