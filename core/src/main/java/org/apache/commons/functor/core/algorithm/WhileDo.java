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
package org.apache.commons.functor.core.algorithm;

import org.apache.commons.functor.NullaryPredicate;
import org.apache.commons.functor.NullaryProcedure;

/**
 * While-do algorithm (test before).
 *
 * @version $Revision: 1508677 $ $Date: 2013-07-30 19:48:02 -0300 (Tue, 30 Jul 2013) $
 */
public class WhileDo extends PredicatedLoop {

    /**
     * serialVersionUID declaration.
     */
    private static final long serialVersionUID = 7562985255209473649L;

    /**
     * Create a new WhileDo.
     * @param test whether to keep going
     * @param body to execute
     */
    public WhileDo(NullaryPredicate test, NullaryProcedure body) {
        super(body, test);
    }

    /**
     * {@inheritDoc}
     */
    public final void run() {
        while (getTest().test()) {
            getBody().run();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "WhileDo<" + getTest() + "," + getBody() + ">";
    }
}
