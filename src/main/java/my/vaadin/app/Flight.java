package my.vaadin.app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Flight Descriptions
 */
@SuppressWarnings("serial")
public class Flight implements Serializable, Cloneable {

	private Long id;
	
	private String origin = "";
	
	private String destination = "";
	
	private LocalDate flightDate;
	
	private FlightStatus status;
	
	private String flightNumber = "";
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}
	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public FlightStatus getStatus() {
		return status;
	}
	
	public void setStatus(FlightStatus status) {
		this.status = status;
	}
	
	public LocalDate getFlightDate() {
		return flightDate;
	}
	
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public boolean isPersisted() {
		return id != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}
		
		if (obj instanceof Flight && obj.getClass().equals(getClass())) {
			return this.id.equals(((Flight) obj).id);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}
	
	@Override
	public Flight clone() throws CloneNotSupportedException {
		return (Flight) super.clone();
	}
	
	@Override
	public String toString() {
		return origin + " " + destination;
	}
}
