package cheque.handover.services.Model;

import cheque.handover.services.Entity.AssignBranch;
import cheque.handover.services.Entity.RoleMaster;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class EditUserDetails {

    private String firstName;
    private String lastName;
    private String mobileNo;
    private List<AssignBranch> assignBranches;
    private RoleMaster roleMaster;
}
