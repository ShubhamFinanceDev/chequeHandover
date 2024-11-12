package cheque.handover.services.Controller;

import cheque.handover.services.ServiceImpl.ReportUserServiceImpl;
import cheque.handover.services.Services.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/report-user")
public class ReportUser {

    @Autowired
    private ReportUserServiceImpl service;

    @PostMapping("/excel-sheet-builder")
    public ResponseEntity<?> getDataForNewRole(@RequestParam(value = "applicationNo",required = false)String applicationNo, @RequestParam(value = "file",required = false)MultipartFile file, HttpServletResponse response){
        return ResponseEntity.ok(service.excelExportService(applicationNo, file, response)).getBody();
    }
}
