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
import org.apache.commons.functor.range.CharacterRange;
import org.apache.commons.functor.range.Endpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision: $ $Date: $
 */
public class TestCharacterGenerator extends BaseFunctorTest {
    // Attributes
    // ------------------------------------------------------------------------
    private CharacterRange ascCharRange = null;
    private CharacterGenerator ascCharGenerator = null;
    private CharacterRange descCharRange = null;
    private CharacterGenerator descCharGenerator = null;
    private Collection<Character> expectedAsc = null;
    private Collection<Character> expectedDesc = null;

    // Test set up
    // ------------------------------------------------------------------------
    @Before
    public void setUp() {
	ascCharRange = new CharacterRange('b', 'k');
	ascCharGenerator = new CharacterGenerator(ascCharRange);
	descCharRange = new CharacterRange('k', 'b');
	descCharGenerator = new CharacterGenerator(descCharRange);
	expectedAsc = Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k');
	expectedDesc = Arrays.asList('k', 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b');
    }
    
    @After
    public void tearDown() {
	ascCharRange = null;
	ascCharGenerator = null;
    }
    
    @Override
    protected Object makeFunctor() throws Exception {
        return new CharacterGenerator('b', 'k');
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testStepChecking() {
        {
            new CharacterGenerator('b', 'b', 0); // step of 0 is ok when range is empty
        }
        {
            new CharacterGenerator('b', 'b', 1); // positive step is ok when range is empty
        }
        {
            new CharacterGenerator('b', 'b', -1); // negative step is ok when range is empty
        }
        {
            new CharacterGenerator('a', 'b', 10); // big steps are ok
        }
        {
            new CharacterGenerator('b', 'a', -10); // big steps are ok
        }
        try {
            new CharacterGenerator('a', 'b', 0);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new CharacterGenerator('a', 'b', -1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
        try {
            new CharacterGenerator('b', 'a', 1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testObjectConstructor() {
        CharacterGenerator range = new CharacterGenerator(new Character('a'), new Character('e'));
        assertEquals("[a, b, c, d, e]", range.toCollection().toString());
        range = new CharacterGenerator(new Character('a'), new Character('e'), new Integer(1));
        assertEquals("[a, b, c, d, e]", range.toCollection().toString());
    }


    @Test
    public void testReverseStep() {
        CharacterGenerator range = new CharacterGenerator('k', 'a', -2);
        assertEquals("[k, i, g, e, c, a]", range.toCollection().toString());
        assertEquals("[k, i, g, e, c, a]", range.toCollection().toString());
    }

    @Test
    public void testStep() {
        CharacterGenerator range = new CharacterGenerator('a', 'k', 2);
        assertEquals("[a, c, e, g, i, k]", range.toCollection().toString());
        assertEquals("[a, c, e, g, i, k]", range.toCollection().toString());
    }

    @Test
    public void testForwardRange() {
        CharacterGenerator range = new CharacterGenerator('a', 'e');
        assertEquals("[a, b, c, d, e]", range.toCollection().toString());
        assertEquals("[a, b, c, d, e]", range.toCollection().toString());
    }

    @Test
    public void testReverseRange() {
        CharacterGenerator range = new CharacterGenerator('e', 'a');
        assertEquals("[e, d, c, b, a]", range.toCollection().toString());
        assertEquals("[e, d, c, b, a]", range.toCollection().toString());
    }

//    @Test
//    public void testEdgeCase() {
//        CharacterGenerator range = new CharacterGenerator(Character.MAX_VALUE-3, Character.MAX_VALUE);
//        assertEquals("[2147483644, 2147483645, 2147483646]", range.toCollection().toString());
//        assertEquals("[2147483644, 2147483645, 2147483646]", range.toCollection().toString());
//    }
    
    @Test
    public void testBoundaries() {
	CharacterRange range = new CharacterRange('b', 'l');
	assertEquals(new Endpoint<Comparable<?>>('b', BoundType.CLOSED), range.getLeftEndpoint());
	assertEquals(new Endpoint<Comparable<?>>('l', BoundType.CLOSED), range.getRightEndpoint());
    }
    
    @Test
    public void testClosedClosedAscending() {
	// [b, l], 3 = b, e, h, k
	CharacterRange range = new CharacterRange(new Character('b'), BoundType.CLOSED, new Character('l'), BoundType.CLOSED, 3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// [b, l], 3 = b, e, h, k
	List<Character> expected = Arrays.asList('b', 'e', 'h', 'k');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedAscending() {
	// (b, l], 3 = e, h, k
	CharacterRange range = new CharacterRange(new Character('b'), BoundType.OPEN, new Character('l'), BoundType.CLOSED, 3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// (b, l], 3 = e, h, k
	List<Character> expected = Arrays.asList('e', 'h', 'k');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenAscending() {
	// [b, l), 3 = b, e, h, k
	CharacterRange range = new CharacterRange(new Character('b'), BoundType.CLOSED, new Character('l'), BoundType.OPEN, 3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// [b, l), 3 = b, e, h, k
	List<Character> expected = Arrays.asList('b', 'e', 'h', 'k');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenAscending() {
	// (b, l), 3 = e, h, k
	CharacterRange range = new CharacterRange(new Character('b'), BoundType.OPEN, new Character('l'), BoundType.OPEN, 3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// (b, l), 3 = e, h, k
	List<Character> expected = Arrays.asList('e', 'h', 'k');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepAscending() {
	// (d, h], 1 = e, f, g, h
	CharacterRange range = new CharacterRange(new Character('d'), BoundType.OPEN, new Character('h'), BoundType.CLOSED, 1);
	CharacterGenerator generator = new CharacterGenerator(range);
	// (d, h], 1 = e, f, g, h
	List<Character> expected = Arrays.asList('e', 'f', 'g', 'h');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedClosedDescending() {
	// [l, b], -3 = l, i, f, c
	CharacterRange range = new CharacterRange('l', BoundType.CLOSED, 'b', BoundType.CLOSED, -3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// [l, b], -3 = l, i, f, c
	List<Character> expected = Arrays.asList('l', 'i', 'f', 'c');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenClosedDescending() {
	// (l, b], -3 = i, f, c
	CharacterRange range = new CharacterRange('l', BoundType.OPEN, 'b', BoundType.CLOSED, -3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// (l, b], -3 = i, f, c
	List<Character> expected = Arrays.asList('i', 'f', 'c');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testClosedOpenDescending() {
	// [l, b), -3 = l, i, f, c
	CharacterRange range = new CharacterRange('l', BoundType.CLOSED, 'b', BoundType.OPEN, -3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// [l, b), -3 = l, i, f, c
	List<Character> expected = Arrays.asList('l', 'i', 'f', 'c');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testOpenOpenDescending() {
	// (l, b), -3 = i, f, c
	CharacterRange range = new CharacterRange('l', BoundType.OPEN, 'b', BoundType.OPEN, -3);
	CharacterGenerator generator = new CharacterGenerator(range);
	// (l, b), -3 = i, f, c
	List<Character> expected = Arrays.asList('i', 'f', 'c');
	Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testSingleStepDescending() {
	// [h, d), -1 = h, g, f
        CharacterRange range = new CharacterRange('h', BoundType.CLOSED, 'e', BoundType.OPEN, -1);
        CharacterGenerator generator = new CharacterGenerator(range);
        // [h, d), -1 = h, g, f
        List<Character> expected = Arrays.asList('h', 'g', 'f');
        Collection<Character> elements = generator.toCollection();
	assertEquals(expected, elements);
    }
    
    @Test
    public void testAscending() {
	final List<Character> list = new ArrayList<Character>();
	ascCharGenerator.run(new UnaryProcedure<Character>() {
	    public void run(Character obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedAsc.containsAll(list));
    }
    
    @Test
    public void testDescending() {
	final List<Character> list = new ArrayList<Character>();
	descCharGenerator.run(new UnaryProcedure<Character>() {
	    public void run(Character obj) {
		list.add(obj);
	    }
	});
	assertTrue(expectedDesc.containsAll(list));
    }
    
    @Test
    public void testToCollection() {
	Collection<Character> ascCol = ascCharGenerator.toCollection();
	assertEquals("Different collections", expectedAsc, ascCol);
	Collection<Character> descCol = descCharGenerator.toCollection();
	assertEquals("Different collections", expectedDesc, descCol);
    }
    
    @Test
    public void testTransformedGenerator() {
	int expected = 10;
	List<Character> list = ascCharGenerator.to(new UnaryFunction<Generator<? extends Character>, List<Character>>() {
	    public List<Character> evaluate(Generator<? extends Character> obj) {
		List<Character> chars = new ArrayList<Character>();
		for(Object element : obj.toCollection()) {
		    chars.add((Character) element);
		}
		return chars;
	    }
	});
	assertEquals(expected, list.size());
	expected = 10;
	list = descCharGenerator.to(new UnaryFunction<Generator<? extends Character>, List<Character>>() {
	    public List<Character> evaluate(Generator<? extends Character> obj) {
		List<Character> chars = new ArrayList<Character>();
		for(Object element : obj.toCollection()) {
		    chars.add((Character) element);
		}
		return chars;
	    }
	});
	assertEquals(expected, list.size());
    }

    @Test
    public void testEquals() {
        CharacterGenerator range = new CharacterGenerator('b', 'f');
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new CharacterGenerator('b', 'f'));
        assertObjectsAreEqual(range, new CharacterGenerator('b', 'f', 1));
        assertObjectsAreEqual(range, new CharacterGenerator(new Character('b'), new Character('f')));
        assertObjectsAreEqual(range, new CharacterGenerator(new Character('b'), new Character('f'), new Integer(1)));
        
        // equals basic properties
     	assertEquals("equals must be reflexive", ascCharGenerator, ascCharGenerator);
     	assertEquals("hashCode must be reflexive", ascCharGenerator.hashCode(),
     		ascCharGenerator.hashCode());
     	assertTrue(!ascCharGenerator.equals(null)); // should be able to compare to null

     	if (ascCharGenerator.equals(descCharGenerator)) {
     	    assertEquals("equals implies hash equals", ascCharGenerator.hashCode(),
     		    descCharGenerator.hashCode());
     	    assertEquals("equals must be symmetric", descCharGenerator, ascCharGenerator);
     	} else {
     	    assertTrue("equals must be symmetric", !descCharGenerator.equals(ascCharGenerator));
     	}

     	// Using different constructors
     	assertEquals("Invalid equals using different constructor",ascCharGenerator,new CharacterGenerator('b', 'k', 1));
     	assertEquals("Invalid equals using different constructor",ascCharGenerator,new CharacterGenerator('b', BoundType.CLOSED, 'k', BoundType.CLOSED, 1));
     	
     	assertFalse("Invalid equals using different constructor",ascCharGenerator.equals(new CharacterGenerator('a', 'k', 1)));
    }
    
    @Test
    public void testToString() {
	assertEquals("CharacterGenerator<CharacterRange<[b, k], 1>>", ascCharGenerator.toString());
	assertEquals("CharacterGenerator<CharacterRange<[k, b], -1>>", descCharGenerator.toString());
    }

}