package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.AssignBranch;
import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Entity.RoleMaster;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.BranchesResponse;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.UserDetailResponse;
import cheque.handover.services.Repository.BranchMasterRepo;
import cheque.handover.services.Repository.UserDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Override
    public CommonResponse saveuser(UserDetail userDetail) {
        CommonResponse commonResponse = new CommonResponse();
        UserDetail userDetails=new UserDetail();
        RoleMaster userRoleDetail=new RoleMaster();
        List<AssignBranch> assignBranchList = new ArrayList<>();
        try {
            Optional<UserDetail> emailExist = userDetailRepo.findUser(userDetail.getEmailId());
            if (emailExist.isEmpty()) {
                userDetails.setPassword(passwordEncoder.encode(userDetail.getPassword()));
                userDetails.setEmailId(userDetail.getEmailId());
                userDetails.setPassword(userDetail.getPassword());
                userDetails.setFirstname(userDetail.getFirstname());
                userDetails.setLastName(userDetail.getLastName());
                userDetails.setMobileNo(userDetail.getMobileNo());

                userRoleDetail.setRole(String.valueOf(userDetail.getRoleMasters().getRole()));
                userRoleDetail.setUserMaster(userDetails);
                userDetails.setRoleMasters(userRoleDetail);

                for (AssignBranch branch : userDetail.getAssignBranches()) {
                    branch.setUserMaster(userDetails);
                    assignBranchList.add(branch);
                }
                userDetails.setAssignBranches(assignBranchList);
                userDetailRepo.save(userDetails);
                commonResponse.setCode("0000");
                commonResponse.setMsg("User saved successfully");
            } else {
                commonResponse.setCode("1111");
                commonResponse.setMsg("User already exists");
            }
        } catch (Exception e) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Error: " + e.getMessage());
        }
        return commonResponse;
    }
}