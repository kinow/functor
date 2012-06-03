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
 * A generator for a range of characters.
 *
 * @since 1.0
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class CharacterRange implements Range<Character, Integer> {
    // attributes
    //---------------------------------------------------------------
    /**
     * Lower limit.
     */
    private final Endpoint<Character> lowerLimit;
    /**
     * Upper limit.
     */
    private final Endpoint<Character> upperLimit;
    /**
     * Increment step.
     */
    private final int step;
    /**
     * Calculate default step.
     */
    public static final BinaryFunction<Character, Character, Integer> DEFAULT_STEP = new BinaryFunction<Character, Character, Integer>() {
	public Integer evaluate(Character left, Character right) {
	    return left > right ? -1 : 1;
	}
    };
    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new CharacterRange.
     * 
     * @param from start
     * @param to end
     */
    public CharacterRange(char from, char to) {
        this(from, to, DEFAULT_STEP.evaluate(from, to).intValue());
    }

    /**
     * Create a new CharacterRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public CharacterRange(char from, char to, int step) {
	this(from, BoundType.CLOSED, to, BoundType.CLOSED, step);
    }
    
    /**
     * Create a new CharacterRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public CharacterRange(char from, BoundType lowerBoundType, char to, BoundType upperBoundType, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to-from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.lowerLimit = new Endpoint<Character>(from, lowerBoundType);
        this.upperLimit = new Endpoint<Character>(to, upperBoundType);;
        this.step = step;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Character> getLowerLimit() {
	return this.lowerLimit;
    }
    
    /**
     * {@inheritDoc}
     */
    public Endpoint<Character> getUpperLimit() {
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
        return "CharacterRange<" + this.lowerLimit.toLeftString() + ", " + this.upperLimit.toRightString() + ", " + step + ">";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CharacterRange)) {
            return false;
        }
        CharacterRange that = (CharacterRange) obj;
        return this.lowerLimit.equals(that.lowerLimit) && this.upperLimit.equals(that.upperLimit) && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CharacterRange".hashCode();
        hash <<= 2;
        hash ^= this.lowerLimit.getValue();
        hash <<= 2;
        hash ^= this.upperLimit.getValue();
        hash <<= 2;
        hash ^= this.step;
        return hash;
    }

}
