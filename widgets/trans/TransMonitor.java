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
 * Объекты этого класса предназначены для создания и запуска требуемого
 * количества параллельно работающих моделей и периодического снятия и
 * усреднения данных о работе модели. Монитор создает массив объектов
 * ITransProcesable, используя фабрику моделей, реализующую интерфейс
 * IModelFactory. Созданные модели монитор готує до старту методом
 * initForTrans() и передает в стартовый список диспетчера с помощью метода
 * componentsToStartList(). Мониторинг параллельно работающих моделей
 * осуществляется через промежутки interval модельного времени. В начале каждого
 * промежутка накопители информации в модели инициализируются с помощью метода
 * resetAccum(). В конце промежутка времени interval, monitor, с помощью метода
 * getTransResult(), опрашивает все параллельно работающие модели. Результаты
 * опроса усредняются, передаются объекту painter для отрисовки на диаграмме и
 * запомонаются в массиве intervalAverageAsocArray.
 * 
 * Creation date: (18.11.2007 17:26:58)
 * 
 * @author: biv
 */

public class TransMonitor extends Actor {

	public TransMonitor() {
		super();
	}

	private double interval; // Интервал усреднения информации о процессе

	private int nIntervals; // Число интервалов усреднения

	private int nParallel; // Число параллельно работающих моделей

	private Diagram diagram; // Объект для отрисовки результатов мониторинга

	private JComboBox<String> comboBox;

	private IModelFactory factory = null; // Фабрика, создающая модели,

	// реализующие интерфейс
	// ITransProcesable

	private Map<String, Double>[] intervalAverageAsocArray; // Массив усредненных
														// значений

	// результатов мониторинга

	// Правила действия монитора

	public void rule() {
		// Масив для моделей
		ITransProcesable[] modelArray = new ITransProcesable[nParallel];
		for (int i = 0; i < nParallel; i++) {
			// Створюємо моделі і готуємо їх до старту
			modelArray[i] = (ITransProcesable) (getFactory()
					.createModel(getDispatcher()));
			modelArray[i].initForTrans(interval * nIntervals);
		}

		// Готуємося до моніторингу моделей
		intervalAverageAsocArray = new HashMap[nIntervals];
		
		// Готуємо модель для комбобокс
		Map<String, ITransMonitoring> resMap = modelArray[0].getMonitoringObjects();
		String[] keyStrings = (String[]) resMap.keySet().toArray(
				new String[resMap.keySet().size()]);
		comboBox.setModel(new DefaultComboBoxModel<String>(keyStrings));
		
		// Цикл по інтервалам усреднення
		for (int i = 0; i < nIntervals; i++) {
			// Ініціалізація накопичувачів інформації
			for (int j = 0; j < nParallel; j++)
				for(ITransMonitoring q : modelArray[j].getMonitoringObjects().values()){
					q.resetAccum();
				};			
			// Затримка на довжину інтервалу
			this.holdForTime(interval);
			
			// Ініціалізація карти середніх значень для інтервалу
			Map<String, Double> averageMap = new HashMap<>();
			for (String s : keyStrings)
				averageMap.put(s, 0.0);		
			
			// Накопичення даних
			for (int j = 0; j < nParallel; j++) {
				for(Entry<String, ITransMonitoring> kv : modelArray[j].getMonitoringObjects().entrySet()){
					double result = kv.getValue().getAccumAverage();
					double sum = averageMap.get(kv.getKey());
					sum += result;
					averageMap.put(kv.getKey(), sum);
				};	
			}
			
			// Усереднення  даних
			for (String s : keyStrings) {
				double sum = averageMap.get(s);
				sum /= nParallel;
				averageMap.put(s, sum);
			}
			// Запис та індикація результату усереднення
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
			System.out.println("Не визначена фабрика моделей");
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
