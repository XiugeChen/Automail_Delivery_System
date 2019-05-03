package strategies;

import java.util.ArrayList;
import java.util.ListIterator;

import automail.MailClassifier;
import automail.MailItem;
import automail.Robot;
import automail.Robot.RobotTeamState;
import exceptions.ItemTooHeavyException;

/**
 * loading strategy for robot team (over one robots)
 * @author Xiuge Chen and Daniel Marshall
 */
public class LoadRobotTeamStrategy implements ILoadStrategy {
	
	/** minimum number of robots needed to carry this item */
	private int numRobotsNeeded;
	
	public LoadRobotTeamStrategy(int numRobotsNeeded) {
		this.numRobotsNeeded = numRobotsNeeded;
	}

	@Override
	public boolean loadItem(ListIterator<Robot> robotI, int numRobots, 
			MailItem item) throws ItemTooHeavyException {
		
		checkWeight(item, numRobots);
		
		ArrayList<Robot> team = new ArrayList<>();
		
		// allocate empty robots in team
		while(robotI.hasNext() && numRobotsNeeded > team.size()) {
			Robot robot = robotI.next();
			
			if (robot.isEmpty())
				team.add(robot);
		}

		// if there are enough number of robots
		if (numRobotsNeeded == team.size()) {
			for (Robot robot: team) {
				robot.addToHand(item);
				
				// let the first robot be the "leader" who reports final delivery
				// let all the robots (except the first one) in team cooperate with the first robot
				if (numRobotsNeeded == team.size())
					robot.setCurrentTeamState(RobotTeamState.TEAM_LEADER);
				else 
					robot.setCurrentTeamState(RobotTeamState.TEAM_MEMBER);
				
				numRobotsNeeded--;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void checkWeight(MailItem item, int numRobots) 
			throws ItemTooHeavyException {
		
		boolean notEnoughRobots = numRobotsNeeded > numRobots;
		boolean exceedWeight = item.getWeight() > 
			MailClassifier.getInstance().getWeightLimit(numRobotsNeeded);
		
		if ( exceedWeight || notEnoughRobots) 
			throw new ItemTooHeavyException();
	}
}
