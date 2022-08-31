package demo.gateway;

import demo.logger.NaiveObjectLogger;
import demo.logger.ObjectLogger;
import demo.model.Message;

import static demo.logger.Labeler.asPrimary;
import static demo.logger.Labeler.asSecondary;

public class FilterGateway {

    private final Gateway gateway;

    private final Gateway primary;
    private final Gateway secondary;

    public FilterGateway(final Gateway gateway) {
        this.gateway = gateway;

        final ObjectLogger logger = new NaiveObjectLogger();
        this.primary = new PrimaryGateway(gateway, logger);
        this.secondary = new SecondaryGateway(logger);
    }

    public Gateway primary() {
        return primary;
    }

    public Gateway secondary() {
        return secondary;
    }

    private static class PrimaryGateway implements Gateway {

        private final Gateway gateway;
        private final ObjectLogger logger;

        private PrimaryGateway(final Gateway gateway, final ObjectLogger logger) {
            this.gateway = gateway;
            this.logger = logger;
        }

        @Override
        public void sendMessage(final Message message) throws GatewayException {
            asPrimary(() -> {
                gateway.sendMessage(message);
                logger.log(message);
            });
        }
    }

    private static class SecondaryGateway implements Gateway {

        private final ObjectLogger logger;

        private SecondaryGateway(final ObjectLogger logger) {this.logger = logger;}

        public void sendMessage(final Message message) {
            asSecondary(() -> {
                logger.log(message);
            });
        }
    }
}
