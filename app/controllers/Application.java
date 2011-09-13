package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Endpoint;
import models.Subscription;
import models.Topic;
import play.libs.WS;
import play.libs.WS.WSRequest;
import play.mvc.Controller;
import play.templates.TemplateLoader;

public class Application extends Controller {

	public static void index() {
		// TODO : need to render endpoints...
		render();
	}

	public static void postNotification(String topicName, String topicPrefix,
			String topicURL, String subscriber, String endpoint, String content) {
		Map<String, Object> map = new HashMap<String, Object>();

		// will be nice to have this type of thing with the same code available
		// in the real renderTemplate method...
		map.put("content", content);
		map.put("topicName", topicName);
		map.put("topicURL", topicURL);
		map.put("topicPrefix", topicPrefix);

		String rendered = TemplateLoader.load("Application/notifytemplate.xml")
				.render(map);

		WSRequest request = WS.url(endpoint)
				.setHeader("content-type", "application/soap+xml")
				.body(rendered);
		try {
			request.postAsync();
			flash.success("Thanks for posting %s!", content);
		} catch (RuntimeException e) {
			e.printStackTrace();
			flash.error("Can not send notification to %s, cause '%s'!",
					endpoint, e.getMessage());
		}
		notification(topicName);
	}

	public static void postSubscribe(String topicName, String topicPrefix,
			String topicURL, String subscriber, String endpoint) {
		
		// TODO : validate fields
		
		Map<String, Object> map = new HashMap<String, Object>();

		// will be nice to have this type of thing with the same code available
		// in the real renderTemplate method...
		map.put("topicName", topicName);
		map.put("topicURL", topicURL);
		map.put("topicPrefix", topicPrefix);
		map.put("subscriber", subscriber);
		
		String rendered = TemplateLoader.load(
				"Application/subscribetemplate.xml").render(map);

		WSRequest request = WS.url(endpoint)
				.setHeader("content-type", "application/soap+xml")
				.body(rendered);
		try {
			request.postAsync();
			flash.success("Subscription done!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			flash.error("Can not send subscribe to %s, cause '%s'!", endpoint,
					e.getMessage());
		}
		subscribe(topicName);
	}

	/**
	 * Subscribe page
	 */
	public static void subscribe(String topicName) {
		List<Endpoint> endpoints = Endpoint.getFromType("producer");
		List<Subscription> subscriptions = Subscription.all().fetch();
		Topic topic = null;
		if (topicName != null) {
		 topic = Topic.find("byName", topicName).first();
		} else {
		}
		String subscriber = request.getBase() + "/TODO";
		render(endpoints, subscriptions, topic, subscriber);
	}

	public static void notification(String topicName) {
		List<Endpoint> endpoints = Endpoint.getFromType("consumer");
		Topic topic = null;
		if (topicName != null) {
		 topic = Topic.find("byName", topicName).first();
		} else {
		}
		render(endpoints, topic);
	}
	
	public static void topics() {
		List<Topic> topics = Topic.findAll();
		render(topics);
	}

	public static void logger() {
		render();
	}

	public static void about() {
		index();
	}

}