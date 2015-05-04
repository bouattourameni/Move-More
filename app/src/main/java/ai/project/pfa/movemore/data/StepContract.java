package ai.project.pfa.movemore.data;

import android.net.Uri;
import android.text.format.Time;

/**
 * Created by user on 05/05/2015.
 */
public class StepContract {

    public static final String CONTENT_AUTHORITY = "ai.project.pfa.movemore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_Normal = "normal";
    public static final String PATH_Sport = "sport";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }
}
