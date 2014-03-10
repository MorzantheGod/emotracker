package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by petrpopov on 10.03.14.
 */
public class BluetoohManager extends BroadcastReceiver {

    private Map<String, String> deviceNames = new HashMap<String, String>();
    private Map<String, BluetoothDevice> devices = new HashMap<String, BluetoothDevice>();
    private BluetoothAdapter bluetoothAdapter;

    private static final String POLAR = "Polar";
    private boolean isWorking = false;

    public BluetoohManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private BluetoothConnectionThread connThread;

    @Override
    public void onReceive(Context context, Intent intent) {

        if( bluetoothAdapter == null ) {
            return;
        }

        String action = intent.getAction();
        // When discovery finds a device

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


            String name = device.getName();
            if( name == null ) {
                return;
            }

            if( name.contains(POLAR)) {
                deviceNames.put(device.getName(), device.getAddress());
                devices.put(device.getName(), device);

                bluetoothAdapter.cancelDiscovery();

                BluetoothConnectionThread b = new BluetoothConnectionThread(device, bluetoothAdapter);
                connThread = b;
                b.start();
            }
        }
    }

    public boolean checkForBluetoothExistence() {
        if( bluetoothAdapter == null ) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (bluetoothAdapter == null) {
            return false;
            // Device does not support Bluetooth
        }

        return true;
    }

    public boolean checkForBluetoohEnabled() {

        if( bluetoothAdapter == null ) {
            return false;
        }

        if (!bluetoothAdapter.isEnabled()) {
            return false;
        }

        return true;
    }

    public void performBluetoothScan() {

        isWorking = true;

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceNames.put(device.getName(), device.getAddress());
                devices.put(device.getName(), device);
            }
        }
    }

    public void startDiscovery() {
        bluetoothAdapter.startDiscovery();
    }

    public void cancel() {
        isWorking = false;

        if( bluetoothAdapter != null ) {
            bluetoothAdapter.cancelDiscovery();
        }

        if( connThread != null ) {
            connThread.cancel();
        }
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
}
