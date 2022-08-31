package demo.gateway;

import demo.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static demo.logger.Tracer.withTraceId;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FilterGatewayTest {

    private FilterGateway filterGateway;
    private Gateway thirdPartyGateway;

    @BeforeEach
    void setUp() {
        thirdPartyGateway = mock(Gateway.class);

        filterGateway = new FilterGateway(thirdPartyGateway);
    }

    @Test
    void thirdPartyGatewayIsInvokedOnlyOnceByPrimary() {
        final Message message = new Message("12345678", "A test message");

        withTraceId(() -> {
            filterGateway.primary().sendMessage(message);
            filterGateway.secondary().sendMessage(message);
        });

        verify(thirdPartyGateway, times(1)).sendMessage(eq(message));
    }
}
