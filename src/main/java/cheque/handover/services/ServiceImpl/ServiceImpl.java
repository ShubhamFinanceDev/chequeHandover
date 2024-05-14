package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Entity.ExcelMaster;
import cheque.handover.services.Entity.UserDetail;
import cheque.handover.services.Model.BranchesResponse;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.UserDetailResponse;
import cheque.handover.services.Repository.BranchMasterRepo;
import cheque.handover.services.Repository.ExcelMasterRepo;
import cheque.handover.services.Repository.UserDetailRepo;
import cheque.handover.services.Utility.DateFormatUtility;
import cheque.handover.services.Utility.ExcelUtilityValidation;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements cheque.handover.services.Services.Service {
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private BranchMasterRepo branchMasterRepo;
    @Autowired
    private ExcelUtilityValidation excelUtilityValidation;
    @Autowired
    private DateFormatUtility dateFormatUtility;
    @Autowired
    private ExcelMasterRepo excelMasterRepo;
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
    public CommonResponse excelUpload(MultipartFile file) {

        CommonResponse commonResponse = new CommonResponse();
        List<ExcelMaster> excelDetails = new ArrayList<>();
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
                    ExcelMaster excelDetail = new ExcelMaster();

                    for (int i = 0; i < 10; i++) {
                        Cell cell = row.getCell(i);
                        errorMsg = (cell == null || cell.getCellType() == CellType.BLANK) ? "file upload error due to row no " + (row.getRowNum() + 1) + " is empty" : "";


                        if (errorMsg.isEmpty()) {
                            switch (i) {
                                case 0:
                                    excelDetail.setApplicantName(row.getCell(0).toString());
                                    ;
                                    break;
                                case 1:
                                    excelDetail.setBranchName(row.getCell(1).toString());
                                    ;
                                    break;
                                case 2:
                                    excelDetail.setRegion(row.getCell(2).toString());
                                    ;
                                    break;
                                case 3:
                                    excelDetail.setHubName(row.getCell(3).toString());
                                    ;
                                    break;
                                case 4:
                                    excelDetail.setApplicationNumber(row.getCell(4).toString());
                                    ;
                                    break;
                                case 5:
                                    excelDetail.setProductName(row.getCell(5).toString());
                                    ;
                                    break;
                                case 6:
                                    excelDetail.setLoanAmount(Long.valueOf(row.getCell(6).toString().replace(".0", "")));
                                    ;
                                    break;
                                case 7:
                                    excelDetail.setSanctionDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(7).toString())));
                                    ;
                                    break;
                                case 8:
                                    excelDetail.setDisbursalDate(Date.valueOf(dateFormatUtility.changeDateFormate(row.getCell(8).toString())));
                                    ;
                                    break;
                                case 9:
                                    excelDetail.setChequeAmount(Long.valueOf(row.getCell(9).toString().replace(".0", "")));
                                    ;
                                    break;
                            }
                        }
                        if (!errorMsg.isEmpty())
                            break;
                    }
                    if (!errorMsg.isEmpty())
                        break;
                    excelDetails.add(excelDetail);
                }

                if (errorMsg.isEmpty()) {
                    excelMasterRepo.saveAll(excelDetails);
                    commonResponse.setMsg("file uploaded successfully " + excelDetails.size() + " row uploaded.");
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
}