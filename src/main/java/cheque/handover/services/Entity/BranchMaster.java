package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "branch_master")
public class BranchMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branch_id")
    private Long branchId;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "state")
    private String state;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "uploaded_by")
    private String uploadedBy;

//    @OneToMany(mappedBy = "branchMaster", cascade = CascadeType.ALL)
//    private List<AssignBranch> assignBranches;
}
