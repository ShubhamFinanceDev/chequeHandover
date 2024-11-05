package cheque.handover.services.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role_master")
public class RoleMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long role_id;
    @Column(name = "role")
    private String role;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDetail userMaster;
}
