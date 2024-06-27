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

    @Modifying
    @Transactional
    @Query("update ApplicationDetails ps set ps.chequeStatus = 'Y' where ps.applicationNumber = :applicationNo and ps.id=:chequeId")
    void updateFlagByApplicationNo(String applicationNo, Long chequeId);

    @Procedure(name = "CHEQUE_STATUS_PROCEDURE")
    void CHEQUE_STATUS_PROCEDURE();

    @Query("select count(d) from ApplicationDetails d where d.branchName in :branchNames")
    long findCount(List<String> branchNames);

    @Query("select count(d) from ApplicationDetails d where  d.chequeNumber =:chequeNumber")
    int checkChequeNumber(Long chequeNumber);
}
