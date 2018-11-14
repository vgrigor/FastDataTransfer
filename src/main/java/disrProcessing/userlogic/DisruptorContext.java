package disrProcessing.userlogic;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import disrProcessing.LongArray_Object;

import java.util.concurrent.CyclicBarrier;

public class DisruptorContext {

    public long getIterations() {
        return iterations;
    }

    public int getNUM_PUBLISHERS() {
        return NUM_PUBLISHERS;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }
    public RingBuffer<LongArray_Object>[] getRingBuffer() {
        return buffers;
    }
    public SequenceBarrier[] getSequenceBarrier() {
        return barriers;
    }

    protected final long iterations;
    int NUM_PUBLISHERS;
    public final int ARRAY_SIZE ;

    private final CyclicBarrier cyclicBarrier;
    private final RingBuffer<LongArray_Object>[] buffers;
    private final SequenceBarrier[] barriers;

    public DisruptorContext(
            int NUM_PUBLISHERS,
            final long iterations,
            int ARRAY_SIZE )
    {

        this.iterations = iterations;
        this.NUM_PUBLISHERS = NUM_PUBLISHERS;
        this.ARRAY_SIZE = ARRAY_SIZE;


    cyclicBarrier = new CyclicBarrier(NUM_PUBLISHERS + 1);
    buffers = new RingBuffer[NUM_PUBLISHERS];
    barriers = new SequenceBarrier[NUM_PUBLISHERS];

    }
}
