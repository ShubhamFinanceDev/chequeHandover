package cheque.handover.services.Controller;

import cheque.handover.services.ServiceImpl.ReportUserServiceImpl;
import cheque.handover.services.Services.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/report-user")
public class ReportUser {

    private static final Logger log = LoggerFactory.getLogger(ReportUser.class);
    @Autowired
    private ReportUserServiceImpl service;

    @PostMapping("/excel-sheet-builder")
    public ResponseEntity<?> getDataForNewRole(@RequestParam(value = "applicationNo",required = false)String applicationNo, @RequestParam(value = "file",required = false)MultipartFile file, HttpServletResponse response){
        log.info("Legality report invoked {} for", file!=null ? "Multi application":applicationNo);
        return ResponseEntity.ok(service.excelExportService(applicationNo, file, response)).getBody();
    }
}
