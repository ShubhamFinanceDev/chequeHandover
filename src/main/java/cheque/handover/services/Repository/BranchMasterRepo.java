package cheque.handover.services.Repository;

import cheque.handover.services.Entity.BranchMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchMasterRepo extends JpaRepository<BranchMaster,Long> {
    @Query("select b from BranchMaster b where b.branchName LIKE CONCAT('%', :branchName, '%')")
    List<BranchMaster> findByBranchName(String branchName);

    @Query(value = "SELECT DISTINCT branch_name FROM branch_master WHERE branch_code IN (SELECT branch_code FROM assign_branch WHERE user_id = (SELECT user_id FROM user_master WHERE email_id = ?1))", nativeQuery = true)
    List<String> findAssignedBranch(String emailId);
}
