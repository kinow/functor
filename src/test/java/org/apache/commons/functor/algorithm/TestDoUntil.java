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
package org.apache.commons.functor.algorithm;

import static org.junit.Assert.assertEquals;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.core.Offset;
import org.apache.commons.functor.core.algorithm.DoUntil;
import org.junit.Test;

/**
 * Tests {@link DoUntil} algorithm.
 */
public class TestDoUntil extends BaseFunctorTest {

	/* (non-Javadoc)
	 * @see org.apache.commons.functor.BaseFunctorTest#makeFunctor()
	 */
	@Override
	protected Object makeFunctor() throws Exception {
		Counter counter = new Counter();
		return new DoUntil(counter, new Offset(10));
	}
	
	@Test
	public void testDoUntil() {
		for(int i=0;i<3;++i){
			Counter counter = new Counter();
			new DoUntil(counter, new Offset(i)).run();
			assertEquals(i+1,counter.count);
		}
	}

}

class Counter implements Procedure {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1378762129350523806L;
	public void run() {
        count++;
    }
    public int count = 0;
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) {
    		return false;
    	}
    	if(obj instanceof Counter) {
    		Counter other = (Counter)obj;
    		return other.count == this.count;
    	}
    	return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return this.count;
    }
}
