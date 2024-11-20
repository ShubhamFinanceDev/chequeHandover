package cheque.handover.services.Repository;

import cheque.handover.services.Entity.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignRole extends JpaRepository<RoleMaster,Long> {
}
