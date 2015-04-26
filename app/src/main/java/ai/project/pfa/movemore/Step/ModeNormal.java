package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.TextView;

import java.io.InputStream;

import ai.project.pfa.movemore.R;

public class ModeNormal extends Activity {

    SensorManagerStep manager;
    InputStream ins;

    TextView steps;
    Fuzzy fuzzy;

    Messenger mService = null;
    boolean mBound;

    public ServiceConnection getmConnection() {
        return mConnection;
    }

    public void setmConnection(ServiceConnection mConnection) {
        this.mConnection = mConnection;
    }

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
        bindService(new Intent(this, Vibration.class),mConnection, Context.BIND_AUTO_CREATE);
        manager=new SensorManagerStep((SensorManager)getSystemService(SENSOR_SERVICE),mConnection);

        fuzzy = new Fuzzy(ins);
        manager.setFuzzy(fuzzy);
        manager.StartListening();


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
