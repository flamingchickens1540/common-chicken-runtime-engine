package ccre.log;

/**
 * Represents a Logging level. This represents how important/severe a logging
 * message is. The levels are, in order of descending severity: severe, warning,
 * info, config, fine, finer, finest.
 *
 * @author skeggsc
 */
public class LogLevel {

    /**
     * A severe error. This usually means that something major didn't work, or
     * an impossible condition occurred.
     */
    public static final LogLevel SEVERE = new LogLevel((byte) 9, "SEVERE", "SEVR");
    /**
     * A warning. This usually means that something bad happened, but most
     * things should probably still work.
     */
    public static final LogLevel WARNING = new LogLevel((byte) 6, "WARNING", "WARN");
    /**
     * A piece of info. This usually means something happened that the user
     * might want to know.
     */
    public static final LogLevel INFO = new LogLevel((byte) 3, "INFO", "INFO");
    /**
     * A piece of configuration information. This usually means something that
     * isn't really important, but is something triggered by configuration
     * instead of normal operation.
     */
    public static final LogLevel CONFIG = new LogLevel((byte) 0, "CONFIG", "CONF");
    /**
     * A top-level debugging message. This can be caused by anything, but
     * probably shouldn't be logged particularly often.
     */
    public static final LogLevel FINE = new LogLevel((byte) -3, "FINE", "FINE");
    /**
     * A mid-level debugging message. This can be caused by anything, and can be
     * called relatively often.
     */
    public static final LogLevel FINER = new LogLevel((byte) -6, "FINER", "FINR");
    /**
     * A low-level debugging message. This can be caused by anything, and might
     * be called many times per second.
     */
    public static final LogLevel FINEST = new LogLevel((byte) -9, "FINEST", "FINS");

    /**
     * Get a LogLevel from its ID level. If it doesn't exist, a RuntimeException
     * is thrown. Should probably only be called on the result of toByte.
     *
     * @param id the ID of the LogLevel.
     * @return the LogLevel with this ID.
     * @see #id
     * @see #toByte(ccre.log.LogLevel)
     */
    public static LogLevel fromByte(byte id) {
        switch (id) {
            case -9:
                return FINEST;
            case -6:
                return FINER;
            case -3:
                return FINE;
            case 0:
                return CONFIG;
            case 3:
                return INFO;
            case 6:
                return WARNING;
            case 9:
                return SEVERE;
            default:
                throw new RuntimeException("Unknown logging level: " + id);
        }
    }

    /**
     * Return a byte representing this logging level - that is, its ID. Used in
     * fromByte.
     *
     * @param level the LogLevel to serialize.
     * @return the byte version of the LogLevel.
     * @see #id
     * @see #fromByte(byte)
     */
    public static byte toByte(LogLevel level) {
        return level.id;
    }
    /**
     * The ID of the LogLevel. The higher, the more severe. SEVERE is 9, FINEST
     * is -9, for example.
     */
    public final byte id;
    /**
     * The long-form message representing this level. This is as opposed to the
     * abbreviation.
     *
     * @see #abbreviation
     */
    public final String message;
    /**
     * The 4-letter abbreviation representing this level. This is as opposed to
     * the long-form message.
     *
     * @see #message
     */
    public final String abbreviation;

    private LogLevel(byte id, String msg, String abbreviation) {
        this.id = id;
        message = msg;
        this.abbreviation = abbreviation;
    }

    /**
     * Check if this logging level is at least as important/severe as the other
     * logging level.
     *
     * @param other the logging level to compare to.
     * @return if this is at least as important.
     */
    public boolean atLeastAsImportant(LogLevel other) {
        return id >= other.id;
    }

    /**
     * Convert this LogLevel to a string. Returns the message.
     *
     * @return the message.
     */
    @Override
    public String toString() {
        return message;
    }
}
