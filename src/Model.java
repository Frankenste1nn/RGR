package src;

import process.Actor;
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
		private MultiActor multiChecker
		;
		//Packer
		private Packer packer;
		private Fixer fixer;
		
		/**
		 * @return the packer
		 */
		public Packer getPacker() {
			if(packer == null)
				packer = new Packer("Packer", gui, this);
			return packer;
		}
		/////////Черги\\\\\\\\\
		// Черга транзакцій
		private QueueForTransactions<PC> queue;
		private QueueForTransactions<PC> f_queue;
		private QueueForTransactions<PC> p_queue;
		
		/////////Гістограми\\\\\\\\\\\\
		// Гістограма для довжини черги
		private DiscretHisto discretHistoQueue;
		private DiscretHisto discretHistoFQueue;
		private DiscretHisto discretHistoPQueue;
		// Гістограма для часу перебування у черзі
		private Histo histoTransactionWaitInQueue;
		private Histo histoTransactionWaitInFQueue;
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
			// TODO добавить всех актеров и разобрать с мульти
			dispatcher.addStartingActor(getGenerator());
			dispatcher.addStartingActor(getMultiChecker());
			dispatcher.addStartingActor(getFixer());
			dispatcher.addStartingActor(getPacker());
		}

		private Actor getFixer() {
			if (fixer == null) {
				fixer = new Fixer("Fixer", gui, this);
			}
			return fixer;
		}
		public Generator getGenerator() {
			if (generator == null) {
				generator = new Generator("Generator", gui, this);
			}
			return generator;
		}
		public Checker getChecker() {
			if (checker == null) {
				checker = new Checker("Checker", gui, this);
				checker.setHistoForActorWaitingTime(getHistoWaitDevice());
			}
			return checker;
		}
		public Object getHistoWaitDevice() {
			
			return histoWaitDevice;
		}
		public MultiActor getMultiChecker() {
			if (multiChecker == null) { 	
				multiChecker = new MultiActor();
				multiChecker.setNameForProtocol("MultiActor для бригади пристроїв");
				multiChecker.setOriginal(getChecker());
				multiChecker.setNumberOfClones(gui.getChsdtTesterCount().getInt());
			}
			return multiChecker;
		}
		public QueueForTransactions<PC> getQueue() {
			if (queue == null) {
				queue = new QueueForTransactions<>("Queue", dispatcher,
						getDiscretHistoQueue());
			}
			return queue;
		}
	
		public QueueForTransactions<PC> getFQueue() {
			if (f_queue == null) {
				f_queue = new QueueForTransactions<>("Fixing Queue", dispatcher,
						getDiscretHistoFQueue());
			}
			return f_queue;
		}
		
		public QueueForTransactions<PC> getPQueue() {
			if (p_queue == null) {
				p_queue = new QueueForTransactions<>("Fixing Queue", dispatcher,
						getDiscretHistoPQueue());
			}
			return p_queue;
		}
		
		public DiscretHisto getDiscretHistoQueue() {
			if (discretHistoQueue == null) {
				discretHistoQueue = new DiscretHisto();
			}
			return discretHistoQueue;
		}
		
		public DiscretHisto getDiscretHistoFQueue() {
			if (discretHistoFQueue == null) {
				discretHistoFQueue = new DiscretHisto();
			}
			return discretHistoFQueue;
		}
		
		public DiscretHisto getDiscretHistoPQueue() {
			if (discretHistoPQueue == null) {
				discretHistoPQueue = new DiscretHisto();
			}
			return discretHistoPQueue;
		}
		
		public void initForTest() {
			// Передаємо чергам painter-ів для динамічної індикації
			getQueue().setPainter(gui.getDiagram_Testing_Order().getPainter());
			getFQueue().setPainter(gui.getDiagram_Fixing_Order().getPainter());
			getPQueue().setPainter(gui.getDiagram_Packing_Order().getPainter());
			
			//Налаштовуємо можливість виведення протоколу на консоль
			if (gui.getCheckBoxConcole().isSelected())
				dispatcher.setProtocolFileName("Console");
			else
				dispatcher.setProtocolFileName("");
		}


}
