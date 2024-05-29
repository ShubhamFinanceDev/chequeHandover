package cheque.handover.services.Model;

import lombok.Data;

import java.util.List;

@Data
public class AllAssignBranchResponse {
    private String msg;
    private String code;
    private List<String> assignBranchList;
}
