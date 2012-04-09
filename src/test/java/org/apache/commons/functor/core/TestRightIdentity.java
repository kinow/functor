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
package org.apache.commons.functor.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryFunction;
import org.apache.commons.functor.BinaryPredicate;
import org.junit.Test;

/**
 * @version $Revision: 1187622 $ $Date: 2011-10-21 23:22:33 -0200 (Fri, 21 Oct 2011) $
 * @author Rodney Waldhoff
 */
public class TestRightIdentity extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return RightIdentity.FUNCTION;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryFunction<Object, Object, Object> f = RightIdentity.FUNCTION;
        assertNull(f.evaluate(null,null));
        assertNull(f.evaluate("xyzzy",null));
        assertEquals("xyzzy",f.evaluate("abcdefg","xyzzy"));
        assertEquals("xyzzy",f.evaluate(null,"xyzzy"));
        assertEquals(new Integer(3),f.evaluate(null,new Integer(3)));
        Object obj = new Long(12345L);
        assertSame(obj,f.evaluate(null,obj));
        assertSame(obj,f.evaluate(obj,obj));
    }

    @Test
    public void testTest() throws Exception {
        BinaryPredicate<Object, Boolean> p = RightIdentity.PREDICATE;
        assertTrue(p.test(null,Boolean.TRUE));
        assertTrue(!p.test(null,Boolean.FALSE));
        try {
            p.test(null,null);
            fail("Expected NullPointerException");
        } catch(NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testEquals() throws Exception {
        BinaryFunction<Object, Object, Object> f = RightIdentity.FUNCTION;
        assertEquals(f,f);
        assertObjectsAreEqual(f,RightIdentity.function());
        assertObjectsAreNotEqual(f,new Identity<Object>());
        assertObjectsAreNotEqual(f,LeftIdentity.function());
        assertObjectsAreNotEqual(f,Constant.TRUE);
        assertObjectsAreNotEqual(f,Constant.of("abcde"));
    }

    @Test
    public void testConstant() throws Exception {
        assertEquals(RightIdentity.function(),RightIdentity.function());
    }
}
