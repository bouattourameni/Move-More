package ai.project.pfa.movemore.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
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
        final String authority = StepContract.CONTENT_AUTHORITY;
        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority, StepContract.PATH_STEP, STEP);
        matcher.addURI(authority, StepContract.PATH_STEP + "/date/*", STEP_WITH_TYPE);
        matcher.addURI(authority, StepContract.PATH_STEP + "/#/*", STEP_WITH_TYPE_AND_DATE);


        return matcher;
    }

    private static final String sTypeAndDateSelection =
            StepContract.StepEntry.TABLE_NAME +
                    "." + StepContract.StepEntry.COLUMN_TYPE + " = ? AND " +
                    StepContract.StepEntry.COLUMN_DATE + " >= ? ";
    private static final String sLocationSettingSelection =
            StepContract.StepEntry.TABLE_NAME +
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

            case STEP: {
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
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case STEP_WITH_TYPE_AND_DATE:
                return StepContract.StepEntry.CONTENT_ITEM_TYPE;
            case STEP_WITH_TYPE:
                return StepContract.StepEntry.CONTENT_TYPE;
            case STEP:
                return StepContract.StepEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(StepContract.StepEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(StepContract.StepEntry.COLUMN_DATE);
            values.put(StepContract.StepEntry.COLUMN_DATE, StepContract.normalizeDate(dateValue));
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case STEP: {
                normalizeDate(values);

                long _id = db.insert(StepContract.StepEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = StepContract.StepEntry.buildStepUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";

        rowsDeleted = db.delete(
                StepContract.StepEntry.TABLE_NAME, selection, strings);

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {


        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        normalizeDate(values);
        rowsUpdated = db.update(StepContract.StepEntry.TABLE_NAME, values, s,
                strings);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues value = values[0];
        long dateValue = 0;
        normalizeDate(value);
        if (value.containsKey(StepContract.StepEntry.COLUMN_DATE))
            dateValue = value.getAsLong(StepContract.StepEntry.COLUMN_DATE);
        uri = StepContract.StepEntry.buildStepWithStartDateAndType(dateValue,
                value.getAsString(StepContract.StepEntry.COLUMN_TYPE));
        Cursor cursor = null;
        db.beginTransaction();
        int returnCount = 0;
        long _id = -1;
        try {
            cursor = getStepByTypeAndDate(uri, null, null);
            String stepsnbre = cursor.getString(cursor.getColumnIndex(StepContract.StepEntry.COLUMN_NBRE_STEPS));
            int step = Integer.parseInt(stepsnbre);
            int stepPrecedent = Integer.parseInt(value.getAsString(StepContract.StepEntry.COLUMN_NBRE_STEPS));
            step = step + stepPrecedent;
            value.put(StepContract.StepEntry.COLUMN_NBRE_STEPS, step);
            _id = update(uri, value, null, null);
            db.setTransactionSuccessful();
        } catch (CursorIndexOutOfBoundsException e) {
            try {


                _id = db.insert(StepContract.StepEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;

                }
                db.setTransactionSuccessful();
            } finally {
            }

        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;


    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
