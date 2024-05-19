package cheque.handover.services.Controller;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class Admin {
    @Autowired
    private Service service;


    @PostMapping("/create-user")
    public ResponseEntity<CommonResponse> userdetail(@RequestBody UserDetail userDetail) {
        CommonResponse commonResponse = new CommonResponse();
        String emailId = userDetail.getEmailId();

        if (!emailId.isEmpty() && emailId.contains("@shubham") && !userDetail.getPassword().isEmpty()) {
            commonResponse = service.saveuser(userDetail);
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

    @PostMapping("/invoke-cheque-status")
    public ResponseEntity<CommonResponse>invokeChequeStatus(){
        CommonResponse commonResponse = service.disableChequeStatus();
        if ("0000".equals(commonResponse.getCode())) {
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }

    }
}
