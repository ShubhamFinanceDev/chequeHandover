package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ApplicationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationDetailsRepo extends JpaRepository<ApplicationDetails, Long> {
}
