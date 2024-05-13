package cheque.handover.services.Model;

import lombok.Data;

@Data
public class ResetNewPassword {
    private String emailId;
    private String newPassword;
    private String confirmNewPassword;
}
