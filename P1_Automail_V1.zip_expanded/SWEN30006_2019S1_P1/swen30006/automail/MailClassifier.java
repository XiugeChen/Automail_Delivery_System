package automail;

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
    
    private MailClassifier() {}
    
    // Singleton get instance method
    public static MailClassifier getInstance() {
    	if (instance == null) {
    		instance = new MailClassifier();
    	} 
    	return instance;
    }
    
    // Method to determine how many 
    public int numRobotsNeeded(MailItem item) {
    	if (item.getWeight() <= INDIVIDUAL_MAX_WEIGHT) {
    		return SMALL_TEAM;
    	} else if (item.getWeight() <= PAIR_MAX_WEIGHT) {
    		return MEDIUM_TEAM;
    	}
    	return LARGE_TEAM;
    }

}
