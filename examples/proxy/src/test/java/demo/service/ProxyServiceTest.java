package demo.service;

import demo.gateway.FilterGateway;
import demo.gateway.Gateway;
import demo.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static demo.logger.Tracer.traceId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProxyServiceTest {

    private Service service;
    private Gateway primary;
    private Gateway secondary;

    @BeforeEach
    void setUp() {
        primary = mock(Gateway.class);
        secondary = mock(Gateway.class);

        final FilterGateway gateway = mock(FilterGateway.class);
        when(gateway.primary()).thenReturn(primary);
        when(gateway.secondary()).thenReturn(secondary);

        service = new ProxyService(gateway);
    }

    @Test
    void bothGatewaysAreInvokedWithMessage() {
        final Message message = new Message("12345678", "A test message");

        service.sendMessage(message);

        verify(primary, times(1)).sendMessage(eq(message));
        verify(secondary, times(1)).sendMessage(eq(message));
    }

    @Test
    void theSameTraceIdShouldBeAvailableToBothPrimaryAndSecondary() {
        final Message message = new Message("12345678", "A test message");

        final AtomicReference<UUID> primaryTraceId = captureTraceIdWhenInvoked(primary, message);
        final AtomicReference<UUID> secondaryTraceId = captureTraceIdWhenInvoked(secondary, message);

        service.sendMessage(message);

        assertThat(primaryTraceId.get())
                .describedAs("The same traceId should be available to both the primary and secondary gateways")
                .isNotNull()
                .isEqualTo(secondaryTraceId.get());
    }

    private static AtomicReference<UUID> captureTraceIdWhenInvoked(final Gateway gateway, final Message message) {
        final AtomicReference<UUID> traceIdHolder = new AtomicReference<>();
        doAnswer(invocation -> {
            traceId().ifPresent(traceIdHolder::set);
            return null;
        }).when(gateway).sendMessage(eq(message));
        return traceIdHolder;
    }
}