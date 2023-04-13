package com.sigmify.vb.booking;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionListener implements HttpSessionListener {

	private final static Logger logger = Logger.getLogger(SessionListener.class.getName()); 
	
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("Session Created");
        event.getSession().setMaxInactiveInterval(1800);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
       logger.info("Session Expired");
    }
    
}
	