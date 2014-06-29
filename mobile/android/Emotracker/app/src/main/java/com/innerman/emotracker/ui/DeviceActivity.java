package com.innerman.emotracker.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.innerman.emotracker.R;
import com.innerman.emotracker.bluetooth.BluetoohManager;
import com.innerman.emotracker.bluetooth.BluetoothManagerState;
import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.config.UserDataStorage;
import com.innerman.emotracker.model.device.DartaSensorDTO;
import com.innerman.emotracker.model.network.DataEventDTO;
import com.innerman.emotracker.model.network.DeviceDTO;
import com.innerman.emotracker.model.network.MessageState;
import com.innerman.emotracker.model.network.WebMessage;
import com.innerman.emotracker.service.DataService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceActivity extends BaseActivity implements ScanActivity {

    private static final String DATE_FORMAT = "HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private Button addButton;
    private Button recordButton;
    private TextView statusView;
    private TextView addDeviceView;
    private TextView pulseView;
    private TextView watingView;

    private BluetoohManager bluetoohManager = new BluetoohManager(this);
    private volatile DeviceDTO mainDevice;

    //data and graph
    private volatile List<DartaSensorDTO> sensorDataList = new ArrayList<DartaSensorDTO>();
    private volatile boolean graphInited = false;
    private volatile GraphViewSeries graphSeries;
    private volatile GraphView.GraphViewData[] graphData;
    private long startTime = 0;

    private boolean btWorkStatus = false;

    private DataEventDTO mainDataEvent = new DataEventDTO();
    private UserDataStorage storage;

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

        storage = new UserDataStorage(getApplicationContext(), getString(R.string.config_name));

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new AddButtonClickListener());

        recordButton = (Button) findViewById(R.id.recordButton);

        recordButton.setOnClickListener(new RecordButtonClickListener(this));

        statusView = (TextView) findViewById(R.id.statusView);
        addDeviceView = (TextView) findViewById(R.id.addDeviceView);
        pulseView = (TextView) findViewById(R.id.pulseView);
        watingView = (TextView) findViewById(R.id.watingView);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //start activity A here
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleReadResultMessage(Message msg) {

        if( msg == null  ) {
            return;
        }

        if( msg.what != AppSettings.READ_MESSAGE ) {
            return;
        }

        if( msg.obj == null ) {
            return;
        }

        if( !(msg.obj instanceof DartaSensorDTO) ) {
            return;
        }

        synchronized (this) {

            if( sensorDataList.size() == 0 ) {
                startTime = System.currentTimeMillis();
            }
            long endTime = System.currentTimeMillis();
            long timeDiff = endTime-startTime;

            DartaSensorDTO dto = (DartaSensorDTO) msg.obj;

            List<DartaSensorDTO> sensors = mainDataEvent.getSensors();
            if( sensors == null ) {
                sensors = new ArrayList<DartaSensorDTO>();
                mainDataEvent.setSensors(sensors);
            }
            sensors.add(dto);
            mainDataEvent.setEndDate(dto.getSystemDate());

            double p = (double)dto.getPulseMs();
            p /= 1000.0;

            String status = getString(R.string.graph_title) + " " + p + "\t";
            status += "\t" + getString(R.string.graph_counter) + " " + dto.getCounter();
            status += "\t" + getString(R.string.graph_measure_time) + " " + (int)Math.ceil(((double)timeDiff)/1000.0) + " s";
            status += "\n" + getString(R.string.graph_time) + " " + DATE_FORMATTER.format(dto.getDeviceDate());
            status += "\t" + getString(R.string.graph_system_time) + " " + DATE_FORMATTER.format(dto.getSystemDate());
            status += "\n" + getString(R.string.x) + dto.getAccX() + "\t" + getString(R.string.y) + dto.getAccY() + "\t" + getString(R.string.z) + dto.getAccZ();

            pulseView.setText(status);

            sensorDataList.add(dto);
            if( sensorDataList.size() >= 0 ) {

                watingView.setVisibility(View.GONE);

                if( !graphInited ) {
                    initGraph(sensorDataList);
                }
                else {
                    appendToGraph(dto);
                }

            }
        }
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

    private void initGraph(List<DartaSensorDTO> sensorData) {

        graphData = getGraphData(sensorData);
        graphSeries = new GraphViewSeries(graphData);

        GraphView graphView = new LineGraphView(this, getString(R.string.graph_title));
        graphView.addSeries(graphSeries);

        graphView.setViewPort(2, 40);
        graphView.setManualYAxisBounds(1.4, 0.4);
        graphView.setScrollable(true);

        graphView.setScalable(true);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
        layout.addView(graphView);

        graphInited = true;
    }

    private void appendToGraph(DartaSensorDTO sensorData) {

        graphData = getGraphData(sensorDataList);

        double p = (double)sensorData.getPulseMs();
        p /= 1000.0;
        GraphView.GraphViewData data = new GraphView.GraphViewData(graphData.length, p );

        graphSeries.appendData(data, true);
    }

    private GraphView.GraphViewData[] getGraphData(List<DartaSensorDTO> sensorDataList) {
        GraphView.GraphViewData[] localGraphData = new GraphView.GraphViewData[sensorDataList.size()];
        for (int i = 0; i < localGraphData.length; i++) {

            double p = (double)sensorDataList.get(i).getPulseMs();
            p /= 1000.0;
            localGraphData[i] = new GraphView.GraphViewData(i, p);
        }

        return localGraphData;
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

    public boolean isBtWorkStatus() {
        return btWorkStatus;
    }

    public void setBtWorkStatus(boolean btWorkStatus) {
        this.btWorkStatus = btWorkStatus;
    }

    private final class RecordButtonClickListener implements View.OnClickListener {

        private Activity activity;

        private RecordButtonClickListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {

            if( !isBtWorkStatus() ) {

                LayoutInflater inflater = activity.getLayoutInflater();


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                View dialogView = inflater.inflate(R.layout.dialog_record, null);
                builder.setView(dialogView);

                final EditText titleDataEvent = (EditText) dialogView.findViewById(R.id.titleDataEvent);
                final EditText descriptionDataEvent = (EditText) dialogView.findViewById(R.id.descriptionDataEvent);

                builder.setTitle(R.string.bt_record_title);
                builder.setPositiveButton(R.string.bt_record, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        //do nothing - override in dialog, to prevent dismiss
                    }
                });
                builder.setNegativeButton(R.string.bt_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        dialog.cancel();

                        setBtWorkStatus(false);
                    }
                });


                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean close = true;

                        String title = titleDataEvent.getText().toString();
                        if(Strings.isNullOrEmpty(title) ) {
                            close = false;
                        }

                        String description = descriptionDataEvent.getText().toString();

                        if( close ) {
                            dialog.dismiss();

                            mainDataEvent = new DataEventDTO();
                            mainDataEvent.setName(title);
                            mainDataEvent.setDescription(description);
                            mainDataEvent.setStartDate(new Date());
                            mainDataEvent.setUserId(storage.getUserId());

                            if (!bluetoohManager.isReading()) {
                                recordButton.setText(getString(R.string.bt_stop));

                                sensorDataList = new ArrayList<DartaSensorDTO>();
                                graphData = new GraphView.GraphViewData[0];

                                if (graphSeries != null) {
                                    graphSeries.resetData(graphData);
                                }

                                pulseView.setText(getString(R.string.graph_title) + " ");
                                watingView.setVisibility(View.VISIBLE);

                                bluetoohManager.startReading();

                                setBtWorkStatus(true);
                            }
                        }


                    }
                });


            }
            else {
                recordButton.setText(getString(R.string.bt_record));
                watingView.setVisibility(View.GONE);

                bluetoohManager.cancelReading();

                setBtWorkStatus(false);

                showMessage(getString(R.string.sending_data));
                new SendDataEventHttpRequestTask().execute(mainDataEvent);
            }
        }
    }

    protected class SendDataEventHttpRequestTask extends AsyncTask<DataEventDTO, Void, WebMessage> {

        private DataService dataService = new DataService();

        @Override
        @SuppressWarnings("unchecked")
        protected WebMessage doInBackground(DataEventDTO... dataEventDTOs) {

            WebMessage<DataEventDTO> message = new WebMessage();

            if(dataEventDTOs == null || dataEventDTOs.length <= 0 ) {
                return message;
            }

            message = dataService.saveDataEvent(dataEventDTOs[0]);

            return message;
        }

        @Override
        protected void onPostExecute(WebMessage webMessage) {
            super.onPostExecute(webMessage);

            if( !webMessage.getState().equals(MessageState.OK)) {
                showMessage(webMessage.getMessage());
            }
            else {
                showMessage(getString(R.string.yarr));
            }
        }
    }

    public synchronized DeviceDTO getMainDevice() {
        return mainDevice;
    }

    public synchronized void setMainDevice(DeviceDTO mainDevice) {
        this.mainDevice = mainDevice;
    }
}
