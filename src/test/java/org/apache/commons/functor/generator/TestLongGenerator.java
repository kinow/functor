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
import org.apache.commons.functor.range.LongRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestLongGenerator extends BaseFunctorTest {
    // Attributes
    // ------------------------------------------------------------------------
    private LongRange ascLongRange = null;
    private LongGenerator ascLongGenerator = null;
    private LongRange descLongRange = null;
    private LongGenerator descLongGenerator = null;
    private Collection<Long> expectedAsc = null;
    private Collection<Long> expectedDesc = null;
    
    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
	ascLongRange = new LongRange(0L, 10L);
	ascLongGenerator = new LongGenerator(ascLongRange);
	descLongRange = new LongRange(10L, 0L);
	descLongGenerator = new LongGenerator(descLongRange);
	expectedAsc = Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
	expectedDesc = Arrays.asList(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L , 1L);
    }
    
    @After
    public void tearDown() {
	ascLongRange = null;
	ascLongGenerator = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return new LongGenerator(10, 20);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Longs from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Long> list = (List<? super Long>)(new LongGenerator(0,10).to(new ArrayList<Long>()));
            for (int i=0;i<10;i++) {
                assertEquals(new Long(i),list.get(i));
            }
        }

        // generates a collection of Longs from 10 (inclusive) to 0 (exclusive)
        {
            List<? super Long> list = (List<? super Long>)(new LongGenerator(10,0).to(new ArrayList<Long>()));
            for (int i=10;i>0;i--) {
                assertEquals(new Long(i),list.get(10-i));
            }
        }
    }

    @Test
    public void testStepChecking() {
        {
            new LongGenerator(2, 2, 0); // step of 0 is ok when range is empty
        }
        {
            new LongGenerator(2, 2, 1); // positive step is ok when range is empty
        }
        {
            new LongGenerator(2, 2, -1); // negative step is ok when range is empty
        }
        {
            new LongGenerator(0, 1, 10); // big steps are ok
        }
        {
            new LongGenerator(1, 0, -10); // big steps are ok
        }
        try {
            new LongGenerator(0, 1, 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new LongGenerator(0, 1, -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new LongGenerator(0, -1, 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        LongGenerator range = new LongGenerator(new Long(0), new Long(5));
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
        range = new LongGenerator(new Long(0), new Long(5), new Long(1));
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        LongGenerator range = new LongGenerator(10, 0, -2);
        assertEquals("[10, 8, 6, 4, 2]", range.toCollection().toString());
        assertEquals("[10, 8, 6, 4, 2]", range.toCollection().toString());
    }

    @Test
    public void testStep() {
        LongGenerator range = new LongGenerator(0, 10, 2);
        assertEquals("[0, 2, 4, 6, 8]", range.toCollection().toString());
        assertEquals("[0, 2, 4, 6, 8]", range.toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        LongGenerator range = new LongGenerator(0, 5);
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
        assertEquals("[0, 1, 2, 3, 4]", range.toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        LongGenerator range = new LongGenerator(5, 0);
        assertEquals("[5, 4, 3, 2, 1]", range.toCollection().toString());
        assertEquals("[5, 4, 3, 2, 1]", range.toCollection().toString());
    }

    @Test
    public void testEdgeCase() {
        LongGenerator range = new LongGenerator(Long.MAX_VALUE - 3L, Long.MAX_VALUE);
        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
        assertEquals("[9223372036854775804, 9223372036854775805, 9223372036854775806]", range.toCollection().toString());
    }
    
    @Test
    public void testBoundaries() {
	LongRange range = new LongRange(0L, 10L);
	assertEquals(new Endpoint<Comparable<?>>(0L, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10L, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscending() {
	// [-5L, 5L], 3L = -5L, -2L, 1L, 4L
	LongRange range = new LongRange(-5L, BoundType.CLOSED, 5L, BoundType.CLOSED, 3L);
	LongGenerator generator = new LongGenerator(range);
	// [-5L, 5L], 3L = -5L, -2L, 1L, 4L
	List<Long> expected = Arrays.asList(-5L, -2L, 1L, 4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedAscending() {
	// (-5L, 5L], 3L = -2L, 1L, 4L
	LongRange range = new LongRange(-5L, BoundType.OPEN, 5L, BoundType.CLOSED, 3L);
	LongGenerator generator = new LongGenerator(range);
	// (-5L, 5L], 3L = -2L, 1L, 4L
	List<Long> expected = Arrays.asList(-2L, 1L, 4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenAscending() {
	// [-5L, 5L), 3L = -5L, -2L, 1L, 4L
	LongRange range = new LongRange(-5L, BoundType.CLOSED, 5L, BoundType.OPEN, 3L);
	LongGenerator generator = new LongGenerator(range);
	// (-5L, 5L], 3L = -5L, -2L, 1L, 4L
	List<Long> expected = Arrays.asList(-5L, -2L, 1L, 4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenAscending() {
	// (-5L, 5L), 3L = -2L, 1L, 4L
	LongRange range = new LongRange(-5L, BoundType.OPEN, 5L, BoundType.OPEN, 3L);
	LongGenerator generator = new LongGenerator(range);
	// (-5L, 5L), 3L = -2L, 1L, 4L
	List<Long> expected = Arrays.asList(-2L, 1L, 4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepAscending() {
	// (-2L, 2L], 1L = -1L, 0L, 1L, 2L
	LongRange range = new LongRange(-2L, BoundType.OPEN, 2L, BoundType.CLOSED, 1L);
	LongGenerator generator = new LongGenerator(range);
	// (-2L, 2L], 1L = -1L, 0L, 1L, 2L
	List<Long> expected = Arrays.asList(-1L, 0L, 1L, 2L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedClosedDescending() {
	// [5L, -5L], -3L = 5L, 2L, -1L, -4L
	LongRange range = new LongRange(5L, BoundType.CLOSED, -5L, BoundType.CLOSED, -3L);
	LongGenerator generator = new LongGenerator(range);
	// [5L, -5L], -3L = 5L, 2L, -1L, -4L
	List<Long> expected = Arrays.asList(5L, 2L, -1L, -4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedDescending() {
	// (5L, -5L], -3L = 2L, -1L, -4L
	LongRange range = new LongRange(5L, BoundType.OPEN, -5L, BoundType.CLOSED, -3L);
	LongGenerator generator = new LongGenerator(range);
	// (5L, -5L], -3L = 2L, -1L, -4L
	List<Long> expected = Arrays.asList(2L, -1L, -4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenDescending() {
	// [5L, -5L), -3L = 5L, 2L, -1L, -4L
	LongRange range = new LongRange(5L, BoundType.CLOSED, -5L, BoundType.OPEN, -3L);
	LongGenerator generator = new LongGenerator(range);
	// [5L, -5L), -3L = 5L, 2L, -1L, -4L
	List<Long> expected = Arrays.asList(5L, 2L, -1L, -4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenDescending() {
	// (5L, -5L), -3L = 2L, -1L, -4L
	LongRange range = new LongRange(5L, BoundType.OPEN, -5L, BoundType.OPEN, -3L);
	LongGenerator generator = new LongGenerator(range);
	// (5L, -5L), -3L = 2L, -1L, -4L
	List<Long> expected = Arrays.asList(2L, -1L, -4L);
	Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepDescending() {
	// [2L, -2L), -1L = 2L, 1L, 0L, -1L
        LongRange range = new LongRange(2L, BoundType.CLOSED, -2L, BoundType.OPEN, -1L);
        LongGenerator generator = new LongGenerator(range);
	// [2L, -2L), -1L = 2L, 1L, 0L, -1L
        List<Long> expected = Arrays.asList(2L, 1L, 0L, -1L);
        Collection<Long> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testAscending() {
	final List<Long> list = new ArrayList<Long>();
	ascLongGenerator.run(new UnaryProcedure<Long>() {
	    public void run(Long obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedAsc.containsAll(list));
    }
    
    @Test
    public void testDescending() {
	final List<Long> list = new ArrayList<Long>();
	descLongGenerator.run(new UnaryProcedure<Long>() {
	    public void run(Long obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedDesc.containsAll(list));
    }
    
    @Test
    public void testToCollection() {
	Collection<Long> ascCol = ascLongGenerator.toCollection();
	assertEquals("Different collections", expectedAsc, ascCol);
	Collection<Long> descCol = descLongGenerator.toCollection();
	assertEquals("Different collections", expectedDesc, descCol);
    }
    
    @Test
    public void testTransformedGenerator() {
	long expected = 45L;
	long total = ascLongGenerator.to(new UnaryFunction<Generator<? extends Long>, Long>() {
	    public Long evaluate(Generator<? extends Long> obj) {
		long total = 0L;
		for(Object element : obj.toCollection()) {
		    total += (Long)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
	expected = 55L;
	total = descLongGenerator.to(new UnaryFunction<Generator<? extends Long>, Long>() {
	    public Long evaluate(Generator<? extends Long> obj) {
		long total = 0L;
		for(Object element : obj.toCollection()) {
		    total += (Long)element;
		}
		return total;
	    }
	});
	assertEquals(expected, total);
    }

    @Test
    public void testEquals() {
        LongGenerator range = new LongGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new LongGenerator(1, 5));
        assertObjectsAreEqual(range, new LongGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new LongGenerator(new Long(1), new Long(5)));
        assertObjectsAreEqual(range, new LongGenerator(new Long(1), new Long(5), new Long(1)));
        
        // equals basic properties
     	assertEquals("equals must be reflexive", ascLongGenerator, ascLongGenerator);
     	assertEquals("hashCode must be reflexive", ascLongGenerator.hashCode(),
     		ascLongGenerator.hashCode());
     	assertTrue(!ascLongGenerator.equals(null)); // should be able to compare to null

     	if (ascLongGenerator.equals(descLongGenerator)) {
     	    assertEquals("equals implies hash equals", ascLongGenerator.hashCode(),
     		    descLongGenerator.hashCode());
     	    assertEquals("equals must be symmetric", descLongGenerator, ascLongGenerator);
     	} else {
     	    assertTrue("equals must be symmetric", !descLongGenerator.equals(ascLongGenerator));
     	}

     	// Using different constructors
     	assertEquals("Invalid equals using different constructor",ascLongGenerator,new LongGenerator(0L, 10L, 1L));
     	assertEquals("Invalid equals using different constructor",ascLongGenerator,new LongGenerator(0L, BoundType.CLOSED, 10L, BoundType.OPEN, 1L));
     	
     	assertFalse("Invalid equals using different constructor",ascLongGenerator.equals(new LongGenerator(1L, 10L, 1L)));
    }
    
    @Test
    public void testToString() {
	assertEquals("LongGenerator<LongRange<[0, 10), 1>>", ascLongGenerator.toString());
	assertEquals("LongGenerator<LongRange<[10, 0), -1>>", descLongGenerator.toString());
    }

}