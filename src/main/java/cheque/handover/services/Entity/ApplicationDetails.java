package cheque.handover.services.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "import_data")
public class ApplicationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cheque_id")
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
    private double loanAmount;
    @Column(name = "sanction_date")
    private Date sanctionDate;
    @Column(name = "disbursal_date")
    private Date disbursalDate;
    @Column(name = "cheque_amount")
    private double chequeAmount;
    @Column(name = "cheque_status")
    private String chequeStatus;
    @Column(name = "cheque_number")
    private Long chequeNumber;
    @Column(name="uploaded_by")
    private String uploadBy;
    @Column(name = "upload_date")
    private Timestamp uploadDate;


}