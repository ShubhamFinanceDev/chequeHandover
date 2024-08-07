package cheque.handover.services.Services;

import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface Service {
    ResponseEntity<?> findUserDetails(String name);

    ResponseEntity<?> allUser();

    List<BranchMaster> findAllBranches();

    List<BranchMaster> findBranchByName(String branchName);

    void saveServiceResult(BranchesResponse branchesResponse, CommonResponse commonResponse, List<BranchMaster> branchByName);

    CommonResponse saveUser(UserDetail userDetail );
    CommonResponse applicationDetailsUpload(MultipartFile file, String emailId);

    ResponseEntity<?> resetPassword(RestPasswordRequest request);

    CommonResponse matchOtp(OtpValidationRequest otpValidationRequest);

    CommonResponse updatePassword(String confirmNewPassword, String newPassword, String emailId);

    FetchExcelData fetchExcelData(String emailId,int pageNo);

    FetchExcelData fetchExcelDataByApplicationNo(String applicationNo, String branchName, int pageNo, String emailId,String status);

    CommonResponse disableChequeStatus();
    CommonResponse chequeStatus(ApplicationFlagUpdate flagUpdate, MultipartFile file) throws IOException, ExecutionException, InterruptedException;

    CommonResponse saveBranch(MultipartFile file, String emailId);
    List<MisReport> fetchReportData(String reportType, String selectedType, String fromDate, String toDate, String selectedDate, String status);
    void generateExcel(HttpServletResponse response, List<MisReport> applicationDetails) throws IOException;

    AllAssignBranchResponse findAssignBranchList(String emailId);

    CommonResponse statusEnableOrDisable(String emailId, String updatedBy);

    ResponseEntity<CommonResponse> userUpdate(Long userId,EditUserDetails inputUpdate);

    boolean checkPattern(String password, String empCode, CommonResponse commonResponse, String emailId);

    CommonResponse updateOldPassword(UpdatePassword updatePassword, UserDetail userDetail);

    void setUpdatePasswordResponse(UpdatePassword updatePassword, UserDetail userDetailOptional, CommonResponse commonResponse);
}
