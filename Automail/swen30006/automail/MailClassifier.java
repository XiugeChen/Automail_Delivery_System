package automail;

import java.util.TreeMap;

/**
 *  Singleton mail classifier class, classify mail item based on weight
 * @author Xiuge Chen and Daniel Marshall
 */
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
    
    /** map number of robots to their maximum weight could carrying */
    private TreeMap<Integer, Integer> teamWeightLimit;
    
    private MailClassifier() {
    	teamWeightLimit = new TreeMap<>();
    	populateTeamWeightLimit();
    }
    
    /**
     * Singleton class instance getter, ensure only one instance will be generated
     * @return the single instance of MailClassifier
     */
    public static MailClassifier getInstance() {
    	if (instance == null) {
    		instance = new MailClassifier();
    	} 
    	return instance;
    }
    
    /**
     * determine the number of robots needed for delivering a given mail item
     * @param item next mail item 
     * @return number of robots needed for delivering this mail item
     */
    public int numRobotsNeeded(MailItem item) {
    	int highestKey = 0;
    	
    	for (int i: teamWeightLimit.keySet()) {
    		if (item.getWeight() <= teamWeightLimit.get(i)) 
    			return i;
    		
    		highestKey = i;
    	}
    	
    	// return highestKey even if no match
    	// item exception will be thrown while loading robots
    	return highestKey;
    }
    
    /**
     * get maximum weight limitation for given number of robots
     * @param numRobot number of robots
     * @return maximum weight could carrying
     */
    public int getWeightLimit(int numRobots) {
    	return teamWeightLimit.get(numRobots);
    }
    
    /**
     * used to generate the map of number of robots and maximum weight could carrying
     */
    private void populateTeamWeightLimit() {
    	teamWeightLimit.put(SMALL_TEAM, INDIVIDUAL_MAX_WEIGHT);
    	teamWeightLimit.put(MEDIUM_TEAM, PAIR_MAX_WEIGHT);
    	teamWeightLimit.put(LARGE_TEAM, TRIPLE_MAX_WEIGHT);
    }
}
