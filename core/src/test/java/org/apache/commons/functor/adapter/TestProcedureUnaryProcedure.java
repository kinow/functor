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
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.composite.Sequence;
import org.junit.Test;

/**
 * @version $Revision: 1365377 $ $Date: 2012-07-24 21:59:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestProcedureUnaryProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new ProcedureUnaryProcedure<Object>(NoOp.INSTANCE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testEvaluate() throws Exception {
        UnaryProcedure<Object> p = new ProcedureUnaryProcedure<Object>(new FunctionProcedure(Constant.of(null)));
        p.run(Boolean.TRUE);
    }

    @Test
    public void testEquals() throws Exception {
        UnaryProcedure<Object> p = new ProcedureUnaryProcedure<Object>(NoOp.INSTANCE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new ProcedureUnaryProcedure<Object>(NoOp.INSTANCE));
        assertObjectsAreNotEqual(p,NoOp.INSTANCE);
        assertObjectsAreNotEqual(p,new ProcedureUnaryProcedure<Object>(new Sequence()));
        assertTrue(!p.equals(null));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(ProcedureUnaryProcedure.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(ProcedureUnaryProcedure.adapt(NoOp.INSTANCE));
    }
}
