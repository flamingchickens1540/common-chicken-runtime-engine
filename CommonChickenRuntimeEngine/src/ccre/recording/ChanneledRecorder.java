/*
 * Copyright 2016 Cel Skeggs
 *
 * This file is part of the CCRE, the Common Chicken Runtime Engine.
 *
 * The CCRE is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * The CCRE is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the CCRE.  If not, see <http://www.gnu.org/licenses/>.
 */
package ccre.recording;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

class ChanneledRecorder {
    private final RecorderThread rthread;
    private volatile boolean closed;

    public ChanneledRecorder(OutputStream out) throws IOException {
        this.rthread = new RecorderThread(out);
        rthread.start();
    }

    public void close() throws InterruptedException {
        closed = true;
        rthread.close();
    }

    private long getTimestamp() {
        // convert to multiples of 10 us
        return System.nanoTime() / 10000;
    }

    public void recordNull(int channel) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_NULL, 0);
    }

    public void recordByte(int channel, byte b) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_BYTE, b);
    }

    public void recordShort(int channel, short s) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_SHORT, s);
    }

    public void recordInt(int channel, int i) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_INT, i);
    }

    public void recordLong(int channel, long l) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_LONG, l);
    }

    public void recordVarInt(int channel, long l) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, RecordSnapshot.T_VARINT, l);
    }

    public void recordBytes(int channel, byte[] bytes, int offset, int length) {
        if (closed) {
            return; // throw it away
        }
        rthread.record(getTimestamp(), channel, Arrays.copyOfRange(bytes, offset, offset + length));
    }

    public void recordBytes(int channel, byte[] bytes) {
        recordBytes(channel, bytes, 0, bytes.length);
    }

    public void recordString(int channel, String string) {
        recordBytes(channel, string.getBytes());
    }
}
