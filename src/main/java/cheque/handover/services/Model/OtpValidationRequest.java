package cheque.handover.services.Model;

import lombok.Data;

@Data
public class OtpValidationRequest {
    private String emailId;
    private String otpCode;
}
