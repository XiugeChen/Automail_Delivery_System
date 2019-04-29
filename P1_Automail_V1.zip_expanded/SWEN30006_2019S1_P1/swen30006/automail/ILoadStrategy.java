package automail;

import java.util.LinkedList;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

public interface ILoadStrategy {
	
	// Load item to either single robot or robot team. Returns true if the 
	// robot/robot team can accept another item from the mail pool (ie in tube)
	// or false if the robot can't accept another item from the mail pool
	public boolean loadItem(ListIterator<Robot> i, MailItem item) throws ItemTooHeavyException;

}
