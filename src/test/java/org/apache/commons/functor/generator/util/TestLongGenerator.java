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
import org.apache.commons.functor.generator.LongGenerator;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestLongGenerator extends BaseFunctorTest {

    @Override
    protected Object makeFunctor() throws Exception {
        return new LongGenerator(10, 20);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testGenerateListExample() {
        // generates a collection of Integers from 0 (inclusive) to 10 (exclusive)
        {
            List<? super Long> list = (List<? super Long>)(new LongGenerator(0,10).to(new ArrayList<Long>()));
            for (int i=0;i<10;i++) {
                assertEquals(new Long(i),list.get(i));
            }
        }

        // generates a collection of Integers from 10 (inclusive) to 0 (exclusive)
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
        range = new LongGenerator(new Integer(0), new Long(5), new Long(1));
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
    public void testEquals() {
        LongGenerator range = new LongGenerator(1, 5);
        assertObjectsAreEqual(range, range);
        assertObjectsAreEqual(range, new LongGenerator(1, 5));
        assertObjectsAreEqual(range, new LongGenerator(1, 5, 1));
        assertObjectsAreEqual(range, new LongGenerator(new Integer(1), new Long(5)));
        assertObjectsAreEqual(range, new LongGenerator(new Long(1), new Short((short)5), new Long(1)));
    }

}