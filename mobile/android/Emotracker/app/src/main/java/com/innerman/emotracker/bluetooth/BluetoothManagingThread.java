package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.model.SensorDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by petrpopov on 09.03.14.
 */
public class BluetoothManagingThread extends Thread {

    public static int READ_MESSAGE = 43;

    private final BluetoothSocket socket;
    private final InputStream in;
    private final OutputStream out;

    private PolarMessageParser parser;
    private Handler readHandler;

    public BluetoothManagingThread(BluetoothSocket socket, Handler readHandler) {

        this.socket = socket;
        this.readHandler = readHandler;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }
        catch (IOException e) {
        }

        in = tmpIn;
        out = tmpOut;
    }

    @Override
    public void run() {

        parser = new PolarMessageParser();

        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = in.read(buffer);


                SensorDTO dto = parser.parseBuffer(buffer);

                Message msg = new Message();
                msg.what = READ_MESSAGE;
                msg.obj = dto;
                readHandler.sendMessage(msg);

                // Send the obtained bytes to the UI activity
                //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
            }
            catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            out.write(bytes);
        }
        catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            socket.close();
        }
        catch (IOException e) {
        }
    }
}
