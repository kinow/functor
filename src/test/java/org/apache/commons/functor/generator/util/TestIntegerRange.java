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
package org.apache.commons.functor.generator.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.generator.IntegerGenerator;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestIntegerRange extends BaseFunctorTest {

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
    public void testEquals() {
        IntegerGenerator range = new IntegerGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new IntegerGenerator(1, 5));
        assertObjectsAreEqual(range, new IntegerGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new IntegerGenerator(new Long(1), new Long(5)));
        assertObjectsAreEqual(range, new IntegerGenerator(new Long(1), new Long(5), new Long(1)));
    }

}