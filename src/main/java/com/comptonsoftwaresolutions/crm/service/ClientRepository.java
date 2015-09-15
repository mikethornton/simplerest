/**
 * 
 */
package com.comptonsoftwaresolutions.crm.service;

import org.springframework.data.repository.CrudRepository;

import com.comptonsoftwaresolutions.crm.model.Client;

/**
 * Client repository which will be picked up by spring data jpa and used
 * to create a runtime instance.
 * 
 * @author dell
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
