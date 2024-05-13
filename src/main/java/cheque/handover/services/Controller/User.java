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
}