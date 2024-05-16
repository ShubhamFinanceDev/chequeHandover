package cheque.handover.services.Model;

import cheque.handover.services.Entity.ApplicationDetails;
import lombok.Data;

import java.util.List;

@Data
public class FetchExcelData {
    private CommonResponse commonResponse;
    private List<ApplicationDetails> applicationDetails;
}
