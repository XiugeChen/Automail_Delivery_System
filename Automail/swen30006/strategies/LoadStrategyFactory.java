package strategies;

import automail.MailClassifier;
import automail.MailItem;

/**
 * Singleton loading strategy factory
 * @author Xiuge Chen and Daniel Marshall
 */
public class LoadStrategyFactory {
	
	private static LoadStrategyFactory instance;
	
	/**
	 * Constructor
	 */
	private LoadStrategyFactory() {};
	
	/**
	 * Singleton class instance getter, ensure only one instance will be generated
	 * @return single instance of LoadStrategyFactory
	 */
	public static LoadStrategyFactory getFactory() {
		if (instance == null) {
			instance = new LoadStrategyFactory();
		}
		return instance;
	}
	
	/**
	 * determine the strategies of a specific item loading based on the weight of it
	 * @param mailItem the item being loading
	 * @return instance of loading robot strategy based on mail item weight
	 */
	public ILoadStrategy getStrategy(MailItem mailItem) {
		if (MailClassifier.getInstance().numRobotsNeeded(mailItem) ==
				MailClassifier.SMALL_TEAM)	
			return new LoadRobotStrategy();

		return new LoadRobotTeamStrategy(MailClassifier.getInstance().
				numRobotsNeeded(mailItem));
	}
}