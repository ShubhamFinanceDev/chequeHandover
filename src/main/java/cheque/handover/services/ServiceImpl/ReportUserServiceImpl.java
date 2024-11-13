package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.ReportUserModel;
import cheque.handover.services.Utility.GetExcelDataForReportUser;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class ReportUserServiceImpl {

    @Autowired
    private GetExcelDataForReportUser getExcelDataForReportUser;
    private final Logger logger = LoggerFactory.getLogger(User.class);
    @Autowired
    @Qualifier("jdbcJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<?> excelExportService(String applicationNo, MultipartFile file, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        List<ReportUserModel> reportUserModel = new ArrayList<>();
        try {
            if (file != null && !file.isEmpty()) {
                String extractedApplicationNo = extractApplicationNoFromExcel(file);
                reportUserModel = jdbcTemplate.query(getExcelDataForReportUser.query(extractedApplicationNo), new BeanPropertyRowMapper<>(ReportUserModel.class));
            } else if (applicationNo != null && !applicationNo.isEmpty()) {
                String newApplicationNo = "'"+applicationNo+"'";
                reportUserModel = jdbcTemplate.query(getExcelDataForReportUser.query(newApplicationNo), new BeanPropertyRowMapper<>(ReportUserModel.class));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Application number or file is required.");
            }
            System.out.println(getExcelDataForReportUser.query(applicationNo));
            if (reportUserModel.isEmpty()) {
                commonResponse.setCode("1111");
                commonResponse.setMsg("Data not found");
                return ResponseEntity.ok(commonResponse);
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("USER_REPORT");
            int rowCount = 0;

            String[] header = {"Document Name", "IRN", "Field(0).id", "Field(0).name", "Field(0).value", "Field(1).id", "Field(1).name", "Field(1).value", "Field(2).id", "Field(2).name", "Field(2).value", "Field(3).id", "Field(3).name", "Field(3).value", "Field(4).id", "Field(4).name", "Field(4).value", "Field(5).id", "Field(5).name", "Field(5).value", "Field(6).id", "Field(6).name", "Field(6).value", "Field(7).id", "Field(7).name", "Field(7).value", "Field(8).id", "Field(8).name", "Field(8).value", "Field(9).id", "Field(9).name", "Field(9).value", "Field(10).id", "Field(10).name", "Field(10).value", "Field(11).id", "Field(11).name", "Field(11).value", "Field(12).id", "Field(12).name", "Field(12).value", "Field(13).id", "Field(13).name", "Field(13).value", "Field(14).id", "Field(14).name", "Field(14).value", "Field(15).id", "Field(15).name", "Field(15).value", "Field(16).id", "Field(16).name", "Field(16).value", "Field(17).id", "Field(17).name", "Field(17).value", "Field(18).id", "Field(18).name", "Field(18).value", "Field(19).id", "Field(19).name", "Field(19).value", "Field(20).id", "Field(20).name", "Field(20).value", "Field(21).id", "Field(21).name", "Field(21).value", "Field(22).id", "Field(22).name", "Field(22).value", "Field(23).id", "Field(23).name", "Field(23).value", "Field(24).id", "Field(24).name", "Field(24).value", "Field(25).id", "Field(25).name", "Field(25).value", "Field(26).id", "Field(26).name", "Field(26).value", "Field(27).id", "Field(27).name", "Field(27).value", "Field(28).id", "Field(28).name", "Field(28).value", "Field(29).id", "Field(29).name", "Field(29).value", "Field(30).id", "Field(30).name", "Field(30).value", "Field(31).id", "Field(31).name", "Field(31).value", "Field(32).id", "Field(32).name", "Field(32).value", "Field(33).id", "Field(33).name", "Field(33).value", "Field(34).id", "Field(34).name", "Field(34).value", "Field(35).id", "Field(35).name", "Field(35).value", "Field(36).id", "Field(36).name", "Field(36).value", "Field(37).id", "Field(37).name", "Field(37).value", "Field(38).id", "Field(38).name", "Field(38).value", "Field(39).id", "Field(39).name", "Field(39).value", "Field(40).id", "Field(40).name", "Field(40).value", "Field(41).id", "Field(41).name", "Field(41).value", "Field(42).id", "Field(42).name", "Field(42).value", "Field(43).id", "Field(43).name", "Field(43).value", "Field(44).id", "Field(44).name", "Field(44).value", "Field(45).id", "Field(45).name", "Field(45).value", "Field(46).id", "Field(46).name", "Field(46).value", "Field(47).id", "Field(47).name", "Field(47).value", "Field(48).id", "Field(48).name", "Field(48).value", "Field(49).id", "Field(49).name", "Field(49).value", "Field(50).id", "Field(50).name", "Field(50).value", "Field(51).id", "Field(51).name", "Field(51).value", "Field(52).id", "Field(52).name", "Field(52).value", "Field(53).id", "Field(53).name", "Field(53).value", "Field(54).id", "Field(54).name", "Field(54).value", "Field(55).id", "Field(55).name", "Field(55).value", "Field(56).id", "Field(56).name", "Field(56).value", "Field(57).id", "Field(57).name", "Field(57).value", "Field(58).id", "Field(58).name", "Field(58).value", "Field(59).id", "Field(59).name", "Field(59).value", "Field(60).id", "Field(60).name", "Field(60).value", "Field(61).id", "Field(61).name", "Field(61).value", "Field(62).id", "Field(62).name", "Field(62).value", "Field(63).id", "Field(63).name", "Field(63).value", "Field(64).id", "Field(64).name", "Field(64).value", "RegistrationType", "LoanNo", "SanctionNo", "State", "Branch", "BranchAddress", "LoanSanctionDate", "InstallmentAmt", "InterestRate", "SanctionAmount", "Tenure", "TypeOfDebt", "AccountClosedFlag", "FundedType", "LoanCurrency", "CreditSub-Type", "FacilityName", "AmtOverdue", "OtherCharges", "DebtStartDate", "InterestAmount", "OldDebtRefNo", "PrincipalOutstanding", "LoanRemarks", "TotalOutstanding", "CreditorBusinessUnit", "DrawingPower", "DaysPastDue", "DocRefNo", "Event", "ExpiryDate", "CurrencyOfDebt", "ClaimExpiryDate", "ContractRefNo", "VendorCode", "PortalID", "Stamp(0).FirstParty", "Stamp(0).SecondParty", "Stamp(0).FirstPartyPin", "Stamp(0).SecondPartyPin", "Stamp(0).FirstPartyIDType", "Stamp(0).SecondPartyIDType", "Stamp(0).FirstPartyIDNumber", "Stamp(0).SecondPartyIDNumber", "Stamp(0).StampAmount", "Stamp(0).Consideration", "Stamp(0).DocDescription", "Stamp(0).StampDutyPayer", "Stamp(0).Article", "Stamp(1).FirstParty", "Stamp(1).SecondParty", "Stamp(1).FirstPartyPin", "Stamp(1).SecondPartyPin", "Stamp(1).FirstPartyIDType", "Stamp(1).SecondPartyIDType", "Stamp(1).FirstPartyIDNumber", "Stamp(1).SecondPartyIDNumber", "Stamp(1).StampAmount", "Stamp(1).Consideration", "Stamp(1).DocDescription", "Stamp(1).StampDutyPayer", "Stamp(1).Article", "Stamp(2).FirstParty", "Stamp(2).SecondParty", "Stamp(2).FirstPartyPin", "Stamp(2).SecondPartyPin", "Stamp(2).FirstPartyIDType", "Stamp(2).SecondPartyIDType", "Stamp(2).FirstPartyIDNumber", "Stamp(2).SecondPartyIDNumber", "Stamp(2).StampAmount", "Stamp(2).Consideration", "Stamp(2).DocDescription", "Stamp(2).StampDutyPayer", "Stamp(2).Article", "Invitee(0).name", "Invitee(0).email", "Invitee(0).phone", "Invitee(0).aadhaar.verifyYob", "Invitee(0).aadhaar.verifyTitle", "Invitee(0).aadhaar.verifyGender", "Invitee(0).Partyname", "Invitee(0).Primaryemail", "Invitee(0).Primarymobile", "Invitee(0).ContactName", "Invitee(0).RelationshipOfPartyWithLoan", "Invitee(0).DoB/Incorporation", "Invitee(0).LegalConstitution", "Invitee(0).AlternateEmailofParty", "Invitee(0).AlternateMobileOfParty", "Invitee(0).OfficialDocType", "Invitee(0).OfficialDocId", "Invitee(0).RegisteredAddressOfTheParty", "Invitee(0).Permanent/RegisteredAddressPIN", "Invitee(0).ContactDesignation", "Invitee(0).PartyCommunicationAddress", "Invitee(0).PartyCommunicationAddressPIN", "Invitee(0).CorpIdentificationNo", "Invitee(0).CKYCKIN", "Invitee(0).PartyType", "Invitee(0).Is_Individual", "Invitee(0).SignatoryGender", "Invitee(0).BusinessUnit", "Invitee(1).name", "Invitee(1).email", "Invitee(1).phone", "Invitee(1).aadhaar.verifyYob", "Invitee(1).aadhaar.verifyTitle", "Invitee(1).aadhaar.verifyGender", "Invitee(2).name", "Invitee(2).email", "Invitee(2).phone", "Invitee(2).aadhaar.verifyYob", "Invitee(2).aadhaar.verifyTitle", "Invitee(2).aadhaar.verifyGender", "Invitee(3).name", "Invitee(3).email", "Invitee(3).phone", "Invitee(3).aadhaar.verifyYob", "Invitee(3).aadhaar.verifyTitle", "Invitee(3).aadhaar.verifyGender", "Invitee(4).name", "Invitee(4).email", "Invitee(4).phone"};
            Row headerRow = sheet.createRow(rowCount++);
            int cellCount = 0;

            for (String headerValue : header) {
                headerRow.createCell(cellCount++).setCellValue(headerValue);
            }
            for (ReportUserModel details : reportUserModel) {
                Row row = sheet.createRow(rowCount++);
                cellCount = 0;

                row.createCell(cellCount++).setCellValue(details.getDocumentName()); // documentName
                row.createCell(cellCount++).setCellValue(details.getIrn());// irn

                for (int i = 0; i < 65; i++) {
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "field" + i + "Id") != null ? getFieldValue(details, "field" + i + "Id") : "");
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "field" + i + "Name") != null ? getFieldValue(details, "field" + i + "Name") : "");
                    String value = getFieldValue(details, "field" + i + "Value");
                    row.createCell(cellCount++).setCellValue(value);
                }

                row.createCell(cellCount++).setCellValue(details.getRegistrationType());
                row.createCell(cellCount++).setCellValue(details.getLoanNo());
                row.createCell(cellCount++).setCellValue(details.getSanctionNo());
                row.createCell(cellCount++).setCellValue(details.getState());
                row.createCell(cellCount++).setCellValue(details.getBranch());
                row.createCell(cellCount++).setCellValue(details.getBranchAddress());
                row.createCell(cellCount++).setCellValue(details.getLoanSanctionDate());
                row.createCell(cellCount++).setCellValue(details.getInstallmentAmt());
                row.createCell(cellCount++).setCellValue(details.getInterestRate());
                row.createCell(cellCount++).setCellValue(details.getSanctionAmount());
                row.createCell(cellCount++).setCellValue(details.getTenure());
                row.createCell(cellCount++).setCellValue(details.getTypeOfDebt());
                row.createCell(cellCount++).setCellValue(details.getAccountClosedFlag());
                row.createCell(cellCount++).setCellValue(details.getFundedType());
                row.createCell(cellCount++).setCellValue(details.getLoanCurrency());
                row.createCell(cellCount++).setCellValue(details.getCreditSubType());
                row.createCell(cellCount++).setCellValue(details.getFacilityName());
                row.createCell(cellCount++).setCellValue(details.getAmtOverdue());
                row.createCell(cellCount++).setCellValue(details.getOtherCharges());
                row.createCell(cellCount++).setCellValue(details.getDebtStartDate());
                row.createCell(cellCount++).setCellValue(details.getInterestAmount());
                row.createCell(cellCount++).setCellValue(details.getOldDebtRefNo());
                row.createCell(cellCount++).setCellValue(details.getPrincipalOutstanding());
                row.createCell(cellCount++).setCellValue(details.getLoanRemarks());
                row.createCell(cellCount++).setCellValue(details.getTotalOutstanding());
                row.createCell(cellCount++).setCellValue(details.getCreditorBusinessUnit());
                row.createCell(cellCount++).setCellValue(details.getDrawingPower());
                row.createCell(cellCount++).setCellValue(details.getDaysPastDue());
                row.createCell(cellCount++).setCellValue(details.getDocRefNo());
                row.createCell(cellCount++).setCellValue(details.getEvent());
                row.createCell(cellCount++).setCellValue(details.getExpiryDate());
                row.createCell(cellCount++).setCellValue(details.getCurrencyOfDebt());
                row.createCell(cellCount++).setCellValue(details.getClaimExpiryDate());
                row.createCell(cellCount++).setCellValue(details.getContractRefNo());
                row.createCell(cellCount++).setCellValue(details.getVendorCode());
                row.createCell(cellCount++).setCellValue(details.getPortalId());

                for (int stampIndex = 0; stampIndex < 3; stampIndex++) {
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "FirstParty"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "SecondParty"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "FirstPartyPin"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "SecondPartyPin"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "FirstPartyIDType"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "SecondPartyIDType"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "FirstPartyIDNumber"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "SecondPartyIDNumber"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "Amount"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "Consideration"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "DocDescription"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "StampDutyPayer"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "stamp" + stampIndex + "Article"));
                }

                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Name"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Email"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Phone"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Aadhaar_VerifyYob"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Aadhaar_VerifyTitle"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0Aadhaar_VerifyGender"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PartyName"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PrimaryEmail"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PrimaryMobile"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0ContactName"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0RelationshipOfPartyWithLoan"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0DobIncorporation"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0LegalConstitution"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0AlternateEmailOfParty"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0AlternateMobileOfParty"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0OfficialDocType"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0OfficialDocId"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0RegisteredAddressOfTheParty"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PermanentRegisteredAddressPin"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0ContactDesignation"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PartyCommunicationAddress"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PartyCommunicationAddressPin"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0CorpIdentificationNo"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0CkycKin"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0PartyType"));
                row.createCell(cellCount++).setCellValue(details.getInvitee0IsIndividual() != null ? details.getInvitee0IsIndividual().toString() : ""); // Assuming it's a Boolean
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0SignatoryGender"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee0BusinessUnit"));

                for (int inviteeIndex = 1; inviteeIndex < 4; inviteeIndex++) {
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Name"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Email"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Phone"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Aadhaar_VerifyYob"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Aadhaar_VerifyTitle"));
                    row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee" + inviteeIndex + "Aadhaar_VerifyGender"));
                }
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee4Name"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee4Email"));
                row.createCell(cellCount++).setCellValue(getFieldValue(details, "invitee4Phone"));
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=MIS_Report.xlsx");

            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {
                    System.out.println("Error closing workbook: " + e.getMessage());
                }
            }
            return ResponseEntity.ok("File exported successfully.");
        } catch (Exception e) {
            logger.error("Exception found:", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating the Excel file.");
        }
    }

    private String extractApplicationNoFromExcel(MultipartFile file) {
        String applicationNo = "";
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            int rowCount = 0;

            StringJoiner applicationNoJoiner = new StringJoiner(", ");

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (rowCount >= 25){
                    return "Excel file exceeds the allowed limit of " + 25 + " application numbers .";
                }
                String applicationNumber = row.getCell(0).getStringCellValue();
                applicationNoJoiner.add("'" + applicationNumber + "'");
                rowCount++;
            }
            applicationNo = applicationNoJoiner.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicationNo;
    }

    private String getFieldValue(ReportUserModel details, String fieldName) {
        try {
            Field field = details.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(details) != null ? field.get(details).toString() : "";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Field not found or inaccessible: " + fieldName, e);
            return null;
        }
    }
}