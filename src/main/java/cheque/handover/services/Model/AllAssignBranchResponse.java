package cheque.handover.services.Model;

import lombok.Data;

import java.util.List;

@Data
public class AllAssignBranchResponse {
    private CommonResponse commonResponse;
    private List<String> assignBranchList;
}
