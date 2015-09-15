/**
 * 
 */
package com.comptonsoftwaresolutions.crm.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test to check equals hashcode impls
 * 
 * @author dell
 *
 */
public class ClientTest {
	private static final String FIRST_NAME = "first";
	private static final String LAST_NAME = "last";
	private Client client;

	@Before
	public void setup() {
		client = createNewClient();
	}

	public Client createNewClient() {
		Client client = new Client();
		client.setFirstName(FIRST_NAME);
		client.setLastName(LAST_NAME);
		return client;
	}

	@Test
	public void symmetric() {
		assertTrue("Should have been equal", client.equals(client));
	}

	@Test
	public void reflexive() {
		Client newClient = createNewClient();
		assertTrue("Should have been equal", client.equals(newClient));
		assertTrue("Should have been equal", newClient.equals(client));
	}

	@Test
	public void transitive() {
		Client newClient = createNewClient();
		Client newClient2 = createNewClient();
		assertTrue("Should have been equal", client.equals(newClient));
		assertTrue("Should have been equal", newClient.equals(newClient2));
		assertTrue("Should have been equal", newClient2.equals(newClient));
	}

	@Test
	public void isNull() {
		assertFalse("Should not be equal", client.equals(null));
	}

	@Test
	public void notEqual() {
		Client newClient = new Client();
		newClient.setFirstName(FIRST_NAME);
		newClient.setLastName("rubbish");
		assertFalse("Should not be equal", client.equals(newClient));
		newClient.setFirstName("rubbish");
		newClient.setLastName(FIRST_NAME);
		assertFalse("Should not be equal", client.equals(newClient));
	}

	@Test
	public void hashcodeEquals() {
		Client newClient = createNewClient();
		assertEquals("Should have equal hashcodes", client.hashCode(), newClient.hashCode());
	}

	@Test
	public void hashcodeNotEquals() {
		Client newClient = createNewClient();
		newClient.setFirstName("rubbish");
		assertNotEquals("Shouldn't have equal hashcodes", client.hashCode(), newClient.hashCode());
	}

	@Test
	public void toStringHasValues() {
		assertTrue("Should contain firstName", client.toString().indexOf(FIRST_NAME) > -1);
		assertTrue("Should contain lastName", client.toString().indexOf(LAST_NAME) > -1);
	}
}
