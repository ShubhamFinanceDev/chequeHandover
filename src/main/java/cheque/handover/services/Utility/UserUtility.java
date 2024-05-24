package cheque.handover.services.Utility;

import cheque.handover.services.Entity.ApplicationDetails;
import cheque.handover.services.Entity.AssignBranch;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.UserDetailResponse;
import cheque.handover.services.Repository.BranchMasterRepo;
import cheque.handover.services.Repository.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserUtility {
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private BranchMasterRepo branchMasterRepo;

    public List<String> findBranchesByUser(String emailId) {

        Optional<UserDetail> userDetail = userDetailRepo.findByEmailId(emailId);
        UserDetail userDetail1 = userDetail.get();
        List<AssignBranch> assignBranches = userDetail1.getAssignBranches();
        List<String> branchCodes = new ArrayList<>();

        for (AssignBranch branches : assignBranches) {
            branchCodes.add(String.valueOf(branches.getBranchCode()));
        }
            return branchMasterRepo.findBranches(branchCodes);
    }
}
