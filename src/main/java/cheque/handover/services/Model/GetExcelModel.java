package cheque.handover.services.Model;

import cheque.handover.services.Entity.ExcelMaster;
import lombok.Data;

import java.util.List;

@Data
public class GetExcelModel {

    CommonResponse commonResponse=new CommonResponse();
private List<ExcelMaster> fetchExcelDetails;


}
