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

        StringBuilder baseQuery = new StringBuilder("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n")
                .append("       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n")
                .append("FROM import_data em \n")
                .append("JOIN issued_cheque cs ON em.cheque_id = cs.cheque_id \n")
                .append("WHERE em.cheque_status = 'Y' ");

        boolean isFirstCondition = true;

        switch (reportType.toLowerCase()) {
            case "user-wise":
                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND cs.updated_by = '").append(selectedType).append("' ");
                    isFirstCondition = false;
                }
                break;

            case "branch-wise":
                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND em.branch_name = '").append(selectedType).append("' ");
                    isFirstCondition = false;
                }
                break;

            case "user-wise-fromdate-todate":
                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND cs.updated_by = '").append(selectedType).append("' ");
                    isFirstCondition = false;
                }

                if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                    if (!isFirstCondition) {
                        baseQuery.append("AND ");
                    }
                    baseQuery.append("DATE(cs.updated_date) BETWEEN '").append(fromDate).append("' AND '").append(toDate).append("' ");
                    isFirstCondition = false;
                }
                break;

            case "branch-wise-fromdate-todate":
                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND em.branch_name = '").append(selectedType).append("' ");
                    isFirstCondition = false;
                }

                if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                    if (!isFirstCondition) {
                        baseQuery.append("AND ");
                    }
                    baseQuery.append("DATE(cs.updated_date) BETWEEN '").append(fromDate).append("' AND '").append(toDate).append("' ");
                    isFirstCondition = false;
                }
                break;

            case "daily-report":
                baseQuery.append("AND DATE(cs.updated_date) = CURDATE()");
                break;

            case "fromdate-todate":
                if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                    baseQuery.append("AND DATE(cs.updated_date) BETWEEN '").append(fromDate).append("' AND '").append(toDate).append("'");
                }
                break;

            case "selected-date":
                if (selectedDate != null && !selectedDate.isEmpty()) {
                    baseQuery.append("AND DATE(cs.updated_date) = '").append(selectedDate).append("'");
                }
                break;

            case "issued":
                baseQuery.setLength(0);
                baseQuery.append("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n")
                        .append("       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n")
                        .append("FROM import_data em \n")
                        .append("JOIN issued_cheque cs ON em.cheque_id = cs.cheque_id \n")
                        .append("WHERE em.cheque_status = 'Y' ");
                break;

            case "not-issued":
                baseQuery.setLength(0);
                baseQuery.append("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n")
                        .append("       em.application_number \n")
                        .append("FROM import_data em \n")
                        .append("WHERE em.cheque_status = 'N' ");
                break;

            case "user-wise-selected-date":
                if (selectedType != null && !selectedType.isEmpty() && selectedDate != null && !selectedDate.isEmpty()) {
                    baseQuery.append("AND cs.updated_by = '").append(selectedType).append("' ")
                            .append("AND DATE(cs.updated_date) = '").append(selectedDate).append("'");
                }
                break;

            case "branch-wise-selected-date":
                if (selectedType != null && !selectedType.isEmpty() && selectedDate != null && !selectedDate.isEmpty()) {
                    baseQuery.append("AND em.branch_name = '").append(selectedType).append("' ")
                            .append("AND DATE(cs.updated_date) = '").append(selectedDate).append("'");
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }

        return baseQuery.toString();
    }

}


