package cheque.handover.services.Utility;

import org.springframework.stereotype.Component;

@Component
public class GetExcelDataForReportUser {

    public String query(String applicationNo) {
        String query = "select * from user";
        return query;
    }
}
