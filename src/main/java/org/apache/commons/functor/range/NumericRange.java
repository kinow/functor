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
package org.apache.commons.functor.range;

import java.util.Collection;

import org.apache.commons.functor.generator.DoubleGenerator;


/**
 * A base class for numeric ranges. The elements within this range must be 
 * a <b>number</b> and <b>comparable</b>.
 * 
 * @param <T> the type of numbers that are both a number and comparable
 * @param <S> the type of the step that is both a number and comparable
 * @see org.apache.commons.functor.range.IntegerRange
 * @see org.apache.commons.functor.range.LongRange
 * @see org.apache.commons.functor.range.FloatRange
 * @see org.apache.commons.functor.range.DoubleRange
 * @see org.apache.commons.functor.range.CharacterRange
 * @since 0.1
 */
public abstract class NumericRange<T extends Number & Comparable<?>, S extends Number & Comparable<?>> implements Range<T, S> {

    /**
     * TODO
     * @return
     */
    public boolean isEmpty() {
	double leftValue = this.getLowerLimit().getValue().doubleValue();
	double rightValue = this.getUpperLimit().getValue().doubleValue();
	boolean closedLeft = this.getLowerLimit().getBoundType() == BoundType.CLOSED;
	boolean closedRight = this.getUpperLimit().getBoundType() == BoundType.CLOSED;
	if(!closedLeft && !closedRight && this.getLowerLimit().equals(this.getUpperLimit())) {
	    return Boolean.TRUE;
	}
	double step = this.getStep().doubleValue();
	if(step > 0.0) {
	    double firstValue = closedLeft ? leftValue : leftValue + step;
	    return closedRight ? firstValue > rightValue : firstValue >= rightValue;
	} else {
	    double firstValue = closedLeft ? leftValue : leftValue + step;
	    return closedRight ? firstValue < rightValue : firstValue <= rightValue;
	}
    }
    
    /**
     * TODO
     * @param obj
     * @return
     */
    public boolean contains(T obj) {
	if(obj == null) {
	    return Boolean.FALSE;
	}
	double leftValue = this.getLowerLimit().getValue().doubleValue();
	double rightValue = this.getUpperLimit().getValue().doubleValue();
	boolean includeLeft = this.getLowerLimit().getBoundType() == BoundType.CLOSED;
	boolean includeRight = this.getUpperLimit().getBoundType() == BoundType.CLOSED;
	double step = this.getStep().doubleValue();
	double value = obj.doubleValue();
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
	DoubleRange doubleRange = new DoubleRange(leftValue, this.getLowerLimit().getBoundType(), rightValue, this.getUpperLimit().getBoundType(), step);
	return (step == 1 || step == -1) ? Boolean.TRUE : new DoubleGenerator(doubleRange).toCollection().contains(Double.valueOf(obj.doubleValue()));
    }
    
    /**
     * TODO
     * @param col
     * @return
     */
    public boolean containsAll(Collection<T> col) {
	if (col == null || col.size() == 0) {
	    return Boolean.FALSE;
	}
	boolean r = Boolean.TRUE;
	for (T t : col) {
	    if (!this.contains(t)) {
		r = Boolean.FALSE;
		break;
	    }
	}
	return r;
    }
    
}
