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

    public boolean isEmpty() {
	if(this.getLowerLimit() == null || this.getUpperLimit() == null) {
	    return Boolean.TRUE;
	} 
	double lowerValue = this.getLowerLimit().getValue().doubleValue();
	double upperValue = this.getUpperLimit().getValue().doubleValue();
	boolean includeLower = this.getLowerLimit().getBoundType() == BoundType.CLOSED;
	boolean includeUpper = this.getUpperLimit().getBoundType() == BoundType.CLOSED;
	if(!includeLower && ! includeUpper && this.getLowerLimit() == this.getUpperLimit()) {
	    return Boolean.TRUE;
	}
	double step = this.getStep().doubleValue();
	if(step > 0.0) {
	    double firstValue = includeLower ? lowerValue : lowerValue + step;
	    return includeUpper ? firstValue > upperValue : firstValue >= upperValue;
	} else {
	    double firstValue = includeUpper ? upperValue : upperValue - step;
	    return includeLower ? firstValue < lowerValue : firstValue <= lowerValue;
	}
    }
    
    public boolean contains(T obj) {
	double lowerValue = this.getLowerLimit().getValue().doubleValue();
	double upperValue = this.getUpperLimit().getValue().doubleValue();
	boolean includeLower = this.getLowerLimit().getBoundType() == BoundType.CLOSED;
	boolean includeUpper = this.getUpperLimit().getBoundType() == BoundType.CLOSED;
	double step = this.getStep().doubleValue();
	double value = obj.doubleValue();
	boolean within = Boolean.FALSE;
	if(includeLower && includeUpper) {
	    within = value >= lowerValue && value <= upperValue;
	} else if(includeLower && !includeUpper) {
	    within = value >= lowerValue && value < upperValue;
	} else if(!includeLower && includeUpper) {
	    within = value > lowerValue && value <= upperValue;
	} else {
	    within = value > lowerValue && value < upperValue;
	}
	DoubleRange doubleRange = new DoubleRange(this.getLowerLimit().getValue().doubleValue(), this.getLowerLimit().getBoundType(), this.getUpperLimit().getValue().doubleValue(), this.getUpperLimit().getBoundType(), step);
	return (step == 1 || step == -1) ? within : new DoubleGenerator(doubleRange).toCollection().contains(Double.valueOf(obj.doubleValue()));
    }
    
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
