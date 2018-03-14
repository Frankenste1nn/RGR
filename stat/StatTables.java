package stat;

import java.util.Arrays;
import java.util.Vector;

/**
 * Insert the type's description here. Creation date: (02.11.2005 23:43:12)
 * 
 * @author: Administrator
 */
public class StatTables {
	/**
	 * StatTables constructor comment.
	 */
	public StatTables() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (02.11.2005
	 * 23:45:08)
	 * 
	 * @return int
	 */
	public static int detectIndex(double n, double[] arr) {
		/* 1 to: anArray size do:[:i| (anArray at:i) >=aFloat ifTrue:[^i].]. */
		for (int i = 0; i < arr.length; i++)
			if (arr[i] >= n)
				return i;
		return 0;
	}

	public static Float fisher05(int kMax, int kMin) {
		int k1, k2;
		String[] str1 = { "164.4", "199.5", "215.7", "224.8", "230.2", "234.0",
				"244.9", "249.0", "254.3" };
		String[] str2 = { "18.5", "19.2", "19.2", "19.16", "19.25", "19.3",
				"19.40", "19.40", "19.40" };
		String[] str3 = { "10.1", "9.6", "9.3", "9.1", "9.0", "8.9", "8.7",
				"8.6", "8.5" };
		String[] str4 = { "7.7", "6.9", "6.6", "6.4", "6.3", "6.2", "5.9",
				"5.8", "5.6" };
		String[] str5 = { "6.6", "5.8", "5.4", "5.2", "5.1", "5.0", "4.7",
				"4.5", "4.4" };
		String[] str6 = { "6.0", "5.1", "4.8", "4.5", "4.4", "4.3", "4.0",
				"3.8", "3.7" };
		String[] str7 = { "5.5", "4.7", "4.4", "4.1", "4.0", "3.9", "3.6",
				"3.4", "3.2" };
		String[] str8 = { "5.3", "4.5", "4.1", "3.8", "3.7", "3.6", "3.3",
				"3.1", "2.9" };
		String[] str9 = { "5.1", "4.3", "3.9", "3.5", "3.5", "3.4", "3.1",
				"2.9", "2.7" };
		String[] str10 = { "5.0", "4.1", "3.7", "3.5", "3.3", "3.2", "2.9",
				"2.7", "2.5" };
		String[] str11 = { "4.5", "3.7", "3.3", "3.1", "2.9", "2.8", "2.5",
				"2.3", "2.1" };
		String[] str12 = { "4.3", "3.4", "3.0", "2.8", "2.6", "2.5", "2.2",
				"2.0", "1.7" };
		String[] str13 = { "4.2", "3.3", "2.9", "2.7", "2.5", "2.4", "2.1",
				"1.9", "1.6" };
		String[] str14 = { "3.9", "3.1", "2.7", "2.5", "2.3", "2.2", "1.8",
				"1.6", "1.3" };
		String[] str15 = { "3.8", "3.0", "2.6", "2.4", "2.2", "2.1", "1.8",
				"1.5", "1.0" };

		Vector arr = new Vector();
		arr.add(str1);
		arr.add(str2);
		arr.add(str3);
		arr.add(str4);
		arr.add(str5);
		arr.add(str6);
		arr.add(str7);
		arr.add(str8);
		arr.add(str9);
		arr.add(str10);
		arr.add(str11);
		arr.add(str12);
		arr.add(str13);
		arr.add(str14);
		arr.add(str15);

		double[] tmpStr1 = { 1, 2, 3, 4, 5, 6, 12, 24, 99999 };
		double[] tmpStr2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 24, 30, 120,
				99999 };

		k1 = detectIndex((double) kMax, tmpStr1);
		k2 = detectIndex((double) kMin, tmpStr2);

