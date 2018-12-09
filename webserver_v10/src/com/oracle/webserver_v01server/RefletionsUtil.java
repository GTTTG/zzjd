package com.oracle.webserver_v01server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.oracle.webserver_v01servlet.WebServlet;

public class RefletionsUtil {
	private static Map<String, Class<?>> maping = new HashMap<>();

	static {
		Reflections reflections = new Reflections();
		Set<Class<?>> set = reflections.getTypesAnnotatedWith(WebServlet.class);

		for (Class<?> clazz : set) {
			maping.put(clazz.getAnnotation(WebServlet.class).value(), clazz);
		}
	}

	public static Class<?> getClass(String url) {
		Class<?> clazz = maping.get(url);
		if (clazz == null) {
			if (url.endsWith(".html")) {
				url = "/static";
			} else {
				url = "/Welcome";
			}
		}
		return maping.get(url);
	}

}
