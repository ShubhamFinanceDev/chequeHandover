package cheque.handover.services.Repository;

import cheque.handover.services.Entity.LoginDetails;
import org.apache.poi.ss.formula.eval.RangeEval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LoginDetailsRepo extends JpaRepository<LoginDetails,Long> {
    @Transactional
    @Modifying
    @Query(value = "update login_detail set last_login=CURRENT_TIMESTAMP where email_id=?1",nativeQuery = true)
    void lastLogin(String email);
}
