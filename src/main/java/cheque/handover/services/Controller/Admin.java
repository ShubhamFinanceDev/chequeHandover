package cheque.handover.services.Controller;

import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.RestPasswordRequest;
import cheque.handover.services.Services.Service;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class Admin {
    @Autowired
    private Service service;

    @PostMapping("/create-user")
    public ResponseEntity<CommonResponse> userDetail(@RequestBody UserDetail userDetail) {
        CommonResponse commonResponse = new CommonResponse();
        String emailId = userDetail.getEmailId();

        if (!emailId.isEmpty() && emailId.contains("@shubham") && !userDetail.getPassword().isEmpty()) {
            commonResponse = service.saveUser(userDetail);
            return ResponseEntity.ok(commonResponse);
        } else {
            commonResponse.setCode("1111");
            commonResponse.setMsg("invalid user-email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
        }
    }

    @PostMapping("/import-data")
    public ResponseEntity<CommonResponse> FileUpload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.applicationDetailsUpload(file));
    }

    @PostMapping("/invoke-status-procedure")
    public ResponseEntity<HashedMap>invokeChequeStatus(@RequestBody RestPasswordRequest inputRequest){
        HashedMap<String,Object> response=new HashedMap<>();
        CommonResponse commonResponse=new CommonResponse();

        if(inputRequest.getEmailId().isEmpty() || inputRequest.getEmailId()== null) {
            commonResponse.setMsg("Required field missing or empty.");
            commonResponse.setCode("1111");
            response.put("commonResponse",commonResponse);
            return ResponseEntity.ok(response);
        }
        System.out.println(inputRequest.getEmailId());
        commonResponse = service.disableChequeStatus();
        response.put("commonResponse",commonResponse);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/add-new-branch")
    public ResponseEntity<?> addNewBranch(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(service.saveBranch(file));
    }

    @PutMapping("/status-update")
    public ResponseEntity<?> enableUser(@RequestParam(name = "emailId")String emailId,@RequestParam(name= "updatedBy")String updatedBy){
        System.out.println(updatedBy);
        return ResponseEntity.ok(service.statusEnableOrDisable(emailId,updatedBy));
    }

}
