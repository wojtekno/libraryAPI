package nowak.wojtek.library.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeService {

    public static Long dateStringToUnixTime(String stringDate) {
        long unixTime;
        if (stringDate == null) return null;

        StringBuffer stringBuffer = new StringBuffer(stringDate);
        if(stringDate.length()==4){
            stringBuffer.append("-01-01");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(stringBuffer.toString());
            unixTime = dt.getTime();
        } catch (ParseException e) {
            return null;
        }
        return unixTime;
    }

    public static String unixTimeToString(Long unixTime){
        Date date = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}
