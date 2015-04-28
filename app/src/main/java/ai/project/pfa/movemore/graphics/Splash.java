package ai.project.pfa.movemore.graphics;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.Step.ModeNormal;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Intent i = new Intent (this, ModeNormal.class);
        startActivity(i);
    }



}
