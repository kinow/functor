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
import java.util.List;

import org.junit.Test;

/**
 * Tests for character range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestCharacterRange {

    // A base range with all integers between a and g
    private final List<Character> fullRange = Collections.unmodifiableList(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
    
    @Test
    public void testEmptyRanges() {
	CharacterRange empty1 = new CharacterRange('a', BoundType.OPEN, 'b', BoundType.OPEN, 2);
	assertTrue("The range was expected to be empty.", empty1.isEmpty());
	CharacterRange empty2 = new CharacterRange('c', BoundType.OPEN, 'a', BoundType.OPEN, -2);
	assertTrue("The range was expected to be empty.", empty2.isEmpty());
	CharacterRange empty3 = new CharacterRange('a', BoundType.OPEN, 'b', BoundType.CLOSED, 2);
	assertTrue("The range was expected to be empty.", empty3.isEmpty());
	CharacterRange empty4 = new CharacterRange('a', BoundType.OPEN, 'a', BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty4.isEmpty());
//	CharacterRange empty5 = new CharacterRange(-3, BoundType.CLOSED, -3, BoundType.OPEN, 1);
//	assertTrue("The range was expected to be empty.", empty5.isEmpty());
//	CharacterRange empty6 = new CharacterRange(1, BoundType.OPEN, 0, BoundType.CLOSED, -2);
//	assertTrue("The range was expected to be empty.", empty6.isEmpty());
//	CharacterRange notEmpty1 = new CharacterRange(-3, BoundType.CLOSED, -3, BoundType.CLOSED, 1);
//	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
//	CharacterRange notEmpty2 = new CharacterRange(-3, BoundType.OPEN, 'g', BoundType.CLOSED, 1);
//	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
//	CharacterRange notEmpty3 = new CharacterRange('a', BoundType.OPEN, 1, BoundType.CLOSED, -1);
//	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
//	CharacterRange notEmpty4 = new CharacterRange('a', BoundType.CLOSED, 1, BoundType.OPEN, -1);
//	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
//	CharacterRange notEmpty5 = new CharacterRange(1, BoundType.CLOSED, 2, BoundType.OPEN, 1);
//	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	CharacterRange range = new CharacterRange('a', 'j');
	assertEquals(new Endpoint<Comparable<?>>('a', BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>('j', BoundType.CLOSED), range.getRightEndpoint());
    }
    
//    @Test
//    public void testClosedClosedAscendingContains() {
//	// [-5, 5], 3 = -5, 'g', 1, 4
//	CharacterRange range = new CharacterRange('a', BoundType.CLOSED, 'g', BoundType.CLOSED, 3);
//
//	// [-5, 5], 3 = -5, 'g', 1, 4
//	List<Character> arr = Arrays.asList('a', 'g', 1, 4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testOpenClosedAscendingContains() {
//	// (-5, 5], 3 = -2, 1, 4
//	CharacterRange range = new CharacterRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);
//
//	// (-5, 5], 3 = -2, 1, 4
//	List<Character> arr = Arrays.asList(-2, 1, 4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testClosedOpenAscendingContains() {
//	// [-5, 5), 3 = -5, 'g', 1, 4
//	CharacterRange range = new CharacterRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);
//
//	// (-5, 5], 3 = -5, 'g', 1, 4
//	List<Character> arr = Arrays.asList(-5, 'g', 1, 4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testOpenOpenAscendingContains() {
//	// (-5, 5), 3 = -2, 1, 4
//	CharacterRange range = new CharacterRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);
//
//	// (-5, 5), 3 = -2, 1, 4
//	List<Character> arr = Arrays.asList(-2, 1, 4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testContainsSingleStepAscending() {
//	CharacterRange ascendingRange = new CharacterRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
//	// (-2, 2], 1 = -1, 0, 1, 2
//        List<Character> arr = Arrays.asList(-1, 0, 1, 2);
//        for(Integer element : arr) {
//            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
//        }
//        Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//        while(elementsNotPresent.hasNext()) {
//            Integer element = elementsNotPresent.next();
//            assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
//        }
//    }
//    
//    @Test
//    public void testClosedClosedDescendingContains() {
//	// [5, -5], -3 = 5, 2, -1, -4
//	CharacterRange range = new CharacterRange(5, BoundType.CLOSED, -5, BoundType.CLOSED, -3);
//
//	// [5, -5], -3 = 5, 2, -1, -4
//	List<Character> arr = Arrays.asList(5, 2, -1, -4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testOpenClosedDescendingContains() {
//	// (5, -5], -3 = 2, -1, -4
//	CharacterRange range = new CharacterRange(5, BoundType.OPEN, -5, BoundType.CLOSED, -3);
//
//	// (5, -5], -3 = 2, -1, -4
//	List<Character> arr = Arrays.asList(2, -1, -4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testClosedOpenDescendingContains() {
//	// [5, -5), -3 = 5, 2, -1, -4
//	CharacterRange range = new CharacterRange(5, BoundType.CLOSED, -5, BoundType.OPEN, -3);
//
//	// [5, -5), -3 = 5, 2, -1, -4
//	List<Character> arr = Arrays.asList(5, 2, -1, -4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testOpenOpenDescendingContains() {
//	// (5, -5), -3 = 2, -1, -4
//	CharacterRange range = new CharacterRange(5, BoundType.OPEN, -5, BoundType.OPEN, -3);
//
//	// (5, -5), -3 = 2, -1, -4
//	List<Character> arr = Arrays.asList(2, -1, -4);
//	for(Integer element : arr) {
//	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
//	}
//	Iterator<Character> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
//	while(elementsNotPresent.hasNext()) {
//	    Integer element = elementsNotPresent.next();
//	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
//	}
//    }
//    
//    @Test
//    public void testContainsSingleStepDescending() {
//        CharacterRange descendingRange = new CharacterRange('a', BoundType.CLOSED, 'g', BoundType.OPEN, -1);
//	// [2, -2), -1 = 2, 1, 0, -1
//        List<Character> arr2 = Arrays.asList(2, 1, 0, -1);
//        for(Integer element : arr2) {
//            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
//        }
//        Iterator<Character> elementsNotPresent2 = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr2));
//        while(elementsNotPresent2.hasNext()) {
//            Integer element = elementsNotPresent2.next();
//            assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
//        }
//    }
    
    @Test
    public void testContainsNullOrEmpty() {
	CharacterRange range = new CharacterRange('a', BoundType.OPEN, 'r', BoundType.CLOSED, 1);
	assertFalse(range.contains(null));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testContainsAll() {
	// (c, g], 1 = d, e, f, g
	CharacterRange range = new CharacterRange('c', BoundType.OPEN, 'g', BoundType.CLOSED, 1);
	List<Character> list = Arrays.asList('d', 'e', 'f', 'g');
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Character> listWithExtraElements = Arrays.asList('a', 'd', 'e', 'f', 'g', 'z');
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
	assertFalse(range.containsAll(null));
	assertFalse(range.containsAll(Collections.EMPTY_LIST));
    }
    
    @Test
    public void testEquals() throws Exception {
	// equals basic properties
	CharacterRange range = new CharacterRange('a', BoundType.CLOSED, 'e', BoundType.OPEN, 1);
        assertEquals("equals must be reflexive",range,range);
        assertEquals("hashCode must be reflexive",range.hashCode(),range.hashCode());
        assertTrue(! range.equals(null) ); // should be able to compare to null

        Object range2 = new CharacterRange('a', BoundType.CLOSED, 'e', BoundType.OPEN, 1);
        if (range.equals(range2)) {
            assertEquals("equals implies hash equals",range.hashCode(),range2.hashCode());
            assertEquals("equals must be symmetric",range2,range);
        } else {
            assertTrue("equals must be symmetric",! range2.equals(range));
        }
        
        // Changing attributes
        Object range3 = new CharacterRange('c', BoundType.CLOSED, 'e', BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range3));
        
        Object range4 = new CharacterRange('a', BoundType.OPEN, 'e', BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range4));
        
        Object range5 = new CharacterRange('a', BoundType.CLOSED, 'r', BoundType.OPEN, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range5));
        
        Object range6 = new CharacterRange('a', BoundType.CLOSED, 'e', BoundType.CLOSED, 1);
        assertFalse("Invalid equals after changing attributes", range.equals(range6));
        
        Object range7 = new CharacterRange('a', BoundType.CLOSED, 'e', BoundType.OPEN, 2);
        assertFalse("Invalid equals after changing attributes", range.equals(range7));
        
        // Using different constructors
        Endpoint<Character> leftEndpoint = new Endpoint<Character>('a', BoundType.CLOSED);
        Endpoint<Character> rightEndpoint = new Endpoint<Character>('e', BoundType.OPEN);
        CharacterRange range8 = new CharacterRange(leftEndpoint, rightEndpoint, 1);
        assertEquals("Invalid equals using different constructor",range,range8);
    }
    
    @Test
    public void testToString() {
	CharacterRange range = new CharacterRange('a', BoundType.OPEN, 'b', BoundType.CLOSED, 1);
	assertEquals("Wrong string value", "CharacterRange<(a, b], 1>", range.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRange() {
	new CharacterRange('z', BoundType.OPEN, 'e', BoundType.CLOSED, 10);
    }
    
}
