package cheque.handover.services.Repository;

import cheque.handover.services.Entity.AssignBranch;
import cheque.handover.services.Entity.BranchMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchMasterRepo extends JpaRepository<BranchMaster,Long> {
    @Query("select b from BranchMaster b where b.branchName LIKE CONCAT('%', :branchName, '%')")
    List<BranchMaster> findByBranchName(String branchName);

    @Query(value = "SELECT DISTINCT bm.branch_name\n" +
            "FROM branch_master bm\n" +
            "WHERE bm.branch_code IN (\n" +
            "        SELECT ab.branch_code\n" +
            "        FROM user_master um\n" +
            "        JOIN assign_branch ab ON um.user_id = ab.user_id\n" +
            "        WHERE um.email_id = ?1\n" +
            "    );\n", nativeQuery = true)
    List<String> findAssignedBranch(String emailId);

    @Query("select b.branchName from BranchMaster b where b.branchCode in :branchCode")
    List<String> findByBranchCode(List<String> branchCode);


}
