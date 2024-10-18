package cheque.handover.services.Controller;

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
    private Service service;

    @GetMapping("/excel-sheet-builder")
    public ResponseEntity<?> getDataForNewRole(@RequestParam("loanNo")String loanNo, HttpServletResponse response){
        return service.excelExportService(loanNo, response);
    }
}
