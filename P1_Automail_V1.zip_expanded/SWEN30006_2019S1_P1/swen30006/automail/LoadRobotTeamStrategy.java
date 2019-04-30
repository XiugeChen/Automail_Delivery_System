package automail;

import java.util.ArrayList;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

public class LoadRobotTeamStrategy implements ILoadStrategy {
	
	private int numRobots;
	
	public LoadRobotTeamStrategy(int numRobots) {
		this.numRobots = numRobots;
	}

	@Override
	public boolean loadItem(ListIterator<Robot> robotI, MailItem item) throws ItemTooHeavyException {
		// TODO Auto-generated method stub
		checkWeight(item);
		
		ArrayList<Robot> team = new ArrayList<>();
		
		// allocate empty robots in team
		while(robotI.hasNext()) {
			Robot robot = robotI.next();
			
			if (robot.isEmpty())
				team.add(robot);
			
			if (numRobots == team.size())
				break;
		}
		
		// if there are enough number of robots
		if (numRobots == team.size()) {
			for (Robot robot: team) {
				robot.addToHand(item);
				
				// let all the robots (except last one) in team cooperate with the last robot
				numRobots--;
				if (numRobots > 0)
					robot.cooperate();
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void checkWeight(MailItem item) throws ItemTooHeavyException {
		// TODO Auto-generated method stub
		if (numRobots == MailClassifier.MEDIUM_TEAM) {
			if (item.getWeight() > MailClassifier.PAIR_MAX_WEIGHT)
				throw new ItemTooHeavyException();
		}
		else if (numRobots == MailClassifier.LARGE_TEAM) {
			if (item.getWeight() > MailClassifier.TRIPLE_MAX_WEIGHT) 
				throw new ItemTooHeavyException();
		}
	}

}
