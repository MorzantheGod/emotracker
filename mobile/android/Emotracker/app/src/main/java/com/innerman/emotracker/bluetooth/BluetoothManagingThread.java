package com.innerman.emotracker.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.device.DartaSensorDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by petrpopov on 09.03.14.
 */

public class BluetoothManagingThread extends Thread {

    private final BluetoothSocket socket;
    private final InputStream in;
    private final OutputStream out;

    private Handler readHandler;

    private BluetoothKeepThread keepThread;

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

        keepThread = new BluetoothKeepThread(readHandler);

        char[] buf = new char[1024];

        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        Reader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        long time1 = System.currentTimeMillis();
        long time2;


        while (true) {
            try {
                time2 = System.currentTimeMillis();

                if (time2 - time1 >= AppSettings.DEVICE_MEASURE_INTERVAL) {

                    if (reader != null) {
                        bytes = reader.read(buf);
                    }
                    else {
                        break;
                    }

                    if (bytes < 0)
                        break;

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(buf, 0, bytes);
                    String res = stringBuilder.toString();

                    List<DartaSensorDTO> dtos = DartaTextParser.fromString(res);

                    if( dtos != null ) {
                        if( dtos.size() > 0 ) {
                            keepThread.addData(dtos);

                            sendReadMessage(dtos.get(0));
//                            if( !keepThread.isAlive() && keepThread.getDataSize() >= AppSettings.DEVICE_DISPLAY_MIN_START) {
//                                keepThread.start();
//                            }
                        }
                    }


                    time1 = time2;
                }
            }
            catch (IOException e) {
                break;
            }
        }
    }

    private void sendReadMessage(DartaSensorDTO dto) {

        Message msg = new Message();
        msg.what = AppSettings.READ_MESSAGE;
        msg.obj = dto;
        readHandler.sendMessage(msg);
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
            if( keepThread != null ) {
                keepThread.cancel();
            }

            socket.close();
        }
        catch (IOException e) {
        }
    }
}
