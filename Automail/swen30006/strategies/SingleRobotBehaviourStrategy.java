package strategies;

import automail.Robot;
import exceptions.ExcessiveDeliveryException;

/**
 * behavior strategy for one single robot
 * @author Xiuge Chen and Daniel Marshall
 */
public class SingleRobotBehaviourStrategy implements IRobotBehaviourStrategy {

	@Override
	public void moveTowards(Robot robot, int destination) {
		if(robot.getCurrentFloor() < destination){
            robot.moveUpperFloor();
        } else {
        	robot.moverLowerFloor();
        }
	}

	@Override
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException {
		robot.makeDeliver();
		
		robot.removeDeliverItem();
		robot.increaseDeliveryCounter();
		
		// Implies a simulation bug
        if(robot.getDeliveryCounter() > 2){  
        	throw new ExcessiveDeliveryException();
        }
	}
}
