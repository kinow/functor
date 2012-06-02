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
package org.apache.commons.functor.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestUnaryFunctionUnaryPredicate extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new UnaryFunctionUnaryPredicate<Object>(Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestWhenTrue() throws Exception {
        UnaryPredicate<Object> p = new UnaryFunctionUnaryPredicate<Object>(Constant.TRUE);
        assertTrue(p.test(null));
    }

    @Test
    public void testTestWhenFalse() throws Exception {
        UnaryPredicate<Object> p = new UnaryFunctionUnaryPredicate<Object>(Constant.FALSE);
        assertTrue(!p.test(null));
    }

    @Test
    public void testEquals() throws Exception {
        UnaryPredicate<Object> p = new UnaryFunctionUnaryPredicate<Object>(Constant.TRUE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new UnaryFunctionUnaryPredicate<Object>(Constant.TRUE));
        assertObjectsAreNotEqual(p,Constant.TRUE);
        assertObjectsAreNotEqual(p,new UnaryFunctionUnaryPredicate<Object>(Constant.FALSE));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(UnaryFunctionUnaryPredicate.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(UnaryFunctionUnaryPredicate.adapt(Constant.TRUE));
    }
}
