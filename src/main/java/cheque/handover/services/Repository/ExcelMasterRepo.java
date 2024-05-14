package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ExcelMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelMasterRepo extends JpaRepository<ExcelMaster, Long> {
}
