package widgets.experiments;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import widgets.Diagram;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import widgets.regres.IRegresable;
import widgets.regres.RegresAnaliser;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import process.Dispatcher;
import process.DispatcherFinishListener;
import process.IModelFactory;
import widgets.ChooseData;
import widgets.ChooseDataH;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.awt.Dimension;

public class ExperimentManager extends JPanel implements IRegresable {
	private JCheckBox jCheckBox;
	private JButton jButtonRedraw;
	private Diagram diagram;
	private RegresAnaliser regresAnaliser;
	private ChooseData chooseDataFactors;
	private JButton jButtonStart;
	private ChooseDataH chooseDataRepeat;
	private int count; // countOfExperimentsOnLevel
	private double[] factors;
	private Map<String, Map<Double, Queue<Double>>> allResultsMap;
	private IModelFactory factory = null;
	private volatile String[] keyStrings;
	private DefaultComboBoxModel<String> comboModel;
	private CountDownLatch createMapCDL;
	private JComboBox<String> comboBox;
	private Executor pool = Executors.newWorkStealingPool();

	/**
	 * Create the panel.
	 */
	public ExperimentManager() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 30, 25, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		diagram = new Diagram();
		diagram.setTitleText("Результати експерименту");
		GridBagConstraints gbc_diagram = new GridBagConstraints();
		gbc_diagram.gridwidth = 2;
		gbc_diagram.insets = new Insets(5, 5, 5, 5);
		gbc_diagram.fill = GridBagConstraints.BOTH;
		gbc_diagram.gridx = 0;
		gbc_diagram.gridy = 0;
		add(diagram, gbc_diagram);

		regresAnaliser = new RegresAnaliser();
		regresAnaliser.setIRegresable(this);
		regresAnaliser.setDiagram(diagram);
		GridBagConstraints gbc_regresAnaliser = new GridBagConstraints();
		gbc_regresAnaliser.gridwidth = 2;
		gbc_regresAnaliser.insets = new Insets(5, 5, 5, 5);
		gbc_regresAnaliser.fill = GridBagConstraints.BOTH;
		gbc_regresAnaliser.gridx = 2;
		gbc_regresAnaliser.gridy = 0;
		add(regresAnaliser, gbc_regresAnaliser);

		JLabel label = new JLabel();
		label.setText("\u0412\u0438\u0431\u0456\u0440 \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u0456\u0432 \u0435\u043A\u0441\u043F\u0435\u0440\u0438\u043C\u0435\u043D\u0442\u0443");
		label.setName("factorLabel");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(5, 5, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);

