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
package disrProcessing.userlogic;

import com.lmax.disruptor.RingBuffer;
import disrProcessing.BasePublisher;
import disrProcessing.LongArray_Object;
import interfaces.DataReceiver;

import java.util.concurrent.CyclicBarrier;

public final class LongArrayPublisher_Object extends BasePublisher implements Runnable, DataReceiver
{


    @Override
    public void publishData(byte[] data) {
        String pubString = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        try
        {
            cyclicBarrier.await();

            for (long i = 0; i < iterations; i++)
            {
                long sequence = ringBuffer.next();
                LongArray_Object event = ringBuffer.get(sequence);
                for (int j = 0; j < arraySize; j++)
                {
                    //event.ll[j] = i + j;
                    //event.ll[j] = 's';
                    //event.ll.setCharAt(j,'s');
                    //event.publish(pubString);

                    event.publish(new String ("1234567890123"));
                    event.publish(data);



                    //event.publish("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
                }
                ringBuffer.publish(sequence);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }


    //@Override
    public  BasePublisher createInstance(
            final CyclicBarrier cyclicBarrier,
            final RingBuffer<LongArray_Object> ringBuffer,
            final long iterations,
            final long arraySize) {

        return new LongArrayPublisher_Object(
        cyclicBarrier,
        ringBuffer,
        iterations,
        arraySize);
    }

    public LongArrayPublisher_Object(
            final CyclicBarrier cyclicBarrier,
            final RingBuffer<LongArray_Object> ringBuffer,
            final long iterations,
            final long arraySize)
    {
        super(cyclicBarrier, ringBuffer, iterations, arraySize);

    }




    public static BasePublisher simpleInstance(){
        return new LongArrayPublisher_Object(null,null,0,0);
    }



    @Override
    public void run()
    {

        String pubString = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        try
        {
            cyclicBarrier.await();

            for (long i = 0; i < iterations; i++)
            {
                long sequence = ringBuffer.next();
                LongArray_Object event = ringBuffer.get(sequence);
                for (int j = 0; j < arraySize; j++)
                {
                    //event.ll[j] = i + j;
                    //event.ll[j] = 's';
                    //event.ll.setCharAt(j,'s');
                    //event.publish(pubString);
                    event.publish(new String ("1234567890123"));
                    //event.publish("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
                }
                ringBuffer.publish(sequence);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }


}
