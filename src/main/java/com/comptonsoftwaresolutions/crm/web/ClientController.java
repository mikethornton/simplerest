/**
 * 
 */
package com.comptonsoftwaresolutions.crm.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comptonsoftwaresolutions.crm.model.Client;
import com.comptonsoftwaresolutions.crm.service.ClientRepository;

/**
 * RestController implementation for clients showing use of spring mvc annotations to 
 * create a RESTful interface.
 * 
 * @author dell
 *
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
	private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
	private ClientRepository clientRepository;

	@Autowired
	public ClientController(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	/**
	 * Gets client by unique id.
	 * 
	 * @param clientId Long
	 * 
	 * @return Client corresponding to id.
	 */
	@RequestMapping(value = "{clientId}", method = RequestMethod.GET)
	public Client getClient(@PathVariable Long clientId) {
		LOG.debug("Selecting client with id [" + clientId + "]");
		Client client = clientRepository.findOne(clientId);
		if (client == null) {
			throw new UserNotFoundException(clientId.toString());
		}
		return client;
	}

	/**
	 * Add a new client.
	 * 
	 * @param client Client
	 * 
	 * @return new clients id
	 */
	@RequestMapping(method = RequestMethod.POST)
	public long addClient(@Valid @RequestBody Client client) {
		LOG.debug("Adding client with firstName [" + client.getFirstName() + "], lastName [" + client.getLastName() + "]");
		clientRepository.save(client);
		return client.getId();
	}

	/**
	 * Get all clients
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Client> getClients() {
		LOG.debug("Getting all clients");
		return clientRepository.findAll();
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="user not found")
	class UserNotFoundException extends RuntimeException {
		private static final long serialVersionUID = -9008932302012161345L;

		public UserNotFoundException(String userId) {
			super(userId);
		}
	}
}
