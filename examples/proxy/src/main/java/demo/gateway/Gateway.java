package demo.gateway;

import demo.model.Message;

public interface Gateway {

    void sendMessage(Message message) throws GatewayException;
}
