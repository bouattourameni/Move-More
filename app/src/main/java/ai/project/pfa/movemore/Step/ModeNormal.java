package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import ai.project.pfa.movemore.R;

public class ModeNormal extends Activity {

    SensorManagerStep manager;
    InputStream ins;

    public static TextView steps;
    Fuzzy fuzzy;
    int type;
    Messenger mService;
    boolean mBound;
    Chronometer ch;
    ImageView nrmlImg;
    ImageView sprImg;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            mBound= true;
            manager=new SensorManagerStep((SensorManager)getSystemService(SENSOR_SERVICE),mService);

            fuzzy = new Fuzzy(ins);
            manager.setFuzzy(fuzzy);
            manager.StartListening();
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
        Bundle b=getIntent().getExtras();
        nrmlImg =(ImageView)findViewById(R.id.NrmImg);
        sprImg= (ImageView)findViewById(R.id.SprImg);
        type=b.getInt("type");

        if (type == 0) {
            sprImg.setVisibility(View.GONE);
            nrmlImg.setVisibility(View.VISIBLE);

        }
        else
        {
            nrmlImg.setVisibility(View.GONE);
            sprImg.setVisibility(View.VISIBLE);
        }
        ch= (Chronometer) findViewById(R.id.chronometer);
        steps=(TextView)findViewById(R.id.steps);
        ch.start();
        ins = getResources().openRawResource(getResources().
                getIdentifier("raw/pedometrefcl", "raw", getPackageName()));
        Intent i=new Intent(this, Vibration.class);
        startService(i);
        boolean b1 = bindService(i, mConnection, 0);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        manager.EndListening();
        ch.stop();
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
