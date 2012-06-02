/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.functor.generator;

import java.util.Iterator;

/**
 * Adapts an {@link Iterator} to the {@link Generator} interface.
 * 
 * @since 1.0
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun
 *          2012) $
 */
public interface Range<T extends Comparable<?>> {

    T getLowerLimit();
    
    BoundType getLowerLimitBoundType();
    
    T getUpperLimit();
    
    BoundType getUpperLimitBoundType();
    
    Number getStep();

}
