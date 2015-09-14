/**
 * 
 */
package com.comptonsoftwaresolutions.crm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.comptonsoftwaresolutions.crm.model.Client;
import com.comptonsoftwaresolutions.crm.service.ClientRepository;

/**
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

	@RequestMapping(value = "{clientId}", method = RequestMethod.GET)
	public Client getClient(@PathVariable Long clientId) {
		LOG.debug("Selecting client with id [" + clientId + "]");
		Client client = clientRepository.findOne(clientId);
		if (client == null) {
			throw new UserNotFoundException(clientId.toString());
		}
		return client;
	}

	@RequestMapping(method = RequestMethod.POST)
	public long addClient(@RequestParam(value = "firstName") String firstName, @RequestParam String lastName) {
		LOG.debug("Adding client with firstName [" + firstName + "], lastName [" + lastName + "]");

		Client client = new Client();
		client.setFirstName(firstName);
		client.setLastName(lastName);
		clientRepository.save(client);
		return client.getId();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Client> getClients() {
		LOG.debug("Getting all clients");
		Iterable<Client> clients = clientRepository.findAll();

		return clients;
	}

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="user not found")
	class UserNotFoundException extends RuntimeException {
		private static final long serialVersionUID = -9008932302012161345L;

		public UserNotFoundException(String userId) {
			super(userId);
		}
	}
}
