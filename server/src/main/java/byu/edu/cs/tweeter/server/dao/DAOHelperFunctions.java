package byu.edu.cs.tweeter.server.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DAOHelperFunctions {
    public static String createDatePlusPostedBy(String alias, long timestamp) {
        String timestampAsString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(timestamp * 1000));
        return timestampAsString + "@" + alias;
    }

    public static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public static long retrieveTimeStampFromString(String timestampAsString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = formatter.parse(timestampAsString);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
