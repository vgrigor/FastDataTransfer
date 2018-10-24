import disrProcessing.userlogic.LongArrayEventHandler_Object;
import disrProcessing.userlogic.LongArrayPublisher_Object;
import disrProcessing.ThreeToThreeSequencedThroughput_ObjectTest_Common;

public class App {
    private static  int NUM_PUBLISHERS = 3;

    public static void main(String[] args) {

        //TODO реализовать создание всех паблишеров снаружи
        // сделать контекст дисрапотора для этого, чтобы инициализовать паблишеры
        //
        LongArrayPublisher_Object longArrayPublisher_Object = new LongArrayPublisher_Object();

        FileReaderPL fileReaderPL = new FileReaderPL();
        fileReaderPL.setDataReceiver(longArrayPublisher_Object);


        ThreeToThreeSequencedThroughput_ObjectTest_Common<LongArrayPublisher_Object> disruptor =
                new ThreeToThreeSequencedThroughput_ObjectTest_Common( LongArrayPublisher_Object.simpleInstance(),
                NUM_PUBLISHERS,
                new LongArrayEventHandler_Object());

        disruptor.main_run();

        try {


            //disruptor.addPublisher();
            disruptor.main_run();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
