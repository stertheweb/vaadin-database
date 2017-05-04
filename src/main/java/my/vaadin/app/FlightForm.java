package my.vaadin.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class FlightForm extends FormLayout {

	private TextField origin = new TextField("Origin");
	private TextField destination = new TextField("Destination");
	private TextField flightNumber = new TextField("Flight Number");
	private NativeSelect<FlightStatus> status = new NativeSelect<>("Flight Status");
	private DateField flightDate = new DateField("Flight Date");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private AmericanAirlines american = AmericanAirlines.getInstance();
	private Flight flight;
	private MyUI myUI;
	private Binder<Flight> binder = new Binder<>(Flight.class);
	
	public FlightForm(MyUI myUI) {
		this.myUI = myUI;
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(origin, destination, flightNumber, status, flightDate, buttons);
		
		status.setItems(FlightStatus.values());
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		binder.bindInstanceFields(this);
		
		save.addClickListener(e -> this.save());
		delete.addClickListener(e -> this.delete());
	}
	
	public void setFlight(Flight flight) {
		this.flight = flight;
		binder.setBean(flight);
		
		delete.setVisible(flight.isPersisted());
		setVisible(true);
		origin.selectAll();
	}

	private void delete() {
		american.delete(flight);
		myUI.updateList();
		setVisible(false);
	}
	
	private void save() {
		american.save(flight);
		myUI.updateList();
		setVisible(false);
	}
}
