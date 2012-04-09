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
package org.apache.commons.functor.core;

import java.io.Serializable;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Predicate;

/**
 * {@link #evaluate Evaluates} to its input argument.
 *
 * {@link #test Tests} to the <code>boolean</code>
 * value of the <code>Boolean</code>-valued parameter.
 * The {@link #test} method throws an exception if
 * the parameter isn't a non-<code>null</code>
 * <code>Boolean</code>.
 *
 * @param <T> the returned value type.
 * @version $Revision: 1187618 $ $Date: 2011-10-21 23:16:16 -0200 (Fri, 21 Oct 2011) $
 * @author Rodney Waldhoff
 */
public final class Identity implements Function<Boolean, Boolean>, Predicate<Boolean>, Serializable {
    // static attributes
    // ------------------------------------------------------------------------

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 4145504259013789494L;

    // constructor
    // ------------------------------------------------------------------------

    /**
     * Create a new Identity.
     */
    public Identity() {
    }

    // function interface
    // ------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object that) {
        return (that instanceof Identity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "Identity".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Identity";
    }

	/* (non-Javadoc)
	 * @see org.apache.commons.functor.Predicate#test(P[])
	 */
	public boolean test(Boolean... obj) {
		return obj == null ? false : obj.length <= 0 ? false : obj[0];
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.functor.Function#evaluate(P[])
	 */
	public Boolean evaluate(Boolean... obj) {
		return obj == null ? false : obj.length <= 0 ? false : obj[0];
	}

}
