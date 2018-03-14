package stat;

import widgets.Diagram;

public interface IHisto {
	Test getTest();

	/**
	 * Инициализировать гистограмму (очистить накопленные ранее
	 * значения, удалить диапазон).
	 */
	public abstract void init();

	
	/** @return среднее значение накопленных значений */
	public abstract double getAverage();


	/**
	 * Отобразить на диаграмме столбчатую диаграмму относительных частот (вертикаль)
	 * для значений (горизонталь).
	 * @param diagram диаграмма, на которой рисовать
	 */
	public abstract void showRelFrec(Diagram diagram);
	public void add(double value);
	public void addFrequencyForValue(double frequency, double value);

}