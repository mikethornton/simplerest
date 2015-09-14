package com.comptonsoftwaresolutions.crm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dell
 *
 */
@SpringBootApplication
public class CRMApplication {
	private static final Logger LOG = LoggerFactory.getLogger(CRMApplication.class);

	public static void main(String[] args){
		LOG.debug("Application starting up, get ready for it!");
		SpringApplication.run(CRMApplication.class, args);
	}
}
