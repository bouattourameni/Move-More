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
    public static final String PATH_NORMAL = "normal";
    public static final String PATH_SPORT = "sport";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class NormalEntry implements BaseColumns {
        public static final String TABLE_NAME = "normal";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NBRE_STEPS = "nbreSteps";
        public static final String COLUMN_TEMPS = "temps";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NORMAL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NORMAL;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NORMAL;


        public static Uri buildStepUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildStepWithStartDate(
                long startDate) {

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(startDate)).build();
        }
        public static String getStepFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }



    /* Inner class that defines the contents of the weather table */



    public static final class SportEntry implements BaseColumns {

        public static final String TABLE_NAME = "sport";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NBRE_STEPS = "nbreSteps";
        public static final String COLUMN_TEMPS = "temps";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPORT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPORT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPORT;


        public static Uri buildStepUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildStepWithStartDate(
                 long startDate) {

                return CONTENT_URI.buildUpon().appendPath(String.valueOf(startDate)).build();
            }
        public static String getStepFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
        }

    }

