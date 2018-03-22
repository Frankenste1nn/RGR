package src;

import process.Actor;
import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import widgets.ChooseData;

public class Model {
	//��������� �� ����������
		private Dispatcher dispatcher;
		//��������� �� �������� �������
		private GUI gui;
		
		////////������\\\\\\\\\
		// ��������� ����������
		private Generator generator;
		// Checker
		private Checker checker;		
		//������� ������������� ��������
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
		/////////�����\\\\\\\\\
		// ����� ����������
		private QueueForTransactions<PC> queue;
		
		/////////ó��������\\\\\\\\\\\\
		// ó�������� ��� ������� �����
		private DiscretHisto discretHistoQueue;
		// ó�������� ��� ���� ����������� � ����
		private Histo histoTransactionWaitInQueue;
		// ó�������� ��� ���� ��������������
		private Histo histoTransactionServiceTime;
		// ó�������� ��� ���� ������� Device
		private Histo histoWaitDevice;
		public Model(Dispatcher d, GUI g) {
			if (d == null || g == null) {
				System.out.println("�� ��������� ���������� ��� GUI ��� Model");
				System.out.println("�������� ������ ���������");
				System.exit(0);
			}
			dispatcher = d;
			gui = g;
			//�������� ������ �� ���������� ������ ����������
			componentsToStartList();
		}
		public void componentsToStartList() {
			// �������� ������ ����������
			// TODO �������� ���� ������� � ��������� � ������
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
		private Object getHistoWaitDevice() {
			
			return histoWaitDevice;
		}
		public MultiActor getMultiChecker() {
			if (multiChecker == null) { 	
				multiChecker = new MultiActor();
				multiChecker.setNameForProtocol("MultiActor ��� ������� ��������");
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
		public DiscretHisto getDiscretHistoQueue() {
			if (discretHistoQueue == null) {
				discretHistoQueue = new DiscretHisto();
			}
			return discretHistoQueue;
		}
		public void initForTest() {
			// �������� ������ painter-�� ��� �������� ���������
			getQueue().setPainter(gui.getDiagram_Testing_Order().getPainter());
			getQueue().setPainter(gui.getDiagram_Fixing_Order().getPainter());
			getQueue().setPainter(gui.getDiagram_Packing_Order().getPainter());
			//����������� ��������� ��������� ��������� �� �������
			if (gui.getCheckBoxConcole().isSelected())
				dispatcher.setProtocolFileName("Console");
			else
				dispatcher.setProtocolFileName("");
		}


}
