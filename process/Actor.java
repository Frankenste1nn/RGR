package process;

import java.util.function.BooleanSupplier;

import stat.Histo;

public abstract class Actor implements Runnable, Cloneable {

	protected String nameForProtocol = this.getClass().getName();

	protected double activateTime;

	BooleanSupplier activateCondition;

	private Histo waitingTimeHisto;

	protected BooleanSemaphore suspendIndicator = new BooleanSemaphore();

	protected Thread thread = null;

	protected Dispatcher dispatcher;

	public Actor() {
		super();
	}

	/**
	 * ����� start() ���������� "�����������" ��� ��������� ������ startList. ��
	 * ��������� ����� ���������� ������ �������� "������", ��������� �
	 * ��������� � ������ rule, ������� ����� run ���������� Runnable. ��������
	 * ����� "������", ����� ������������ ������������ �����, �� ����
	 * "���������", �� ��� ���, ���� "�����" �� ����� �������������
	 */
	final void start() {
		// ������� � ��������� ����� ���������� ������ �������� ������
		thread = new Thread(this, getNameForProtocol());
		thread.start();
	}

	/**
	 * ����� ���������� ������� �������� ������. ������ ���� ������������� �
	 * ���������.
	 * 
	 * @throws DispatcherFinishException
	 */
	abstract protected void rule() throws DispatcherFinishException;

	/**
	 * ����� ���������� Runnable. ��������� ��������� ������ 䳿 ������
	 * � �������� ������
	 */
	public void run() {
		dispatcher.printToProtocol("  " + nameForProtocol + " ������");
		try {
			rule();
		} catch (DispatcherFinishException e) {
			dispatcher.printToProtocol("  " + nameForProtocol 
				+ " �� ���������, �� ��������� ������� ������.");
			return;
		} finally {
			dispatcher.printToProtocol("  " + nameForProtocol + " ������ ��������");

			// ���������� ��������� � ���� "������ �����������"
			// � ����� ����� ����������� "����������", �� ���������
			// ������ 䳿 ������ "������" ���������,
			// �� �� ��������� "����������" ���������� ������
			suspendIndicator.setValue(true);
		}
	}

