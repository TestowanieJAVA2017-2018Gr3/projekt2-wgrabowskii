package com.example.mockdemo.app;

import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.SendingStatus;

public class MyServiceMock implements MessageService {

    @Override
    public ConnectionStatus checkConnection(String server) {
        if (server != null && server.endsWith(".pl")) {
            return ConnectionStatus.SUCCESS;
        }
        return ConnectionStatus.FAILURE;
    }

    @Override
    public SendingStatus send(String server, String message) throws MalformedRecipientException {
        if (server == "serverFailure") {
            return SendingStatus.SENDING_ERROR;
        }
        if (message != null && server != null && message.length() > 2 && checkConnection(server) == ConnectionStatus.SUCCESS){
            return SendingStatus.SENT;
        }
        else {
            throw new MalformedRecipientException();
        }
    }
}
