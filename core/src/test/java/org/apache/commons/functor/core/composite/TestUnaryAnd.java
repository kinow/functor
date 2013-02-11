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
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
@SuppressWarnings("unchecked")
public class TestUnaryAnd extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new UnaryAnd<Object>(Constant.TRUE, Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTrue() throws Exception {
        assertTrue((new UnaryAnd<Object>()).test("xyzzy"));
        assertTrue((new UnaryAnd<Object>(Constant.TRUE)).test("xyzzy"));
        assertTrue((new UnaryAnd<Object>(Constant.TRUE,Constant.TRUE)).test("xyzzy"));
        assertTrue((new UnaryAnd<Object>(Constant.TRUE,Constant.TRUE,Constant.TRUE)).test("xyzzy"));

        UnaryAnd<Object> p = new UnaryAnd<Object>(Constant.TRUE);
        assertTrue(p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(p.test("xyzzy"));
        }

        UnaryAnd<Object> q = new UnaryAnd<Object>(Constant.TRUE);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy"));
        }

        UnaryAnd<Object> r = new UnaryAnd<Object>(p,q);
        assertTrue(r.test("xyzzy"));
    }

    @Test
    public void testFalse() throws Exception {
        assertFalse(new UnaryAnd<Object>(Constant.FALSE).test("xyzzy"));
        assertFalse(new UnaryAnd<Object>(Constant.TRUE,Constant.FALSE).test("xyzzy"));
        assertFalse(new UnaryAnd<Object>(Constant.TRUE,Constant.TRUE,Constant.FALSE).test("xyzzy"));

        UnaryAnd<Object> p = new UnaryAnd<Object>(Constant.FALSE);
        assertTrue(!p.test("xyzzy"));
        for (int i=0;i<10;i++) {
            p.and(Constant.TRUE);
            assertTrue(!p.test("xyzzy"));
        }

        UnaryAnd<Object> q = new UnaryAnd<Object>(Constant.TRUE);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(Constant.TRUE);
            assertTrue(q.test("xyzzy"));
        }

        UnaryAnd<Object> r = new UnaryAnd<Object>(p,q);
        assertTrue(!r.test("xyzzy"));
    }

    @Test
    public void testDuplicateAdd() throws Exception {
        UnaryPredicate<Object> p = Constant.TRUE;
        UnaryAnd<Object> q = new UnaryAnd<Object>(p,p);
        assertTrue(q.test("xyzzy"));
        for (int i=0;i<10;i++) {
            q.and(p);
            assertTrue(q.test("xyzzy"));
        }
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testEquals() throws Exception {
        UnaryAnd<Object> p = new UnaryAnd<Object>();
        assertEquals(p,p);
        UnaryAnd<Object> q = new UnaryAnd<Object>();
        assertObjectsAreEqual(p,q);

        for (int i=0;i<3;i++) {
            p.and(Constant.truePredicate());
            assertObjectsAreNotEqual(p,q);
            q.and(Constant.truePredicate());
            assertObjectsAreEqual(p,q);
            p.and(new UnaryAnd<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreNotEqual(p,q);
            q.and(new UnaryAnd<Object>(Constant.truePredicate(),Constant.falsePredicate()));
            assertObjectsAreEqual(p,q);
        }

        assertObjectsAreNotEqual(p,Constant.truePredicate());
        UnaryAnd<Object> r = new UnaryAnd<Object>();
        r.and(Constant.truePredicate());
        r.and(new UnaryAnd<Object>(Constant.truePredicate(),Constant.falsePredicate()));
        assertObjectsAreEqual(r, new UnaryAnd<Object>(r.getUnaryPredicateList()));
        assertObjectsAreNotEqual(p, new UnaryAnd((Iterable<UnaryPredicate<Object>>)null));
        assertObjectsAreNotEqual(p, new UnaryAnd((UnaryPredicate<Object>[])null));
        assertObjectsAreNotEqual(p, new UnaryAnd((UnaryPredicate<Object>)null));
        assertTrue(!p.equals(null));
    }

}
