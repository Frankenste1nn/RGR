package widgets.regres;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import widgets.Diagram;

public class TestForRegresAnaliser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private JPanel jContentPane = null;
	private RegresAnaliser regresAnaliser = null;
	private Diagram diagram = null;
	private IRegresable sourceData = new IRegresable() {
		@Override
		public Map<Double, List<Double>> getResultMap() {
			double a = 3;
			double b = 2;
			
			double[] mas = { 1.0, 2.0, 3.0, 4.0};
			HashMap<Double, List<Double>> resMap = new HashMap<>();
			for (int i = 0; i < mas.length; i++) {
				ArrayList<Double> resList = new ArrayList<>(5);
				resList.add(a / mas[i] + b);
				resList.add(a /  mas[i] + b + Math.random());
				resList.add(a /  mas[i] + b + Math.random());
				resList.add(a /  mas[i] + b - Math.random());
				resList.add(a /  mas[i] + b - Math.random());
				resMap.put(mas[i], resList);
			}
			return resMap;
		};
	};

	/**
	 * This method initializes regresAnaliser
	 * 
	 * @return experiment.controls.RegresAnaliser
	 */
	private RegresAnaliser getRegresAnaliser() {
		if (regresAnaliser == null) {
			regresAnaliser = new RegresAnaliser();
			regresAnaliser.setIRegresable(sourceData);
			regresAnaliser.setDiagram(getDiagram());
		}
		return regresAnaliser;
	}

	/**
	 * This method initializes diagram
	 * 
	 * @return paint.Diagram
	 */
	private Diagram getDiagram() {
		if (diagram == null) {
			diagram = new Diagram();
			diagram.setTitleText("Графік гіперболи y= 3/х+2");
			diagram.setVerticalMaxText("10");
		}
		return diagram;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestForRegresAnaliser application = new TestForRegresAnaliser();
		application.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public TestForRegresAnaliser() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(635, 282);
		this.setContentPane(getJContentPane());
		this.setTitle("Application");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getRegresAnaliser(), null);
			jContentPane.add(getDiagram(), null);
		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
