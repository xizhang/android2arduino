package com.appinmotion.android2arduino;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Arduino {
    
    private final AsyncTransceiver mTransceiver;
    private static final Set<Integer> sObjectIds = new HashSet<>();
    private static final Random sRandom = new Random();    
    
    public Arduino(AsyncTransceiver transceiver) {
        mTransceiver = transceiver;
    }
    
    public interface AsyncTransceiver {
        // Send data to Arduino in a fire and forget.
        void send(byte[] data);
        // Send data to Arduino and block the thread for return value.
        byte[] sendForResult(byte[] data);
    }
    
    private static int nextObjectId() {
        int id = sRandom.nextInt();
        while (sObjectIds.contains(id)) {
            id = sRandom.nextInt();
        }
        sObjectIds.add(id);
        return id;
    }
    
    // --- Generated code below ---
    // _PLACE_HOLDER_
}
