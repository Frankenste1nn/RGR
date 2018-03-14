package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class NormDlg extends RandomDlg {
	private ChooseDataH chooseM;
	private ChooseDataH chooseS;
	public NormDlg() {
		setTitle("\u041D\u043E\u0440\u043C\u0430\u043B\u044C\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(2, 1, 0, 0));
		setBounds(100, 100, 209, 162);//TODO
		chooseM = new ChooseDataH();
		chooseM.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseM.setText("1");
		chooseM.setTitle("\u0421\u0435\u0440\u0435\u0434\u043D\u0454 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseM);
		
		chooseS = new ChooseDataH();
		chooseS.setText("0.2");
		chooseS.setTitle("\u0421\u0442\u0430\u043D\u0434\u0430\u0440\u0442\u043D\u0435 \u0432\u0456\u0434\u0445\u0456\u043B\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseS);
	}

	public ChooseDataH getChooseM() {
		return chooseM;
	}
	@Override
	protected void createRandom() {
		double m=chooseM.getDouble();
		double s = chooseS.getDouble();
		random = new Norm(m,s,chckbxRound.isSelected());
	}
	
}
