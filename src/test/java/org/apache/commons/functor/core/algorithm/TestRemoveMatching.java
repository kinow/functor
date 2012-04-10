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
package org.apache.commons.functor.core.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.algorithm.RemoveMatching;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link RemoveMatching} algorithm.
 */
public class TestRemoveMatching extends BaseFunctorTest {

	// Lifecycle
    // ------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<Integer>();
        evens = new ArrayList<Integer>();
        for (int i=0;i<10;i++) {
            list.add(new Integer(i));
            if (i%2 == 0) {
                evens.add(new Integer(i));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        evens = null;
    }

    // Tests
    // ------------------------------------------------------------------------
	
	@Override
	protected Object makeFunctor() throws Exception {
		return new RemoveMatching<Integer>();
	}
	
	@Test
    public void testRemove() {
        new RemoveMatching<Integer>().run(list.iterator(),isOdd);
        assertEquals(evens,list);
    }
	
	// Attributes
    // ------------------------------------------------------------------------
    private List<Integer> list = null;
    private List<Integer> evens = null;
    private UnaryPredicate<Integer> isOdd = new UnaryPredicate<Integer>() {
        public boolean test(Integer obj) {
            return obj % 2 != 0;
        }
    };

    // Classes
    // ------------------------------------------------------------------------

    static class Doubler implements UnaryFunction<Integer, Integer> {
        public Integer evaluate(Integer obj) {
            return new Integer(2*obj);
        }
    }

}
