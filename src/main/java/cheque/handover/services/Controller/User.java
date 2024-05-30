package cheque.handover.services.Controller;

import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Services.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class User {

    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private Service service;

    private Logger logger = LoggerFactory.getLogger(User.class);

    @GetMapping("/get-user-details")
    public ResponseEntity<?> userData(@RequestParam(name = "name",required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.ok(service.allUser().getBody());
        }else {
            return ResponseEntity.ok(service.findUserDetails(name).getBody());
        }
    }

    @GetMapping("/get-all-branches")
    public ResponseEntity<?> allBranches(@RequestParam(name = "branchName", required = false) String branchName) {
        CommonResponse commonResponse = new CommonResponse();
        BranchesResponse branchesResponse = new BranchesResponse();

        if (branchName != null && !branchName.isEmpty()) {
            service.saveServiceResult(branchesResponse, commonResponse, service.findBranchByName(branchName));
        } else {
            service.saveServiceResult(branchesResponse, commonResponse, service.findAllBranches());

        }
        return ResponseEntity.ok(branchesResponse);
    }

    @GetMapping("/fetch-excel-data")
    public ResponseEntity<?> excelDataByUser(@RequestParam(name = "emailId")String emailId,@RequestParam(name = "applicationNo",required = false)String applicationNo,@RequestParam(name = "pageNo") int pageNo,@RequestParam(name = "branchName",required = false)String branchName){
        if ((branchName != null && !branchName.isEmpty()) || (applicationNo != null && !applicationNo.isEmpty())) {
            return ResponseEntity.ok(service.fetchExcelDataByApplicationNo(applicationNo, branchName, pageNo, emailId));
        } else {
            return ResponseEntity.ok(service.fetchExcelData(emailId, pageNo));
        }
    }

    @PostMapping("/update-application-flag")
    public ResponseEntity<?> updateFlag(@RequestParam("file") MultipartFile file, @RequestParam("consumerType") String consumerType, @RequestParam("date") Date date, @RequestParam("applicationNo") String applicationNo, @RequestParam(value = "emailId",required = false) String emailId ) throws IOException, ExecutionException, InterruptedException {
        ApplicationFlagUpdate applicationFlagUpdate=new ApplicationFlagUpdate();
        applicationFlagUpdate.setApplicationNo(applicationNo);
        applicationFlagUpdate.setConsumerType(consumerType);
        applicationFlagUpdate.setDate(date);
        applicationFlagUpdate.setUpdatedBy(emailId);
        return ResponseEntity.ok(service.chequeStatus(applicationFlagUpdate,file));
    }
    @GetMapping("/generate-mis-report")
    public String generateMis(HttpServletResponse response, @RequestParam String emailId, @RequestParam String reportType, @RequestParam(required = false) String branchName ) throws IOException {
        System.out.println(emailId);
        System.out.println(reportType);
        service.generateExcel(response, emailId, reportType, branchName);
        return "Success";
    }
    @GetMapping("/get-list-of-assign-branches")
    public ResponseEntity<AllAssignBranchResponse> getAllAssignBranch(@RequestParam(name = "emailId")String emailId){
        return ResponseEntity.ok(service.findAssignBranchList(emailId));
    }
}