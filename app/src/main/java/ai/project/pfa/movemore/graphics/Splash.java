package ai.project.pfa.movemore.graphics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.Step.ModeNormal;
import ai.project.pfa.movemore.stat.Statistics;

public class Splash extends Activity implements AdapterView.OnItemSelectedListener {

    Button buttonNormal;
    Button buttonSportif;
    Button buttonStat;
    Button buttonAbout;

    Intent intentModes;
    Intent intentStat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        buttonNormal=(Button)findViewById(R.id.buttonNormal);
        buttonSportif=(Button)findViewById(R.id.buttonSportif);
        buttonStat=(Button)findViewById(R.id.buttonStatistique);
        buttonAbout=(Button)findViewById(R.id.buttonAboutMe);
        intentModes = new Intent(this, ModeNormal.class);
        intentStat = new Intent (this, Statistics.class);
        buttonNormal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intentModes.putExtra("type", 0);
                startActivity(intentModes);
            }
        });
        buttonSportif.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intentModes.putExtra("type", 1);
                startActivity(intentModes);
            }
        });
        buttonStat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(intentStat);
            }
        });
        buttonAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getApplication())
                        .setTitle("About Me")
                        .setMessage("Ameni Bouattour #Study_Jams")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })

                        .setIcon(android.R.drawable.btn_star)
                        .show();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}