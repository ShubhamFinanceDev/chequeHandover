package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ApplicationDetails;

import cheque.handover.services.Model.FetchExcelData;
import cheque.handover.services.Model.MisReport;
import jakarta.transaction.Transactional;


import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.nio.charset.MalformedInputException;
import java.util.List;

@Repository
public interface ApplicationDetailsRepo extends JpaRepository<ApplicationDetails, Long> {
   @Query("select d from ApplicationDetails d where d.branchName in :branchNames")
    List<ApplicationDetails> findAlldetails(List branchNames, Pageable pageable);
    @Query("select a from ApplicationDetails a where a.applicationNumber =:applicationNo")
    List<ApplicationDetails> findByApplicationNo(String applicationNo, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update ApplicationDetails ps set ps.chequeStatus = 'Y' where ps.applicationNumber = :applicationNo")
    void updateFlagByApplicationNo(String applicationNo);

//    List<MisReport> findByFlag();

    @Procedure(name = "CHEQUE_STATUS_PROCEDURE")
    void CHEQUE_STATUS_PROCEDURE();

    @Query("select count(d) from ApplicationDetails d where d.branchName in :branchNames")
    long findCount(List<String> branchNames);
    @Query("select count(d) from ApplicationDetails d where d.applicationNumber in :applicationNo")
    long findCountByApplicationNo(String applicationNo);

}
