package cheque.handover.services.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "assign_branch")
public class AssignBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assign_branch_id")
    private Long assignBranchId;
    @Column(name = "branch_code")
    private Long branchCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;

}
