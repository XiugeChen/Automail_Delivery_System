package strategies;

import automail.Robot;
import automail.Robot.RobotTeamState;
import exceptions.ExcessiveDeliveryException;

/**
 * behavior strategy for robot in team status
 * @author Xiuge Chen and Daniel Marshall
 */
public class TeamRobotBehaviourStrategy implements IRobotBehaviourStrategy {
	
	/** the interval of team moving */
	private static final int MOVE_INTERVAL = 2;

	@Override
	public void moveTowards(Robot robot, int destination) {
		// move only when counter is no less than the interval
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
		// only the single team leader is responsible for make final delivery
		// avoiding multiple delivery of same item
		if (robot.getCurrentTeamState() == RobotTeamState.TEAM_LEADER)
			robot.makeDeliver();
		
		robot.setCurrentTeamState(RobotTeamState.SINGLE);
		robot.removeDeliverItem();
		robot.increaseDeliveryCounter();
		
		// Implies a simulation bug
        if(robot.getDeliveryCounter() > 2){ 
        	throw new ExcessiveDeliveryException();
        }
	}
}
