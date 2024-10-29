package cheque.handover.services.ServiceImpl;

import cheque.handover.services.Controller.User;
import cheque.handover.services.Model.CommonResponse;
import cheque.handover.services.Model.ReportUserModel;
import cheque.handover.services.Utility.GetExcelDataForReportUser;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportUserServiceImpl {

    @Autowired
    private GetExcelDataForReportUser getExcelDataForReportUser;
    private final Logger logger = LoggerFactory.getLogger(User.class);
    @Autowired
    @Qualifier("jdbcJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<?> excelExportService(String applicationNo, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        List<ReportUserModel> reportUserModel = new ArrayList<>();
        if (applicationNo == null || applicationNo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Application number is required.");
        }
        try {
            reportUserModel = jdbcTemplate.query(getExcelDataForReportUser.query(applicationNo), new BeanPropertyRowMapper<>(ReportUserModel.class));
            System.out.println(getExcelDataForReportUser.query(applicationNo));
            if (reportUserModel.isEmpty()) {
                commonResponse.setCode("1111");
                commonResponse.setMsg("Data not found");
                return ResponseEntity.ok(commonResponse);
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("USER_REPORT");
            int rowCount = 0;

            String[] header = {"documentName", "irn", "field(0).Id", "field(0).Name", "field(0).Value", "field(1).Id", "field(1).Name", "field(1).Value", "field(2).Id", "field(2).Name", "field(2).Value", "field(3).Id", "field(3).Name", "field(3).Value", "field(4).Id", "field(4).Name", "field(4).Value", "field(5).Id", "field(5).Name", "field(5).Value", "field(6).Id", "field(6).Name", "field(6).Value", "field(7).Id", "field(7).Name", "field(7).Value", "field(8).Id", "field(8).Name", "field(8).Value", "field(9).Id", "field(9).Name", "field(9).Value", "field(10).Id", "field(10).Name", "field(10).Value", "field(11).Id", "field(11).Name", "field(11).Value", "field(12).Id", "field(12).Name", "field(12).Value", "field(13).Id", "field(13).Name", "field(13).Value", "field(14).Id", "field(14).Name", "field(14).Value", "field(15).Id", "field(15).Name", "field(15).Value", "field(16).Id", "field(16).Name", "field(16).Value", "field(17).Id", "field(17).Name", "field(17).Value", "field(18).Id", "field(18).Name", "field(18).Value", "field(19).Id", "field(19).Name", "field(19).Value", "field(20).Id", "field(20).Name", "field(20).Value", "field(21).Id", "field(21).Name", "field(21).Value", "field(22).Id", "field(22).Name", "field(22).Value", "field(23).Id", "field(23).Name", "field(23).Value", "field(24).Id", "field(24).Name", "field(24).Value", "field(25).Id", "field(25).Name", "field(25).Value", "field(26).Id", "field(26).Name", "field(26).Value", "field(27).Id", "field(27).Name", "field(27).Value", "field(28).Id", "field(28).Name", "field(28).Value", "field(29).Id", "field(29).Name", "field(29).Value", "field(30).Id", "field(30).Name", "field(30).Value", "field(31).Id", "field(31).Name", "field(31).Value", "field(32).Id", "field(32).Name", "field(32).Value", "field(33).Id", "field(33).Name", "field(33).Value", "field(34).Id", "field(34).Name", "field(34).Value", "field(35).Id", "field(35).Name", "field(35).Value", "field(36).Id", "field(36).Name", "field(36).Value", "field(37).Id", "field(37).Name", "field(37).Value", "field(38).Id", "field(38).Name", "field(38).Value", "field(39).Id", "field(39).Name", "field(39).Value", "field(40).Id", "field(40).Name", "field(40).Value", "field(41).Id", "field(41).Name", "field(41).Value", "field(42).Id", "field(42).Name", "field(42).Value", "field(43).Id", "field(43).Name", "field(43).Value", "field(44).Id", "field(44).Name", "field(44).Value", "field(45).Id", "field(45).Name", "field(45).Value", "field(46).Id", "field(46).Name", "field(46).Value", "field(47).Id", "field(47).Name", "field(47).Value", "field(48).Id", "field(48).Name", "field(48).Value", "field(49).Id", "field(49).Name", "field(49).Value", "field(50).Id", "field(50).Name", "field(50).Value", "field(51).Id", "field(51).Name", "field(51).Value", "field(52).Id", "field(52).Name", "field(52).Value", "field(53).Id", "field(53).Name", "field(53).Value", "field(54).Id", "field(54).Name", "field(54).Value", "field(55).Id", "field(55).Name", "field(55).Value", "field(56).Id", "field(56).Name", "field(56).Value", "field(57).Id", "field(57).Name", "field(57).Value", "field(58).Id", "field(58).Name", "field(58).Value", "field(59).Id", "field(59).Name", "field(59).Value", "field(60).Id", "field(60).Name", "field(60).Value", "field(61).Id", "field(61).Name", "field(61).Value", "field(62).Id", "field(62).Name", "field(62).Value", "field(63).Id", "field(63).Name", "field(63).Value", "field(64).Id", "field(64).Name", "field(64).Value", "registrationType", "loanNo", "sanctionNo", "state", "branch", "branchAddress", "loanSanctionDate", "installmentAmt", "interestRate", "sanctionAmount", "tenure", "typeOfDebt", "accountClosedFlag", "fundedType", "loanCurrency", "creditSubType", "facilityName", "amtOverdue", "otherCharges", "debtStartDate", "interestAmount", "oldDebtRefNo", "principalOutstanding", "loanRemarks", "totalOutstanding", "creditorBusinessUnit", "drawingPower", "daysPastDue", "docRefNo", "event", "expiryDate", "currencyOfDebt", "claimExpiryDate", "contractRefNo", "vendorCode", "portalId", "stamp(0).FirstParty", "stamp(0).SecondParty", "stamp(0).FirstPartyPin", "stamp(0).SecondPartyPin", "stamp(0).FirstPartyIDType", "stamp(0).SecondPartyIDType", "stamp(0).FirstPartyIDNumber", "stamp(0).SecondPartyIDNumber", "stamp(0).Amount", "stamp(0).Consideration", "stamp(0).DocDescription", "stamp(0).StampDutyPayer", "stamp(0).Article", "stamp(1).FirstParty", "stamp(1).SecondParty", "stamp(1).FirstPartyPin", "stamp(1).SecondPartyPin", "stamp(1).FirstPartyIDType", "stamp(1).SecondPartyIDType", "stamp(1).FirstPartyIDNumber", "stamp(1).SecondPartyIDNumber", "stamp(1).Amount", "stamp(1).Consideration", "stamp(1).DocDescription", "stamp(1).StampDutyPayer", "stamp(1).Article", "stamp(2).FirstParty", "stamp(2).SecondParty", "stamp(2).FirstPartyPin", "stamp(2).SecondPartyPin", "stamp(2).FirstPartyIDType", "stamp(2).SecondPartyIDType", "stamp(2).FirstPartyIDNumber", "stamp(2).SecondPartyIDNumber", "stamp(2).Amount", "stamp(2).Consideration", "stamp(2).DocDescription", "stamp(2).StampDutyPayer", "stamp(2).Article", "invitee(0).Name", "invitee(0).Email", "invitee(0).Phone", "invitee(0).Aadhaar.VerifyYob", "invitee(0).Aadhaar.VerifyTitle", "invitee(0).Aadhaar.VerifyGender", "invitee(0).PartyName", "invitee(0).PrimaryEmail", "invitee(0).PrimaryMobile", "invitee(0).ContactName", "invitee(0).RelationshipOfPartyWithLoan", "invitee(0).DobIncorporation", "invitee(0).LegalConstitution", "invitee(0).AlternateEmailOfParty", "invitee(0).AlternateMobileOfParty", "invitee(0).OfficialDocType", "invitee(0).OfficialDocId", "invitee(0).RegisteredAddressOfTheParty", "invitee(0).PermanentRegisteredAddressPin", "invitee(0).ContactDesignation", "invitee(0).PartyCommunicationAddress", "invitee(0).PartyCommunicationAddressPin", "invitee(0).CorpIdentificationNo", "invitee(0).CkycKin", "invitee(0).PartyType", "invitee(0).IsIndividual", "invitee(0).SignatoryGender", "invitee(0).BusinessUnit", "invitee(1).Name", "invitee(1).Email", "invitee(1).Phone", "invitee(1).Aadhaar.VerifyYob", "invitee(1).Aadhaar.VerifyTitle", "invitee(1).Aadhaar.VerifyGender", "invitee(2).Name", "invitee(2).Email", "invitee(2).Phone", "invitee(2).Aadhaar.VerifyYob", "invitee(2).Aadhaar.VerifyTitle", "invitee(2).Aadhaar.VerifyGender", "invitee(3).Name", "invitee(3).Email", "invitee(3).Phone", "invitee(3).Aadhaar.VerifyYob", "invitee(3).Aadhaar.VerifyTitle", "invitee(3).Aadhaar.VerifyGender", "invitee(4).Name", "invitee(4).Email", "invitee(4).Phone"};
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
            response.setHeader("Content-Disposition", "attachment; filename=USER_REPORT.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

            logger.info("Excel generated successfully");
            return ResponseEntity.ok("File exported successfully.");
        } catch (Exception e) {
            logger.error("Exception found:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating the Excel file.");
        }
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