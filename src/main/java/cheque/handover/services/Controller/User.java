package cheque.handover.services.Controller;

import cheque.handover.services.JwtAuthentication.JwtHelper;
import cheque.handover.services.Model.JwtRequest;
import cheque.handover.services.Model.JwtResponse;
import cheque.handover.services.Repository.UserDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class User {

    @Autowired
    private UserDetailRepo userDetailRepo;

    private Logger logger = LoggerFactory.getLogger(User.class);



}
