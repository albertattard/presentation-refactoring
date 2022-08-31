package demo.logger;

import java.util.Optional;

final public class Labeler {

    private static final ThreadLocal<Label> THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static void asPrimary(final Runnable task) {
        as(Label.PRIMARY, task);
    }

    public static void asSecondary(final Runnable task) {
        as(Label.SECONDARY, task);
    }

    private static void as(final Label label, final Runnable task) {
        THREAD_LOCAL.set(label);
        System.out.printf("As [%s]%n", label);

        try {
            task.run();
        } finally {
            THREAD_LOCAL.remove();
        }
    }

    public static Optional<Label> label() {
        return Optional.ofNullable(THREAD_LOCAL.get());
    }

    public enum Label {
        PRIMARY,
        SECONDARY;
    }

    private Labeler() {}
}
