package se.chalmers.eda397.pairprogramming.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    private static DateHelper instance;

    public static DateHelper getInstance() {
        if (instance == null){
            instance = new DateHelper();
        }
        return instance;
    }

    public Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return formatter.parse(dateString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String toSimpleDateString(Date date) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return DATE_FORMAT.format(date);
    }


}