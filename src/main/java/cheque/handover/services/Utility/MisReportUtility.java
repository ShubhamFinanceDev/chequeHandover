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

    public String misQuery(String emailId, String reportType , String branchName) {

        String query = "";

        switch (reportType.toLowerCase()) {

            case "userwise":

                query = "SELECT em.applicant_name, \n" +
                        "em.loan_amount, \n" +
                        "em.cheque_amount, \n" +
                        "em.branch_name, \n" +
                        "em.application_number, \n" +
                        "cs.consumer_type, \n" +
                        "cs.handover_date, \n" +
                        "cs.updated_by \n" +
                        "FROM excel_master em \n" +
                        "JOIN cheque_status cs ON em.application_number = cs.application_number \n" +
                        "WHERE em.cheque_status = 'Y' \n" +
                        "AND cs.updated_by = '"+ emailId +"'  \n";

                break;

            case "branchwise":
                query =  "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                        "       em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n" +
                        "FROM excel_master em \n" +
                        "JOIN cheque_status cs ON em.application_number = cs.application_number \n" +
                        "WHERE em.cheque_status = 'Y' AND em.branch_name = '" + branchName + "' ";
                break;

            case "daily":

                query = "SELECT em.applicant_name, em.loan_amount, em.cheque_amount, em.branch_name, \n" +
                        "em.application_number, cs.consumer_type, cs.handover_date, cs.updated_by \n" +
                        "FROM excel_master em \n" +
                        "JOIN cheque_status cs ON em.application_number = cs.application_number \n" +
                        "WHERE em.cheque_status = 'Y' AND cs.updated_date = CURDATE()";

                break;

            default:

                throw new IllegalArgumentException("Invalid report type: " + reportType);


        }


        return query;

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

                    rs.getLong("loan_amount")

            );

        }

    }

}

