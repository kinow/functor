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
 * A range of integers.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class IntegerRange extends NumericRange<Integer, Integer> {
    // attributes
    //---------------------------------------------------------------
    /**
     * Lower limit.
     */
    private final Endpoint<Integer> lowerLimit;
    /**
     * Upper limit.
     */
    private final Endpoint<Integer> upperLimit;
    /**
     * Increment step.
     */
    private final int step;
    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Integer, Integer, Integer> DEFAULT_STEP = new BinaryFunction<Integer, Integer, Integer>() {
	public Integer evaluate(Integer left, Integer right) {
	    return left > right ? -1 : 1;
	}
    };
    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new IntegerRange.
     * 
     * @param from start
     * @param to end
     */
    public IntegerRange(Number from, Number to) {
        this(from.intValue(), to.intValue());
    }

    /**
     * Create a new IntegerRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(Number from, Number to, Number step) {
        this(from.intValue(), to.intValue(), step.intValue());
    }

    /**
     * Create a new IntegerRange.
     * 
     * @param from start
     * @param to end
     */
    public IntegerRange(int from, int to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }
    
    /**
     * Create a new IntegerRange.
     * 
     * @param from start
     * @param to end
     */
    public IntegerRange(int from, int to, int step) {
        this(from, DEFAULT_LOWER_BOUND_TYPE, to, DEFAULT_UPPER_BOUND_TYPE, step);
    }

    /**
     * Create a new IntegerRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerRange(int from, BoundType lowerBoundType, int to, BoundType upperBoundType, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to-from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.lowerLimit = new Endpoint<Integer>(from, lowerBoundType);
        this.upperLimit = new Endpoint<Integer>(to, upperBoundType);;
        this.step = step;
    }
    
    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Integer> getLowerLimit() {
	return this.lowerLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Endpoint<Integer> getUpperLimit() {
	return this.upperLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Integer getStep() {
	return this.step;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	return "IntegerRange<" + this.lowerLimit.toLeftString() + ", " + this.upperLimit.toRightString() + ", " + this.step + ">";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntegerRange)) {
            return false;
        }
        IntegerRange that = (IntegerRange) obj;
        return this.lowerLimit.equals(that.lowerLimit) && this.upperLimit.equals(that.upperLimit) && this.step == that.step;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
	int hash = "IntegerRange".hashCode();
        hash <<= 2;
        hash ^= this.lowerLimit.getValue();
        hash <<= 2;
        hash ^= this.upperLimit.getValue();
        hash <<= 2;
        hash ^= this.step;
        return hash;
    }
}
