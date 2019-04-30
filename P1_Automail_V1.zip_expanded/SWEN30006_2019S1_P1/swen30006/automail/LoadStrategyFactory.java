package automail;

// Singleton load strategy factory
public class LoadStrategyFactory {
	
	private static LoadStrategyFactory instance;
	
	private LoadStrategyFactory() {};
	
	public static LoadStrategyFactory getFactory() {
		if (instance == null) {
			instance = new LoadStrategyFactory();
		}
		return instance;
	}
	
	// Return instance of load robot strategy based on 
	// mail item weight
	public ILoadStrategy getStrategy(MailItem mailItem) {
		if (MailClassifier.getInstance().numRobotsNeeded(mailItem) == MailClassifier.SMALL_TEAM) {
			return new LoadRobotStrategy();
		} 
		return new LoadRobotTeamStrategy(MailClassifier.getInstance().numRobotsNeeded(mailItem));
	}
}