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

import java.util.Collection;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.generator.IntegerGenerator;

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
     * Left limit.
     */
    private final Endpoint<Character> leftEndpoint;
    /**
     * Right limit.
     */
    private final Endpoint<Character> rightEndpoint;
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
    public CharacterRange(char from, BoundType leftBoundType, char to, BoundType rightBoundType, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to-from)) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.leftEndpoint = new Endpoint<Character>(from, leftBoundType);
        this.rightEndpoint = new Endpoint<Character>(to, rightBoundType);;
        this.step = step;
    }
    
    /**
     * Create a new CharacterRange.
     * 
     * @param from start
     * @param to end
     * @param step increment
     */
    public CharacterRange(Endpoint<Character> from, Endpoint<Character> to, int step) {
        if (from != to && Integer.signum(step) != Integer.signum(to.getValue()-from.getValue())) {
            throw new IllegalArgumentException("Will never reach " + to + " from " + from + " using step " + step);
        }
        this.leftEndpoint = from;
        this.rightEndpoint = to;
        this.step = step;
    }

    // methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Endpoint<Character> getLeftEndpoint() {
	return this.leftEndpoint;
    }
    
    /**
     * {@inheritDoc}
     */
    public Endpoint<Character> getRightEndpoint() {
	return this.rightEndpoint;
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
        return "CharacterRange<" + this.leftEndpoint.toLeftString() + ", " + this.rightEndpoint.toRightString() + ", " + step + ">";
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
        return this.leftEndpoint.equals(that.leftEndpoint) && this.rightEndpoint.equals(that.rightEndpoint) && this.step == that.step;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "CharacterRange".hashCode();
        hash <<= 2;
        hash ^= this.leftEndpoint.getValue();
        hash <<= 2;
        hash ^= this.rightEndpoint.getValue();
        hash <<= 2;
        hash ^= this.step;
        return hash;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.functor.range.Range#isEmpty()
     */
    public boolean isEmpty() {
	double leftValue = this.getLeftEndpoint().getValue().charValue();
	double rightValue = this.getRightEndpoint().getValue().charValue();
	boolean closedLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	boolean closedRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
	if(!closedLeft && !closedRight && this.getLeftEndpoint().equals(this.getRightEndpoint())) {
	    return Boolean.TRUE;
	}
	double step = this.getStep().intValue();
	if(step > 0.0) {
	    double firstValue = closedLeft ? leftValue : leftValue + step;
	    return closedRight ? firstValue > rightValue : firstValue >= rightValue;
	} else {
	    double firstValue = closedLeft ? leftValue : leftValue + step;
	    return closedRight ? firstValue < rightValue : firstValue <= rightValue;
	}
    }

    /* (non-Javadoc)
     * @see org.apache.commons.functor.range.Range#contains(java.lang.Comparable)
     */
    public boolean contains(Character obj) {
	if(obj == null) {
	    return Boolean.FALSE;
	}
	char leftValue = this.getLeftEndpoint().getValue().charValue();
	char rightValue = this.getRightEndpoint().getValue().charValue();
	boolean includeLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	boolean includeRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
	int step = this.getStep().intValue();
	char value = obj.charValue();
	boolean within = Boolean.FALSE;
	if (step > 0.0) {
	    if (includeLeft && includeRight) {
		within = value >= leftValue && value <= rightValue;
	    } else if (includeLeft) {
		within = value >= leftValue && value < rightValue;
	    } else if (includeRight) {
		within = value > leftValue && value <= rightValue;
	    } else {
		within = value > leftValue && value < rightValue;
	    }
	} else {
	    if (includeLeft && includeRight) {
		within = value >= rightValue && value <= leftValue;
	    } else if (includeLeft) {
		within = value > rightValue && value <= leftValue;
	    } else if (includeRight) {
		within = value >= rightValue && value < leftValue;
	    } else {
		within = value > rightValue && value < leftValue;
	    }
	}
	if(!within) {
	    return Boolean.FALSE;
	}
	IntegerRange integerRange = new IntegerRange(leftValue, this.getLeftEndpoint().getBoundType(), rightValue, this.getRightEndpoint().getBoundType(), step);
	return (step == 1 || step == -1) ? Boolean.TRUE : new IntegerGenerator(integerRange).toCollection().contains(obj.charValue());
    }

    /* (non-Javadoc)
     * @see org.apache.commons.functor.range.Range#containsAll(java.util.Collection)
     */
    public boolean containsAll(Collection<Character> col) {
	if (col == null || col.size() == 0) {
	    return Boolean.FALSE;
	}
	boolean r = Boolean.TRUE;
	for (Character t : col) {
	    if (!this.contains(t)) {
		r = Boolean.FALSE;
		break;
	    }
	}
	return r;
    }

}
