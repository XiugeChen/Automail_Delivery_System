package strategies;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import exceptions.ItemTooHeavyException;

public class MailPool implements IMailPool {

	private class Item {
		int priority;
		int destination;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.priority < i2.priority) {
				order = 1;
			} else if (i1.priority > i2.priority) {
				order = -1;
			} else if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	private int numRobots;

	public MailPool(int nrobots){
		// Start empty
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		numRobots = nrobots;
	}

	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		pool.add(item);
		pool.sort(new ItemComparator());
	}
	
	@Override
	public void step() throws ItemTooHeavyException {
		try{
			ListIterator<Robot> robotI = robots.listIterator();
			while (robotI.hasNext()) loadItem(robotI);
			
			ListIterator<Robot> newRobotI = robots.listIterator();
			dispatchRobot(newRobotI);
		} catch (Exception e) { 
            throw e; 
        } 
	} 
	
	/**
	 * 
	 * @param i
	 * @throws ItemTooHeavyException
	 */
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		ListIterator<Item> j = pool.listIterator();
		ILoadStrategy loadStrategy;
		
		if (pool.size() > 0) {
			try {
				while (j.hasNext()) {
					MailItem mailItem = j.next().mailItem;
					
					loadStrategy = LoadStrategyFactory.getFactory().getStrategy(mailItem);
					
					if (loadStrategy.loadItem(i, numRobots, mailItem))
						j.remove();
				}
			} catch (Exception e) {
				throw e;
			}
		}
		// just iterate through the robot if nothing in the pool
		else {
			i.next();
		}
	}
	
	/**
	 * dispatch any robot that is not empty (has items in its hand or tube)
	 * @param robotI: robot list iterator
	 */
	private void dispatchRobot(ListIterator<Robot> robotI) {
		while (robotI.hasNext()) {
			Robot robot = robotI.next();
			
			if (!robot.isEmpty()) {
				robot.dispatch();
				robotI.remove();
			}
		}
	}

	@Override
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

}
