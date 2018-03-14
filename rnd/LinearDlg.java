package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class LinearDlg extends RandomDlg {
	private ChooseDataH chooseX;
	private ChooseDataH chooseF;
	public LinearDlg() {
		setTitle("\u0414\u043E\u0432\u0456\u043B\u044C\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(2, 1, 0, 0));
		setBounds(100, 100, 327, 166);//TODO
		chooseX = new ChooseDataH();
		chooseX.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseX.setText("0 1 2 3");
		chooseX.setTitle("\u0417\u043D\u0430\u0447\u0435\u043D\u043D\u044F \u0437\u043C\u0456\u043D\u043D\u043E\u0457 \u0443 \u0432\u0443\u0437\u043B\u0430\u0445");
		getContentPanel().add(chooseX);
		
		chooseF = new ChooseDataH();
		chooseF.setResizeWeight(0.3);
		chooseF.setContinuousLayout(true);
		chooseF.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseF.setText("0 0.2 0.8 1");
		chooseF.setTitle("\u0406\u043D\u0442\u0435\u0433\u0440\u0430\u043B\u044C\u043D\u0430 \u0444\u0443\u043D\u043A\u0446\u0456\u044F \u0443 \u0432\u0443\u0437\u043B\u0430\u0445");
		getContentPanel().add(chooseF);
		chooseF.setDividerLocation(0.75);
	}

	public ChooseDataH getChooseM() {
		return chooseX;
	}
	@Override
	protected void createRandom() {
		double[] x = chooseX.getDoubleArray();
		double[] f = chooseF.getDoubleArray();
		random = new Linear(x,f,chckbxRound.isSelected());
	}
	
}
