/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package disrProcessing;

public final class LongArray_Object
{
    public static final int ARRAY_SIZE = 13;
    public static final int ELEMENT_SIZE = 130;
    //public long[] ll = new long[3];
    //public byte[] ll = new byte[3];
    //public String ll = new String("qqq");
    //public StringBuilder ll = new StringBuilder("1234567890123");
    public StringBuilder ll = new StringBuilder(ARRAY_SIZE);
    //public byte[] ll = new byte[ELEMENT_SIZE];


    public LongArray_Object(){
        for(int i = 0; i < ARRAY_SIZE; i++){
            ll.append( 'c');
        }
    }

    public void publish(String str){
        //ll.replace(0,str.length(),str)  ;
        //ll.setCharAt(2,'w');

        ll.setLength(0);
        ll.append(str);
    };

    public void transform(int i, char c ){
        ll.setCharAt(i,c);

    };

    public void transform( ){
        ll.setCharAt(2,'w');

    };


}
