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
import org.apache.commons.functor.Function;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1188380 $ $Date: 2011-10-24 19:07:48 -0200 (Mon, 24 Oct 2011) $
 * @author Rodney Waldhoff
 */
public class TestPredicateFunction extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new PredicateFunction(Constant.of(Boolean.TRUE));
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestWhenTrue() throws Exception {
        Function<Boolean> f = new PredicateFunction(Constant.TRUE);
        assertEquals(Boolean.TRUE,f.evaluate());
    }

    @Test
    public void testTestWhenFalse() throws Exception {
        Function<Boolean> f = new PredicateFunction(Constant.FALSE);
        assertEquals(Boolean.FALSE,f.evaluate());
    }

    @Test
    public void testEquals() throws Exception {
        Function<Boolean> f = new PredicateFunction(Constant.TRUE);
        assertEquals(f,f);
        assertObjectsAreEqual(f,new PredicateFunction(Constant.TRUE));
        assertObjectsAreNotEqual(f,Constant.of("x"));
        assertObjectsAreNotEqual(f,new PredicateFunction(Constant.FALSE));
    }

    @Test
    public void testAdaptNull() throws Exception {
        assertNull(FunctionPredicate.adapt(null));
    }

    @Test
    public void testAdapt() throws Exception {
        assertNotNull(PredicateFunction.adapt(Constant.TRUE));
    }
}
