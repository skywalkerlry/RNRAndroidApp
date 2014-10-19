package com.example.rnrandroidapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerActivity extends Activity {

    public static final int PLAY = 1;
    public static final int PAUSE = 2;

    ImageButton imageButtonFavorite;
    ImageButton imageButtonNext;
    ImageButton imageButtonPlay;
    ImageButton imageButtonPre;
    ImageButton imageButtonRepeat;
    SeekBar musicSeekBar;

    IServicePlayer iPlayer;
    boolean isPlaying = false;
    boolean isLoop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        imageButtonFavorite = (ImageButton) findViewById(R.id.imageButtonFavorite);
        imageButtonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        imageButtonPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
        imageButtonPre = (ImageButton) findViewById(R.id.imageButtonPre);
        imageButtonRepeat = (ImageButton) findViewById(R.id.imageButtonRepeat);
        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);

        bindService(new Intent(PlayerActivity.this, MusicService.class), conn, Context.BIND_AUTO_CREATE);
        startService(new Intent(PlayerActivity.this, MusicService.class));

        imageButtonPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("yao", "imageButtonPlay -> onClick");

                if (!isPlaying) {
                    try {
                        iPlayer.play();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    imageButtonPlay.setBackgroundResource(R.drawable.pause_button);
                    isPlaying = true;

                } else {
                    try {
                        iPlayer.pause();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    imageButtonPlay.setBackgroundResource(R.drawable.play_button);
                    isPlaying = false;
                }
            }
        });

        musicSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (iPlayer != null) {
                    try {
                        iPlayer.seekTo(seekBar.getProgress());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        handler.post(updateThread);
    }

    private ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("yao", "ServiceConnection -> onServiceConnected");
            iPlayer = IServicePlayer.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName className) {
        };
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        };
    };

    private Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            if (iPlayer != null) {
                try {
                    musicSeekBar.setMax(iPlayer.getDuration());
                    musicSeekBar.setProgress(iPlayer.getCurrentPosition());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            handler.post(updateThread);
        }
    };

}