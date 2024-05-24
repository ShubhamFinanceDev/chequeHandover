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

    public String misQuery(){
        return "SELECT \n" +
                "    em.applicant_name,\n" +
                "    em.loan_amount,\n" +
                "    em.cheque_amount,\n" +
                "    em.branch_name,\n" +
                "    em.application_number,\n" +
                "    cs.consumer_type,\n" +
                "    cs.ddfs_flag,\n" +
                "    cs.handover_date\n" +
                "FROM \n" +
                "    excel_master em, \n" +
                "    cheque_status cs\n" +
                "WHERE \n" +
                "    em.application_number = cs.application_number\n" +
                "    AND em.cheque_status = 'Y';";
    }

    public static class MisReportRowMapper implements RowMapper<MisReport> {
        @Override
        public MisReport mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            return new MisReport(
                    rs.getString("applicant_name"),
                    rs.getString("branch_name"),
                    rs.getString("application_number"),
                    rs.getLong("cheque_amount"),
                    rs.getString("ddfs_flag"),
                    rs.getString("consumer_type"),
                    rs.getDate("handover_date"),
                    rs.getLong("loan_amount")
            );
        }
    }
}