	/**
	 * ����� ��������� �������� ��������� ������ 䳿 "������" �� ��� holdTime
	 * (��� ��� ����������, ���������). ����� ���� ����� �������� "������",
	 * �� ���'����� �� ��������� ����.
	 */
	protected final void holdForTime(double holdTime) {
		if (holdTime < 0) {
			System.out.println("Negative holdTime! It is imposible. There was not made holdForTime.");
			return;
		}
		// �������� �� �� �����, ���� ��������� ������� ������..
		if (!getDispatcher().getThread().isAlive()) {
			System.out.println("!getDispatcher().getThread().isAlive()");
			return;
		}
		// ���������� ��� ���������� ������ 䳿 "������"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// �������� ��������� �� "������" 
		//� ������ ������, �� �������� �� ������ ���.
		getDispatcher().getTimingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " ��������� �� " + Double.toString(activateTime));
		// ���������� ��������� � ���� "������ �����������",
		// �������� ���� "���������" ���� ����������  ������. 
		suspendIndicator.setValue(true);
		// ������������ ���� ��������� ������ 䳿 "������"
		suspendIndicator.waitForValue(false);
		// ��� ����� ������ �������� ��������� ������ 䳿
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " ������������");
	}

	protected final void holdForTimeOrWaitForCondition(double holdTime, BooleanSupplier c, String s) {
		waitForConditionOrHoldForTime(c, s, holdTime);
	}

	/**
	 * ����� ������������ �������� ���������� ������ �������� "������" ��
	 * ���������� �������, ������� ������� � ���� ???������ testCondition(),
	 * ���������� WaitCondition???. � ����� ������ �� �������� ������ �����
	 * ������ �, ������������ � ������ ����� � ���� ���������. ������� ����� �
	 * ����, ��� ������������� ������ �������� "������", ������� ����������
	 * ������� ����� ��������� � � ������, ����� "���������", ����� �����������
	 * ������, "���������" ��� ������ ������. ������� ����� ������ �� ��������
	 * ���������� �������, ������������� � �������� �������� ��� �������
	 * ���������. ������������ ������� ��������������� � ���������� ��������
	 * �������������. ���������� ���������� ������ �������� ����� ������������.
	 * 
	 * @throws Exception
	 */
	protected final void waitForCondition(BooleanSupplier c, String textForProtocol) throws DispatcherFinishException {
		// ���� ����� ����������, �������� �� �������
		if (c.getAsBoolean())
			return;
		// ���� ��������� ������� ������, �������� �� �� �����.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// �������� �ᒺ��, �� ������ �����.
		activateCondition = c;
		// �������� "������" �� ������ ������
		// �� �������� �� ��������� �����
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " ���� '" + textForProtocol + "'");
		// �����'������� ���, ���� �������� �������
		double stopTime = dispatcher.getCurrentTime();
		// ���������� ��������� � ���� "������ �����������",
		// �������  "����������" ��������� ��������� 
		suspendIndicator.setValue(true);
		// ���������� ���� ��������� ������ 䳿 "������"
		// � ���� �������.
		suspendIndicator.waitForValue(false);
		// ��� ����� �������� ��������� ������ 䳿
		if (waitingTimeHisto != null)
			// ���������� � �������� �� ��������� ��� �������
			waitingTimeHisto.add(dispatcher.getCurrentTime() - stopTime);
		if (activateCondition.getAsBoolean())
			getDispatcher().printToProtocol("  " + getNameForProtocol() + " ��������� '" + textForProtocol + "'");
		else
			throw new DispatcherFinishException();
	}

	/**
	 * ����� ������������ �������� ������ �������� "������" �� ����������
	 * �������, ������� ������� � ���� ������ testCondition(), ����������
	 * WaitCondition. �� ����������������� �������� �� ����� ���� ����� �����
	 * holdTime holdTime (����� ����� �����������, ���������).
	 */
	protected final void waitForConditionOrHoldForTime(BooleanSupplier c, String s, double holdTime) {
		// �������� �� ����� ������, ���� ��������� �������� ������.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// ���� ������� ����������� �� ������� �� �����
		if (c.getAsBoolean())
			return;
		// ���������� ������, �������� �������.
		activateCondition = c;
		// ��������� ����� ������������� ������ �������� "������"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ��������� �����
		getDispatcher().getTimingActorQueue().add(this);
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ���������� �������
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " ���� '" + s + "', ��� �� ����� �� �� "
				+ Double.toString(activateTime));

		// ������������� ��������� � ��������� "����� �������������",
		// ��� ����� ����� ����������� �������� "����������".
		suspendIndicator.setValue(true);
		// ��������� ����� ���������� ������ �������� "������"
		// � ��������� ��������.
		// ����� ������ ����� ����������� ������ "������"
		// ��������� ���������� ��������� � ��������� false
		suspendIndicator.waitForValue(false);
		// ����� ����� ������������ ���������� ������ ��������
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " ������������ ");
	}

	/**
	 * ������ "�������" ������ ���������� �������������, �� ��� ���� ���������
	 * ������ ���� � ������� ����.
	 */
	public Object clone() {
		try {
			Actor clon = (Actor) super.clone();
			clon.suspendIndicator = new BooleanSemaphore();
			return clon;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * ����� ������������ "�����������" ��� ������� ������ "��������", ������
	 * ���������� �������.
	 * 
	 * @return process.WaitCondition
	 */
	BooleanSupplier getActivateCondition() {
		return activateCondition;
	}

	/**
	 * ����� ���������� �����, �� ������� ���� ������������� �������������
	 * ������ �������� ������� "������" *
	 * 
	 * @return double
	 */
	public double getActivateTime() {
		return activateTime;
	}

	/**
	 * ����� ������������ ������ � ����� "������". ���� ����� ����� ��� ������
	 * ���������� ���������� � ��������.
	 * 
	 * @return java.lang.String
	 */

	public String getNameForProtocol() {
		return nameForProtocol;
	}

	/**
	 * ����� ������������ ������ � ���������� ��������� ������ ���������� ������
	 * ��������. ��������� ������ ���� ��������, ���������������� �
	 * ��������������� ���������� ������ ������ �������� ��� "������" ��� �
	 * "����������". ������������ "�����������".
	 * 
	 * @return process.BooleanSemaphore
	 */

	BooleanSemaphore getSuspendIndicator() {
		return suspendIndicator;
	}

	/**
	 * ������������� ��������� ��������� ������ ���������� ������ ��������.
	 * ��������� ������ ���� ��������, ���������������� � ���������������
	 * ���������� ������ ������ �������� ��� "������" ��� � "����������".
	 * ������������ ��� ������������.
	 * 
	 * @param newBooleanIndicator
	 *            process.BooleanSemaphore
	 */
	// private void setSuspendIndicator(BooleanSemaphore newBooleanIndicator) {
	// suspendIndicator = newBooleanIndicator;
	// }
	/**
	 * ������� ����� ������, ������� ����� �������������� ��� ������ ���������
	 * ������ ������
	 * 
	 * @param newNameForProtocol
	 *            java.lang.String
	 */
	public void setNameForProtocol(java.lang.String newNameForProtocol) {
		nameForProtocol = newNameForProtocol;
	}

	/**
	 * ��� ������� ��������
	 */
	public String toString() {
		return getNameForProtocol();
	}

	void setActivateCondition(BooleanSupplier activateCondition) {
		this.activateCondition = activateCondition;
	}

	void setActivateTime(double activateTime) {
		this.activateTime = activateTime;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public final void replaceActivateTimeBy(double newActivateTime) throws Exception {
		if (getDispatcher().getCurrentTime() > newActivateTime)
			throw new Exception("Time revers is impossible");
		if (!getDispatcher().getTimingActorQueue().contains(this))
			throw new Exception("Actor is not in  timingActorQueue");
		this.setActivateTime(newActivateTime);
	}

	public Thread getThread() {
		return thread;
	}

	void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;

	}

	public void terminateWaiting() {
		this.setActivateCondition(() -> true);
	}

	public Histo getWaitingTimeHisto() {
		return waitingTimeHisto;
	}

	public void setHistoForActorWaitingTime(Histo waitingTimeHisto) {
		this.waitingTimeHisto = waitingTimeHisto;
	}

}
