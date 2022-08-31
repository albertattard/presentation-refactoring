package demo.service;

import demo.gateway.Gateway;
import demo.gateway.GatewayException;
import demo.model.Message;

public class NewService implements Service {

    private final Gateway gateway;

    public NewService(final Gateway gateway) {this.gateway = gateway;}

    @Override
    public void sendMessage(final Message message) throws ServiceException {
        try {
            gateway.sendMessage(message);
        } catch (final GatewayException e) {
            throw new ServiceException(e);
        }
    }
}
