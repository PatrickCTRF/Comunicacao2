package com.example.patrick.comunicacao2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final int SAY_POSITIVO = 0;
    static final int SAY_NEGATIVO = 1;

    boolean mIsBinded;
    Messenger mMessenger;
    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub
            mIsBinded=false;
            mServiceConnection=null;
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            // TODO Auto-generated method stub
            mIsBinded=true;
            mMessenger = new Messenger(arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mIntent = new Intent();
        mIntent.setAction("com.example.patrick.comunicacao1.RemoteService");
        bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);

        Button mButton = (Button)findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Message msg = Message.obtain(null, SAY_NEGATIVO, 0 , 0);
                try {
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
