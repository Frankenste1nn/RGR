package widgets.parmFinder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import widgets.Diagram;
import widgets.regres.IRegresable;

/**
 * Этот компонент обеспечивает поиск параметров экспоненциального переходного
 * процесса Искомыми параметрами являются установившийся уровень выходного
 * сигнала и длительность переходного процесса Поиск производится методом
 * координатного спуска Целевая функция - либо сумма квадратов отклонений либо
 * сумма модулей Для работы компонента ему нужно передать: Художника, который
 * будет рисовать линию регрессии public void setParmFinderPainter(paint.Painter
 * arg1) Посилання на IRegresable, компонент, що містить мапу, ключами якої є
 * точки на оси времени для которых задаются значения выходного сигнала а value
 * - значеня выходного сигнала для заданных значений времени
 * setIRegresable(getTransMonitorView()); Creation date: (24.03.2006 21:20:40)
 * Modification: 10.05.2014 Компонент сделан в среде VisualAge
 * 
 * @author: biv
 */
public class ParmFinderView extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JLabel ivjJLabel10 = null;

	private javax.swing.JLabel ivjJLabel6 = null;

	private javax.swing.JLabel ivjJLabel7 = null;

	private javax.swing.JLabel ivjJLabel8 = null;

	private javax.swing.JLabel ivjJLabel9 = null;

	private javax.swing.JTextField ivjJTFLevelStart = null;

	private javax.swing.JTextField ivjJTFStepLevel = null;

	private javax.swing.JTextField ivjJTFStepTau = null;

	private javax.swing.JTextField ivjJTFTauStart = null;

	private javax.swing.JLabel ivjJLabel91 = null;

	private javax.swing.JTextField ivjJTFBestLevel1 = null;

	private javax.swing.JTextField ivjJTFBestTau1 = null;

	IvjEventHandler ivjEventHandler = new IvjEventHandler();

	private TransParmFinder ivjParmFinder = new widgets.parmFinder.TransParmFinder(
			this);
	private javax.swing.ButtonGroup ivjButtonGroup1 = null;

	private javax.swing.JButton ivjJButtonFind = null;

	private javax.swing.JButton ivjJButtonTest = null;

	private javax.swing.JLabel ivjJLabel71 = null;

	private javax.swing.JLabel ivjJLabel911 = null;

	private javax.swing.JRadioButton ivjJRadioButtonAbs = null;

	private javax.swing.JRadioButton ivjJRadioButtonSqr = null;

	private javax.swing.JTextField ivjJTFResult = null;

	private IRegresable iRegresable;

	private JCheckBox jCheckBox = null;

	/**
	 * @return the iRegresable
	 */
	public IRegresable getIRegresable() {
		if (iRegresable == null) {
			System.err.println("Не визначено джерело даних для ParmFinderView");
			System.err.println("Є метод setIRegresable(TransprocessManager)");
		}
		return iRegresable;
	}

	/**
	 * @param iRegresable
	 *            the iRegresable to set
	 */
	public void setIRegresable(IRegresable iRegresable) {
		this.iRegresable = iRegresable;

	}

	class IvjEventHandler implements java.awt.event.ActionListener,
			java.awt.event.ItemListener,
			widgets.parmFinder.FinderFinishListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == ParmFinderView.this.getJButtonTest())
				connEtoM8(e);
			if (e.getSource() == ParmFinderView.this.getJButtonTest())
				connEtoM7(e);
			if (e.getSource() == ParmFinderView.this.getJButtonTest())
				connEtoM9(e);
			if (e.getSource() == ParmFinderView.this.getJButtonFind())
				runFindRegres(e);
		};

		public void itemStateChanged(java.awt.event.ItemEvent e) {
			if (e.getSource() == ParmFinderView.this.getJRadioButtonSqr())
				connEtoM13(e);
		};

		public void onFinderFinish(java.util.EventObject evt) {
			if (evt.getSource() == ParmFinderView.this.ivjParmFinder)
				connEtoM14();
			if (evt.getSource() == ParmFinderView.this.ivjParmFinder)
				connEtoM15();
		};
	};

	/**
	 * ParmFinderView constructor comment.
	 */
	public ParmFinderView() {
		super();
		initialize();
	}

	/**
	 * ParmFinderView constructor comment.
	 * 
	 * @param layout
	 *            java.awt.LayoutManager
	 */
	public ParmFinderView(java.awt.LayoutManager layout) {
		super(layout);
	}

	/**
	 * ParmFinderView constructor comment.
	 * 
	 * @param layout
	 *            java.awt.LayoutManager
	 * @param isDoubleBuffered
	 *            boolean
	 */
	public ParmFinderView(java.awt.LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * ParmFinderView constructor comment.
	 * 
	 * @param isDoubleBuffered
	 *            boolean
	 */
	public ParmFinderView(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	/**
	 * connEtoM1:
	 * (JButtonFind.action.actionPerformed(java.awt.event.ActionEvent) -->
	 * ParmFinder.findRegres(DDDD)F)
	 * 
	 * @return float
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	public float runFindRegres(java.awt.event.ActionEvent arg1) {
		float result = 0;
		result = ivjParmFinder.findRegres(new Double(getJTFLevelStart()
				.getText()).doubleValue(), new Double(getJTFTauStart()
				.getText()).doubleValue(), new Double(getJTFStepLevel()
				.getText()).doubleValue(),
				new Double(getJTFStepTau().getText()).doubleValue());
		getJTFResult().setText(String.valueOf(result));

		return result;
	}

	/**
	 * connEtoM10: (
	 * (JButton11,action.actionPerformed(java.awt.event.ActionEvent) -->
	 * ParmFinder,drawRegres()F).normalResult --> JTFResult.text)
	 * 
	 * @param result
	 *            float
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM10(float result) {
		try {
			// user code begin {1}
			// user code end
			getJTFResult().setText(String.valueOf(result));
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM13:
	 * (JRadioButtonSqr.item.itemStateChanged(java.awt.event.ItemEvent) -->
	 * ParmFinder.metodSqr)
	 * 
	 * @param arg1
	 *            java.awt.event.ItemEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM13(java.awt.event.ItemEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			ivjParmFinder.setMetodSqr(getJRadioButtonSqr().isSelected());
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM14: (ParmFinder.finderFinish. --> JButton1.enabled)
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM14() {
		try {
			// user code begin {1}
			// user code end
			getJTFBestLevel1()
					.setText(String.valueOf(ivjParmFinder.getLevel()));
			// user code begin {2}
			getJTFBestLevel1().select(0, 0);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM15: (ParmFinder.finderFinish. --> JTFBestTau1.text)
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM15() {
		try {
			// user code begin {1}
			// user code end
			getJTFBestTau1().setText(String.valueOf(ivjParmFinder.getTau()));
			// user code begin {2}
			getJTFBestTau1().select(0, 0);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM5: (ParmFinderView.initialize() -->
	 * ButtonGroup1.add(Ljavax.swing.AbstractButton;)V)
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM5() {
		try {
			// user code begin {1}
			// user code end
			getButtonGroup1().add(getJRadioButtonAbs());
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM6: (ParmFinderView.initialize() -->
	 * ButtonGroup1.add(Ljavax.swing.AbstractButton;)V)
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM6() {
		try {
			// user code begin {1}
			// user code end
			getButtonGroup1().add(getJRadioButtonSqr());
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM7: (JButton11.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> ParmFinder.level)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM7(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			ivjParmFinder.setLevel(new Double(getJTFLevelStart().getText())
					.doubleValue());
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM8: (JButton11.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> ParmFinder.tau)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void connEtoM8(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			ivjParmFinder.setTau(new Double(getJTFTauStart().getText())
					.doubleValue());
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoM9: (JButton11.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> ParmFinder.drawRegres()F)
	 * 
	 * @return float
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private float connEtoM9(java.awt.event.ActionEvent arg1) {
		float connEtoM9Result = 0;
		try {
			// user code begin {1}
			// user code end
			connEtoM9Result = ivjParmFinder.drawRegres();
			connEtoM10(connEtoM9Result);
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
		return connEtoM9Result;
	}

	/**
	 * Return the ButtonGroup1 property value.
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.ButtonGroup getButtonGroup1() {
		if (ivjButtonGroup1 == null) {
			try {
				ivjButtonGroup1 = new javax.swing.ButtonGroup();
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjButtonGroup1;
	}

	/**
	 * Return the JButton1 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JButton getJButtonFind() {
		if (ivjJButtonFind == null) {
			try {
				ivjJButtonFind = new javax.swing.JButton();
				ivjJButtonFind.setName("JButton1");
				ivjJButtonFind.setText("Пошук");
				ivjJButtonFind.setMargin(new java.awt.Insets(2, 20, 2, 20));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJButtonFind;
	}

	/**
	 * Return the JButton11 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JButton getJButtonTest() {
		if (ivjJButtonTest == null) {
			try {
				ivjJButtonTest = new javax.swing.JButton();
				ivjJButtonTest.setName("JButton11");
				ivjJButtonTest.setText("Тест");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJButtonTest;
	}

	/**
	 * Return the JLabel10 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel10() {
		if (ivjJLabel10 == null) {
			try {
				ivjJLabel10 = new javax.swing.JLabel();
				ivjJLabel10.setName("JLabel10");
				ivjJLabel10.setText("Початкові");
				ivjJLabel10
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel10;
	}

	/**
	 * Return the JLabel6 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel6() {
		if (ivjJLabel6 == null) {
			try {
				ivjJLabel6 = new javax.swing.JLabel();
				ivjJLabel6.setName("JLabel6");
				ivjJLabel6.setPreferredSize(new java.awt.Dimension(39, 16));
				ivjJLabel6.setHorizontalAlignment(SwingConstants.CENTER);
				ivjJLabel6.setText("Тривалість");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel6;
	}

	/**
	 * Return the JLabel7 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel7() {
		if (ivjJLabel7 == null) {
			try {
				ivjJLabel7 = new javax.swing.JLabel();
				ivjJLabel7.setName("JLabel7");
				ivjJLabel7.setHorizontalAlignment(SwingConstants.CENTER);
				ivjJLabel7.setText("Рівень");

				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel7;
	}

	/**
	 * Return the JLabel71 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel71() {
		if (ivjJLabel71 == null) {
			try {
				ivjJLabel71 = new javax.swing.JLabel();
				ivjJLabel71.setName("JLabel71");
				ivjJLabel71.setText("Значення");
				ivjJLabel71
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel71;
	}

	/**
	 * Return the JLabel8 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel8() {
		if (ivjJLabel8 == null) {
			try {
				ivjJLabel8 = new javax.swing.JLabel();
				ivjJLabel8.setName("JLabel8");
				ivjJLabel8.setText("Оптимальні");
				ivjJLabel8
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel8;
	}

	/**
	 * Return the JLabel9 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel9() {
		if (ivjJLabel9 == null) {
			try {
				ivjJLabel9 = new javax.swing.JLabel();
				ivjJLabel9.setName("JLabel9");
				ivjJLabel9.setText("Крок");
				ivjJLabel9
						.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel9
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel9
						.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel9;
	}

	/**
	 * Return the JLabel91 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel91() {
		if (ivjJLabel91 == null) {
			try {
				ivjJLabel91 = new javax.swing.JLabel();
				ivjJLabel91.setName("JLabel91");
				ivjJLabel91.setText("Значення параметрів пошуку");
				ivjJLabel91
						.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel91
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel91
						.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel91;
	}

	/**
	 * Return the JLabel911 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getJLabel911() {
		if (ivjJLabel911 == null) {
			try {
				ivjJLabel911 = new javax.swing.JLabel();
				ivjJLabel911.setName("JLabel911");
				ivjJLabel911.setText("Цільова функція");
				ivjJLabel911
						.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel911
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				ivjJLabel911
						.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJLabel911;
	}

	/**
	 * Return the JRadioButton1 property value.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JRadioButton getJRadioButtonAbs() {
		if (ivjJRadioButtonAbs == null) {
			try {
				ivjJRadioButtonAbs = new javax.swing.JRadioButton();
				ivjJRadioButtonAbs.setName("JRadioButtonAbs");
				ivjJRadioButtonAbs.setText("minAbs");
				ivjJRadioButtonAbs
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJRadioButtonAbs;
	}

	/**
	 * Return the JRadioButton2 property value.
	 * 
	 * @return javax.swing.JRadioButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JRadioButton getJRadioButtonSqr() {
		if (ivjJRadioButtonSqr == null) {
			try {
				ivjJRadioButtonSqr = new javax.swing.JRadioButton();
				ivjJRadioButtonSqr.setName("JRadioButtonSqr");
				ivjJRadioButtonSqr.setSelected(true);
				ivjJRadioButtonSqr.setText("minSqr");
				ivjJRadioButtonSqr
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJRadioButtonSqr;
	}

	/**
	 * Return the JTFBestLevel1 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFBestLevel1() {
		if (ivjJTFBestLevel1 == null) {
			try {
				ivjJTFBestLevel1 = new javax.swing.JTextField();
				ivjJTFBestLevel1.setName("JTFBestLevel1");
				ivjJTFBestLevel1
						.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFBestLevel1.setText("0.00");
				ivjJTFBestLevel1.setEditable(false);
				ivjJTFBestLevel1.setMargin(new java.awt.Insets(0, 0, 0, 0));
				ivjJTFBestLevel1
						.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFBestLevel1;
	}

	/**
	 * Return the JTFBestTau1 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFBestTau1() {
		if (ivjJTFBestTau1 == null) {
			try {
				ivjJTFBestTau1 = new javax.swing.JTextField();
				ivjJTFBestTau1.setName("JTFBestTau1");
				ivjJTFBestTau1.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFBestTau1.setText("0.00");
				ivjJTFBestTau1.setEditable(false);
				ivjJTFBestTau1
						.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFBestTau1;
	}

	/**
	 * Return the JTFLevelStart property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFLevelStart() {
		if (ivjJTFLevelStart == null) {
			try {
				ivjJTFLevelStart = new javax.swing.JTextField();
				ivjJTFLevelStart.setName("JTFLevelStart");
				ivjJTFLevelStart
						.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFLevelStart.setText("1");
				ivjJTFLevelStart
						.setHorizontalAlignment(javax.swing.JTextField.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFLevelStart;
	}

	/**
	 * Return the JTFResult property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFResult() {
		if (ivjJTFResult == null) {
			try {
				ivjJTFResult = new javax.swing.JTextField();
				ivjJTFResult.setName("JTFResult");
				ivjJTFResult.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFResult.setText("0.00");
				ivjJTFResult.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
				ivjJTFResult.setSelectionEnd(0);
				ivjJTFResult.setEditable(false);
				ivjJTFResult
						.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
				ivjJTFResult.setSelectionStart(0);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFResult;
	}

	/**
	 * Return the JTFStepLevel property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFStepLevel() {
		if (ivjJTFStepLevel == null) {
			try {
				ivjJTFStepLevel = new javax.swing.JTextField();
				ivjJTFStepLevel.setName("JTFStepLevel");
				ivjJTFStepLevel
						.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFStepLevel.setText("0.10");
				ivjJTFStepLevel
						.setHorizontalAlignment(javax.swing.JTextField.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFStepLevel;
	}

	/**
	 * Return the JTFStepTau property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFStepTau() {
		if (ivjJTFStepTau == null) {
			try {
				ivjJTFStepTau = new javax.swing.JTextField();
				ivjJTFStepTau.setName("JTFStepTau");
				ivjJTFStepTau.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFStepTau.setText("1.00");
				ivjJTFStepTau
						.setHorizontalAlignment(javax.swing.JTextField.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFStepTau;
	}

	/**
	 * Return the JTFTauStart property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getJTFTauStart() {
		if (ivjJTFTauStart == null) {
			try {
				ivjJTFTauStart = new javax.swing.JTextField();
				ivjJTFTauStart.setName("JTFTauStart");
				ivjJTFTauStart.setBorder(new javax.swing.border.EtchedBorder());
				ivjJTFTauStart.setText("10.0");
				ivjJTFTauStart
						.setHorizontalAlignment(javax.swing.JTextField.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTFTauStart;
	}

	/**
	 * Method generated to support the promotion of the parmFinderPainter
	 * attribute.
	 * 
	 * @return paint.Painter
	 */
	public widgets.Painter getParmFinderPainter() {
		return ivjParmFinder.getPainter();
	}

	/**
	 * Called whenever the part throws an exception.
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* Uncomment the following lines to print uncaught exceptions to stdout */
		// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
		// exception.printStackTrace(System.out);
	}

	/**
	 * Initializes connections
	 * 
	 * @exception java.lang.Exception
	 *                The exception description.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getJButtonTest().addActionListener(ivjEventHandler);
		getJRadioButtonSqr().addItemListener(ivjEventHandler);
		ivjParmFinder.addFinderFinishListener(ivjEventHandler);
		getJButtonFind().addActionListener(ivjEventHandler);
	}

	/**
	 * Initialize the class.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints19.gridy = 5;
			gridBagConstraints19.ipadx = 2;
			gridBagConstraints19.ipady = 3;
			gridBagConstraints19.gridx = 2;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints18.gridy = 1;
			gridBagConstraints18.ipadx = 21;
			gridBagConstraints18.ipady = -1;
			gridBagConstraints18.gridx = 0;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.insets = new Insets(2, 2, 1, 4);
			gridBagConstraints17.gridy = 5;
			gridBagConstraints17.ipadx = 2;
			gridBagConstraints17.ipady = -1;
			gridBagConstraints17.gridx = 3;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints16.gridx = 3;
			gridBagConstraints16.gridy = 4;
			gridBagConstraints16.ipadx = 57;
			gridBagConstraints16.ipady = 5;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.insets = new Insets(2, 2, 1, 4);
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints15.gridy = 4;
			gridBagConstraints15.ipadx = 24;
			gridBagConstraints15.ipady = 9;
			gridBagConstraints15.gridx = 2;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 4;
			gridBagConstraints14.ipadx = 68;
			gridBagConstraints14.ipady = 9;
			gridBagConstraints14.gridwidth = 2;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints13.gridy = 5;
			gridBagConstraints13.ipadx = 15;
			gridBagConstraints13.ipady = 1;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints12.gridy = 5;
			gridBagConstraints12.ipadx = 13;
			gridBagConstraints12.ipady = 1;
			gridBagConstraints12.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(1, 2, 1, 4);
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.ipadx = 86;
			gridBagConstraints11.ipady = 9;
			gridBagConstraints11.gridwidth = 3;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.gridy = 3;
			gridBagConstraints10.ipadx = 57;
			gridBagConstraints10.ipady = 5;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(2, 2, 1, 4);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints9.gridx = 3;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = 57;
			gridBagConstraints9.ipady = 5;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(1, 2, 1, 4);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(1, 2, 1, 2);
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.ipadx = 42;
			gridBagConstraints8.ipady = 9;
			gridBagConstraints8.gridx = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(2, 2, 1, 2);
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.ipadx = 42;
			gridBagConstraints7.ipady = 9;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 53;
			gridBagConstraints6.ipady = 5;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(2, 2, 1, 2);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.ipadx = 70;
			gridBagConstraints5.ipady = 5;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(1, 2, 1, 2);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.ipadx = 53;
			gridBagConstraints4.ipady = 5;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.insets = new Insets(1, 2, 1, 2);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.ipadx = 53;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(2, 2, 1, 2);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(2, 2, 2, 4);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipadx = 17;
			gridBagConstraints2.ipady = 9;
			gridBagConstraints2.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.ipadx = 54;
			gridBagConstraints1.ipady = 9;
			gridBagConstraints1.gridx = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(3, 2, 1, 2);
			gridBagConstraints.gridy = 1;
			gridBagConstraints.ipadx = 23;
			gridBagConstraints.ipady = 9;
			gridBagConstraints.gridx = 1;
			setName("ParmFinderView");
			setBorder(new javax.swing.border.EtchedBorder());
			setLayout(new GridBagLayout());
			setSize(350, 171);

			this.add(getJLabel10(), gridBagConstraints);
			this.add(getJLabel9(), gridBagConstraints1);
			this.add(getJLabel8(), gridBagConstraints2);
			this.add(getJTFStepTau(), gridBagConstraints3);
			this.add(getJTFStepLevel(), gridBagConstraints4);
			this.add(getJTFLevelStart(), gridBagConstraints5);
			this.add(getJTFTauStart(), gridBagConstraints6);
			this.add(getJLabel6(), gridBagConstraints7);
			this.add(getJLabel7(), gridBagConstraints8);
			this.add(getJTFBestLevel1(), gridBagConstraints9);
			this.add(getJTFBestTau1(), gridBagConstraints10);
			this.add(getJLabel91(), gridBagConstraints11);
			this.add(getJRadioButtonAbs(), gridBagConstraints12);
			this.add(getJRadioButtonSqr(), gridBagConstraints13);
			this.add(getJLabel911(), gridBagConstraints14);
			this.add(getJLabel71(), gridBagConstraints15);
			this.add(getJTFResult(), gridBagConstraints16);
			this.add(getJButtonFind(), gridBagConstraints17);
			this.add(getJButtonTest(), gridBagConstraints18);
			this.add(getJCheckBox(), gridBagConstraints19);
			initConnections();
			connEtoM5();
			connEtoM6();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		// user code end
	}

	/**
	 * main entrypoint - starts the part when it is run as an application
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			javax.swing.JFrame frame = new javax.swing.JFrame();
			ParmFinderView aParmFinderView;
			aParmFinderView = new ParmFinderView();
			frame.setContentPane(aParmFinderView);
			frame.setSize(aParmFinderView.getSize());
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			frame.setVisible(true);
			java.awt.Insets insets = frame.getInsets();
			frame.setSize(frame.getWidth() + insets.left + insets.right,
					frame.getHeight() + insets.top + insets.bottom);
			frame.setVisible(true);
		} catch (Throwable exception) {
			System.err
					.println("Exception occurred in main() of javax.swing.JPanel");
			exception.printStackTrace(System.out);
		}
	}

	/**
	 * Method generated to support the promotion of the parmFinderPainter
	 * attribute.
	 * 
	 * @param arg1
	 *            paint.Painter
	 */
	public void setDiagram(Diagram arg1) {
		ivjParmFinder.setPainter(arg1.getPainter());
	}

	public double getTau() {
		return Double.parseDouble(getJTFTauStart().getText());
	}

	public double getStepT() {
		return Double.parseDouble(getJTFStepTau().getText());
	}

	public double getLevel() {
		return Double.parseDouble(getJTFLevelStart().getText());
	}

	public double getStepL() {
		return Double.parseDouble(getJTFStepLevel().getText());
	}

	public boolean isMetodSqr() {
		return getJRadioButtonSqr().isSelected();
	}

	public boolean isCheckProtokol() {
		return getJCheckBox().isSelected();
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setMargin(new Insets(1, 1, 1, 1));
			jCheckBox.setText("Протокол");
		}
		return jCheckBox;
	}

}
