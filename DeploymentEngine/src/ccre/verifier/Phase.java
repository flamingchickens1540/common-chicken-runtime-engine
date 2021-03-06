/*
 * Copyright 2016 Cel Skeggs.
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
package ccre.verifier;

import java.lang.annotation.Annotation;

enum Phase {
    SETUP(SetupPhase.class) {
    },
    FLOW(FlowPhase.class) {
        @Override
        public boolean allowedFrom(Phase from) {
            return from == this || from == SETUP || from == IMPERATIVE;
        }
    },
    IMPERATIVE(ImperativePhase.class) {
    },
    IGNORED(IgnoredPhase.class) {
        @Override
        public boolean allowedFrom(Phase from) {
            return true;
        }
    };

    public final Class<? extends Annotation> annot;

    private Phase(Class<? extends Annotation> annot) {
        this.annot = annot;
    }

    public boolean allowedFrom(Phase from) {
        return this == from;
    }
}
