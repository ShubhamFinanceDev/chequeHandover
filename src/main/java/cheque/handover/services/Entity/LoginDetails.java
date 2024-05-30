package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "login_detail")
@Data
public class LoginDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_detail_id")
    private Long id;
    @Column(name = "email_id")
    private String emailId;
    @Column(name="last_login")
    private Timestamp lastLogin;
    @Column(name = "enable")
    private boolean enable;
    @Column(name = "deactivation_date")
    private Timestamp deactivationDate;
    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;

}
