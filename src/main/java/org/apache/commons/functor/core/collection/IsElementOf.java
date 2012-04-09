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
package org.apache.commons.functor.core.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.functor.Predicate;
import org.apache.commons.lang3.Validate;

/**
 * A {@link BinaryPredicate} that checks to see if the
 * specified object is an element of the specified
 * Collection.
 *
 * @param <L> the left argument type.
 * @param <R> the right argument type.
 * @since 1.0
 * @version $Revision: 1234990 $ $Date: 2012-01-23 19:18:10 -0200 (Mon, 23 Jan 2012) $
 * @author  Jason Horman (jason@jhorman.org)
 * @author  Rodney Waldhoff
 */
public final class IsElementOf implements Predicate<Object>, Serializable {
    // static members
    //---------------------------------------------------------------

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = -7639051806015321070L;

    // constructors
    //---------------------------------------------------------------
    /**
     * Create a new IsElementOf.
     */
    public IsElementOf() {
    }

    // instance methods
    //---------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public boolean test(Object... args) {
    	Validate.notNull(args, "Arguments must not be null.");
    	Validate.isTrue(args.length >= 2, "Arguments number must be at least 2");
    	Object col = args[0];
    	Object obj = args[1];
        if (col instanceof Collection<?>) {
            return testCollection(obj, (Collection<?>) col);
        }
        if (col.getClass().isArray()) {
            return testArray(obj, col);
        }
        throw new IllegalArgumentException("Expected Collection or Array, found " + col.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IsElementOf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return "IsElementOf".hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IsElementOf";
    }

    /**
     * Test a collection.
     * @param obj to find
     * @param col to search
     * @return boolean
     */
    private boolean testCollection(Object obj, Collection<?> col) {
        return col.contains(obj);
    }

    /**
     * Test an array.
     * @param obj to find
     * @param array to search
     * @return boolean
     */
    private boolean testArray(Object obj, Object array) {
        for (int i = 0, m = Array.getLength(array); i < m; i++) {
            Object value = Array.get(array, i);
            if (obj == value) {
                return true;
            }
            if (obj != null && obj.equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
		IsElementOf isElementOf = new IsElementOf();
		List<String> tokens = Arrays.asList("a", "b");
		System.out.println(isElementOf.test(tokens, "a"));
	}

}
