package stat;

import widgets.Diagram;

public interface IHisto {
	Test getTest();

	/**
	 * ���������������� ����������� (�������� ����������� �����
	 * ��������, ������� ��������).
	 */
	public abstract void init();

	
	/** @return ������� �������� ����������� �������� */
	public abstract double getAverage();


	/**
	 * ���������� �� ��������� ���������� ��������� ������������� ������ (���������)
	 * ��� �������� (�����������).
	 * @param diagram ���������, �� ������� ��������
	 */
	public abstract void showRelFrec(Diagram diagram);
	public void add(double value);
	public void addFrequencyForValue(double frequency, double value);

}