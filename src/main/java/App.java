import disrProcessing.LongArrayPublisher_Object;
import disrProcessing.LongArray_Object;
import disrProcessing.ThreeToThreeSequencedThroughput_ObjectTest_Common;

public class App {
    private static  int NUM_PUBLISHERS = 3;

    public static void main(String[] args) {


        ThreeToThreeSequencedThroughput_ObjectTest_Common<LongArrayPublisher_Object> disruptor =
                new ThreeToThreeSequencedThroughput_ObjectTest_Common( LongArrayPublisher_Object.simpleInstance(), NUM_PUBLISHERS);

        try {


            //disruptor.addPublisher();
            disruptor.main_run();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
