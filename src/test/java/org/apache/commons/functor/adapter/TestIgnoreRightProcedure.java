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

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.BinaryProcedure;
import org.apache.commons.functor.core.Identity;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestIgnoreRightProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IgnoreRightProcedure<Object, Object>(NoOp.INSTANCE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        BinaryProcedure<Object, Object> p = new IgnoreRightProcedure<Object, Object>(
                new UnaryFunctionUnaryProcedure<Object>(Identity.INSTANCE));
        p.run(Boolean.TRUE,null);
    }

    @Test
    public void testEquals() throws Exception {
        BinaryProcedure<Object, Object> p = new IgnoreRightProcedure<Object, Object>(NoOp.INSTANCE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new IgnoreRightProcedure<Object, Object>(NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,NoOp.INSTANCE);
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(IgnoreRightProcedure.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(IgnoreRightProcedure.adapt(NoOp.INSTANCE));
    }
}
