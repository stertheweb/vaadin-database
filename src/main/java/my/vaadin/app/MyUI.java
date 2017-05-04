package my.vaadin.app;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private AmericanAirlines american = AmericanAirlines.getInstance();
	private Grid<Flight> grid = new Grid<>(Flight.class);
	private TextField filterText = new TextField();
	private FlightForm form = new FlightForm(this);

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new Label("<b>American Airlines Flight Database<b>", ContentMode.HTML));

		filterText.setPlaceholder("filter by airport...");
		filterText.addValueChangeListener(e -> updateList());
		filterText.setValueChangeMode(ValueChangeMode.LAZY);

		Button clearFilterTextButton = new Button(FontAwesome.TIMES);
		clearFilterTextButton.setDescription("Clear Filter");
		clearFilterTextButton.addClickListener(e -> filterText.clear());

		CssLayout filtering = new CssLayout();
		filtering.addComponents(filterText, clearFilterTextButton);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		Button addFlightButton = new Button("Add New Flight");
		addFlightButton.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setFlight(new Flight());
		});

		HorizontalLayout toolbar = new HorizontalLayout(filtering, addFlightButton);

		grid.setColumns("origin", "destination",
				"flightNumber", "status", "flightDate");

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSizeFull();
		grid.setSizeFull();
		main.setExpandRatio(grid, 1);

		layout.addComponents(toolbar, main);

		// Gather list of Flights from AmericanAirlines and assign it to Grid
		updateList();

		setContent(layout);

		form.setVisible(false);

		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				form.setVisible(false);
			}

			else {
				form.setFlight(event.getValue());
			}
		});

	}

	public void updateList() {
		List<Flight> flights = american.findAll(filterText.getValue());
		grid.setItems(flights);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
