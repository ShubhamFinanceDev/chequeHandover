package cheque.handover.services.Utility;

import cheque.handover.services.Model.MisReport;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Service
public class MisReportUtility {

    public String  misQuery(String reportType, String selectedType, String fromDate, String toDate, String selectedDate) {

        String baseQuery = "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                "       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n" +
                "FROM import_data em \n" +
                "JOIN issued_cheque cs ON em.cheque_id = cs.cheque_id \n" +
                "WHERE em.cheque_status = 'Y' ";

        switch (reportType.toLowerCase()) {
            case "user-wise":
                baseQuery += "AND cs.updated_by = '" + selectedType + "' ";
                break;

            case "branch-wise":
                baseQuery += "AND em.branch_name = '" + selectedType + "' ";
                break;

            case "daily-report":
                baseQuery += "AND DATE(cs.updated_date) = CURDATE()";
                break;

            case "fromdate-todate":
                baseQuery += "AND DATE(cs.updated_date) BETWEEN '" + fromDate + "' AND '" + toDate + "'";
                break;

            case "selected-date":
                baseQuery += "AND DATE(cs.updated_date) = '" + selectedDate + "'";
                break;

            case "issued":
                baseQuery = "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                        "       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n" +
                        "FROM import_data em \n" +
                        "JOIN issued_cheque cs ON em.cheque_id = cs.cheque_id \n" +
                        "WHERE em.cheque_status = 'Y' ";
                break;

            case "not-issued":
                baseQuery = "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                        "       em.application_number " +
                        "FROM import_data em \n" +
                        "WHERE em.cheque_status = 'N'; ";
                break;

            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }

        return baseQuery;
    }

}

