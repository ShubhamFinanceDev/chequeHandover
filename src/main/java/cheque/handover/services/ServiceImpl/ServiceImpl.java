package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.*;
import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.*;
import cheque.handover.services.Utility.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ServiceImpl implements cheque.handover.services.Services.Service {
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private BranchMasterRepo branchMasterRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ExcelUtilityValidation excelUtilityValidation;
    @Autowired
    private DateFormatUtility dateFormatUtility;
    @Autowired
    private ApplicationDetailsRepo applicationDetailsRepo;
    @Autowired
    private OtpUtility otpUtility;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private DdfsUtility ddfsUtility;
    @Autowired
    private ChequeStatusRepo chequeStatusRepo;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MisReportUtility misReportUtility;
    @Autowired
    private UserUtility userUtility;
    @Autowired
    private LoginDetailsRepo loginDetailsRepo;


    private final Logger logger = LoggerFactory.getLogger(User.class);

    public ResponseEntity<?> findUserDetails(String name) {

        List<UserDetailResponse> userDetailResponseList = new ArrayList<>();
        AllUserDetailList allUserDetailList = new AllUserDetailList();
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<UserDetail> userDetail = userDetailRepo.findByFirstName(name);
            setUserDetail(userDetail, userDetailResponseList, commonResponse, allUserDetailList);

            return ResponseEntity.ok(allUserDetailList);

        } catch (Exception e) {
            commonResponse.setMsg("Data not found");
            commonResponse.setCode("1111");
            logger.info("User not exist");
            allUserDetailList.setCommonResponse(commonResponse);
            return ResponseEntity.ok(allUserDetailList);
        }
    }

    public ResponseEntity<?> allUser() {

        CommonResponse commonResponse = new CommonResponse();
        AllUserDetailList allUserDetailList = new AllUserDetailList();
        List<UserDetailResponse> userDetailResponseList = new ArrayList<>();

        try {
            List<UserDetail> userDetail = userDetailRepo.findAll();
            if (!userDetail.isEmpty()) {
                setUserDetail(userDetail, userDetailResponseList, commonResponse, allUserDetailList);
                return ResponseEntity.ok(allUserDetailList);
            } else {
                commonResponse.setCode("0000");
                commonResponse.setMsg("Data found successfully");
            }
        } catch (Exception e) {
            commonResponse.setMsg("Data not found or Technical issue :" + e.getMessage());
            commonResponse.setCode("1111");
        }
        return ResponseEntity.ok(commonResponse);

    }


    private void setUserDetail(List<UserDetail> userDetail, List<UserDetailResponse> userDetailResponseList, CommonResponse commonResponse, AllUserDetailList allUserDetailList) {

        for (UserDetail userData : userDetail) {
            UserDetailResponse userDetails = new UserDetailResponse();

            userDetails.setUserId(userData.getUserId());
            userDetails.setFirstname(userData.getFirstname());
            userDetails.setLastName(userData.getLastName());
            userDetails.setEmailId(userData.getEmailId());
            userDetails.setMobileNo("******"+userData.getMobileNo().substring(userData.getMobileNo().length()-4,userData.getMobileNo().length()));
            userDetails.setCreatedBy(userData.getCreatedBy());
            userDetails.setEnabled(userData.isEnabled());
            userDetails.setCreateDate(String.valueOf(userData.getCreateDate()));
            List<Long> assignBranches = new ArrayList<>();
            if (!userData.getAssignBranches().isEmpty()) {
                userData.getAssignBranches().forEach(branch -> {

                    assignBranches.add(branch.getBranchCode());
                });
            }
            userDetails.setAssignBranches(userUtility.listOfBranch(assignBranches));
            userDetails.setRoleMaster(userData.getRoleMasters().getRole());
            if (userData.getLoginDetails() != null) {
                userDetails.setLastLogin(userData.getLoginDetails().getLastLogin());
            }
            userDetailResponseList.add(userDetails);
        }

        commonResponse.setCode("0000");
        commonResponse.setMsg("Data found successfully");
        allUserDetailList.setCommonResponse(commonResponse);
        allUserDetailList.setUserDetailResponse(userDetailResponseList);
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
    public CommonResponse saveUser(UserDetail userDetail) {
        CommonResponse commonResponse = new CommonResponse();
        UserDetail userDetails = new UserDetail();
        RoleMaster userRoleDetail = new RoleMaster();
        LoginDetails loginDetails = new LoginDetails();

        List<AssignBranch> assignBranchList = new ArrayList<>();
        try {
            Optional<UserDetail> emailExist = userDetailRepo.findUser(userDetail.getEmailId());
            if (emailExist.isEmpty()) {
                userDetails.setPassword(passwordEncoder.encode(userDetail.getPassword()));
                userDetails.setEmailId(userDetail.getEmailId());
                userDetails.setFirstname(userDetail.getFirstname());
                userDetails.setLastName(userDetail.getLastName());
                userDetails.setMobileNo(userDetail.getMobileNo());
                loginDetails.setEmailId(userDetail.getEmailId());
                userDetails.setCreatedBy(userDetail.getCreatedBy());
                logger.info("createdBy : " + userDetails.getCreatedBy());
                loginDetails.setEnable(true);
                loginDetails.setUserMaster(userDetails);
                userDetails.setLoginDetails(loginDetails);


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

    @Override
    public CommonResponse applicationDetailsUpload(MultipartFile file) {

        CommonResponse commonResponse = new CommonResponse();
        List<ApplicationDetails> applicationDetails = new ArrayList<>();
        int count = 0;
        String errorMsg = "";

        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next(); // Skipping header row
            boolean fileFormat = excelUtilityValidation.ExcelFileFormat(headerRow);

            System.out.println(fileFormat);

            if (fileFormat) {

                System.out.println("file format matched");

                while (rowIterator.hasNext()) {
                    count++;
                    Row row = rowIterator.next();
                    ApplicationDetails applicationDetails1 = new ApplicationDetails();

                    for (int i = 0; i < 10; i++) {
                        Cell cell = row.getCell(i);
                        errorMsg = (cell == null || cell.getCellType() == CellType.BLANK) ? "file upload error due to row no " + (row.getRowNum() + 1) + " is empty" : "";


                        if (errorMsg.isEmpty()) {
                            switch (i) {
                                case 0:
                                    applicationDetails1.setApplicantName(row.getCell(0).toString());
                                    ;
                                    break;
                                case 1:
                                    applicationDetails1.setBranchName(row.getCell(1).toString());
                                    ;
                                    break;
                                case 2:
                                    applicationDetails1.setRegion(row.getCell(2).toString());
                                    ;
                                    break;
                                case 3:
                                    applicationDetails1.setHubName(row.getCell(3).toString());
                                    ;
                                    break;
                                case 4:
                                    applicationDetails1.setApplicationNumber(row.getCell(4).toString());
                                    ;
                                    break;
                                case 5:
                                    applicationDetails1.setProductName(row.getCell(5).toString());
                                    ;
                                    break;
                                case 6:
                                    applicationDetails1.setLoanAmount(Long.valueOf(row.getCell(6).toString().replace(".0", "")));
                                    ;
                                    break;
                                case 7:
                                    applicationDetails1.setSanctionDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(7).toString())));
                                    ;
                                    break;
                                case 8:
                                    applicationDetails1.setDisbursalDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(8).toString())));
                                    ;
                                    break;
                                case 9:
                                    long chequeAmount = Long.parseLong(row.getCell(9).toString().replace(".0", ""));
                                    if (chequeAmount < 0) {
                                        errorMsg = "Cheque amount cannot be negative for row " + (row.getRowNum() + 1);
                                    } else {
                                        applicationDetails1.setChequeAmount(chequeAmount);
                                    }
                                    break;
                            }
                        }
                        if (!errorMsg.isEmpty())
                            break;
                    }
                    if (!errorMsg.isEmpty())
                        break;
                    applicationDetails1.setChequeStatus("N");
                    applicationDetails.add(applicationDetails1);
                }

                if (errorMsg.isEmpty()) {
                    applicationDetailsRepo.saveAll(applicationDetails);
                    commonResponse.setMsg("file uploaded successfully " + applicationDetails.size() + " row uploaded.");
                    commonResponse.setCode("0000");
                } else {
                    commonResponse.setCode("1111");
                    commonResponse.setMsg(errorMsg);
                }
            } else {
                errorMsg = "file format is not matching or technical issue.";
                commonResponse.setCode("1111");
                commonResponse.setMsg(errorMsg);
            }
        } catch (Exception e) {
            errorMsg = "file is empty or technical issue.";
            commonResponse.setCode("1111");
            commonResponse.setMsg(errorMsg);

            System.out.println(e);
        }

        System.out.println(errorMsg);
        return commonResponse;
    }

    public ResponseEntity<?> resetPassword(RestPasswordRequest request) {

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

                    otpUtility.sendOtpOnMail(request.getEmailId(), String.valueOf(otpCode));

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
        } catch (Exception e) {
            commonResponse.setMsg("Technical error.");
            commonResponse.setCode("1111");
            logger.error("Exception", e);
            return ResponseEntity.ok(commonResponse);
        }
    }

    public CommonResponse matchOtp(OtpValidationRequest otpValidationRequest) {

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
        } catch (Exception e) {
            commonResponse.setMsg("Otp or emailId is not correct");
            commonResponse.setCode("1111");
        }
        return commonResponse;
    }

    public CommonResponse updatePassword(String confirmNewPassword, String newPassword, String emailId) {
        CommonResponse commonResponse = new CommonResponse();
        if (newPassword.equals(confirmNewPassword)) {
            String password = passwordEncoder.encode(confirmNewPassword);
            userDetailRepo.updatePassword(emailId, password);
            commonResponse.setMsg("Your Password is reset");
            commonResponse.setCode("0000");
        } else {
            commonResponse.setMsg("New Password and Confirm Password is not same");
            commonResponse.setCode("1111");
        }
        return commonResponse;
    }

    public FetchExcelData fetchExcelData(String emailId, int pageNo) {

        FetchExcelData fetchExcelData = new FetchExcelData();
        CommonResponse commonResponse = new CommonResponse();
        List<ApplicationDetails> applicationDetails = new ArrayList<>();
        int pageSize = 100;
        long totalCount = 0;

        try {
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            List<String> branchNames = userUtility.findBranchesByUser(emailId);
            applicationDetails = applicationDetailsRepo.findAllDetails(branchNames, pageable);
            totalCount = applicationDetailsRepo.findCount(branchNames);

            if (!applicationDetails.isEmpty()) {
                commonResponse.setMsg("Data found successfully");
                commonResponse.setCode("0000");
                fetchExcelData.setTotalCount(totalCount);
                fetchExcelData.setNextPage(pageNo <= (totalCount / pageSize));
                fetchExcelData.setApplicationDetails(applicationDetails);
                fetchExcelData.setCommonResponse(commonResponse);
                return fetchExcelData;
            } else {
                commonResponse.setCode("1111");
                commonResponse.setMsg("Data not found");
                fetchExcelData.setCommonResponse(commonResponse);
            }
        } catch (Exception e) {
            System.out.println(("Technical issue :" + e.getMessage()));
        }
        return fetchExcelData;
    }

    public FetchExcelData fetchExcelDataByApplicationNo(String applicationNo, String branchName, int pageNo, String emailId) {
        CommonResponse commonResponse = new CommonResponse();
        FetchExcelData fetchExcelData = new FetchExcelData();
        List<ApplicationDetails> applicationDetails = new ArrayList<>();
        int pageSize = 100;
        long totalCount = 0;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<String> assignBranches = userUtility.findBranchesByUser(emailId);
        try {
            for (String branch : assignBranches) {
                if (branch.equals(branchName)) ;
                {
                    if ((applicationNo != null && !applicationNo.isEmpty()) && (branchName != null && !branchName.isEmpty())) {
                        applicationDetails = applicationDetailsRepo.findDetailByBranchAndApplication(branchName, applicationNo, pageable);
                        totalCount = applicationDetailsRepo.findDetailByBranchAndApplicationCount(branchName, applicationNo);
                    } else {
                        applicationDetails = (applicationNo != null && !applicationNo.isEmpty()) ? applicationDetailsRepo.findDetailByApplication(applicationNo, assignBranches, pageable) : applicationDetailsRepo.findDetailByBranch(branchName, pageable);
                        totalCount = (applicationNo != null && !applicationNo.isEmpty()) ? applicationDetailsRepo.findDetailByApplicationCount(applicationNo, assignBranches) : applicationDetailsRepo.findDetailByBranchCount(branchName);
                        System.out.println("total" + totalCount);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (!applicationDetails.isEmpty()) {
            commonResponse.setMsg("Data found successfully");
            commonResponse.setCode("0000");
            fetchExcelData.setTotalCount(totalCount);
            fetchExcelData.setNextPage(pageNo <= (totalCount / pageSize));
            fetchExcelData.setApplicationDetails(applicationDetails);
            fetchExcelData.setCommonResponse(commonResponse);

            fetchExcelData.setApplicationDetails(applicationDetails);
            fetchExcelData.setCommonResponse(commonResponse);
        } else {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Data not found");

            fetchExcelData.setCommonResponse(commonResponse);
        }

        return fetchExcelData;
    }

    @Override
    public CommonResponse disableChequeStatus() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            applicationDetailsRepo.CHEQUE_STATUS_PROCEDURE();
            commonResponse.setCode("0000");
            commonResponse.setMsg("SUCCESS.");
            return commonResponse;
        } catch (Exception e) {
            commonResponse.setMsg("Technical issue :" + e);
            commonResponse.setCode("1111");
            return commonResponse;
        }
    }


    public CommonResponse chequeStatus(ApplicationFlagUpdate flagUpdate, MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        CommonResponse commonResponse = new CommonResponse();
        ChequeStatus chequeStatus = new ChequeStatus();

        CompletableFuture<Boolean> response = ddfsUtility.callDDFSApi(file, flagUpdate.getApplicationNo());
        System.out.println("DDfs response" + response);
        chequeStatus.setApplicationNo(flagUpdate.getApplicationNo());
        chequeStatus.setDdfsFlag("Y");
        chequeStatus.setConsumerType(flagUpdate.getConsumerType());
        chequeStatus.setHandoverDate(flagUpdate.getDate());
        chequeStatus.setUpdatedBy(flagUpdate.getUpdatedBy());
        chequeStatus.setUpdatedDate(Date.valueOf(LocalDate.now()));

        chequeStatusRepo.save(chequeStatus);

        applicationDetailsRepo.updateFlagByApplicationNo(flagUpdate.getApplicationNo());
        commonResponse.setMsg("Data save successfully");
        commonResponse.setCode("0000");

        return commonResponse;
    }

    public CommonResponse saveBranch(MultipartFile file) {
        CommonResponse commonResponse = new CommonResponse();
        List<BranchMaster> branchMasterList = new ArrayList<>();
        int count = 0;
        String errorMsg = "";

        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();
            boolean fileFormat = excelUtilityValidation.branchAddValidation(headerRow);

            System.out.println(fileFormat);

            if (fileFormat) {

                while (rowIterator.hasNext()) {
                    count++;
                    Row row = rowIterator.next();
                    BranchMaster branchMaster = new BranchMaster();

                    for (int i = 0; i < 3; i++) {
                        Cell cell = row.getCell(i);
                        errorMsg = (cell == null || cell.getCellType() == CellType.BLANK) ? "file upload error due to row no " + (row.getRowNum() + 1) + " is empty" : "";

                        if (errorMsg.isEmpty()) {
                            switch (i) {
                                case 0:
                                    branchMaster.setBranchName(row.getCell(0).toString());
                                    break;
                                case 1:
                                    branchMaster.setBranchCode(row.getCell(1).toString().replace(".0", ""));
                                    if (branchMasterRepo.existsByBranchCode(branchMaster.getBranchCode())) {
                                        errorMsg = "Branch code '" + branchMaster.getBranchCode() + "' already exists.";
                                        break;
                                    }
                                    break;
                                case 2:
                                    branchMaster.setState(row.getCell(2).toString());
                                    break;
                            }
                        }
                        if (!errorMsg.isEmpty())
                            break;
                    }
                    if (!errorMsg.isEmpty())
                        break;

                    branchMasterList.add(branchMaster);
                }
                if (errorMsg.isEmpty()) {
                    branchMasterRepo.saveAll(branchMasterList);
                    commonResponse.setCode("0000");
                    commonResponse.setMsg("file uploaded successfully " + branchMasterList.size() + "row uploaded.");
                } else {
                    commonResponse.setMsg(errorMsg);
                    commonResponse.setCode("1111");
                }
            } else {
                commonResponse.setMsg("File format not match");
                commonResponse.setCode("1111");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commonResponse;
    }

    public HttpServletResponse generateExcel(HttpServletResponse response, String emailId, String reportType , String branchName) throws IOException {

        List<MisReport> applicationDetails = new ArrayList<>();

        applicationDetails = jdbcTemplate.query(misReportUtility.misQuery(emailId, reportType, branchName), new MisReportUtility.MisReportRowMapper());

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("MIS_Report");
        int rowCount = 0;

        String[] header = {"ApplicationNumber", "BranchName", "ApplicantName", "ChequeAmount", "ConsumerType", "HandoverDate", "LoanAmount"};
        Row headerRow = sheet.createRow(rowCount++);
        int cellCount = 0;

        for (String headerValue : header) {
            headerRow.createCell(cellCount++).setCellValue(headerValue);
        }
        for (MisReport details : applicationDetails) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(details.getApplicationNumber());
            row.createCell(1).setCellValue(details.getBranchName());
            row.createCell(2).setCellValue(details.getApplicantName());
            row.createCell(3).setCellValue(details.getChequeAmount());
            row.createCell(4).setCellValue(details.getConsumerType());
            row.createCell(5).setCellValue(details.getHandoverDate().toString());
            row.createCell(6).setCellValue(details.getLoanAmount());
        }

        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=MIS_Report.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    public AllAssignBranchResponse findAssignBranchList(String emailId) {
        AllAssignBranchResponse assignBranchResponse = new AllAssignBranchResponse();
        List<String> userAssignBranch = userUtility.findBranchesByUser(emailId);
        if (!userAssignBranch.isEmpty()) {
            assignBranchResponse.setCode("0000");
            assignBranchResponse.setMsg("Data found successfully");
            assignBranchResponse.setAssignBranchList(userAssignBranch);
        } else {
            assignBranchResponse.setCode("1111");
            assignBranchResponse.setMsg("No branch assign to you");
        }
        return assignBranchResponse;
    }

    public CommonResponse statusEnableOrDisable(String emailId, String updatedBy) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            Optional<UserDetail> userDetail = userDetailRepo.findByEmailId(emailId);
            if (userDetail.isPresent()) {

                UserDetail userDetail1 = userDetail.get();
                if (userDetail1.getLoginDetails() != null) {
                    userDetail1.getLoginDetails().setEnable(!userDetail1.getLoginDetails().isEnable());
                    userDetail1.getLoginDetails().setUpdatedBy(updatedBy);
                    userDetail1.getLoginDetails().setDeactivationDate(Timestamp.valueOf(LocalDateTime.now()));
                    loginDetailsRepo.save(userDetail1.getLoginDetails());
                }
                commonResponse.setCode("0000");
                commonResponse.setMsg("Status update successfully");
            }
        } catch (Exception e) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("User not found or Technical issue " + e.getMessage());
        }
        return commonResponse;
    }
}