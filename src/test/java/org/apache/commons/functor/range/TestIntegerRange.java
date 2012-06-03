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

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.collection.FilteredIterator;
import org.junit.Test;

/**
 * Tests for integer range
 * 
 * @since 1.0
 * @version $Revision: $ $Date: $
 */
public class TestIntegerRange {

    // A base range with all integers between -5 and 5
    private final List<Integer> fullRange = Collections.unmodifiableList(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5));
    
    /**
     * Helper predicate. Returns only the elements missing in a given collection.
     */
    private final static class IsElementNotPresent implements UnaryPredicate<Integer> {
        
	private final List<Integer> list;
	
	public IsElementNotPresent(List<Integer> list) {
	    this.list = list;
	}
	
        public boolean test(Integer obj) {
    	    return !list.contains(obj);
        }
    };
    
    @Test
    public void testEmptyRanges() {
	IntegerRange empty1 = new IntegerRange(-2, BoundType.OPEN, -1, BoundType.OPEN, 2);
	IntegerRange empty2 = new IntegerRange(2, BoundType.OPEN, 0, BoundType.OPEN, -2);
	IntegerRange empty3 = new IntegerRange(0, BoundType.OPEN, 1, BoundType.CLOSED, 2);
	IntegerRange empty4 = new IntegerRange(-3, BoundType.OPEN, -3, BoundType.OPEN, 1);
	assertEquals("The range was expected to be null.", empty1.isEmpty(), Boolean.TRUE);
	assertEquals("The range was expected to be null.", empty2.isEmpty(), Boolean.TRUE);
	assertEquals("The range was expected to be null.", empty3.isEmpty(), Boolean.TRUE);
	assertEquals("The range was expected to be null.", empty4.isEmpty(), Boolean.TRUE);
    }
    
    @Test
    public void testBoundaries() {
	IntegerRange range = new IntegerRange(0, 10);
	assertEquals(range.getLowerLimit(), new Endpoint<Comparable<?>>(0, BoundType.CLOSED));
	assertEquals(range.getUpperLimit(), new Endpoint<Comparable<?>>(10, BoundType.OPEN));
    }
    
    @Test
    public void testClosedClosedContains() {
	// [-5, 5], 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.CLOSED, 3);

	// [-5, 5], 3 = -5, -2, 1, 4
	List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Integer> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Integer element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenClosedContains() {
	// (-5, 5], 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.CLOSED, 3);

	// (-5, 5], 3 = -2, 1, 4
	List<Integer> arr = Arrays.asList(-2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Integer> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Integer element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testClosedOpenContains() {
	// [-5, 5), 3 = -5, -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.CLOSED, 5, BoundType.OPEN, 3);

	// (-5, 5], 3 = -5, -2, 1, 4
	List<Integer> arr = Arrays.asList(-5, -2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Integer> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Integer element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testOpenOpenContains() {
	// (-5, 5), 3 = -2, 1, 4
	IntegerRange range = new IntegerRange(-5, BoundType.OPEN, 5, BoundType.OPEN, 3);

	// (-5, 5), 3 = -2, 1, 4
	List<Integer> arr = Arrays.asList(-2, 1, 4);
	for(Integer element : arr) {
	    assertTrue("Expected element ["+element+"] is missing in range ["+range+"]", range.contains(element));
	}
	Iterator<Integer> elementsNotPresent = FilteredIterator.filter(fullRange.iterator(), new IsElementNotPresent(arr));
	while(elementsNotPresent.hasNext()) {
	    Integer element = elementsNotPresent.next();
	    assertFalse("Unexpected element ["+element+"] is present in range ["+range+"]", range.contains(element));
	}
    }
    
    @Test
    public void testContainsAll() {
	// (-2, 2], 1 = -1, 0, 1, 2
	IntegerRange range = new IntegerRange(-2, BoundType.OPEN, 2, BoundType.CLOSED, 1);
	List<Integer> list = Arrays.asList(-1, 0, 1, 2);
	assertTrue("Range ["+range+"] was expected to contain all elements from list ["+list+"]", range.containsAll(list));
	List<Integer> listWithExtraElements = Arrays.asList(2, -1, 0, 1, 2, 3);
	assertFalse("Range ["+range+"] has more elements than expected", range.containsAll(listWithExtraElements));
    }
    
}
