/*
 * Copyright 2015-2016 Cel Skeggs
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
package ccre.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

@SuppressWarnings("javadoc")
public class VerifyingLoggingTarget implements LoggingTarget {

    public boolean ifExpected;
    public LogLevel levelExpected = LogLevel.SEVERE;
    public String messageExpected = null;
    public boolean isThrowableExpected = false;
    public Predicate<Throwable> throwablePredicate = (t) -> t == null;
    public String stringExpected = null;
    public Runnable onNext = null;
    private boolean hasFailure = false;

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        try {
            assertNotNull(level);
            assertNotNull(message);
            assertTrue(ifExpected);
            assertTrue(isThrowableExpected);
            assertEquals(levelExpected, level);
            assertEquals(messageExpected, message);
            assertTrue(throwablePredicate.test(throwable));
        } catch (Throwable thr) {
            if (throwable != null) {
                System.err.println("Exception on caught exception");
                throwable.printStackTrace();
            }
            hasFailure = true;
            throw thr;
        }
        ifExpected = false;
        if (onNext != null) {
            Runnable r = onNext;
            onNext = null;
            r.run();
        }
    }

    @Override
    public void log(LogLevel level, String message, String extended) {
        try {
            assertNotNull(level);
            assertNotNull(message);
            assertTrue(ifExpected);
            assertFalse(isThrowableExpected);
            assertEquals(levelExpected, level);
            assertEquals(messageExpected, message);
            assertEquals(stringExpected, extended);
        } catch (Throwable thr) {
            hasFailure = true;
            throw thr;
        }
        ifExpected = false;
    }

    public void check() {
        assertFalse("Did not receive expected log.", ifExpected);
        assertFalse("Failed during individual log reception.", hasFailure);
    }

    public void configureString(LogLevel level, String message, String string) {
        this.ifExpected = true;
        this.isThrowableExpected = false;
        this.levelExpected = level;
        this.messageExpected = message;
        this.stringExpected = string;
    }

    public void configureThrowable(LogLevel level, String message, Throwable thr) {
        configureThrowable(level, message, (t) -> t == thr);
    }

    public void configureThrowable(LogLevel level, String message, Predicate<Throwable> expectedThr) {
        this.ifExpected = true;
        this.isThrowableExpected = true;
        this.throwablePredicate = expectedThr;
        this.levelExpected = level;
        this.messageExpected = message;
        this.stringExpected = null;
    }
}
