package cheque.handover.services.Model;

import cheque.handover.services.Entity.RoleMaster;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDetailResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNo;
    private String encodedMobileNo;
    private String createdBy;
    private boolean enabled;
    private String createDate;
    private Timestamp lastLogin;
    private List<String> branchesCode;
    private List<String> assignBranches;
    private List<String> roleMaster;
    private String empCode;

}
