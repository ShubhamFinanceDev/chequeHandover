package cheque.handover.services.Repository;

import cheque.handover.services.Entity.UserDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail,Long> {
    @Query("select u from UserDetail u where u.emailId=:userName")
    Optional<UserDetail> findUser(String userName);
    @Query("select e from UserDetail e where e.emailId=:emailId")
   Optional<UserDetail> findByEmailId(String emailId);
    @Transactional
    @Modifying
    @Query("update UserDetail ps set ps.password=:password where ps.emailId=:emailId")
    void updatePassword(String emailId, String password);
}
