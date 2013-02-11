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

package org.apache.commons.functor.generator.range;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.UnaryProcedure;

/**
 * A generator for a range of doubles.
 *
 * @since 1.0
 * @version $Revision: 1439633 $ $Date: 2013-01-28 19:04:46 -0200 (Mon, 28 Jan 2013) $
 */
public class DoubleRange extends NumericRange<Double> {

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Double, Double, Double> DEFAULT_STEP =
            new BinaryFunction<Double, Double, Double>() {

        public Double evaluate(Double left, Double right) {
            return left > right ? -1.0d : 1.0d;
        }
    };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     */
    public DoubleRange(Number from, Number to) {
        this(from.doubleValue(), to.doubleValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(Number from, Number to, Number step) {
        this(from.doubleValue(), to.doubleValue(), step.doubleValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     */
    public DoubleRange(double from, double to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(double from, double to, double step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public DoubleRange(double from, BoundType leftBoundType, double to, BoundType rightBoundType, double step) {
        this(new Endpoint<Double>(Double.valueOf(from), leftBoundType), new Endpoint<Double>(Double.valueOf(to),
            rightBoundType), Double.valueOf(step));
    }

    /**
     * Create a new DoubleRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to, Double step) {
        super(from, to, step);
    }

    // methods
    // ---------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Double> proc) {
        final double step = this.getStep();
        final boolean includeLeftValue = this.getLeftEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final boolean includeRightValue = this.getRightEndpoint()
            .getBoundType() == BoundType.CLOSED;
        final double leftValue = this.getLeftEndpoint().getValue();
        final double rightValue = this.getRightEndpoint().getValue();
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

}
