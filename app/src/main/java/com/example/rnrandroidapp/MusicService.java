package com.example.rnrandroidapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MusicService extends Service {

    String tag = "yao";

    public static MediaPlayer mPlayer;

    public boolean isPause = false;

    IServicePlayer.Stub stub = new IServicePlayer.Stub() {

        @Override
        public void play() throws RemoteException {
            mPlayer.start();
        }

        @Override
        public void pause() throws RemoteException {
            mPlayer.pause();
        }

        @Override
        public void stop() throws RemoteException {
            mPlayer.stop();
        }

        @Override
        public int getDuration() throws RemoteException {
            return mPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return mPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int current) throws RemoteException {
            mPlayer.seekTo(current);
        }

        @Override
        public boolean setLoop(boolean loop) throws RemoteException {
            return false;
        }

    };

    @Override
    public void onCreate() {
        Log.i(tag, "MusicService onCreate()");
        mPlayer = MediaPlayer.create(getApplicationContext(), ElfPlayerUtil.getFileinSD("Wind.mp3"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

}
