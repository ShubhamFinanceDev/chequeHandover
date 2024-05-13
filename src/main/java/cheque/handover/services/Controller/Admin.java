package cheque.handover.services.Controller;

import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Admin {
    @Autowired
    private Service service;

    @PostMapping("/createUser")
    public CommonResponse userdetail(@RequestBody UserDetail userDetail/*@RequestParam(value = "created_By",required = false)String createdBy*/) {
        CommonResponse commonResponse = new CommonResponse();
        String emailId = userDetail.getEmailId();
        String password = userDetail.getPassword();
        if (emailId != null && emailId.contains("@shubham") && userDetail.getPassword() != null) {
            commonResponse = service.saveuser(userDetail);
        } else {
            commonResponse.setCode("1111");
            commonResponse.setMsg("invalid user-email");
        }
        return commonResponse;
    }
}
