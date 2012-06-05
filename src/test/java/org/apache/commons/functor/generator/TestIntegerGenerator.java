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
import org.apache.commons.functor.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestIntegerGenerator extends BaseFunctorTest {
    // Attributes
    // ------------------------------------------------------------------------
    private IntegerRange ascIntRange = null;
    private IntegerGenerator ascIntGenerator = null;
    private IntegerRange descIntRange = null;
    private IntegerGenerator descIntGenerator = null;
    private Collection<Integer> expectedAsc = null;
    private Collection<Integer> expectedDesc = null;

    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
	ascIntRange = new IntegerRange(0, 10);
	ascIntGenerator = new IntegerGenerator(ascIntRange);
	descIntRange = new IntegerRange(10, 0);
	descIntGenerator = new IntegerGenerator(descIntRange);
	expectedAsc = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	expectedDesc = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2 , 1);
    }
    
    @After
    public void tearDown() {
	ascIntRange = null;
	ascIntGenerator = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return new IntegerGenerator(10, 20);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Integers from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Integer> list = (List<? super Integer>)(new IntegerGenerator(0,10).to(new ArrayList<Integer>()));
            for (int i=0;i<10;i++) {
                assertEquals(new Integer(i),list.get(i));
            }
        }

        // generates a collection of Integers from 10 (inclusive) to 0 (exclusive)
        {
            List<? super Integer> list = (List<? super Integer>)(new IntegerGenerator(10,0).to(new ArrayList<Integer>()));
            for (int i=10;i>0;i--) {
                assertEquals(new Integer(i),list.get(10-i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            new IntegerGenerator(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            new IntegerGenerator(2, 2, 1); // positive step is ok when range is empty
        }
        {
            new IntegerGenerator(2, 2, -1); // negative step is ok when range is empty
        }
        {
            new IntegerGenerator(0, 1, 10); // big steps are ok
        }
        {
            new IntegerGenerator(1, 0, -10); // big steps are ok
        }
        try {
            new IntegerGenerator(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new IntegerGenerator(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new IntegerGenerator(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        IntegerGenerator range = new IntegerGenerator(new Integer(0), new Integer(5));
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
        range = new IntegerGenerator(new Integer(0), new Integer(5), new Integer(1));
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        IntegerGenerator range = new IntegerGenerator(10, 0, -2);
        assertEquals("[10, 8, 6, 4, 2]", range.toCollection().toString());
        assertEquals("[10, 8, 6, 4, 2]", range.toCollection().toString());
    }

    @Test
    public void testStep() {
        IntegerGenerator range = new IntegerGenerator(0, 10, 2);
        assertEquals("[0, 2, 4, 6, 8]", range.toCollection().toString());
        assertEquals("[0, 2, 4, 6, 8]", range.toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        IntegerGenerator range = new IntegerGenerator(0, 5);
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        IntegerGenerator range = new IntegerGenerator(5, 0);
        assertEquals("[5, 4, 3, 2, 1]", range.toCollection().toString());
        assertEquals("[5, 4, 3, 2, 1]", range.toCollection().toString());
    }

    @Test
    public void testEdgeCase() {
        IntegerGenerator range = new IntegerGenerator(Integer.MAX_VALUE - 3, Integer.MAX_VALUE);
        assertEquals("[2147483644, 2147483645, 2147483646]", range.toCollection().toString());
        assertEquals("[2147483644, 2147483645, 2147483646]", range.toCollection().toString());
    }
    
    @Test
    public void testBoundaries() {
	IntegerRange range = new IntegerRange(0, 10);
	assertEquals(new Endpoint<Comparable<?>>(0, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscending() {
	// [-5, 5], 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// [-5, 5], 3 = -5, -2, 1, 4
	List<Integer> expected = Arrays.asList(-5, -2, 1, 4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedAscending() {
	// (-5, 5], 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (-5, 5], 3 = -2, 1, 4
	List<Integer> expected = Arrays.asList(-2, 1, 4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenAscending() {
	// [-5, 5), 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (-5, 5], 3 = -5, -2, 1, 4
	List<Integer> expected = Arrays.asList(-5, -2, 1, 4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenAscending() {
	// (-5, 5), 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (-5, 5), 3 = -2, 1, 4
	List<Integer> expected = Arrays.asList(-2, 1, 4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepAscending() {
	// (-2, 2], 1 = -1, 0, 1, 2
	IntegerRange range = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (-2, 2], 1 = -1, 0, 1, 2
	List<Integer> expected = Arrays.asList(-1, 0, 1, 2);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedClosedDescending() {
	// [5, -5], -3 = 5, 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// [5, -5], -3 = 5, 2, -1, -4
	List<Integer> expected = Arrays.asList(5, 2, -1, -4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedDescending() {
	// (5, -5], -3 = 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (5, -5], -3 = 2, -1, -4
	List<Integer> expected = Arrays.asList(2, -1, -4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenDescending() {
	// [5, -5), -3 = 5, 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// [5, -5), -3 = 5, 2, -1, -4
	List<Integer> expected = Arrays.asList(5, 2, -1, -4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenDescending() {
	// (5, -5), -3 = 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);
	IntegerGenerator generator = new IntegerGenerator(range);
	// (5, -5), -3 = 2, -1, -4
	List<Integer> expected = Arrays.asList(2, -1, -4);
	Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepDescending() {
	// [2, -2), -1 = 2, 1, 0, -1
        IntegerRange range = new IntegerRange(2, BoundType.CLOSED, -2, BoundType.OPEN, -1);
        IntegerGenerator generator = new IntegerGenerator(range);
	// [2, -2), -1 = 2, 1, 0, -1
        List<Integer> expected = Arrays.asList(2, 1, 0, -1);
        Collection<Integer> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testAscending() {
	final List<Integer> list = new ArrayList<Integer>();
	ascIntGenerator.run(new UnaryProcedure<Integer>() {
	    public void run(Integer obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedAsc.containsAll(list));
    }
    
    @Test
    public void testDescending() {
	final List<Integer> list = new ArrayList<Integer>();
	descIntGenerator.run(new UnaryProcedure<Integer>() {
	    public void run(Integer obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedDesc.containsAll(list));
    }
    
    @Test
    public void testToCollection() {
	Collection<Integer> ascCol = ascIntGenerator.toCollection();
	assertEquals("Different collections", expectedAsc, ascCol);
	Collection<Integer> descCol = descIntGenerator.toCollection();
	assertEquals("Different collections", expectedDesc, descCol);
    }
    
    @Test
    public void testTransformedGenerator() {
	int expected = 45;
	int total = ascIntGenerator.to(new UnaryFunction<Generator<? extends Integer>, Integer>() {
	    public Integer evaluate(Generator<? extends Integer> obj) {
		int total = 0;
		for(Object element : obj.toCollection()) {
		    total += (Integer)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
	expected = 55;
	total = descIntGenerator.to(new UnaryFunction<Generator<? extends Integer>, Integer>() {
	    public Integer evaluate(Generator<? extends Integer> obj) {
		int total = 0;
		for(Object element : obj.toCollection()) {
		    total += (Integer)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
    }

    @Test
    public void testEquals() {
        IntegerGenerator range = new IntegerGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new IntegerGenerator(1, 5));
        assertObjectsAreEqual(range, new IntegerGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new IntegerGenerator(new Integer(1), new Integer(5)));
        assertObjectsAreEqual(range, new IntegerGenerator(new Integer(1), new Integer(5), new Integer(1)));
        
        // equals basic properties
     	assertEquals("equals must be reflexive", ascIntGenerator, ascIntGenerator);
     	assertEquals("hashCode must be reflexive", ascIntGenerator.hashCode(),
     		ascIntGenerator.hashCode());
     	assertTrue(!ascIntGenerator.equals(null)); // should be able to compare to null

     	if (ascIntGenerator.equals(descIntGenerator)) {
     	    assertEquals("equals implies hash equals", ascIntGenerator.hashCode(),
     		    descIntGenerator.hashCode());
     	    assertEquals("equals must be symmetric", descIntGenerator, ascIntGenerator);
     	} else {
     	    assertTrue("equals must be symmetric", !descIntGenerator.equals(ascIntGenerator));
     	}

     	// Using different constructors
     	assertEquals("Invalid equals using different constructor",ascIntGenerator,new IntegerGenerator(0, 10, 1));
     	assertEquals("Invalid equals using different constructor",ascIntGenerator,new IntegerGenerator(0, BoundType.CLOSED, 10, BoundType.OPEN, 1));
     	
     	assertFalse("Invalid equals using different constructor",ascIntGenerator.equals(new IntegerGenerator(1, 10, 1)));
    }
    
    @Test
    public void testToString() {
	assertEquals("IntegerGenerator<IntegerRange<[0, 10), 1>>", ascIntGenerator.toString());
	assertEquals("IntegerGenerator<IntegerRange<[10, 0), -1>>", descIntGenerator.toString());
    }

}