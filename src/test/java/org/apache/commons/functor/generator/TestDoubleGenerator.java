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
import org.apache.commons.functor.range.DoubleRange;
import org.apache.commons.functor.range.Endpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: $ $Date: $
 */
public class TestDoubleGenerator extends BaseFunctorTest {
    // Attributes
    // ------------------------------------------------------------------------
    private DoubleRange ascDoubleRange = null;
    private DoubleGenerator ascDoubleGenerator = null;
    private DoubleRange descDoubleRange = null;
    private DoubleGenerator descDoubleGenerator = null;
    private Collection<Double> expectedAsc = null;
    private Collection<Double> expectedDesc = null;
    
    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
	ascDoubleRange = new DoubleRange(0.0d, 10.0d);
	ascDoubleGenerator = new DoubleGenerator(ascDoubleRange);
	descDoubleRange = new DoubleRange(10.0d, 0.0d);
	descDoubleGenerator = new DoubleGenerator(descDoubleRange);
	expectedAsc = Arrays.asList(0.0d, 1.0d, 2.0d, 3.0d, 4.0d, 5.0d, 6.0d, 7.0d, 8.0d, 9.0d);
	expectedDesc = Arrays.asList(10.0d, 9.0d, 8.0d, 7.0d, 6.0d, 5.0d, 4.0d, 3.0d, 2.0d , 1.0d);
    }
    
    @After
    public void tearDown() {
	ascDoubleRange = null;
	ascDoubleGenerator = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return new DoubleGenerator(10, 20);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Doubles from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Double> list = (List<? super Double>)(new DoubleGenerator(0,10).to(new ArrayList<Double>()));
            for (int i=0;i<10;i++) {
                assertEquals(new Double(i),list.get(i));
            }
        }

        // generates a collection of Doubles from 10 (inclusive) to 0 (exclusive)
        {
            List<? super Double> list = (List<? super Double>)(new DoubleGenerator(10,0).to(new ArrayList<Double>()));
            for (int i=10;i>0;i--) {
                assertEquals(new Double(i),list.get(10-i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            new DoubleGenerator(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            new DoubleGenerator(2, 2, 1); // positive step is ok when range is empty
        }
        {
            new DoubleGenerator(2, 2, -1); // negative step is ok when range is empty
        }
        {
            new DoubleGenerator(0, 1, 10); // big steps are ok
        }
        {
            new DoubleGenerator(1, 0, -10); // big steps are ok
        }
        try {
            new DoubleGenerator(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new DoubleGenerator(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new DoubleGenerator(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        DoubleGenerator range = new DoubleGenerator(new Double(0), new Double(5));
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
        range = new DoubleGenerator(new Double(0), new Double(5), new Double(1));
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        DoubleGenerator range = new DoubleGenerator(10, 0, -2);
        assertEquals("[10.0, 8.0, 6.0, 4.0, 2.0]", range.toCollection().toString());
        assertEquals("[10.0, 8.0, 6.0, 4.0, 2.0]", range.toCollection().toString());
    }

    @Test
    public void testStep() {
        DoubleGenerator range = new DoubleGenerator(0, 10, 2);
        assertEquals("[0.0, 2.0, 4.0, 6.0, 8.0]", range.toCollection().toString());
        assertEquals("[0.0, 2.0, 4.0, 6.0, 8.0]", range.toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        DoubleGenerator range = new DoubleGenerator(0, 5);
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
        assertEquals("[0.0, 1.0, 2.0, 3.0, 4.0]", range.toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        DoubleGenerator range = new DoubleGenerator(5, 0);
        assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", range.toCollection().toString());
        assertEquals("[5.0, 4.0, 3.0, 2.0, 1.0]", range.toCollection().toString());
    }

//    @Test
//    public void testEdgeCase() {
//        DoubleGenerator range = new DoubleGenerator(Double.MAX_VALUE - 3.0d, Double.MAX_VALUE);
//        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
//        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
//    }
    
    @Test
    public void testBoundaries() {
	DoubleRange range = new DoubleRange(0.0d, 10.0d);
	assertEquals(new Endpoint<Comparable<?>>(0.0d, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10.0d, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscending() {
	// [-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
	DoubleRange range = new DoubleRange(-5.0d, BoundType.CLOSED, 5.0d, BoundType.CLOSED, 3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// [-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
	List<Double> expected = Arrays.asList(-5.0d, -2.0d, 1.0d, 4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedAscending() {
	// (-5.0d, 5.0d], 3.0d = -2.0d, 1.0d, 4.0d
	DoubleRange range = new DoubleRange(-5.0d, BoundType.OPEN, 5.0d, BoundType.CLOSED, 3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (-5.0d, 5.0d], 3.0d = -2.0d, 1.0d, 4.0d
	List<Double> expected = Arrays.asList(-2.0d, 1.0d, 4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenAscending() {
	// [-5.0d, 5.0d), 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
	DoubleRange range = new DoubleRange(-5.0d, BoundType.CLOSED, 5.0d, BoundType.OPEN, 3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (-5.0d, 5.0d], 3.0d = -5.0d, -2.0d, 1.0d, 4.0d
	List<Double> expected = Arrays.asList(-5.0d, -2.0d, 1.0d, 4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenAscending() {
	// (-5.0d, 5.0d), 3.0d = -2.0d, 1.0d, 4.0d
	DoubleRange range = new DoubleRange(-5.0d, BoundType.OPEN, 5.0d, BoundType.OPEN, 3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (-5.0d, 5.0d), 3.0d = -2.0d, 1.0d, 4.0d
	List<Double> expected = Arrays.asList(-2.0d, 1.0d, 4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepAscending() {
	// (-2.0d, 2.0d], 1.0d = -1.0d, 0.0d, 1.0d, 2.0d
	DoubleRange range = new DoubleRange(-2.0d, BoundType.OPEN, 2.0d, BoundType.CLOSED, 1.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (-2.0d, 2.0d], 1.0d = -1.0d, 0.0d, 1.0d, 2.0d
	List<Double> expected = Arrays.asList(-1.0d, 0.0d, 1.0d, 2.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedClosedDescending() {
	// [5.0d, -5.0d], -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
	DoubleRange range = new DoubleRange(5.0d, BoundType.CLOSED, -5.0d, BoundType.CLOSED, -3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// [5.0d, -5.0d], -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
	List<Double> expected = Arrays.asList(5.0d, 2.0d, -1.0d, -4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedDescending() {
	// (5.0d, -5.0d], -3.0d = 2.0d, -1.0d, -4.0d
	DoubleRange range = new DoubleRange(5.0d, BoundType.OPEN, -5.0d, BoundType.CLOSED, -3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (5.0d, -5.0d], -3.0d = 2.0d, -1.0d, -4.0d
	List<Double> expected = Arrays.asList(2.0d, -1.0d, -4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenDescending() {
	// [5.0d, -5.0d), -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
	DoubleRange range = new DoubleRange(5.0d, BoundType.CLOSED, -5.0d, BoundType.OPEN, -3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// [5.0d, -5.0d), -3.0d = 5.0d, 2.0d, -1.0d, -4.0d
	List<Double> expected = Arrays.asList(5.0d, 2.0d, -1.0d, -4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenDescending() {
	// (5.0d, -5.0d), -3.0d = 2.0d, -1.0d, -4.0d
	DoubleRange range = new DoubleRange(5.0d, BoundType.OPEN, -5.0d, BoundType.OPEN, -3.0d);
	DoubleGenerator generator = new DoubleGenerator(range);
	// (5.0d, -5.0d), -3.0d = 2.0d, -1.0d, -4.0d
	List<Double> expected = Arrays.asList(2.0d, -1.0d, -4.0d);
	Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepDescending() {
	// [2.0d, -2.0d), -1.0d = 2.0d, 1.0d, 0.0d, -1.0d
        DoubleRange range = new DoubleRange(2.0d, BoundType.CLOSED, -2.0d, BoundType.OPEN, -1.0d);
        DoubleGenerator generator = new DoubleGenerator(range);
	// [2.0d, -2.0d), -1.0d = 2.0d, 1.0d, 0.0d, -1.0d
        List<Double> expected = Arrays.asList(2.0d, 1.0d, 0.0d, -1.0d);
        Collection<Double> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testAscending() {
	final List<Double> list = new ArrayList<Double>();
	ascDoubleGenerator.run(new UnaryProcedure<Double>() {
	    public void run(Double obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedAsc.containsAll(list));
    }
    
    @Test
    public void testDescending() {
	final List<Double> list = new ArrayList<Double>();
	descDoubleGenerator.run(new UnaryProcedure<Double>() {
	    public void run(Double obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedDesc.containsAll(list));
    }
    
    @Test
    public void testToCollection() {
	Collection<Double> ascCol = ascDoubleGenerator.toCollection();
	assertEquals("Different collections", expectedAsc, ascCol);
	Collection<Double> descCol = descDoubleGenerator.toCollection();
	assertEquals("Different collections", expectedDesc, descCol);
    }
    
    @Test
    public void testTransformedGenerator() {
	double expected = 45.0d;
	double total = ascDoubleGenerator.to(new UnaryFunction<Generator<? extends Double>, Double>() {
	    public Double evaluate(Generator<? extends Double> obj) {
		double total = 0.0d;
		for(Object element : obj.toCollection()) {
		    total += (Double)element;
		}
		return total;
	    }
	});
	assertTrue(expected == total);
	expected = 55.0d;
	total = descDoubleGenerator.to(new UnaryFunction<Generator<? extends Double>, Double>() {
	    public Double evaluate(Generator<? extends Double> obj) {
		double total = 0.0d;
		for(Object element : obj.toCollection()) {
		    total += (Double)element;
		}
		return total;
	    }
	});
	assertTrue(expected == total);
    }

    @Test
    public void testEquals() {
        DoubleGenerator range = new DoubleGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new DoubleGenerator(1, 5));
        assertObjectsAreEqual(range, new DoubleGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new DoubleGenerator(new Double(1), new Double(5)));
        assertObjectsAreEqual(range, new DoubleGenerator(new Double(1), new Short((short)5), new Double(1)));
        
        // equals basic properties
     	assertEquals("equals must be reflexive", ascDoubleGenerator, ascDoubleGenerator);
     	assertEquals("hashCode must be reflexive", ascDoubleGenerator.hashCode(),
     		ascDoubleGenerator.hashCode());
     	assertTrue(!ascDoubleGenerator.equals(null)); // should be able to compare to null

     	if (ascDoubleGenerator.equals(descDoubleGenerator)) {
     	    assertEquals("equals implies hash equals", ascDoubleGenerator.hashCode(),
     		    descDoubleGenerator.hashCode());
     	    assertEquals("equals must be symmetric", descDoubleGenerator, ascDoubleGenerator);
     	} else {
     	    assertTrue("equals must be symmetric", !descDoubleGenerator.equals(ascDoubleGenerator));
     	}

     	// Using different constructors
     	assertEquals("Invalid equals using different constructor",ascDoubleGenerator,new DoubleGenerator(0.0d, 10.0d, 1.0d));
     	assertEquals("Invalid equals using different constructor",ascDoubleGenerator,new DoubleGenerator(0.0d, BoundType.CLOSED, 10.0d, BoundType.OPEN, 1.0d));
     	
     	assertFalse("Invalid equals using different constructor",ascDoubleGenerator.equals(new DoubleGenerator(1.0d, 10.0d, 1.0d)));
    }
    
    @Test
    public void testToString() {
	assertEquals("DoubleGenerator<DoubleRange<[0.0, 10.0), 1.0>>", ascDoubleGenerator.toString());
	assertEquals("DoubleGenerator<DoubleRange<[10.0, 0.0), -1.0>>", descDoubleGenerator.toString());
    }

}