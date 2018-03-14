package widgets.trans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import process.Actor;
import process.IModelFactory;
import widgets.Diagram;

/**
 * ������� ����� ������ ������������� ��� �������� � ������� ����������
 * ���������� ����������� ���������� ������� � �������������� ������ �
 * ���������� ������ � ������ ������. ������� ������� ������ ��������
 * ITransProcesable, ��������� ������� �������, ����������� ���������
 * IModelFactory. ��������� ������ ������� ���� �� ������ �������
 * initForTrans() � �������� � ��������� ������ ���������� � ������� ������
 * componentsToStartList(). ���������� ����������� ���������� �������
 * �������������� ����� ���������� interval ���������� �������. � ������ �������
 * ���������� ���������� ���������� � ������ ���������������� � ������� ������
 * resetAccum(). � ����� ���������� ������� interval, monitor, � ������� ������
 * getTransResult(), ���������� ��� ����������� ���������� ������. ����������
 * ������ �����������, ���������� ������� painter ��� ��������� �� ��������� �
 * ������������ � ������� intervalAverageAsocArray.
 * 
 * Creation date: (18.11.2007 17:26:58)
 * 
 * @author: biv
 */

public class TransMonitor extends Actor {

	public TransMonitor() {
		super();
	}

	private double interval; // �������� ���������� ���������� � ��������

	private int nIntervals; // ����� ���������� ����������

	private int nParallel; // ����� ����������� ���������� �������

	private Diagram diagram; // ������ ��� ��������� ����������� �����������

	private JComboBox<String> comboBox;

	private IModelFactory factory = null; // �������, ��������� ������,

	// ����������� ���������
	// ITransProcesable

	private Map<String, Double>[] intervalAverageAsocArray; // ������ �����������
														// ��������

	// ����������� �����������

	// ������� �������� ��������

	public void rule() {
		// ����� ��� �������
		ITransProcesable[] modelArray = new ITransProcesable[nParallel];
		for (int i = 0; i < nParallel; i++) {
			// ��������� ����� � ������ �� �� ������
			modelArray[i] = (ITransProcesable) (getFactory()
					.createModel(getDispatcher()));
			modelArray[i].initForTrans(interval * nIntervals);
		}

		// �������� �� ���������� �������
		intervalAverageAsocArray = new HashMap[nIntervals];
		
		// ������ ������ ��� ���������
		Map<String, ITransMonitoring> resMap = modelArray[0].getMonitoringObjects();
		String[] keyStrings = (String[]) resMap.keySet().toArray(
				new String[resMap.keySet().size()]);
		comboBox.setModel(new DefaultComboBoxModel<String>(keyStrings));
		
		// ���� �� ���������� ����������
		for (int i = 0; i < nIntervals; i++) {
			// ����������� ������������� ����������
			for (int j = 0; j < nParallel; j++)
				for(ITransMonitoring q : modelArray[j].getMonitoringObjects().values()){
					q.resetAccum();
				};			
			// �������� �� ������� ���������
			this.holdForTime(interval);
			
			// ����������� ����� ������� ������� ��� ���������
			Map<String, Double> averageMap = new HashMap<>();
			for (String s : keyStrings)
				averageMap.put(s, 0.0);		
			
			// ����������� �����
			for (int j = 0; j < nParallel; j++) {
				for(Entry<String, ITransMonitoring> kv : modelArray[j].getMonitoringObjects().entrySet()){
					double result = kv.getValue().getAccumAverage();
					double sum = averageMap.get(kv.getKey());
					sum += result;
					averageMap.put(kv.getKey(), sum);
				};	
			}
			
			// �����������  �����
			for (String s : keyStrings) {
				double sum = averageMap.get(s);
				sum /= nParallel;
				averageMap.put(s, sum);
			}
			// ����� �� ��������� ���������� �����������
			intervalAverageAsocArray[i] = averageMap;
			if (diagram != null) {
				diagram.getPainter().drawOvalAtXY(
						(float) (interval * (i + 0.5)),
						averageMap.get(keyStrings[0]).floatValue(), 3, 3);
				diagram.getPainter().drawOvalAtXY(
						(float) (interval * (i + 0.5)),
						averageMap.get(keyStrings[0]).floatValue(), 2, 2);
			}
		}
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public double getInterval() {
		return interval;
	}

	public int getNIntervals() {
		return nIntervals;
	}

	public int getNParallel() {
		return nParallel;
	}

	public IModelFactory getFactory() {
		if (factory == null)
			System.out.println("�� ��������� ������� �������");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}

	public void setNIntervals(int intervals) {
		nIntervals = intervals;
	}

	public void setNParallel(int parallel) {
		nParallel = parallel;
	}

	public Map<String, Double>[] getIntervalAverageAsocArray() {
		return intervalAverageAsocArray;
	}

	public double[] getAverageArray() {
		double[] array = new double[nIntervals];
		String key = (String) comboBox.getSelectedItem();
		for (int i = 0; i < array.length; i++) {
			Map<String, Double> map = intervalAverageAsocArray[i];
			array[i] = map.get(key);
		}
		return array;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

}
