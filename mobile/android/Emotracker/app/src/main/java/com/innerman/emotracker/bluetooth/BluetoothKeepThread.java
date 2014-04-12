package com.innerman.emotracker.bluetooth;

import android.os.Handler;
import android.os.Message;

import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.device.DartaSensorDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 12.04.14.
 */
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

    @Override
    public void run() {

        long period = 1000;

        long prevTime = 0;
        long nextTime = 0;

        int prev = -1;
        int next = -1;

        DartaSensorDTO prevDTO = null;
        DartaSensorDTO nextDTO = null;

        synchronized (data) {

            while(true) {

                while (size <= 0 || size <= prev+3 || size <= next+1) {

                    if( cancelled ) {
                        break;
                    }

                    try {
                        data.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if( cancelled ) {
                    break;
                }

                if( prev == -1 ) {
                    prev = 0;
                    prevDTO = data.get(prev);
                    prevTime = System.currentTimeMillis();
                    sendMessage(prevDTO);
                    continue;
                }


                nextTime = System.currentTimeMillis();
                long timeDiff = nextTime - prevTime;
                if( timeDiff >= period ) {
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

                    long dtoDiff = nextDTO.getDeviceDate().getTime() - prevDTO.getDeviceDate().getTime();
                    if( timeDiff >= dtoDiff ) {
                        sendMessage(nextDTO);

                        prevTime = nextTime;
                        prevDTO = nextDTO;
                        prev = next;
                    }
                }

            }
        }
    }

    public void sendMessage(DartaSensorDTO dto) {
        Message msg = new Message();
        msg.what = AppSettings.READ_MESSAGE;
        msg.obj = dto;
        readHandler.sendMessage(msg);
    }

    public synchronized void cancel() {
        cancelled = true;
    }
}
