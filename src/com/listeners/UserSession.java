package com.listeners;

import java.util.ArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.models.Parcel.User;
/**
 * Updated by Sydney 2/29/2012
 */
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
	 private ArrayList<User> usersList;
	 private int num_sessions;
    /**
     * Default constructor. 
     */
    public UserSession() {
    	usersList = new ArrayList<User>();
    	num_sessions = 0;
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        //assuming loginSevlet will update these perameters    	
    	num_sessions++;
    	User user = new User(null, null, null, -1, null);
    	usersList.add(user);
    	arg0.getSession().setAttribute("User",user);
    	arg0.getSession().setAttribute("SessionId", num_sessions-1);

    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        int sessionId = (Integer) arg0.getSession().getAttribute("SessionId");
        usersList.remove(sessionId-1);
        num_sessions--;
    }
	
}
