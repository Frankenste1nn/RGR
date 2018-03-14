package widgets.regres;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stat.StatTables;

/**
 * @author sanbok
 */
// instanceVariableNames: 'matrix p n factorArray avrg avrgArray doverArray
// dArray dExpr dFactor dAdequat '
public abstract class RegresTesters {// implements CalcParam{
	//private double[][] matrix;

	protected Double[] factorArray;

	private int p;

	protected int n;

	protected double[] avrgArray, dArray;

	protected double[] doverArray;

	protected double dExpr;

	private double avrg;

	private double dFactor;

	private double dAdequat;

	protected abstract void calculateParameters();

	public abstract double f(double arg0);

	public double getAvrg() {
		return avrg;
	}

	public double[] getAvrgArray() {
		return avrgArray;
	}

	public double[] getDArray() {
		return dArray;
	}

	public double getDExpr() {
		return dExpr;
	}

	public double getDFactor() {
		return dFactor;
	}

	public double[] getDoverArray() {
		return doverArray;
	}

	public abstract String getLabelName();

	public boolean isDispersUniform() {
		double sum = 0;
		double max = 0;
		for (int i = 0; i < dArray.length; i++) {
			sum += dArray[i];
			if (dArray[i] > max)
				max = dArray[i];
		}
		double g = max / sum;
		return (g < StatTables.kochren05(n - 1, p).floatValue());
	}

	public boolean isFactorValid() {
		double f = dFactor / (dExpr / n);
		if (f <= 1)
			return false;
		return (f > (StatTables.fisher05(p - 1, p * (n - 1))).floatValue());
	}

	public boolean isRegresAdequat() {
		double f = dAdequat / (dExpr / n);
		if (f <= 1)
			return true;
		return (f < (StatTables.fisher05(p - this.k(), p * (n - 1)))
				.floatValue());
	}

	public abstract int k();

	public abstract String parametersAsString();

	public void testMatrix( Map<Double, List<Double>> resultMap) {
		//double[][] arg0, double[] arg1
		if (resultMap == null)
			return;
		
		Collection<Double> factorSet = resultMap.keySet();
		factorArray=new Double[resultMap.size()];
		factorArray=factorSet.toArray(factorArray);
		Arrays.sort(factorArray);
		
		p = factorArray.length; // „исло уровней
		double sum, d;
		


		
		
		// ¬ычисление массива средних значений дл€ каждого уровн€
		avrgArray = new double[p]; 
		int i=0;
		for(Double factor: factorArray){
			sum = 0; n=0;
			List<Double> resultList = resultMap.get(factor);
			for(Double result:resultList){
				n++;
				sum += result;
			}
			avrgArray[i++] = sum / n;
		}
		// ¬ычисление массива дисперсий отклика на каждом уровне
		dArray = new double[p];
		i=0;
		for(Double factor: factorArray){
			sum = 0; n=0;
			List<Double> resultList = resultMap.get(factor);	
			for(Double result:resultList){
				n++;
				d = result - avrgArray[i];
				sum += d * d;
			}
			dArray[i++] = sum / (n - 1);
		}
		// ¬ычисление массива доверительных интервалов дл€ отклика на всех
		// уровн€х
		doverArray = new double[p];
		for (int i1 = 0; i1 < p; i1++)
			doverArray[i1] = StatTables.student05(n) * Math.sqrt(dArray[i1] / n);
		// ¬ычисление дисперсии эксперимента dExpr
		sum = 0;
		for (int i1 = 0; i1 < p; i1++)
			sum += dArray[i1];
		dExpr = sum / p;
		// ¬ычисление общего среднего avrg
		sum = 0;
		for (int i1 = 0; i1 < p; i1++)
			sum += avrgArray[i1];
		avrg = sum / p;
		// —равнение данных по уровн€м
		if (p > 1) {
			// ¬ычисление дисперсии фактора dFactor
			for (int i1 = 0; i1 < p; i1++) {
				d = avrgArray[i1] - avrg;
				sum += d * d;
			}
			dFactor = sum / (p - 1);
			// –асчет параметров выбранного уравнени€ регресии (вызов из
			// подкласса)
			this.calculateParameters();
			if (p > this.k()) {
				// ¬ычисление дисперсии адекватности dAdequat.
				sum = 0;
				for (int i1 = 0; i1 < p; i1++) {
					d = this.f(factorArray[i1]) - avrgArray[i1];
					sum += d * d;
				}
				dAdequat = sum / (p - this.k());
			}
		}
	}
}
