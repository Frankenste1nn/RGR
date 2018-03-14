
package widgets.experiments;

import java.util.Map;



/**
 * Insert the type's description here. Creation date: (14.03.2006 8:36:42)
 * 
 * @author: Administrator
 */
public interface IExperimentable {
	
	public void initForExperiment(double factor);	
	
	public Map<String, Double> getResultOfExperiment();
	
}
