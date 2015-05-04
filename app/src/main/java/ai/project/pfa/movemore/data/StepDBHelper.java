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
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NORMAL_TABLE = "CREATE TABLE " + StepContract.NormalEntry.TABLE_NAME + " (" +
                StepContract.NormalEntry._ID + " INTEGER PRIMARY KEY," +
                StepContract.NormalEntry.COLUMN_DATE + " TEXT UNIQUE NOT NULL, " +
                StepContract.NormalEntry.COLUMN_NBRE_STEPS + " TEXT NOT NULL, " +
                StepContract.NormalEntry.COLUMN_TEMPS + " REAL NOT NULL, " +
                "UNIQUE (" + StepContract.NormalEntry.COLUMN_DATE +") ON CONFLICT IGNORE"+
                " );";

        final String SQL_CREATE_SPORT_TABLE = "CREATE TABLE " + StepContract.SportEntry.TABLE_NAME + " (" +

                StepContract.SportEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StepContract.SportEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                StepContract.SportEntry.COLUMN_NBRE_STEPS + " TEXT NOT NULL, " +
                StepContract.SportEntry.COLUMN_TEMPS + " INTEGER NOT NULL," +

                " UNIQUE (" + StepContract.SportEntry.COLUMN_DATE
                 + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_NORMAL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SPORT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
