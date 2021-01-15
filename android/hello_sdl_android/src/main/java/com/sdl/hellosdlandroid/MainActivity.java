package com.sdl.hellosdlandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartdevicelink.proxy.rpc.VehicleType;

import java.util.List;

import static com.smartdevicelink.util.SdlAppInfo.deserializeVehicleMake;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ACTION_ALARM = "sdl.router.alarm";
    private static final String ACTION_PROXY_STARTED = "sdl.router.proxy.started";
    private static final String ACTION_PROXY_STOPPED = "sdl.router.proxy.stopped";
    private static final String ACTION_VEHICLE_NOT_SUPPORTED = "sdl.router.proxy.not.supported";
    private static final String ACTION_PROXY_CONNECTED = "sdl.router.proxy.connected";
    private static final String KEY_ALARM_MESSAGE = "message";

    private MainActivityReceiver activityReceiver;
    private Button startServiceButton;
    private Button stopServiceButton;
    private TextView loggerField;
    private ScrollView scrollView;

    private void printLoggerMessage(String message) {
        loggerField.append(message + "\n");
        loggerField.append("====================================\n");

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class MainActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null && action.equals(ACTION_ALARM)) {
                String message = intent.getStringExtra(KEY_ALARM_MESSAGE);
                if (message != null) {
                      int chunkCount = message.length() / 4000;     // integer division
                      for (int i = 0; i <= chunkCount; i++) {
                          int max = 4000 * (i + 1);
                          String output;
                          if (max >= message.length()) {
                              output = message.substring(4000 * i);
                          } else {
                              output = message.substring(4000 * i, max);
                          }

                          if (i == 0) {
                              Log.i(TAG, "#DEMO# " + output);
                          } else {
                              Log.i(TAG, "#DEMO# \t\t" + output);
                          }
                      }
                }
                printLoggerMessage(message);
            }

            if (action != null && action.equals(ACTION_PROXY_STARTED)) {
                Log.i(TAG, "#DEMO# Proxy has been started");
                printLoggerMessage("Proxy has been started");
                stopServiceButton.setEnabled(true);
                startServiceButton.setEnabled(false);
            }

            if (action != null && action.equals(ACTION_PROXY_STOPPED)) {
                Log.i(TAG, "#DEMO# Proxy has been stopped");
                printLoggerMessage("Proxy has been stopped");
                stopServiceButton.setEnabled(false);
                startServiceButton.setEnabled(true);
            }

            if (action != null &&
                    (action.equals(ACTION_VEHICLE_NOT_SUPPORTED) ||  action.equals(ACTION_PROXY_CONNECTED))) {
                final String message = intent.getStringExtra(KEY_ALARM_MESSAGE);
                Log.i(TAG, "#DEMO# " + message);
                printLoggerMessage(message);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(message).setPositiveButton("OK", null);
                        if (action.equals(ACTION_PROXY_CONNECTED)) {
                            builder.setTitle("App is connected!");
                        } else {
                            builder.setTitle("Failed to connect");
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityReceiver = new MainActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ALARM);
        filter.addAction(ACTION_PROXY_STARTED);
        filter.addAction(ACTION_PROXY_STOPPED);
        filter.addAction(ACTION_VEHICLE_NOT_SUPPORTED);
        filter.addAction(ACTION_PROXY_CONNECTED);

        this.registerReceiver(activityReceiver, filter);

        final EditText editor_addr = findViewById(R.id.tcp_ip_address_value);
        editor_addr.setText("127.0.0.1");

        final EditText editor_port = findViewById(R.id.tcp_port_value);
        editor_port.setText("12345");

        final EditText editor_version = findViewById(R.id.protocol_version);
        editor_version.setText("5.3.0");

        startServiceButton = findViewById(R.id.start_service);
        stopServiceButton = findViewById(R.id.stop_service);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If we are connected to a module we want to start our SdlService
                if (BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
                    SdlReceiver.queryForConnectedService(MainActivity.this);
                } else if (BuildConfig.TRANSPORT.equals("TCP")) {
                    Intent proxyIntent = new Intent(MainActivity.this, SdlService.class);

                    proxyIntent.putExtra(SdlService.KEY_TCP_IP, editor_addr.getText().toString());
                    proxyIntent.putExtra(SdlService.KEY_TCP_PORT,
                            Integer.valueOf(editor_port.getText().toString()));
                    proxyIntent.putExtra(SdlService.KEY_PROTOCOL_VERSION,
                            editor_version.getText().toString());

                    hideKeyboardFrom(MainActivity.this, startServiceButton);

                    startService(proxyIntent);
                }
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardFrom(MainActivity.this, stopServiceButton);

                Intent proxyIntent = new Intent(MainActivity.this, SdlService.class);
                stopService(proxyIntent);
            }
        });

        final Button clearConsoleButton = findViewById(R.id.clear_console);
        clearConsoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(MainActivity.this, clearConsoleButton);
                loggerField.setText("");
            }
        });

        XmlResourceParser parser = getResources().getXml(R.xml.supported_vehicle_type);
        List<VehicleType> vehicleMakes = deserializeVehicleMake(parser);

        StringBuilder builder = new StringBuilder();
        builder.append("Supported vehicle(s): ");
        for (VehicleType i : vehicleMakes) {
            builder.append("\"").append(i.toString()).append("\"; ");
        }

        TextView label = findViewById(R.id.top_label);
        label.setText(builder.toString());

        loggerField = findViewById(R.id.log_output);
        scrollView = findViewById(R.id.scroll_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(activityReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
