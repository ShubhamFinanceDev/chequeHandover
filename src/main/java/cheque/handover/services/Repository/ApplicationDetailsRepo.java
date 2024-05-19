package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ApplicationDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationDetailsRepo extends JpaRepository<ApplicationDetails, Long> {
   @Query("select d from ApplicationDetails d where d.branchName in :branchNames")
    List<ApplicationDetails> findAlldetails(List branchNames);
    @Query("select a from ApplicationDetails a where a.applicationNumber =:applicationNo")
    List<ApplicationDetails> findByApplicationNo(String applicationNo);


@Procedure(name = "CHEQUE_STATUS_PROCEDURE")
    void CHEQUE_STATUS_PROCEDURE();

}
