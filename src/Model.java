package src;

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
		private MultiActor multiFixer;
		//Packer
		private Packer packer;
		
		/////////�����\\\\\\\\\
		// ����� ����������
		private QueueForTransactions<Transaction> queue;
		
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
				multiFixer.setNameForProtocol("MultiActor ��� ������� ��������");
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
