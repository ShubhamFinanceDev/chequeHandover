package cheque.handover.services.Model;

import cheque.handover.services.Entity.FetchExcelDetail;
import lombok.Data;

import java.util.List;

@Data
public class GetExcelModel {

    CommonResponse commonResponse=new CommonResponse();
private List<FetchExcelDetail> fetchExcelDetails;


}
