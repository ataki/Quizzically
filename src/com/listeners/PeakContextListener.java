package com.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.backend.*;

/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
@WebListener
public class PeakContextListener implements javax.servlet.ServletContextListener {

    /**
     * Default constructor. 
     */
    public PeakContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see PeakContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
    	
    	// Initialize all managers
    	AnnouncementManager announcementManager = new AnnouncementManager();
    	QuizManager quizManager = new QuizManager();
    	QuestionManager questionManager = new QuestionManager();
    	MessageManager messageManager = new MessageManager();
    	ActivityManager activityManager = new ActivityManager();
    	//FriendManager friendManager = new FriendManager();
    	AchievementManager achievementManager = new AchievementManager();
    	
    	// Store managers into ServletContext
    	context.setAttribute("announcementManager", announcementManager);
    	context.setAttribute("quizManager", quizManager);
    	context.setAttribute("questionManager", questionManager);
    	context.setAttribute("messageManager", messageManager);
    	context.setAttribute("activityManager", activityManager);
    	//context.setAttribute("friendManager", friendManager);
    	context.setAttribute("achievementManager", achievementManager);

    }

	/**
     * @see PeakContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
