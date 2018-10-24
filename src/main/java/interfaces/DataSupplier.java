package interfaces;

public interface DataSupplier {
    byte[] onDataReady( byte[] data, DataReceiver dataReceiver  );
    void setDataReceiver(DataReceiver  dataReceiver );

}
