package cheque.handover.services.Controller;

import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.EditUserDetails;
import cheque.handover.services.Model.RestPasswordRequest;
import cheque.handover.services.Model.UpdatePassword;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Services.Service;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class Admin {
    @Autowired
    private Service service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailRepo userDetailRepo;
    private final Logger logger = LoggerFactory.getLogger(Admin.class);

    @PostMapping("/create-user")
    public ResponseEntity<CommonResponse> userDetail(@RequestBody UserDetail userDetail) {
        CommonResponse commonResponse = new CommonResponse();
        String emailId = userDetail.getEmailId();

        if(service.checkPattern(userDetail.getPassword(),userDetail.getEmpCode(),commonResponse,emailId)){
            commonResponse = service.saveUser(userDetail);
            return ResponseEntity.ok(commonResponse);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
        }
    }

    @PostMapping("/import-data")
    public ResponseEntity<CommonResponse> FileUpload(@RequestParam("file") MultipartFile file,@RequestParam("emailId") String emailId) {
        return ResponseEntity.ok(service.applicationDetailsUpload(file,emailId));
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
    public ResponseEntity<?> addNewBranch(@RequestParam("file") MultipartFile file, @RequestParam("emailId") String emailId) {
        return ResponseEntity.ok(service.saveBranch(file, emailId));
    }

    @PutMapping("/status-update")
    public ResponseEntity<?> enableUser(@RequestParam(name = "emailId")String emailId,@RequestParam(name= "updatedBy")String updatedBy){
        System.out.println(updatedBy);
        return ResponseEntity.ok(service.statusEnableOrDisable(emailId,updatedBy));
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<?> editUserDetails(@PathVariable Long userId, @RequestBody EditUserDetails inputUpdate) {
        CommonResponse commonResponse=new CommonResponse();
        if(!inputUpdate.getEmailId().contains("@shubham.co")) {
            commonResponse.setMsg("Invalid user-email.");
            commonResponse.setCode("1111");

            return ResponseEntity.ok(commonResponse);
        }

        return ResponseEntity.ok(service.userUpdate(userId,inputUpdate).getBody());
    }

    @PutMapping("/update-password")
    public CommonResponse updatePassword(@RequestBody UpdatePassword updatePassword) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            Optional<UserDetail> userDetailOptional = userDetailRepo.findByEmailId(updatePassword.getEmail());
            if (userDetailOptional.isPresent()) {
                service.setUpdatePasswordResponse(updatePassword, userDetailOptional.get(), commonResponse);
                if (commonResponse.getCode() == null) {
                    return service.updateOldPassword(updatePassword, userDetailOptional.get());
                }
            } else {
                commonResponse.setMsg("user not found");
                commonResponse.setCode("1111");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return commonResponse;
    }
}
