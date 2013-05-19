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
import org.apache.commons.functor.Function;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a
 * {@link Function Function}
 * to the
 * {@link BinaryFunction BinaryFunction} interface
 * by ignoring the first binary argument.
 * <p/>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying functor is.  Attempts to serialize
 * an instance whose delegate is not
 * <code>Serializable</code> will result in an exception.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @param <T> the returned value type.
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public final class IgnoreLeftFunction<L, R, T> implements BinaryFunction<L, R, T>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 4677703245851183542L;
    /** The {@link Function Function} I'm wrapping. */
    private final Function<? super R, ? extends T> function;

    /**
     * Create a new IgnoreLeftFunction.
     * @param function Function for right argument
     */
    public IgnoreLeftFunction(Function<? super R, ? extends T> function) {
        this.function = Validate.notNull(function, "Function argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public T evaluate(L left, R right) {
        return function.evaluate(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof IgnoreLeftFunction<?, ?, ?>
                && equals((IgnoreLeftFunction<?, ?, ?>) that));
    }

    /**
     * Learn whether another IgnoreLeftFunction is equal to this.
     * @param that IgnoreLeftFunction to test
     * @return boolean
     */
    public boolean equals(IgnoreLeftFunction<?, ?, ?> that) {
        return null != that && function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "IgnoreLeftFunction".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IgnoreLeftFunction<" + function + ">";
    }

    /**
     * Adapt a Function to the BinaryFunction interface.
     * @param <L> left type
     * @param <R> right type
     * @param <T> result type
     * @param function to adapt
     * @return IgnoreLeftFunction
     */
    public static <L, R, T> IgnoreLeftFunction<L, R, T> adapt(Function<? super R, ? extends T> function) {
        return null == function ? null : new IgnoreLeftFunction<L, R, T>(function);
    }

}
