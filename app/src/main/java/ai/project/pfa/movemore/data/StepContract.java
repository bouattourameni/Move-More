package ai.project.pfa.movemore.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by user on 05/05/2015.
 */
public class StepContract {

    public static final String CONTENT_AUTHORITY = "ai.project.pfa.movemore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_STEP = "step";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class StepEntry implements BaseColumns {
        public static final String TABLE_NAME = "step";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NBRE_STEPS = "nbreSteps";
        public static final String COLUMN_TEMPS = "temps";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEP;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEP;


        public static Uri buildStepUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildStepWithStartDate(
                long startDate) {

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(startDate)).build();
        }

        public static Uri buildStepWithStartDateAndType(
                long startDate, String type) {

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(startDate))
                    .appendQueryParameter(COLUMN_TYPE, type)
                    .build();
        }
//        public static Uri buildStepWithStartDateAndType(
//                long startDate, String type) {
//
//            return CONTENT_URI.buildUpon().appendPath(String.valueOf(startDate))
//                    .appendQueryParameter(COLUMN_DATE, type)
//                    .build();
//        }
        public static String getNbreStepFromUri(Uri uri) {
            return uri.getPathSegments().get(3);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(0));
        }
        public static String getTypeFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }









    }

