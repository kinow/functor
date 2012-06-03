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

package org.apache.commons.functor.generator;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.range.BoundType;
import org.apache.commons.functor.range.IntegerRange;


/**
 * A generator for the range <i>from</i> (inclusive) to <i>to</i> (exclusive).
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public final class IntegerGenerator extends BaseGenerator<Integer> {
    // attributes
    //---------------------------------------------------------------
    /**
     * The range of this generator.
     */
    private final IntegerRange range;

    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new IntegerGenerator.
     * 
     * @param from start
     * @param to end
     */
    public IntegerGenerator(int from, int to) {
        this(from, to, IntegerRange.DEFAULT_STEP.evaluate(from, to));
    }
    
    /**
     * Create a new IntegerGenerator.
     * 
     * @param from start
     * @param to end
     */
    public IntegerGenerator(int from, int to, int step) {
        this(from, IntegerRange.DEFAULT_LEFT_BOUND_TYPE, to, IntegerRange.DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new IntegerGenerator.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public IntegerGenerator(int from, BoundType lowerBoundType, int to, BoundType upperBoundType, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.range = new IntegerRange(from, lowerBoundType, to, upperBoundType, step);
    }
    
    /**
     * Create a new IntegerGenerator.
     * 
     * @param range
     */
    public IntegerGenerator(IntegerRange range) {
	this.range = range;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * Get the range of this generator.
     * 
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
	final boolean includeLowerLimit = this.range.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	final boolean includeUpperLimit = this.range.getRightEndpoint().getBoundType() == BoundType.CLOSED;
        if (step < 0) {
            final int from = includeLowerLimit ? this.range.getLeftEndpoint().getValue() : 
        	(this.range.getLeftEndpoint().getValue() - step);
            final int to = includeUpperLimit ? this.range.getRightEndpoint().getValue() : 
        	(this.range.getRightEndpoint().getValue() + 1);
            for (int i = from; i >= to; i += step) {
                proc.run(Integer.valueOf(i));
            }
        } else {
            final int from = includeLowerLimit ? this.range.getLeftEndpoint().getValue() : 
        	(this.range.getLeftEndpoint().getValue() + step);
            final int to = includeUpperLimit ? this.range.getRightEndpoint().getValue() : 
        	(this.range.getRightEndpoint().getValue() - 1);
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
