/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Transformed Generator class.
 */
public class TestTransformedGenerator
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = new IntegerGenerator(1, 10);
        sumsTwoGenerator = new TransformedGenerator<Integer, Integer>(wrappedGenerator, sumsTwo);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        sumsTwo = null;
        sumsTwoGenerator = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testConstructorProhibitsNull() {
        try {
            new TransformedGenerator<Integer, Integer>(null, sumsTwo);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new TransformedGenerator<Integer, Integer>(wrappedGenerator, null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
        try {
            new TransformedGenerator<Integer, Integer>(null, null);
            fail("ExpectedNullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquals() {
        TransformedGenerator<Integer, Integer> anotherTransformedGenerator =
                        new TransformedGenerator<Integer, Integer>(wrappedGenerator, sumsTwo);
        assertEquals(sumsTwoGenerator, sumsTwoGenerator);
        assertEquals(sumsTwoGenerator, anotherTransformedGenerator);
        assertTrue(!sumsTwoGenerator.equals((TransformedGenerator<Integer, Integer>)null));

        TransformedGenerator<Integer, Integer> aGenerateWithADifferentFunction =
            new TransformedGenerator<Integer, Integer>(wrappedGenerator, new UnaryFunction<Integer, Integer>() {
                public Integer evaluate( Integer obj ) {
                    return obj;
                }
            });
        assertTrue( !sumsTwoGenerator.equals(aGenerateWithADifferentFunction));

        TransformedGenerator<Integer, Integer> aTransformedGeneratorWithADifferentWrapped =
        		new TransformedGenerator<Integer, Integer>(new IntegerGenerator(1,2), sumsTwo);
        assertTrue(!sumsTwoGenerator.equals(aTransformedGeneratorWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(sumsTwoGenerator.hashCode(), sumsTwoGenerator.hashCode());
        assertEquals(sumsTwoGenerator.hashCode(), new TransformedGenerator<Integer, Integer>(wrappedGenerator, sumsTwo).hashCode());
        assertFalse(sumsTwoGenerator.hashCode() == new TransformedGenerator<Integer, Integer>(wrappedGenerator, sumsTwo) {
            @Override
            protected Generator<? extends Integer> getWrappedGenerator() {
                return null;
            }
        }.hashCode());
    }

    @Test
    public void testGenerate() {
        final List<Integer> doubledValues = new ArrayList<Integer>();
        sumsTwoGenerator.run(new UnaryProcedure<Integer>() {
            public void run( Integer obj ) {
                doubledValues.add(obj);
            }
        });

        final List<Integer> expected = Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10 , 11);

        assertEquals(9, doubledValues.size());
        assertEquals(expected, doubledValues);
    }

    // Attributes
    // ------------------------------------------------------------------------
    private static final Integer TWO = new Integer(2);

    private Generator<Integer> wrappedGenerator = null;
    private UnaryFunction<Integer, Integer> sumsTwo = new UnaryFunction<Integer, Integer>() {
        public Integer evaluate( Integer obj ) {
            return obj += TWO;
        }
    };
    private TransformedGenerator<Integer, Integer> sumsTwoGenerator = null;

}
