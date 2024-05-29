package cheque.handover.services.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MisReport {
    private String applicantName;
    private String branchName;
    private String ApplicationNumber;
    private Long ChequeAmount;
//    private String ddfsFlag;
    private String consumerType;
    private Date handoverDate;
    private Long loanAmount;


}