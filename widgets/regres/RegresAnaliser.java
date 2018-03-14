package widgets.regres;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import widgets.Diagram;

/**
 * @author biv Компонент работает с объектом experimentControl, реализующим
 *         интерфейс IRegresable, и диаграммой. Наличие диаграммы не
 *         обязательно. Объект experimentControl дает доступ к двум массивам
 *         чисел. Первый массив - значения фактора, второй масив - значения
 *         фунции отклика. Задача компонента проверить соответствие между
 *         экспериментальными данными и выбранной функцией регрессии. Базовый
 *         набор функций задается в соответствии с набором подклассов класса
 *         RegresTesters. Возможно расширение этого набора при расширении
 *         количества подклассов. Для добавлении новых функций используется
 *         метод addFunction(RegresTesters obj) Результаты обработки выводятся в
 *         виде текстового сообщения и в виде линии регрессии, проходящей около
 *         экспериментальных точек на диаграмме
 */
public class RegresAnaliser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox jComboBoxChooseRegres = null;

	private JScrollPane jScrollPaneRegresTestResult = null;

	private JTextPane jTextPaneRegresTestResult = null;

	private JButton jButtonTestRegres = null;

	private JPanel jPanelChooseRegres = null;

	private ArrayList<RegresTesters> funcArray = (new ArrayList<RegresTesters>()); // @jve:decl-index=0:

	private IRegresable regresable = null;

	private Diagram diagram = null;

	private DefaultComboBoxModel defaultComboBoxModel = null; // @jve:decl-index=0:visual-constraint="174,293"

	public void setDiagram(Diagram d) {
		diagram = d;
	}

	public RegresAnaliser() {
		super();
		initialize();
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxChooseRegres() {
		if (jComboBoxChooseRegres == null) {
			jComboBoxChooseRegres = new JComboBox();
			jComboBoxChooseRegres.setName("JComboBox1");
			jComboBoxChooseRegres.setMinimumSize(new Dimension(126, 25));
			jComboBoxChooseRegres.setPreferredSize(new Dimension(130, 25));
			jComboBoxChooseRegres.setModel(getDefaultComboBoxModel());
			jComboBoxChooseRegres.setMaximumSize(new Dimension(32767, 25));
			jComboBoxChooseRegres.setVisible(true);
		}
		return jComboBoxChooseRegres;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneRegresTestResult() {
		if (jScrollPaneRegresTestResult == null) {
			jScrollPaneRegresTestResult = new JScrollPane();
			jScrollPaneRegresTestResult.setName("JScrollPaneRegresTestResult");
			jScrollPaneRegresTestResult.setBorder(new EtchedBorder());
			jScrollPaneRegresTestResult
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPaneRegresTestResult
					.setViewportView(getJTextPaneRegresTestResult());
		}
		return jScrollPaneRegresTestResult;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPaneRegresTestResult() {
		if (jTextPaneRegresTestResult == null) {
			jTextPaneRegresTestResult = new JTextPane();
			jTextPaneRegresTestResult.setName("JTextPaneRegresTestResult");
		}
		return jTextPaneRegresTestResult;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonTestRegres() {
		if (jButtonTestRegres == null) {
			jButtonTestRegres = new JButton();
			jButtonTestRegres.setName("jButtonTestRegres");
			jButtonTestRegres.setText("Тестувати");
			jButtonTestRegres.setMargin(new Insets(2, 4, 2, 4));
			jButtonTestRegres
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							buttonTestClick();
						}
					});
		}
		return jButtonTestRegres;
	}

	/**
	 * This method initializes jPanelChooseRegres
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelChooseRegres() {
		if (jPanelChooseRegres == null) {
			jPanelChooseRegres = new JPanel();
			jPanelChooseRegres.setLayout(new CardLayout());
			jPanelChooseRegres
					.setBorder(javax.swing.BorderFactory.createTitledBorder(
							javax.swing.BorderFactory.createEmptyBorder(0, 0,
									0, 0),
							"Вибір функції регресії",
							javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
							javax.swing.border.TitledBorder.DEFAULT_POSITION,
							new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
							new java.awt.Color(51, 51, 51)));
			jPanelChooseRegres.setVisible(true);
			jPanelChooseRegres.add(getJComboBoxChooseRegres(),
					getJComboBoxChooseRegres().getName());
		}
		return jPanelChooseRegres;
	}

	/**
	 * This method initializes defaultComboBoxModel
	 * 
	 * @return javax.swing.DefaultComboBoxModel
	 */
	private DefaultComboBoxModel getDefaultComboBoxModel() {
		if (defaultComboBoxModel == null) {
			defaultComboBoxModel = new DefaultComboBoxModel();
		}
		return defaultComboBoxModel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		getDefaultComboBoxModel().addElement("");
		addFunction(new Regres1Undepend());
		addFunction(new Regres1Proportion());
		addFunction(new Regres1NonProportion1());
		addFunction(new Regres1NonProportion2());
		addFunction(new Regres2Linear());
		addFunction(new Regres2Giperbola());
		addFunction(new Regres2Parabola());
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.insets = new java.awt.Insets(3, 5, 2, 3);
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.ipadx = 27;
		gridBagConstraints3.ipady = 2;
		gridBagConstraints3.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.ipadx = 138;
		gridBagConstraints2.ipady = 100;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.weighty = 1.0;
		gridBagConstraints2.insets = new java.awt.Insets(3, 5, 2, 3);
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.insets = new java.awt.Insets(3, 23, 3, 23);
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.ipadx = 40;
		gridBagConstraints1.gridx = 0;
		this.setLayout(new GridBagLayout());
		this.setSize(177, 223);
		this.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory
						.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
				javax.swing.BorderFactory
						.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED)));
		this.add(getJButtonTestRegres(), gridBagConstraints1);
		this.add(getJScrollPaneRegresTestResult(), gridBagConstraints2);
		this.add(getJPanelChooseRegres(), gridBagConstraints3);

	}

	public void addFunction(RegresTesters obj) {
		funcArray.add(obj);
		getDefaultComboBoxModel().addElement(obj.getLabelName());
	}

	public void buttonTestClick() {
		String st = "";
		if (regresable == null) {
			getJTextPaneRegresTestResult().setText("Нема джерела даних!");
			return;
		}
		if (regresable.getResultMap() == null) {
			st = "Нема даних для аналізу ";
			getJTextPaneRegresTestResult().setText(st);
			return;
		}

		st = (String) getJComboBoxChooseRegres().getSelectedItem();
		drawData(diagram);

		Double[] factorArray = new Double[regresable.getResultMap().size()];
		factorArray = regresable.getResultMap().keySet().toArray(factorArray);
		Arrays.sort(factorArray);
		Collection<List<Double>> listResult = regresable.getResultMap()
				.values();

		RegresTesters tst = null, tmp = null;
		// Находим подходящий тестер по полю labelName
		if (factorArray.length == 1)
			tst = (RegresTesters) funcArray.get(0);
		else {
			for (int i = 0; i < funcArray.size(); i++) {
				tmp = (RegresTesters) funcArray.get(i);
				if (st == tmp.getLabelName()) {
					tst = tmp;
					break;
				}
			}
		}
		if (tst != null) {
			String strResult = "Результати тестування:\n";
			// просим тестер обработать результат
			tst.testMatrix(regresable.getResultMap());
			if (factorArray.length > 1) {
				if (diagram != null) {
					widgets.Painter pa = diagram.getPainter();
					pa.setColor(new Color(255, 0, 0));

					// прорисовываем линию регрессии
					double start = factorArray[0];
					double end = factorArray[factorArray.length - 1];
					double delta = (end - start) / 100;
					double cur = start - delta;
					pa.placeToXY((float) cur, (float) tst.f(cur));
					while (cur <= end) {
						cur += delta;
						try {
							pa.drawToXY((float) cur, (float) tst.f(cur));
						} catch (Exception e) {

						}

					}
					double dovIn = 0;
					double sr = 0;
					int bigD = 0;
					for (int i = 0; i < factorArray.length; i++) {
						dovIn = tst.getDoverArray()[i];
						sr = tst.getAvrgArray()[i];
						bigD = pa.convertDY((float) dovIn);
						pa.drawOvalAtXY(factorArray[i].floatValue(),
								(float) sr, 5, bigD);
						if (bigD > 1)
							pa.drawOvalAtXY(factorArray[i].floatValue(),
									(float) sr, 4, bigD - 1);
					}
				}
				// Выводим результатов регресионного анализа
				strResult += tst.parametersAsString() + "\n";
				strResult += tst.isDispersUniform() ? "Дисперсії однородні\n"
						: "Дисперсії неоднородні\n";
				strResult += tst.isFactorValid() ? "Вплив фактору суттєвий\n"
						: "Вплив фактору відсутній\n";
				strResult += tst.isRegresAdequat() ? "Лінія регрессії адекватна\n"
						: "Лінія регрессії не адекватна\n";

			} else {
				// Выводим информацию о доверительном интервале
				double dovIn = tst.getDoverArray()[0];
				double sr = tst.getAvrg();
				if (diagram != null) {
					widgets.Painter pa = diagram.getPainter();
					pa.setColor(new Color(255, 0, 0));

					int bigD = pa.convertDY((float) dovIn);
					pa.drawOvalAtXY(factorArray[0].floatValue(), (float) sr, 5,
							bigD);
					if (bigD > 1)
						pa.drawOvalAtXY(factorArray[0].floatValue(),
								(float) sr, 4, bigD - 1);

				}
				strResult = "Середнє значенння функції: " + sr + ".\n";
				strResult += "Дисперсія експерименту: " + tst.dExpr + ".\n";
				strResult += "Ширина довірчого інтервалу: " + (2 * dovIn)
						+ ".\n";
				strResult += "Нижня границя інтервалу: " + (sr - dovIn) + ".\n";
				strResult += "Верхня границя інтервалу: " + (sr + dovIn)
						+ ".\n";
			}
			getJTextPaneRegresTestResult().setText(strResult);
		}
	}

	public void setIRegresable(IRegresable iReg) {
		this.regresable = iReg;

	}

	public void drawData(Diagram diagram) {
		if (diagram == null)
			return;
		diagram.getPainter().setColor(new Color(0, 0, 255));
		diagram.clear();
		if (regresable.getResultMap() == null)
			return;

		Set<Double> factorSet = regresable.getResultMap().keySet();
		for (Double factor : factorSet) {
			List<Double> resultList = regresable.getResultMap().get(factor);
			for (Double result : resultList) {
				diagram.getPainter().drawOvalAtXY(factor.floatValue(),
						result.floatValue(), 4, 4);

			}
		}

	}
} // @jve:decl-index=0:visual-constraint="151,49"
