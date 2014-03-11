package com.innerman.emotracker.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.innerman.emotracker.R;
import com.innerman.emotracker.bluetooth.BluetoohManager;
import com.innerman.emotracker.model.DeviceDTO;
import com.innerman.emotracker.model.SensorDTO;

public class ResultsActivity extends BaseActivity {

    private static final Integer REQUEST_ENABLE_BT = 42;
    private static final Integer REQUEST_ENABLE_BT_FROM_SCAN = 43;

    private DeviceDTO connectedDevice;

    private Button scanButton;
    private TextView statusView;
    private TextView deviceView;
    private TextView pulseView;

    private final Handler scanHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleScanResultMessage(msg);
        }
    };
    private final Handler readHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleReadResultMessage(msg);
        }
    };
    private BluetoohManager bluetoohManager = new BluetoohManager(scanHandler, readHandler);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);
        setTitle(getString(R.string.title_activity_results));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


        checkForBluetoothExistence();
        checkForBluetoohEnabled(REQUEST_ENABLE_BT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onScanButtonClick();

            }
        });

        statusView = (TextView) findViewById(R.id.statusView);
        deviceView = (TextView) findViewById(R.id.deviceView);
        pulseView = (TextView) findViewById(R.id.pulseView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( REQUEST_ENABLE_BT.equals(requestCode)) {
            if(resultCode == RESULT_CANCELED) {
                showMessage("You must enable Bluetooth for using this device");
            }
        }
        else if( REQUEST_ENABLE_BT_FROM_SCAN.equals(requestCode) ) {
            if(resultCode == RESULT_CANCELED) {
                showMessage("You must enable Bluetooth for using this device");
            }
            else if( resultCode == RESULT_OK ) {
                performScan();
            }
        }
    }

    private void onScanButtonClick() {
        boolean working = bluetoohManager.isScanning();
        boolean reading = bluetoohManager.isReading();

        if( !working ) {

            boolean enabled = checkForBluetoohEnabled(REQUEST_ENABLE_BT_FROM_SCAN);
            if( enabled ) {
                performScan();
            }
        }
        else {
            if( reading ) {
                bluetoohManager.cancelReading();
            }
            else {
                bluetoohManager.cancelDiscovery();

                scanButton.setText("Scan");
                statusView.setText("Not connected");
                deviceView.setText("");
            }
        }
    }

    private void performScan() {
        performBluetoothScan();
        scanButton.setText("Stop");
        statusView.setText("Searching...");
    }

    private void checkForBluetoothExistence() {
        boolean exist = bluetoohManager.checkForBluetoothExistence();
        if( !exist ) {
            showMessage("Device does not support bluetooth :(");
            setFormEnabled(false);
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

    private void performBluetoothScan() {
        bluetoohManager.performBluetoothScan();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoohManager, filter);

        bluetoohManager.startDiscovery();
    }

    private void handleScanResultMessage(Message msg) {
        if( msg == null ) {
            return;
        }

        int what = msg.what;
        if( what != BluetoohManager.SCAN_MESSAGE ) {
            return;
        }

        if( msg.obj == null ) {
            return;
        }

        if( !(msg.obj instanceof DeviceDTO) ) {
            return;
        }

        bluetoohManager.cancelDiscovery();

        DeviceDTO dto = (DeviceDTO) msg.obj;
        scanButton.setText("Scan");

        if( statusView != null ) {
            statusView.setText("Connected to: " + dto.getName());
        }

        if( deviceView != null ) {
            deviceView.setText("MAC: " + dto.getMac());
        }
    }

    private void handleReadResultMessage(Message msg) {
        if( msg == null ) {
            return;
        }

        int what = msg.what;
        if( what != BluetoohManager.READ_MESSAGE ) {
            return;
        }

        if( !(msg.obj instanceof SensorDTO) ) {
            return;
        }

        SensorDTO dto = (SensorDTO) msg.obj;

        new Test(dto).run();
    }

    private class Test implements Runnable {

        private SensorDTO dto;

        private Test(SensorDTO dto) {
            this.dto = dto;
        }

        @Override
        public void run() {
            if( pulseView != null ) {
                pulseView.setText( String.valueOf(dto.getHeartRate()));
            }
        }
    }

    private void setFormEnabled(boolean flag) {
        scanButton.setEnabled(flag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoohManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_results, container, false);
            return rootView;
        }
    }
}