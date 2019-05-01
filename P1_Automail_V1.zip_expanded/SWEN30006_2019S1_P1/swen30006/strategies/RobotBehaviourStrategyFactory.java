package strategies;

import automail.Robot;
import automail.Robot.RobotTeamState;

public class RobotBehaviourStrategyFactory {
	private static RobotBehaviourStrategyFactory factoryInstance;
	
	private RobotBehaviourStrategyFactory() {}
	
	public static RobotBehaviourStrategyFactory getFactory() {
		if (factoryInstance == null)
			factoryInstance = new RobotBehaviourStrategyFactory();
		
		return factoryInstance;
	}
	
	public IRobotBehaviourStrategy getStrategy(Robot robot) {
		if (robot.getCurrentTeamState() == RobotTeamState.SINGLE)
			return new SingleRobotBehaviourStrategy();
		
		return new TeamRobotBehaviourStrategy();
	}
}
