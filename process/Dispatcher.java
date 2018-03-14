package process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

public class Dispatcher implements Runnable {
	/**
	 * Признак работающего диспетчера
	 */
	private boolean starting = false;

	/**
	 * Ссылка на поток выполнения правил действия диспетчера
	 */
	private Thread thread;

	/**
	 * Значение текущего модельного времени Якщо -1, він ще не стартовав
	 */
	private double currentTime;

	/**
	 * Список "актеров", желающих выполнить свои правила действия
	 */
	private Deque<Actor> startList;

	/**
	 * Список "актеров", которые приостановили свои правила действия до
	 * некоторого момента времени
	 */
	private Queue<Actor> timingActorQueue;

	/**
	 * Список "актеров", которые приостановили свои правила действия до
	 * выполнения некоторого условия
	 */
	private List<Actor> waitingActorQueue;

	/**
	 * Имя файла для вывода отладочного протокола работы модели. Имя "Console"
	 * обеспечивает вывод протокола на консоль
	 */
	private String protocolFileName = "";

	/**
	 * Поток вывода протокола в файл
	 */
	private BufferedWriter out;

	/**
	 * Список для слушателей события dispatcherStart
	 */
	private Vector<DispatcherStartListener> dispatcherStartListeners;

	/**
	 * Список для слушателей события changeTime
	 */
	private Vector<ChangeTimeListener> changeTimeListeners;

	/**
	 * Список для слушателей события dispatcherFinish
	 */
	private Vector<DispatcherFinishListener> dispatcherFinishListeners;

	public Dispatcher() {
		super();
		startList = new ArrayDeque<Actor>();
	}

	/**
	 * Метод для добавления "актера" в список стартующих актеров. Это
	 * единственный способ для "актера" начать выполнение своих правил действия.
	 * Метод обеспечивает "актера" ссылкой на "Диспетчера"
	 * 
	 * @param a
	 */
	public void addStartingActor(Actor a) {
		a.setDispatcher(this);
		startList.addLast(a);
	}

	/**
	 * Инициализация "Диспетчера" и запуск потока выполнения его правил действия
	 * 
	 */
	public void start() {
		if (starting) {
			System.out
					.println("Спробу повторно запустити диспетчер проігноровано");
			return;
		}
		starting = true;
		currentTime = 0;
		timingActorQueue = new PriorityQueue<Actor>(
				(a1,a2)->Double.compare(a1.activateTime, a2.activateTime)
				);
		waitingActorQueue = new LinkedList<Actor>();
		if (protocolFileName != null && protocolFileName != "Console"
				&& protocolFileName.trim() != "") {
			// FileWriter file;
			try {
				out = new BufferedWriter(
						new FileWriter(protocolFileName, false));
			} catch (java.io.IOException e) {
				System.out.println("Не удалось открыть файл протокола");
			}
		}
		Thread thread = new Thread(this, "Dispatcher");
		this.thread = thread;
		thread.start();
	}

