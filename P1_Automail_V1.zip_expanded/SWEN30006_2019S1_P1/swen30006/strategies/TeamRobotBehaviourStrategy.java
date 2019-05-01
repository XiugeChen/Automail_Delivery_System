package strategies;

import automail.Robot;
import automail.Robot.RobotTeamState;
import exceptions.ExcessiveDeliveryException;

public class TeamRobotBehaviourStrategy implements IRobotBehaviourStrategy {
	
	private static final int MOVE_INTERVAL = 2;

	@Override
	public void moveTowards(Robot robot, int destination) {
		// TODO Auto-generated method stub		
		if (robot.getMoveIntervalCounter() >= MOVE_INTERVAL) {
			if(robot.getCurrentFloor() < destination){
				robot.setCurrentFloor(robot.getCurrentFloor() + 1);
			} else {
				robot.setCurrentFloor(robot.getCurrentFloor() - 1);
			}
			
			robot.setMoveIntervalCounter(0);
		}
		else 
			robot.setMoveIntervalCounter(robot.getMoveIntervalCounter() + 1);
	}

	@Override
	public void deliverItem(Robot robot) throws ExcessiveDeliveryException {
		// TODO Auto-generated method stub
		if (robot.getCurrentTeamState() == RobotTeamState.TEAM_LEADER)
			robot.getDelivery().deliver(robot.getDeliverItem());
		
		robot.setCurrentTeamState(RobotTeamState.SINGLE);
		robot.setDeliverItem(null);
		robot.setDeliveryCounter(robot.getDeliveryCounter() + 1);
        if(robot.getDeliveryCounter() > 2){  // Implies a simulation bug
        	throw new ExcessiveDeliveryException();
        }
	}
}
