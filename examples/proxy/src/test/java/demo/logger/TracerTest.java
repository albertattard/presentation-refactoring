package demo.logger;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static demo.logger.Tracer.traceId;
import static org.assertj.core.api.Assertions.assertThat;

class TracerTest {

    @Test
    void traceIdShouldBeSetOnlyWithinTheWithTraceIdCall() {
        assertThat(traceId())
                .describedAs("The traceId should not be set outside the withTraceId() method call")
                .isEmpty();

        Tracer.withTraceId(() -> {
            assertThat(traceId())
                    .describedAs("The traceId should be set within the withTraceId() method call")
                    .isPresent();
        });

        assertThat(traceId())
                .describedAs("The traceId should not be set outside the withTraceId() method call")
                .isEmpty();
    }

    @Test
    void theSameTraceIdShouldBeReturnedWithinTheSameWithTraceIdCall() {
        Tracer.withTraceId(() -> {
            final Optional<UUID> a = traceId();
            final Optional<UUID> b = traceId();
            assertThat(a)
                    .describedAs("The same traceId should be returned within the same withTraceId() method call")
                    .isEqualTo(b);
        });
    }

    @Test
    void aDifferentTraceIdShouldBeReturnedWithinDifferentWithTraceIdCalls() {
        final int numberOfMethodCalls = 10;

        final Set<UUID> traceIds = new HashSet<>(numberOfMethodCalls);
        for (int i = 0; i < numberOfMethodCalls; i++) {
            Tracer.withTraceId(() -> traceId().ifPresent(traceIds::add));
        }

        assertThat(traceIds)
                .describedAs("A different traceId should be returned within different withTraceId method calls")
                .hasSize(numberOfMethodCalls);
    }
}
