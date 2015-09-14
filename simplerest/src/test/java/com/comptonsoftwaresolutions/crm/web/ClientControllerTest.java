/**
 * 
 */
package com.comptonsoftwaresolutions.crm.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comptonsoftwaresolutions.crm.model.Client;
import com.comptonsoftwaresolutions.crm.service.ClientRepository;

/**
 * @author dell
 *
 */
public class ClientControllerTest {
	private static final Long ID = Long.valueOf(12);
	private static final String FIRST_NAME = "first";
	private static final String LAST_NAME = "last";

	@Mock
	private ClientRepository repository;
	@Mock
	private Client client;
	private ClientController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ClientController(repository);
	}

	@Test
	public void testGetClient() {
		when(repository.findOne(ID)).thenReturn(client);
		assertEquals("Should have returned client", client, controller.getClient(ID));

		verify(repository).findOne(ID);
	}

	@Test(expected = ClientController.UserNotFoundException.class)
	public void testNoClient() {
		controller.getClient(ID);
	}

	@Test
	public void testAddClient() {
		ArgumentCaptor<Client> argCaptor = ArgumentCaptor.forClass(Client.class);

		controller.addClient(FIRST_NAME, LAST_NAME);

		verify(repository).save(argCaptor.capture());
		assertEquals("Should have set first name", FIRST_NAME, argCaptor.getValue().getFirstName());
		assertEquals("Should have set last name", LAST_NAME, argCaptor.getValue().getLastName());
	}

	@Test
	public void getClients() {
		@SuppressWarnings("unchecked")
		Iterable<Client> clientIterable = mock(Iterable.class);
		@SuppressWarnings("unchecked")
		Iterator<Client> clientIterator = mock(Iterator.class);
		when(clientIterable.iterator()).thenReturn(clientIterator);
		when(clientIterator.hasNext()).thenReturn(true);

		when(repository.findAll()).thenReturn(clientIterable);
		assertEquals("Should have returned iterable", clientIterable, controller.getClients());
	}
}
