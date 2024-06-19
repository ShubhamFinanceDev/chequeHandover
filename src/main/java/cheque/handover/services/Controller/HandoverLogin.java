package cheque.handover.services.Controller;

import cheque.handover.services.JwtAuthentication.JwtHelper;
import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.LoginDetailsRepo;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Services.Service;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;

@RestController
@RequestMapping("/handover-service")
@CrossOrigin
public class HandoverLogin {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private LoginDetailsRepo loginDetailsRepo;
    @Autowired
    private Service service;
    private Logger logger = LoggerFactory.getLogger(HandoverLogin.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

//        System.out.println("email+"+request.getEmailId());
//        System.out.println(passwordEncoder.encode(request.getPassword()));

        final boolean[] userRole = new boolean[1];

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailId());

        this.doAuthenticate(request.getEmailId(), request.getPassword());

        String token = this.helper.generateToken(userDetails);
        userDetails.getAuthorities().forEach(grantedAuthority -> {
                String roleName= String.valueOf(grantedAuthority);
              userRole[0] =(roleName.equals("ROLE_ADMIN")) ? true : false;
            System.out.println(roleName);
        });

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .role(userRole[0])
                .emailId(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
            loginDetailsRepo.lastLogin(email);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @PostMapping("/generate-otp")
    public ResponseEntity<?> resetUserPassword(@RequestBody RestPasswordRequest request){
        return ResponseEntity.ok(service.resetPassword(request).getBody());
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> otpValidation(@RequestBody OtpValidationRequest otpValidationRequest){
        CommonResponse commonResponse = new CommonResponse();
        if (otpValidationRequest.getOtpCode() != null && otpValidationRequest.getEmailId() != null){
            return ResponseEntity.ok(service.matchOtp(otpValidationRequest));
        }else{
            commonResponse.setCode("1111");
            commonResponse.setMsg("Required field ");
            return ResponseEntity.ok(commonResponse);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> restUserPassword(@RequestBody ResetNewPassword reset){
        return ResponseEntity.ok(service.updatePassword(reset.getConfirmNewPassword(), reset.getNewPassword(), reset.getEmailId()));
    }

}