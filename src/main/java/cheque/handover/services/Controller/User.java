package cheque.handover.services.Controller;

import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Services.Service;
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
    public ResponseEntity<?> userData(@RequestParam(name = "emailId") String emailId) {
        return ResponseEntity.ok(service.findUserDetails(emailId).getBody());
    }

    @GetMapping("/get-all-branches")
    public ResponseEntity<?> allBranches(@RequestParam(name = "branchName", required = false) String branchName) {
        CommonResponse commonResponse = new CommonResponse();
        BranchesResponse branchesResponse = new BranchesResponse();

        if (branchName != null) {
            service.saveServiceResult(branchesResponse, commonResponse, service.findBranchByName(branchName));
        } else {
            service.saveServiceResult(branchesResponse, commonResponse, service.findAllBranches());

        }
        return ResponseEntity.ok(branchesResponse);
    }

    @GetMapping("/fetch-excel-data")
    public ResponseEntity<?> excelDataByUser(@RequestParam(name = "emailId")String emailId,@RequestParam(name = "applicationNo",required = false)String applicationNo){
        if (applicationNo == null || applicationNo.isEmpty()) {
            return ResponseEntity.ok(service.fetchExcelData(emailId));
        }else {
            return ResponseEntity.ok(service.fetchExcelDataByApplicationNo(applicationNo));
        }
    }

    @PostMapping("/update-application-flag")
    public ResponseEntity<?> updateFlag(@RequestParam("file") MultipartFile file, @RequestParam("consumerType") String consumerType, @RequestParam("date") Date date, @RequestParam("applicationNo") String applicationNo) throws IOException, ExecutionException, InterruptedException {
        ApplicationFlagUpdate applicationFlagUpdate=new ApplicationFlagUpdate();
        applicationFlagUpdate.setApplicationNo(applicationNo);
        applicationFlagUpdate.setConsumerType(consumerType);
        applicationFlagUpdate.setDate(date);
        return ResponseEntity.ok(service.chequeStatus(applicationFlagUpdate,file));
    }


}