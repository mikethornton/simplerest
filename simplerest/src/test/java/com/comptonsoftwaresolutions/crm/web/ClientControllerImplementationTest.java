/**
 * 
 */
package com.comptonsoftwaresolutions.crm.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.comptonsoftwaresolutions.crm.CRMApplication;
import com.comptonsoftwaresolutions.crm.model.Client;
import com.comptonsoftwaresolutions.crm.service.ClientRepository;

/**
 * Using a full spring boot integration test run tests on each rest mapping
 * method to ensure they function as expected.
 * 
 * @author dell
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CRMApplication.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles(profiles = { "test" })
public class ClientControllerImplementationTest {
	private static final String FIRST_NAME = "first";
	private static final String LAST_NAME = "last";
	private static final String FIRST_NAME_2 = "Mike";
	private static final String LAST_NAME_2 = "Thornton";

	@Autowired
	private ClientRepository repository;
	private Client client1;
	private RestTemplate template = new RestTemplate();
	@Value("${local.server.port}")
	int port;

	/**
	 * Set up for test, create a client and save in repository.
	 */
	@Before
	public void setup() {
		client1 = new Client();
		client1.setFirstName(FIRST_NAME);
		client1.setLastName(LAST_NAME);
		repository.save(client1);
	}

	/**
	 * After test delete any objects created.
	 */
	@After
	public void after() {
		repository.deleteAll();
	}

	/**
	 * Test that a client is returned using the RestTemplate and that it is as
	 * expected.
	 */
	@Test
	public void testGetClient() {
		Client returnedClient = template.getForObject("http://localhost:" + port + "/clients/" + client1.getId(),
				Client.class);
		assertEquals("Should have returned equal client", client1, returnedClient);
		assertNotSame("Should not be exactly the same client", client1, returnedClient);
	}

	/**
	 * Test that if there is no user mapped to that client id that we get an
	 * error.
	 */
	@Test(expected = HttpClientErrorException.class)
	public void testNoClient() {
		try {
			template.getForObject("http://localhost:" + port + "/clients/" + (client1.getId() + 1), Client.class);
		} catch (HttpClientErrorException e) {
			assertEquals("Wrong error code", HttpStatus.NOT_FOUND, e.getStatusCode());
			throw e;
		}
		fail("Should have thrown exception");
	}

	/**
	 * Test that if we add a valid user then it gets persisted correctly.
	 */
	@Test
	public void testAddClient() {
		Long id = template.postForObject(
				"http://localhost:" + port + "/clients?firstName=" + FIRST_NAME_2 + "&lastName=" + LAST_NAME_2, null,
				Long.class);

		Client newClient = repository.findOne(id);

		assertNotNull("Should have returned new client", newClient);
		assertEquals("Should have set first name", FIRST_NAME_2, newClient.getFirstName());
		assertEquals("Should have set last name", LAST_NAME_2, newClient.getLastName());
	}

	/**
	 * Test that if we don't provide a firstName when adding a new client we get
	 * an error returned.
	 */
	@Test(expected = HttpClientErrorException.class)
	public void testAddClientNoFirstName() {
		try {
			template.postForObject("http://localhost:" + port + "/clients?lastName=" + LAST_NAME_2, null, Long.class);
		} catch (HttpClientErrorException e) {
			assertEquals("Wrong error code", HttpStatus.BAD_REQUEST, e.getStatusCode());
			throw e;
		}
		fail("Should have thrown exception");
	}

	/**
	 * Test that if we don't provide a lastName when adding a new client we get
	 * an error returned.
	 */
	@Test(expected = HttpClientErrorException.class)
	public void testAddClientNoLastName() {
		try {
			template.postForObject("http://localhost:" + port + "/clients?firstName=" + FIRST_NAME_2, null,
					Long.class);
		} catch (HttpClientErrorException e) {
			assertEquals("Wrong error code", HttpStatus.BAD_REQUEST, e.getStatusCode());
			throw e;
		}
		fail("Should have thrown exception");
	}

	/**
	 * Test that if can retrieve clients.
	 */
	@Test
	public void getClients() {
		@SuppressWarnings("unchecked")
		Collection<Client> clients = template.getForObject("http://localhost:" + port + "/clients",
				Collection.class);

		assertEquals("should return clients", 1, clients.size());
	}

	/**
	 * If there are no clients in the backend we get an error.
	 */
	@Test
	public void getClientsNoneFound() {
		repository.delete(client1);

		@SuppressWarnings("unchecked")
		Collection<Client> clients =template.getForObject("http://localhost:" + port + "/clients", Collection.class);

		assertEquals("should return clients", 0, clients.size());
	}
}
