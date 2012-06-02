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

package org.apache.commons.functor.generator.util;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.BaseGenerator;
import org.apache.commons.functor.generator.BoundType;


/**
 * A generator for the range <i>from</i> (inclusive) to <i>to</i> (exclusive).
 *
 * @since 1.0
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class IntegerGenerator extends BaseGenerator<Integer> {
    // const //TODO: check what's the standard
    //---------------------------------------------------------------
    
    
    // attributes
    //---------------------------------------------------------------

    /**
     * The integer range of this generator.
     */
    private final IntegerRange range;

    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new IntegerGenerator.
     * @param from start
     * @param to end
     */
    public IntegerGenerator(int from, int to) {
        this(from, to, IntegerRange.defaultStep(from, to));
    }
    
    /**
     * Create a new IntegerGenerator.
     * @param from start
     * @param to end
     */
    public IntegerGenerator(int from, int to, int step) {
        this(from, IntegerRange.DEFAULT_LOWER_BOUND_TYPE, to, IntegerRange.DEFAULT_UPPER_BOUND_TYPE, IntegerRange.defaultStep(from, to));
    }

    /**
     * Create a new IntegerGenerator.
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerGenerator(int from, BoundType lowerBoundType, int to, BoundType upperBoundType, int step) {
        if (from != to && IntegerRange.signOf(step) != IntegerRange.signOf(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.range = new IntegerRange(from, lowerBoundType, to, upperBoundType, step);
    }
    
    /**
     * Create a new IntegerGenerator.
     * @param range
     */
    public IntegerGenerator(IntegerRange range) {
	this.range = range;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * @return the range
     */
    public IntegerRange getRange() {
	return (IntegerRange) range;
    }
    
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Integer> proc) {
	final int step = this.range.getStep();
	final boolean includeLowerLimit = this.range.getLowerLimitBoundType() == BoundType.OPEN;
	final boolean includeUpperLimit = this.range.getUpperLimitBoundType() == BoundType.OPEN;
	final int from = includeLowerLimit ? this.range.getLowerLimit() : (this.range.getLowerLimit() + step);
	final int to = includeUpperLimit ? this.range.getUpperLimit() : (this.range.getUpperLimit() - step);
        if (step < 0) {
            for (int i = from; i >= to; i += step) {
                proc.run(Integer.valueOf(i));
            }
        } else {
            for (int i = from; i <= to; i += step) {
                proc.run(Integer.valueOf(i));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IntegerGenerator<" + this.range.toString() + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntegerGenerator)) {
            return false;
        }
        IntegerGenerator that = (IntegerGenerator) obj;
        return this.range.equals(that.getRange());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IntegerGenerator".hashCode();
        hash <<= 2;
        hash ^= this.range.hashCode();
        return hash;
    }
}
