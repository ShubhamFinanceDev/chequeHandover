package cheque.handover.services.Repository;

import cheque.handover.services.Entity.FetchExcelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetchExcelRepo extends JpaRepository<FetchExcelDetail,Long> {
    @Query("select d from FetchExcelDetail d where d.branchName in :branchNames")
    List<FetchExcelDetail> findAlldetails(List  branchNames);

    @Query("select a from FetchExcelDetail a where a.applicationNumber =:applicationNumber")
    List<FetchExcelDetail> findApplicationNumber(String applicationNumber);
}
