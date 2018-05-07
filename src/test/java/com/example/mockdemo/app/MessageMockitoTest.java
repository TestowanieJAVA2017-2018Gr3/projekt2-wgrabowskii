package com.example.mockdemo.app;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

public class MessageMockitoTest {

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "inf.ug.edu.eu";

    private final String VALID_MESSAGE = "some message";
    private final String INVALID_MESSAGE = "ab";

    @Mock
    private MessageService service;
    private Messenger messenger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        messenger = new Messenger(service);
    }

    @Test
    public void testConnectionWithValidServerAndSuccess() {
        when(service.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
        int result = messenger.testConnection(VALID_SERVER);
        verify(service).checkConnection(VALID_SERVER);
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testConnectionWithValidServerAndFailure() {
        when(service.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
        int result = messenger.testConnection(VALID_SERVER);
        verify(service).checkConnection(VALID_SERVER);
        assertThat(result, is(equalTo(1)));
    }

    @Test
    public void testConnectionWithInvalidServer() {
        when(service.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
        int result = messenger.testConnection(INVALID_SERVER);
        verify(service).checkConnection(INVALID_SERVER);
        assertThat(result, is(equalTo(1)));
    }

    @Test
    public void testConnectionWithNoServer() {
         when(service.checkConnection(null)).thenReturn(ConnectionStatus.FAILURE);
         int result = messenger.testConnection(null);
         verify(service).checkConnection(null);
        assertThat(result, is(equalTo(1)));
    }

    @Test
    public void testSendingWithSuccess() throws MalformedRecipientException {
        when(service.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
        int result = messenger.testSending(VALID_SERVER, VALID_MESSAGE);
        verify(service).send(VALID_SERVER, VALID_MESSAGE);
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testSendingWithSendingError() throws MalformedRecipientException {
        when(service.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
        int result = messenger.testSending(VALID_SERVER, VALID_MESSAGE);
        verify(service).send(VALID_SERVER, VALID_MESSAGE);
        assertThat(result, is(equalTo(1)));
    }

    @Test
    public void testSendingWithErrors() throws MalformedRecipientException {
        when(service.send(INVALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
        int result = messenger.testSending(INVALID_SERVER, INVALID_MESSAGE);
        verify(service).send(INVALID_SERVER, INVALID_MESSAGE);
        assertThat(result, is(equalTo(2)));
    }
}
