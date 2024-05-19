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
    @Query("select b.branchName from BranchMaster b where b.branchCode in :branchCodes")
    List<String> findBranches(List<String> branchCodes);
}
