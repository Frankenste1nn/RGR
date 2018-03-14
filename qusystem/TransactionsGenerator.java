package qusystem;

import process.Actor;
import process.QueueForTransactions;
import rnd.Randomable;

public class TransactionsGenerator extends Actor  {
	protected QueueForTransactions outputQueue;

	protected double finishTime = 0;
	private Randomable random;

	protected Randomable getRandom() {
		if(random==null)
			System.out.println("Не визначено random для TransactionsGenerator.");
		return random;
	}

	public void setRandom(Randomable random) {
		this.random = random;
	}

	public TransactionsGenerator() {
		super();
	}

	/**
	 * Определяет условие продолжения работы генератора заявок. Creation date:
	 * (14.05.2005 11:46:03)
	 * 
	 * @return boolean
	 */
	protected boolean continueCondition() {
		return getDispatcher().getCurrentTime() <= finishTime;
	}

	protected void createTransaction() {
		getOutputQueue().addLast(new Object());

	}

	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 11:43:20)
	 * 
	 * @return double
	 */
	public double getFinishTime() {
		return finishTime;
	}

	public QueueForTransactions getOutputQueue() {
		if(outputQueue==null)
			System.out.println("Не визначено outputQueue для TransactionsGenerator.");
		return outputQueue;
	}

	public void rule() {
		while (true) {
			holdForTime(getRandom().next());
			if (!continueCondition()) {
				break;
			}
			getDispatcher().printToProtocol(
					"  " + getNameForProtocol() + " створює транзакцію.");
			createTransaction();		
		}
	}

	/**
	 * Insert the method's description here. Creation date: (14.05.2005
	 * 11:43:20)
	 * 
	 * @param newFinishTime
	 *            double
	 */
	public void setFinishTime(double newFinishTime) {
		finishTime = newFinishTime;
	}

	public void setOutputQueue(QueueForTransactions newOutputQueue) {
		outputQueue = newOutputQueue;
	}
}
