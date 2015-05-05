package ai.project.pfa.movemore.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by user on 05/05/2015.
 */
public class StepProvider extends ContentProvider {
    private StepDBHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();



    public static final int STEP = 100;
    public static final int STEP_WITH_TYPE = 101;
    public static final int STEP_WITH_TYPE_AND_DATE = 102;



    private static UriMatcher buildUriMatcher() {

    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new StepDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(StepContract.StepEntry.TABLE_NAME);

        Cursor c = qb.query(mOpenHelper.getReadableDatabase(),
                //colonnes
                projection,
                //where
                selection,
                //oderby
                selectionArgs,
                null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
//        Cursor retCursor;
//        retCursor = mOpenHelper.getReadableDatabase().query(
//                StepContract.NormalEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder
//        );
//        return  retCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
