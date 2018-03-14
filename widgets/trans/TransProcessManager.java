package widgets.trans;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import process.Dispatcher;
import process.IModelFactory;
import widgets.Diagram;
import widgets.parmFinder.ParmFinderView;
import widgets.regres.IRegresable;

import java.awt.Insets;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Объекты этого класса предназначены для задания параметров мониторинга,
 * создания и запуска требуемого количества параллельно работающих моделей и
 * периодического снятия и усреднения данных о работе модели. Создание моделей,
 * их запуск и мониторинг осуществляется объектом monitor, который создается с
 * помощью безымянного внутреннего класса, наследующего класс Actor. Объект
 * monitor создается вместе с диспетчером при нажатии на кнопку "Старт". Объект
 * monitor передается диспетчеру, после чего диспетчер запускается. После
 * запуска диспетчера начинают выполняться правила действия монитора. Монитор
 * создает массив объектов ITransProcesable, используя объект model тако же
 * типа, вызывая его метод createAndInitTransModel(Dispatcherdispatcher,double
 * finishTime), который должен обеспечить создание и подготовку модели к работе.
 * Работать модель должна с тем же диспетчером, что и монитор. Созданные модели
 * монитор передает в стартовый список диспетчера с помощью метода
 * componentsToStartList(). Мониторинг параллельно работающих моделей
 * осуществляется через промежутки interval модельного времени. В начале каждого
 * промежутка накопители информации в модели инициализируются с помощью метода
 * resetAccum(). В конце промежутка времени interval, monitor, с помощью метода
 * getTransResult(), опрашивает все параллельно работающие модели. Результаты
 * опроса усредняются, передаются объекту painter для отрисовки на диаграмме и
 * запомонаются в массиве intervalAverageArray.
 * 
 * Creation date: (18.11.2007 17:26:58)
 * 
 * @author: biv
 */
