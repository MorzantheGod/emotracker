package com.innerman.emotracker.bluetooth;

import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.device.DartaSensorDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 12.04.14.
 * This is bullshit
 */

@Deprecated
public class BluetoothKeepThread extends Thread {

    private volatile boolean cancelled = false;
    private int size = 0;
    private final List<DartaSensorDTO> data = new ArrayList<DartaSensorDTO>();

    private Handler readHandler;

    public BluetoothKeepThread(Handler readHandler) {
        this.readHandler = readHandler;
    }

    public void addData(List<DartaSensorDTO> dtos) {

        synchronized (data) {
            if( dtos != null ) {
                data.addAll(dtos);
            }

            size = data.size();
            data.notifyAll();
        }
    }

    public int getDataSize() {
        synchronized (data) {
            return data.size();
        }
    }

    @Override
    public void run() {

        long period = AppSettings.DEVICE_DISPLAY_INTERVAL;



        int prev = -1;
        int next = -1;

        DartaSensorDTO prevDTO = null;
        DartaSensorDTO nextDTO = null;

//        try {
//            Thread.sleep(AppSettings.DEVICE_DISPLAY_SLEEP_TIME);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        long prevTime = System.currentTimeMillis();
        long nextTime = 0;

        synchronized (data) {

            while(true) {

                nextTime = System.currentTimeMillis();
                long timeDiff = nextTime - prevTime;
                if( timeDiff < period ) {
                    continue;
                }

                //wait for data
                while (size <= 0 || size <= prev+1) {

                    if( cancelled ) {
                        sendOthers(prev);
                        break;
                    }

                    try {
                        data.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //end of waiting

                if( cancelled ) {
                    sendOthers(prev);
                    break;
                }

                if( prev == -1 ) {
                    prev = 0;
                    prevDTO = data.get(prev);
                    prevTime = System.currentTimeMillis();
                    sendMessage(prevDTO);
                    continue;
                }

                next = prev + 1;

                if( data.size() <= next ) {
                    continue;
                }
                nextDTO = data.get(next);

                if( nextDTO == null || prevDTO == null ) {
                    continue;
                }

                if( nextDTO.getDeviceDate() == null || prevDTO.getDeviceDate() == null ) {
                    continue;
                }

                sendMessage(nextDTO);

                prevTime = nextTime;
                prevDTO = nextDTO;
                prev = next;
            }
        }
    }

    public synchronized void cancel() {
        cancelled = true;
    }

    private void sendMessage(DartaSensorDTO dto) {
        Message msg = new Message();
        msg.what = AppSettings.READ_MESSAGE;
        msg.obj = dto;
        readHandler.sendMessage(msg);
    }

    private void sendOthers(int prev) {

        if( data.size() <= 0 || data.size() <= prev || prev < 0 ) {
            return;
        }

        try {
            for(int i = prev+1; i <= data.size(); i++) {
                DartaSensorDTO dto = data.get(i);
                sendMessage(dto);
            }
        }
        catch (Exception e) {}
    }
}
