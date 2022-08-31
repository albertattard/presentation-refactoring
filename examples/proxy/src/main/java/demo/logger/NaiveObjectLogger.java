package demo.logger;

import java.util.UUID;

import static demo.logger.Labeler.label;
import static demo.logger.Tracer.traceId;

public class NaiveObjectLogger implements ObjectLogger {

    @Override
    public void log(final Object object) {
        final UUID uuid = traceId().orElse(null);
        final Labeler.Label label = label().orElse(null);

        /* Prevent mixing messages coming from different threads */
        synchronized (this) {
            System.out.printf("[%s] as %s >> %s%n", uuid, label, object);
        }
    }
}
