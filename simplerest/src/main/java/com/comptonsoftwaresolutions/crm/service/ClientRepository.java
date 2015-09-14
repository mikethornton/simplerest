/**
 * 
 */
package com.comptonsoftwaresolutions.crm.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.comptonsoftwaresolutions.crm.model.Client;

/**
 * @author dell
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
	List<Client> findByLastName(String lastName);
}
