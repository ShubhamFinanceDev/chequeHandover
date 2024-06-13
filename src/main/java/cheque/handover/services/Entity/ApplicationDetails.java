package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "excel_master")
public class ApplicationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "applicant_name")
    private String applicantName;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "region")
    private String region;
    @Column(name = "hub_name")
    private String hubName;
    @Column(name = "application_number")
    private String applicationNumber;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "loan_amount")
    private Long loanAmount;
    @Column(name = "sanction_date")
    private Date sanctionDate;
    @Column(name = "disbursal_date")
    private Date disbursalDate;
    @Column(name = "cheque_amount")
    private Integer chequeAmount;
    @Column(name = "cheque_status")
    private String chequeStatus;
}