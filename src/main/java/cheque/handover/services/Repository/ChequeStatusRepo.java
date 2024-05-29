package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ChequeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChequeStatusRepo extends JpaRepository<ChequeStatus,Long> {

    @Query("select a from ChequeStatus a where a.applicationNo =:applicationNo")
    ChequeStatus findByApplicationNo(String applicationNo);
}
