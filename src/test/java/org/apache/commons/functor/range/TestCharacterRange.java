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
 * Tests for character range.
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestCharacterRange {

    // A base range with all integers between a and h
    private final List<Character> fullRange = Collections.unmodifiableList(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'));
    
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
	CharacterRange empty5 = new CharacterRange('b', BoundType.CLOSED, 'b', BoundType.OPEN, 1);
	assertTrue("The range was expected to be empty.", empty5.isEmpty());
	CharacterRange empty6 = new CharacterRange('d', BoundType.OPEN, 'c', BoundType.CLOSED, -2);
	assertTrue("The range was expected to be empty.", empty6.isEmpty());
	CharacterRange notEmpty1 = new CharacterRange('a', BoundType.CLOSED, 'a', BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty1.isEmpty());
	CharacterRange notEmpty2 = new CharacterRange('a', BoundType.OPEN, 'b', BoundType.CLOSED, 1);
	assertFalse("The range was not expected to be empty.", notEmpty2.isEmpty());
	CharacterRange notEmpty3 = new CharacterRange('b', BoundType.OPEN, 'a', BoundType.CLOSED, -1);
	assertFalse("The range was not expected to be empty.", notEmpty3.isEmpty());
	CharacterRange notEmpty4 = new CharacterRange('b', BoundType.CLOSED, 'a', BoundType.OPEN, -1);
	assertFalse("The range was not expected to be empty.", notEmpty4.isEmpty());
	CharacterRange notEmpty5 = new CharacterRange('a', BoundType.CLOSED, 'b', BoundType.OPEN, 1);
	assertFalse("The range was not expected to be empty.", notEmpty5.isEmpty());
    }
    
    @Test
    public void testBoundaries() {
	CharacterRange range = new CharacterRange('a', 'j');
	assertEquals(new Endpoint<Comparable<?>>('a', BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>('j', BoundType.CLOSED), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscendingContains() {
	// [b, l], 3 = 'b', 'e', 'h', 'k'
	CharacterRange range = new CharacterRange('b', BoundType.CLOSED, 'l', BoundType.CLOSED, 3);
	// [b, l], 3 = 'b', 'e', 'h', 'k'
	List<Character> arr = Arrays.asList('b', 'e', 'h', 'k');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedAscendingContains() {
	// (b, l], 3 = 'e', 'h', 'k'
	CharacterRange range = new CharacterRange('b', BoundType.OPEN, 'l', BoundType.CLOSED, 3);
	// (b, l], 3 = 'e', 'h', 'k'
	List<Character> arr = Arrays.asList('e', 'h', 'k');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenAscendingContains() {
	// [b, l), 3 = 'b', 'e', 'h', 'k'
	CharacterRange range = new CharacterRange('b', BoundType.CLOSED, 'l', BoundType.OPEN, 3);
	// [b, l), 3 = 'b', 'e', 'h', 'k'
	List<Character> arr = Arrays.asList('b', 'e', 'h', 'k');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenAscendingContains() {
	// (b, l), 3 = 'e', 'h', 'k'
	CharacterRange range = new CharacterRange('b', BoundType.OPEN, 'l', BoundType.OPEN, 3);
	// (b, l), 3 = 'e', 'h', 'k'
	List<Character> arr = Arrays.asList('e', 'h', 'k');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepAscending() {
	// (b, l], 1 = 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'
	CharacterRange ascendingRange = new CharacterRange('b', BoundType.OPEN, 'l', BoundType.CLOSED, 1);
	// (b, l], 1 = 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'
        List<Character> arr = Arrays.asList('c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l');
        for(Character element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+ascendingRange+"]", ascendingRange.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+ascendingRange+"]", ascendingRange.contains(element));
	}
    }
    
    @Test
    public void testClosedClosedDescendingContains() {
	// [l, b], -3 = 'l', 'i', 'f', 'c'
	CharacterRange range = new CharacterRange('l', BoundType.CLOSED, 'b', BoundType.CLOSED, -3);
	// [l, b], -3 = 'l', 'i', 'f', 'c'
	List<Character> arr = Arrays.asList('l', 'i', 'f', 'c');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedDescendingContains() {
	// (l, b], -3 = 'i', 'f', 'c'
	CharacterRange range = new CharacterRange('l', BoundType.OPEN, 'b', BoundType.CLOSED, -3);
	// (l, b], -3 = 'i', 'f', 'c'
	List<Character> arr = Arrays.asList('i', 'f', 'c');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenDescendingContains() {
	// [l, b), -3 = 'l', 'i', 'f', 'c'
	CharacterRange range = new CharacterRange('l', BoundType.CLOSED, 'b', BoundType.OPEN, -3);
	// [l, b), -3 = 'l', 'i', 'f', 'c'
	List<Character> arr = Arrays.asList('l', 'i', 'f', 'c');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenDescendingContains() {
	// (l, b), -3 = 'i', 'f', 'c'
	CharacterRange range = new CharacterRange('l', BoundType.OPEN, 'b', BoundType.OPEN, -3);
	// (l, b), -3 = 'i', 'f', 'c'
	List<Character> arr = Arrays.asList('i', 'f', 'c');
	for(Character element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsSingleStepDescending() {
	// [l, b), -1 = 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c'
        CharacterRange descendingRange = new CharacterRange('l', BoundType.CLOSED, 'b', BoundType.OPEN, -1);
        // [l, b), -1 = 'l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c'
        List<Character> arr = Arrays.asList('l', 'k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c');
        for(Character element : arr) {
            assertTrue("Expected element ["+element+"] is missing in range ["+descendingRange+"]", descendingRange.contains(element));
        }
        List<Character> elementsNotPresent = new ArrayList<Character>(fullRange);
	elementsNotPresent.removeAll(arr);
	for(Character element : elementsNotPresent) {
	    assertFalse("Unexpected element ["+element+"] is present in range ["+descendingRange+"]", descendingRange.contains(element));
	}
    }
    
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
    
    @Test
    public void testConstructorUsingSameEndpoint() {
	Endpoint<Character> uniqueEndpoint = new Endpoint<Character>('a', BoundType.CLOSED);
        try {
            new CharacterRange(uniqueEndpoint, uniqueEndpoint, 1);
	} catch(IllegalArgumentException e) {
	    fail("Not expected to get here");
	}
    }
    
    @Test
    public void testInvalidRange() {
	try {
	    new CharacterRange('a', BoundType.OPEN, 'z', BoundType.CLOSED, -100);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
	Endpoint<Character> leftEndpoint = new Endpoint<Character>('a', BoundType.CLOSED);
        Endpoint<Character> rightEndpoint = new Endpoint<Character>('z', BoundType.OPEN);
        try {
            new CharacterRange(leftEndpoint, rightEndpoint, -100);
	    fail("Not expected to get here");
	} catch(IllegalArgumentException e) {
	    // Do nothing
	}
    }
    
    @Test
    public void testDefaultStep() {
	assertEquals("Invalid default step", Integer.valueOf(-1), CharacterRange.DEFAULT_STEP.evaluate('c', 'a'));
	assertEquals("Invalid default step", Integer.valueOf(1), CharacterRange.DEFAULT_STEP.evaluate('a', 'c'));
    }
    
}
