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
 * Tests for integer range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestIntegerRange {

    // A base range with all integers between -6 and 6
    private final List<Integer> fullRange = Collections.unmodifiableList(Arrays.asList(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6));
    
    @Test
    public void testEmptyRanges() {
	IntegerRange empty1 = new IntegerRange(-2, BoundType.OPEN, -1, BoundType.OPEN, 2);
	assertTrue("The range was expected to be empty.", empty1.isEmpty());
	IntegerRange empty2 = new IntegerRange(2, BoundType.OPEN, 0, BoundType.OPEN, -2);
	assertTrue("The range was expected to be empty.", empty2.isEmpty());
	IntegerRange empty3 = new IntegerRange(0, BoundType.OPEN, 1, BoundType.CLOSED, 2);
	assertTrue("The range was expected to be empty.", empty3.isEmpty());
	IntegerRange empty4 = new IntegerRange(-3, BoundType.OPEN, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty4.isEmpty());
	IntegerRange empty5 = new IntegerRange(-3, BoundType.CLOSED, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty5.isEmpty());
	IntegerRange empty6 = new IntegerRange(1, BoundType.OPEN, 0, BoundType.CLOSED, -2);
	assertTrue("The range was expected to be empty.", empty6.isEmpty());
	IntegerRange notEmpty1 = new IntegerRange(-3, BoundType.CLOSED, -3, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
	IntegerRange notEmpty2 = new IntegerRange(-3, BoundType.OPEN, -2, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
	IntegerRange notEmpty3 = new IntegerRange(2, BoundType.OPEN, 1, BoundType.CLOSED, -1);
	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
	IntegerRange notEmpty4 = new IntegerRange(2, BoundType.CLOSED, 1, BoundType.OPEN, -1);
	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
	IntegerRange notEmpty5 = new IntegerRange(1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	IntegerRange range = new IntegerRange(0, 10);
	assertEquals(new Endpoint<Comparable<?>>(0, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscendingContains() {
	// [-5, 5], 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);
	// [-5, 5], 3 = -5, -2, 1, 4
	List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedAscendingContains() {
	// (-5, 5], 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);
	// (-5, 5], 3 = -2, 1, 4
	List<Integer> arr = Arrays.asList(-2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenAscendingContains() {
	// [-5, 5), 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);
	// (-5, 5], 3 = -5, -2, 1, 4
	List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenAscendingContains() {
	// (-5, 5), 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);
	// (-5, 5), 3 = -2, 1, 4
	List<Integer> arr = Arrays.asList(-2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepAscending() {
	// (-2, 2], 1 = -1, 0, 1, 2
	IntegerRange ascendingRange = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	// (-2, 2], 1 = -1, 0, 1, 2
        List<Integer> arr = Arrays.asList(-1, 0, 1, 2);
        for(Integer element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
	}
    }
    
    @Test
    public void testClosedClosedDescendingContains() {
	// [5, -5], -3 = 5, 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);
	// [5, -5], -3 = 5, 2, -1, -4
	List<Integer> arr = Arrays.asList(5, 2, -1, -4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedDescendingContains() {
	// (5, -5], -3 = 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);
	// (5, -5], -3 = 2, -1, -4
	List<Integer> arr = Arrays.asList(2, -1, -4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenDescendingContains() {
	// [5, -5), -3 = 5, 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);
	// [5, -5), -3 = 5, 2, -1, -4
	List<Integer> arr = Arrays.asList(5, 2, -1, -4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenDescendingContains() {
	// (5, -5), -3 = 2, -1, -4
	IntegerRange range = new IntegerRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);
	// (5, -5), -3 = 2, -1, -4
	List<Integer> arr = Arrays.asList(2, -1, -4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepDescending() {
	// [2, -2), -1 = 2, 1, 0, -1
        IntegerRange descendingRange = new IntegerRange(2, BoundType.CLOSED, -2, BoundType.OPEN, -1);
	// [2, -2), -1 = 2, 1, 0, -1
        List<Integer> arr = Arrays.asList(2, 1, 0, -1);
        for(Integer element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
        }
        List<Integer> elementsNotPresent = new ArrayList<Integer>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Integer element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
	}
    }
    
    @Test
    public void testContainsNullOrEmpty() {
	IntegerRange range = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertFalse(range.contains(null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
	// (-2, 2], 1 = -1, 0, 1, 2
	IntegerRange range = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	List<Integer> list = Arrays.asList(-1, 0, 1, 2);
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Integer> listWithExtraElements = Arrays.asList(2, -1, 0, 1, 2, 3);
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
	assertFalse(range.containsAll(null));
	assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }
    
    @Test
    public void testEquals() throws Exception {
	// equals basic properties
	IntegerRange range = new IntegerRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertEquals("equals must be reflexive",range,range);
        assertEquals("hashCode must be reflexive",range.hashCode(),range.hashCode());
        assertTrue(! range.equals(null) ); // should be able to compare to null

        Object range2 = new IntegerRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals",range.hashCode(),range2.hashCode());
            assertEquals("equals must be symmetric",range2,range);
        } else {
            assertTrue("equals must be symmetric",! range2.equals(range));
        }
        
        // Changing attributes
        Object range3 = new IntegerRange(-1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range3));
        
        Object range4 = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range4));
        
        Object range5 = new IntegerRange(-2, BoundType.CLOSED, 1, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range5));
        
        Object range6 = new IntegerRange(-2, BoundType.CLOSED, 2, BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range6));
        
        Object range7 = new IntegerRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes", range.equals(range7));
        
        // Using different constructors
        IntegerRange range8 = new IntegerRange(Long.valueOf(-2), Long.valueOf(2), Long.valueOf(1));
        assertEquals("Invalid equals using different constructor",range,range8);
        
        IntegerRange range9 = new IntegerRange(Long.valueOf(-2), Long.valueOf(2));
        assertEquals("Invalid equals using different constructor",range,range9);
        
        Endpoint<Integer> leftEndpoint = new Endpoint<Integer>(-2, BoundType.CLOSED);
        Endpoint<Integer> rightEndpoint = new Endpoint<Integer>(2, BoundType.OPEN);
        IntegerRange range10 = new IntegerRange(leftEndpoint, rightEndpoint, 1);
        assertEquals("Invalid equals using different constructor",range,range10);
    }
    
    @Test
    public void testToString() {
	IntegerRange range = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertEquals("Wrong string value", "IntegerRange<(-2, 2], 1>", range.toString());
    }
    
    @Test
    public void testConstructorUsingSameEndpoint() {
	Endpoint<Integer> uniqueEndpoint = new Endpoint<Integer>(10, BoundType.CLOSED);
        try {
            new IntegerRange(uniqueEndpoint, uniqueEndpoint, 1);
	} catch(IllegalArgumentException e) {
	    fail("Not expected to get here");
	}
    }
    
    @Test
    public void testInvalidRange() {
	try {
	    new IntegerRange(10, BoundType.OPEN, -5, BoundType.CLOSED, 10);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
	Endpoint<Integer> leftEndpoint = new Endpoint<Integer>(10, BoundType.CLOSED);
        Endpoint<Integer> rightEndpoint = new Endpoint<Integer>(-5, BoundType.OPEN);
        try {
            new IntegerRange(leftEndpoint, rightEndpoint, 1);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
    }
    
    @Test
    public void testDefaultStep() {
	assertEquals("Invalid default step", Integer.valueOf(-1), IntegerRange.DEFAULT_STEP.evaluate(10, 1));
	assertEquals("Invalid default step", Integer.valueOf(1), IntegerRange.DEFAULT_STEP.evaluate(1, 10));
    }
    
}
