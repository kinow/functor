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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.range.BoundType;
import org.apache.commons.functor.range.Endpoint;
import org.apache.commons.functor.range.FloatRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: $ $Date: $
 */
public class TestFloatGenerator extends BaseFunctorTest {
    // Attributes
    // ------------------------------------------------------------------------
    private FloatRange ascFloatRange = null;
    private FloatGenerator ascFloatGenerator = null;
    private FloatRange descFloatRange = null;
    private FloatGenerator descFloatGenerator = null;
    private Collection<Float> expectedAsc = null;
    private Collection<Float> expectedDesc = null;
    
    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
	ascFloatRange = new FloatRange(0.0f, 10.0f);
	ascFloatGenerator = new FloatGenerator(ascFloatRange);
	descFloatRange = new FloatRange(10.0f, 0.0f);
	descFloatGenerator = new FloatGenerator(descFloatRange);
	expectedAsc = Arrays.asList(0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f);
	expectedDesc = Arrays.asList(10.0f, 9.0f, 8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f , 1.0f);
    }
    
    @After
    public void tearDown() {
	ascFloatRange = null;
	ascFloatGenerator = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return new FloatGenerator(10, 20);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Floats from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Float> list = (List<? super Float>)(new FloatGenerator(0,10).to(new ArrayList<Float>()));
            for (int i=0;i<10;i++) {
                assertEquals(new Float(i),list.get(i));
            }
        }

        // generates a collection of Floats from 10 (inclusive) to 0 (exclusive)
        {
            List<? super Float> list = (List<? super Float>)(new FloatGenerator(10,0).to(new ArrayList<Float>()));
            for (int i=10;i>0;i--) {
                assertEquals(new Float(i),list.get(10-i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            new FloatGenerator(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            new FloatGenerator(2, 2, 1); // positive step is ok when range is empty
        }
        {
            new FloatGenerator(2, 2, -1); // negative step is ok when range is empty
        }
        {
            new FloatGenerator(0, 1, 10); // big steps are ok
        }
        {
            new FloatGenerator(1, 0, -10); // big steps are ok
        }
        try {
            new FloatGenerator(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new FloatGenerator(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new FloatGenerator(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        FloatGenerator range = new FloatGenerator(new Float(0), new Float(5));
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
        range = new FloatGenerator(new Float(0), new Float(5), new Float(1));
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        FloatGenerator range = new FloatGenerator(10, 0, -2);
        assertEquals("[10.0, 8.0, 6.0, 4.0, 2.0]", range.toCollection().toString());
        assertEquals("[10.0, 8.0, 6.0, 4.0, 2.0]", range.toCollection().toString());
    }

    @Test
    public void testStep() {
        FloatGenerator range = new FloatGenerator(0, 10, 2);
        assertEquals("[0.0, 2.0, 4.0, 6.0, 8.0]", range.toCollection().toString());
        assertEquals("[0.0, 2.0, 4.0, 6.0, 8.0]", range.toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        FloatGenerator range = new FloatGenerator(0, 5);
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        FloatGenerator range = new FloatGenerator(5, 0);
        assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", range.toCollection().toString());
        assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", range.toCollection().toString());
    }

//    @Test
//    public void testEdgeCase() {
//        FloatGenerator range = new FloatGenerator(Float.MAX_VALUE - 3.0f, Float.MAX_VALUE);
//        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
//        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
//    }
    
    @Test
    public void testBoundaries() {
	FloatRange range = new FloatRange(0.0f, 10.0f);
	assertEquals(new Endpoint<Comparable<?>>(0.0f, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10.0f, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscending() {
	// [-5.0f, 5.0f], 3.0f = -5.0f, -2.0f, 1.0f, 4.0f
	FloatRange range = new FloatRange(-5.0f, BoundType.CLOSED, 5.0f, BoundType.CLOSED, 3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// [-5.0f, 5.0f], 3.0f = -5.0f, -2.0f, 1.0f, 4.0f
	List<Float> expected = Arrays.asList(-5.0f, -2.0f, 1.0f, 4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedAscending() {
	// (-5.0f, 5.0f], 3.0f = -2.0f, 1.0f, 4.0f
	FloatRange range = new FloatRange(-5.0f, BoundType.OPEN, 5.0f, BoundType.CLOSED, 3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (-5.0f, 5.0f], 3.0f = -2.0f, 1.0f, 4.0f
	List<Float> expected = Arrays.asList(-2.0f, 1.0f, 4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenAscending() {
	// [-5.0f, 5.0f), 3.0f = -5.0f, -2.0f, 1.0f, 4.0f
	FloatRange range = new FloatRange(-5.0f, BoundType.CLOSED, 5.0f, BoundType.OPEN, 3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (-5.0f, 5.0f], 3.0f = -5.0f, -2.0f, 1.0f, 4.0f
	List<Float> expected = Arrays.asList(-5.0f, -2.0f, 1.0f, 4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenAscending() {
	// (-5.0f, 5.0f), 3.0f = -2.0f, 1.0f, 4.0f
	FloatRange range = new FloatRange(-5.0f, BoundType.OPEN, 5.0f, BoundType.OPEN, 3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (-5.0f, 5.0f), 3.0f = -2.0f, 1.0f, 4.0f
	List<Float> expected = Arrays.asList(-2.0f, 1.0f, 4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepAscending() {
	// (-2.0f, 2.0f], 1.0f = -1.0f, 0.0f, 1.0f, 2.0f
	FloatRange range = new FloatRange(-2.0f, BoundType.OPEN, 2.0f, BoundType.CLOSED, 1.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (-2.0f, 2.0f], 1.0f = -1.0f, 0.0f, 1.0f, 2.0f
	List<Float> expected = Arrays.asList(-1.0f, 0.0f, 1.0f, 2.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedClosedDescending() {
	// [5.0f, -5.0f], -3.0f = 5.0f, 2.0f, -1.0f, -4.0f
	FloatRange range = new FloatRange(5.0f, BoundType.CLOSED, -5.0f, BoundType.CLOSED, -3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// [5.0f, -5.0f], -3.0f = 5.0f, 2.0f, -1.0f, -4.0f
	List<Float> expected = Arrays.asList(5.0f, 2.0f, -1.0f, -4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedDescending() {
	// (5.0f, -5.0f], -3.0f = 2.0f, -1.0f, -4.0f
	FloatRange range = new FloatRange(5.0f, BoundType.OPEN, -5.0f, BoundType.CLOSED, -3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (5.0f, -5.0f], -3.0f = 2.0f, -1.0f, -4.0f
	List<Float> expected = Arrays.asList(2.0f, -1.0f, -4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenDescending() {
	// [5.0f, -5.0f), -3.0f = 5.0f, 2.0f, -1.0f, -4.0f
	FloatRange range = new FloatRange(5.0f, BoundType.CLOSED, -5.0f, BoundType.OPEN, -3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// [5.0f, -5.0f), -3.0f = 5.0f, 2.0f, -1.0f, -4.0f
	List<Float> expected = Arrays.asList(5.0f, 2.0f, -1.0f, -4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenDescending() {
	// (5.0f, -5.0f), -3.0f = 2.0f, -1.0f, -4.0f
	FloatRange range = new FloatRange(5.0f, BoundType.OPEN, -5.0f, BoundType.OPEN, -3.0f);
	FloatGenerator generator = new FloatGenerator(range);
	// (5.0f, -5.0f), -3.0f = 2.0f, -1.0f, -4.0f
	List<Float> expected = Arrays.asList(2.0f, -1.0f, -4.0f);
	Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepDescending() {
	// [2.0f, -2.0f), -1.0f = 2.0f, 1.0f, 0.0f, -1.0f
        FloatRange range = new FloatRange(2.0f, BoundType.CLOSED, -2.0f, BoundType.OPEN, -1.0f);
        FloatGenerator generator = new FloatGenerator(range);
	// [2.0f, -2.0f), -1.0f = 2.0f, 1.0f, 0.0f, -1.0f
        List<Float> expected = Arrays.asList(2.0f, 1.0f, 0.0f, -1.0f);
        Collection<Float> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testAscending() {
	final List<Float> list = new ArrayList<Float>();
	ascFloatGenerator.run(new UnaryProcedure<Float>() {
	    public void run(Float obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedAsc.containsAll(list));
    }
    
    @Test
    public void testDescending() {
	final List<Float> list = new ArrayList<Float>();
	descFloatGenerator.run(new UnaryProcedure<Float>() {
	    public void run(Float obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedDesc.containsAll(list));
    }
    
    @Test
    public void testToCollection() {
	Collection<Float> ascCol = ascFloatGenerator.toCollection();
	assertEquals("Different collections", expectedAsc, ascCol);
	Collection<Float> descCol = descFloatGenerator.toCollection();
	assertEquals("Different collections", expectedDesc, descCol);
    }
    
    @Test
    public void testTransformedGenerator() {
	Float expected = 45.0f;
	Float total = ascFloatGenerator.to(new UnaryFunction<Generator<? extends Float>, Float>() {
	    public Float evaluate(Generator<? extends Float> obj) {
		Float total = 0.0f;
		for(Object element : obj.toCollection()) {
		    total += (Float)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
	expected = 55.0f;
	total = descFloatGenerator.to(new UnaryFunction<Generator<? extends Float>, Float>() {
	    public Float evaluate(Generator<? extends Float> obj) {
		Float total = 0.0f;
		for(Object element : obj.toCollection()) {
		    total += (Float)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
    }

    @Test
    public void testEquals() {
        FloatGenerator range = new FloatGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new FloatGenerator(1, 5));
        assertObjectsAreEqual(range, new FloatGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new FloatGenerator(new Float(1), new Float(5)));
        assertObjectsAreEqual(range, new FloatGenerator(new Float(1), new Short((short)5), new Float(1)));
        
        // equals basic properties
     	assertEquals("equals must be reflexive", ascFloatGenerator, ascFloatGenerator);
     	assertEquals("hashCode must be reflexive", ascFloatGenerator.hashCode(),
     		ascFloatGenerator.hashCode());
     	assertTrue(!ascFloatGenerator.equals(null)); // should be able to compare to null

     	if (ascFloatGenerator.equals(descFloatGenerator)) {
     	    assertEquals("equals implies hash equals", ascFloatGenerator.hashCode(),
     		    descFloatGenerator.hashCode());
     	    assertEquals("equals must be symmetric", descFloatGenerator, ascFloatGenerator);
     	} else {
     	    assertTrue("equals must be symmetric", !descFloatGenerator.equals(ascFloatGenerator));
     	}

     	// Using different constructors
     	assertEquals("Invalid equals using different constructor",ascFloatGenerator,new FloatGenerator(0.0f, 10.0f, 1.0f));
     	assertEquals("Invalid equals using different constructor",ascFloatGenerator,new FloatGenerator(0.0f, BoundType.CLOSED, 10.0f, BoundType.OPEN, 1.0f));
     	
     	assertFalse("Invalid equals using different constructor",ascFloatGenerator.equals(new FloatGenerator(1.0f, 10.0f, 1.0f)));
    }
    
    @Test
    public void testToString() {
	assertEquals("FloatGenerator<FloatRange<[0.0, 10.0), 1.0>>", ascFloatGenerator.toString());
	assertEquals("FloatGenerator<FloatRange<[10.0, 0.0), -1.0>>", descFloatGenerator.toString());
    }

}