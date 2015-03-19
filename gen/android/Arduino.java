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
    
    public void pinMode(int pin, int mode) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putInt(0);
        byteBuffer.getInt(pin);
        byteBuffer.getInt(mode);
        mTransceiver.send(byteBuffer.array());
    }

    public void digitalWrite(int pin, int value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putInt(1);
        byteBuffer.getInt(pin);
        byteBuffer.getInt(value);
        mTransceiver.send(byteBuffer.array());
    }

    public void analogWrite(int pin, int value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putInt(2);
        byteBuffer.getInt(pin);
        byteBuffer.getInt(value);
        mTransceiver.send(byteBuffer.array());
    }

    public int digitalRead(int pin) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putInt(3);
        byteBuffer.getInt(pin);
        byte[] bytes = mTransceiver.sendForResult(byteBuffer.array());
        return ByteBuffer.wrap(bytes).getInt();
    }

    public int analogRead(int pin) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putInt(4);
        byteBuffer.getInt(pin);
        byte[] bytes = mTransceiver.sendForResult(byteBuffer.array());
        return ByteBuffer.wrap(bytes).getInt();
    }

    public class Servo {

        public final int objectId = nextObjectId();

        public Servo() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            byteBuffer.putInt(6);
            byteBuffer.putInt(this.objectId);
            mTransceiver.send(byteBuffer.array());
        }

        public void attach(int pin) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(12);
            byteBuffer.putInt(7);
            byteBuffer.putInt(this.objectId);
            byteBuffer.getInt(pin);
            mTransceiver.send(byteBuffer.array());
        }

        public void attach(int pin, int min, int max) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            byteBuffer.putInt(8);
            byteBuffer.putInt(this.objectId);
            byteBuffer.getInt(pin);
            byteBuffer.getInt(min);
            byteBuffer.getInt(max);
            mTransceiver.send(byteBuffer.array());
        }

        public void write(int value) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(12);
            byteBuffer.putInt(9);
            byteBuffer.putInt(this.objectId);
            byteBuffer.getInt(value);
            mTransceiver.send(byteBuffer.array());
        }

        public void writeMicroseconds(int value) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(12);
            byteBuffer.putInt(10);
            byteBuffer.putInt(this.objectId);
            byteBuffer.getInt(value);
            mTransceiver.send(byteBuffer.array());
        }
    }

}
