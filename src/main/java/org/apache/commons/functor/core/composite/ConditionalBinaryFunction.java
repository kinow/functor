/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.composite;

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryFunction BinaryFunction}
 * similiar to Java's "ternary"
 * or "conditional" operator (<code>&#x3F; &#x3A;</code>).
 * Given a {@link BinaryPredicate predicate}
 * <i>p</i> and {@link BinaryFunction functions}
 * <i>f</i> and <i>g</i>, {@link #evaluate evaluates}
 * to
 * <code>p.test(x,y) ? f.evaluate(x,y) : g.evaluate(x,y)</code>.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the output function returned value type.
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public final class ConditionalBinaryFunction<L, R, T> implements BinaryFunction<L, R, T>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -994698971284481482L;

    /** Base hash integer used to shift hash. */
    private static final int HASH_SHIFT = 4;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * the condition to be evaluated.
     */
    private final BinaryPredicate<? super L, ? super R> ifPred;
    /**
     * the function executed if the condition is satisfied.
     */
    private final BinaryFunction<? super L, ? super R, ? extends T> thenFunc;
    /**
     * the function executed if the condition is not satisfied.
     */
    private final BinaryFunction<? super L, ? super R, ? extends T> elseFunc;

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new ConditionalBinaryFunction.
     * @param ifPred if
     * @param thenFunc then
     * @param elseFunc else
     */
    public ConditionalBinaryFunction(BinaryPredicate<? super L, ? super R> ifPred,
            BinaryFunction<? super L, ? super R, ? extends T> thenFunc,
            BinaryFunction<? super L, ? super R, ? extends T> elseFunc) {
        this.ifPred = Validate.notNull(ifPred, "BinaryPredicate argument was null");
        this.thenFunc = Validate.notNull(thenFunc, "'then' BinaryFunction argument was null");
        this.elseFunc = Validate.notNull(elseFunc, "'else' BinaryFunction argument was null");
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        if (ifPred.test(left, right)) {
            return thenFunc.evaluate(left, right);
        } else {
            return elseFunc.evaluate(left, right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof ConditionalBinaryFunction<?, ?, ?>
                                    && equals((ConditionalBinaryFunction<?, ?, ?>) that));
    }

    /**
     * Learn whether another ConditionalBinaryFunction is equal to this.
     * @param that ConditionalBinaryFunction to test
     * @return boolean
     */
    public boolean equals(ConditionalBinaryFunction<?, ?, ?> that) {
        return null != that
                && (null == ifPred ? null == that.ifPred : ifPred.equals(that.ifPred))
                && (null == thenFunc ? null == that.thenFunc : thenFunc.equals(that.thenFunc))
                && (null == elseFunc ? null == that.elseFunc : elseFunc.equals(that.elseFunc));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "ConditionalBinaryFunction".hashCode();
        if (null != ifPred) {
            hash <<= HASH_SHIFT;
            hash ^= ifPred.hashCode();
        }
        if (null != thenFunc) {
            hash <<= HASH_SHIFT;
            hash ^= thenFunc.hashCode();
        }
        if (null != elseFunc) {
            hash <<= HASH_SHIFT;
            hash ^= elseFunc.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConditionalBinaryFunction<" + ifPred + "?" + thenFunc + ":" + elseFunc + ">";
    }

}