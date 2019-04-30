package automail;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

// Singleton mail classifier class
public class MailClassifier {
	
	// Mail weight constants
    static public final int INDIVIDUAL_MAX_WEIGHT = 2000;
    static public final int PAIR_MAX_WEIGHT = 2600;
    static public final int TRIPLE_MAX_WEIGHT = 3000;
    
    // Determine number of robots needed to carry a mail item
    static public final int SMALL_TEAM = 1;
    static public final int MEDIUM_TEAM = 2;
    static public final int LARGE_TEAM = 3;
    
    private static MailClassifier instance = null;
    
    private TreeMap<Integer, Integer> teamWeightLimit;
    
    private MailClassifier() {
    	teamWeightLimit = new TreeMap<>();
    	
    	populateTeamWeightLimit();
    }
    
    // Singleton get instance method
    public static MailClassifier getInstance() {
    	if (instance == null) {
    		instance = new MailClassifier();
    	} 
    	return instance;
    }
    
    /**
     * last team has 
     * @param item
     * @return
     */
    public int numRobotsNeeded(MailItem item) {
    	int highestKey = 0;
    	
    	for (int i: teamWeightLimit.keySet()) {
    		if (item.getWeight() <= teamWeightLimit.get(i)) 
    			return i;
    		
    		highestKey = i;
    	}
    	
    	// return highestKey which means item exception will be thrown while loading robots
    	return highestKey;
    }
    
    /**
     * 
     * @param numRobot
     * @return
     */
    public int getWeightLimit(int numRobots) {
    	return teamWeightLimit.get(numRobots);
    }
    
    /**
     * 
     */
    private void populateTeamWeightLimit() {
    	teamWeightLimit.put(SMALL_TEAM, INDIVIDUAL_MAX_WEIGHT);
    	teamWeightLimit.put(MEDIUM_TEAM, PAIR_MAX_WEIGHT);
    	teamWeightLimit.put(LARGE_TEAM, TRIPLE_MAX_WEIGHT);
    }
}
