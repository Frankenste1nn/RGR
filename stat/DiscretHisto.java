/**
 * 
 */
package stat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import widgets.Diagram;

/**
 * Сначала термины, которые будут часто употребляться при описании. Их два.
 * Значение (value) - некая величина, которая меняется в каком-либо процессе.
 * Например, длина очереди.
 * Частота (frequency) - сколько раз появлялось значение в процессе, или сколько
 * времени оно оставалось постоянным. Для длины очереди, например, частотой
 * будет время, на протяжении которого длина очереди оставалась постоянной.
 * Клас позволяет анализировать значения и частоты. Сначала нужно накопить
 * некоторое количество данных методом addFrequencyForValue. Например, следим 
 * за очередью. Как только длина меняется, добавляем: значение - предыдущая
 * длина, частота - сколько времени очередь оставалась неизменной. После
 * накопления нужного количества данных, можем построить диаграмму методом    
 * showRelFrec, которая отобразит относительную частоту для каждого значения,
 * например для всех длин очереди будет видно сколько времени очередь была
 * такой длины. Либо методом toString получим в текстовом виде ту же информацию
 * плюс среднее значение (средняя длина очереди).
 * @author UlanYar
 */
public class DiscretHisto implements IHisto {
	private int count;
	
	/** Здесь будет накапливаться сума значений, умноженных на частоты. */
	private double totalIntegral = 0;
	
	/** Здесь будет накапливаться сума всех частот. */
	private double totalInterval = 0;
	
	/** Проверять ли поступающие значения на вхождение в границы диапазона */
	private boolean trim = false;
	
	/** Начало диапазона */
	private int from;
	
	/** Конец диапазона */
	private int to;
	
	/** Максимальное количество накапливаемых значений */
	private int maxcnt = DEFAULT_MAX_CNT; 
	
	/** Значение по умолчанию для максимального количества накапливаемых значений */
	public static final int DEFAULT_MAX_CNT = 20;
	
	/** 
	 * SortedMap, в которой значения отображаются на абсолютные частоты.
	 * Здесь будут накапливаться частоты для значений. 
	 */
	private SortedMap<Double, Double> map = new TreeMap<Double, Double>();
	
	/** @return массив абсолютных частот для значений, накопленных ранее */
	public double [] frequencies() {
		double [] frequencies = new double[map.size()];
		int i = 0;
		for (Double d: map.values())
			frequencies[i++] = d;
		return frequencies;
	}	
	
	/**
	 * Обработать накопленные значения
	 * (оставить наиболее значимый дипазон).
	 */
	private void normalize() {
		if (map.size() > maxcnt) {
			double [] values = values();
			double [] frequencies = frequencies();
			
			double minv = values[0];
			double maxv = values[maxcnt - 1];
			double maxfreq = 0;
			for (int i = 0; i < maxcnt; maxfreq += frequencies[i++]);
			double prevfreq = maxfreq;
			
			// по всех дипазонах начиная со второго.
			for (int i = 1; i <= map.size() - maxcnt; i++) {
				prevfreq -= frequencies[i - 1];
				prevfreq += frequencies[i + maxcnt - 1];
				if (prevfreq > maxfreq) {
					maxfreq = prevfreq;
					minv = values[i];
					maxv = values[i + maxcnt - 1];
				}
			}
			
			// частоты, соответствующие значениям, выходящим за границы диапазона,
			// добавляем к крайним значениям диапазона.
			double minfs = 0;
			double maxfs = 0;
			for (double value: map.keySet()) {
				if (value < minv) minfs += map.get(value);
				if (value > maxv) maxfs += map.get(value);
			}
			
			List<Double> todelete = new ArrayList<Double>();
			for (double value: map.keySet()) {
				if (value < minv) todelete.add(value);
				if (value > maxv) todelete.add(value);
			}
			for (Double d: todelete)
				map.remove(d);
			map.put(minv, map.get(minv) + minfs);
			map.put(maxv, map.get(maxv) + maxfs);
		}
	}

	/* (non-Javadoc)
	 * @see stat.IHisto#init()
	 */
	@Override
	public void init() {
		count=0;
		map.clear();
		trim = false;
		totalIntegral = 0;
		totalInterval = 0;
	}
	
	/**
	 * Инициализировать гистограмму (очистить накопленные ранее
	 * значения, удалить диапазон).
	 * @param maxcnt максимальное количество накапливаемых значений
	 */
	public void init(int maxcnt) {
		this.maxcnt = maxcnt;
		init();
	}
	
	/**
	 * Обрабатывать значения в диапазоне от b1 до b2
	 * @param b1 начало диапазона
	 * @param b2 конец диапазона
	 */
	public void initFromTo(int b1, int b2) {
		map.clear();
		trim = true;
		from = b1;
		to = b2;
		
		// из-за отсутствия этих двух строк была ошибка.
		totalIntegral = 0;
		totalInterval = 0;
	}
	
	
	public double [] relfrequencies() {
		normalize();
		double [] frequencies = frequencies();
		for (int i = 0, j = frequencies.length; i < j; i++)
			frequencies[i] /= totalInterval;
		return frequencies;
	}
	
	/** @return массив значений, накопленных ранее */
	public double [] values() {
		double [] values = new double[map.size()];
		int i = 0;
		for (Double d: map.keySet())
			values[i++] = d;
		return values;
	}
	public void add(double value){
		addFrequencyForValue(1.0,value);
	}
	/**
	 * Добавить частоту для значения.
	 * @param frequency частота
	 * @param value значение
	 */
	public void addFrequencyForValue(double frequency, double value) {
		count++;
		totalIntegral += (value * frequency);
		totalInterval += frequency;
		if (trim) {
			if (value < from) value = from;
			if (value > to) value = to;
		}
		map.put(value, new Double((map.containsKey(value) ? map.get(value) : 0) + frequency));
	}
	
	/* (non-Javadoc)
	 * @see stat.IHisto#getAverage()
	 */
	@Override
	public double getAverage() {
		return totalIntegral / totalInterval;
	}
	
	/**
	 * Отобразить на диаграмме столбчатую диаграмму относительных частот (вертикаль)
	 * для значений (горизонталь).
	 * @param diagram диаграмма, на которой рисовать
	 * @param color цвет столбцов
	 * @param width ширина иглы в в долях ширины интервала
	 * @param reset требуется ли изменение настройки диаграммы
	 */
	public void showRelFrec(Diagram diagram, Color color, double width, boolean reset) {
		normalize();
		diagram.setPainterColor(color);
		diagram.drawNeedleDiagram(values(), relfrequencies(), width, reset);
	}

	/* (non-Javadoc)
	 * @see stat.IHisto#showRelFrec(widgets.Diagram)
	 */
	@Override
	public void showRelFrec(widgets.Diagram diagram) {
		showRelFrec(diagram, Color.magenta, 0.2, true);
	}
	
	/* (non-Javadoc)
	 * @see stat.IHisto#toString()
	 */
	@Override

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Результати обробки:\n");
		buffer.append("Кількість доданків = " + count + "\n");
		buffer.append("Середнє значення = " + getAverage() + "\n");
		buffer.append("Значення  Частота\n");
		for (Double value: map.keySet())
			buffer.append(StatTables.format(value, 8, 1) + 
					StatTables.format(map.get(value) / totalInterval, 9, 4) + "\n");
		return buffer.toString();
	}

	@Override
	public Test getTest() {
		// TODO Auto-generated method stub
		return null;
	}
}
