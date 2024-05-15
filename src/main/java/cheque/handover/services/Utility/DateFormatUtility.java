package cheque.handover.services.Utility;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateFormatUtility {

        public String changeDateFormate(String chequeDate) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String outputDateString="";
            try {
                Date inputDate = originalFormat.parse(chequeDate);
                SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");

                outputDateString = desiredFormat.format(inputDate);

            } catch (Exception e) {
                System.out.println(e);
            }
            return outputDateString;
        }
}
