package com.innerman.emotracker.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.innerman.emotracker.R;
import com.innerman.emotracker.bluetooth.BluetoohManager;
import com.innerman.emotracker.bluetooth.BluetoothManagerState;
import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.DeviceDTO;

public class DeviceActivity extends BaseActivity implements ScanActivity {

    private Button addButton;
    private TextView statusView;
    private TextView addDeviceView;

    private BluetoohManager bluetoohManager = new BluetoohManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        //check for bluetooth on start
        checkForBluetoothExistence();
        checkForBluetoohEnabled(AppSettings.REQUEST_ENABLE_BT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new AddButtonClickListener());

        statusView = (TextView) findViewById(R.id.statusView);
        addDeviceView = (TextView) findViewById(R.id.addDeviceView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( AppSettings.REQUEST_ENABLE_BT.equals(requestCode)) {
            if(resultCode == RESULT_CANCELED) {
                showMessage(getString(R.string.bt_enable));
            }
        }
        else if( AppSettings.REQUEST_ENABLE_BT_FROM_SCAN.equals(requestCode) ) {
            if(resultCode == RESULT_CANCELED) {
                showMessage(getString(R.string.bt_enable));
            }
            else if( resultCode == RESULT_OK ) {
              //  performScan();
            }
        }
    }

    private void checkForBluetoothExistence() {
        boolean exist = bluetoohManager.checkForBluetoothExistence();
        if( !exist ) {
            showMessage(getString(R.string.bt_not_support));
    //        setFormEnabled(false);
        }
    }

    private boolean checkForBluetoohEnabled(int status) {
        boolean enabled = bluetoohManager.checkForBluetoohEnabled();
        if( !enabled ) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, status);
        }

        return enabled;
    }

    @Override
    public void handleReadResultMessage(Message msg) {

    }

    @Override
    public void handleScanResultMessage(Message msg) {

    }

    @Override
    public void handleCurrentStateMessage(Message msg) {

        if( msg == null  ) {
            return;
        }

        if( msg.what == BluetoothManagerState.START_SCAN.getValue() ) {
            addButton.setText(getString(R.string.bt_stop));
            addButton.setEnabled(false);

            statusView.setText(getString(R.string.bt_searching));
            addDeviceView.setVisibility(View.GONE);
        }
        else if( msg.what == BluetoothManagerState.DEVICE_NOT_FOUND.getValue() ) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bluetoohManager, filter);
        }
        else if( msg.what == BluetoothManagerState.CANCEL_READ.getValue() ) {
            statusView.setText(getString(R.string.sending_data));
            // new SaveHttpRequestTask().execute(getDataDTO());
        }
        else if( msg.what == BluetoothManagerState.CANCEL_DISCOVERY.getValue() ) {
            statusView.setText(getString(R.string.bt_not_connected));
            addDeviceView.setVisibility(View.VISIBLE);
        }
        else if( msg.what == BluetoothManagerState.DEVICE_FOUND.getValue() ) {
            addButton.setEnabled(true);
            addButton.setText(getString(R.string.bt_scan));

            if( msg.obj != null && msg.obj instanceof DeviceDTO ) {
                DeviceDTO device = (DeviceDTO) msg.obj;

                String message = getString(R.string.bt_connected_to) + device.getName();
                statusView.setText(message);
            }
            else {
                statusView.setText(getString(R.string.bt_unknown_error));
            }
            addDeviceView.setVisibility(View.GONE);
        }
    }

    private final class AddButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            bluetoohManager.toggleScanOrAddDevice();
        }
    }
}
