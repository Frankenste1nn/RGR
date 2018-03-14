package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class DiscretDlg extends RandomDlg {
	private ChooseDataH chooseX;
	private ChooseDataH chooseP;
	public DiscretDlg() {
		chckbxRound.setVisible(false);
		chckbxRound.setEnabled(false);
		setTitle("\u0414\u0438\u0441\u043A\u0440\u0435\u0442\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(2, 1, 0, 0));
		setBounds(100, 100, 291, 147);//TODO
		chooseX = new ChooseDataH();
		chooseX.setText("1 2 3");
		chooseX.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseX.setText(" 2 3 4 5");
		chooseX.setTitle("\u0417\u043D\u0430\u0447\u0435\u043D\u043D\u044F \u0437\u043C\u0456\u043D\u043D\u043E\u0457");
		getContentPanel().add(chooseX);
		
		chooseP = new ChooseDataH();
		chooseP.setResizeWeight(0.3);
		chooseP.setContinuousLayout(true);
		chooseP.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseP.setText("0.1  0.3 0.4  0.2");
		chooseP.setTitle("\u0406\u043C\u043E\u0432\u0456\u0440\u043D\u043E\u0441\u0442\u0456 \u0437\u043D\u0430\u0447\u0435\u043D\u044C");
		getContentPanel().add(chooseP);
		chooseP.setDividerLocation(0.75);
	}

	public ChooseDataH getChooseM() {
		return chooseX;
	}
	@Override
	protected void createRandom() {
		double[] x = chooseX.getDoubleArray();
		double[] p = chooseP.getDoubleArray();
		random = new Discret(x,p);
	}
	
}
