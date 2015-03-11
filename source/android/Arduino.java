package com.appinmotion.android2arduino

public class Arduino {
    
    private final AsyncTransceiver mTransceiver;
    
    public Arduino(AsyncTransceiver transceiver) {
        mTransceiver = transceiver;
    }
    
    public interface AsyncTransceiver {
        // Send data to Arduino in a fire and forget.
        byte[] send(byte[] data);
        // Send data to Arduino and block the thread for return value.
        byte[] sendForResult(byte[] data)
    }
    
    // --- Generated code below ---
    
    // _PLACE_HOLDER_
}
