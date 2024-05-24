package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ApplicationDetails;

import jakarta.transaction.Transactional;


import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDetailsRepo extends JpaRepository<ApplicationDetails, Long> {
   @Query("select d from ApplicationDetails d where d.branchName in :branchNames")
    List<ApplicationDetails> findAllDetails(List branchNames, Pageable pageable);
    @Query("select a from ApplicationDetails a where a.applicationNumber =:applicationNo")
    ApplicationDetails findByApplicationNo(String applicationNo);

    @Modifying
    @Transactional
    @Query("update ApplicationDetails ps set ps.chequeStatus = 'Y' where ps.applicationNumber = :applicationNo")
    void updateFlagByApplicationNo(String applicationNo);

//    List<MisReport> findByFlag();

    @Procedure(name = "CHEQUE_STATUS_PROCEDURE")
    void CHEQUE_STATUS_PROCEDURE();

    @Query("select count(d) from ApplicationDetails d where d.branchName in :branchNames")
    long findCount(List<String> branchNames);
    @Query("select count(d) from ApplicationDetails d where d.applicationNumber =:applicationNo")
    long findCountByApplicationNo(String applicationNo);

    @Query("select d from ApplicationDetails d where d.branchName =:branchName")
    ApplicationDetails findByBranch(String branchName);
 @Query("SELECT COUNT(d) FROM ApplicationDetails d WHERE (:applicationNo IS NULL OR d.applicationNumber = :applicationNo) AND (:branchName IS NULL OR d.branchName = :branchName)")
    long findDetailByBranchAndApplication(String branchName, String applicationNo);
@Query("SELECT d FROM ApplicationDetails d WHERE (:applicationNo IS NULL OR d.applicationNumber = :applicationNo) AND (:branchName IS NULL OR d.branchName = :branchName)")
 List<ApplicationDetails> findDetailByBranchAndApplication(String branchName, String applicationNo, Pageable pageable);
}
