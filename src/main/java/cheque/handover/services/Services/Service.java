package cheque.handover.services.Services;

import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.BranchesResponse;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.GetExcelModel;
import cheque.handover.services.Model.UserDetailResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Service {
    ResponseEntity<?> findUserDetails(String emailId);

    List<BranchMaster> findAllBranches();

    List<BranchMaster> findBranchByName(String branchName);

    void saveServiceResult(BranchesResponse branchesResponse, CommonResponse commonResponse, List<BranchMaster> branchByName);

    CommonResponse saveuser(UserDetail userDetail );

    GetExcelModel getexcelModel(String emailId);

    GetExcelModel getExcelModelByApplicationNumber(String applicationNumber);
}
