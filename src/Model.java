package src;

import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import sun.nio.cs.Surrogate.Generator;

public class Model {
	//Посилання на диспетчера
		private Dispatcher dispatcher;
		//Посилання на візуальну частину
		private GUI gui;
		
		////////Актори\\\\\\\\\
		// Генератор транзакцій
		private Generator generator;
		// Обслуговуючий прилад
		private Device device;		
		//Бригада обслуговуючих пристроїв
		private MultiActor multiDevice;
		
		/////////Черги\\\\\\\\\
		// Черга транзакцій
		private QueueForTransactions<Transaction> queue;
		
		/////////Гістограми\\\\\\\\\\\\
		// Гістограма для довжини черги
		private DiscretHisto discretHistoQueue;
		// Гістограма для часу перебування у черзі
		private Histo histoTransactionWaitInQueue;
		// Гістограма для часу обслуговування
		private Histo histoTransactionServiceTime;
		// Гістограма для часу чекання Device
		private Histo histoWaitDevice;
		public Model(Dispatcher d, GUI g) {
			if (d == null || g == null) {
				System.out.println("Не визначено диспетчера або GUI для Model");
				System.out.println("Подальша робота неможлива");
				System.exit(0);
			}
			dispatcher = d;
			gui = g;
			//Передаємо акторів до стартового списку диспетчера
			componentsToStartList();
		}
		public void componentsToStartList() {
			// Передаємо акторів диспетчеру
			dispatcher.addStartingActor(getGenerator());
			dispatcher.addStartingActor(getMultiDevice());
		}

		public Generator getGenerator() {
			if (generator == null) {
				generator = new Generator("Generator", gui, this);
			}
			return generator;
		}
		public Device getDevice() {
			if (device == null) {
				device = new Device("Device", gui, this);
				device.setHistoForActorWaitingTime(getHistoWaitDevice());
			}
			return device;
		}
		public MultiActor getMultiDevice() {
			if (multiDevice == null) { 	
				multiDevice = new MultiActor();
				multiDevice.setNameForProtocol("MultiActor для бригади пристроїв");
				multiDevice.setOriginal(getDevice());
				multiDevice.setNumberOfClones(gui.getChooseDataNdevice().getInt());
			}
			return multiDevice;
		}
		public QueueForTransactions<Transaction> getQueue() {
			if (queue == null) {
				queue = new QueueForTransactions<>("Queue", dispatcher,
						getDiscretHistoQueue());
			}
			return queue;
		}
		public DiscretHisto getDiscretHistoQueue() {
			if (discretHistoQueue == null) {
				discretHistoQueue = new DiscretHisto();
			}
			return discretHistoQueue;
		}
		public void initForTest() {
			// Передаємо чергам painter-ів для динамічної індикації
			getQueue().setPainter(gui.getDiagramQueue().getPainter());
			//Налаштовуємо можливість виведення протоколу на консоль
			if (gui.getJCheckBox().isSelected())
				dispatcher.setProtocolFileName("Console");
			else
				dispatcher.setProtocolFileName("");
		}


}
