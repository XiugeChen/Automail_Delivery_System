package strategies;

import automail.Robot;
import exceptions.ExcessiveDeliveryException;

public class SingleRobotBehaviourStrategy implements IRobotBehaviourStrategy {

	@Override
	public void moveTowards(Robot robot, int destination) {
		// TODO Auto-generated method stub
		if(robot.getCurrentFloor() < destination){
            robot.setCurrentFloor(robot.getCurrentFloor() + 1);
        } else {
        	robot.setCurrentFloor(robot.getCurrentFloor() - 1);
        }
	}

	@Override
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException {
		// TODO Auto-generated method stub
		robot.getDelivery().deliver(robot.getDeliverItem());
		
		robot.setDeliverItem(null);
		robot.setDeliveryCounter(robot.getDeliveryCounter() + 1);
        if(robot.getDeliveryCounter() > 2){  // Implies a simulation bug
        	throw new ExcessiveDeliveryException();
        }
	}
}
