package cheque.handover.services.Model;

import cheque.handover.services.Entity.BranchMaster;
import lombok.Data;

import java.util.List;

@Data
public class BranchesResponse {
    private CommonResponse commanResponse;
    private List<BranchMaster> branchMasters;
}
