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

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * Adapts a <code>Boolean</code>-valued
 * {@link Function Function}
 * to the {@link Predicate Predicate}
 * interface.
 * <p/>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying function is.  Attempts to serialize
 * an instance whose delegate is not
 * <code>Serializable</code> will result in an exception.
 *
 * @param <A> the argument type.
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public final class FunctionPredicate<A> implements Predicate<A>, Serializable {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -9211927278252224707L;
    /** The {@link Function Function} I'm wrapping. */
    private final Function<? super A, Boolean> function;

    /**
     * Create an {@link Predicate Predicate} wrapping
     * the given {@link Function Function}.
     * @param function the {@link Function Function} to wrap
     */
    public FunctionPredicate(Function<? super A, Boolean> function) {
        this.function = Validate.notNull(function, "Function argument was null");
    }

    /**
     * {@inheritDoc}
     * Returns the <code>boolean</code> value of the non-<code>null</code>
     * <code>Boolean</code> returned by the {@link Function#evaluate evaluate}
     * method of my underlying function.
     */
    public boolean test(A obj) {
        return function.evaluate(obj).booleanValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this
                || (that instanceof FunctionPredicate<?> && equals((FunctionPredicate<?>) that));
    }

    /**
     * Learn whether another FunctionPredicate is equal to this.
     * @param that FunctionPredicate to test
     * @return boolean
     */
    public boolean equals(FunctionPredicate<?> that) {
        return null != that && function.equals(that.function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = "FunctionPredicate".hashCode();
        hash ^= function.hashCode();
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FunctionPredicate<" + function + ">";
    }

    /**
     * Adapt the given, possibly-<code>null</code>,
     * {@link Function Function} to the
     * {@link Predicate Predicate} interface.
     * When the given <code>Function</code> is <code>null</code>,
     * returns <code>null</code>.
     *
     * @param <A> the argument type.
     * @param function the possibly-<code>null</code>
     *        {@link Function Function} to adapt
     * @return a {@link Predicate Predicate} wrapping the given
     *         {@link Function Function}, or <code>null</code>
     *         if the given <code>Function</code> is <code>null</code>
     */
    public static <A> FunctionPredicate<A> adapt(Function<? super A, Boolean> function) {
        return null == function ? null : new FunctionPredicate<A>(function);
    }

}
