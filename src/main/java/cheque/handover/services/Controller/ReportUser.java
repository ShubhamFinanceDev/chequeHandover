package cheque.handover.services.Controller;

import cheque.handover.services.ServiceImpl.ReportUserServiceImpl;
import cheque.handover.services.Services.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report-user")
public class ReportUser {

    @Autowired
    private ReportUserServiceImpl service;

    @GetMapping("/excel-sheet-builder")
    public ResponseEntity<?> getDataForNewRole(@RequestParam(value = "applicationNo")String applicationNo, HttpServletResponse response){
        return ResponseEntity.ok(service.excelExportService(applicationNo, response)).getBody();
    }
}