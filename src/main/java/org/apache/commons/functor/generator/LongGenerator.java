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
import org.apache.commons.functor.range.LongRange;


/**
 * A generator for the range <i>from</i> (inclusive) to <i>to</i> (exclusive).
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public final class LongGenerator extends BaseGenerator<Long> {
    // attributes
    //---------------------------------------------------------------
    /**
     * The range of this generator.
     */
    private final LongRange range;

    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new LongGenerator.
     * 
     * @param from start
     * @param to end
     */
    public LongGenerator(long from, long to) {
        this(from, to, LongRange.DEFAULT_STEP.evaluate(from, to));
    }
    
    /**
     * Create a new LongGenerator.
     * 
     * @param from start
     * @param to end
     */
    public LongGenerator(long from, long to, long step) {
        this(from, LongRange.DEFAULT_LEFT_BOUND_TYPE, to, LongRange.DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new LongGenerator.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public LongGenerator(long from, BoundType lowerBoundType, long to, BoundType upperBoundType, long step) {
        if (from != to && Long.signum(step) != Long.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.range = new LongRange(from, lowerBoundType, to, upperBoundType, step);
    }
    
    /**
     * Create a new LongGenerator.
     * 
     * @param range
     */
    public LongGenerator(LongRange range) {
	this.range = range;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * Get the range of this generator.
     * 
     * @return the range
     */
    public LongRange getRange() {
	return (LongRange) range;
    }
    
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Long> proc) {
	final long step = this.range.getStep();
	final boolean includeLowerLimit = this.range.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	final boolean includeUpperLimit = this.range.getRightEndpoint().getBoundType() == BoundType.CLOSED;
	final long from = includeLowerLimit ? this.range.getLeftEndpoint().getValue() : (this.range.getLeftEndpoint().getValue() + step);
	final long to = includeUpperLimit ? this.range.getRightEndpoint().getValue() : (this.range.getRightEndpoint().getValue() - step);
        if (step < 0) {
            for (long i = from; i >= to; i += step) {
                proc.run(Long.valueOf(i));
            }
        } else {
            for (long i = from; i <= to; i += step) {
                proc.run(Long.valueOf(i));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LongGenerator<" + this.range.toString() + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LongGenerator)) {
            return false;
        }
        LongGenerator that = (LongGenerator) obj;
        return this.range.equals(that.getRange());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "LongGenerator".hashCode();
        hash <<= 2;
        hash ^= this.range.hashCode();
        return hash;
    }
}
