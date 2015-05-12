package ai.project.pfa.movemore.Step;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.data.StepContract;
import ai.project.pfa.movemore.graphics.Home;

public class ModeStep extends Activity {

    public static TextView steps;
    SensorManagerStep manager;
    InputStream ins;
    Fuzzy fuzzy;
    int type;
    Messenger mService;
    boolean mBound;
    Chronometer ch;
    ImageView nrmlImg;
    ImageView sprImg;

    Button start;
    Button stop;
    Date date = new Date();
    Time time;

    CharSequence storeChrono = "00:00";


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            mBound = true;

            manager = new SensorManagerStep((SensorManager) getSystemService(SENSOR_SERVICE), mService);


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
        Bundle b = getIntent().getExtras();
        nrmlImg = (ImageView) findViewById(R.id.NrmImg);
        sprImg = (ImageView) findViewById(R.id.SprImg);
        type = b.getInt("type");
        ch = (Chronometer) findViewById(R.id.chronometer);
        steps = (TextView) findViewById(R.id.steps);


        Intent i = new Intent(this, Vibration.class);


        time = new Time();
        time.setToNow();
        date.setDate(time.monthDay);
        date.setMonth(time.month);
        date.setYear(time.year);

        if (type == 1) {
            sprImg.setVisibility(View.GONE);
            nrmlImg.setVisibility(View.VISIBLE);

        } else {
            nrmlImg.setVisibility(View.GONE);
            sprImg.setVisibility(View.VISIBLE);
        }

        startService(i);
        boolean b1 = bindService(i, mConnection, 0);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         ins = getResources().openRawResource(getResources().
                                                 getIdentifier("raw/pedometrefcl", "raw", getPackageName()));
                                         if (mBound) {
                                             ch.start();
                                             fuzzy = new Fuzzy(ins);
                                             manager.setFuzzy(fuzzy);
                                             manager.StartListening();
                                         }
                                     }
                                 }

        );

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ch.stop();
                manager.EndListening();
                unbindService(mConnection);
                steps.setText("0");
                createOrUpdateData();
                mBound = false;
                Home.currentType = -1;

            }
        });


    }

    public void createOrUpdateData() {
        Vector<ContentValues> cVVector = new Vector<ContentValues>(4);
        ContentValues StepValues = new ContentValues();

        StepValues.put(StepContract.StepEntry.COLUMN_DATE, date.getTime());
        StepValues.put(StepContract.StepEntry.COLUMN_TYPE, Integer.toString(type));
        StepValues.put(StepContract.StepEntry.COLUMN_NBRE_STEPS, String.valueOf(steps.getText()));
        StepValues.put(StepContract.StepEntry.COLUMN_TEMPS, String.valueOf(ch.getText()));

        cVVector.add(StepValues);
        int inserted = 0;
        // add to database
        if (cVVector.size() > 0) {
            // Student: call bulkInsert to add the weatherEntries to the database here
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            inserted = getApplication().getContentResolver().bulkInsert(StepContract.StepEntry.CONTENT_URI, cvArray);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
   
    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            manager.EndListening();
            ch.stop();
            Home.currentType = -1;
        }
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
