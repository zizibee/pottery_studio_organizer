package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// A class that represents a log of studio events.
/**
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
	/** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;
	
	/** 
	 * Prevent external construction.
	 * (Singleton Design Pattern).
	 */
    // EFFECTS: constructs EventLog with an empty events list
    private EventLog() {
        events = new ArrayList<Event>();
    }
	
	/**
	 * Gets instance of EventLog - creates it
	 * if it doesn't already exist.
	 * (Singleton Design Pattern)
	 * @return  instance of EventLog
	 */
    // MODIFIES: this
    // EFFECTS: if EventLog is null, construct the EventLog; otherwise return the EventLog
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }
	
	/**
	 * Adds an event to the event log.
	 * @param e the event to be added
	 */
    // MODIFIES: this
    // EFFECTS: add the given event to events list
    public void logEvent(Event e) {
        events.add(e);
    }
	
	/**
	 * Clears the event log and logs the event.
	 */
    // MODIFIES: this
    // EFFECTS: clears events list, adds a new event to events list indicating log was cleared
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: return events list iterator
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
