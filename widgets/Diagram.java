package widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;


/**
 * Insert the type's description here. Creation date: (04.04.2005 19:52:36)
 * 
 * @author: Administrator
 */

public class Diagram extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IvjEventHandler ivjEventHandler = new IvjEventHandler();

	private DiagramPanel ivjDiagramPanel = null;

	private javax.swing.JTextField ivjHorizontalMax = null;

	private javax.swing.JTextField ivjHorizontalMin = null;

	private Painter ivjPainter = null;

	private javax.swing.JTextField ivjVerticalMax = null;

	private javax.swing.JTextField ivjVerticalMin = null;

	private javax.swing.JLabel ivjTitle = null;

	private javax.swing.JPopupMenu ivjJPopupMenu1 = null;

	private javax.swing.JMenuItem ivjJMenuItem1 = null;
	


	protected transient DiagramListener fieldDiagramListenerEventMulticaster = null;

	class IvjEventHandler implements java.awt.event.ActionListener,
			java.awt.event.ComponentListener, java.awt.event.MouseListener,
			java.beans.PropertyChangeListener, javax.swing.event.CaretListener {
		
		// обработчик события по клику в контекстном меню "очистить"
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == Diagram.this.getJMenuItem1())
				Diagram.this.clear();
		};

		public void caretUpdate(javax.swing.event.CaretEvent e) {
			if (e.getSource() == Diagram.this.getVerticalMax()) {
				getDiagramPanel().clear();
				Diagram.this
						.fireVerticalMaxCaret_caretUpdate(new java.util.EventObject(
								this));
				Diagram.this
						.fireVerticalMaxCaret_caretUpdate(new java.util.EventObject(
								this));
			}
			if (e.getSource() == Diagram.this.getVerticalMin()) {
				getDiagramPanel().clear();
			}
			if (e.getSource() == Diagram.this.getHorizontalMin()) {
				getDiagramPanel().clear();
			}
			if (e.getSource() == Diagram.this.getHorizontalMax()) {
				getDiagramPanel().clear();
			}
		};

		public void componentHidden(java.awt.event.ComponentEvent e) {
		};

		public void componentMoved(java.awt.event.ComponentEvent e) {
		};

		public void componentResized(java.awt.event.ComponentEvent e) {
			if (e.getSource() == Diagram.this) {
				getDiagramPanel().drawGrid();
			}
		};

		public void componentShown(java.awt.event.ComponentEvent e) {
		};

		public void mouseClicked(java.awt.event.MouseEvent e) {
		};

		public void mouseEntered(java.awt.event.MouseEvent e) {
		};

		public void mouseExited(java.awt.event.MouseEvent e) {
		};

		/*
		 * (non-Javadoc)
		 *  Отображение контекстного меню по клику мышки
		 */
		public void mousePressed(java.awt.event.MouseEvent e) {
			if (e.getSource() == Diagram.this.getDiagramPanel()) {
				// отображает меню по клику на диаграмме 
				getJPopupMenu1().show(getDiagramPanel(), 50, 40);
			}
		};

		public void mouseReleased(java.awt.event.MouseEvent e) {
		};

		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			if (evt.getSource() == Diagram.this.getTitle()
					&& (evt.getPropertyName().equals("text"))) {
				Diagram.this.firePropertyChange("JLabelTitleText", evt
						.getOldValue(), evt.getNewValue());
			}
			if (evt.getSource() == Diagram.this.getDiagramPanel()
					&& (evt.getPropertyName().equals("image"))) {
				Diagram.this.firePropertyChange("JPanelDiagramImage", evt
						.getOldValue(), evt.getNewValue());
			}
			if (evt.getSource() == Diagram.this.getDiagramPanel()
					&& (evt.getPropertyName().equals("gridColor"))) {
				Diagram.this.firePropertyChange("JPanelDiagramGridColor", evt
						.getOldValue(), evt.getNewValue());
			}
			if (evt.getSource() == Diagram.this.getDiagramPanel()
					&& (evt.getPropertyName().equals("background"))) {
				Diagram.this.firePropertyChange("JPanelDiagramBackground", evt
						.getOldValue(), evt.getNewValue());
				Diagram.this.firePropertyChange("panelBackground", evt
						.getOldValue(), evt.getNewValue());
			}
			if (evt.getSource() == Diagram.this.getDiagramPanel()
					&& (evt.getPropertyName().equals("gridByY"))) {
				Diagram.this.firePropertyChange("JPanelDiagramGridByY", evt
						.getOldValue(), evt.getNewValue());
			}
			if (evt.getSource() == Diagram.this.getDiagramPanel()
					&& (evt.getPropertyName().equals("gridByX"))) {
				Diagram.this.firePropertyChange("JPanelDiagramGridByX", evt
						.getOldValue(), evt.getNewValue());
			}
		};
	};

	/**
	 * Diagram constructor comment.
	 */
	public Diagram() {
		super();
		initialize();
	}

	/**
	 * Diagram constructor comment.
	 * 
	 * @param layout
	 *            java.awt.LayoutManager
	 */
	public Diagram(java.awt.LayoutManager layout) {
		super(layout);
	}

	/**
	 * Diagram constructor comment.
	 * 
	 * @param layout
	 *            java.awt.LayoutManager
	 * @param isDoubleBuffered
	 *            boolean
	 */
	public Diagram(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * Diagram constructor comment.
	 * 
	 * @param isDoubleBuffered
	 *            boolean
	 */
	public Diagram(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	/**
	 * 
	 * @param newListener
	 *            paint.DiagramListener
	 */
	public void addDiagramListener(DiagramListener newListener) {
		fieldDiagramListenerEventMulticaster = DiagramListenerEventMulticaster
				.add(fieldDiagramListenerEventMulticaster, newListener);
		return;
	}

	/**
	 * Comment
	 */

	public void changeLocation() {
		int width = getWidth();
		int height = getHeight();
		int titleHeight = getTitle().getHeight();
		getTitle().setLocation(2, 3);
		getTitle().setSize(width - 4, titleHeight);
		int textWidth = getHorizontalMin().getWidth();
		int textHeight = getHorizontalMin().getHeight();
		getHorizontalMin().setLocation(textWidth + 1, height - textHeight - 3);
		getHorizontalMax().setLocation(width - textWidth - 2,
				height - textHeight - 3);
		getVerticalMin().setLocation(2, height - 2 * textHeight - 5);
		getVerticalMax().setLocation(2, titleHeight + 3);
		getDiagramPanel().setBounds(textWidth + 3, titleHeight + 5,
				width - textWidth - 9, height - textHeight - titleHeight - 9);
		// getDiagramPanel().setImage(new
		// BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB));
		// getDiagramPanel().drawGrid();
		return;
	}

	/**
	 * 
	 */
	public void clear() {
try {
	getDiagramPanel().clear();
	getPainter().placeToXY(0, 0);
	
} catch (Exception e) {
	// TODO: handle exception
}
	}

	/**
	 * Comment
	 */
	public void diagram_Initialize() {
		getDiagramPanel().paint(getDiagramPanel().getGraphics());
		return;
	}

	/**
	 * Insert the method's description here. Creation date: (18.01.2006
	 * 12:57:41)
	 */
	public synchronized void drawBarsDiagram(double borders[], // массив границ
																// по
																// горизонтали
			double[] frequency, // массив значений по вертикали (в промежутках)
			double width, // ширина столбика в долях ширины интервала
			double shift, // смещение столбика от левой границы промежутка
			boolean reSet) // требуется ли изменение настройки диаграммы
	
	{
		
		getPainter().drawBarsDiagram(borders, frequency, width, shift, reSet);
	
	}

	/**
	 * Вывод графической зависимости между последовательностями Creation date:
	 * (05.11.2005 23:18:02)
	 * 
	 * @param anInt
	 *            int
	 */
	public void drawDependency(double[] x, double[] y, java.awt.Color color,
			boolean reSet) {
		getPainter().drawDependency(x,  y, color,
				 reSet);
}

	/**
	 * Insert the method's description here. Creation date: (18.01.2006
	 * 12:57:41)
	 */
	public synchronized void drawNeedleDiagram(double[] values,
	// массив значений по горизонтали (возрастающий)
			double[] frequency, // массив значений по вертикали (в промежутках)
			double width, // ширина иглы в в долях ширины интервала
			boolean reSet) // требуется ли изменение настройки диаграммы
	{getPainter().drawNeedleDiagram(values, frequency, width, reSet);

	}

	/**
	 * Method to support listener events.
	 * 
	 * @param newEvent
	 *            java.util.EventObject
	 */
	protected void fireVerticalMaxCaret_caretUpdate(
			java.util.EventObject newEvent) {
		if (fieldDiagramListenerEventMulticaster == null) {
			return;
		}
		;
		fieldDiagramListenerEventMulticaster
				.verticalMaxCaret_caretUpdate(newEvent);
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramBackground
	 * attribute.
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getDiagramBackground() {
		return getDiagramPanel().getBackground();
	}

	// import paint.DiagramPanel
	/**
	 * Return the JPanelDiagram property value.
	 * 
	 * @return paint.DiagramPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private DiagramPanel getDiagramPanel() {
		if (ivjDiagramPanel == null) {
			try {
				ivjDiagramPanel = new DiagramPanel();
				ivjDiagramPanel.setName("DiagramPanel");
				ivjDiagramPanel
						.setBorder(new javax.swing.border.EtchedBorder());
				ivjDiagramPanel
						.setBackground(new java.awt.Color(211, 248, 248));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjDiagramPanel;
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridByX
	 * attribute.
	 * 
	 * @return int
	 */
	public int getGridByX() {
		return getDiagramPanel().getGridByX();
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridByY
	 * attribute.
	 * 
	 * @return int
	 */
	public int getGridByY() {
		return getDiagramPanel().getGridByY();
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridColor
	 * attribute.
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getGridColor() {
		return getDiagramPanel().getGridColor();
	}

	/**
	 * Return the JTextField11 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getHorizontalMax() {
		if (ivjHorizontalMax == null) {
			try {
				ivjHorizontalMax = new javax.swing.JTextField();
				ivjHorizontalMax.setName("HorizontalMax");
				ivjHorizontalMax.setText("5");
				ivjHorizontalMax
						.setPreferredSize(new java.awt.Dimension(65, 18));
				ivjHorizontalMax
						.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				ivjHorizontalMax.setMinimumSize(new java.awt.Dimension(35, 18));
				ivjHorizontalMax
						.setHorizontalAlignment(javax.swing.JTextField.LEFT);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjHorizontalMax;
	}

	/**
	 * Method generated to support the promotion of the horizontalMaxEnabled
	 * attribute.
	 * 
	 * @return boolean
	 */
	public boolean getHorizontalMaxEnabled() {
		return getHorizontalMax().isEnabled();
	}

	/**
	 * Method generated to support the promotion of the horizontalMaxText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getHorizontalMaxText() {
		return getHorizontalMax().getText();
	}

	/**
	 * Return the JTextField12 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getHorizontalMin() {
		if (ivjHorizontalMin == null) {
			try {
				ivjHorizontalMin = new javax.swing.JTextField();
				ivjHorizontalMin.setName("HorizontalMin");
				ivjHorizontalMin.setText("0");
				ivjHorizontalMin
						.setPreferredSize(new java.awt.Dimension(55, 18));
				ivjHorizontalMin
						.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				ivjHorizontalMin.setMinimumSize(new java.awt.Dimension(35, 18));
				ivjHorizontalMin
						.setHorizontalAlignment(javax.swing.JTextField.LEFT);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjHorizontalMin;
	}

	/**
	 * Method generated to support the promotion of the horizontalMinEnabled
	 * attribute.
	 * 
	 * @return boolean
	 */
	public boolean getHorizontalMinEnabled() {
		return getHorizontalMin().isEnabled();
	}

	/**
	 * Method generated to support the promotion of the horizontalMinText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getHorizontalMinText() {
		return getHorizontalMin().getText();
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramImage
	 * attribute.
	 * 
	 * @return java.awt.image.BufferedImage
	 */
	public java.awt.image.BufferedImage getImage() {
		return getDiagramPanel().getImage();
	}

	/**
	 * Return the JMenuItem1 property value.
	 * 
	 * @return javax.swing.JMenuItem
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JMenuItem getJMenuItem1() {
		if (ivjJMenuItem1 == null) {
			try {
				ivjJMenuItem1 = new javax.swing.JMenuItem();
				ivjJMenuItem1.setName("JMenuItem1");
				ivjJMenuItem1
						.setBorder(new javax.swing.border.CompoundBorder());
				ivjJMenuItem1.setText("Clear");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJMenuItem1;
	}
///////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Return the JPopupMenu1 property value.
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public javax.swing.JPopupMenu getJPopupMenu1() {
		if (ivjJPopupMenu1 == null) {
			try {
				ivjJPopupMenu1 = new javax.swing.JPopupMenu();
				ivjJPopupMenu1.setName("JPopupMenu1");
				ivjJPopupMenu1.add(getJMenuItem1());

				// добавляем в меню пункт

			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJPopupMenu1;
	}

	/**
	 * Return the DiagramPainter property value.
	 * 
	 * @return pnt.Painter
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public Painter getPainter() {
		if (ivjPainter == null) {
			try {
				ivjPainter = new widgets.Painter(this);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				System.out.println("??????? no Painter");
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPainter;
	}

	/**
	 * Method generated to support the promotion of the diagramPainterColor
	 * attribute.
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getPainterColor() {
		return getPainter().getColor();
	}

	/**
	 * Method generated to support the promotion of the diagramPainterDiagram
	 * attribute.
	 * 
	 * @return pnt.Diagram
	 */
	public Diagram getPainterDiagram() {
		return getPainter().getDiagram();
	}

	/**
	 * Method generated to support the promotion of the panelBackground
	 * attribute.
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getPanelBackground() {
		return getDiagramPanel().getBackground();
	}

	/**
	 * Return the JLabelTitle property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JLabel getTitle() {
		if (ivjTitle == null) {
			try {
				ivjTitle = new javax.swing.JLabel();
				ivjTitle.setName("Title");
				ivjTitle.setText("Title");
				ivjTitle
						.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjTitle;
	}

	/**
	 * Method generated to support the promotion of the JLabelTitleText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getTitleText() {
		return getTitle().getText();
	}

	/**
	 * Return the JTextField13 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getVerticalMax() {
		if (ivjVerticalMax == null) {
			try {
				ivjVerticalMax = new javax.swing.JTextField();
				ivjVerticalMax.setName("VerticalMax");
				ivjVerticalMax.setText("5");
				ivjVerticalMax.setPreferredSize(new java.awt.Dimension(30, 18));
				ivjVerticalMax
						.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				ivjVerticalMax.setMinimumSize(new java.awt.Dimension(30, 18));
				ivjVerticalMax
						.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjVerticalMax;
	}

	/**
	 * Method generated to support the promotion of the verticalMaxEnabled
	 * attribute.
	 * 
	 * @return boolean
	 */
	public boolean getVerticalMaxEnabled() {
		return getVerticalMax().isEnabled();
	}

	/**
	 * Method generated to support the promotion of the verticalMaxText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getVerticalMaxText() {
		return getVerticalMax().getText();
	}

	/**
	 * Return the JTextField1 property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTextField getVerticalMin() {
		if (ivjVerticalMin == null) {
			try {
				ivjVerticalMin = new javax.swing.JTextField();
				ivjVerticalMin.setName("VerticalMin");
				ivjVerticalMin.setText("0");
				ivjVerticalMin.setPreferredSize(new java.awt.Dimension(30, 18));
				ivjVerticalMin
						.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				ivjVerticalMin.setMinimumSize(new java.awt.Dimension(30, 18));
				ivjVerticalMin
						.setHorizontalAlignment(javax.swing.JTextField.LEFT);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjVerticalMin;
	}

	/**
	 * Method generated to support the promotion of the verticalMinEnabled
	 * attribute.
	 * 
	 * @return boolean
	 */
	public boolean getVerticalMinEnabled() {
		return getVerticalMin().isEnabled();
	}

	/**
	 * Method generated to support the promotion of the verticalMinText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getVerticalMinText() {
		return getVerticalMin().getText();
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
		getTitle().addPropertyChangeListener(ivjEventHandler);
		getDiagramPanel().addPropertyChangeListener(ivjEventHandler);
		this.addComponentListener(ivjEventHandler);
		getVerticalMax().addCaretListener(ivjEventHandler);
		getVerticalMin().addCaretListener(ivjEventHandler);
		getHorizontalMin().addCaretListener(ivjEventHandler);
		getHorizontalMax().addCaretListener(ivjEventHandler);
		getJMenuItem1().addActionListener(ivjEventHandler);
		getDiagramPanel().addMouseListener(ivjEventHandler);
		getPainter().setDiagram(this);
	}

	/**
	 * Initialize the class.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("Diagram");
			setBorder(new javax.swing.border.EtchedBorder());
			setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
			setLayout(new java.awt.GridBagLayout());
			setSize(458, 136);

			java.awt.GridBagConstraints constraintsVerticalMin = new java.awt.GridBagConstraints();
			constraintsVerticalMin.gridx = 0;
			constraintsVerticalMin.gridy = 2;
			constraintsVerticalMin.anchor = java.awt.GridBagConstraints.SOUTH;
			constraintsVerticalMin.insets = new java.awt.Insets(1, 2, 1, 1);
			add(getVerticalMin(), constraintsVerticalMin);

			java.awt.GridBagConstraints constraintsHorizontalMax = new java.awt.GridBagConstraints();
			constraintsHorizontalMax.gridx = 2;
			constraintsHorizontalMax.gridy = 3;
			constraintsHorizontalMax.anchor = java.awt.GridBagConstraints.EAST;
			constraintsHorizontalMax.insets = new java.awt.Insets(1, 1, 2, 5);
			add(getHorizontalMax(), constraintsHorizontalMax);

			java.awt.GridBagConstraints constraintsHorizontalMin = new java.awt.GridBagConstraints();
			constraintsHorizontalMin.gridx = 1;
			constraintsHorizontalMin.gridy = 3;
			constraintsHorizontalMin.insets = new java.awt.Insets(1, -15, 2, 1);
			add(getHorizontalMin(), constraintsHorizontalMin);

			java.awt.GridBagConstraints constraintsVerticalMax = new java.awt.GridBagConstraints();
			constraintsVerticalMax.gridx = 0;
			constraintsVerticalMax.gridy = 1;
			constraintsVerticalMax.insets = new java.awt.Insets(1, 2, 1, 1);
			add(getVerticalMax(), constraintsVerticalMax);

			java.awt.GridBagConstraints constraintsDiagramPanel = new java.awt.GridBagConstraints();
			constraintsDiagramPanel.gridx = 1;
			constraintsDiagramPanel.gridy = 1;
			constraintsDiagramPanel.gridwidth = 2;
			constraintsDiagramPanel.gridheight = 2;
			constraintsDiagramPanel.fill = java.awt.GridBagConstraints.BOTH;
			constraintsDiagramPanel.weightx = 1.0;
			constraintsDiagramPanel.weighty = 1.0;
			constraintsDiagramPanel.ipadx = 193;
			constraintsDiagramPanel.ipady = 130;
			constraintsDiagramPanel.insets = new java.awt.Insets(1, 1, 1, 15);
			add(getDiagramPanel(), constraintsDiagramPanel);

			java.awt.GridBagConstraints constraintsTitle = new java.awt.GridBagConstraints();
			constraintsTitle.gridx = 1;
			constraintsTitle.gridy = 0;
			constraintsTitle.gridwidth = 2;
			constraintsTitle.fill = java.awt.GridBagConstraints.HORIZONTAL;
			constraintsTitle.insets = new java.awt.Insets(4, 4, 4, 4);
			add(getTitle(), constraintsTitle);
			initConnections();
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
			Diagram aDiagram;
			aDiagram = new Diagram();
			frame.setContentPane(aDiagram);
			frame.setSize(aDiagram.getSize());
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			frame.setVisible(true);
			java.awt.Insets insets = frame.getInsets();
			frame.setSize(frame.getWidth() + insets.left + insets.right, frame
					.getHeight()
					+ insets.top + insets.bottom);
			frame.setVisible(true);
		} catch (Throwable exception) {
			System.err
					.println("Exception occurred in main() of javax.swing.JPanel");
			exception.printStackTrace(System.out);
		}
	}

	/**
	 * 
	 * @param newListener
	 *            paint.DiagramListener
	 */
	public void removeDiagramListener(DiagramListener newListener) {
		fieldDiagramListenerEventMulticaster = DiagramListenerEventMulticaster
				.remove(fieldDiagramListenerEventMulticaster, newListener);
		return;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 12:54:33)
	 * 
	 * @return double
	 */
	public float scaleX() {
		float xMax = Float.parseFloat(getHorizontalMax().getText());
		float xMin = Float.parseFloat(getHorizontalMin().getText());
		float d = xMax - xMin;
		int w = getDiagramPanel().getWidth();
		return w / d;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 12:54:33)
	 * 
	 * @return double
	 */
	public float scaleY() {
		float yMax = Float.parseFloat(getVerticalMax().getText());
		float yMin = Float.parseFloat(getVerticalMin().getText());
		float d = yMax - yMin;
		int h = getDiagramPanel().getHeight();
		return h / d;
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramBackground
	 * attribute.
	 * 
	 * @param arg1
	 *            java.awt.Color
	 */
	public void setBackground(java.awt.Color arg1) {
		getDiagramPanel().setBackground(arg1);
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridByX
	 * attribute.
	 * 
	 * @param arg1
	 *            int
	 */
	public void setGridByX(int arg1) {
		getDiagramPanel().setGridByX(arg1);
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridByY
	 * attribute.
	 * 
	 * @param arg1
	 *            int
	 */
	public void setGridByY(int arg1) {
		getDiagramPanel().setGridByY(arg1);
	}

	/**
	 * Method generated to support the promotion of the JPanelDiagramGridColor
	 * attribute.
	 * 
	 * @param arg1
	 *            java.awt.Color
	 */
	public void setGridColor(java.awt.Color arg1) {
		getDiagramPanel().setGridColor(arg1);
	}

	/**
	 * Method generated to support the promotion of the horizontalMaxEnabled
	 * attribute.
	 * 
	 * @param arg1
	 *            boolean
	 */
	public void setHorizontalMaxEnabled(boolean arg1) {
		getHorizontalMax().setEnabled(arg1);
	}

	/**
	 * Method generated to support the promotion of the horizontalMaxText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setHorizontalMaxText(java.lang.String arg1) {

			getHorizontalMax().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the horizontalMinEnabled
	 * attribute.
	 * 
	 * @param arg1
	 *            boolean
	 */
	public void setHorizontalMinEnabled(boolean arg1) {
		getHorizontalMin().setEnabled(arg1);
	}

	/**
	 * Method generated to support the promotion of the horizontalMinText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setHorizontalMinText(java.lang.String arg1) {
		getHorizontalMin().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the diagramPainterColor
	 * attribute.
	 * 
	 * @param arg1
	 *            java.awt.Color
	 */
	public void setPainterColor(java.awt.Color arg1) {
		getPainter().setColor(arg1);
	}

	/**
	 * Method generated to support the promotion of the diagramPainterDiagram
	 * attribute.
	 * 
	 * @param arg1
	 *            pnt.Diagram
	 */
//	public void setPainterDiagram(Diagram arg1) {
//		getPainter().setDiagram(arg1);
//	}

	/**
	 * Method generated to support the promotion of the panelBackground
	 * attribute.
	 * 
	 * @param arg1
	 *            java.awt.Color
	 */
	public void setPanelBackground(java.awt.Color arg1) {
		getDiagramPanel().setBackground(arg1);
	}

	/**
	 * Method generated to support the promotion of the JLabelTitleText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setTitleText(java.lang.String arg1) {
		getTitle().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the verticalMaxEnabled
	 * attribute.
	 * 
	 * @param arg1
	 *            boolean
	 */
	public void setVerticalMaxEnabled(boolean arg1) {
		getVerticalMax().setEnabled(arg1);
	}

	/**
	 * Method generated to support the promotion of the verticalMaxText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setVerticalMaxText(java.lang.String arg1) {
		getVerticalMax().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the verticalMinEnabled
	 * attribute.
	 * 
	 * @param arg1
	 *            boolean
	 */
	public void setVerticalMinEnabled(boolean arg1) {
		getVerticalMin().setEnabled(arg1);
	}

	/**
	 * Method generated to support the promotion of the verticalMinText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void setVerticalMinText(java.lang.String arg1) {
		getVerticalMin().setText(arg1);
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:28:07)
	 * 
	 * @return int
	 */
	public int x0() {
		float xMax = Float.parseFloat(getHorizontalMax().getText());
		float xMin = Float.parseFloat(getHorizontalMin().getText());
		float d = -xMin / (xMax - xMin);
		int w = getDiagramPanel().getWidth();
		return Math.round(w * d) - 1;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:28:07)
	 * 
	 * @return int
	 */
	public int y0() {
		float yMax = Float.parseFloat(getVerticalMax().getText());
		float yMin = Float.parseFloat(getVerticalMin().getText());
		float d = yMax / (yMax - yMin);
		int h = getDiagramPanel().getHeight();
		return Math.round(h * d) - 1;
	}
	
	
	
	public Graphics getPanelGraphics(){
		return getDiagramPanel().getGraphics();
	}
	 class DiagramPanel extends javax.swing.JPanel {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private java.awt.image.BufferedImage image;

			private int gridByX = 0;

			private int gridByY = 0;

			private java.awt.Color gridColor = java.awt.Color.darkGray;

			/**
			 * DiagramPanel constructor comment.
			 */
			public DiagramPanel() {
				super();
				initialize();
			}

			/**
			 * DiagramPanel constructor comment.
			 * 
			 * @param layout
			 *            java.awt.LayoutManager
			 */
			public DiagramPanel(java.awt.LayoutManager layout) {
				super(layout);
			}

			/**
			 * DiagramPanel constructor comment.
			 * 
			 * @param layout
			 *            java.awt.LayoutManager
			 * @param isDoubleBuffered
			 *            boolean
			 */
			public DiagramPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
				super(layout, isDoubleBuffered);
			}

			/**
			 * DiagramPanel constructor comment.
			 * 
			 * @param isDoubleBuffered
			 *            boolean
			 */
			public DiagramPanel(boolean isDoubleBuffered) {
				super(isDoubleBuffered);
			}

			/**
			 * Insert the method's description here. Creation date: (04.05.2005
			 * 21:29:44)
			 */
			public void clear() {
				drawGrid();
				try {
					getGraphics().drawImage(image, 0, 0, this);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}

			/**
			 * Insert the method's description here. Creation date: (12.04.2005
			 * 23:40:12)
			 */

			public void drawGrid() {
				int w = getWidth();
				int h = getHeight();

				Graphics gc = image.getGraphics();
				gc.setColor(getBackground());
				gc.fillRect(0, 0, w, h);
				gc.setColor(getGridColor());

				for (int i = 1; i < getGridByX(); i++) {
					int x = Math.round((w * i) / getGridByX());
					gc.drawLine(x, 0, x, h); // Vertical Lines
				}

				for (int i = 1; i < getGridByY(); i++) {
					int y = Math.round((h * i) / getGridByY());
					gc.drawLine(0, y, w, y); // Horizontal Lines
				}

				int[] ax = { 0, w - 1, w - 1, 0 };
				int[] ay = { 0, 0,     h - 1, h - 1 };
				gc.setColor(Color.darkGray);
				gc.drawPolygon(ax, ay, 4);
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:27:46)
			 * 
			 * @return int
			 */
			public int getGridByX() {
				if (gridByX < 1) {
					return 5;
				}
				return gridByX;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:28:16)
			 * 
			 * @return int
			 */
			public int getGridByY() {
				if (gridByY < 1) {
					return 5;
				}
				return gridByY;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:32:09)
			 * 
			 * @return java.awt.Color
			 */
			public java.awt.Color getGridColor() {
				return gridColor;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 12:25:08)
			 * 
			 * @return java.awt.image.BufferedImage
			 */
			public java.awt.image.BufferedImage getImage() {
				return image;
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
			 * Initialize the class.
			 */
			/* WARNING: THIS METHOD WILL BE REGENERATED. */
			private void initialize() {
				try {
					// user code begin {1}
					// user code end
					setName("DiagramPanel");
					setBorder(new javax.swing.border.EtchedBorder());
					setLayout(null);
					setBackground(new java.awt.Color(196, 239, 251));
					setSize(160, 120);
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


			/**
			 * Insert the method's description here. Creation date: (12.04.2005
			 * 23:38:54)
			 * 
			 * @param gg
			 *            java.awt.Graphics
			 */
			public void paint(java.awt.Graphics gc) {
				super.paint(gc);
				if (image != null) {
					gc.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
				}

			}

			/**
			 * Insert the method's description here. Creation date: (12.04.2005
			 * 23:40:12)
			 */

			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width, height);
				setImage(new BufferedImage(getWidth(), getHeight(),
						BufferedImage.TYPE_INT_RGB));
				drawGrid();
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:27:46)
			 * 
			 * @param newGridByX
			 *            int
			 */
			public void setGridByX(int newGridByX) {
				gridByX = newGridByX;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:28:16)
			 * 
			 * @param newGridByY
			 *            int
			 */
			public void setGridByY(int newGridByY) {
				gridByY = newGridByY;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 15:32:09)
			 * 
			 * @param newGridColor
			 *            java.awt.Color
			 */
			public void setGridColor(java.awt.Color newGridColor) {
				gridColor = newGridColor;
			}

			/**
			 * Insert the method's description here. Creation date: (01.05.2005
			 * 12:25:08)
			 * 
			 * @return java.awt.image.BufferedImage
			 */
			public void setImage(java.awt.image.BufferedImage newImage) {
				image = newImage;
			}
		}
	 static class DiagramListenerEventMulticaster extends
		java.awt.AWTEventMulticaster implements DiagramListener {
	/**
	 * Constructor to support multicast events.
	 * 
	 * @param a
	 *            java.util.EventListener
	 * @param b
	 *            java.util.EventListener
	 */
	protected DiagramListenerEventMulticaster(java.util.EventListener a,
			java.util.EventListener b) {
		super(a, b);
	}

	/**
	 * Add new listener to support multicast events.
	 * 
	 * @return paint.DiagramListener
	 * @param a
	 *            paint.DiagramListener
	 * @param b
	 *            paint.DiagramListener
	 */
	public static DiagramListener add(DiagramListener a,
			DiagramListener b) {
		return (DiagramListener) addInternal(a, b);
	}

	/**
	 * Add new listener to support multicast events.
	 * 
	 * @return java.util.EventListener
	 * @param a
	 *            java.util.EventListener
	 * @param b
	 *            java.util.EventListener
	 */
	protected static java.util.EventListener addInternal(
			java.util.EventListener a, java.util.EventListener b) {
		if (a == null)
			return b;
		if (b == null)
			return a;
		return new DiagramListenerEventMulticaster(a, b);
	}

	/**
	 * 
	 * @return java.util.EventListener
	 * @param oldl
	 *            paint.DiagramListener
	 */
	protected java.util.EventListener remove(DiagramListener oldl) {
		if (oldl == a)
			return b;
		if (oldl == b)
			return a;
		java.util.EventListener a2 = removeInternal(a, oldl);
		java.util.EventListener b2 = removeInternal(b, oldl);
		if (a2 == a && b2 == b)
			return this;
		return addInternal(a2, b2);
	}

	/**
	 * Remove listener to support multicast events.
	 * 
	 * @return paint.DiagramListener
	 * @param l
	 *            paint.DiagramListener
	 * @param oldl
	 *            paint.DiagramListener
	 */
	public static DiagramListener remove(DiagramListener l,
			DiagramListener oldl) {
		if (l == oldl || l == null)
			return null;
		if (l instanceof DiagramListenerEventMulticaster)
			return (DiagramListener) ((DiagramListenerEventMulticaster) l)
					.remove(oldl);
		return l;
	}

	/**
	 * 
	 * @param newEvent
	 *            java.util.EventObject
	 */
	public void verticalMaxCaret_caretUpdate(java.util.EventObject newEvent) {
		((DiagramListener) a).verticalMaxCaret_caretUpdate(newEvent);
		((DiagramListener) b).verticalMaxCaret_caretUpdate(newEvent);
	}
}
	 interface DiagramListener extends java.util.EventListener {
			/**
			 * 
			 * @param newEvent
			 *            java.util.EventObject
			 */
			void verticalMaxCaret_caretUpdate(java.util.EventObject newEvent);
		}

}
