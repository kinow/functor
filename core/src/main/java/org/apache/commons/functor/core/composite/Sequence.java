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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.Procedure;

/**
 * A {@link Procedure Procedure}
 * that {@link Procedure#run runs} an ordered
 * sequence of {@link Procedure Procedures}.
 * When the sequence is empty, this procedure is does
 * nothing.
 * <p>
 * Note that although this class implements
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @param <A> the argument type.
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public class Sequence<A> implements Procedure<A>, Serializable {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 9194268249717820246L;
    // attributes
    // ------------------------------------------------------------------------
    /**
     * The data structure to store the procedure sequence.
     */
    private final List<Procedure<? super A>> list = new ArrayList<Procedure<? super A>>();

    // constructor
    // ------------------------------------------------------------------------
    /**
     * Create a new Sequence.
     */
    public Sequence() {
        super();
    }

    /**
     * Create a new Sequence instance.
     *
     * @param procedures to run sequentially
     */
    public Sequence(Procedure<? super A>... procedures) {
        this();
        if (procedures != null) {
            for (Procedure<? super A> p : procedures) {
                then(p);
            }
        }
    }

    /**
     * Create a new Sequence instance.
     *
     * @param procedures to run sequentially
     */
    public Sequence(Iterable<Procedure<? super A>> procedures) {
        this();
        if (procedures != null) {
            for (Procedure<? super A> p : procedures) {
                then(p);
            }
        }
    }

    // modifiers
    // ------------------------------------------------------------------------
    /**
     * Fluently add a Procedure to the sequence.
     * @param p Procedure to add
     * @return this
     */
    public Sequence<A> then(Procedure<? super A> p) {
        if (p != null) {
            list.add(p);
        }
        return this;
    }

    // predicate interface
    // ------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void run(A obj) {
        for (Iterator<Procedure<? super A>> iter = list.iterator(); iter.hasNext();) {
            iter.next().run(obj);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return that == this || (that instanceof Sequence<?> && equals((Sequence<?>) that));
    }

    /**
     * Learn whether another Sequence is equal to this.
     * @param that Sequence to test
     * @return boolean
     */
    public boolean equals(Sequence<?> that) {
        // by construction, list is never null
        return null != that && list.equals(that.list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // by construction, list is never null
        return "Sequence".hashCode() ^ list.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Sequence<" + list + ">";
    }

}
