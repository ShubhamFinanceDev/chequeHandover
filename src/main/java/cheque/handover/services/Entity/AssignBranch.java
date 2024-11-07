package cheque.handover.services.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "assign_branch")
public class AssignBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_branch_id")
    private Long assignBranchId;
    @Column(name = "branch_code")
    private String branchCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "branch_id")
//    private BranchMaster branchMaster;
}