		Float ret = Float.valueOf(((String[]) arr.get(k2))[k1]);
		return ret;
	}

	/**
	 * Insert the method's description here. Creation date: (24.01.2006
	 * 20:45:45)
	 * 
	 * @return java.lang.String
	 * @param x
	 *            double
	 * @param len
	 *            int
	 * @param dec
	 *            int
	 */
	public static String format(double x, int len, int dec) {
		// округляем
		double r = 1.0;
		for (int i = 0; i < dec; i++)
			r *= 0.1;
		float xf = (float) roundTo(x, r);
		// дописываем нули справа
		StringBuffer bs = new StringBuffer();
		bs.append(xf);
		if (dec > 0)
			while (bs.toString().indexOf(".") + dec > bs.length() - 1)
				bs.append("0");
		// добавим пробелы слева и обрежем до одинаковой длины
		if (bs.length() < len) {
			bs.reverse();
			bs.append("                     ");
			bs.setLength(len);
			bs.reverse();
		}

		return bs.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (24.01.2006
	 * 20:45:45)
	 * 
	 * @return java.lang.String
	 * @param x
	 *            double
	 * @param len
	 *            int
	 * @param dec
	 *            int
	 */
	public static String format(float x, int len, int dec) {
		double y = (double) x;
		return StatTables.format(y, len, dec);

	}

	/**
	 * Insert the method's description here. Creation date: (24.01.2006
	 * 20:45:45)
	 * 
	 * @return java.lang.String
	 * @param x
	 *            double
	 * @param len
	 *            int
	 * @param dec
	 *            int
	 */
	public static String format(int x, int len) {
		StringBuffer bs = new StringBuffer();
		bs.append(x);
		// добавим пробелы слева и обрежем до нужнойой длины
		if (bs.length() < len) {
			bs.reverse();
			bs.append("                     ");
			bs.setLength(len);
			bs.reverse();
		}
		return bs.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (02.11.2005
	 * 23:48:36)
	 * 
	 * @return double[]
	 */
	public static double[] hi05() {
		double[] arr = { 3.8, 6.0, 7.8, 9.5, 11.1, 12.6, 14.1, 15.5, 16.9,
				18.3, 19.7, 21.0, 22.4, 23.7, 25.0, 26.3, 27.6, 28.9, 30.1,
				31.4, 32.7, 33.9, 35.2, 36.4, 37.7, 38.9, 40.1, 41.3, 42.6,
				43.8 };

		return arr;
	}

	public static Float kochren05(int K, int L) {
		/*
		 * K - это число степеней свободы. Обычно число экспериментов на уровне
		 * - 1 L - это количество независимых выборок одинакового размера
		 * В.Е.Гмурман. ТВ и МС, 1977, стр.325, стр.468
		 */

		String[] str1 = { "0.9985", "0.9750", "0.9392", "0.9057", "0.8772",
				"0.8534", "0.8332", "0.8159", "0.8010", "0.7880", "0.7341",
				"0.6602", "0.5813", "0.5000" };
		String[] str2 = { "0.9669", "0.8709", "0.7977", "0.7457", "0.7071",
				"0.6771", "0.6530", "0.6333", "0.6167", "0.6025", "0.5466",
				"0.4748", "0.4031", "0.3333" };
		String[] str3 = { "0.9065", "0.7679", "0.6841", "0.6287", "0.5895",
				"0.5598", "0.5365", "0.5175", "0.5017", "0.4884", "0.4366",
				"0.3720", "0.3093", "0.2500" };
		String[] str4 = { "0.8412", "0.6338", "0.5981", "0.5440", "0.5063",
				"0.4783", "0.4564", "0.4387", "0.4241", "0.4118", "0.3645",
				"0.3066", "0.2013", "0.2000" };
		String[] str5 = { "0.7808", "0.6161", "0.5321", "0.4803", "0.4447",
				"0.4184", "0.3980", "0.3817", "0.3682", "0.3568", "0.3135",
				"0.2612", "0.2119", "0.1667" };
		String[] str6 = { "0.7271", "0.5612", "0.4800", "0.4307", "0.3974",
				"0.3726", "0.3535", "0.3384", "0.3259", "0.3154", "0.2756",
				"0.2278", "0.1833", "0.1429" };
		String[] str7 = { "0.6798", "0.5157", "0.4377", "0.3910", "0.3595",
				"0.3362", "0.3185", "0.3043", "0.2926", "0.2829", "0.2462",
				"0.2022", "0.1616", "0.1250" };
		String[] str8 = { "0.6385", "0.4775", "0.4027", "0.3584", "0.3286",
				"0.3067", "0.2901", "0.2768", "0.2659", "0.2568", "0.2226",
				"0.1820", "0.1446", "0.1111" };
		String[] str9 = { "0.6020", "0.4450", "0.3733", "0.3311", "0.3029",
				"0.2823", "0.2666", "0.2541", "0.2439", "0.2353", "0.2032",
				"0.1655", "0.1308", "0.1000" };
		String[] str10 = { "0.5410", "0.3924", "0.3624", "0.2880", "0.2624",
				"0.2439", "0.2299", "0.2187", "0.2098", "0.2020", "0.1737",
				"0.1403", "0.1100", "0.0833" };
		String[] str11 = { "0.4709", "0.3346", "0.2758", "0.2419", "0.2195",
				"0.2034", "0.1911", "0.1815", "0.1736", "0.1671", "0.1429",
				"0.1144", "0.0889", "0.0667" };
		String[] str12 = { "0.3894", "0.2705", "0.2205", "0.1921", "0.1735",
				"0.1602", "0.1501", "0.1422", "0.1357", "0.1303", "0.1108",
				"0.0879", "0.0675", "0.0500" };
		String[] str13 = { "0.3434", "0.2354", "0.1907", "0.1656", "0.1493",
				"0.1374", "0.1286", "0.1216", "0.1160", "0.1113", "0.0942",
				"0.0743", "0.0567", "0.0417" };
		String[] str14 = { "0.2929", "0.1980", "0.1593", "0.1377", "0.1237",
				"0.1137", "0.1061", "0.1002", "0.0958", "0.0921", "0.0771",
				"0.0604", "0.0457", "0.0333" };
		String[] str15 = { "0.2370", "0.1576", "0.1259", "0.1082", "0.0968",
				"0.0887", "0.0827", "0.0780", "0.0745", "0.0713", "0.0595",
				"0.0462", "0.0347", "0.0250" };
		String[] str16 = { "0.1737", "0.1131", "0.0895", "0.0765", "0.0682",
				"0.0623", "0.0583", "0.0552", "0.0520", "0.0497", "0.0411",
				"0.0316", "0.0234", "0.0167" };
		String[] str17 = { "0.0998", "0.0632", "0.0495", "0.0419	 0.0371",
				"0.0337", "0.0312", "0.0292", "0.0279", "0.0266", "0.0218",
				"0.0165", "0.0120", "0.0083" };
		String[] str18 = { "0.0000", "0.0000", "0.0000", "0.0000", "0.0000",
				"0.0000", "0.0000", "0.0000", "0.0000", "0.0000", "0.0000",
				"0.0000", "0.0000", "0.0000" };

		Vector arr = new Vector();
		arr.add(str1);
		arr.add(str2);
		arr.add(str3);
		arr.add(str4);
		arr.add(str5);
		arr.add(str6);
		arr.add(str7);
		arr.add(str8);
		arr.add(str9);
		arr.add(str10);
		arr.add(str11);
		arr.add(str12);
		arr.add(str13);
		arr.add(str14);
		arr.add(str15);
		arr.add(str16);
		arr.add(str17);
		arr.add(str18);

		double[] tmpStr1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 36, 144, 99999 };
		double[] tmpStr2 = { 2, 3, 4, 5, 6, 7, 9, 10, 12, 15, 20, 24, 30, 40,
				60, 120, 99999 };

		int k = detectIndex((double) K, tmpStr1);
		int l = detectIndex((double) L, tmpStr2);

		Float ret = Float.valueOf(((String[]) arr.get(l))[k]);
		return ret;
	}

	/**
	 * Расчет коэффициента корреляции. Creation date: (21.01.2006 11:30:25)
	 * 
	 * @return double
	 * @param x1
	 *            double[]
	 * @param x2
	 *            double[]
	 */
	public static double koefKorr(double[] x1, double[] x2) {
		/* Подготовка к обработке данных */
		double s1 = 0; // Сумма элементов первой последовательности
		double s1s1 = 0; // Сумма квадратов элементов первой
							// последовательности
		double s2 = 0; // Сумма элементов второй последовательности
		double s2s2 = 0; // Сумма квадратов элементов второй
							// последовательности
		double s1s2 = 0; // Сумма произведений элементов последовательностей
		/* Обработка последовательностей */
		int size = x1.length;
		for (int i = 0; i < size; i++) {
			// Накапливаем суммы
			s1 += x1[i];
			s1s1 += x1[i] * x1[i];
			s2 += x2[i];
			s2s2 += x2[i] * x2[i];
			s1s2 += x1[i] * x2[i];
		}
		// Расчет средних значений
		s1 = s1 / size;
		s1s1 = s1s1 / size;
		s2 = s2 / size;
		s2s2 = s2s2 / size;
		s1s2 = s1s2 / size;
		// Расчет среднеквадратичных отклонений
		double sigma1 = Math.sqrt(s1s1 - s1 * s1);
		double sigma2 = Math.sqrt(s2s2 - s2 * s2);
		/* Расчет коэффициента корреляции */
		Double r;
		r = (s1s2 - s1 * s2) / (sigma1 * sigma2);

		if(r.isInfinite() || r.isNaN())
				
			r = new Double(1);
		
//		if ((sigma1 == 0) || (sigma2 == 0))
//			r = 1;
//		else
			
		return r;
	}

	/**
	 * Insert the method's description here. Creation date: (03.11.2005 0:18:38)
	 * 
	 * @return double
	 * @param ind
	 *            int
	 */
	public static double kolmogorovSmirnov05(int ind) {
		/*
		 * n:=#( 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 25 30 35).
		 * a:=#( 0.975 0.842 0.708 0.624 0.565 0.521 0.468 0.457 0.432 0.410
		 * 0.391 0.375 0.361 0.349 0.338 0.328 0.318 0.309 0.301 0.294 0.27 0.24
		 * 0.23 ). i:=0. n do:[:x|i:=i+1. x>=anInteger ifTrue:[^a at:i] ]. ^1.36
		 * / anInteger sqrt .
		 */
		int[] n = new int[23];
		double[] a = new double[23];
		for (int i = 0; i < 20; i++)
			n[i] = i + 1;
		n[20] = 25;
		n[21] = 30;
		n[22] = 35;
		a[0] = 0.975;
		a[1] = 0.842;
		a[2] = 0.708;
		a[3] = 0.624;
		a[4] = 0.565;
		a[5] = 0.521;
		a[6] = 0.468;
		a[7] = 0.457;
		a[8] = 0.432;
		a[9] = 0.410;
		a[10] = 0.391;
		a[11] = 0.375;
		a[12] = 0.361;
		a[13] = 0.349;
		a[14] = 0.338;
		a[15] = 0.328;
		a[16] = 0.318;
		a[17] = 0.309;
		a[18] = 0.301;
		a[19] = 0.294;
		a[20] = 0.27;
		a[21] = 0.24;
		a[22] = 0.23;
		for (int i = 0; i < n.length; i++)
			if (n[i] >= ind)
				return a[i];
		return (1.36 / java.lang.Math.sqrt(ind));
	}

	/**
	 * Insert the method's description here. Creation date: (25.01.2006
	 * 18:07:04)
	 * 
	 * @return double[]
	 * @param a
	 *            double[]
	 * @param t
	 *            double[]
	 * @param textArea
	 *            javax.swing.JTextArea
	 */
	public static double kolmSmirnovResult(double[] a, double[] t, double[] b,
			javax.swing.JTextArea textArea) {
		/* вычисляем отклонения и находим max */
		double max = 0;
		double[] d = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			d[i] = Math.abs(a[i] - t[i]);
			if (d[i] > max)
				max = d[i];
		}
		if (textArea != null) {
			/* Готовим данные к выводу */
			// вначале округляем
			float[] ar = new float[a.length];
			float[] tr = new float[a.length];
			float[] dr = new float[a.length];
			for (int i = 0; i < a.length; i++) {
				ar[i] = (float) StatTables.roundTo(a[i], 0.01);
				tr[i] = (float) StatTables.roundTo(t[i], 0.01);
				dr[i] = (float) StatTables.roundTo(d[i], 0.01);
			}
			float[] br = new float[b.length];
			for (int i = 0; i < b.length; i++) {
				br[i] = (float) stat.StatTables.roundTo(b[i], 0.01);
			}
			// теперь удалим слева лишние интервалы содержащие 0
			int k = 1;
			int n = ar.length;
			while ((ar[k] == 0) & (tr[k] == 0)) {
				for (int j = k; j < ar.length - 1; j++) {
					ar[j] = ar[j + 1];
					tr[j] = tr[j + 1];
				}
				for (int j = k; j < br.length - 1; j++)
					br[j] = br[j + 1];
				n--;
			}
			// удалим справа лишние интервалы содержащие 1
			while ((ar[n - 2] == 1) & (tr[n - 2] == 1))
				n--;
			// ширина поля для границы интервала
			int maxLenB = 0;
			for (int i = 0; i < br.length; i++) {
				int l = String.valueOf(Math.round(br[i])).length();
				if (l > maxLenB)
					maxLenB = l;
			}
			maxLenB += 3;
			String z = " Интервалы  ";
			for (int i = 0; i < maxLenB - 3; i++) {
				z = " " + z + " ";
			}
			// сформируем табличку результатов
			String s = "Проверка  по  Колмогорову-Смирнову:\n";
			s += z + "Эксп. Теор. Откл.\n";
			for (int i = 0; i < n; i++) {
				s += stat.StatTables.format(br[i], maxLenB, 2) + " .. ";
				s += stat.StatTables.format(br[i + 1], maxLenB, 2) + "  ";
				s += stat.StatTables.format(ar[i], 4, 2) + "  ";
				s += stat.StatTables.format(tr[i], 4, 2) + "  ";
				s += stat.StatTables.format(dr[i], 4, 2) + "\n";
			}
			textArea.append(s);
		}
		return max;
	}

	/**
	 * Insert the method's description here. Creation date: (20.01.2006
	 * 22:12:43)
	 * 
	 * @return double
	 * @param af
	 *            int[]
	 * @param at
	 *            double[]
	 */
	public static double[] pirsonResult(double[] af, // массив абсолютнах факт.
			// частот
			double[] at, // массив абсолютнах теор. частот
			double[] br, // массив границ интервалов
			javax.swing.JTextArea textArea, // область вывода
			int k /* число уже определенных параметров */) {
		/*
		 * Особенность критерия Пирсона в том, что интервалы с частотами
		 * меньшими 5 следует объединять с соседними
		 */
		// Вначале группирум хвост массива (если надо)
		int n = af.length;
		while (af[n - 1] < 5) {
			af[n - 2] += af[n - 1];
			at[n - 2] += at[n - 1];
			br[n - 1] = br[n];
			n--;
		}
		// Теперь группируем начало массива
		int i = 0;
		while (i < n) {
			if (af[i] < 5) {
				af[i] += af[i + 1];
				at[i] += at[i + 1];
				for (int j = i + 1; j < n - 1; j++) {
					af[j] = af[j + 1];
					at[j] = at[j + 1];
				}
				for (int j = i + 1; j < n; j++)
					br[j] = br[j + 1];
				n--;
			} else
				i++;
		}

		// ширина поля для границы интервала
		int maxLenB = 0;
		for (int j = 0; j < br.length; j++) {
			int l = String.valueOf(Math.round(br[j])).length();
			if (l > maxLenB)
				maxLenB = l;
		}
		maxLenB += 3;
		String z = " Інтервали  ";
		for (int j = 0; j < maxLenB - 3; j++) {
			z = " " + z + " ";
		}

		// Выводим результаты
		if (textArea != null) {
			String s = "Перевірка за критерієм Пірсона:" + "\n";
			s += z + "Експ.   Теор. " + "\n";
			for (int j = 0; j < n; j++) {
				s += format(br[j], maxLenB, 2) + " .. ";
				s += format(br[j + 1], maxLenB, 2) + "  ";
				s += format(af[j], 4, 0) + "  ";
				s += format(at[j], 6, 1) + "\n";
			}
			textArea.append(s);

		}
		// Теперь считаем хи-квадрат
		double sum = 0;
		for (int j = 0; j < n; j++)
			if (at[j] != 0)
				sum += (af[j] - at[j]) * (af[j] - at[j]) / at[j];
		/* Критическое значение хи-квадрат */
		double max = hi05()[n - k];
		// формируем результат
		double[] result = new double[2];
		result[0] = sum;
		result[1] = max;
		return result;
	}

	/**
	 * Insert the method's description here. Creation date: (02.11.2005
	 * 23:45:08)
	 * 
	 * @return int
	 */
	public static double roundTo(double value, double roundval) {
		long multiplier = Math.round(value / roundval);
		return (roundval * multiplier);
	}

	/**
	 * Insert the method's description here. Creation date: (03.11.2005 0:31:30)
	 * 
	 * @return double
	 * @param ind
	 *            int
	 */
	public static double student05(int ind) {
		/*
		 * n:=#( 1 2 3 4 5 6 7 9 13 29). a:=#( 12.7 4.3 3.2 2.8 2.6 2.5 2.4 2.3
		 * 2.2 2.1). i:=0. n do:[:x|i:=i+1. x>=anInteger ifTrue:[^a at:i] ].
		 * ^1.96 .
		 */
		int[] n = new int[11];
		for (int i = 0; i < 8; i++)
			n[i] = i + 1;
		n[8] = 13;
		n[9] = 29;
		
		double[] a = new double[10];
		a[0] = 6.3;
		a[1] = 2.9;
		a[2] = 2.35;
		a[3] = 2.13;
		a[4] = 2.01;
		a[5] = 1.94;
		a[6] = 1.89;
		a[7] = 1.86;
		a[8] = 1.77;
		a[9] = 1.70;
		for (int i = 0; i < n.length; i++)
			if (n[i] > ind)
				return a[i];
		return 1.64;
	}
	public static double student025(int ind) {
		/*
		 * n:=#( 1 2 3 4 5 6 7 9 13 29). a:=#( 12.7 4.3 3.2 2.8 2.6 2.5 2.4 2.3
		 * 2.2 2.1). i:=0. n do:[:x|i:=i+1. x>=anInteger ifTrue:[^a at:i] ].
		 * ^1.96 .
		 */
		int[] n = new int[11];
		for (int i = 0; i < 8; i++)
			n[i] = i + 1;
		n[8] = 13;
		n[9] = 29;
		
		double[] a = new double[10];
		a[0] = 12.7;
		a[1] = 4.3;
		a[2] = 3.2;
		a[3] = 2.8;
		a[4] = 2.6;
		a[5] = 2.5;
		a[6] = 2.4;
		a[7] = 2.3;
		a[8] = 2.2;
		a[9] = 2.1;
		for (int i = 0; i < n.length; i++)
			if (n[i] > ind)
				return a[i];
		return 1.96;
	}
}
