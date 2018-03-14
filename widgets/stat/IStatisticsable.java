package widgets.stat;

import java.util.Map;

import stat.IHisto;

public interface IStatisticsable {
	public Map<String,IHisto> getStatistics();
	public void initForStatistics();

}
