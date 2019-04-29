package automail;

import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

public class LoadRobot implements ILoadStrategy {

	@Override
	public boolean loadItem(ListIterator<Robot> i, MailItem item, Boolean isLastItem) throws ItemTooHeavyException {
		// no available robot
		if (!i.hasNext())
			return false;
		
		Robot robot = i.next();
		
		// has space to load, hand first as we want higher priority delivered first
		if (!robot.isHandFull()) {
			robot.addToHand(item); 
			// return list pointer to the same robot
			i.previous();
			
			// if it is the last item, send the robot to delivery immediately
			if (isLastItem) {
				robot.dispatch();
				i.remove();
			}
			
			return true;
		} 
		else if (!robot.isTubeFull()) {
			robot.addToTube(item);
			
			// no more space
			robot.dispatch();
			i.remove();
			return true;
		}
		
		// try to load item on next robot
		return loadItem(i, item, isLastItem);
	}
}