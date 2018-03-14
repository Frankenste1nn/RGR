package stat;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import widgets.Diagram;

/**
 * 
 * @author: Administrator
 */
public class Histo implements Cloneable, IHisto {
	private class Fv {
		private double f = 0;
		private double v = 0;

		Fv(double newF, double newV) {
			super();
			f = newF;
			v = newV;
		}
	}

	private double border[] = null;

	private double sumInterval[] = null;

	private Vector<Fv> tmpVector = new Vector<Fv>();

	private int count;

	private double total;

	private double total2;

	private double totalF;

	private double zMin;

	private double zMax;

	protected Test test;

	/**
	 * Histo constructor comment.
	 */
	public Histo() {
		super();
	}

	public Histo(double left, double right, int intervals) {
		this();
		initFromTo(left, right, intervals);
	}

	public double[] absolutFrequency() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return sumInterval;
	}

	/**
	 * Возвращает накопленные отностельные частоты. Creation date: (10.12.2004
	 * 16:20:53)
	 * 
	 * @return double[]
	 */
	public double[] accumFrequency() {
		{
			if (border == null) {
				fillHistoForVector(tmpVector);
			}
			double[] result = new double[sumInterval.length];
			double s = 0, a = 0;
			for (int i = 0; i < sumInterval.length; i++) {
				s += sumInterval[i];
			}
			for (int i = 0; i < result.length; i++) {
				a += sumInterval[i] / s;
				result[i] = a;
			}
			return result;
		}
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:21:50)
	 * 
	 * @param aNumber
	 *            double
	 */
	public void add(double aNumber) {
		if (border == null) {
			tmpVector.addElement(new Fv(1, aNumber));
			if (tmpVector.size() > 500) {
				fillHistoForVector(tmpVector);
			}
		} else
			baseAdd(new Fv(1, aNumber));
	}

	public void addFrequencyForValue(double f, double v) {
		if (border == null) {
			tmpVector.addElement(new Fv(f, v));
			if (tmpVector.size() > 500) {
				fillHistoForVector(tmpVector);
			}
		} else
			baseAdd(new Fv(f, v));
	}

	private void baseAdd(Fv fv) {

		if (fv.v < border[0]) {
			sumInterval[0] = sumInterval[0] + fv.f;
		} else {
			if (fv.v > border[border.length - 1]) {
				sumInterval[sumInterval.length - 1] = sumInterval[sumInterval.length - 1] + fv.f;
			} else {
				int n;
				for (n = 1; fv.v > border[n]; n++)
					;
				sumInterval[n] += fv.f;
			}
		}
		count++;
		totalF += fv.f;
		total += fv.v * fv.f;
		total2 += fv.v * fv.v * fv.f;
		if (fv.v < zMin)
			zMin = fv.v;
		if (fv.v > zMax)
			zMax = fv.v;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:22:45)
	 * 
	 * @return double
	 */
	public double average() {
		if (border == null) {
			if (tmpVector.size() == 0)
				return 0;
			fillHistoForVector(tmpVector);
		}
		return (total / totalF);
	}

	public double getAverage() {
		// if (tmpVector.size()==0) return 0;
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return (total / totalF);
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:23:29)
	 * 
	 * @return double[]
	 */
	public double[] getBorders() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return border;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:23:57)
	 * 
	 * @return int
	 */
	public int count() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}

		return count;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:24:56)
	 * 
	 * @param fileName
	 *            java.lang.String
	 */
	public void fillHistoForFile(String fileName) {
		try {
			FileReader fileInput = new FileReader(fileName);
			BufferedReader dataInStream = new BufferedReader(fileInput);
			String result;
			int cnt = 0;
			double xMax = 0;
			double xMin = 99999999;
			/*
			 * Определение минимального и максимального и подсчет строк в файле"
			 */
			while ((result = dataInStream.readLine()) != null) {
				double z = Double.parseDouble(result);
				if (xMax <= z)
					xMax = z;
				if (xMin >= z)
					xMin = z;
				cnt++;
			}
			/* Расчёт кол-ва интервалов */
			int n = (int) stat.StatTables.roundTo((1 + (1.5 * Math.log(cnt))), (double) 1);
			/* Корректировка кол-ва интервалов на ширину начального участка */
			if (xMax - xMin > 0)
				n = (int) stat.StatTables.roundTo(n * xMax / (xMax - xMin), (double) 1);
			/* Инициализация гистограмы */

			this.initFromTo((double) 0, (xMax + 0.00001), n);
			fileInput.close();
			dataInStream.close();
			fileInput = new FileReader(fileName);
			dataInStream = new LineNumberReader(fileInput);
			/* Наполнение гистограммы из файла */
			while ((result = dataInStream.readLine()) != null) {
				double z = Double.parseDouble(result);
				this.add(z);
			}
			dataInStream.close();
		} catch (FileNotFoundException fnf) {
			javax.swing.JOptionPane.showMessageDialog(null, "Файл" + fileName + "не найден", "Создание гистограммы",
					javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (IOException ioe) {
			javax.swing.JOptionPane.showMessageDialog(null, "Не могу прочитать строку в файле", "Создание гистограммы",
					javax.swing.JOptionPane.ERROR_MESSAGE);
		}

	}

	public void fillHistoForVector(Vector v) {
		Fv result;
		int cnt = 0;
		double xMax = -Double.MAX_VALUE;
		double xMin = Double.MAX_VALUE;
		/*
		 * Определение минимального и максимального и подсчет строк в векторе"
		 */
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			result = (Fv) iter.next();
			double z = result.v;
			if (xMax <= z)
				xMax = z;
			if (xMin >= z)
				xMin = z;
			cnt++;
		}
		/* Расчёт кол-ва интервалов */
		int n = (int) stat.StatTables.roundTo((1 + (1.5 * Math.log(cnt))), (double) 1);
		if (n < 1)
			n = 1;
		/* Корректировка кол-ва интервалов на ширину начального участка */
		// if (xMax-xMin>0) n = (int)
		// stat.StatTables.roundTo(n*xMax/(xMax-xMin),(double)1);
		/* Инициализация гистограмы */

		this.initFromTo((double) (xMin - 0.001), (xMax + 0.001), n);
		/* Наполнение гистограммы из файла */
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			result = (Fv) iter.next();
			this.baseAdd(result);
		}

	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:31:17)
	 * 
	 * @param left
	 *            double
	 * @param right
	 *            double
	 * @param intervals
	 *            int
	 */
	public void init() {
		border = null;
		tmpVector = new Vector<Fv>();
	}

	public void initFromTo(double left, double right, int intervals) {
		border = new double[intervals + 1];
		for (int i = 0; i < border.length; i++) {
			border[i] = ((right - left) * i) / intervals + left;
		}
		sumInterval = new double[intervals + 2];
		for (int i = 0; i < sumInterval.length; i++) {
			sumInterval[i] = 0;
		}
		total = 0;
		total2 = 0;
		count = 0;
		totalF = 0;
		zMax = -999999999;
		zMin = 999999999;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:22:45)
	 * 
	 * @return double
	 */
	public double kErlang() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return (average() / sigma()) * (average() / sigma());
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:27:38)
	 * 
	 * @return double
	 */
	public double max() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return zMax;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:28:08)
	 * 
	 * @return double
	 */
	public double min() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return zMin;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:23:29)
	 * 
	 * @return double[]
	 */
	public double[] realBorders() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		double[] rb = new double[border.length + 2];
		for (int i = 0; i < border.length; i++)
			rb[i + 1] = border[i];
		double di = rb[2] - rb[1];
		rb[0] = rb[1] - di;
		rb[rb.length - 1] = rb[rb.length - 2] + di;
		return rb;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:28:32)
	 * 
	 * @return double[]
	 */
	public double[] relativeFrequency() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		double[] result = new double[sumInterval.length];
		double s = 0;
		for (int i = 0; i < sumInterval.length; i++) {
			s += sumInterval[i];
		}
		for (int i = 0; i < sumInterval.length; i++) {
			result[i] = sumInterval[i] / s;
		}
		return result;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:30:44)
	 * 
	 * @return double
	 */
	public void showAccumFrec(Diagram diagram, java.awt.Color color, double width, double shift, boolean reSet) {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		diagram.setPainterColor(color);
		diagram.drawBarsDiagram(realBorders(), accumFrequency(), width, shift, reSet);
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:30:44)
	 * 
	 * @return double
	 */
	public void showRelFrec(Diagram diagram) {
		if (border == null) {
			if (tmpVector.size() < 3)
				return;
			fillHistoForVector(tmpVector);
		}
		diagram.setPainterColor(java.awt.Color.magenta);
		// Для функції Тест
		addMenuItemTest(diagram);
		diagram.drawBarsDiagram(realBorders(), relativeFrequency(), 0.8, 0.1, true);

	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:30:44)
	 * 
	 * @return double
	 */
	public void showRelFrec(Diagram diagram, java.awt.Color color, double width, double shift, boolean reSet) {
		if (border == null) {
			if (tmpVector.size() < 3)
				return;
			fillHistoForVector(tmpVector);
		}
		diagram.setPainterColor(color);
		diagram.drawBarsDiagram(realBorders(), relativeFrequency(), width, shift, reSet);
		addMenuItemTest(diagram);
	}

	/**
	 * Метод для добавления пункта меню тест в контекстное меню диаграммы
	 * 
	 * @param diagram
	 *            - диаграмма в контекстное меню которой надо добавить пункт
	 *            меню тест
	 */
	private void addMenuItemTest(Diagram diagram) {
		// проверяем есть ли уже данный пункт в контекстном меню
		Component comp = checkComponentByName(diagram.getJPopupMenu1(), "JMenuItemTest");
		if (comp != null) {
			diagram.getJPopupMenu1().remove(comp);
		}
		// Створюємо новий
		JMenuItem itemTest = new javax.swing.JMenuItem();
		itemTest.setName("JMenuItemTest");
		itemTest.setBorder(new javax.swing.border.CompoundBorder());
		itemTest.setText("Test");
		itemTest.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (test != null) {
					test.setVisible(false);
					test=null;
				}
					test = new Test(diagram, Histo.this);
					test.setVisible(true);
				
			}

		});
		diagram.getJPopupMenu1().add(itemTest);
	}

	public Test getTest() {
		return test;
	}

	/**
	 * Метод для проверки на наличие пункта контекстного меню по его имени
	 * 
	 * @param cont
	 *            - контекстное меню, в котором необходимо произвести проверку
	 *            на наличие пункта меню с именем name
	 * @param name
	 *            - имя пункта контекстного меню
	 * @return
	 */
	private Component checkComponentByName(JPopupMenu cont, String name) {
		Component comp[] = cont.getComponents();
		for (int i = 0; i < cont.getComponentCount(); i++)
			if (comp[i].getName().equals(name))
				return comp[i];
		return null;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:30:13)
	 * 
	 * @return double
	 */
	public double sigma() {
		if (border == null) {
			if (tmpVector.size() < 3)
				return 0;
			fillHistoForVector(tmpVector);
		}
		return (Math.sqrt((total2 / totalF) - (this.average() * this.average())));
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:29:32)
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		if (border == null) {
			if (tmpVector.size() < 3)
				return "";
			fillHistoForVector(tmpVector);
		}
		String s = "Параметри вибірки:\n";
		s += "Обсяг вибірки = " + Integer.toString(count()) + ".\n";
		s += "Мінимальне значення = " + Double.toString(min()) + ".\n";
		s += "Найбільше значення = " + Double.toString(max()) + ".\n";
		s += "Середнє значення = " + StatTables.format(average(), 1, 3) + ".\n";
		s += "Стандартне відхилення = " + StatTables.format(sigma(), 1, 3) + ".\n";
		s += "Коеф.Ерланга = (M/Sigma)^2 = " + StatTables.format(kErlang(), 1, 1) + ".\n";
		// ширина поля для границы интервала
		double[] br = realBorders();
		int maxLenB = 0;
		for (int j = 0; j < br.length; j++) {
			int l = String.valueOf(Math.round(br[j])).length();
			if (l > maxLenB)
				maxLenB = l;

		}
		maxLenB += 3;
		String z = " Інтервали  ";
		for (int j = 0; j < maxLenB - 3; j++) {
			z = " " + z + " ";
		}
		// Выводим табличку интервалов и частот
		s += z + "Відн.частоти\n";
		for (int j = 0; j < br.length - 1; j++) {
			s += stat.StatTables.format(br[j], maxLenB, 2) + " .. ";
			s += stat.StatTables.format(br[j + 1], maxLenB, 2) + "  ";
			s += stat.StatTables.format(relativeFrequency()[j], 9, 4) + "\n";
		}
		return s;
	}

	/**
	 * Insert the method's description here. Creation date: (10.12.2004
	 * 16:30:44)
	 * 
	 * @return double
	 */
	public double total() {
		if (border == null) {
			fillHistoForVector(tmpVector);
		}
		return total;
	}

	/**
	 * Глубокое клонирование объкта(Deep copy)
	 */
	public Histo clone() {
		Histo h = new Histo();
		h.border = this.border.clone();
		h.count = this.count;
		h.sumInterval = this.sumInterval.clone();
		Vector clone = (Vector) this.tmpVector.clone();
		h.tmpVector = clone;
		h.total = this.total;
		h.total2 = this.total2;
		h.zMax = this.zMax;
		h.zMin = h.zMin;
		return h;
	}

}
