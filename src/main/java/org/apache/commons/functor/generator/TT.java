/*
 * The MIT License
 *
 * Copyright (c) <2012> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.apache.commons.functor.generator;

import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.functor.generator.util.IntegerGenerator;
import org.apache.commons.functor.generator.util.IntegerRange;

/**
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 0.1
 */
public class TT {

    public static void main(String[] args) {
	IntegerRange range = new IntegerRange(9, BoundType.OPEN, 8, BoundType.CLOSED, -1);
//	System.out.println(range.getLowerLimit());
//	System.out.println(range.getUpperLimit());
//	System.out.println(range.getStep());
	final UnaryProcedure<Integer> myProc = new UnaryProcedure<Integer>() {
	    
	    public void run(Integer obj) {
		System.out.println(obj);
	    }
	};
	IntegerGenerator generator = new IntegerGenerator(range);
	generator.run(myProc);
    }
    
}
