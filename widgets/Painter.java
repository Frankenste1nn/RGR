package widgets;

/**
 * Insert the type's description here. Creation date: (02.05.2005 14:45:03)
 * 
 * @author: Administrator
 */
public class Painter {
	private int x = 0;

	private int y = 0;

	private Diagram diagram;

	private java.awt.Color color;

	private java.awt.image.BufferedImage image;

	private java.awt.Graphics gc;

	private java.awt.Graphics gi;


	/**
	 * Painter constructor comment.
	 */
	public Painter(Diagram newDiagram) {
		this.setDiagram(newDiagram);
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:31:00)
	 * 
	 * @return int
	 * @param floatX
	 *            java.lang.Float
	 */
	public int convertDX(float floatDX) {
		int x = Math.round(floatDX * getDiagram().scaleX());
		if (x < -10000) {
			return -10000;
		}
		if (x > 10000) {
			return 10000;
		}
		return x;
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:31:00)
	 * 
	 * @return int
	 * @param floatY
	 *            java.lang.Float
	 */
	public int convertDY(float floatDY) {
		int y = Math.round(floatDY * getDiagram().scaleY());
		if (y < -10000) {
			return -10000;
		}
		if (y > 10000) {
			return 10000;
		}
		return y;
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:31:00)
	 * 
	 * @return int
	 * @param floatX
	 *            java.lang.Float
	 */
	public int convertX(float floatX) {
		int x = getDiagram().x0() + Math.round(floatX * getDiagram().scaleX());
		if (x < -10000) {
			return -10000;
		}
		if (x > 10000) {
			return 10000;
		}
		return x;
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:31:00)
	 * 
	 * @return int
	 * @param floatY
	 *            java.lang.Float
	 */
	public int convertY(float floatY) {
		int y = getDiagram().y0() - Math.round(floatY * getDiagram().scaleY());
		if (y < -10000) {
			return -10000;
		}
		if (y > 10000) {
			return 10000;
		}
		return y;
	}
	public synchronized void drawNeedleDiagram(double[] values,
			// массив значений по горизонтали (возрастающий)
					double[] frequency, // массив значений по вертикали (в промежутках)
					double width, // ширина иглы в в долях ширины интервала
					boolean reSet) // требуется ли изменение настройки диаграммы
			{
				if (values.length != frequency.length)
					return;
				double dx = (values[values.length - 1] - values[0])
						/ (values.length - 1);
				double xMin = values[0] - dx;
				double xMax = values[values.length - 1] + dx;
				float xxMax = (float) stat.StatTables.roundTo(xMax, 0.1);
				float xxMin = (float) stat.StatTables.roundTo(xMin, 0.1);
				double sh = dx * width / 2;

				// Изменяем настройку диаграммы, если нужно
				if (reSet) {
					// Настраиваем сетку по горизонтали
					getDiagram().setGridByX(values.length + 1);
					// Находим мах по вертикади
					double yyMax = frequency[0];
					for (int i = 0; i < frequency.length; i++)
						if (frequency[i] > yyMax)
							yyMax = frequency[i];
					// Прописываем крайние значения диаграммы
					// float xxMin = (float) stat.StatTables.roundTo(values[0], 0.1);
					getDiagram().setHorizontalMinText(Float.toString(xxMin));
					getDiagram().setHorizontalMaxText(Float.toString(xxMax));
					getDiagram().setVerticalMinText(Double.toString(0.0));
					float vmax = (float) stat.StatTables.roundTo(0.1 + yyMax, 0.1);
					if(yyMax>2)
						vmax = (float) stat.StatTables.roundTo(yyMax, 1);
					getDiagram().setVerticalMaxText(Float.toString(vmax));
				}
				// Отображаем frequency в виде столбчатой диаграммы
				for (int i = 0; i < values.length; i++) {
					fillRectAtXY((float) (values[i] - sh),
							(float) frequency[i], (float) (2 * sh),
							(float) frequency[i]);
				}
			}

	
	public synchronized void drawBarsDiagram(double borders[], // массив границ
			// по
			// горизонтали
			double[] frequency, // массив значений по вертикали (в промежутках)
			double width, // ширина столбика в долях ширины интервала
			double shift, // смещение столбика от левой границы промежутка
			boolean reSet) // требуется ли изменение настройки диаграммы
	{
		if (borders.length - frequency.length != 1)
			return;
		// Изменяем настройку диаграммы, если нужно
		if (reSet) {
			// Настраиваем сетку по горизонтали
			getDiagram().setGridByX(borders.length - 1);
			// Находим мах по осям
			float xxMax = (float) stat.StatTables.roundTo(
					borders[borders.length - 1], 0.01);
			double yyMax = frequency[0];
			for (int i = 0; i < frequency.length; i++)
				if (frequency[i] > yyMax)
					yyMax = frequency[i];
			// Прописываем крайние значения диаграммы
			float xxMin = (float) stat.StatTables.roundTo(borders[0], 0.01);
			getDiagram().setHorizontalMinText(Float.toString(xxMin));
			getDiagram().setHorizontalMaxText(Float.toString(xxMax));
			getDiagram().setVerticalMinText(Double.toString(0.0));
			float vmax = (float) stat.StatTables.roundTo(0.1 + yyMax, 0.1);
			getDiagram().setVerticalMaxText(Float.toString(vmax));
		}
		// Отображаем frequency в виде столбчатой диаграммы
		float xxMax = (float) stat.StatTables.roundTo(
				borders[borders.length - 1], 0.01);
		double xxMin = (float) stat.StatTables.roundTo(borders[0], 0.01);
		double dx = stat.StatTables.roundTo((xxMax - xxMin)
				/ (frequency.length), 0.001);
		double sh = shift * dx;
		for (int i = 0; i < frequency.length; i++) {
			fillRectAtXY((float) (xxMin + dx * i + sh),
					(float) frequency[i], (float) (dx * width),
					(float) frequency[i]);
		}
	}

