package cheque.handover.services.Utility;

import cheque.handover.services.Model.MisReport;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@Service
public class MisReportUtility {

    public String reportWise(String reportType,String value) {
        System.out.println(reportType + "reportWise in");
        String sql = "SELECT \n" +
                "    em.applicant_name,\n" +
                "    em.loan_amount,\n" +
                "    em.cheque_amount,\n" +
                "    em.branch_name,\n" +
                "    em.application_number,\n" +
                "    cs.consumer_type,\n" +
                "    cs.handover_date,\n" +
                "    cs.updated_by\n" +
                "FROM \n" +
                "    excel_master em\n" +
                "JOIN \n" +
                "    cheque_status cs ON em.application_number = cs.application_number\n" +
                "WHERE \n" +
                "    em.cheque_status = 'Y'\n";

        if (reportType.equals("userWise")) {
             sql+=" AND cs.updated_by = '" + value + "'\n";
        } else if (reportType.equals("branchWise")) {
             sql += " AND  em.branch_name= '" + value + "'\n";
        } else {
             sql += " AND cs.updated_date =CURDATE()\n";
        }
        System.out.println(sql);
        return sql;
    }

    public static class MisReportRowMapper implements RowMapper<MisReport> {
        @Override
        public MisReport mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            return new MisReport(
                    rs.getString("applicant_name"),
                    rs.getString("branch_name"),
                    rs.getString("application_number"),
                    rs.getLong("cheque_amount"),
                    rs.getString("consumer_type"),
                    rs.getDate("handover_date"),
                    rs.getLong("loan_amount")
            );
        }
    }
}
