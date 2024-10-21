package cheque.handover.services.Model;

import jakarta.mail.search.SearchTerm;
import lombok.Data;

@Data
public class ReportUserModel {
    private String emailId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String password;
}