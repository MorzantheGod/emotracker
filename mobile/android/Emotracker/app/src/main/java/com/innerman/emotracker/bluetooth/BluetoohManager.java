package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.model.DeviceDTO;

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

    public static int SCAN_MESSAGE = 42;
    public static int READ_MESSAGE = 43;

    private static final String POLAR = "Polar";
    private boolean isScanning = false;
    private boolean isReading = false;

    private Handler scanHandler;
    private Handler readHandler;

    public BluetoohManager(Handler scanHandler, Handler readHandler) {
        this.scanHandler = scanHandler;
        this.readHandler = readHandler;
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

                DeviceDTO dto = new DeviceDTO();
                dto.setName(name);
                dto.setMac(device.getAddress());

                Message msg = new Message();
                msg.what = SCAN_MESSAGE;
                msg.obj = dto;
                scanHandler.sendMessage(msg);

                BluetoothConnectionThread b = new BluetoothConnectionThread(device, bluetoothAdapter, readHandler);
                connThread = b;
                b.start();
                isReading = true;
            }
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
        {
            bluetoothAdapter.cancelDiscovery();
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

        isScanning = true;

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


    public void cancelDiscovery() {
        isScanning = false;

        if( bluetoothAdapter != null ) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    public void cancelReading() {
        isReading = false;

        if( connThread != null ) {
            connThread.cancel();
        }
    }

    public boolean isScanning() {
        return isScanning;
    }

    public boolean isReading() {
        return isReading;
    }

}
