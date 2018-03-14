package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class UniformDlg extends RandomDlg {
	private ChooseDataH chooseMax;
	private ChooseDataH chooseMin;
	public UniformDlg() {
		setTitle("\u0420\u0456\u0432\u043D\u043E\u043C\u0456\u0440\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(2, 1, 0, 2));
		setBounds(100, 100, 203, 167);//TODO
		
		chooseMin = new ChooseDataH();
		chooseMin.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseMin.setText("1");
		chooseMin.setTitle("\u041C\u0456\u043D\u0456\u043C\u0430\u043B\u044C\u043D\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseMin);
		
		chooseMax = new ChooseDataH();
		chooseMax.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseMax.setText("3");
		chooseMax.setTitle("\u041C\u0430\u043A\u0441\u0438\u043C\u0430\u043B\u044C\u043D\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseMax);
	}

	@Override
	protected void createRandom() {
		double min=chooseMin.getDouble();
		double max=chooseMax.getDouble();
		random = new Uniform(min,max,chckbxRound.isSelected());
	}
	

}
