package com.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.models.User;

/**
 * Application Lifecycle Listener implementation class UserSessionListener
 *
 */
@WebListener
public class UserSessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public UserSessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
    	// The follwing two lines are for testing purpose
//        User user = new User();
//        event.getSession().setAttribute("user", user);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        // TODO Auto-generated method stub
    }
	
}
