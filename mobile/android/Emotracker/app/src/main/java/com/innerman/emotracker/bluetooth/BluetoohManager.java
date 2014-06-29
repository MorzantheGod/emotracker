package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.model.network.DeviceDTO;
import com.innerman.emotracker.ui.ScanActivity;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    private static final String PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";

    private static final String DEVICE_NAME = "H-C-2010-06-01";
    private static final List<String> DEVICE_NAME_LIST = Arrays.asList(DEVICE_NAME, "Emotracker", "EmoDEV");

    private boolean isScanning = false;
    private boolean isReading = false;

    private Handler scanHandler;
    private Handler readHandler;
    private Handler curStateHandler;
    private ScanActivity scanActivity;


    private BluetoothConnectionThread connThread;

    public BluetoohManager(ScanActivity scanActivity) {
        this.scanActivity = scanActivity;

        scanHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                BluetoohManager.this.scanActivity.handleScanResultMessage(msg);
            }
        };


        readHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                BluetoohManager.this.scanActivity.handleReadResultMessage(msg);
            }
        };

        curStateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                BluetoohManager.this.scanActivity.handleCurrentStateMessage(msg);
            }
        };

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void toggleScanOrAddDevice() {

        boolean scanning = isScanning();
        boolean reading = isReading();

        if( !scanning && !reading ) {

            boolean enabled = checkForBluetoohEnabled();
            if( enabled ) {

                sendCurrentStateMessage(BluetoothManagerState.START_SCAN);

                //disable paired devices
                Map<String, BluetoothDevice> devices = new HashMap<String, BluetoothDevice>(); //performBluetoothScan();
                if( devices.isEmpty() ) {
                    sendCurrentStateMessage(BluetoothManagerState.START_DISCOVERY);

                    startDiscovery();
                }
                else {
                    isScanning = false;

                    sendCurrentStateMessage(BluetoothManagerState.DEVICE_FOUND, getMainDevice() );
                }
            }
            else {
                sendCurrentStateMessage(BluetoothManagerState.ENABLE_BLUETOOTH);
            }
        }
        else {
            if( reading ) {
                cancelReading();

                sendCurrentStateMessage(BluetoothManagerState.CANCEL_READ);
            }
            else {
                cancelDiscovery();

                sendCurrentStateMessage(BluetoothManagerState.CANCEL_DISCOVERY);

            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if( bluetoothAdapter == null ) {
            return;
        }

        String action = intent.getAction();
        // When discovery finds a device

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if( device == null ) {
                return;
            }

            String name = device.getName();
            if( name == null ) {
                return;
            }

            if( DEVICE_NAME_LIST.contains(name) ) {
                deviceNames.put(device.getName(), device.getAddress());
                devices.put(device.getName(), device);

                cancelDiscovery();


                try {
                    byte[] pin = ByteBuffer.allocate(4).putInt(1234).array();
                    Method m = device.getClass().getMethod("setPin", byte[].class);
                    m.invoke(device, pin);

                    device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                sendCurrentStateMessage(BluetoothManagerState.DEVICE_FOUND, new DeviceDTO(device.getName(), device.getAddress()));
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

    public Map<String, BluetoothDevice> performBluetoothScan() {

        isScanning = true;

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {

                if( device.getName() != null && device.getName().contains(DEVICE_NAME)) {
                    deviceNames.put(device.getName(), device.getAddress());
                    devices.put(device.getName(), device);
                }
            }
        }

        return devices;
    }

    public void startDiscovery() {
        isScanning = true;

        bluetoothAdapter.startDiscovery();
    }

    public void cancelDiscovery() {
        isScanning = false;

        if( bluetoothAdapter != null ) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    public DeviceDTO startReading() {

        BluetoothDevice device = getMainBluetoothDevice();
        return startReadingFromDevice(device);
    }

    public void cancelReading() {
        isReading = false;

        if( connThread != null ) {
            connThread.cancel();
        }
    }


    private DeviceDTO getMainDevice() {

        BluetoothDevice device = getMainBluetoothDevice();
        return convertBluetoothToDTO(device);
    }

    private DeviceDTO convertBluetoothToDTO(BluetoothDevice device) {
        DeviceDTO dto = new DeviceDTO();
        dto.setName(device.getName());
        dto.setMac(device.getAddress());

        return dto;
    }

    private BluetoothDevice getMainBluetoothDevice() {

        for (Map.Entry<String, BluetoothDevice> entry : this.devices.entrySet()) {
            String key = entry.getKey();
            if( DEVICE_NAME_LIST.contains(key) ) {

                BluetoothDevice device = entry.getValue();
                return device;
            }
        }

        return null;
    }

    private DeviceDTO startReadingFromDevice(BluetoothDevice device) {

        DeviceDTO dto = convertBluetoothToDTO(device);

        Message msg = new Message();
        msg.what = SCAN_MESSAGE;
        msg.obj = dto;
        scanHandler.sendMessage(msg);

        BluetoothConnectionThread b = new BluetoothConnectionThread(device, bluetoothAdapter, readHandler);
        connThread = b;
        b.start();
        isReading = true;

        return dto;
    }

    private void sendCurrentStateMessage(BluetoothManagerState state) {

        sendCurrentStateMessage(state, null);
    }

    private void sendCurrentStateMessage(BluetoothManagerState state, Object obj) {

        Message msg = new Message();
        msg.what = state.getValue();
        msg.obj = obj;

        curStateHandler.sendMessage(msg);
    }


    public boolean isScanning() {
        return isScanning;
    }

    public boolean isReading() {
        return isReading;
    }

}
