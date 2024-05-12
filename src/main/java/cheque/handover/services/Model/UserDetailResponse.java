package cheque.handover.services.Model;

import cheque.handover.services.Entity.UserDetail;
import lombok.Data;

@Data
public class UserDetailResponse {
    private CommonResponse commonResponse;
    private UserDetail userDetail;
}