	public void drawDependency(double x[], double y[], java.awt.Color color,
			boolean reSet) {
		if (x.length != y.length)
			return;
		if (reSet) {
			// Поиск предельных значений в массивах
			double xMin = x[0];
			double xMax = x[0];
			double yMin = y[0];
			double yMax = y[0];
			for (int i = 0; i < x.length; i++) {
				if (x[i] > xMax)
					xMax = x[i];
				else if (x[i] < xMin)
					xMin = x[i];
				if (y[i] > yMax)
					yMax = y[i];
				else if (y[i] < yMin)
					yMin = y[i];
			}
			// Округляем крайние значения
			xMin = stat.StatTables.roundTo(xMin - 0.05, 0.1);
			xMax = stat.StatTables.roundTo(xMax + 0.05, 0.1);
			yMin = stat.StatTables.roundTo(yMin - 0.05, 0.1);
			yMax = stat.StatTables.roundTo(yMax + 0.05, 0.1);
			// Настраиваем диаграмму
			getDiagram().clear();
			getDiagram().setHorizontalMinText(Float.toString((float) xMin));
			getDiagram().setHorizontalMaxText(Float.toString((float) xMax));
			getDiagram().setVerticalMinText(Float.toString((float) yMin));
			getDiagram().setVerticalMaxText(Float.toString((float) yMax));
		}
		/* Вывод графика */
		placeToXY((float) x[0], (float) y[0]);
		setColor(color);
		for (int i = 1; i < x.length; i++) {
			drawToXY((float) x[i], (float) y[i]);
		}
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:03:56)
	 * 
	 * @param newX
	 *            float
	 * @param newY
	 *            float
	 * @param radius
	 *            int
	 */
	public void drawOvalAtXY(float floatX, float floatY, int radiusW,
			int radiusH) {
		updateParameters();
		gi.drawOval(convertX(floatX) - radiusW, convertY(floatY) - radiusH,
				2 * radiusW, 2 * radiusH);
		gc.drawImage(getImage(), 0, 0, null);
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:03:56)
	 * 
	 * @param newX
	 *            float
	 * @param newY
	 *            float
	 * @param radius
	 *            int
	 */
	public void drawRectAtXY(float floatX, float floatY, float floatW,
			float floatH) {
		updateParameters();
		gi.drawRect(convertX(floatX), convertY(floatY), convertDX(floatW),
				convertDY(floatH));
		gc.drawImage(getImage(), 0, 0, null);
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 15:05:57)
	 * 
	 * @param newX
	 *            double
	 * @param newY
	 *            double
	 */
	public void drawToXY(float floatX, float floatY) {
		int lastX = x;
		int lastY = y;
		x = convertX(floatX);
		y = convertY(floatY);
		updateParameters();
		gi.drawLine(lastX, lastY, x, y);
		gc.drawImage(getImage(), 0, 0, null);
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:03:56)
	 * 
	 * @param newX
	 *            float
	 * @param newY
	 *            float
	 * @param radius
	 *            int
	 */
	public void fillOvalAtXY(float floatX, float floatY, int radiusW,
			int radiusH) {
		updateParameters();
		gi.fillOval(convertX(floatX) - radiusW, convertY(floatY) - radiusH,
				2 * radiusW, 2 * radiusH);
		gc.drawImage(getImage(), 0, 0, null);
	}

