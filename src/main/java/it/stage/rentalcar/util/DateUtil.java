package it.stage.rentalcar.util;

import java.time.LocalDate;

public class DateUtil {

    public static LocalDate parseDate(String date){
        if(date!=null){
            return LocalDate.parse(date);
        }
        else return null;
    }
}
