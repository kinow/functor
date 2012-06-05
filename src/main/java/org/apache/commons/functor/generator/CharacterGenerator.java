/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package org.apache.commons.functor.generator;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.range.BoundType;
import org.apache.commons.functor.range.CharacterRange;

/**
 * A generator for the range <i>from</i> to <i>to</i>.
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public final class CharacterGenerator
    extends BaseGenerator<Character> {

    // attributes
    // ---------------------------------------------------------------
    /**
     * The range of this generator.
     */
    private final CharacterRange range;

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new CharacterGenerator.
     *
     * @param from start
     * @param to end
     */
    public CharacterGenerator(char from, char to) {
        this(from, to, CharacterRange.DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new CharacterGenerator.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public CharacterGenerator(char from, char to, int step) {
        this(from, CharacterRange.DEFAULT_LEFT_BOUND_TYPE, to,
             CharacterRange.DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new CharacterGenerator.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public CharacterGenerator(char from, BoundType leftBoundType, char to,
                              BoundType rightBoundType, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach "
                                                + to
                                                + " from "
                                                + from
                                                + " using step "
                                                + step);
        }
        this.range = new CharacterRange(from, leftBoundType, to,
                                        rightBoundType, step);
    }

    /**
     * Create a new CharacterGenerator.
     *
     * @param range character range
     */
    public CharacterGenerator(CharacterRange range) {
        this.range = range;
    }

    // methods
    // ---------------------------------------------------------------
    /**
     * Get the range of this generator.
     *
     * @return CharacterRange
     */
    public CharacterRange getRange() {
        return (CharacterRange) range;
    }

    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Character> proc) {
        final int step = this.range.getStep();
        final boolean includeLeftValue = this.range.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.range.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final char leftValue = this.range.getLeftEndpoint().getValue();
        final char rightValue = this.range.getRightEndpoint().getValue();
        if (step < 0) {
            final char from = (char) (includeLeftValue ? leftValue : leftValue
                                                                     + step);
            if (includeRightValue) {
                for (char i = from; i >= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (char i = from; i > rightValue; i += step) {
                    proc.run(i);
                }
            }
        } else {
            final char from = (char) (includeLeftValue ? this.range
                .getLeftEndpoint().getValue() : (this.range.getLeftEndpoint()
                .getValue() + step));
            final char to = (char) (includeRightValue ? this.range
                .getRightEndpoint().getValue() : (this.range.getRightEndpoint()
                .getValue() - 1));
            for (char i = from; i <= to; i += step) {
                proc.run(i);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CharacterGenerator<" + this.range.toString() + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharacterGenerator)) {
            return false;
        }
        CharacterGenerator that = (CharacterGenerator) obj;
        return this.range.equals(that.getRange());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CharacterGenerator".hashCode();
        hash <<= 2;
        hash ^= this.range.hashCode();
        return hash;
    }
}
