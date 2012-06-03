/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0f
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0f
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
 * Tests for float range.
 * 
 * @since 1.0f
 * @version $Revision: $ $Date: $
 */
public class TestFloatRange extends BaseNumericRangeTest<Float> {

    // A base range with all longs between -6 and 6
    private final List<Float> fullRange = Collections.unmodifiableList(Arrays.asList(-6.0f, -5.0f, -4.0f, -3.0f, -2.0f, -1.0f, 0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f));
    
    @Test
    public void testEmptyRanges() {
	FloatRange empty1 = new FloatRange(-2, BoundType.OPEN, -1, BoundType.OPEN, 2);
	assertTrue("The range was expected to be empty.", empty1.isEmpty());
	FloatRange empty2 = new FloatRange(2, BoundType.OPEN, 0, BoundType.OPEN, -2);
	assertTrue("The range was expected to be empty.", empty2.isEmpty());
	FloatRange empty3 = new FloatRange(0, BoundType.OPEN, 1, BoundType.CLOSED, 2);
	assertTrue("The range was expected to be empty.", empty3.isEmpty());
	FloatRange empty4 = new FloatRange(-3, BoundType.OPEN, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty4.isEmpty());
	FloatRange empty5 = new FloatRange(-3, BoundType.CLOSED, -3, BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty5.isEmpty());
	FloatRange empty6 = new FloatRange(1, BoundType.OPEN, 0, BoundType.CLOSED, -2);
	assertTrue("The range was expected to be empty.", empty6.isEmpty());
	FloatRange notEmpty1 = new FloatRange(-3, BoundType.CLOSED, -3, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
	FloatRange notEmpty2 = new FloatRange(-3, BoundType.OPEN, -2, BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
	FloatRange notEmpty3 = new FloatRange(2, BoundType.OPEN, 1, BoundType.CLOSED, -1);
	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
	FloatRange notEmpty4 = new FloatRange(2, BoundType.CLOSED, 1, BoundType.OPEN, -1);
	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
	FloatRange notEmpty5 = new FloatRange(1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	FloatRange range = new FloatRange(0.0f, 10.0f);
	assertEquals(new Endpoint<Comparable<?>>(0.0f, BoundType.CLOSED), range.getLowerLimit());
	assertEquals(new Endpoint<Comparable<?>>(10.0f, BoundType.OPEN), range.getUpperLimit());
    }
    
    @Test
    public void testClosedClosedAscendingContains() {
	// [-5, 5], 3 = -5, -2, 1, 4
	FloatRange range = new FloatRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);

	// [-5, 5], 3 = -5, -2, 1, 4
	List<Float> arr = Arrays.asList(-5.0f, -2.0f, 1.0f, 4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedAscendingContains() {
	// (-5, 5], 3 = -2, 1, 4
	FloatRange range = new FloatRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);

	// (-5, 5], 3 = -2, 1, 4
	List<Float> arr = Arrays.asList(-2.0f, 1.0f, 4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenAscendingContains() {
	// [-5, 5), 3 = -5, -2, 1, 4
	FloatRange range = new FloatRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);

	// (-5, 5], 3 = -5, -2, 1, 4
	List<Float> arr = Arrays.asList(-5.0f, -2.0f, 1.0f, 4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenAscendingContains() {
	// (-5, 5), 3 = -2, 1, 4
	FloatRange range = new FloatRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);

	// (-5, 5), 3 = -2, 1, 4
	List<Float> arr = Arrays.asList(-2.0f, 1.0f, 4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepAscending() {
	FloatRange ascendingRange = new FloatRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	// (-2, 2], 1 = -1, 0, 1, 2
        List<Float> arr = Arrays.asList(-1.0f, 0.0f, 1.0f, 2.0f);
        for(Float element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
        Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
        while(elementsNotPresent.hasNext()) {
            Float element = elementsNotPresent.next();
            assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
    }
    
    @Test
    public void testClosedClosedDescendingContains() {
	// [5, -5], -3 = 5, 2, -1, -4
	FloatRange range = new FloatRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);

	// [5, -5], -3 = 5, 2, -1, -4
	List<Float> arr = Arrays.asList(5.0f, 2.0f, -1.0f, -4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedDescendingContains() {
	// (5, -5], -3 = 2, -1, -4
	FloatRange range = new FloatRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);

	// (5, -5], -3 = 2, -1, -4
	List<Float> arr = Arrays.asList(2.0f, -1.0f, -4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenDescendingContains() {
	// [5, -5), -3 = 5, 2, -1, -4
	FloatRange range = new FloatRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);

	// [5, -5), -3 = 5, 2, -1, -4
	List<Float> arr = Arrays.asList(5.0f, 2.0f, -1.0f, -4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenDescendingContains() {
	// (5, -5), -3 = 2, -1, -4
	FloatRange range = new FloatRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);

	// (5, -5), -3 = 2, -1, -4
	List<Float> arr = Arrays.asList(2.0f, -1.0f, -4.0f);
	for(Float element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Float> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Float element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepDescending() {
        FloatRange descendingRange = new FloatRange(2, BoundType.CLOSED, -2, BoundType.OPEN, -1);
	// [2, -2), -1 = 2, 1, 0, -1
        List<Float> arr2 = Arrays.asList(2.0f, 1.0f, 0.0f, -1.0f);
        for(Float element : arr2) {
            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
        }
        Iterator<Float> elementsNotPresent2 = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr2));
        while(elementsNotPresent2.hasNext()) {
            Float element = elementsNotPresent2.next();
            assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
        }
    }
    
    @Test
    public void testContainsNullOrEmpty() {
	FloatRange range = new FloatRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertFalse(range.contains(null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
	// (-2, 2], 1 = -1, 0, 1, 2
	FloatRange range = new FloatRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	List<Float> list = Arrays.asList(-1.0f, 0.0f, 1.0f, 2.0f);
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Float> listWithExtraElements = Arrays.asList(2.0f, -1.0f, 0.0f, 1.0f, 2.0f, 3.0f);
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
	assertFalse(range.containsAll(null));
	assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }
    
    @Test
    public void testEquals() throws Exception {
	// equals basic properties
	FloatRange range = new FloatRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertEquals("equals must be reflexive",range,range);
        assertEquals("hashCode must be reflexive",range.hashCode(),range.hashCode());
        assertTrue(! range.equals(null) ); // should be able to compare to null

        Object range2 = new FloatRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals",range.hashCode(),range2.hashCode());
            assertEquals("equals must be symmetric",range2,range);
        } else {
            assertTrue("equals must be symmetric",! range2.equals(range));
        }
        
        // Changing attributes
        Object range3 = new FloatRange(-1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range3));
        
        Object range4 = new FloatRange(-2, BoundType.OPEN, 2, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range4));
        
        Object range5 = new FloatRange(-2, BoundType.CLOSED, 1, BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range5));
        
        Object range6 = new FloatRange(-2, BoundType.CLOSED, 2, BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range6));
        
        Object range7 = new FloatRange(-2, BoundType.CLOSED, 2, BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes", range.equals(range7));
        
        // Using different constructors
        FloatRange range8 = new FloatRange(Long.valueOf(-2), Long.valueOf(2), Long.valueOf(1));
        assertEquals("Invalid equals using different constructor",range,range8);
        
        FloatRange range9 = new FloatRange(Long.valueOf(-2), Long.valueOf(2));
        assertEquals("Invalid equals using different constructor",range,range9);
    }
    
    @Test
    public void testToString() {
	FloatRange range = new FloatRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	assertEquals("Wrong string value", "FloatRange<(-2.0, 2.0], 1.0>", range.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRange() {
	new FloatRange(10, BoundType.OPEN, -5, BoundType.CLOSED, 10);
    }
    
}
