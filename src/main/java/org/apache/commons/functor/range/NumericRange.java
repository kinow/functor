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
     * {@inheritDoc}
     */
    public boolean isEmpty() {
	double leftValue = this.getLeftEndpoint().getValue().doubleValue();
	double rightValue = this.getRightEndpoint().getValue().doubleValue();
	boolean closedLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	boolean closedRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
	if(!closedLeft && !closedRight && this.getLeftEndpoint().equals(this.getRightEndpoint())) {
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
     * {@inheritDoc}
     */
    public boolean contains(T obj) {
	if(obj == null) {
	    return Boolean.FALSE;
	}
	double leftValue = this.getLeftEndpoint().getValue().doubleValue();
	double rightValue = this.getRightEndpoint().getValue().doubleValue();
	boolean includeLeft = this.getLeftEndpoint().getBoundType() == BoundType.CLOSED;
	boolean includeRight = this.getRightEndpoint().getBoundType() == BoundType.CLOSED;
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
	DoubleRange doubleRange = new DoubleRange(leftValue, this.getLeftEndpoint().getBoundType(), rightValue, this.getRightEndpoint().getBoundType(), step);
	return (step == 1 || step == -1) ? Boolean.TRUE : new DoubleGenerator(doubleRange).toCollection().contains(Double.valueOf(obj.doubleValue()));
    }
    
    /**
     * {@inheritDoc}
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
