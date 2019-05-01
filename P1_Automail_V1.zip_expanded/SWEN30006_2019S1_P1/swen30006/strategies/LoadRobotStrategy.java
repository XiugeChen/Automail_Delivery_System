package strategies;

import java.util.ListIterator;

import automail.MailClassifier;
import automail.MailItem;
import automail.Robot;
import exceptions.ItemTooHeavyException;

public class LoadRobotStrategy implements ILoadStrategy {

	@Override
	public boolean loadItem(ListIterator<Robot> robotI, int numRobots, MailItem item) throws ItemTooHeavyException {
		checkWeight(item, numRobots);
		
		// no available robots
		if (!robotI.hasNext())
			return false;
		
		Robot robot = robotI.next();
		
		// has space to load, hand first as we want higher priority delivered first
		if (!robot.isHandFull()) {
			robot.addToHand(item); 
			// return list pointer to the same robot
			robotI.previous();
			
			return true;
		} else if (!robot.isTubeFull()) {
			robot.addToTube(item);
			return true;
		}
		
		// try to load on next item
		return loadItem(robotI, numRobots, item);
	}
	
	@Override
	public void checkWeight(MailItem item, int numRobots) throws ItemTooHeavyException {
		if (item.getWeight() > MailClassifier.getInstance().getWeightLimit(1)) 
			throw new ItemTooHeavyException();
	}
}