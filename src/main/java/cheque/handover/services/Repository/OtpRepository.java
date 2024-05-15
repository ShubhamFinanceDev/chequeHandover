package cheque.handover.services.Repository;

import cheque.handover.services.Entity.OtpManage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpManage,Long> {
    @Query("select COUNT(otp) from OtpManage otp where otp.emailId=:emailId")
    int countEmailId(String emailId);

    @Modifying
    @Transactional
    @Query("Delete from OtpManage otp where otp.emailId=:emailId")
    void deletePrevOtp(String emailId);

    @Query("select e from OtpManage e where e.emailId=:emailId and e.otpCode=:otpCode")
    Optional<OtpManage> findByEmail(String emailId, String otpCode);
}
