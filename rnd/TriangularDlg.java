package rnd;
import java.awt.GridLayout;
import widgets.ChooseDataH;
import javax.swing.SwingConstants;

public class TriangularDlg extends RandomDlg {
	private ChooseDataH chooseAvg;
	private ChooseDataH chooseMax;
	private ChooseDataH chooseMin;
	public TriangularDlg() {
		setTitle("\u0422\u0440\u0438\u043A\u0443\u0442\u043D\u0438\u0439 \u0440\u043E\u0437\u043F\u043E\u0434\u0456\u043B ");
		getContentPanel().setLayout(new GridLayout(3, 1, 0, 2));
		setBounds(100, 100, 219, 206);//TODO
		
		chooseMin = new ChooseDataH();
		chooseMin.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseMin.setText("1");
		chooseMin.setTitle("\u041C\u0456\u043D\u0456\u043C\u0430\u043B\u044C\u043D\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseMin);
		chooseAvg = new ChooseDataH();
		chooseAvg.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseAvg.setText("2");
		chooseAvg.setTitle("\u0421\u0435\u0440\u0435\u0434\u043D\u0454 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseAvg);
		
		chooseMax = new ChooseDataH();
		chooseMax.getjLabel().setHorizontalAlignment(SwingConstants.LEFT);
		chooseMax.setText("3");
		chooseMax.setTitle("\u041C\u0430\u043A\u0441\u0438\u043C\u0430\u043B\u044C\u043D\u0435 \u0437\u043D\u0430\u0447\u0435\u043D\u043D\u044F");
		getContentPanel().add(chooseMax);
	}

	@Override
	protected void createRandom() {
		double min=chooseMin.getDouble();
		double avg=chooseAvg.getDouble();
		double max=chooseMax.getDouble();
		random = new Triangular(min,avg,max,chckbxRound.isSelected());
	}
	

}
