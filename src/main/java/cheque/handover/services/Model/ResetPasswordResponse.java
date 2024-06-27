package cheque.handover.services.Model;

import cheque.handover.services.Entity.OtpManage;
import lombok.Data;

@Data
public class ResetPasswordResponse
{
    private CommonResponse commonResponse;

//    private Long otpId;
    private String otpCode;
    private String emailId;
}
