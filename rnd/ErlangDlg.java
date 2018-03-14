package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class ErlangDlg extends RandomDlg {
	private ChooseDataH chooseM;
	private ChooseDataH chooseK;
	public ErlangDlg() {
		setTitle("\u0415\u0440\u043B\u0430\u043D\u0433\u0430 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(2, 1, 0, 0));
		setBounds(100, 100, 207, 166);//TODO
		chooseM = new ChooseDataH();
		chooseM.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseM.setText("1");
		chooseM.setTitle("\u0421\u0435\u0440\u0435\u0434\u043D\u0454 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseM);
		
		chooseK = new ChooseDataH();
		chooseK.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseK.setText("2");
		chooseK.setTitle("\u041A\u043E\u0435\u0444\u0456\u0446\u0456\u0454\u043D\u0442");
		getContentPanel().add(chooseK);
	}

	public ChooseDataH getChooseM() {
		return chooseM;
	}
	@Override
	protected void createRandom() {
		double m=chooseM.getDouble();
		int k = chooseK.getInt();
		random = new Erlang(m,k,chckbxRound.isSelected());
	}
	
}
