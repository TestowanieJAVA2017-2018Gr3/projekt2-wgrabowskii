package com.example.mockdemo.app;

import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class Messenger {

	private MessageService ms;

	public Messenger(MessageService ms) {
		this.ms = ms;
	}

	public int testConnection(String server) {
		switch (ms.checkConnection(server)){
            case FAILURE:
                return 1;
            case SUCCESS:
                return 0;
        }
        return 1;
	}

	public int testSending(String server, String message) {

        try {
            SendingStatus sendStatus = ms.send(server, message);
            switch (sendStatus) {
                case SENT:
                    return 0;
                case SENDING_ERROR:
                    return 1;
                default:
                    return -1;
            }
        } catch (MalformedRecipientException e) {
            return 2;
        }
    }
}
