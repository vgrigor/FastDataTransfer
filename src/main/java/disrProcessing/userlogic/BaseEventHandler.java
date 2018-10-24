package disrProcessing.userlogic;

import com.lmax.disruptor.BatchStartAware;
import com.lmax.disruptor.EventHandler;
import disrProcessing.LongArray_Object;
import interfaces.DataReceiver;

import java.util.concurrent.CountDownLatch;

public abstract class BaseEventHandler<E>  implements EventHandler<E>, BatchStartAware {

    abstract public long getBatchesProcessed();
    abstract public void reset(final CountDownLatch latch, final long expectedCount);
}
