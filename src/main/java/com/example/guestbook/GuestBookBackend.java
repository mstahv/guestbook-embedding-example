package com.example.guestbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GuestBookBackend {

	private static GuestBookBackend INSTANCE = new GuestBookBackend();

	private Map<String, List<Post>> topics = new HashMap<>();
	private Map<String, Set<MainView>> subscribers = new HashMap<>();

	public synchronized void post(String topic, Post post) {
		getPosts(topic).add(post);
		subscribers.get(topic).forEach(s -> {
			s.getUI().get().access(() -> {
				s.addPostToUI(post);
			});

		});
		;
	}

	public List<Post> getPosts(String topic) {
		List<Post> list = topics.get(topic);
		if (list == null) {
			list = new ArrayList<>();
			topics.put(topic, list);
		}
		return list;
	}

	public static GuestBookBackend get() {
		return INSTANCE;
	}

	private Set<MainView> getSubscribers(String topic) {
		Set<MainView> list = subscribers.get(topic);
		if (list == null) {
			list = new HashSet<>();
			subscribers.put(topic, list);
		}
		return list;
	}

	public synchronized void subscribe(MainView mainView) {
		getSubscribers(mainView.getTopic()).add(mainView);
	}

	public synchronized void unsubscribe(MainView mainView) {
		getSubscribers(mainView.getTopic()).remove(mainView);
	}
}
