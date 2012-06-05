/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.functor.generator;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.range.BoundType;
import org.apache.commons.functor.range.DoubleRange;

/**
 * A generator for the range <i>from</i> to <i>to</i>.
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public final class DoubleGenerator extends BaseGenerator<Double> {

    // attributes
    // ---------------------------------------------------------------
    /**
     * The range of this generator.
     */
    private final DoubleRange range;

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new DoubleGenerator.
     *
     * @param from start
     * @param to end
     */
    public DoubleGenerator(double from, double to) {
        this(from, to, DoubleRange.DEFAULT_STEP.evaluate(from, to));
    }

    /**
     * Create a new DoubleGenerator.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleGenerator(double from, double to, double step) {
        this(from, DoubleRange.DEFAULT_LEFT_BOUND_TYPE, to,
             DoubleRange.DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new DoubleGenerator.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public DoubleGenerator(double from, BoundType leftBoundType, double to,
                           BoundType rightBoundType, double step) {
        if (from != to && Math.signum(step) != Math.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to
                                               + " from " + from
                                               + " using step " + step);
        }
        this.range = new DoubleRange(from, leftBoundType, to, rightBoundType,
                                     step);
    }

    /**
     * Create a new DoubleGenerator.
     *
     * @param range the range
     */
    public DoubleGenerator(DoubleRange range) {
        this.range = range;
    }

    // methods
    // ---------------------------------------------------------------
    /**
     * Get the range of this generator.
     *
     * @return the range
     */
    public DoubleRange getRange() {
        return (DoubleRange) range;
    }

    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Double> proc) {
        final double step = this.range.getStep();
        final boolean includeLeftValue = this.range.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.range.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final double leftValue = this.range.getLeftEndpoint().getValue();
        final double rightValue = this.range.getRightEndpoint().getValue();
        if (step < 0) {
            final double from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (double i = from; i >= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (double i = from; i > rightValue; i += step) {
                    proc.run(i);
                }
            }
        } else {
            final double from = includeLeftValue ? leftValue : leftValue + step;
            if (includeRightValue) {
                for (double i = from; i <= rightValue; i += step) {
                    proc.run(i);
                }
            } else {
                for (double i = from; i < rightValue; i += step) {
                    proc.run(i);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DoubleGenerator<" + this.range.toString() + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleGenerator)) {
            return false;
        }
        DoubleGenerator that = (DoubleGenerator) obj;
        return this.range.equals(that.getRange());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "DoubleGenerator".hashCode();
        hash <<= 2;
        hash ^= this.range.hashCode();
        return hash;
    }
}
