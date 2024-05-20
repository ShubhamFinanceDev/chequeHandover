package cheque.handover.services.Controller;

import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/invoke-status-procedure")
    public ResponseEntity<CommonResponse>invokeChequeStatus(){
        CommonResponse commonResponse = service.disableChequeStatus();
        if ("0000".equals(commonResponse.getCode())) {
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add-new-branch")
    public ResponseEntity<?> addNewBranch(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(service.saveBranch(file));
    }

    @GetMapping("/generate-mis-report")
    public CommonResponse generateMis() throws IOException {
        CommonResponse commonResponse = service.generateExcel();
        return commonResponse;
    }
}
