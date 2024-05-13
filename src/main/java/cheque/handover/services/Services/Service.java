package cheque.handover.services.Services;

import cheque.handover.services.Model.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface Service {

    CommonResponse excelUpload(MultipartFile file);
}
