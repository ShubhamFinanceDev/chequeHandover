package cheque.handover.services.Repository;

import cheque.handover.services.Entity.ExcelMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetchExcelRepo extends JpaRepository<ExcelMaster,Long> {
    @Query("select d from ExcelMaster d where d.branchName in :branchNames")
    List<ExcelMaster> findAlldetails(List  branchNames);

    @Query("select a from ExcelMaster a where a.applicationNumber =:applicationNumber")
    List<ExcelMaster> findApplicationNumber(String applicationNumber);
}
