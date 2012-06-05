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

package org.apache.commons.functor.range;

import org.apache.commons.functor.BinaryFunction;

/**
 * A generator for a range of float.
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class FloatRange extends NumericRange<Float, Float> {

    // attributes
    // ---------------------------------------------------------------
    /**
     * Left limit.
     */
    private final Endpoint<Float> leftEndpoint;

    /**
     * Right limit.
     */
    private final Endpoint<Float> rightEndpoint;

    /**
     * Increment step.
     */
    private final float step;

    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Float, Float, Float> DEFAULT_STEP = new BinaryFunction<Float, Float, Float>() {

        public Float evaluate(Float left, Float right) {
            return left > right ? -1.0f : 1.0f;
        }
    };

    // constructors
    // ---------------------------------------------------------------
    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     */
    public FloatRange(Number from, Number to) {
        this(from.floatValue(), to.floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(Number from, Number to, Number step) {
        this(from.floatValue(), to.floatValue(), step.floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     */
    public FloatRange(float from, float to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).floatValue());
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(float from, float to, float step) {
        this(from, DEFAULT_LEFT_BOUND_TYPE, to, DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param leftBoundType type of left bound
     * @param to end
     * @param rightBoundType type of right bound
     * @param step increment
     */
    public FloatRange(float from, BoundType leftBoundType, float to,
                      BoundType rightBoundType, float step) {
        if (from != to && Math.signum(step) != Math.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach "
                                                + to
                                                + " from "
                                                + from
                                                + " using step " + step);
        }
        this.leftEndpoint = new Endpoint<Float>(from, leftBoundType);
        this.rightEndpoint = new Endpoint<Float>(to, rightBoundType);
        this.step = step;
    }

    /**
     * Create a new FloatRange.
     *
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(Endpoint<Float> from, Endpoint<Float> to, float step) {
        if (from != to
             && Math.signum(step) != Math.signum(to.getValue().doubleValue()
                                          - from.getValue().doubleValue())) {
            throw new IllegalArgumentException("Will never reach "
                                          + to
                                          + " from "
                                          + from
                                          + " using step "
                                          + step);
        }
        this.leftEndpoint = from;
        this.rightEndpoint = to;
        this.step = step;
    }

    // methods
    // ---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Float> getLeftEndpoint() {
        return this.leftEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Endpoint<Float> getRightEndpoint() {
        return this.rightEndpoint;
    }

    /**
     * {@inheritDoc}
     */
    public Float getStep() {
        return this.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FloatRange<" + this.leftEndpoint.toLeftString() + ", "
                + this.rightEndpoint.toRightString() + ", " + this.step + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FloatRange)) {
            return false;
        }
        FloatRange that = (FloatRange) obj;
        return this.leftEndpoint.equals(that.leftEndpoint)
                && this.rightEndpoint.equals(that.rightEndpoint)
                && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FloatRange".hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue().hashCode();
        hash <<= 2;
        hash ^= Float.valueOf(this.step).hashCode();
        return hash;
    }
}
