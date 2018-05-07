package com.example.mockdemo.app;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

public class MessageEasyMockTest {

    private MessageService service;
    private Messenger messenger;

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "inf.ug.edu.eu";

    private final String VALID_MESSAGE = "some message";
    private final String INVALID_MESSAGE = "ab";

    @Before
    public void SetUp() {
        service = createNiceMock(MessageService.class);
        messenger = new Messenger(service);
    }

    @Test
    public void testConnectionWithValidServer(){
        expect(service.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
        replay(service);

        int result = messenger.testConnection(VALID_SERVER);

        assertEquals(0, result);
    }

    @Test
    public void testConnectionWithValidServerAndFailure(){
        expect(service.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
        replay(service);

        int result = messenger.testConnection(VALID_SERVER);

        assertEquals(1, result);
    }

    @Test
    public void testConnectionWithInvalidServer(){
        expect(service.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
        replay(service);

        int result = messenger.testConnection(INVALID_SERVER);

        assertEquals(1, result);
    }

    @Test
    public void testSendingWithValidData() throws MalformedRecipientException {
        expect(service.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT);
        replay(service);

        int result = messenger.testSending(VALID_SERVER, VALID_MESSAGE);

        assertEquals(0, result);
    }

    @Test
    public void testSendingWithInvalidData() throws MalformedRecipientException {
        expect(service.send(INVALID_SERVER, INVALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR).once();
        replay(service);

        int result = messenger.testSending(INVALID_SERVER, INVALID_MESSAGE);

        assertEquals(1, result);
    }

    @Test
    public void testSendingWithNoServer() throws MalformedRecipientException {
        expect(service.send(null, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        replay(service);

        int result = messenger.testSending(null, INVALID_MESSAGE);

        assertEquals(2, result);
    }

}
