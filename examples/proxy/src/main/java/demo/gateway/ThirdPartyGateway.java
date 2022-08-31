package demo.gateway;

import demo.model.Message;

public class ThirdPartyGateway implements Gateway {
    @Override
    public void sendMessage(final Message message) throws GatewayException {
        /* Send message to the receiver */
    }
}