		/**
		 * @param floatX
		 * @param floatY
		 * @param floatW
		 * @param floatH
		 */
		public void fillRectAtXY(float floatX, float floatY, float floatW,
			float floatH) {
		updateParameters();
		gi.fillRect(convertX(floatX), convertY(floatY), convertDX(floatW),
				convertDY(floatH));

		gc.drawImage(getImage(), 0, 0, null);

	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 16:07:18)
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getColor() {
		return color;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 15:02:11)
	 * 
	 * @return pnt.Diagram
	 */
	public Diagram getDiagram() {
		if (diagram == null)
			System.out.println("Не визначено Diagram для Painter");
		return diagram;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 20:25:21)
	 * 
	 * @return java.awt.Graphics
	 */
	public java.awt.Graphics getGc() {
		return gc;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 20:24:53)
	 * 
	 * @return java.awt.image.BufferedImage
	 */
	public java.awt.image.BufferedImage getImage() {
		return image;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:45:53)
	 * 
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:46:15)
	 * 
	 * @return int
	 */
	public int getY() {
		return y;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 15:05:57)
	 * 
	 * @param newX
	 *            double
	 * @param newY
	 *            double
	 */
	public void placeToXY(float floatX, float floatY) {
		x = convertX(floatX);
		y = convertY(floatY);
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 16:07:18)
	 * 
	 * @param newColor
	 *            java.awt.Color
	 */
	public void setColor(java.awt.Color newColor) {
		color = newColor;
		gi.setColor(color);

	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 15:02:11)
	 * 
	 * @param newDiagram
	 *            pnt.Diagram
	 */
	public void setDiagram(Diagram newDiagram) {
		diagram = newDiagram;
		image = diagram.getImage();
		gc = diagram.getGraphics();
		gi = image.getGraphics();
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 20:25:21)
	 * 
	 * @param newGc
	 *            java.awt.Graphics
	 */
	public void setGc(java.awt.Graphics newGc) {
		gc = newGc;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 20:24:53)
	 * 
	 * @param newImage
	 *            java.awt.image.BufferedImage
	 */
	public void setImage(java.awt.image.BufferedImage newImage) {
		image = newImage;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:45:53)
	 * 
	 * @param newX
	 *            int
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * Insert the method's description here. Creation date: (02.05.2005
	 * 14:46:15)
	 * 
	 * @param newY
	 *            int
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * Insert the method's description here. Creation date: (05.05.2005
	 * 20:11:59)
	 */
	private void updateParameters() {
		image = getDiagram().getImage();
		gc = getDiagram().getPanelGraphics();
		gi = image.getGraphics();
		gi.setColor(getColor());
	}
}
