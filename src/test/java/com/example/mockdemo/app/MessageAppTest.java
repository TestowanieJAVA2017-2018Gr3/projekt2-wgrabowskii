package com.example.mockdemo.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageAppTest {

    private MyServiceMock serverMock;
    private Messenger messenger;

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String PROBLEM_SERVER = "serverFailure";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";

    @Before
    public void setUp() {
        serverMock = new MyServiceMock();
        messenger = new Messenger(serverMock);
    }

    @Test
    public void connectionWithValidServer() {
        assertEquals(0, messenger.testConnection(VALID_SERVER));
    }

    @Test
    public void connectionWithoutServer() {
        assertEquals(1, messenger.testConnection(null));
    }

    @Test
    public void connectionWithInvalidServer() {
        assertEquals(1, messenger.testConnection(INVALID_SERVER));
    }

    @Test
    public void sendingValidMessageValidServer() {
        assertEquals(0, messenger.testSending(VALID_SERVER, VALID_MESSAGE));
    }

    @Test
    public void sendingValidMessageProblemServer() {
        assertEquals(1, messenger.testSending(PROBLEM_SERVER, VALID_MESSAGE));
    }

    @Test
    public void sendingValidMessageInvalidServer() {
        assertEquals(2, messenger.testSending(INVALID_SERVER, VALID_MESSAGE));
    }

    @Test
    public void sendingInvalidMessageValidServer() {
        assertEquals(2, messenger.testSending(VALID_SERVER, INVALID_MESSAGE));
    }

    @Test
    public void sendingInvalidMessageInvalidServer() {
        assertEquals(2, messenger.testSending(INVALID_SERVER, INVALID_MESSAGE));
    }

    @Test
    public void sendingValidMessageNoServer() {
        assertEquals(2, messenger.testSending(null, VALID_MESSAGE));
    }

    @Test
    public void sendingNoMessageValidServer() {
        assertEquals(2, messenger.testSending(VALID_SERVER, null));
    }

    @Test
    public void sendingNoMessageInvalidServer() {
        assertEquals(2, messenger.testSending(INVALID_SERVER, null));
    }

    @Test
    public void sendingInvalidMessageNoServer() {
        assertEquals(2, messenger.testSending(null, INVALID_MESSAGE));
    }
}

