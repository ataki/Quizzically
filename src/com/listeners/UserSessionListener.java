package com.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.models.Quiz;
import com.models.User;

/**
 * Application Lifecycle Listener implementation class UserSessionListener
 * 
 * TODO: 
 * 	- Remember that after taking a quiz, we create a "Quiz.result" object
 *  	to display on the account. We set a result attribute to the 
 *  	session variable. THIS NEEDS TO BE CLEARED when the hits "finalize",
 *  	so that users visiting/refreshing the page don't see an old results page.
 *   
 */
@WebListener
public class UserSessionListener implements HttpSessionListener {

	User user;
	
	/* 
	 * Variables that pertain to taking quizzes 
	 */
	Quiz quiz;
	int curQuestionNum;
	
    /**
     * Default constructor. 
     */
    public UserSessionListener() {
        // Initialize variables to null until
    	// session is created.
    	user = null;
    	quiz = null;
    	curQuestionNum = -1;
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
    	// The follwing two lines are for testing purpose
        user = new User();
        event.getSession().setAttribute("user", user);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        // free references to the object
    	user = null;
        quiz = null;
        curQuestionNum = -1; // not necessary, but just in case
    }
	
}
