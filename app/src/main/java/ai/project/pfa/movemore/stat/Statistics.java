package ai.project.pfa.movemore.stat;

import android.content.CursorLoader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ai.project.pfa.movemore.R;
import ai.project.pfa.movemore.data.StepContract;
import android.app.LoaderManager;
import android.content.Loader;
import android.widget.TextView;

public class Statistics extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>

{



    private static final int STEP_LOADER = 0;
private static final String[] STEP_COLUMNS = {

        StepContract.StepEntry.TABLE_NAME + "." + StepContract.StepEntry._ID,
        StepContract.StepEntry.COLUMN_TYPE,
        StepContract.StepEntry.COLUMN_DATE,
        StepContract.StepEntry.COLUMN_NBRE_STEPS
};

TextView nbrestepnormal;
    TextView nbrestepsport;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getLoaderManager().initLoader(STEP_LOADER, null, this);
        nbrestepnormal = (TextView) findViewById(R.id.nbrestepnormal);
        nbrestepsport = (TextView) findViewById (R.id.nbrestepsport);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return new CursorLoader(getApplicationContext(),
                    StepContract.StepEntry.CONTENT_URI,
                    STEP_COLUMNS,
                    null,
                    null,
                    null);
    }




    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String s;
        String type;
        int nbrenormal = 0;
        int nbresport= 0;
        int step;
        if(data.getCount()!=0){
            if(data.moveToFirst()){
                do{
                    type = data.getString(data.getColumnIndex(StepContract.StepEntry.COLUMN_TYPE));
                    s=data.getString(data.getColumnIndex(StepContract.StepEntry.COLUMN_NBRE_STEPS));
                    step = Integer.parseInt(s);
                    if (type.equals("1")){
                        nbrenormal = nbrenormal + step;
                    }else {
                        nbresport = nbresport + step;
                    }
                }while(data.moveToNext());
            }
            data.close();
            nbrestepnormal.setText(Integer.toString(nbrenormal));
            nbrestepsport.setText(Integer.toString(nbresport));
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
