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
    	MessageManager messageManager = new MessageManager();
    	AnnouncementManager announcementManager = new AnnouncementManager();
    	context.setAttribute("messageManager", messageManager);
    	context.setAttribute("announcementManager", announcementManager);
    }

	/**
     * @see PeakContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
