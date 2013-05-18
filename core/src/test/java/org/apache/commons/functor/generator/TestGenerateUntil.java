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
package org.apache.commons.functor.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.generator.util.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Generate Until class.
 * @version $Revision: 1365330 $ $Date: 2012-07-24 19:40:04 -0300 (Tue, 24 Jul 2012) $
 */
public class TestGenerateUntil
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = new IntegerRange(1, 10);
        generateUntil = new GenerateUntil<Integer>(wrappedGenerator, isMoreThanFive);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        isMoreThanFive = null;
        generateUntil = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullWrappedPredicate() {
        new GenerateUntil<Integer>(generateUntil, null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullGenerator() {
        new GenerateUntil<Integer>(null, isMoreThanFive);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullGeneratorOrNullWrappedPredicate() {
        new GenerateUntil<Integer>(null, null);
    }

    @Test
    public void testEquals() {
        Generator<Integer> anotherGenerate = new GenerateUntil<Integer>(new IntegerRange(1, 10), isMoreThanFive);
        assertEquals(generateUntil, generateUntil);
        assertEquals(generateUntil, anotherGenerate);
        assertTrue(!generateUntil.equals((GenerateUntil<Integer>)null));

		Generator<Integer> aGenerateWithADifferentPredicate = new GenerateUntil<Integer>(
				new IntegerRange(1, 10),
				new UnaryPredicate<Integer>() {
				public boolean test(Integer obj) {
					return obj > FIVE;
				}
			});
        assertTrue(!generateUntil.equals(aGenerateWithADifferentPredicate));

        Generator<Integer> aGenerateWithADifferentWrapped = new GenerateUntil<Integer>(new IntegerRange(1,2), isMoreThanFive);
        assertTrue(!generateUntil.equals(aGenerateWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(generateUntil.hashCode(), generateUntil.hashCode());
        assertEquals(generateUntil.hashCode(), new GenerateUntil<Integer>(wrappedGenerator, isMoreThanFive).hashCode());
    }

    // Attributes
    // ------------------------------------------------------------------------
    private static final Integer FIVE = new Integer(5);

    private Generator<Integer> wrappedGenerator = null;
    private UnaryPredicate<Integer> isMoreThanFive = new UnaryPredicate<Integer>() {
        public boolean test( Integer obj ) {
            return obj > FIVE;
        }
    };
    private Generator<Integer> generateUntil = null;
}
