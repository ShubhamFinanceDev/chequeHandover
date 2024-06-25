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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private AssignBranchRepo assignBranchRepo;


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
            userDetails.setFirstName(userData.getFirstName());
            userDetails.setLastName(userData.getLastName());
            userDetails.setEmailId(userData.getEmailId());
            userDetails.setMobileNo("******" + userData.getMobileNo().substring(userData.getMobileNo().length() - 4));
            userDetails.setEncodedMobileNo(Base64.getEncoder().encodeToString(userData.getMobileNo().getBytes()));
            String fullNames = userDetailRepo.findFullNameByEmailId(userData.getCreatedBy());
            if (!fullNames.isEmpty()) {
                userDetails.setCreatedBy(fullNames);
            }
            userDetails.setEnabled(userData.isEnabled());
            userDetails.setCreateDate(String.valueOf(userData.getCreateDate()));
            List<String> assignBranches = new ArrayList<>();

            if (!userData.getAssignBranches().isEmpty()) {
                userData.getAssignBranches().forEach(branch -> {

                    assignBranches.add(branch.getBranchCode());
                });
            }
            List<String> allBranches = userUtility.listOfBranch(assignBranches);
            if (assignBranches.equals("ALL")) {
                userDetails.setAssignBranches(allBranches);
            } else {
                userUtility.listOfBranch(assignBranches);
            }

            userDetails.setAssignBranches(userUtility.listOfBranch(assignBranches));
            userDetails.setBranchesCode(assignBranches);
            userDetails.setRoleMaster(userData.getRoleMasters().getRole());
            if (userData.getLoginDetails() != null) {
                userDetails.setLastLogin(userData.getLoginDetails().getLastLogin());
            }
            userDetails.setEmpCode(userData.getEmpCode());
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
            Optional<UserDetail> emailExist = userDetailRepo.findUserByEmailEmp(userDetail.getEmailId(), userDetail.getEmpCode());
            if (emailExist.isEmpty()) {
                userDetails.setPassword(passwordEncoder.encode(userDetail.getPassword()));
                userDetails.setEmailId(userDetail.getEmailId());
                userDetails.setFirstName(userDetail.getFirstName());
                userDetails.setLastName(userDetail.getLastName());
                userDetails.setMobileNo(userDetail.getMobileNo());
                loginDetails.setEmailId(userDetail.getEmailId());
                userDetails.setCreatedBy(userDetail.getCreatedBy());
                userDetails.setEmpCode(userDetail.getEmpCode());
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
                commonResponse.setMsg("User Email-Id or Employee-Id already exists.");
            }
        } catch (Exception e) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Error: " + e.getMessage());
        }
        return commonResponse;
    }

    @Override
    public CommonResponse applicationDetailsUpload(MultipartFile file, String emailId) {

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

                    for (int i = 0; i < 11; i++) {
                        Cell cell = row.getCell(i);
                        errorMsg = (cell == null || cell.getCellType() == CellType.BLANK) ? "file upload error due to row no " + (row.getRowNum() + 1) + " is empty" : "";


                        if (errorMsg.isEmpty()) {
                            switch (i) {
                                case 0:
                                    applicationDetails1.setApplicantName(row.getCell(0).toString());

                                    break;
                                case 1:
                                    applicationDetails1.setBranchName(row.getCell(1).toString());

                                    break;
                                case 2:
                                    applicationDetails1.setRegion(row.getCell(2).toString());

                                    break;
                                case 3:
                                    applicationDetails1.setHubName(row.getCell(3).toString());

                                    break;
                                case 4:
                                    applicationDetails1.setApplicationNumber(row.getCell(4).toString());

                                    break;
                                case 5:
                                    applicationDetails1.setProductName(row.getCell(5).toString());

                                    break;
                                case 6:
                                    String loanAmount = row.getCell(6).toString();
                                    errorMsg = excelUtilityValidation.chequeAmount(loanAmount, row.getRowNum(), "loan");
                                    if (errorMsg.isEmpty())
                                        applicationDetails1.setLoanAmount(excelUtilityValidation.decimalFormat(loanAmount));

                                    break;
                                case 7:
                                    applicationDetails1.setSanctionDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(7).toString())));
                                    break;
                                case 8:
                                    applicationDetails1.setDisbursalDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(8).toString())));

                                    break;
                                case 9:
                                    String chequeAmount = row.getCell(9).toString();
                                    errorMsg = excelUtilityValidation.chequeAmount(chequeAmount, row.getRowNum(), "Cheque");
                                    if (errorMsg.isEmpty())
                                        applicationDetails1.setChequeAmount(excelUtilityValidation.decimalFormat(chequeAmount));

                                    break;
                                case 10:
                                    String chequeNumber = row.getCell(10).toString().replace(".0", "");
                                    errorMsg = excelUtilityValidation.chequeNumberFormat(chequeNumber, applicationDetails, row.getRowNum());
                                    if (errorMsg.isEmpty())
                                        applicationDetails1.setChequeNumber(Long.valueOf(chequeNumber));

                                    break;

                            }
                        }
                        if (!errorMsg.isEmpty()) break;
                    }
                    if (!errorMsg.isEmpty()) break;
                    applicationDetails1.setChequeStatus("N");
                    applicationDetails1.setUploadBy(emailId);
                    applicationDetails1.setUploadDate(Timestamp.from(Instant.now()));
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

