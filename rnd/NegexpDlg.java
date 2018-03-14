package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class NegexpDlg extends RandomDlg {
	private ChooseDataH chooseM;
	public NegexpDlg() {
		setTitle("\u0415\u043A\u0441\u043F\u043E\u043D\u0435\u043D\u0446\u0456\u0439\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(1, 1, 0, 2));
		setBounds(100, 100, 224, 136);
		chooseM = new ChooseDataH();
		chooseM.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseM.setText("1");
		chooseM.setTitle("\u0421\u0435\u0440\u0435\u0434\u043D\u0454 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseM);
	}

	public ChooseDataH getChooseM() {
		return chooseM;
	}
	@Override
	protected void createRandom() {
		double m=chooseM.getDouble();
		random = new Negexp(m,chckbxRound.isSelected());
	}
	
}
