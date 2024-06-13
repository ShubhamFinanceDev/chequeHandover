package cheque.handover.services.Repository;

import cheque.handover.services.Entity.AssignBranch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignBranchRepo extends JpaRepository<AssignBranch,Long> {

}
