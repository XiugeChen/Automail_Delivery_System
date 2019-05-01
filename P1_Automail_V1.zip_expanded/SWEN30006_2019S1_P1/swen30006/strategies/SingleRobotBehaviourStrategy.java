package strategies;

import automail.Robot;
import exceptions.ExcessiveDeliveryException;

public class SingleRobotBehaviourStrategy implements IRobotBehaviourStrategy {

	@Override
	public void moveTowards(Robot robot, int destination) {
		// TODO Auto-generated method stub
		if(robot.getCurrentFloor() < destination){
            robot.moveUpperFloor();
        } else {
        	robot.moverLowerFloor();
        }
	}

	@Override
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException {
		// TODO Auto-generated method stub
		robot.makeDeliver();
		
		robot.removeDeliverItem();
		robot.increaseDeliveryCounter();
        if(robot.getDeliveryCounter() > 2){  // Implies a simulation bug
        	throw new ExcessiveDeliveryException();
        }
	}
}
