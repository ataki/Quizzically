package com.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.backend.MessageManager;

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
    	MessageManager manager = new MessageManager();
    	ServletContext context = event.getServletContext();
    	context.setAttribute("messageManager", manager);
    }

	/**
     * @see PeakContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
