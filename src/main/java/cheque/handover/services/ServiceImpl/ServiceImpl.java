package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Entity.OtpManage;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.BranchMasterRepo;
import cheque.handover.services.Repository.OtpRepository;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Utility.OtpUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements cheque.handover.services.Services.Service {
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private BranchMasterRepo branchMasterRepo;
    @Autowired
    private OtpUtility otpUtility;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(User.class);

    public ResponseEntity<?> findUserDetails(String emailId) {

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        CommonResponse commonResponse = new CommonResponse();

        try {
            Optional<UserDetail> userDetail = userDetailRepo.findByEmailId(emailId);
            userDetailResponse.setUserDetail(userDetail.get());
            commonResponse.setCode("0000");
            commonResponse.setMsg("Success");
            userDetailResponse.setCommonResponse(commonResponse);
            return ResponseEntity.ok(userDetailResponse);

        } catch (Exception e) {
            commonResponse.setMsg("Data not found");
            commonResponse.setCode("1111");
            logger.info("User not exist");
            return ResponseEntity.ok(commonResponse);
        }
    }

    public List<BranchMaster> findAllBranches() {

        List<BranchMaster> branchMasterList = new ArrayList<>();

        try {
            branchMasterList = branchMasterRepo.findAll();

        } catch (Exception e) {
            logger.info("Technical error.");

        }
        return branchMasterList;
    }


    public List<BranchMaster> findBranchByName(String branchName) {

        List<BranchMaster> branchMasterList = new ArrayList<>();
        try {
            branchMasterList = branchMasterRepo.findByBranchName(branchName);

        } catch (Exception e) {
            logger.info("Data not found");
        }
        return branchMasterList;
    }

    @Override
    public void saveServiceResult(BranchesResponse branchesResponse, CommonResponse commonResponse, List<BranchMaster> branchByName) {
        if (branchByName.isEmpty()) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Data not found");

        } else {
            commonResponse.setCode("0000");
            commonResponse.setMsg("Success");
        }
        branchesResponse.setCommanResponse(commonResponse);
        branchesResponse.setBranchMasters(branchByName);

    }

    public ResponseEntity<?> resetPassword(RestPasswordRequest request){

        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        CommonResponse commonResponse = new CommonResponse();
        try {
            Optional<UserDetail> userDetail = userDetailRepo.findByEmailId(request.getEmailId());
            if (userDetail.isPresent()) {
                UserDetail userDetailData = userDetail.get();
                int otpCode = otpUtility.generateOtp(userDetailData);
                if (otpCode > 0) {
                    OtpManage otpManage = new OtpManage();
                    otpManage.setOtpCode(String.valueOf(otpCode));
                    otpManage.setEmailId(request.getEmailId());
                    otpManage.setExpTime(LocalDateTime.now());
                    otpRepository.save(otpManage);

                    resetPasswordResponse.setOtpId(otpManage.getOtpId());
                    resetPasswordResponse.setOtpCode(String.valueOf(otpCode));
                    resetPasswordResponse.setEmailId(otpManage.getEmailId());

                    commonResponse.setCode("0000");
                    commonResponse.setMsg("otp generated success");

                    otpUtility.sendOtpOnMail(request.getEmailId(),String.valueOf(otpCode));

                    resetPasswordResponse.setCommonResponse(commonResponse);
                    return ResponseEntity.ok(resetPasswordResponse);
                } else {
                    commonResponse.setMsg("Otp did not generated, please try again");
                    commonResponse.setCode("1111");
                    return ResponseEntity.ok(commonResponse);
                }
            } else {
                commonResponse.setMsg("user not found");
                commonResponse.setCode("1111");
                return ResponseEntity.ok(commonResponse);
            }
        }catch (Exception e) {
            commonResponse.setMsg("Technical error.");
            commonResponse.setCode("1111");
            logger.error("Exception", e);
            return ResponseEntity.ok(commonResponse);
        }
    }

    public CommonResponse matchOtp(OtpValidationRequest otpValidationRequest){

        CommonResponse commonResponse = new CommonResponse();
        try {
            Optional<OtpManage> otpManages = otpRepository.findByEmail(otpValidationRequest.getEmailId(), otpValidationRequest.getOtpCode());
            OtpManage otpManage = otpManages.get();
            Duration duration = Duration.between(otpManage.getExpTime(), LocalDateTime.now());
            long betweenTime = duration.toMinutes();
            if (betweenTime <= 1) {
                commonResponse.setMsg(" Otp match Success");
                commonResponse.setCode("0000");
            } else {
                commonResponse.setMsg("Your Otp is expired");
                commonResponse.setCode("1111");
            }
        }catch (Exception e){
            commonResponse.setMsg("Otp or emailId is not correct");
            commonResponse.setCode("1111");
        }
        return commonResponse;
    }

    public CommonResponse updatePassword(String confirmNewPassword, String newPassword, String emailId){
        CommonResponse commonResponse = new CommonResponse();
        if (newPassword.matches(confirmNewPassword)){
            String password = passwordEncoder.encode(confirmNewPassword);
            userDetailRepo.updatePassword(emailId,password);
            commonResponse.setMsg("Your Password is reset");
            commonResponse.setCode("0000");
        }else{
            commonResponse.setMsg("New Password and Confirm Password is not same");
            commonResponse.setCode("1111");
        }
        return commonResponse;
    }
}