public class TransProcessManager extends javax.swing.JPanel implements IRegresable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double interval; // Интервал усреднения информации о процессе

	private int nIntervals; // Число интервалов усреднения

	private int nParallel; // Число параллельно работающих моделей

	private IModelFactory factory = null;

	private TransMonitor monitor = null;

	private Dispatcher dispatcher = null;

	private JButton jButtonStart = null;

	private JPanel jPanelIntrval = null;

	private JTextField jTextFieldInterval = null;

	private JPanel jPanelNintervals = null;

	private JTextField jTextFieldNintervals = null;

	private JPanel jPanelNparallel = null;

	private JTextField jTextFieldNParallel = null;
	private JLabel label;
	private Diagram diagram;
	private JComboBox<String> comboBox;

	/**
	 * This is the default constructor
	 */
	public TransProcessManager() {
		super();
		initialize();
	}

	/**
	 * Initialize the class.
	 */
	private void initialize() {
		try {
			setName("TransProcessManager");
			setBorder(new LineBorder(new Color(0, 0, 0)));
			setSize(387, 227);
			this.setNumberOfModel("200");
			this.setNumberOfInterval("5");
			this.setTextInterval("15");
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] { 150, 0, 0 };
			gridBagLayout.rowHeights = new int[] { 13, 54, 54, 54, 26, 0 };
			gridBagLayout.columnWeights = new double[] { 0.0, 1.0,
					Double.MIN_VALUE };
			gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0,
					Double.MIN_VALUE };
			setLayout(gridBagLayout);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.VERTICAL;
			gbc_label.insets = new Insets(5, 5, 5, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 0;
			add(getLabel(), gbc_label);
			GridBagConstraints gbc_jPanelIntrval = new GridBagConstraints();
			gbc_jPanelIntrval.insets = new Insets(0, 0, 5, 5);
			gbc_jPanelIntrval.gridx = 0;
			gbc_jPanelIntrval.gridy = 1;
			this.add(getJPanelIntrval(), gbc_jPanelIntrval);
			GridBagConstraints gbc_diagram_1 = new GridBagConstraints();
			gbc_diagram_1.gridheight = 4;
			gbc_diagram_1.insets = new Insets(5, 5, 5, 5);
			gbc_diagram_1.fill = GridBagConstraints.BOTH;
			gbc_diagram_1.gridx = 1;
			gbc_diagram_1.gridy = 0;
			add(getDiagram(), gbc_diagram_1);
			GridBagConstraints gbc_jPanelNintervals = new GridBagConstraints();
			gbc_jPanelNintervals.insets = new Insets(0, 0, 5, 5);
			gbc_jPanelNintervals.gridx = 0;
			gbc_jPanelNintervals.gridy = 2;
			this.add(getJPanelNintervals(), gbc_jPanelNintervals);
			GridBagConstraints gbc_jPanelNparallel = new GridBagConstraints();
			gbc_jPanelNparallel.insets = new Insets(0, 0, 5, 5);
			gbc_jPanelNparallel.gridx = 0;
			gbc_jPanelNparallel.gridy = 3;
			this.add(getJPanelNparallel(), gbc_jPanelNparallel);
			GridBagConstraints gbc_jButtonStart = new GridBagConstraints();
			gbc_jButtonStart.insets = new Insets(5, 5, 5, 5);
			gbc_jButtonStart.gridx = 0;
			gbc_jButtonStart.gridy = 4;
			this.add(getJButtonStart(), gbc_jButtonStart);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(5, 5, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 4;
			add(getComboBox(), gbc_comboBox);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	/**
	 * This method initializes jButtonStart
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("Старт");
			jButtonStart.setMinimumSize(new Dimension(66, 22));
			jButtonStart.setHorizontalTextPosition(SwingConstants.CENTER);
			jButtonStart.setMaximumSize(new Dimension(11111, 2222222));
			jButtonStart.setPreferredSize(new Dimension(196, 560));
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startMonitoring();
				}
			});
		}
		return jButtonStart;
	}

	// Запуск процеса моделюванння
	private void startMonitoring() {

		getJButtonStart().setEnabled(false);
		diagram.setHorizontalMaxText(String.valueOf((int) getFinishTime()));
		diagram.setGridByX(getNIntervals());
		diagram.clear();
		monitor = new TransMonitor();
		monitor.setNameForProtocol("Monitor");
		monitor.setDiagram(getDiagram());
		monitor.setComboBox(getComboBox());

		monitor.setNParallel(getNParallel());
		monitor.setNIntervals(getNIntervals());
		monitor.setInterval(getInterval());
		monitor.setFactory(getFactory());

		getDispatcher().addStartingActor(getMonitor());
		getDispatcher().start();
		new Thread() {
			public void run() {
				try {
					dispatcher.getThread().join();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				getJButtonStart().setEnabled(true);
			};

		}.start();

	}

	/*
	 * Возвращает время моделирования
	 */
	public double getFinishTime() {
		return getInterval() * getNIntervals();
	}

	// Позволяет связать монитор с искателем параметров
	// функции регресии для результатов мониторинга
	public void setParmFinder(ParmFinderView o) {
	}

	/**
	 * This method initializes jPanelIntrval
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelIntrval() {
		if (jPanelIntrval == null) {
			jPanelIntrval = new JPanel();
			jPanelIntrval.setLayout(new CardLayout());
			jPanelIntrval
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Інтервал усереднення",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelIntrval.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelIntrval.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelIntrval.add(getJTextFieldInterval(), getJTextFieldInterval()
					.getName());
		}
		return jPanelIntrval;
	}

	/**
	 * This method initializes jTextFieldInterval
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldInterval() {
		if (jTextFieldInterval == null) {
			jTextFieldInterval = new JTextField();
			jTextFieldInterval.setName("jTextFieldInterval");
			jTextFieldInterval
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldInterval.setText("15");
		}
		return jTextFieldInterval;
	}

	/**
	 * This method initializes jPanelNintervals
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNintervals() {
		if (jPanelNintervals == null) {
			jPanelNintervals = new JPanel();
			jPanelNintervals.setLayout(new CardLayout());
			jPanelNintervals
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Кількість інтервалів",
									javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelNintervals.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelNintervals.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelNintervals.add(getJTextFieldNintervals(),
					getJTextFieldNintervals().getName());
		}
		return jPanelNintervals;
	}

	/**
	 * This method initializes jTextFieldNintervals
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNintervals() {
		if (jTextFieldNintervals == null) {
			jTextFieldNintervals = new JTextField();
			jTextFieldNintervals.setName("jTextFieldNintervals");
			jTextFieldNintervals
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldNintervals.setText("5");
		}
		return jTextFieldNintervals;
	}

	/**
	 * This method initializes jPanelParallel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNparallel() {
		if (jPanelNparallel == null) {
			jPanelNparallel = new JPanel();
			jPanelNparallel.setLayout(new CardLayout());
			jPanelNparallel
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"Паралельно моделей",
									javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelNparallel.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelNparallel.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelNparallel.add(getJTextFieldNparallel(),
					getJTextFieldNparallel().getName());
		}
		return jPanelNparallel;
	}

	/**
	 * This method initializes jTextFieldParallel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNparallel() {
		if (jTextFieldNParallel == null) {
			jTextFieldNParallel = new JTextField();
			jTextFieldNParallel.setName("jTextFieldNParallel");
			jTextFieldNParallel
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldNParallel.setText("200");
		}
		return jTextFieldNParallel;
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNparallelText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getNumberOfModel() {
		return getJTextFieldNparallel().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldIntervalText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTextInterval() {
		return getJTextFieldInterval().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNintervalsText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getNumberOfInterval() {
		return getJTextFieldNintervals().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNparallelText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setNumberOfModel(java.lang.String arg1) {
		getJTextFieldNparallel().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the JTextFieldIntervalText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setTextInterval(java.lang.String arg1) {
		getJTextFieldInterval().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNintervalsText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setNumberOfInterval(java.lang.String arg1) {
		getJTextFieldNintervals().setText(arg1);
	}

	/**
	 * Called whenever the part throws an exception.
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* Uncomment the following lines to print uncaught exceptions to stdout */
		System.out.println("Не прошла инициализация визуальной части монитра");
		exception.printStackTrace(System.out);
	}

	public double getInterval() {
		return interval = Integer.parseInt(getJTextFieldInterval().getText());
	}

	public int getNIntervals() {
		return nIntervals = Integer.parseInt(getJTextFieldNintervals()
				.getText());
	}

	public int getNParallel() {
		nParallel = Integer.parseInt(getJTextFieldNparallel().getText());
		return nParallel;
	}

	private IModelFactory getFactory() {
		if (factory == null)
			System.out.println("Не виначено factory для TransProcessManager");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}

	public TransMonitor getMonitor() {
		return monitor;
	}

	public Dispatcher getDispatcher() {
		if (dispatcher == null) {
			dispatcher = new Dispatcher();
			dispatcher.setProtocolFileName("");
		}
		return dispatcher;
	}

	@Override
	public Map<Double, List<Double>> getResultMap() {
		HashMap<Double, List<Double>> resMap = new HashMap<>();
		for (int i = 0; i < nIntervals; i++) {
			double key = (i + 0.5) * interval;
			ArrayList<Double> resList = new ArrayList<>(1);
			resList.add(getMonitor().getAverageArray()[i]);
			resMap.put(key, resList);
		}
		return resMap;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel(
					"\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u0438 \u043C\u043E\u043D\u0456\u0442\u043E\u0440\u0443");
			label.setPreferredSize(new Dimension(102, 20));
			label.setFont(new Font("Tahoma", Font.PLAIN, 12));
			label.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return label;
	}

	public Diagram getDiagram() {
		if (diagram == null) {
			diagram = new Diagram();
			diagram.setTitleText("\u041F\u0435\u0440\u0435\u0445\u0456\u0434\u043D\u0438\u0439 \u043F\u0440\u043E\u0446\u0435\u0441");
			diagram.setPainterColor(Color.MAGENTA);
		}
		return diagram;
	}

	public JComboBox<String> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<String>();
			comboBox.addActionListener((e) -> onComboAction(e));
		}
		return comboBox;
	}

	private void onComboAction(ActionEvent e) {
		monitor.getDiagram().clear();
		double[] array = monitor.getAverageArray();
		for (int j = 0; j < array.length; j++){
			diagram.getPainter().drawOvalAtXY((float) (interval * (j + 0.5)),
					(float) array[j], 3, 3);
		diagram.getPainter().drawOvalAtXY((float) (interval * (j + 0.5)),
				(float) array[j], 2, 2);
		}
	}
} // @jve:decl-index=0:visual-constraint="52,6"
