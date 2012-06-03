/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
 * A generator for a range of doubles.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class DoubleRange extends NumericRange<Double, Double> {
    // attributes
    //---------------------------------------------------------------
    /**
     * Lower limit.
     */
    private final Endpoint<Double> lowerLimit;
    /**
     * Upper limit.
     */
    private final Endpoint<Double> upperLimit;
    /**
     * Increment step.
     */
    private final double step;
    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Double, Double, Double> DEFAULT_STEP = new BinaryFunction<Double, Double, Double>() {
	public Double evaluate(Double left, Double right) {
	    return left > right ? -1d : 1d;
	}
    };
    // constructors
    //---------------------------------------------------------------
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
     */
    public DoubleRange(double from, double to, double step) {
        this(from, DEFAULT_LOWER_BOUND_TYPE, to, DEFAULT_UPPER_BOUND_TYPE, step);
    }

    /**
     * Create a new DoubleRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(double from, BoundType lowerBoundType, double to, BoundType upperBoundType, double step) {
        if (from != to && Math.signum(step) != Math.signum(to-from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.lowerLimit = new Endpoint<Double>(from, lowerBoundType);
        this.upperLimit = new Endpoint<Double>(to, upperBoundType);
        this.step = step;
    }
    
    /**
     * Create a new DoubleRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public DoubleRange(Endpoint<Double> from, Endpoint<Double> to, double step) {
        if (from != to && Math.signum(step) != Math.signum(to.getValue().doubleValue()-from.getValue().doubleValue())) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.lowerLimit = from;
        this.upperLimit = to;
        this.step = step;
    }
    
    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Double> getLowerLimit() {
	return this.lowerLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Endpoint<Double> getUpperLimit() {
	return this.upperLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Double getStep() {
	return this.step;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "DoubleRange<" + this.lowerLimit.toLeftString() + ", " + this.upperLimit.toRightString() + ", " + this.step + ">";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
            return true;
        }
        if (!(obj instanceof DoubleRange)) {
            return false;
        }
        DoubleRange that = (DoubleRange) obj;
        return this.lowerLimit.equals(that.lowerLimit) && this.upperLimit.equals(that.upperLimit) && this.step == that.step;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	int hash = "DoubleRange".hashCode();
        hash <<= 2;
        hash ^= this.lowerLimit.getValue().hashCode();
        hash <<= 2;
        hash ^= this.upperLimit.getValue().hashCode();
        hash <<= 2;
        hash ^= Double.valueOf(this.step).hashCode();
        return hash;
    }
}
