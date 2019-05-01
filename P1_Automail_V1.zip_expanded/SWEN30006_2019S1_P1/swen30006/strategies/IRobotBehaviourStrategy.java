package strategies;

import automail.Robot;
import exceptions.ExcessiveDeliveryException;

public interface IRobotBehaviourStrategy {
	public void moveTowards(Robot robot, int destination);
	
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException;
}
