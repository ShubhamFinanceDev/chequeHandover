package cheque.handover.services.Controller;

import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
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
    @GetMapping("/Get-excel-data")
public ResponseEntity<?> excelDataGet(@RequestParam(name = "email_id")String emailId,@RequestParam(name = "application_Number",required = false)String applicationNumber){
if (applicationNumber == null || applicationNumber.isEmpty()){
return ResponseEntity.ok(service.getexcelModel(emailId));

}else{
return ResponseEntity.ok(service.getExcelModelByApplicationNumber(applicationNumber));
}
    }


}