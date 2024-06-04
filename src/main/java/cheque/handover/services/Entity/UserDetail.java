package cheque.handover.services.Entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Table(name = "user_master")
@Entity
@Data
public class UserDetail implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "password")
    private String password;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "creation_date")
    private Timestamp createDate;
    @Column(name = "employee_id")
    private String employeeId;
    @PrePersist
    private void onCreate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        createDate = Timestamp.valueOf(localDateTime.format(formatter));
    }

    @OneToOne(mappedBy = "userMaster", cascade = CascadeType.ALL)
    private RoleMaster roleMasters;

    @OneToMany(mappedBy = "userMaster", cascade = CascadeType.ALL)
    private List<AssignBranch> assignBranches;

    @OneToOne(mappedBy = "userMaster", cascade = CascadeType.ALL)
    private LoginDetails loginDetails;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.roleMasters.getRole()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.emailId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.loginDetails.isEnable();
    }
}
