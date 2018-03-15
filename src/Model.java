package src;

import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import widgets.ChooseData;

public class Model {
	//Посилання на диспетчера
		private Dispatcher dispatcher;
		//Посилання на візуальну частину
		private GUI gui;
		
		////////Актори\\\\\\\\\
		// Генератор транзакцій
		private Generator generator;
		// Checker
		private Checker checker;		
		//Бригада обслуговуючих пристроїв
		private MultiActor multiFixer;
		//Packer
		private Packer packer;
		
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
		public Checker getChecker() {
			if (checker == null) {
				checker = new Checker("Device", gui, this);
				checker.setHistoForActorWaitingTime(getHistoWaitDevice());
			}
			return checker;
		}
		private Object getHistoWaitDevice() {
			// TODO Auto-generated method stub
			return null;
		}
		public MultiActor getMultiDevice() {
			if (multiFixer == null) { 	
				multiFixer = new MultiActor();
				multiFixer.setNameForProtocol("MultiActor для бригади пристроїв");
				multiFixer.setOriginal(getChecker());
				multiFixer.setNumberOfClones(gui.getChsdtTesterCount().getInt());
			}
			return multiFixer;
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
			getQueue().setPainter(gui.getDiagram_Testing_Order().getPainter());
			getQueue().setPainter(gui.getDiagram_Fixing_Order().getPainter());
			getQueue().setPainter(gui.getDiagram_Packing_Order().getPainter());
			//Налаштовуємо можливість виведення протоколу на консоль
			if (gui.getCheckBoxConcole().isSelected())
				dispatcher.setProtocolFileName("Console");
			else
				dispatcher.setProtocolFileName("");
		}


}
