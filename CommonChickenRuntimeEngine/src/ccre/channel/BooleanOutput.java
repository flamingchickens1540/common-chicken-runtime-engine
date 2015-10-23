/*
 * Copyright 2013-2014 Colby Skeggs
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
package ccre.channel;

import ccre.log.Logger;
import ccre.util.Utils;

/**
 * A BooleanOutput is an interface for anything that can be turned on or off. It
 * can be set to true or false.
 *
 * @see BooleanInput
 * @author skeggsc
 */
public interface BooleanOutput {

    /**
     * A BooleanOutput that goes nowhere. All data sent here is ignored.
     */
    BooleanOutput ignored = new BooleanOutput() {
        public void set(boolean newValue) {
            // Do nothing.
        }
    };

    /**
     * Set the boolean value of this output. In other words, turn it on or off.
     *
     * @param value The new value to send to this output.
     */
    public void set(boolean value);

    public default void safeSet(boolean value) {
        try {
            set(value);
        } catch (Throwable ex) {
            Logger.severe("Error during channel propagation", ex);
        }
    }

    public default BooleanOutput invert() {
        BooleanOutput original = this;
        return new BooleanOutput() {
            @Override
            public void set(boolean value) {
                original.set(!value);
            }

            @Override
            public BooleanOutput invert() {
                return original;
            }
        };
    }

    public default BooleanOutput combine(BooleanOutput other) {
        Utils.checkNull(other);
        BooleanOutput self = this;
        return value -> {
            try {
                self.set(value);
            } catch (Throwable thr) {
                try {
                    other.set(value);
                } catch (Throwable thr2) {
                    thr.addSuppressed(thr2);
                }
                throw thr;
            }
            other.set(value);
        };
    }

    public default BooleanOutput limitUpdatesTo(EventInput update) {
        Utils.checkNull(update);
        BooleanOutput original = this;
        return new BooleanOutput() {
            private boolean lastValue, anyValue;

            {
                update.send(() -> {
                    if (anyValue) {
                        original.set(lastValue);
                    }
                });
            }

            @Override
            public void set(boolean value) {
                this.lastValue = value;
                this.anyValue = true;
            }
        };
    }

    public default EventOutput eventSet(boolean value) {
        return value ? eventSetTrue() : eventSetFalse();
    }

    public default EventOutput eventSet(BooleanInput value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return () -> set(value.get());
    }

    public default EventOutput eventSetTrue() {
        return () -> set(true);
    }

    public default EventOutput eventSetFalse() {
        return () -> set(false);
    }

    public default void setWhen(boolean value, EventInput when) {
        when.send(this.eventSet(value));
    }

    public default void setWhen(BooleanInput value, EventInput when) {
        when.send(this.eventSet(value));
    }

    public default void setTrueWhen(EventInput when) {
        setWhen(true, when);
    }

    public default void setFalseWhen(EventInput when) {
        setWhen(false, when);
    }

    /**
     * Returns a BooleanOutput, and when the value written to it changes, it
     * fires the associated event. This will only fire when the value changes,
     * and is false by default.
     *
     * Either parameter can be null, which is equivalent to passing
     * <code>EventOutput.ignored</code>. They cannot both be null - this will
     * throw a NullPointerException.
     *
     * @param toFalse if the output becomes false.
     * @param toTrue if the output becomes true.
     * @return the output that can trigger the events.
     */
    public static BooleanOutput onChange(final EventOutput toFalse, final EventOutput toTrue) {
        if (toFalse == null && toTrue == null) {
            throw new NullPointerException("Both toFalse and toTrue are null in onChange! You can only have at most one be null.");
        }
        return new BooleanOutput() {
            private boolean last;

            public void set(boolean value) {
                if (value == last) {
                    return;
                }
                if (value) {
                    last = true;
                    if (toTrue != null) {
                        toTrue.event();
                    }
                } else {
                    last = false;
                    if (toFalse != null) {
                        toFalse.event();
                    }
                }
            }
        };
    }

    public default BooleanOutput filter(BooleanInput allow) {
        BooleanOutput original = this;
        return new BooleanOutput() {
            private boolean lastValue, anyValue;

            {
                allow.onPress().send(() -> {
                    if (anyValue) {
                        original.set(lastValue);
                    }
                });
            }

            @Override
            public void set(boolean value) {
                lastValue = value;
                anyValue = true;
                if (allow.get()) {
                    original.set(value);
                }
            }
        };
    }

    public default BooleanOutput filterNot(BooleanInput deny) {
        BooleanOutput original = this;
        return new BooleanOutput() {
            private boolean lastValue, anyValue;

            {
                deny.onRelease().send(() -> {
                    if (anyValue) {
                        original.set(lastValue);
                    }
                });
            }

            @Override
            public void set(boolean value) {
                lastValue = value;
                anyValue = true;
                if (!deny.get()) {
                    original.set(value);
                }
            }
        };
    }
}
