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

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.util.DaemonThreadFactory;
import disrProcessing.userlogic.LongArrayEventHandler_Object;
import disrProcessing.userlogic.LongArrayPublisher_Object;
import disruptor.AbstractPerfTestDisruptor;
import disruptor.MultiBufferBatchEventProcessor;
import disruptor.PerfTestContext;

import java.util.concurrent.*;

/**
 * <pre>
 *
 * Sequence a series of events from multiple publishers going to one event processor.
 *
 * Disruptor:
 * ==========
 *             track to prevent wrap
 *             +--------------------+
 *             |                    |
 *             |                    |
 * +----+    +====+    +====+       |
 * | P1 |--->| RB |--->| SB |--+    |
 * +----+    +====+    +====+  |    |
 *                             |    v
 * +----+    +====+    +====+  | +----+
 * | P2 |--->| RB |--->| SB |--+>| EP |
 * +----+    +====+    +====+  | +----+
 *                             |
 * +----+    +====+    +====+  |
 * | P3 |--->| RB |--->| SB |--+
 * +----+    +====+    +====+
 *
 * P1 - Publisher 1
 * P2 - Publisher 2
 * P3 - Publisher 3
 * RB - RingBuffer
 * SB - SequenceBarrier
 * EP - EventProcessor
 *
 * </pre>
 */
public final class ThreeToThreeSequencedThroughput_ObjectTest extends AbstractPerfTestDisruptor
{
    private static final int NUM_PUBLISHERS = 3;
    //private static final int ARRAY_SIZE = 3;
    private static final int BUFFER_SIZE = 1024 * 64;
    private static final long ITERATIONS = 1000L * 1000L * 180L;
    private final ExecutorService executor =
        Executors.newFixedThreadPool(NUM_PUBLISHERS + 1, DaemonThreadFactory.INSTANCE);
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_PUBLISHERS + 1);

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    private final RingBuffer<LongArray_Object>[] buffers = new RingBuffer[NUM_PUBLISHERS];
    private final SequenceBarrier[] barriers = new SequenceBarrier[NUM_PUBLISHERS];
    private final LongArrayPublisher_Object[] valuePublishers = new LongArrayPublisher_Object[NUM_PUBLISHERS];

    private final LongArrayEventHandler_Object handler = new LongArrayEventHandler_Object();
    private final MultiBufferBatchEventProcessor<LongArray_Object> batchEventProcessor;

    private static final EventFactory<LongArray_Object> FACTORY = new EventFactory<LongArray_Object>()
    {
        @Override
        public LongArray_Object newInstance()
        {
            //return new LongArray_Object[ARRAY_SIZE];
            return new LongArray_Object();
        }
    };

    {
        for (int i = 0; i < NUM_PUBLISHERS; i++)
        {
            buffers[i] = RingBuffer.createSingleProducer(FACTORY, BUFFER_SIZE, new YieldingWaitStrategy());
            barriers[i] = buffers[i].newBarrier();
            valuePublishers[i] = new LongArrayPublisher_Object(
                cyclicBarrier,
                buffers[i],
                ITERATIONS / NUM_PUBLISHERS,
                    LongArray_Object.ARRAY_SIZE);
        }

        batchEventProcessor = new MultiBufferBatchEventProcessor<LongArray_Object>(buffers, barriers, handler);

        for (int i = 0; i < NUM_PUBLISHERS; i++)
        {
            buffers[i].addGatingSequences(batchEventProcessor.getSequences()[i]);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected int getRequiredProcessorCount()
    {
        return 4;
    }

    @Override
    protected PerfTestContext runDisruptorPass() throws Exception
    {
        PerfTestContext perfTestContext = new PerfTestContext();
        final CountDownLatch latch = new CountDownLatch(1);
        handler.reset(latch, ITERATIONS);

        Future<?>[] futures = new Future[NUM_PUBLISHERS];
        for (int i = 0; i < NUM_PUBLISHERS; i++)
        {
            futures[i] = executor.submit(valuePublishers[i]);
        }
        executor.submit(batchEventProcessor);

        long start = System.currentTimeMillis();
        cyclicBarrier.await();

        for (int i = 0; i < NUM_PUBLISHERS; i++)
        {
            futures[i].get();
        }

        latch.await();

        //perfTestContext.setDisruptorOps((ITERATIONS * 1000L * LongArray_Object.ARRAY_SIZE) / (System.currentTimeMillis() - start));
        perfTestContext.setDisruptorOps((ITERATIONS * 1000L * 1) / (System.currentTimeMillis() - start));
        //perfTestContext.setBatchData(handler.getBatchesProcessed(), ITERATIONS * LongArray_Object.ARRAY_SIZE);
        perfTestContext.setBatchData(handler.getBatchesProcessed(), ITERATIONS * 1);
        batchEventProcessor.halt();

        return perfTestContext;
    }



    public static void main(String[] args) throws Exception
    {
        new ThreeToThreeSequencedThroughput_ObjectTest().testImplementations();
    }
}
