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
package org.apache.commons.functor.adapter;

import java.io.Serializable;

import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.NullaryFunction;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link BinaryFunction BinaryFunction}
 * to the
 * {@link NullaryFunction NullaryFunction} interface
 * using constant arguments.
 * <p/>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying objects are.  Attempts to serialize
 * an instance whose delegates are not
 * <code>Serializable</code> will result in an exception.
 *
 * @param <T> the returned value type.
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class FullyBoundNullaryFunction<T> implements NullaryFunction<T>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -8588331452137525985L;
    /** The {@link BinaryFunction BinaryFunction} I'm wrapping. */
    private final BinaryFunction<Object, Object, ? extends T> function;
    /** The left parameter to pass to {@code function}. */
    private final Object left;
    /** The right parameter to pass to {@code function}. */
    private final Object right;

    /**
     * Create a new FullyBoundNullaryFunction.
     * @param <L> the left argument type.
     * @param <R> the right argument type.
     * @param function the function to adapt
     * @param left the left side argument to use
     * @param right the right side argument to use
     */
    @SuppressWarnings("unchecked")
    public <L, R> FullyBoundNullaryFunction(
            BinaryFunction<? super L, ? super R, ? extends T> function,
            L left, R right) {
        this.function =
            (BinaryFunction<Object, Object, T>) Validate.notNull(function,
                "BinaryFunction argument was null");
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate() {
        return function.evaluate(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof FullyBoundNullaryFunction<?>
                && equals((FullyBoundNullaryFunction<?>) that));
    }

    /**
     * Learn whether another FullyBoundNullaryFunction is equal to this.
     * @param that FullyBoundNullaryFunction to test
     * @return boolean
     */
    public boolean equals(FullyBoundNullaryFunction<?> that) {
        return null != that && function.equals(that.function)
                && (null == left ? null == that.left : left.equals(that.left))
                && (null == right ? null == that.right : right.equals(that.right));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FullyBoundNullaryFunction".hashCode();
        hash <<= 2;
        hash ^= function.hashCode();
        hash <<= 2;
        if (null != left) {
            hash ^= left.hashCode();
        }
        hash <<= 2;
        if (null != right) {
            hash ^= right.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FullyBoundNullaryFunction<" + function + "(" + left + ", " + right + ")>";
    }

    /**
     * Adapt a BinaryNullaryFunction as a NullaryFunction.
     * @param <L> left type
     * @param <R> right type
     * @param <T> result type
     * @param function to adapt
     * @param left left side argument
     * @param right right side argument
     * @return FullyBoundNullaryFunction
     */
    public static <L, R, T> FullyBoundNullaryFunction<T> bind(
            BinaryFunction<? super L, ? super R, ? extends T> function, L left, R right) {
        return null == function ? null : new FullyBoundNullaryFunction<T>(function, left, right);
    }

}
