package net.sinlo.bookmanage.bookmanage.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hello on 2018/6/21.
 */

public class DateUtil {
    public static  String getDataString(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");

        String dateString = simpleDateFormat.format(date);
        return dateString;
    }
    public static  String getAl10Day(){
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        calendar2.add(Calendar.DATE, 10);
        String three_days_after = sdf2.format(calendar2.getTime());
       return three_days_after;
    }

}
