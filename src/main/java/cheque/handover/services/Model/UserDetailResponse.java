package cheque.handover.services.Model;

import cheque.handover.services.Entity.RoleMaster;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDetailResponse {

    private CommonResponse commonResponse;
    private Long userId;
    private String firstname;
    private String lastName;
    private String emailId;
    private String mobileNo;
    private String createdBy;
    private boolean enabled;
    private String createDate;
    private Timestamp lastLogin;

    private List<String> assignBranches;
    private String roleMaster;

}
