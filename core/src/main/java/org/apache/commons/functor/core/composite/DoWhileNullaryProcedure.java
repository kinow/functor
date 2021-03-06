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

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;


/**
 * A {@link NullaryProcedure} implementation of a while loop. Given a {@link NullaryPredicate}
 * <i>c</i> and an {@link NullaryProcedure} <i>p</i>, {@link #run runs}
 * <code>do { p.run(); } while(c.test())</code>.
 * <p>
 * Note that although this class implements
 * {@link java.io.Serializable}, a given instance will
 * only be truly <code>Serializable</code> if all the
 * underlying functors are.  Attempts to serialize
 * an instance whose delegates are not all
 * <code>Serializable</code> will result in an exception.
 * </p>
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class DoWhileNullaryProcedure extends AbstractLoopNullaryProcedure {
    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -6064417600588553892L;

    /**
     * Create a new DoWhileNullaryProcedure.
     * @param action to do
     * @param condition while true
     */
    public DoWhileNullaryProcedure(NullaryProcedure action, NullaryPredicate condition) {
        super(condition, action);
    }

    /**
     * {@inheritDoc}
     */
    public final void run() {
        do {
            getAction().run();
        } while (getCondition().test());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DoWhileNullaryProcedure<do(" + getAction() + ") while(" + getCondition() + ")>";
    }
}
