package ai.project.pfa.movemore.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 05/05/2015.
 */
public class StepDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "step.db";

    public StepDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STEP_TABLE = "CREATE TABLE " + StepContract.StepEntry.TABLE_NAME + " (" +
                StepContract.StepEntry._ID + " INTEGER PRIMARY KEY," +
                StepContract.StepEntry.COLUMN_DATE + " TEXT UNIQUE NOT NULL, " +
                StepContract.StepEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                StepContract.StepEntry.COLUMN_NBRE_STEPS + " TEXT NOT NULL, " +
                StepContract.StepEntry.COLUMN_TEMPS + " TEXT NOT NULL, " +

                "UNIQUE (" + StepContract.StepEntry.COLUMN_DATE + ") ON CONFLICT IGNORE " +

                "UNIQUE (" + StepContract.StepEntry.COLUMN_TYPE + ") ON CONFLICT IGNORE" +
                " );";


        sqLiteDatabase.execSQL(SQL_CREATE_STEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StepContract.StepEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
