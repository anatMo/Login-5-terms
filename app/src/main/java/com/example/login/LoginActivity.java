package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    private ImageView main_IMG_x0;
    private ImageView main_IMG_x1;
    private ImageView main_IMG_x2;
    private ImageView main_IMG_x3;
    private ImageView main_IMG_x4;
    private ImageView login_IMG_x5;

    private EditText login_EDTXT_password;

    private AppCompatButton main_BTN_update;
    private AppCompatButton login_BTN_login;

    private boolean isPaused;
    private boolean bluetoothOK;
    private boolean silentModeOK;
    private boolean flightStateOK;
    private boolean autoRotateOK;
    private boolean isAppPausedOK;
    private boolean passwordOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        setUpdateButtonOnClick();
        setLoginButtonOnClick();
        updateUI();


    }

    private void setLoginButtonOnClick() {
        login_BTN_login.setOnClickListener(v -> {
            if(bluetoothOK && silentModeOK && flightStateOK && autoRotateOK && isAppPausedOK && passwordOK){
                login();
            }
        });
    }

    private void login() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        // prevent option to back-click back to here
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    private void setUpdateButtonOnClick() {
        main_BTN_update.setOnClickListener(v -> {
            String pass = login_EDTXT_password.getText().toString();
            Log.d("STRING",pass);
            updateUI();
        });
    }

    private void updateUI() {
        updateBluetoothState();
        updateSilentMode();
        updateFlightState();
        updateAutoRotate();
        updateIsAppPaused();
        checkPassword();
    }

    private void checkPassword() {
        if(login_EDTXT_password.getText().toString().equals("4455")){
            login_IMG_x5.setVisibility(View.INVISIBLE);
            passwordOK = true;
        }
        else{
            login_IMG_x5.setVisibility(View.VISIBLE);
            passwordOK = false;
        }
    }

    private void updateIsAppPaused() {
        if(isPaused){
            main_IMG_x4.setVisibility(View.INVISIBLE);
            isAppPausedOK = true;
        }else{
            main_IMG_x4.setVisibility(View.VISIBLE);
            isAppPausedOK = false;
        }
    }

    private void updateAutoRotate() {
        if (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            // Rotate tMode is disabled
            main_IMG_x3.setVisibility(View.INVISIBLE);
            autoRotateOK = true;
        } else {
            // Rotate Mode is enabled
            main_IMG_x3.setVisibility(View.VISIBLE);
            autoRotateOK = false;
        }
    }


    private void updateFlightState() {
        if(isAirplaneModeOn(this)){
            // FlightMode is enabled
            main_IMG_x2.setVisibility(View.INVISIBLE);
            flightStateOK = true;
        }else{
            // FlightMode is disabled
            main_IMG_x2.setVisibility(View.VISIBLE);
            flightStateOK = false;

        }
    }

    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }


    private void updateSilentMode() {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
            // SilentMode is enabled
            main_IMG_x1.setVisibility(View.INVISIBLE);
            silentModeOK = true;
        }else{
            // SilentMode is disabled
            main_IMG_x1.setVisibility(View.VISIBLE);
            silentModeOK = false;
        }
    }

    private void updateBluetoothState() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled()){
            // Bluetooth is disabled
            main_IMG_x0.setVisibility(View.VISIBLE);
            bluetoothOK = false;
        }else{
            // Bluetooth is enabled
            main_IMG_x0.setVisibility(View.INVISIBLE);
            bluetoothOK = true;
        }
    }





    private void findViews() {
        main_IMG_x0 = findViewById(R.id.login_IMG_x0);
        main_IMG_x1 = findViewById(R.id.login_IMG_x1);
        main_IMG_x2 = findViewById(R.id.login_IMG_x2);
        main_IMG_x3 = findViewById(R.id.login_IMG_x3);
        main_IMG_x4 = findViewById(R.id.login_IMG_x4);
        login_IMG_x5 = findViewById(R.id.login_IMG_x5);

        login_EDTXT_password = findViewById(R.id.login_EDTXT_password);

        main_BTN_update = findViewById(R.id.login_BTN_update);
        login_BTN_login = findViewById(R.id.login_BTN_login);
    }
}