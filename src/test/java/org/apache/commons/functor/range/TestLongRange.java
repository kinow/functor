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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.functor.core.collection.FilteredIterator;
import org.junit.Test;

/**
 * Tests for long range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestLongRange extends BaseNumericRangeTest<Long> {

    // A base range with all longs between -6 and 6
    private final List<Long> fullRange = Collections.unmodifiableList(Arrays.asList(-6L, -5L, -4L, -3L, -2L, -1L, 0L, 1L, 2L, 3L, 4L, 5L, 6L));
    
    @Test
    public void testEmptyRanges() {
	LongRange empty1 = new LongRange(-2, BoundType.OPEN, -1, BoundType.OPEN, 2);
	assertTrue("The range was expected to be empty.", empty1.isEmpty());
	LongRange empty2 = new LongRange(2, BoundType.OPEN, 0, BoundType.OPEN, -2);
	assertTrue("The range was expected to be empty.", empty2.isEmpty());
	LongRange empty3 = new LongRange(0, BoundType.OPEN, 1, BoundType.CLOSED, 2);
	assertTrue("The range was expected to be empty.", empty3.isEmpty());
	LongRange empty4 = new LongRange(-3, BoundType.OPEN, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty4.isEmpty());
	LongRange empty5 = new LongRange(-3, BoundType.CLOSED, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty5.isEmpty());
	LongRange empty6 = new LongRange(1, BoundType.OPEN, 0, BoundType.CLOSED, -2);
	assertTrue("The range was expected to be empty.", empty6.isEmpty());
	LongRange notEmpty1 = new LongRange(-3, BoundType.CLOSED, -3, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
	LongRange notEmpty2 = new LongRange(-3, BoundType.OPEN, -2, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
	LongRange notEmpty3 = new LongRange(2, BoundType.OPEN, 1, BoundType.CLOSED, -1);
	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
	LongRange notEmpty4 = new LongRange(2, BoundType.CLOSED, 1, BoundType.OPEN, -1);
	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
	LongRange notEmpty5 = new LongRange(1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	LongRange range = new LongRange(0L, 10L);
	assertEquals(new Endpoint<Comparable<?>>(0L, BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>(10L, BoundType.OPEN), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscendingContains() {
	// [-5, 5], 3 = -5, -2, 1, 4
	LongRange range = new LongRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);

	// [-5, 5], 3 = -5, -2, 1, 4
	List<Long> arr = Arrays.asList(-5L, -2L, 1L, 4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedAscendingContains() {
	// (-5, 5], 3 = -2, 1, 4
	LongRange range = new LongRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);

	// (-5, 5], 3 = -2, 1, 4
	List<Long> arr = Arrays.asList(-2L, 1L, 4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenAscendingContains() {
	// [-5, 5), 3 = -5, -2, 1, 4
	LongRange range = new LongRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);

	// (-5, 5], 3 = -5, -2, 1, 4
	List<Long> arr = Arrays.asList(-5L, -2L, 1L, 4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenAscendingContains() {
	// (-5, 5), 3 = -2, 1, 4
	LongRange range = new LongRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);

	// (-5, 5), 3 = -2, 1, 4
	List<Long> arr = Arrays.asList(-2L, 1L, 4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepAscending() {
	LongRange ascendingRange = new LongRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	// (-2, 2], 1 = -1, 0, 1, 2
        List<Long> arr = Arrays.asList(-1L, 0L, 1L, 2L);
        for(Long element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
        Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
        while(elementsNotPresent.hasNext()) {
            Long element = elementsNotPresent.next();
            assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
    }
    
    @Test
    public void testClosedClosedDescendingContains() {
	// [5, -5], -3 = 5, 2, -1, -4
	LongRange range = new LongRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);

	// [5, -5], -3 = 5, 2, -1, -4
	List<Long> arr = Arrays.asList(5L, 2L, -1L, -4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedDescendingContains() {
	// (5, -5], -3 = 2, -1, -4
	LongRange range = new LongRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);

	// (5, -5], -3 = 2, -1, -4
	List<Long> arr = Arrays.asList(2L, -1L, -4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenDescendingContains() {
	// [5, -5), -3 = 5, 2, -1, -4
	LongRange range = new LongRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);

	// [5, -5), -3 = 5, 2, -1, -4
	List<Long> arr = Arrays.asList(5L, 2L, -1L, -4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenDescendingContains() {
	// (5, -5), -3 = 2, -1, -4
	LongRange range = new LongRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);

	// (5, -5), -3 = 2, -1, -4
	List<Long> arr = Arrays.asList(2L, -1L, -4L);
	for(Long element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Long> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Long element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepDescending() {
        LongRange descendingRange = new LongRange(2, BoundType.CLOSED, -2, BoundType.OPEN, -1);
	// [2, -2), -1 = 2, 1, 0, -1
        List<Long> arr2 = Arrays.asList(2L, 1L, 0L, -1L);
        for(Long element : arr2) {
            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
        }
        Iterator<Long> elementsNotPresent2 = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr2));
        while(elementsNotPresent2.hasNext()) {
            Long element = elementsNotPresent2.next();
            assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
        }
    }
    
    @Test
    public void testContainsNullOrEmpty() {
	LongRange range = new LongRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertFalse(range.contains(null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
	// (-2, 2], 1 = -1, 0, 1, 2
	LongRange range = new LongRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	List<Long> list = Arrays.asList(-1L, 0L, 1L, 2L);
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Long> listWithExtraElements = Arrays.asList(2L, -1L, 0L, 1L, 2L, 3L);
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
	assertFalse(range.containsAll(null));
	assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }
    
    @Test
    public void testEquals() throws Exception {
	// equals basic properties
	LongRange range = new LongRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertEquals("equals must be reflexive",range,range);
        assertEquals("hashCode must be reflexive",range.hashCode(),range.hashCode());
        assertTrue(! range.equals(null) ); // should be able to compare to null

        Object range2 = new LongRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals",range.hashCode(),range2.hashCode());
            assertEquals("equals must be symmetric",range2,range);
        } else {
            assertTrue("equals must be symmetric",! range2.equals(range));
        }
        
        // Changing attributes
        Object range3 = new LongRange(-1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range3));
        
        Object range4 = new LongRange(-2, BoundType.OPEN, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range4));
        
        Object range5 = new LongRange(-2, BoundType.CLOSED, 1, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range5));
        
        Object range6 = new LongRange(-2, BoundType.CLOSED, 2, BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range6));
        
        Object range7 = new LongRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes", range.equals(range7));
        
        // Using different constructors
        LongRange range8 = new LongRange(Long.valueOf(-2), Long.valueOf(2), Long.valueOf(1));
        assertEquals("Invalid equals using different constructor",range,range8);
        
        LongRange range9 = new LongRange(Long.valueOf(-2), Long.valueOf(2));
        assertEquals("Invalid equals using different constructor",range,range9);
        
        Endpoint<Long> leftEndpoint = new Endpoint<Long>(-2L, BoundType.CLOSED);
        Endpoint<Long> rightEndpoint = new Endpoint<Long>(2L, BoundType.OPEN);
        LongRange range10 = new LongRange(leftEndpoint, rightEndpoint, 1L);
        assertEquals("Invalid equals using different constructor",range,range10);
    }
    
    @Test
    public void testToString() {
	LongRange range = new LongRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertEquals("Wrong string value", "LongRange<(-2, 2], 1>", range.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRange() {
	new LongRange(10, BoundType.OPEN, -5, BoundType.CLOSED, 10);
    }
    
}
