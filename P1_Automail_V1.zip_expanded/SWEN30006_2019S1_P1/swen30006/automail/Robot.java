package automail;

import exceptions.ExcessiveDeliveryException;
import strategies.IMailPool;
import strategies.IRobotBehaviourStrategy;
import strategies.RobotBehaviourStrategyFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * The robot delivers mail!
 */
public class Robot {
    IMailDelivery delivery;
    protected final String id;
    /** Possible states the robot can be in */
    public enum RobotState { DELIVERING, WAITING, RETURNING }
    public RobotState current_state;
    /**  */
    public enum RobotTeamState {SINGLE, TEAM_LEADER, TEAM_MEMBER};
    private RobotTeamState current_team_state;
    private int current_floor;
    private int destination_floor;
    private IMailPool mailPool;
    private boolean receivedDispatch;
    
    private MailItem deliveryItem = null;
    private MailItem tube = null;
    
    private int deliveryCounter;
    private int moveIntervalCounter;

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
    public Robot(IMailDelivery delivery, IMailPool mailPool){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
    	current_state = RobotState.RETURNING;
    	current_team_state = RobotTeamState.SINGLE;
        current_floor = Building.MAILROOM_LOCATION;
        this.delivery = delivery;
        this.mailPool = mailPool;
        this.receivedDispatch = false;
        this.deliveryCounter = 0;
        this.moveIntervalCounter = 0;
    }
    
    public void dispatch() {
    	receivedDispatch = true;
    }
    
    public void makeDeliver() {
    	if (deliveryItem != null && current_floor == destination_floor)
    		delivery.deliver(deliveryItem);
    }
    
    public void removeDeliverItem() {
    	deliveryItem = null;
    }
    
    public void increaseDeliveryCounter() {
    	deliveryCounter++;
    }
    
    public void moveUpperFloor() {
    	if (current_floor < Building.FLOORS)
    		current_floor++;
    }
    
    public void moverLowerFloor() {
    	if (current_floor > Building.LOWEST_FLOOR)
    		current_floor--;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void step() throws ExcessiveDeliveryException {    	
    	switch(current_state) {
    		/** This state is triggered when the robot is returning to the mailroom after a delivery */
			case RETURNING:
				returnningStep();
				break;
			case WAITING:
				waitingStep();
				break;
			case DELIVERING:
				deliveringStep();
				break;
    	}
    }
    
    private void returnningStep() {
    	IRobotBehaviourStrategy behaviourStrategy = RobotBehaviourStrategyFactory.getFactory().getStrategy(this);
    	
    	/** If its current position is at the mailroom, then the robot should change state */
        if(current_floor == Building.MAILROOM_LOCATION){
        	if (tube != null) {
        		mailPool.addToPool(tube);
                System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), tube.toString());
                tube = null;
        	}
			/** Tell the sorter the robot is ready */
			mailPool.registerWaiting(this);
        	changeState(RobotState.WAITING);
        	waitingStep();
        } else {
        	/** If the robot is not at the mailroom floor yet, then move towards it! */
        	behaviourStrategy.moveTowards(this, Building.MAILROOM_LOCATION);
        }
    }
    
    private void waitingStep() {
    	/** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
        if(!isEmpty() && receivedDispatch){
        	receivedDispatch = false;
        	deliveryCounter = 0; // reset delivery counter
			setRoute();
        	changeState(RobotState.DELIVERING);
        }
    }
    
    private void deliveringStep() throws ExcessiveDeliveryException {
    	IRobotBehaviourStrategy behaviourStrategy = RobotBehaviourStrategyFactory.getFactory().getStrategy(this);
    	
    	if (current_floor == destination_floor) { // If already here drop off either way
            /** Delivery complete, report this to the simulator! */
    		behaviourStrategy.deliverItem(this);
            
            /** Check if want to return, i.e. if there is no item in the tube*/
            if (tube == null)
            	changeState(RobotState.RETURNING);
            else {
                /** If there is another item, set the robot's route to the location to deliver the item */
                deliveryItem = tube;
                tube = null;
                setRoute();
                changeState(RobotState.DELIVERING);
            }
		} else {
    		/** The robot is not at the destination yet, move towards it! */
			behaviourStrategy.moveTowards(this, destination_floor);
		}
    }

    /**
     * Sets the route for the robot
     */
    private void setRoute() {
        /** Set the destination floor */
        destination_floor = deliveryItem.getDestFloor();
    }
    
    private String getIdTube() {
    	return String.format("%s(%1d)", id, (tube == null ? 0 : 1));
    }
    
    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    private void changeState(RobotState nextState){
    	assert(!(deliveryItem == null && tube != null));
    	if (current_state != nextState) {
            System.out.printf("T: %3d > %7s changed from %s to %s%n", Clock.Time(), getIdTube(), current_state, nextState);
    	}
    	current_state = nextState;
    	if(nextState == RobotState.DELIVERING){
            System.out.printf("T: %3d > %7s-> [%s]%n", Clock.Time(), getIdTube(), deliveryItem.toString());
    	}
    }
    
	static private int count = 0;
	static private Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();

	@Override
	public int hashCode() {
		Integer hash0 = super.hashCode();
		Integer hash = hashMap.get(hash0);
		if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
		return hash;
	}

	public boolean isEmpty() {
		return (deliveryItem == null && tube == null);
	}
	
	// Is Tube full
	public boolean isTubeFull() {
		return (this.tube != null);
	}
	
	// Is hand full
	public boolean isHandFull() {
		return (this.deliveryItem != null);
	}

	public void addToHand(MailItem mailItem) {
		assert(deliveryItem == null);
		deliveryItem = mailItem;
	}

	public void addToTube(MailItem mailItem) {
		assert(tube == null);
		tube = mailItem;
	}
	
	// getter and setter 
	public int getCurrentFloor() {
		return this.current_floor;
	}
	public int getDeliveryCounter() {
		return this.deliveryCounter;
	}
	public RobotTeamState getCurrentTeamState() {
		return this.current_team_state;
	}
	public int getMoveIntervalCounter() {
		return this.moveIntervalCounter;
	}
	
	public void setCurrentTeamState(RobotTeamState newTeamState) {
		this.current_team_state = newTeamState;
	}
	public void setMoveIntervalCounter(int moveIntervalCounter) {
		this.moveIntervalCounter = moveIntervalCounter;
	}
}
