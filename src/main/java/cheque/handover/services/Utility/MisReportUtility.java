package cheque.handover.services.Utility;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MisReportUtility {
    public String misQuery(String reportType, String selectedType, String fromDate, String toDate, String selectedDate, String status) {
        StringBuilder baseQuery = new StringBuilder("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name,em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by FROM import_data em JOIN issued_cheque cs ON em.cheque_id = cs.cheque_id \n");
        switch (reportType.toLowerCase()) {
            case "user-wise":

                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND cs.updated_by = '").append(selectedType).append("' ");
                }
                if (status.equalsIgnoreCase("not-issued")) {
                    baseQuery = new StringBuilder("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, em.application_number FROM import_data em WHERE em.cheque_status = 'N' AND em.uploaded_by = '").append(selectedType).append("' ");
                }
                fromAndToSelectedDate(status, fromDate, toDate, selectedDate, baseQuery);
                break;

            case "branch-wise":

                if (selectedType != null && !selectedType.isEmpty()) {
                    baseQuery.append("AND em.branch_name = '").append(selectedType).append("' ");
                } else if (status.equalsIgnoreCase("not-issued")) {
                    baseQuery = new StringBuilder("SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, em.application_number FROM import_data em WHERE em.cheque_status = 'N' AND em.branch_name= '").append(selectedType).append("' ");
                }
                fromAndToSelectedDate(status, fromDate, toDate, selectedDate, baseQuery);
                break;

            case "daily-report":
                fromAndToSelectedDate(status, fromDate, toDate, selectedDate, baseQuery);
                break;

            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }

        System.out.println("Final SQL Query: " + baseQuery);
        return baseQuery.toString();
    }


    private void fromAndToSelectedDate(String status, String fromDate, String toDate, String selectedDate, StringBuilder baseQuery) {
        if (status.equalsIgnoreCase("issued")) {
            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                baseQuery.append("AND DATE(cs.updated_date) BETWEEN '").append(fromDate).append("' AND '").append(toDate).append("' ");
            }
            if (!selectedDate.isEmpty()) {
                baseQuery.append("AND DATE(cs.updated_date) = '").append(selectedDate).append("' ");
            }
        }
        if (status.isEmpty()) {
            baseQuery.append("AND DATE(cs.updated_date) = '").append(selectedDate).append("' ");
        }
    }


}