package com.utm.searchengine.source;

import javax.servlet.http.HttpServletRequest;

public class ServeSessionID {
	
	public static String getSessionID(HttpServletRequest req, String id) {
		return (String) req.getSession().getAttribute(id);
	}

}
