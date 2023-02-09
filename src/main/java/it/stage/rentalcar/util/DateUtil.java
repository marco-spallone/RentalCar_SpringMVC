package it.stage.rentalcar.util;

import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

    public static LocalDate parseDate(String date){
        if(date!=null){
            return LocalDate.parse(date);
        }
        else return null;
    }
}
