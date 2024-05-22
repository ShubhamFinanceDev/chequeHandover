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
        return "SELECT em.applicant_name,em.loan_amount,em.cheque_amount,em.branch_name,em.application_number,\n" +
                "    cs.consumer_type,cs.ddfs_flag,cs.handover_date FROM \n" +
                "    excel_master em, \n" +
                "    cheque_status cs WHERE \n" +
                "    em.application_number = cs.application_number;";
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
