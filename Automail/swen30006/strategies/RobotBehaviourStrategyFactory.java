package strategies;

import automail.Robot;
import automail.Robot.RobotTeamState;

/**
 * Singleton robot behavior strategy factory
 * @author Xiuge Chen and Daniel Marshall
 */
public class RobotBehaviourStrategyFactory {
	private static RobotBehaviourStrategyFactory factoryInstance;
	
	/**
	 * Constructor
	 */
	private RobotBehaviourStrategyFactory() {}
	
	/**
	 * Singleton class instance getter, ensure only one instance will be generated
	 * @return single instance of RobotBehaviourStrategyFactory
	 */
	public static RobotBehaviourStrategyFactory getFactory() {
		if (factoryInstance == null)
			factoryInstance = new RobotBehaviourStrategyFactory();
		
		return factoryInstance;
	}
	
	/**
	 * determine the behavior strategies of a specific robot based on the team status of it
	 * @param robot the robot
	 * @return instance of robot behavior strategy based on its team status
	 */
	public IRobotBehaviourStrategy getStrategy(Robot robot) {
		if (robot.getCurrentTeamState() == RobotTeamState.SINGLE)
			return new SingleRobotBehaviourStrategy();
		
		return new TeamRobotBehaviourStrategy();
	}
}
