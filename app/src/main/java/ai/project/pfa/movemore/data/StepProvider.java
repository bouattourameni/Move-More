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

    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    {
        qb.setTables(StepContract.StepEntry.TABLE_NAME);
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=StepContract.CONTENT_AUTHORITY;
        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority, StepContract.PATH_STEP, STEP);
        matcher.addURI(authority, StepContract.PATH_STEP + "/date/*", STEP_WITH_TYPE);
        matcher.addURI(authority, StepContract.PATH_STEP + "/#/*", STEP_WITH_TYPE_AND_DATE);


        return matcher;
    }
    private static final String sTypeAndDateSelection =
            StepContract.StepEntry.TABLE_NAME+
                    "." + StepContract.StepEntry.COLUMN_TYPE + " = ? AND " +
                    StepContract.StepEntry.COLUMN_DATE + " >= ? ";
    private static final String sLocationSettingSelection =
            StepContract.StepEntry.TABLE_NAME+
                    "." + StepContract.StepEntry.COLUMN_TYPE + " = ? ";

    private Cursor getStep(Uri uri, String[] projection, String sortOrder) {
     return qb.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }
    private Cursor getStepByType(
            Uri uri, String[] projection, String sortOrder) {

        String type = StepContract.StepEntry.getTypeFromUri(uri);

        return qb.query(mOpenHelper.getReadableDatabase(),
                projection,
                sTypeAndDateSelection,
                new String[]{type},
                null,
                null,
                sortOrder
        );
    }
    private Cursor getStepByTypeAndDate(
            Uri uri, String[] projection, String sortOrder) {

        long date = StepContract.StepEntry.getDateFromUri(uri);
        String type = StepContract.StepEntry.getTypeFromUri(uri);

        return qb.query(mOpenHelper.getReadableDatabase(),
                projection,
                sTypeAndDateSelection,
                new String[]{type, Long.toString(date)},
                null,
                null,
                sortOrder
        );
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new StepDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case STEP:
            {
                retCursor = getStep(uri, projection, sortOrder);
                break;
            }

            case STEP_WITH_TYPE: {
                retCursor = getStepByType(uri, projection, sortOrder);
                break;
            }

            case STEP_WITH_TYPE_AND_DATE: {
                retCursor = getStepByTypeAndDate(uri, projection, sortOrder);

                break;

            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
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
