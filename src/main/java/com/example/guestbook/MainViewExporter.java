package com.example.guestbook;

import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.webcomponent.WebComponent;

@Push
public class MainViewExporter extends WebComponentExporter<MainView>{

	public MainViewExporter() {
		super("guest-book");
		// read topic property and pass it to the Java component, default to ""
		addProperty("topic", "")
			.onChange((c, t) -> c.setTopic(t));
	}

	@Override
	protected void configureInstance(WebComponent<MainView> webComponent, MainView component) {
		// NOP
	}

}
