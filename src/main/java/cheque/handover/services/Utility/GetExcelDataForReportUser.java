package cheque.handover.services.Utility;

import org.springframework.stereotype.Component;

@Component
public class GetExcelDataForReportUser {

    public String query() {
        String query = "select 8 from newRole";
        return query;
    }
}
