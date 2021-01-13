package com.sdl.hellosdlandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editor_addr = findViewById(R.id.tcp_ip_address_value);
        editor_addr.setText("127.0.0.1");

        final EditText editor_port = findViewById(R.id.tcp_port_value);
        editor_port.setText("12345");

        final Button startServiceButton = findViewById(R.id.start_service);
        final Button stopServiceButton = findViewById(R.id.stop_service);

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

                    startService(proxyIntent);

                    stopServiceButton.setEnabled(true);
                    startServiceButton.setEnabled(false);
                }
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proxyIntent = new Intent(MainActivity.this, SdlService.class);
                stopService(proxyIntent);

                stopServiceButton.setEnabled(false);
                startServiceButton.setEnabled(true);
            }
        });
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
