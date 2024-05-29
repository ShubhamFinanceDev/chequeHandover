package cheque.handover.services.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

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
    private Timestamp timestamp;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;

}
