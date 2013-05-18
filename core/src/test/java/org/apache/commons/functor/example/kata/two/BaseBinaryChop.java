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
package org.apache.commons.functor.example.kata.two;

import java.util.Arrays;
import java.util.List;

/**
 * See http://pragprog.com/pragdave/Practices/Kata/KataTwo.rdoc,v
 * for more information on this Kata.
 *
 * @version $Revision: 1363514 $ $Date: 2012-07-19 17:13:49 -0300 (Thu, 19 Jul 2012) $
 */
public abstract class BaseBinaryChop implements BinaryChop {
    public int find(int seeking, int[] in) {
        Integer[] In = new Integer[in.length];
        for (int i=0;i<in.length;i++) {
            In[i] = new Integer(in[i]);
        }
        return find(new Integer(seeking), In);
    }

    public int find(Integer seeking, Integer[] in) {
        return find(seeking, Arrays.asList(in));
    }

    protected static int compare(List<Integer> list, int index, Integer obj) {
        return ((Comparable<Integer>) list.get(index)).compareTo(obj);
    }

    protected static boolean greaterThan(List<Integer> list, int index, Integer obj) {
        return compare(list,index,obj) > 0;
    }

    protected static boolean equals(List<Integer> list, int index, Integer obj) {
        return compare(list,index,obj) == 0;
    }

    protected static final Integer NEGATIVE_ONE = new Integer(-1);

}