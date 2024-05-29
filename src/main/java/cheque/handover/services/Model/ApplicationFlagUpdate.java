package cheque.handover.services.Model;

import lombok.Data;

import java.sql.Date;

@Data
public class ApplicationFlagUpdate {
    private String consumerType;
    private Date date;
    private String applicationNo;
    private String emailId;
}
