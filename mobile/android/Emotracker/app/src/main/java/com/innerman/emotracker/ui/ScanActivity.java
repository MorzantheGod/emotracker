package com.innerman.emotracker.ui;

import android.os.Message;

/**
 * Created by petrpopov on 12.04.14.
 */
public interface ScanActivity {

    public void handleReadResultMessage(Message msg);
    public void handleScanResultMessage(Message msg);
    public void handleCurrentStateMessage(Message msg);
}
