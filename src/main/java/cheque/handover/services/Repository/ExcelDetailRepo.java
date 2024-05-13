package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ExcelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelDetailRepo extends JpaRepository<ExcelDetail, Long> {
}
