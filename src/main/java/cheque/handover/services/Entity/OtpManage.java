package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "otp_manage")
public class OtpManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;
    @Column(name = "otp_code")
    private String otpCode;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "exp_time")
    private LocalDateTime expTime;
}
