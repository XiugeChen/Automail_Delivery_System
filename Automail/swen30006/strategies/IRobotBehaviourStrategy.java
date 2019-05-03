package strategies;

import automail.Robot;
import exceptions.ExcessiveDeliveryException;

/**
 * Strategy interface for different robot behavior (move and make delivery) strategies
 * @author Xiuge Chen and Daniel Marshall
 *
 */
public interface IRobotBehaviourStrategy {
	/**
	 * make robot move towards specific detination floor
	 * @param robot the robot
	 * @param destination destination floor
	 */
	public void moveTowards(Robot robot, int destination);
	
	/**
	 * let robot make final delivery of item (not the whole delivery process)
	 * @param robot the robot
	 * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
	 */
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException;
}
