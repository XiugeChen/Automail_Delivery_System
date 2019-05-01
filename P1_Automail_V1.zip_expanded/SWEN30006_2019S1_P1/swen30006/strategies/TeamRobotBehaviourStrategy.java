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
				robot.moveUpperFloor();
			} else {
				robot.moverLowerFloor();
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
			robot.makeDeliver();
		
		robot.setCurrentTeamState(RobotTeamState.SINGLE);
		robot.removeDeliverItem();
		robot.increaseDeliveryCounter();
        if(robot.getDeliveryCounter() > 2){  // Implies a simulation bug
        	throw new ExcessiveDeliveryException();
        }
	}
}