	/**
	 * Метод интерфейса Runnable. Правила действия "Диспетчера".
	 * 
	 */
	public void run() {
		Actor readyActor;
		printToProtocol("Модельний час " + Double.toString(currentTime));
		// Формирование события DispatcherStart
		fireDispatcherStartEvent();
		// Основной цикл выполнения правил действия
		while (true) {
			// Обработка списка стартующих актеров
			runStartList();
			// Обработка списка актеров, ждущих выполнения условия
			// readyActor - это актер, для которого выполнилось условие
			// Если readyActor == null, то условие не выполнилось
			// ни для одного из актеров
			readyActor = testWaitingQueue();
			if (readyActor == null) {
				// Цикл выполнения правил действия "Диспетчера" заканчивается,
				// если условие не выполнилось ни для одного из актеров
				// и если список актеров задержанных на некоторое время пуст
				if (timingActorQueue.isEmpty()) {
					break;
				}
				// Если условие не выполнилось ни для одного из актеров
				// выбираем из списка актеров задержанных на некоторое время,
				// актера с наименьшим временем активации
				readyActor = timingActorQueue.remove();
				// Возможно readyActor еще и ждал чего то
				waitingActorQueue.remove(readyActor);
				// Меняем текущее время в соответствии со временем активации
				// актера
				setCurrentTime(readyActor.getActivateTime());
			}
			// Меняем состояние индикатора объекта readyActor
			// тем самым давая возможность его потоку продолжить работу
			readyActor.getSuspendIndicator().setValue(false);
			// С помощью того же самого индикатора останавливаем поток
			// "Диспетчера"
			readyActor.getSuspendIndicator().waitForValue(true);
		}
		// Диспечер завершает работу
		if (waitingActorQueue.size() > 0) {
			// Освобождаются потоки, ждущие выполнения условий
			releaseWaitingQueue();
		}
		printToProtocol("  Диспетчер роботу завершив");
		// Формирование события DispatcherFinish
		fireDispatcherFinishEvent();
		starting = false;
		if (out != null)
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	protected void finalize() throws Throwable {
		out.close();
		super.finalize();
	}

	/**
	 * Обработка списка стартующих актеров Метод запускает потоки выполнения
	 * правил действия актеров. После старта каждого "актера" выполнение этого
	 * метода приостанавливается, и возобновляется только после того, как поток
	 * стартовавшего "актерa" приостановится.
	 */
	private void runStartList() {
		while (!startList.isEmpty()) {
			Actor a = startList.removeFirst();
			a.getSuspendIndicator().setValue(false);
			a.start();
			a.getSuspendIndicator().waitForValue(true);
		}
	}

	/**
	 * Обработка списка актеров, ждущих выполнения условий
	 * 
	 * @return process.Actor если его условие выполнено
	 * @return null если условие ни для кого не выполнено
	 */
	private Actor testWaitingQueue() {
		Iterator<Actor> i = waitingActorQueue.iterator();
		while (i.hasNext()) {
			Actor a = i.next();
			if (a.getActivateCondition().getAsBoolean()) {
				waitingActorQueue.remove(a);
				timingActorQueue.remove(a);
				return a;
			}
		}
		return null;
	}

	/**
	 * Установка нового значения текущего времени и формирование события
	 * ChangeTime
	 * 
	 * @param newCurrentTime
	 */
	private void setCurrentTime(double newCurrentTime) {
		currentTime = newCurrentTime;
		fireChangeTimeEvent();
		printToProtocol("Модельний час " + Double.toString(currentTime));
	}

	/**
	 * Метод, освобождающий список актеров ждущих выполнения условия
	 */
	private void releaseWaitingQueue() {
		printToProtocol("  Диспетчер звільняє потоки, що чекали виконання умови");
		while (waitingActorQueue.size() > 0) {
			Actor a = waitingActorQueue.remove(0);
			a.getSuspendIndicator().setValue(false);
			a.getSuspendIndicator().waitForValue(true);
		}
	}

	/**
	 * Задание имени файла для вывода отладочного протокола работы модели. Имя
	 * "Console" обеспечивает вывод протокола на консоль
	 * 
	 * @param newProtocolFileName
	 */
	public void setProtocolFileName(java.lang.String newProtocolFileName) {
		protocolFileName = newProtocolFileName;
	}

	/**
	 * Метод вывода в протокол строки отладочной информации
	 * 
	 * @param newString
	 */
	public void printToProtocol(String newString) {
		if (protocolFileName == null || protocolFileName.trim().length() == 0) {
			return;
		}
		if (protocolFileName == "Console") {
			System.out.println(newString);
			return;
		}
		if (out != null) {
			try {
				out.write(newString);
				out.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// FileWriter file;
		// try {
		// file = new java.io.FileWriter(protocolFileName, true);
		// file.write(newString + "\n");
		// file.close();
		// } catch (java.io.IOException e) {
		// }
	}

	// ////////////////////////////////////////////////////////////////
	// Методы, обеспечивающие доступ к полям диспетчера

	/**
	 * Метод доступа к текущему модельному времени
	 * 
	 * @return double
	 */
	public double getCurrentTime() {
		return currentTime;
	}

	/**
	 * Доступ к потоку выполнения правил действия диспетчера
	 * 
	 * @return boolean
	 */
	public final Thread getThread() {
		return thread;
	}

	/**
	 * Доступ к упорядоченной очереди актеров, приостановленных на время
	 * 
	 * @return PriorityQueue
	 */
	public final Queue<Actor> getTimingActorQueue() {
		return timingActorQueue;
	}

	/**
	 * Доступ к очереди актеров, ждущих выполнения условия
	 * 
	 * @return Queue
	 */
	public final List<Actor> getWaitingActorQueue() {
		return waitingActorQueue;
	}

	// ////////////////////////////////////////////////////////////////
	// Методы, обеспечивающие формирование событий Диспетчера
	/**
	 * Add a ChangeTimeListener to the listener list. The listener is registered
	 * for all properties.
	 * 
	 * @param listener
	 *            TheChangeTimeListener to be added
	 */

	public synchronized void addChangeTimeListener(ChangeTimeListener listener) {
		if (changeTimeListeners == null) {
			changeTimeListeners = new java.util.Vector<ChangeTimeListener>();
		}
		changeTimeListeners.addElement(listener);
	}

	/**
	 * Add a DispatcherFinishListener to the listener list. The listener is
	 * registered for all properties.
	 * 
	 * @param listener
	 *            The DispatcherFinishListener to be added
	 */

	public synchronized void addDispatcherFinishListener(
			DispatcherFinishListener listener) {
		if (dispatcherFinishListeners == null) {
			dispatcherFinishListeners = new java.util.Vector<DispatcherFinishListener>();
		}
		dispatcherFinishListeners.addElement(listener);
	}

	/**
	 * Add a DispatcherStartListener to the listener list. The listener is
	 * registered for all properties.
	 * 
	 * @param listener
	 *            The DispatcherStartListener to be added
	 */

	public synchronized void addDispatcherStartListener(
			DispatcherStartListener listener) {
		if (dispatcherStartListeners == null) {
			dispatcherStartListeners = new java.util.Vector<DispatcherStartListener>();
		}
		dispatcherStartListeners.addElement(listener);
	}

	/**
	 * Fire an existing ChangeTimeEvent to any registered leafDeletedListeners.
	 * 
	 * @param evt
	 *            The ChangeTimeEvent object.
	 */
	private void fireChangeTimeEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (changeTimeListeners != null) {
				targets = (java.util.Vector) changeTimeListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				ChangeTimeListener target = (ChangeTimeListener) targets
						.elementAt(i);
				target.onChangeTime(new EventObject(Dispatcher.this));
			}
		}

	}

	/**
	 * Fire an existing DispatcherFinishEvent to any registered
	 * dispatcherFinishListeners.
	 * 
	 * @param evt
	 *            The DispatcherFinishEvent object.
	 */
	private void fireDispatcherFinishEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (dispatcherFinishListeners != null) {
				targets = (java.util.Vector) dispatcherFinishListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				DispatcherFinishListener target = (DispatcherFinishListener) targets
						.elementAt(i);
				target.onDispatcherFinish();
			}
		}

	}

	/**
	 * Fire an existing DispatcherStartEvent to any registered
	 * dispatcherStartListeners.
	 * 
	 * @param evt
	 *            The DispatcherStartEvent object.
	 */
	private void fireDispatcherStartEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (dispatcherStartListeners != null) {
				targets = (java.util.Vector) dispatcherStartListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				DispatcherStartListener target = (DispatcherStartListener) targets
						.elementAt(i);
				target.onDispatcherStart();
			}
		}

	}

	/**
	 * Remove a ChangeTimeListener from the listener list. This removes a
	 * ChangeTimeListener that was registered for all properties.
	 * 
	 * @param listener
	 *            The ChangeTimeListener to be removed
	 */

	public synchronized void removeChangeTimeListener(
			ChangeTimeListener listener) {
		if (changeTimeListeners == null) {
			return;
		}
		changeTimeListeners.removeElement(listener);
	}

	/**
	 * Remove a DispatcherFinishListener from the listener list. This removes a
	 * DispatcherFinishListener that was registered.
	 * 
	 * @param listener
	 *            The DispatcherFinishListener to be removed
	 */

	public synchronized void removeDispatcherFinishListener(
			DispatcherFinishListener listener) {
		if (dispatcherFinishListeners == null) {
			return;
		}
		dispatcherFinishListeners.removeElement(listener);
	}

	/**
	 * Remove a DispatcherStartListener from the listener list. This removes a
	 * DispatcherStartListener that was registered.
	 * 
	 * @param listener
	 *            The DispatcherStartListener to be removed
	 */

	public synchronized void removeDispatcherStartListener(
			DispatcherStartListener listener) {
		if (dispatcherStartListeners == null) {
			return;
		}
		dispatcherStartListeners.removeElement(listener);
	}

}
