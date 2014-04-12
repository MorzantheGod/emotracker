package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by petrpopov on 09.03.14.
 */

@Deprecated
public class BluetoothConnectionPolarThread extends Thread  {

    //SPP profile
    //https://stackoverflow.com/questions/4032391/android-bluetooth-where-can-i-get-uuid
    //http://www.bluecove.org/bluecove/apidocs/javax/bluetooth/UUID.html
    private static final String uuid = "00001101-0000-1000-8000-00805F9B34FB";
    private static final UUID buuid = UUID.fromString(uuid);

    private final BluetoothSocket socket;
    private final BluetoothDevice device;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManagingPolarThread connThread;

    private Handler readHandler;

    public BluetoothConnectionPolarThread(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, Handler readHandler) {

        this.bluetoothAdapter = bluetoothAdapter;
        this.device = device;
        this.readHandler = readHandler;


        BluetoothSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(buuid);
        }
        catch (IOException e) {
        }

        socket = tmp;
    }

    @Override
    public void run() {

        // Cancel discovery because it will slow down the connection
        bluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                socket.close();
            }
            catch (IOException closeException) {
            }
            return;
        }

        BluetoothManagingPolarThread b = new BluetoothManagingPolarThread(socket, readHandler);
        connThread = b;
        b.start();
    }

    public void cancel() {
        try {
            socket.close();
            if( connThread != null ) {
                connThread.cancel();
            }
        }
        catch (IOException e) {
        }
    }
}
