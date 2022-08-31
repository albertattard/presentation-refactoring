package demo.logger;

import java.util.Optional;
import java.util.UUID;

public final class Tracer {

    private static final ThreadLocal<UUID> THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static void withTraceId(final Runnable task) {
        final UUID traceId = UUID.randomUUID();
        THREAD_LOCAL.set(traceId);
        System.out.printf("Using trace id [%s]%n", traceId);

        try {
            task.run();
        } finally {
            THREAD_LOCAL.remove();
        }
    }

    public static Optional<UUID> traceId() {
        return Optional.ofNullable(THREAD_LOCAL.get());
    }

    private Tracer() {}
}