//                    resetPasswordResponse.setOtpId(otpManage.getOtpId());
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
            if (betweenTime <= 8) {
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
            addFetchData(commonResponse, fetchExcelData, applicationDetails, totalCount, pageNo, pageSize);

        } catch (Exception e) {
            System.out.println(("Technical issue :" + e.getMessage()));
        }
        return fetchExcelData;
    }

    private void addFetchData(CommonResponse commonResponse, FetchExcelData fetchExcelData, List<ApplicationDetails> applicationDetails, Long totalCount, int pageNo, int pageSize) {
        if (!applicationDetails.isEmpty()) {
            commonResponse.setMsg("Data found successfully");
            commonResponse.setCode("0000");
            fetchExcelData.setTotalCount(totalCount);
            fetchExcelData.setNextPage(pageNo <= (totalCount / pageSize));
            fetchExcelData.setApplicationDetails(applicationDetails);
            fetchExcelData.setCommonResponse(commonResponse);
        } else {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Data not found");
            fetchExcelData.setCommonResponse(commonResponse);
        }
    }

    public FetchExcelData fetchExcelDataByApplicationNo(String applicationNo, String branchName, int pageNo, String emailId, String status) {
        CommonResponse commonResponse = new CommonResponse();
        FetchExcelData fetchExcelData = new FetchExcelData();
        List<ApplicationDetails> applicationDetails = new ArrayList<>();
        int pageSize = 100;
        long totalCount = 0;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<String> assignBranches = userUtility.findBranchesByUser(emailId);
        try {
            for (String branch : assignBranches) {
                if (branch.equals(branchName) || branchName != null && !branchName.isEmpty() || applicationNo != null && !applicationNo.isEmpty() || status != null && !status.isEmpty()) {
                    applicationDetails = jdbcTemplate.query(userUtility.findByGivenCriteria(applicationNo, branchName, status, pageable), new BeanPropertyRowMapper<>(ApplicationDetails.class));
                    totalCount = applicationDetails.size();
                }
            }
            addFetchData(commonResponse, fetchExcelData, applicationDetails, totalCount, pageNo, pageSize);

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            logger.error("Error while calling cheque status procedure.{}", e.getMessage());
            commonResponse.setMsg("Technical issue :");
            commonResponse.setCode("1111");
            return commonResponse;
        }
    }


    public CommonResponse chequeStatus(ApplicationFlagUpdate flagUpdate, MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        CommonResponse commonResponse = new CommonResponse();
        ChequeStatus chequeStatus = new ChequeStatus();

        CompletableFuture<Boolean> response = ddfsUtility.callDDFSApi(file, flagUpdate.getApplicationNo());
        System.out.println("DDfs response" + response);
        chequeStatus.setChequeId(flagUpdate.getChequeId());
        chequeStatus.setDdfsFlag("Y");
        chequeStatus.setConsumerType(flagUpdate.getConsumerType());
        chequeStatus.setHandoverDate(flagUpdate.getDate());
        chequeStatus.setUpdatedBy(flagUpdate.getUpdatedBy());
        chequeStatus.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        if (response.get().equals(true)) {
            applicationDetailsRepo.updateFlagByApplicationNo(flagUpdate.getApplicationNo(), flagUpdate.getChequeId());
            chequeStatusRepo.save(chequeStatus);
            commonResponse.setMsg("Data save successfully");
            commonResponse.setCode("0000");
        } else {
            commonResponse.setMsg("Technical issue or Try again.");
            commonResponse.setCode("1111");
        }

        return commonResponse;
    }

    public CommonResponse saveBranch(MultipartFile file, String emailId) {
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
                List<BranchMaster> branchMasters = branchMasterRepo.findAll();
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
                                    String branchCode = row.getCell(1).toString().replace(".0", "");
                                    errorMsg = excelUtilityValidation.checkSheetDuplicateBranchCod(branchMasterList, branchCode, row.getRowNum(), branchMasters);
                                    branchMaster.setBranchCode(branchCode);
                                    break;
                                case 2:
                                    branchMaster.setState(row.getCell(2).toString());
                                    break;
                            }
                        }
                        if (!errorMsg.isEmpty()) break;
                    }
                    if (!errorMsg.isEmpty()) break;

                    // Set the current timestamp
                    branchMaster.setUploadedDate(Timestamp.from(Instant.now()));
                    branchMaster.setUploadedBy(emailId);

                    branchMasterList.add(branchMaster);
                }
                if (errorMsg.isEmpty()) {
                    branchMasterRepo.saveAll(branchMasterList);
                    commonResponse.setCode("0000");
                    commonResponse.setMsg("file uploaded successfully " + branchMasterList.size() + " rows uploaded.");
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

    public List<MisReport> fetchReportData(String reportType, String selectedType, String fromDate, String toDate, String selectedDate) {
        List<MisReport> fetchedData = new ArrayList<>();
        try {

            return jdbcTemplate.query(misReportUtility.misQuery(reportType, selectedType, fromDate, toDate, selectedDate), new BeanPropertyRowMapper<>(MisReport.class));
        } catch (Exception e) {
            logger.error("Error while executing report query" + e.getMessage());
            return fetchedData;
        }
    }

    public void generateExcel(HttpServletResponse response, List<MisReport> applicationDetails) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("MIS_Report");
        int rowCount = 0;

        String[] header = {"ApplicationNumber", "BranchName", "ApplicantName", "ChequeAmount", "ConsumerType", "HandoverDate", "LoanAmount", "UpdatedBy"};
        Row headerRow = sheet.createRow(rowCount++);
        int cellCount = 0;

        for (String headerValue : header) {
            headerRow.createCell(cellCount++).setCellValue(headerValue);
        }
        for (MisReport details : applicationDetails) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(details.getApplicationNumber() != null ? details.getApplicationNumber() : "");
            row.createCell(1).setCellValue(details.getBranchName() != null ? details.getBranchName() : "");
            row.createCell(2).setCellValue(details.getApplicantName() != null ? details.getApplicantName() : "");
            row.createCell(3).setCellValue(details.getChequeAmount() != null ? details.getChequeAmount() : 0.0);
            row.createCell(4).setCellValue(details.getConsumerType() != null ? details.getConsumerType() : "");
            row.createCell(5).setCellValue(details.getHandoverDate() != null ? details.getHandoverDate().toString() : "");
            row.createCell(6).setCellValue(details.getLoanAmount() != null ? details.getLoanAmount() : 0.0);
        }

        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=MIS_Report.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        return response;
    }

    public AllAssignBranchResponse findAssignBranchList(String emailId) {
        AllAssignBranchResponse assignBranchResponse = new AllAssignBranchResponse();
        CommonResponse commonResponse = new CommonResponse();

        List<String> userAssignBranch = userUtility.findBranchesByUser(emailId);
        if (!userAssignBranch.isEmpty()) {
            commonResponse.setCode("0000");
            commonResponse.setMsg("Data found successfully");
            userAssignBranch.remove("ALL");
            assignBranchResponse.setAssignBranchList(userAssignBranch);
        } else {
            commonResponse.setCode("1111");
            commonResponse.setMsg("No branch assign to you");
        }
        assignBranchResponse.setCommonResponse(commonResponse);
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

    public ResponseEntity<CommonResponse> userUpdate(Long userId, EditUserDetails inputDetails) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            Optional<UserDetail> userDetail1 = userDetailRepo.findById(userId);
            UserDetail userDetails = userDetail1.get();
            userDetails.setEmailId(inputDetails.getEmailId());
            userDetails.setFirstName(inputDetails.getFirstName());
            userDetails.setLastName(inputDetails.getLastName());
            userDetails.setMobileNo(inputDetails.getMobileNo());
            userDetails.getRoleMasters().setRole(inputDetails.getRoleMasters().getRole());

            for (AssignBranch assignBranch : userDetails.getAssignBranches()) {

                inputDetails.getAssignBranches().removeIf(assignBranch1 -> assignBranch.getBranchCode().equals(assignBranch1.getBranchCode()));
            }
            inputDetails.getAssignBranches().forEach(branch -> {
                branch.setUserMaster(userDetails);
            });

            userDetails.setAssignBranches(inputDetails.getAssignBranches());
            userDetailRepo.save(userDetails);
            commonResponse.setCode("0000");
            commonResponse.setMsg("Updated successfully");
            return ResponseEntity.ok(commonResponse);

        } catch (Exception e) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("User not exist.");
            return ResponseEntity.ok(commonResponse);
        }
    }

    @Override
    public boolean checkPattern(String password, String empCode, CommonResponse commonResponse, String emailId) {
        if (!password.matches(".{8,}") || !emailId.contains("@shubham")) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("invalid email format or password to short.");
            return false;
        } else if (!empCode.matches("\\d{5}")) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Invalid employee code format. It must be exactly 5 numeric digits.");
            return false;
        }
        return true;
    }
}