/*
 * The MIT License
 *
 * Copyright (c) <2012> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.apache.commons.functor.generator.util;

import org.apache.commons.functor.generator.BoundType;
import org.apache.commons.functor.generator.Endpoint;
import org.apache.commons.functor.generator.Range;


/**
 * 
 * @since 1.0
 */
public class IntegerRange implements Range<Integer> {

    /**
     * TODO
     */
    public static final BoundType DEFAULT_LOWER_BOUND_TYPE = BoundType.OPEN;
    /**
     * TODO
     */
    public static final BoundType DEFAULT_UPPER_BOUND_TYPE = BoundType.OPEN;
    
    private final Endpoint<Integer> lowerLimit;
    private final Endpoint<Integer> upperLimit;
    private final int step;    
    
    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new IntegerRange.
     * @param from start
     * @param to end
     */
    public IntegerRange(Number from, Number to) {
        this(from.intValue(), to.intValue());
    }

    /**
     * Create a new IntegerRange.
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Number from, Number to, Number step) {
        this(from.intValue(), to.intValue(), step.intValue());
    }

    /**
     * Create a new IntegerRange.
     * @param from start
     * @param to end
     */
    public IntegerRange(int from, int to) {
        this(from, to, defaultStep(from, to));
    }
    
    /**
     * Create a new IntegerRange.
     * @param from start
     * @param to end
     */
    public IntegerRange(int from, int to, int step) {
        this(from, DEFAULT_LOWER_BOUND_TYPE, to, DEFAULT_UPPER_BOUND_TYPE, step);
    }

    /**
     * Create a new IntegerRange.
     * @param lowerLimit start
     * @param upperLimit end
     * @param step increment
     */
    public IntegerRange(int lowerLimit, BoundType lowerBoundType, int upperLimit, BoundType upperBoundType, int step) {
        if (lowerLimit != upperLimit && signOf(step) != signOf(upperLimit-lowerLimit)) {
            throw new IllegalArgumentException("Will never reach " + upperLimit + " from " + lowerLimit + " using step " + step);
        }
        this.lowerLimit = new Endpoint<Integer>(lowerLimit, lowerBoundType);
        this.upperLimit = new Endpoint<Integer>(upperLimit, upperBoundType);;
        this.step = step;
    }
    
    /**
     * {@inheritDoc}
     */
    public Integer getLowerLimit() {
	return this.lowerLimit.getValue();
    }
    
    /**
     * {@inheritDoc}
     */
    public BoundType getLowerLimitBoundType() {
        return this.lowerLimit.getBoundType();
    }
    
    /**
     * {@inheritDoc}
     */
    public Integer getUpperLimit() {
	return this.upperLimit.getValue();
    }
    
    /**
     * {@inheritDoc}
     */
    public BoundType getUpperLimitBoundType() {
        return this.upperLimit.getBoundType();
    }
    
    /**
     * {@inheritDoc}
     */
    public Integer getStep() {
	return step;
    }
    
    // private methods
    //---------------------------------------------------------------
    /**
     * Get <code>value/|value|</code> (0 when value == 0).
     * @param value to test
     * @return int
     */
    static int signOf(int value) {
        return value < 0 ? -1 : value > 0 ? 1 : 0;
    }

    /**
     * Calculate default step to get from <code>from</code> to <code>to</code>.
     * @param from start
     * @param to end
     * @return int
     */
    static int defaultStep(int from, int to) {
        return from > to ? -1 : 1;
    }
}
