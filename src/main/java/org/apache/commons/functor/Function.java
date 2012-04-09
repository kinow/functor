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
package org.apache.commons.functor;

/**
 * A functor that takes no arguments and returns a value.
 * <p>
 * Implementors are encouraged but not required to make their functors
 * {@link java.io.Serializable Serializable}.
 * </p>
 *
 * @param <T> the returned value type.
 * @since 1.0
 * @version $Revision: 1156737 $ $Date: 2011-08-11 15:59:53 -0300 (Thu, 11 Aug 2011) $
 * @author Rodney Waldhoff
 */
public interface Function<T, P> extends Functor {
    /**
     * Evaluate this function.
     * @return the T result of this evaluation
     */
    T evaluate(P... args);
}
