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
 * A generator for a range of float.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class FloatRange implements Range<Float, Float> {
    // attributes
    //---------------------------------------------------------------
    /**
     * Lower limit.
     */
    private final Endpoint<Float> lowerLimit;
    /**
     * Upper limit.
     */
    private final Endpoint<Float> upperLimit;
    /**
     * Increment step.
     */
    private final float step;
    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Float, Float, Float> DEFAULT_STEP = new BinaryFunction<Float, Float, Float>() {
	public Float evaluate(Float left, Float right) {
	    return left > right ? -1f : 1f;
	}
    };
    // constructors
    //---------------------------------------------------------------
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
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }
    
    /**
     * Create a new FloatRange.
     * 
     * @param from start
     * @param to end
     */
    public FloatRange(float from, float to, float step) {
        this(from, DEFAULT_LOWER_BOUND_TYPE, to, DEFAULT_UPPER_BOUND_TYPE, step);
    }

    /**
     * Create a new FloatRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatRange(float from, BoundType lowerBoundType, float to, BoundType upperBoundType, float step) {
        if (from != to && Math.signum(step) != Math.signum(to-from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.lowerLimit = new Endpoint<Float>(from, lowerBoundType);
        this.upperLimit = new Endpoint<Float>(to, upperBoundType);;
        this.step = step;
    }
    
    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Float> getLowerLimit() {
	return this.lowerLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Endpoint<Float> getUpperLimit() {
	return this.upperLimit;
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
	return "FloatRange<" + this.lowerLimit.toLeftString() + ", " + this.upperLimit.toRightString() + ", " + this.step + ">";
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
        return this.lowerLimit.equals(that.lowerLimit) && this.upperLimit.equals(that.upperLimit) && this.step == that.step;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	int hash = "FloatRange".hashCode();
        hash <<= 2;
        hash ^= this.lowerLimit.getValue().hashCode();
        hash <<= 2;
        hash ^= this.upperLimit.getValue().hashCode();
        hash <<= 2;
        hash ^= Float.valueOf(this.step).hashCode();
        return hash;
    }
}
