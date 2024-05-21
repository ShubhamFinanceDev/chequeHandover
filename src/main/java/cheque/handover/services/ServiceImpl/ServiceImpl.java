package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.*;
import cheque.handover.services.Model.*;
import cheque.handover.services.Repository.*;
import cheque.handover.services.Utility.DateFormatUtility;
import cheque.handover.services.Utility.DdfsUtility;
import cheque.handover.services.Utility.ExcelUtilityValidation;
import cheque.handover.services.Utility.OtpUtility;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        UserDetail userDetails = new UserDetail();
        RoleMaster userRoleDetail = new RoleMaster();
        List<AssignBranch> assignBranchList = new ArrayList<>();
        try {
            Optional<UserDetail> emailExist = userDetailRepo.findUser(userDetail.getEmailId());
            if (emailExist.isEmpty()) {
                userDetails.setPassword(passwordEncoder.encode(userDetail.getPassword()));
                userDetails.setEmailId(userDetail.getEmailId());
                userDetails.setFirstname(userDetail.getFirstname());
                userDetails.setLastName(userDetail.getLastName());
                userDetails.setMobileNo(userDetail.getMobileNo());

                userDetails.setCreatedBy(userDetail.getCreatedBy());
                logger.info("createdBy : " + userDetail.getCreatedBy());


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
                                    applicationDetails1.setChequeAmount(Long.valueOf(row.getCell(9).toString().replace(".0", "")));
                                    ;
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
        if (newPassword.matches(confirmNewPassword)) {
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

    public FetchExcelData fetchExcelData(String emailId,int pageNo) {

        FetchExcelData fetchExcelData = new FetchExcelData();
        CommonResponse commonResponse = new CommonResponse();
        int pageSize = 100;

        try {
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            Optional<UserDetail> userDetail = userDetailRepo.findByEmailId(emailId);
            List<ApplicationDetails> applicationDetails = new ArrayList<>();
            UserDetail userDetail1 = userDetail.get();
            List<AssignBranch> assignBranches = userDetail1.getAssignBranches();
            List<String> branchCodes = new ArrayList<>();

            for (AssignBranch branches : assignBranches) {
                branchCodes.add(String.valueOf(branches.getBranchCode()));
            }
            if (!branchCodes.isEmpty()) {
                List<String> branchNames = branchMasterRepo.findBranches(branchCodes);
                applicationDetails = applicationDetailsRepo.findAlldetails(branchNames,pageable);
                long totalCount=applicationDetailsRepo.findCount(branchNames);
                if (applicationDetails != null) {
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
            }
        } catch (Exception e) {
            System.out.println(("Technical issue :"+e.getMessage()));
        }
        return fetchExcelData;
    }

    public FetchExcelData fetchExcelDataByApplicationNo(String applicationNo,int pageNo){
        CommonResponse commonResponse = new CommonResponse();
        FetchExcelData fetchExcelData = new FetchExcelData();

        int pageSize = 100;
        try {
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            List<ApplicationDetails> applicationDetails = applicationDetailsRepo.findByApplicationNo(applicationNo,pageable);
            long totalCount=applicationDetailsRepo.findCountByApplicationNo(applicationNo);
            if (applicationDetails != null) {
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
        }catch (Exception e){
            commonResponse.setMsg("Technical issue :"+e);
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
            commonResponse.setMsg("SUCCESS");
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
                                    ;
                                    break;
                                case 1:
                                    branchMaster.setBranchCode(row.getCell(1).toString().replace(".0", ""));
                                    ;
                                    break;
                                case 2:
                                    branchMaster.setState(row.getCell(2).toString());
                                    ;
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

    public CommonResponse generateExcel() throws IOException {
        CommonResponse commonResponse = new CommonResponse();
        List<ApplicationDetails> applicationDetails = applicationDetailsRepo.findByFlag();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Persons");

        int rowCount = 0;
        Row headerRow = sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("ApplicationNumber");
        headerRow.createCell(1).setCellValue("DisbursalDate");
        headerRow.createCell(2).setCellValue("SanctionDate");
        headerRow.createCell(3).setCellValue("BranchName");
        headerRow.createCell(4).setCellValue("HubName");
        headerRow.createCell(5).setCellValue("Region");
        headerRow.createCell(6).setCellValue("ApplicantName");
        headerRow.createCell(7).setCellValue("ChequeAmount");
        headerRow.createCell(8).setCellValue("ProductName");
        headerRow.createCell(9).setCellValue("LoanAmount");
        headerRow.createCell(10).setCellValue("getChequeStatus");

        for (ApplicationDetails details : applicationDetails) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(details.getApplicationNumber());
            row.createCell(1).setCellValue(details.getDisbursalDate());
            row.createCell(2).setCellValue(details.getSanctionDate());
            row.createCell(3).setCellValue(details.getBranchName());
            row.createCell(4).setCellValue(details.getHubName());
            row.createCell(5).setCellValue(details.getRegion());
            row.createCell(6).setCellValue(details.getApplicantName());
            row.createCell(7).setCellValue(details.getChequeAmount());
            row.createCell(8).setCellValue(details.getProductName());
            row.createCell(9).setCellValue(details.getLoanAmount());
            row.createCell(10).setCellValue(details.getChequeStatus());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String filePath = "D:\\demo\\push\\ApplicationDetails_" + timestamp + ".xlsx";

        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            commonResponse.setCode("0000");
            commonResponse.setMsg("Excel generated successfully ");
        } catch (IOException e) {
            commonResponse.setCode("1111");
            commonResponse.setMsg("Technical issue ");
        } finally {
            workbook.close();
        }
        return commonResponse;
    }
}