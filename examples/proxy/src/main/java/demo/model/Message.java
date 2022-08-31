package demo.model;

import java.util.Objects;

public class Message {

    private final String receiver;
    private final String message;

    public Message(final String receiver, final String message) {
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Message other = (Message) object;
        return Objects.equals(receiver, other.receiver)
                && Objects.equals(message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, message);
    }

    @Override
    public String toString() {
        return String.format("Message{receiver=%s, message=%s}", receiver, message);
    }
}
