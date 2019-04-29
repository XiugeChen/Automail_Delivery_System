package automail;

import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

public class LoadRobot implements ILoadStrategy {

	@Override
	public boolean loadItem(ListIterator<Robot> i, MailItem item) throws ItemTooHeavyException {
		Robot robot = i.next();

		
		if (!robot.isHandFull()) {
			robot.addToHand(item); // hand first as we want higher priority delivered first
			// Still space to load
			return true;
		} else if (!robot.isTubeFull()) {
			robot.addToTube(item);
			// No more space
			return false;
		}
		robot.dispatch();
		i.remove();
		return false;

	}
}