		chooseDataFactors = new ChooseData();
		chooseDataFactors.setText("0.5  0.6  0.7  0.8");
		chooseDataFactors
				.setTitle("\u0417\u043D\u0430\u0447\u0435\u043D\u043D\u044F \u0444\u0430\u043A\u0442\u043E\u0440\u0443");
		chooseDataFactors.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					double[] ar = chooseDataFactors.getDoubleArray();
					Arrays.sort(ar);
					int max = (int) (ar[ar.length - 1] + 1);
					String maxText = String.valueOf(max);
					getDiagram().setHorizontalMaxText(maxText);
				} catch (Exception e2) {

				}
			}
		});
		GridBagConstraints gbc_chooseDataFactors = new GridBagConstraints();
		gbc_chooseDataFactors.gridwidth = 2;
		gbc_chooseDataFactors.gridheight = 2;
		gbc_chooseDataFactors.insets = new Insets(5, 5, 5, 5);
		gbc_chooseDataFactors.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooseDataFactors.gridx = 2;
		gbc_chooseDataFactors.gridy = 1;
		add(chooseDataFactors, gbc_chooseDataFactors);

		comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new Dimension(228, 20));
		comboBox.setMinimumSize(new Dimension(228, 20));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onComboChange(e);
			}
		});

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 5, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		add(comboBox, gbc_comboBox);

		jCheckBox = new JCheckBox("Ln");
		GridBagConstraints gbc_jCheckBox = new GridBagConstraints();
		gbc_jCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_jCheckBox.gridx = 0;
		gbc_jCheckBox.gridy = 3;
		add(jCheckBox, gbc_jCheckBox);

		jButtonRedraw = new JButton(
				"\u041F\u0435\u0440\u0435\u0440\u0438\u0441\u043E\u0432\u0430\u0442\u0438");
		jButtonRedraw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				redraw(getDiagram());
			}
		});

		GridBagConstraints gbc_jButtonRedraw = new GridBagConstraints();
		gbc_jButtonRedraw.insets = new Insets(5, 5, 5, 5);
		gbc_jButtonRedraw.gridx = 1;
		gbc_jButtonRedraw.gridy = 3;
		add(jButtonRedraw, gbc_jButtonRedraw);

		chooseDataRepeat = new ChooseDataH();
		chooseDataRepeat.setText("  5   ");
		chooseDataRepeat.setDividerLocation(0.7);
		chooseDataRepeat
				.setTitle("\u041F\u043E\u0432\u0442\u043E\u0440\u0456\u0432 \u043D\u0430 \u0440\u0456\u0432\u043D\u0456");
		GridBagConstraints gbc_chooseDataRepeat = new GridBagConstraints();
		gbc_chooseDataRepeat.insets = new Insets(5, 5, 5, 5);
		gbc_chooseDataRepeat.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooseDataRepeat.gridx = 2;
		gbc_chooseDataRepeat.gridy = 3;
		add(chooseDataRepeat, gbc_chooseDataRepeat);

		jButtonStart = new JButton("\u0421\u0442\u0430\u0440\u0442");
		Runnable r = () -> onBtnStart();
		jButtonStart.addActionListener((e) -> new Thread(r).start());
		GridBagConstraints gbc_jButtonStart = new GridBagConstraints();
		gbc_jButtonStart.insets = new Insets(5, 5, 5, 5);
		gbc_jButtonStart.gridx = 3;
		gbc_jButtonStart.gridy = 3;
		add(jButtonStart, gbc_jButtonStart);

	}

	public void onBtnStart() {
		getJButtonStart().setEnabled(false);
		// Читаємо параметри експерименту
		count = getChooseDataRepeat().getInt();
		factors = getChooseDataFactors().getDoubleArray();
		// Бар'єр для відновлення Enabled кнопки Старт
		CountDownLatch doneCDL = new CountDownLatch(factors.length * count);
		// Створюємо карту для результатів
		allResultsMap = new ConcurrentSkipListMap<>();
		createMapCDL = new CountDownLatch(1);

		// Готуємо діаграму
		if (getDiagram() != null) {
			getDiagram().getPainter().setColor(new Color(0, 0, 255));
			getDiagram().clear();
		}

		// Клас, що містить завдання для потоків, що проводять експерименти
		// //////////////////////////////////////////
		class Task implements Runnable {
			private double factor;

			public Task(double factor) {
				this.factor = factor;
			}

			@Override
			public void run() {
				Dispatcher dispatcher = new Dispatcher();
				IExperimentable model = (IExperimentable) (getFactory()
						.createModel(dispatcher));
				// Ініціалізація моделі
				model.initForExperiment(factor);
				// Handler for DispatcherFinishEvent
				DispatcherFinishListener df = new Df(model, factor, doneCDL);
				// () -> {
				// // Отримуємо мапу "Назва відгуку" => "Значення відгуку"
				// Map<String, Double> resMap = model.getResultOfExperiment();
				// // Якщо це перший результат, створюємо модель комбо
				// // та мапу для усіх результатів
				// if (comboModel == null)
				// createComboModel(resMap);
				// if (allResultsMap.isEmpty())
				// initResultMap();
				// try {
				// createMapCDL.await();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// // Розносимо результати по мапі усіх результатів
				// for (String keyStr : allResultsMap.keySet()) {
				// Double y = resMap.get(keyStr);
				// allResultsMap.get(keyStr).get(factor).add(y);
				// if (comboBox.getSelectedItem().equals(keyStr)) {
				// if (getJCheckBox().isSelected())
				// y = Math.log(y);
				// getDiagram().getPainter().drawOvalAtXY(
				// (float) factor, y.floatValue(), 4, 4);
				// }
				// }
				// doneCDL.countDown();
				// };
				dispatcher.addDispatcherFinishListener(df);
				// Починаємо експеримент з моделлю
				dispatcher.start();
			}
		}
		// Цикл повторів для одного значення фактору
		for (int k = 0; k < count; k++) {
			// Цикл експериментів для різних значень фактору
			for (int j = 0; j < factors.length; j++) {
				// Запуск експерименту
				pool.execute(new Task(factors[j]));
			}
		}
		try {
			doneCDL.await();
		} catch (InterruptedException e) {
			System.out.println("terminated after 1 MINUTES");
		}
		// TODO New create place of comboModel after doneCDL.await(); 10/05/20154
		// *********************************************

		keyStrings = (String[]) allResultsMap.keySet().toArray(
				new String[allResultsMap.keySet().size()]);
		comboModel = new DefaultComboBoxModel<>(keyStrings);
		comboBox.setModel(comboModel);
		// **************************************************
		getJButtonStart().setEnabled(true);
		redraw(diagram);
	}

	// TODO "Double-Checked Locking" idiom 11/05/15
	private void createKeyStrings(Map<String, Double> resMap) {
		String[] result = keyStrings;
		if (result == null) {
			synchronized (this) {
				result = keyStrings;
				if (result == null) {
					keyStrings = result = (String[]) resMap.keySet().toArray(
							new String[resMap.keySet().size()]);
				}
			}
		}
		//TODO move after doneCDL.await(); 10/05/20154
		// comboModel = new DefaultComboBoxModel<>(keyStrings);
		// comboBox.setModel(comboModel);
	}

	// TODO ?add synchronized 8.05.2015
	private synchronized void initResultMap() {
		if (allResultsMap.isEmpty())

			for (String keyStr : keyStrings) {
				Map<Double, Queue<Double>> subMap = new ConcurrentHashMap<>();
				for (double f : factors)
					subMap.put(f, new ConcurrentLinkedQueue<Double>());
				allResultsMap.put(keyStr, subMap);
			}
		createMapCDL.countDown();

	}

	public void redraw(Diagram diagram) {
		if (diagram == null)
			return;
		diagram.getPainter().setColor(new Color(0, 0, 255));
		diagram.clear();
		if (getResultMap() == null)
			return;

		for (Double factor : getResultMap().keySet()) {
			List<Double> resultList = getResultMap().get(factor);
			for (Double result : resultList) {
				diagram.getPainter().drawOvalAtXY(factor.floatValue(),
						result.floatValue(), 4, 4);
			}
		}
	}

	private void onComboChange(ActionEvent e) {
		redraw(getDiagram());

	}

	public IModelFactory getFactory() {
		if (factory == null)
			System.out.println("Не визначено factory для ExperimentControl");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}

	@Override
	public Map<Double, List<Double>> getResultMap() {
		Map<Double, List<Double>> returnMap = new TreeMap<>();
		String keyStr = (String) comboBox.getSelectedItem();
		if (keyStr != null) {
			Map<Double, Queue<Double>> subMap = allResultsMap.get(keyStr);

			for (double f : factors) {
				ArrayList<Double> arr = new ArrayList<>(count);
				for (Double y : subMap.get(f)) {
					if (getJCheckBox().isSelected())
						arr.add(Math.log(y));
					else
						arr.add(y);
				}
				returnMap.put(f, arr);
			}
		}
		return returnMap;
	}

	public JCheckBox getJCheckBox() {
		return jCheckBox;
	}

	public JButton getJButtonRedraw() {
		return jButtonRedraw;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public RegresAnaliser getRegresAnaliser() {
		return regresAnaliser;
	}

	public ChooseData getChooseDataFactors() {
		return chooseDataFactors;
	}

	public JButton getJButtonStart() {
		return jButtonStart;
	}

	public ChooseDataH getChooseDataRepeat() {
		return chooseDataRepeat;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	// TODO ???
	private class Df implements DispatcherFinishListener {

		private IExperimentable model;
		private double factor;
		private CountDownLatch doneCDL;

		public Df(IExperimentable model, double factor, CountDownLatch doneCDL) {
			this.model = model;
			this.factor = factor;
			this.doneCDL = doneCDL;
		}

		@Override
		public void onDispatcherFinish() {
			try {
				// Отримуємо мапу "Назва відгуку" => "Значення відгуку"
				Map<String, Double> resMap = model.getResultOfExperiment();
				// Якщо це перший результат, створюємо модель комбо
				// та мапу для усіх результатів
				// TODO sleep?
				// Thread.sleep((long) (Math.random() * 100));
				if (keyStrings == null)
					createKeyStrings(resMap);
				if (allResultsMap.isEmpty())
					initResultMap();
				try {
					createMapCDL.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Розносимо результати по мапі усіх результатів
				int i = 0;
				//for (String keyStr : allResultsMap.keySet()) {
				for (String keyStr : keyStrings) {
					Double y = resMap.get(keyStr);
					allResultsMap.get(keyStr).get(factor).add(y);
					if (getJCheckBox().isSelected())
						y = Math.log(y);
					// TODO if i==0
					if (i == 0)
						getDiagram().getPainter().drawOvalAtXY((float) factor,
								y.floatValue(), 4, 4);
					i++;

				}
				doneCDL.countDown();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println(Arrays.toString(keyStrings));
				e1.printStackTrace();
			}

		}

	}
}
