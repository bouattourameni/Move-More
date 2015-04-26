package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;

import java.io.InputStream;

import ai.project.pfa.movemore.R;

public class ModeNormal extends Activity {

    SensorManagerStep manager;
    InputStream ins;

    TextView steps;
    Fuzzy fuzzy;

    Messenger mService;
    boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mService = new Messenger(iBinder);
            mBound= true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_normal);

        steps=(TextView)findViewById(R.id.steps);
        ins = getResources().openRawResource(getResources().
                getIdentifier("raw/pedometrefcl", "raw", getPackageName()));



    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i=new Intent(this, Vibration.class);
        startService(i);
        boolean b=getApplicationContext().bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        Message msg = Message.obtain();
        try {
            mService.send(msg);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        manager=new SensorManagerStep((SensorManager)getSystemService(SENSOR_SERVICE),mService);

        fuzzy = new Fuzzy(ins);
        manager.setFuzzy(fuzzy);
        manager.StartListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
