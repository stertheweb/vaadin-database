package my.vaadin.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Database of American Airlines Flights
 */

public class AmericanAirlines {

	private static AmericanAirlines instance;
	private static final Logger LOGGER = Logger.getLogger(AmericanAirlines.class.getName());

	private final HashMap<Long, Flight> routes = new HashMap<>();
	private long nextId = 0;

	private AmericanAirlines() {

	}

	public static AmericanAirlines getInstance() {
		if (instance == null) {
			instance = new AmericanAirlines();
			instance.ensureTestData();
		}
		return instance;
	}

	public synchronized List<Flight> findAll() {
		return findAll(null);
	}

	public synchronized List<Flight> findAll(String stringFilter) {
		ArrayList<Flight> arrayList = new ArrayList<>();
		for(Flight route : routes.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| route.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(route.clone());
				}
			} catch (CloneNotSupportedException cex) {
				Logger.getLogger(AmericanAirlines.class.getName()).log(Level.SEVERE, null, cex);
			}
		}
		Collections.sort(arrayList, new Comparator<Flight>() {

			@Override
			public int compare (Flight o1, Flight o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized List<Flight> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Flight> arrayList = new ArrayList<>();
		for(Flight route : routes.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| route.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(route.clone());
				}
			} catch (CloneNotSupportedException cex) {
				Logger.getLogger(AmericanAirlines.class.getName()).log(Level.SEVERE, null, cex);
			}
		}
		Collections.sort(arrayList, new Comparator<Flight>() {

			@Override
			public int compare (Flight o1, Flight o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	public synchronized long count() {
		return routes.size();
	}

	public synchronized void delete (Flight value) {
		routes.remove(value.getId());
	}

	public synchronized void save(Flight entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "Flight is null");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Flight) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		routes.put(entry.getId(), entry);
	}

	private void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] routes = new String[] { "Miami NewYork-JFK", "NewYork-JFK Kingston",
					"NewYork-JFK Mumbai", "LosAngeles NewYork-JFK", "Miami Washington-Reagan",
					"NewYork-JFK SanFrancisco", "NewYork-JFK MontegoBay", "Miami SanJuan",
					"NewYork-JFK PortOfSpain", "LosAngeles London-Heathrow", "LosAngeles Phoenix",
					"Dallas-FtWorth Phoenix", "Phoenix SaltLakeCity", "LosAngeles LasVegas",
					"LosAngeles SanFrancisco", "Dallas-FtWorth Seattle", "Phoenix Denver",
					"Phoenix Houston-IAH", "Dallas-FtWorth OklahomaCity", "Phoenix OklahomaCity",
					"Dallas-FtWorth LasVegas", "Phoenix LasVegas", "Charlotte Munich",
					"Philadelphia Munich", "Philadelphia Charlotte", "Charlotte Phoenix",
					"Dallas-FtWorth Washington-Reagan", "Dallas-FtWorth Miami", "Phoenix Portland",
			"Dallas-FtWorth NewYork-JFK" };
			Random random = new Random(0);
			for (String route : routes) {
				String[] split = route.split(" ");
				Flight f = new Flight();
				f.setOrigin(split[0]);
				f.setDestination(split[1]);
				f.setFlightNumber(Integer.toString(random.nextInt(5000)));
				f.setStatus(FlightStatus.values()
						[random.nextInt(FlightStatus.values().length)]);
				f.setFlightDate(LocalDate.now());
				save(f);
			}
		}

	}

}
