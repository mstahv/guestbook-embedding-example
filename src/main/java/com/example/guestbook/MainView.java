package com.example.guestbook;

import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

/*
 * The Route annotation in actual deployment is obsolete as this Vaadin app 
 * is meant to be embedded. But it doesn't hurt and helps the development. 
 */
@Route("what")
public class MainView extends VerticalLayout implements HasUrlParameter<String> {

	private String topic;
	private VerticalLayout messages;

	/*
	 * This is normally not in use, but handy for development. Embedded app gets the
	 * topic as web component attribute.
	 */
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		if (parameter == null) {
			parameter = "";
		}
		setTopic(parameter);
	}

	/**
	 * Sets the topic for the Guestbook and initializes the view.
	 * 
	 * @param topic
	 */
	void setTopic(String topic) {
		setSpacing(false);
		this.topic = topic;
		add(new H2("Guestbook"));

		messages = new VerticalLayout();
		messages.setPadding(false);
		messages.setSpacing(false);
		add(messages);

		GuestBookBackend.get().getPosts(topic).forEach(this::addPostToUI);
		GuestBookBackend.get().subscribe(this);

		TextField name = new TextField("Name");
		TextArea message = new TextArea("Message");
		Button send = new Button("Send");
		send.addClickListener(e -> post(new Post(name.getValue(), message.getValue())));
		add(new Hr(), name, message, send);

	}

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public void addPostToUI(Post p) {
		messages.add(
				new Paragraph(String.format("%s %s : %s", p.getTs().format(formatter), p.getName(), p.getMessage())));
	}

	private void post(Post p) {
		GuestBookBackend.get().post(topic, p);
	}	
	
	public String getTopic() {
		return topic;
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		GuestBookBackend.get().unsubscribe(this);
		super.onDetach(detachEvent);
	}
}
