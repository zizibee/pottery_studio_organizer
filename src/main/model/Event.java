package model;

import java.util.Calendar;
import java.util.Date;

// Code is taken from AlarmSystem example

// A class representing a studio event
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;
	
	/**
	 * Creates an event with the given description
	 * and the current date/time stamp.
	 * @param description  a description of the event
	 */
    // EFFECTS: constructs event, sets dateLogged to current date/time and sets description to given description
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }
	
	/**
	 * Gets the date of this event (includes time).
	 * @return  the date of the event
	 */
    // EFFECTS: returns date of event
    public Date getDate() {
        return dateLogged;
    }
	
	/**
	 * Gets the description of this event.
	 * @return  the description of the event
	 */
    // EFFECTS: returns description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns true if given object is not null, is of same class, and has an equal description;
    //          returns false otherwise
    @Override
	public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged) && this.description.equals(otherEvent.description));
    }

    // EFFECTS: returns hashcode created using hash constant, dateLogged and description
    @Override
	public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns a string of the dateLogged and description
    @Override
	public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
