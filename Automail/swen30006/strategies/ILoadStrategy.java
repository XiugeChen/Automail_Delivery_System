package strategies;

import java.util.ListIterator;

import automail.MailItem;
import automail.Robot;
import exceptions.ItemTooHeavyException;

/**
 * Strategy interface for different loading strategies (load one item to robots)
 * @author Xiuge Chen and Daniel Marshall
 *
 */
public interface ILoadStrategy {
	
	/**
	 * Load item to different combination of robots
	 * @param robotI list iterator of all currently available robots (ie. robots in Waiting)
	 * @param numRobots total number of robots this system have
	 * @param item the item is being loading
	 * @return true if the robot/robot team could be able to deliver this item from the mail pool
	 * @throws ItemTooHeavyException if this item is too heavy for all possible combination of robots.
	 */
	public boolean loadItem(ListIterator<Robot> robotI, int numRobots, 
			MailItem item) throws ItemTooHeavyException;
	
	/**
	 * check whether the weight of item is too heavy for all possible combination of robots
	 * @param item The item is being loading
	 * @param numRobots Total number of robots this system have
	 * @throws ItemTooHeavyException If this item is too heavy for all possible combination of robots.
	 */
	public void checkWeight(MailItem item, int numRobots) 
			throws ItemTooHeavyException;
}
