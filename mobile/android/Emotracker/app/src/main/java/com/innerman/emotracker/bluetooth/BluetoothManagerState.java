package com.innerman.emotracker.bluetooth;

/**
 * Created by petrpopov on 12.04.14.
 */
public enum BluetoothManagerState {
    START_SCAN(0),
    START_READ(1),
    DEVICE_FOUND(2),
    DEVICE_NOT_FOUND(3),
    CANCEL_READ(4),
    CANCEL_DISCOVERY(5);

    private final int value;

    private BluetoothManagerState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
