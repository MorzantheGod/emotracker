package com.innerman.emotracker.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.innerman.emotracker.R;
import com.innerman.emotracker.bluetooth.BluetoohManager;
import com.innerman.emotracker.bluetooth.BluetoothManagerState;
import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.DeviceDTO;

public class DeviceActivity extends BaseActivity implements ScanActivity {

    private Button addButton;
    private Button recordButton;
    private TextView statusView;
    private TextView addDeviceView;

    private BluetoohManager bluetoohManager = new BluetoohManager(this);

    private volatile DeviceDTO mainDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new RecordButtonClickListener());

        statusView = (TextView) findViewById(R.id.statusView);
        addDeviceView = (TextView) findViewById(R.id.addDeviceView);

        checkFormComponents();
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

        if( msg.what == BluetoothManagerState.ENABLE_BLUETOOTH.getValue() ) {
            addButton.setText(getString(R.string.bt_scan));

            setAddDeviceViewStatus();

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, AppSettings.REQUEST_ENABLE_BT);
        }
        else if( msg.what == BluetoothManagerState.START_SCAN.getValue() ) {
            statusView.setText(getString(R.string.bt_searching));
            addDeviceView.setVisibility(View.GONE);
        }
        else if( msg.what == BluetoothManagerState.START_DISCOVERY.getValue() ) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bluetoohManager, filter);
        }
        else if( msg.what == BluetoothManagerState.CANCEL_READ.getValue() ) {
            statusView.setText(getString(R.string.sending_data));
            // new SaveHttpRequestTask().execute(getDataDTO());
        }
        else if( msg.what == BluetoothManagerState.CANCEL_DISCOVERY.getValue() ) {
            statusView.setText(getString(R.string.bt_not_connected));

            setAddDeviceViewStatus();
        }
        else if( msg.what == BluetoothManagerState.DEVICE_FOUND.getValue() ) {
            addButton.setEnabled(false);
            addButton.setText(getString(R.string.bt_scan));

            if( msg.obj != null && msg.obj instanceof DeviceDTO ) {
                DeviceDTO device = (DeviceDTO) msg.obj;

                setMainDevice(device);
                checkFormComponents();
                setAddDeviceViewStatus();

                String statusMessage = getString(R.string.bt_connected_to) + " " + device.getName();
                statusView.setText(statusMessage);
            }
            else {
                statusView.setText(getString(R.string.bt_unknown_error));
                addDeviceView.setText(getString(R.string.bt_add_device));
            }

            addDeviceView.setVisibility(View.VISIBLE);
        }
    }

    private void setAddDeviceViewStatus() {

        DeviceDTO dto = getMainDevice();
        setAddDeviceViewStatus(dto);
    }

    private void setAddDeviceViewStatus(DeviceDTO dto) {

        if( dto == null ) {
            addDeviceView.setText(getString(R.string.bt_add_device));
            addDeviceView.setVisibility(View.VISIBLE);
        }
        else {
            String macMessage = getString(R.string.bt_mac) + " " + dto.getMac();
            addDeviceView.setText(macMessage);

            addDeviceView.setVisibility(View.VISIBLE);
        }
    }

    private void checkFormComponents() {

        DeviceDTO device = getMainDevice();
        if( device == null ) {
            recordButton.setEnabled(false);
        }
        else {
            addButton.setEnabled(false);
            recordButton.setEnabled(true);
        }
    }

    private final class AddButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if( !bluetoohManager.isScanning() ) {
                addButton.setText(getString(R.string.bt_stop));
            }
            else {
                addButton.setText(getString(R.string.bt_scan));
            }


            bluetoohManager.toggleScanOrAddDevice();
        }
    }

    private final class RecordButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            bluetoohManager.startReading();
        }
    }

    public synchronized DeviceDTO getMainDevice() {
        return mainDevice;
    }

    public synchronized void setMainDevice(DeviceDTO mainDevice) {
        this.mainDevice = mainDevice;
    }
}
