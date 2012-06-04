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

package org.apache.commons.functor.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Tests for double range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestDoubleRange {

    // A base range with all longs between -6 and 6
    private final List<Double> fullRange = Collections.unmodifiableList(Arrays.asList(-6.0, -5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0));
    
    @Test
    public void testEmptyRanges() {
	DoubleRange empty1 = new DoubleRange(-2, BoundType.OPEN, -1, BoundType.OPEN, 2);
	assertTrue("The range was expected to be empty.", empty1.isEmpty());
	DoubleRange empty2 = new DoubleRange(2, BoundType.OPEN, 0, BoundType.OPEN, -2);
	assertTrue("The range was expected to be empty.", empty2.isEmpty());
	DoubleRange empty3 = new DoubleRange(0, BoundType.OPEN, 1, BoundType.CLOSED, 2);
	assertTrue("The range was expected to be empty.", empty3.isEmpty());
	DoubleRange empty4 = new DoubleRange(-3, BoundType.OPEN, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty4.isEmpty());
	DoubleRange empty5 = new DoubleRange(-3, BoundType.CLOSED, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty5.isEmpty());
	DoubleRange empty6 = new DoubleRange(1, BoundType.OPEN, 0, BoundType.CLOSED, -2);
	assertTrue("The range was expected to be empty.", empty6.isEmpty());
	DoubleRange notEmpty1 = new DoubleRange(-3, BoundType.CLOSED, -3, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
	DoubleRange notEmpty2 = new DoubleRange(-3, BoundType.OPEN, -2, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
	DoubleRange notEmpty3 = new DoubleRange(2, BoundType.OPEN, 1, BoundType.CLOSED, -1);
	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
	DoubleRange notEmpty4 = new DoubleRange(2, BoundType.CLOSED, 1, BoundType.OPEN, -1);
	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
	DoubleRange notEmpty5 = new DoubleRange(1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	DoubleRange range = new DoubleRange(0.0, 10.0);
	assertEquals(new Endpoint<Comparable<?>>(0.0, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10.0, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscendingContains() {
	// [-5, 5], 3 = -5, -2, 1, 4
	DoubleRange range = new DoubleRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);
	// [-5, 5], 3 = -5, -2, 1, 4
	List<Double> arr = Arrays.asList(-5.0, -2.0, 1.0, 4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedAscendingContains() {
	// (-5, 5], 3 = -2, 1, 4
	DoubleRange range = new DoubleRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);
	// (-5, 5], 3 = -2, 1, 4
	List<Double> arr = Arrays.asList(-2.0, 1.0, 4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenAscendingContains() {
	// [-5, 5), 3 = -5, -2, 1, 4
	DoubleRange range = new DoubleRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);
	// (-5, 5], 3 = -5, -2, 1, 4
	List<Double> arr = Arrays.asList(-5.0, -2.0, 1.0, 4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenAscendingContains() {
	// (-5, 5), 3 = -2, 1, 4
	DoubleRange range = new DoubleRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);
	// (-5, 5), 3 = -2, 1, 4
	List<Double> arr = Arrays.asList(-2.0, 1.0, 4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepAscending() {
	// (-2, 2], 1 = -1, 0, 1, 2
	DoubleRange ascendingRange = new DoubleRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	// (-2, 2], 1 = -1, 0, 1, 2
        List<Double> arr = Arrays.asList(-1.0, 0.0, 1.0, 2.0);
        for(Double element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
	}
    }
    
    @Test
    public void testClosedClosedDescendingContains() {
	// [5, -5], -3 = 5, 2, -1, -4
	DoubleRange range = new DoubleRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);
	// [5, -5], -3 = 5, 2, -1, -4
	List<Double> arr = Arrays.asList(5.0, 2.0, -1.0, -4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedDescendingContains() {
	// (5, -5], -3 = 2, -1, -4
	DoubleRange range = new DoubleRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);
	// (5, -5], -3 = 2, -1, -4
	List<Double> arr = Arrays.asList(2.0, -1.0, -4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenDescendingContains() {
	// [5, -5), -3 = 5, 2, -1, -4
	DoubleRange range = new DoubleRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);
	// [5, -5), -3 = 5, 2, -1, -4
	List<Double> arr = Arrays.asList(5.0, 2.0, -1.0, -4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenDescendingContains() {
	// (5, -5), -3 = 2, -1, -4
	DoubleRange range = new DoubleRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);
	// (5, -5), -3 = 2, -1, -4
	List<Double> arr = Arrays.asList(2.0, -1.0, -4.0);
	for(Double element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepDescending() {
	// [2, -2), -1 = 2, 1, 0, -1
        DoubleRange descendingRange = new DoubleRange(2, BoundType.CLOSED, -2, BoundType.OPEN, -1);
	// [2, -2), -1 = 2, 1, 0, -1
        List<Double> arr = Arrays.asList(2.0, 1.0, 0.0, -1.0);
        for(Double element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
        }
        List<Double> elementsNotPresent = new ArrayList<Double>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Double element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
	}
    }
    
    @Test
    public void testContainsNullOrEmpty() {
	DoubleRange range = new DoubleRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertFalse(range.contains(null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
	// (-2, 2], 1 = -1, 0, 1, 2
	DoubleRange range = new DoubleRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	List<Double> list = Arrays.asList(-1.0, 0.0, 1.0, 2.0);
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Double> listWithExtraElements = Arrays.asList(2.0, -1.0, 0.0, 1.0, 2.0, 3.0);
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
	assertFalse(range.containsAll(null));
	assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }
    
    @Test
    public void testEquals() throws Exception {
	// equals basic properties
	DoubleRange range = new DoubleRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertEquals("equals must be reflexive",range,range);
        assertEquals("hashCode must be reflexive",range.hashCode(),range.hashCode());
        assertTrue(! range.equals(null) ); // should be able to compare to null

        Object range2 = new DoubleRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals",range.hashCode(),range2.hashCode());
            assertEquals("equals must be symmetric",range2,range);
        } else {
            assertTrue("equals must be symmetric",! range2.equals(range));
        }
        
        // Changing attributes
        Object range3 = new DoubleRange(-1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range3));
        
        Object range4 = new DoubleRange(-2, BoundType.OPEN, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range4));
        
        Object range5 = new DoubleRange(-2, BoundType.CLOSED, 1, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range5));
        
        Object range6 = new DoubleRange(-2, BoundType.CLOSED, 2, BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range6));
        
        Object range7 = new DoubleRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes", range.equals(range7));
        
        // Using different constructors
        DoubleRange range8 = new DoubleRange(Long.valueOf(-2), Long.valueOf(2), Long.valueOf(1));
        assertEquals("Invalid equals using different constructor",range,range8);
        
        DoubleRange range9 = new DoubleRange(Long.valueOf(-2), Long.valueOf(2));
        assertEquals("Invalid equals using different constructor",range,range9);
        
        Endpoint<Double> leftEndpoint = new Endpoint<Double>(-2.0d, BoundType.CLOSED);
        Endpoint<Double> rightEndpoint = new Endpoint<Double>(2.0d, BoundType.OPEN);
        DoubleRange range10 = new DoubleRange(leftEndpoint, rightEndpoint, 1.0d);
        assertEquals("Invalid equals using different constructor",range,range10);
    }
    
    @Test
    public void testToString() {
	DoubleRange range = new DoubleRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertEquals("Wrong string value", "DoubleRange<(-2.0, 2.0], 1.0>", range.toString());
    }
    
    @Test
    public void testConstructorUsingSameEndpoint() {
	Endpoint<Double> uniqueEndpoint = new Endpoint<Double>(10.0d, BoundType.CLOSED);
        try {
            new DoubleRange(uniqueEndpoint, uniqueEndpoint, 1.0d);
	} catch(IllegalArgumentException e) {
	    fail("Not expected to get here");
	}
    }
    
    @Test
    public void testInvalidRange() {
	try {
	    new DoubleRange(10.0d, BoundType.OPEN, -5.0d, BoundType.CLOSED, 10.0d);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
	Endpoint<Double> leftEndpoint = new Endpoint<Double>(10.0d, BoundType.CLOSED);
        Endpoint<Double> rightEndpoint = new Endpoint<Double>(-5.0d, BoundType.OPEN);
        try {
            new DoubleRange(leftEndpoint, rightEndpoint, 1.0f);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
    }
    
    @Test
    public void testDefaultStep() {
	assertEquals("Invalid default step", Double.valueOf(-1.0d), DoubleRange.DEFAULT_STEP.evaluate(10.0d, 1.0d));
	assertEquals("Invalid default step", Double.valueOf(1.0d), DoubleRange.DEFAULT_STEP.evaluate(1.0d, 10.0d));
    }
    
}
