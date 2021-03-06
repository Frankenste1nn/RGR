package src;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import process.Actor;
import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import widgets.experiments.IExperimentable;
import widgets.stat.IStatisticsable;
import widgets.trans.ITransMonitoring;
import widgets.trans.ITransProcesable;

public class Model implements IStatisticsable, IExperimentable, ITransProcesable{
	// ��������� �� ����������
	private Dispatcher dispatcher;
	// ��������� �� �������� �������
	private GUI gui;

	//////// ������\\\\\\\\\
	// ��������� ����������
	private Generator generator;
	// Checker
	private Checker checker;
	// ������� ������������� ��������
	private MultiActor multiChecker;
	private MultiActor multiPacker;
	// Packer
	private Packer packer;
	private Fixer fixer;

	/**
	 * @return the packer
	 */
	public Packer getPacker() {
		if (packer == null)
			packer = new Packer("Packer", gui, this);
		packer.setHistoForActorWaitingTime(getHistoTransactionWaitInPQueue());
		return packer;
	}

	///////// �����\\\\\\\\\
	// ����� ����������
	private QueueForTransactions<PC> queue;
	private QueueForTransactions<PC> f_queue;
	private QueueForTransactions<PC> p_queue;

	///////// ó��������\\\\\\\\\\\\
	// ó�������� ��� ������� �����
	private DiscretHisto discretHistoQueue;
	private DiscretHisto discretHistoFQueue;
	private DiscretHisto discretHistoPQueue;
	// ó�������� ��� ���� ����������� � ����
	private Histo histoTransactionWaitInQueue;
	private Histo histoTransactionWaitInPQueue;
	public Histo getHistoTransactionWaitInPQueue() {
		if(histoTransactionWaitInPQueue == null)
			histoTransactionWaitInPQueue = new Histo();
		return histoTransactionWaitInPQueue;
	}

	public Histo getHistoTransactionWaitInQueue() {
		if(histoTransactionWaitInQueue == null)
			histoTransactionWaitInQueue = new Histo();
		return histoTransactionWaitInQueue;
	}

	public Histo getHistoTransactionWaitInFQueue() {
		if(histoTransactionWaitInFQueue == null)
			histoTransactionWaitInFQueue = new Histo();
		return histoTransactionWaitInFQueue;
	}

	public Histo getHistoTransactionServiceTime() {
		if(histoTransactionServiceTime == null)
			histoTransactionServiceTime = new Histo();
		return histoTransactionServiceTime;
	}
	private Histo histoTransactionWaitInFQueue;
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
		// �������� ������ �� ���������� ������ ����������
		componentsToStartList();
	}

	public void componentsToStartList() {
		// �������� ������ ����������
		dispatcher.addStartingActor(getGenerator());
		dispatcher.addStartingActor(getMultiChecker());
		dispatcher.addStartingActor(getFixer());
		dispatcher.addStartingActor(getMultiPacker());
	}

	private Actor getFixer() {
		if (fixer == null) {
			fixer = new Fixer("Fixer", gui, this);
			fixer.setHistoForActorWaitingTime(getHistoTransactionWaitInFQueue());
		}
		return fixer;
	}

	public Generator getGenerator() {
		if (generator == null) {
			generator = new Generator("Generator", gui, this);
			//generator.setHistoForActorWaitingTime(waitingTimeHisto);
		}
		return generator;
	}

	public Checker getChecker() {
		if (checker == null) {
			checker = new Checker("Checker", gui, this);
			checker.setHistoForActorWaitingTime(getHistoTransactionWaitInQueue());
		}
		return checker;
	}

	public Object getHistoWaitDevice() {
		if(histoWaitDevice == null)
			histoWaitDevice = new Histo();
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
	
	public MultiActor getMultiPacker() {
		if (multiPacker == null) {
			multiPacker = new MultiActor();
			multiPacker.setNameForProtocol("MultiActor packer");
			multiPacker.setOriginal(getPacker());
			multiPacker.setNumberOfClones(gui.getChooseDataPackers().getInt());
		}
		
		return multiPacker;
 	}
	
	public QueueForTransactions<PC> getQueue() {
		if (queue == null) {
			queue = new QueueForTransactions<>("Queue", dispatcher, getDiscretHistoQueue());
		}
		return queue;
	}

	public QueueForTransactions<PC> getFQueue() {
		if (f_queue == null) {
			f_queue = new QueueForTransactions<>("Fixing Queue", dispatcher, getDiscretHistoFQueue());
		}
		return f_queue;
	}

	public QueueForTransactions<PC> getPQueue() {
		if (p_queue == null) {
			p_queue = new QueueForTransactions<>("Packing Queue", dispatcher, getDiscretHistoPQueue());
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
		// �������� ������ painter-�� ��� �������� ���������
		gui.getDiagram_Testing_Order().getPainter().setColor(Color.BLUE);
		gui.getDiagram_Fixing_Order().getPainter().setColor(Color.BLUE);
		gui.getDiagram_Packing_Order().getPainter().setColor(Color.BLUE);
		getQueue().setPainter(gui.getDiagram_Testing_Order().getPainter());
		getFQueue().setPainter(gui.getDiagram_Fixing_Order().getPainter());
		getPQueue().setPainter(gui.getDiagram_Packing_Order().getPainter());

		// ����������� ��������� ��������� ��������� �� �������
		if (gui.getCheckBoxConcole().isSelected())
			dispatcher.setProtocolFileName("Console");
		else
			dispatcher.setProtocolFileName("");
	}

	@Override
	public Map<String, IHisto> getStatistics() {
		//gui.getStatisticsManager().setFactory(gui.getFactory());
		Map<String, IHisto> map = new HashMap<>();
		map.put("Test queue", discretHistoQueue);
		map.put("Fix queue", discretHistoFQueue);
		map.put("Pack queue", discretHistoPQueue);
		map.put("Test waiting", histoTransactionWaitInQueue);
		map.put("Fix waiting", histoTransactionWaitInFQueue);
		map.put("Pack waiting", histoTransactionWaitInPQueue);
		map.put("Service", histoWaitDevice);
		return map;
	}

	@Override
	public void initForStatistics() {
		
	}

	@Override
	public void initForExperiment(double factor) {
		getMultiChecker().setNumberOfClones((int) factor);
		getMultiChecker().setHistoForActorWaitingTime(histoWaitDevice);
	}

	@Override
	public Map<String, Double> getResultOfExperiment() {
		Map<String, Double> map = new HashMap<>();
		map.put("Average queue size", discretHistoQueue.getAverage());
		map.put("Queue waiting", /*histoTransactionWaitInQueue.max() -*/ histoWaitDevice.getAverage());
		map.put("Checker waiting", histoWaitDevice.getAverage());
		return map;
	}

	@Override
	public void initForTrans(double finishTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, ITransMonitoring> getMonitoringObjects() {
		Map<String,  ITransMonitoring> map = new HashMap<>();
		map.put("Check queue", getQueue());
		System.out.println(getQueue().toString());
		map.put("Fix queue", getFQueue());
		System.out.println(getFQueue().toString());
		map.put("Pack queue", getPQueue());
		System.out.println(getPQueue().toString());
		return map;
	}

}	
