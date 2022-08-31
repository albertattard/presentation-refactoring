package demo.service;

import demo.gateway.FilterGateway;
import demo.model.Message;

import static demo.logger.Tracer.withTraceId;

public class ProxyService implements Service {

    private final Service primary;
    private final Service secondary;

    public ProxyService(final FilterGateway gateway) {
        this.primary = new OldService(gateway.primary());
        this.secondary = new NewService(gateway.secondary());
    }

    @Override
    public void sendMessage(final Message message) throws ServiceException {
        withTraceId(() -> {
            this.primary.sendMessage(message);
            this.secondary.sendMessage(message);
        });
    }
}
