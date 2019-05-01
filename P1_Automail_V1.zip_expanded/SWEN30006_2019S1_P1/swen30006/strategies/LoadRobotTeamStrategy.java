package strategies;

import java.util.ArrayList;
import java.util.ListIterator;

import automail.MailClassifier;
import automail.MailItem;
import automail.Robot;
import automail.Robot.RobotTeamState;
import exceptions.ItemTooHeavyException;

public class LoadRobotTeamStrategy implements ILoadStrategy {
	
	private int numRobotsNeeded;
	
	public LoadRobotTeamStrategy(int numRobotsNeeded) {
		this.numRobotsNeeded = numRobotsNeeded;
	}

	@Override
	public boolean loadItem(ListIterator<Robot> robotI, int numRobots, MailItem item) throws ItemTooHeavyException {
		// TODO Auto-generated method stub
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
				
				// let all the robots (except last one) in team cooperate with the last robot
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
	public void checkWeight(MailItem item, int numRobots) throws ItemTooHeavyException {
		// TODO Auto-generated method stub
		// 
		if (item.getWeight() > MailClassifier.getInstance().getWeightLimit(numRobotsNeeded) || numRobotsNeeded > numRobots) 
			throw new ItemTooHeavyException();
	}
}
