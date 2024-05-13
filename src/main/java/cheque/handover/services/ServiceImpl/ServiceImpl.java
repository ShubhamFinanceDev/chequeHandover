package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Entity.ExcelDetail;
import cheque.handover.services.Utility.ExcelUtilityValidation;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Repository.ExcelDetailRepo;
import cheque.handover.services.Services.Service;
import cheque.handover.services.Utility.DateFormatUtility;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private ExcelDetailRepo excelDetailRepo;

    @Autowired
    private ExcelUtilityValidation excelUtilityValidation;

    @Autowired
    private DateFormatUtility dateFormatUtility;

    @Override
    public CommonResponse excelUpload(MultipartFile file) {

        CommonResponse commonResponse = new CommonResponse();
        List<ExcelDetail> excelDetails = new ArrayList<>();
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
                    ExcelDetail excelDetail = new ExcelDetail();

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
                    excelDetailRepo.saveAll(excelDetails);
                    commonResponse.setMsg("file uploaded successfully " + excelDetails.size() + " row uploaded.");
                    commonResponse.setCode("0000");
                } else {
                    errorMsg = "file is empty";
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