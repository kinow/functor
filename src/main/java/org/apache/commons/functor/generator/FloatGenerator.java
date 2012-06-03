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
import org.apache.commons.functor.range.FloatRange;


/**
 * A generator for the range <i>from</i> (inclusive) to <i>to</i> (exclusive).
 *
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public final class FloatGenerator extends BaseGenerator<Float> {
    // attributes
    //---------------------------------------------------------------
    /**
     * The range of this generator.
     */
    private final FloatRange range;

    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new FloatGenerator.
     * 
     * @param from start
     * @param to end
     */
    public FloatGenerator(float from, float to) {
        this(from, to, FloatRange.DEFAULT_STEP.evaluate(from, to));
    }
    
    /**
     * Create a new FloatGenerator.
     * 
     * @param from start
     * @param to end
     */
    public FloatGenerator(float from, float to, float step) {
        this(from, FloatRange.DEFAULT_LEFT_BOUND_TYPE, to, FloatRange.DEFAULT_RIGHT_BOUND_TYPE, step);
    }

    /**
     * Create a new FloatGenerator.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public FloatGenerator(float from, BoundType lowerBoundType, float to, BoundType upperBoundType, float step) {
        if (from != to && Math.signum(step) != Math.signum(to - from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.range = new FloatRange(from, lowerBoundType, to, upperBoundType, step);
    }
    
    /**
     * Create a new FloatGenerator.
     * 
     * @param range
     */
    public FloatGenerator(FloatRange range) {
	this.range = range;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * Get the range of this generator.
     * 
     * @return the range
     */
    public FloatRange getRange() {
	return (FloatRange) range;
    }
    
    /**
     * {@inheritDoc}
     */
    public void run(UnaryProcedure<? super Float> proc) {
	final float step = this.range.getStep();
	final boolean includeLowerLimit = this.range.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	final boolean includeUpperLimit = this.range.getRightEndpoint().getBoundType() == BoundType.CLOSED;
	final float from = includeLowerLimit ? this.range.getLeftEndpoint().getValue() : (this.range.getLeftEndpoint().getValue() + step);
	final float to = includeUpperLimit ? this.range.getRightEndpoint().getValue() : (this.range.getRightEndpoint().getValue() - step);
        if (step < 0) {
            for (float i = from; i >= to; i += step) {
                proc.run(i);
            }
        } else {
            for (float i = from; i <= to; i += step) {
                proc.run(i);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FloatGenerator<" + this.range.toString() + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FloatGenerator)) {
            return false;
        }
        FloatGenerator that = (FloatGenerator) obj;
        return this.range.equals(that.getRange());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FloatGenerator".hashCode();
        hash <<= 2;
        hash ^= this.range.hashCode();
        return hash;
    }
}
