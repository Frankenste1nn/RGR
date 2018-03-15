package src;

import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import sun.nio.cs.Surrogate.Generator;

public class Model {
	//��������� �� ����������
		private Dispatcher dispatcher;
		//��������� �� �������� �������
		private maingui gui;
		
		////////������\\\\\\\\\
		// ��������� ����������
		private Generator generator;
		// ������������� ������
		private Device device;		
		//������� ������������� ��������
		private MultiActor multiDevice;
		
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
				multiDevice.setNameForProtocol("MultiActor ��� ������� ��������");
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
			// �������� ������ painter-�� ��� �������� ���������
			getQueue().setPainter(gui.getDiagramQueue().getPainter());
			//����������� ��������� ��������� ��������� �� �������
			if (gui.getJCheckBox().isSelected())
				dispatcher.setProtocolFileName("Console");
			else
				dispatcher.setProtocolFileName("");
		}


}
