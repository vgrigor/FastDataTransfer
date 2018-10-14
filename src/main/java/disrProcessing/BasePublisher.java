package disrProcessing;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CyclicBarrier;

public /*abstract*/ class BasePublisher implements Runnable {

    protected final CyclicBarrier cyclicBarrier;
    protected final RingBuffer<LongArray_Object> ringBuffer;
    protected final long iterations;
    protected final long arraySize;

    public BasePublisher(
            final CyclicBarrier cyclicBarrier,
            final RingBuffer<LongArray_Object> ringBuffer,
            final long iterations,
            final long arraySize)
    {
        this.cyclicBarrier = cyclicBarrier;
        this.ringBuffer = ringBuffer;
        this.iterations = iterations;
        this.arraySize = arraySize;
    }


    public <E extends BasePublisher> BasePublisher[] createArray(int number,
                                       final CyclicBarrier cyclicBarrier,
                                       final RingBuffer<LongArray_Object> ringBuffer,
                                       final long iterations,
                                       final long arraySize
    ){




        BasePublisher[] publishers = new BasePublisher[number];

        for(int i=0; i< number ; i++) {
            publishers[i] = createInstance(
                    cyclicBarrier,
            ringBuffer,
            iterations,
            arraySize);
        }

        return publishers;
    }
    //Обязаны реализовать наследники
    public  /*abstract*/ BasePublisher createInstance(final CyclicBarrier cyclicBarrier,
                                          final RingBuffer<LongArray_Object> ringBuffer,
                                          final long iterations,
                                          final long arraySize){
        return null;
    };


    //Обязаны реализовать наследники
    @Override
    public void run() {

    }

}
