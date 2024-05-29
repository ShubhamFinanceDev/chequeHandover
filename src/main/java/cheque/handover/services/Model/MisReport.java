package cheque.handover.services.Model;

import lombok.Data;

import java.sql.Date;

@Data
public class MisReport {
    private String applicantName;
    private String branchName;
    private String ApplicationNumber;
    private Long ChequeAmount;
//    private String ddfsFlag;
    private String consumerType;
    private Date handoverDate;
    private Long loanAmount;

    public MisReport(String applicantName, String branchName, String applicationNumber, Long chequeAmount/*, String ddfsFlag*/, String consumerType, Date handoverDate, Long loanAmount) {
        this.applicantName = applicantName;
        this.branchName = branchName;
        ApplicationNumber = applicationNumber;
        ChequeAmount = chequeAmount;
//        this.ddfsFlag = ddfsFlag;
        this.consumerType = consumerType;
        this.handoverDate = handoverDate;
        this.loanAmount = loanAmount;
    }
}