package cheque.handover.services.Utility;

import cheque.handover.services.Entity.ApplicationDetails;
import cheque.handover.services.Entity.AssignBranch;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.UserDetailResponse;
import cheque.handover.services.Repository.BranchMasterRepo;
import cheque.handover.services.Repository.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
            System.out.println(emailId);
    return branchMasterRepo.findAssignedBranch(emailId);
    }

    public List<String> listOfBranch(List<String> branchCode){
        List<String> branchName = new ArrayList<>();
        try
        {
            branchName = branchMasterRepo.findByBranchCode(branchCode);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return branchName;
    }


    public String findByGivenCriteria(String applicationNo, String branchName, String status) {

        StringBuilder query = new StringBuilder();

        boolean isFirstCondition = true;

        if (applicationNo != null && !applicationNo.isEmpty()) {
            query.append("application_number = '").append(applicationNo).append("'");
            isFirstCondition = false;
        }

        if (branchName != null && !branchName.isEmpty()) {
            if (!isFirstCondition) {
                query.append(" AND ");
            }
            query.append("branch_name = '").append(branchName).append("'");
            isFirstCondition = false;
        }

        if (status != null && !status.isEmpty()) {
            if (!isFirstCondition) {
                query.append(" AND ");
            }
            query.append("cheque_status = '").append(status).append("'");
            isFirstCondition = false;
        }
        return query.toString();
    }

    public String pagination(Pageable pageable)
    {
        return " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
    }
}