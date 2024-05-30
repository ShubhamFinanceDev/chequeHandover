package cheque.handover.services.Repository;

import cheque.handover.services.Entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail,Long> {
    @Query("select u from UserDetail u where u.emailId=:userName")
    Optional<UserDetail> findUser(String userName);
    @Query("select e from UserDetail e where e.firstname LIKE :name%")
    List<UserDetail> findByFirstName(String name);
    @Transactional
    @Modifying
    @Query("update UserDetail ps set ps.password=:password where ps.emailId=:emailId")
    void updatePassword(String emailId, String password);

     @Query("select e from UserDetail e where e.emailId=:emailId")
    Optional<UserDetail> findByEmailId(String emailId);
//    @Modifying
//    @Transactional
//    @Query("UPDATE UserDetail u\n" +
//            "SET u.enabled = CASE\n" +
//            "                   WHEN u.enabled = true THEN false\n" +
//            "                   ELSE true\n" +
//            "                 END,\n" +
//            "    u.createDate = current_timestamp, u.createdBy=:updatedBy \n"+
//            "WHERE u.emailId = :emailId")
//    void enableUserStatus(String emailId,String updatedBy);
}