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
	 * Метод start() вызывается "Диспетчером" при обработке списка startList. Он
	 * запускает поток выполнения правил действия "актера", описанных в
	 * подклассе в методе rule, вызывая метод run интерфейса Runnable. Запустив
	 * поток "актера", метод приостановит родительский поток, то есть
	 * "Диспетчер", до тех пор, пока "актер" не будет приостановлен
	 */
	final void start() {
		// Создаем и запускаем поток выполнения правил действия актера
		thread = new Thread(this, getNameForProtocol());
		thread.start();
	}

	/**
	 * Метод определяет правила действия актера. Должен быть переопределен в
	 * подклассе.
	 * 
	 * @throws DispatcherFinishException
	 */
	abstract protected void rule() throws DispatcherFinishException;

	/**
	 * Метод интерфейсу Runnable. Забезпечує виконання правил дії актора
	 * у окремому потоці
	 */
	public void run() {
		dispatcher.printToProtocol("  " + nameForProtocol + " стартує");
		try {
			rule();
		} catch (DispatcherFinishException e) {
			dispatcher.printToProtocol("  " + nameForProtocol 
				+ " не дочекався, бо диспетчер закінчив роботу.");
			return;
		} finally {
			dispatcher.printToProtocol("  " + nameForProtocol + " роботу завершив");

			// Переводимо індикатор у стан "Актора призупинено"
			// і таким чином повідомляємо "Диспетчера", що виконання
			// правил дії даного "актора" завершено,
			// що дає можливість "Диспетчеру" продовжити роботу
			suspendIndicator.setValue(true);
		}
	}

	/**
	 * Метод забезпечує затримку виконання правил дії "актора" на час holdTime
	 * (час тут віртуальний, модельний). Метод імітує якусь діяльність "актора",
	 * що пов'язана із витратами часу.
	 */
	protected final void holdForTime(double holdTime) {
		if (holdTime < 0) {
			System.out.println("Negative holdTime! It is imposible. There was not made holdForTime.");
			return;
		}
		// Затримка не має сенсу, якщо диспетчер закінчив роботу..
		if (!getDispatcher().getThread().isAlive()) {
			System.out.println("!getDispatcher().getThread().isAlive()");
			return;
		}
		// Обчислюємо час відновлення правил дії "актора"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// Заносимо посилання на "актора" 
		//у список акторів, що затримані на деякий час.
		getDispatcher().getTimingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " затримано до " + Double.toString(activateTime));
		// Переводимо індикатор у стан "Актора призупинено",
		// внаслідок чого "Диспетчер" може продовжити  работу. 
		suspendIndicator.setValue(true);
		// Призупиняємо потік виконання правил дії "актора"
		suspendIndicator.waitForValue(false);
		// Тут актор колись відновить виконання правил дії
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " активізувався");
	}

	protected final void holdForTimeOrWaitForCondition(double holdTime, BooleanSupplier c, String s) {
		waitForConditionOrHoldForTime(c, s, holdTime);
	}

	/**
	 * Метод обеспечивает задержку выполнения правил действия "актера" до
	 * выполнения условия, которое описано в виде ???метода testCondition(),
	 * интерфейса WaitCondition???. К этому методу мы получаем доступ через
	 * объект с, передаваемый в данный метод в виде параметра. Следует иметь в
	 * виду, что возобновление правил действия "актера", ждущего выполнения
	 * условия может произойти и в случае, когда "Диспетчер", перед завершением
	 * работы, "отпускает" все ждущие потоки. Поэтому после выхода их ожидания
	 * выполнения условия, целесообразно в правилах действия это условие
	 * проверить. Невыполнение условия свидетельствует о завершении процесса
	 * моделирования. Дальнейшее выполнение правил действия будет бессмысленно.
	 * 
	 * @throws Exception
	 */
	protected final void waitForCondition(BooleanSupplier c, String textForProtocol) throws DispatcherFinishException {
		// Якщо умова виконується, затримка не потрібна
		if (c.getAsBoolean())
			return;
		// Якщо диспетчер закінчив роботу, затримка не має сенсу.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// Зберігаємо об’єкт, що містить умову.
		activateCondition = c;
		// Передаємо "актора" до списку акторів
		// що затримані до виконання умови
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " чекає '" + textForProtocol + "'");
		// Запам'ятовуємо час, коли почалося чекання
		double stopTime = dispatcher.getCurrentTime();
		// Переводимо індикатор у стан "Актора призупинено",
		// надаючи  "Диспетчеру" можливість працювати 
		suspendIndicator.setValue(true);
		// Переводимо потік виконання правил дії "актора"
		// у стан чекання.
		suspendIndicator.waitForValue(false);
		// Тут актор відновлює виконання правил дії
		if (waitingTimeHisto != null)
			// Обчислюємо і передаємо до гістограми час чекання
			waitingTimeHisto.add(dispatcher.getCurrentTime() - stopTime);
		if (activateCondition.getAsBoolean())
			getDispatcher().printToProtocol("  " + getNameForProtocol() + " дочекався '" + textForProtocol + "'");
		else
			throw new DispatcherFinishException();
	}

	/**
	 * Метод обеспечивает задержку правил действия "актера" до выполнения
	 * условия, которое описано в виде метода testCondition(), интерфейса
	 * WaitCondition. Но продолжительность ожидания не может быть более время
	 * holdTime holdTime (время здесь виртуальное, модельное).
	 */
	protected final void waitForConditionOrHoldForTime(BooleanSupplier c, String s, double holdTime) {
		// Задержка не имеет смысла, если диспетчер закончил работу.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// Если условие выполняется то задерка не нужна
		if (c.getAsBoolean())
			return;
		// Запомонаем объект, хранящий условие.
		activateCondition = c;
		// Вычисляем время возобновления правил действия "актера"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// Передаем "актера" "Диспетчеру", в список актеров,
		// задержанных на некоторое время
		getDispatcher().getTimingActorQueue().add(this);
		// Передаем "актера" "Диспетчеру", в список актеров,
		// задержанных до выполнения условия
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " чекає '" + s + "', але не пізніше ніж до "
				+ Double.toString(activateTime));

		// Устанавливаем индикатор в состояние "Актер приостановлен",
		// тем самым давая возможность работать "Диспетчеру".
		suspendIndicator.setValue(true);
		// Переводим поток выполнения правил действия "актера"
		// в состояние ожидания.
		// Когда придет время возобновить работу "актера"
		// диспетчер переключит индикатор в состояние false
		suspendIndicator.waitForValue(false);
		// Здесь актер возобновляет выполнение правил действия
		getDispatcher().printToProtocol("  " + getNameForProtocol() + " активізувався ");
	}

	/**
	 * Иногда "актеров" удобно размножать клонированием, но при этом индикатор
	 * должен быть у каждого свой.
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
	 * Метод используется "Диспетчером" при анализе списка "актереов", ждущих
	 * выполнения условия.
	 * 
	 * @return process.WaitCondition
	 */
	BooleanSupplier getActivateCondition() {
		return activateCondition;
	}

	/**
	 * Метод возвращает время, на которое было заплпнировано возобновление
	 * правил действия данного "актера" *
	 * 
	 * @return double
	 */
	public double getActivateTime() {
		return activateTime;
	}

	/**
	 * Метод обеспечивает доступ к имени "актера". Чаще всего нужен при выводе
	 * отладочной информации в протокол.
	 * 
	 * @return java.lang.String
	 */

	public String getNameForProtocol() {
		return nameForProtocol;
	}

	/**
	 * Метод обеспечивает доступ к индикатору состояния потока выполнения правил
	 * действия. Индикатор играет роль семафора, останавливающего и
	 * возобновляющего выполнение потока правил действия как "актера" так и
	 * "Диспетчера". Используется "Диспетчером".
	 * 
	 * @return process.BooleanSemaphore
	 */

	BooleanSemaphore getSuspendIndicator() {
		return suspendIndicator;
	}

	/**
	 * Устанавливает индикатор состояния потока выполнения правил действия.
	 * Индикатор играет роль семафора, останавливающего и возобновляющего
	 * выполнение потока правил действия как "актера" так и "Диспетчера".
	 * Используется при клонировании.
	 * 
	 * @param newBooleanIndicator
	 *            process.BooleanSemaphore
	 */
	// private void setSuspendIndicator(BooleanSemaphore newBooleanIndicator) {
	// suspendIndicator = newBooleanIndicator;
	// }
	/**
	 * Задание имени актера, которое будет использоваться при выводе протокола
	 * работы модели
	 * 
	 * @param newNameForProtocol
	 *            java.lang.String
	 */
	public void setNameForProtocol(java.lang.String newNameForProtocol) {
		nameForProtocol = newNameForProtocol;
	}

	/**
	 * При отладке помогает
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
