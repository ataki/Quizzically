package com.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class UserSession
 * 
 * Maintains state for a user login session. 
 * 
 * STATE:
 * - When a user is logged in, we keep track of the user
 * object that logged in. 
 * - We also keep track of progress when taking a quiz,
 * and when a user has finished taking the quiz.
 * 
 * ON DEMAND:
 * When visiting their home page or quiz page, a user
 * may want to know about the past history and performance
 * of various users. This information is dynamically presented
 * via a call to the backend; we don't keep this data in the 
 * session state.
 * 
 */
@WebListener
public class UserSession implements HttpSessionListener {
	/**
	 * User object for this session
	 */
	// private User user;
	
    /**
     * Default constructor. 
     */
    public UserSession() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
