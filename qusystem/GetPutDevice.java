package qusystem;

import java.util.function.BooleanSupplier;

import process.DispatcherFinishException;
import process.QueueForTransactions;
import widgets.trans.ITransMonitoring;

public class GetPutDevice extends FinishDevice {
	protected QueueForTransactions outputQueue;

	public GetPutDevice() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 22:40:27)
	 * 
	 * @return qusystem.QueueForTransactions
	 */
	public ITransMonitoring getOutputQueue() {
		if (outputQueue == null)
			System.out.println("�� ��������� outputQueue ��� GetPutDevice .");

		return outputQueue;
	}

	public void rule() {
		
		BooleanSupplier hasPlace = () -> outputQueue.size() < outputQueue
				.getMaxSize();
		while (getDispatcher().getCurrentTime() <= finishTime) {
			try {
				waitForCondition(isTransaction,
						"���� � " + inputQueue.getNameForProtocol()
								+ " �������� ����������");
			} catch (DispatcherFinishException e) {
				return;
			}

			transaction = inputQueue.removeFirst();
			beforeHold();
			holdForTime(getRandom().next());
			if (getDispatcher().getCurrentTime() > finishTime)
				break;
			try {
				waitForCondition(hasPlace, "���� � " + inputQueue.getNameForProtocol()
								+ " �������� ���� ��� ����������");
			} catch (DispatcherFinishException e) {
				return;
			}

			outputQueue.addLast(transaction);
			afterHold();
		}
	}

	public void setOutputQueue(QueueForTransactions newOutputQueue) {
		outputQueue = newOutputQueue;
	}
}
