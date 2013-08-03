package ccre.igneous;

import ccre.chan.BooleanInputPoll;
import ccre.chan.BooleanStatus;
import ccre.concurrency.ReporterThread;
import edu.wpi.first.wpilibj.Relay;

/**
 * A compressor that replaces the builtin compressor with an alternate source
 * for whether or not to enable the compressor.
 *
 * @author skeggsc
 */
class CCustomCompressor extends ReporterThread {

    /**
     * The pressure switch that decides whether or not the compressor should be
     * running.
     */
    private BooleanInputPoll pressureSwitch;
    /**
     * The status that represents whether or not this is enabled (independently
     * of the pressure switch).
     */
    public BooleanStatus isEnabled = new BooleanStatus();
    /**
     * The relay that controls the compressor.
     */
    private Relay relay;

    /**
     * Create a new CCustomCompressor with the specified pressure control input,
     * and the specified channel for the compressor-control relay.
     *
     * @param pressureSwitch the switch that, when on, turns off the compressor.
     * @param compressorRelayChannel the channel to control the compressor's
     * status.
     */
    CCustomCompressor(BooleanInputPoll pressureSwitch, int compressorRelayChannel) {
        super("Custom-Compressor");
        this.pressureSwitch = pressureSwitch;
        relay = new Relay(compressorRelayChannel, Relay.Direction.kForward);
        start();
    }

    protected void threadBody() throws InterruptedException {
        while (true) {
            if (isEnabled.readValue()) {
                relay.set(!pressureSwitch.readValue() ? Relay.Value.kOn : Relay.Value.kOff);
            } else {
                relay.set(Relay.Value.kOff);
            }
            Thread.sleep(500);
        }
    }
}