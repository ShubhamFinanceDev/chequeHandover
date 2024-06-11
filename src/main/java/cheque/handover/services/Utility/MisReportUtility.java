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

    public String misQuery(String reportType, String selectedType) {


        String baseQuery = "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                "       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n" +
                "FROM import_data em \n" +
                "JOIN issued_cheque cs ON em.cheque_id = cs.issued_id \n" +
                "WHERE em.cheque_status = 'Y' ";

        switch (reportType.toLowerCase()) {
            case "user-wise":
                baseQuery =baseQuery+"AND cs.updated_by = '" + selectedType + "' ";
                break;

            case "branch-wise":
                baseQuery =baseQuery +"AND em.branch_name = '" + selectedType + "' ";
                break;

            case "daily-report":
                baseQuery =baseQuery+ "AND DATE(cs.updated_date) = CURDATE()";
                break;

            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }
        System.out.println(baseQuery);
        return baseQuery;
    }

    public static class MisReportRowMapper implements RowMapper<MisReport> {

        @Override

        public MisReport mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new MisReport(

                    rs.getString("applicant_name"),

                    rs.getString("branch_name"),

                    rs.getString("application_number"),

                    rs.getLong("cheque_amount"),

//                    rs.getString("ddfs_flag"),

                    rs.getString("consumer_type"),

                    rs.getDate("handover_date"),

                    rs.getLong("loan_amount"),

                    rs.getString("updated_by")

            );

        }

    }

}

