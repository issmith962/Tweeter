package edu.byu.cs.tweeter.Client.view.main;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public abstract class InactivityManagerActivity extends AppCompatActivity {
    public static final long DISCONNECT_TIMEOUT = 1800000; // 30 m * 60 s * 1000 ms

    /*
    FOR TEST PURPOSES! (the timeout above is the proper implementation...
    public static final long DISCONNECT_TIMEOUT = 90000; // 1.5 m * 60 s * 1000 ms
     */


    private static Handler disconnectHandler = new Handler(message -> {
        // todo
        return true;
    });

    private Runnable disconnectCallback = this::reset;

    public abstract void reset();

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}
