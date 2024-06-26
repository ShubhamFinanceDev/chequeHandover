package cheque.handover.services.Utility;

import cheque.handover.services.Controller.Admin;
import cheque.handover.services.Entity.ApplicationDetails;
import cheque.handover.services.Entity.BranchMaster;
import cheque.handover.services.Repository.ApplicationDetailsRepo;
import cheque.handover.services.Repository.BranchMasterRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExcelUtilityValidation {

    @Autowired
    private ApplicationDetailsRepo applicationDetailsRepo;


    private final Logger logger = LoggerFactory.getLogger(Admin.class);

    public boolean ExcelFileFormat(Row headerRow) {
        String[] expectedHeaders = {"Applicant Name", "Branch Name", "Region", "Hub Name", "Application Number", "Product Name", "Loan Amount", "Sanction Date", "Disbursal date", "Cheque Amount", "Cheque Number"};

        for (int i = 0; i < 11; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || cell.getCellType() == CellType.BLANK)
                return false;

            String cellName = cell.toString();
            if (!cellName.equals(expectedHeaders[i]))
                return false;
        }

        return true;
    }

    public boolean branchAddValidation(Row headerRow) {
        String[] expectedHeaders = {"Branch Name", "Branch Code", "State"};

        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || cell.getCellType() == CellType.BLANK)
                return false;

            String cellName = cell.toString();
            if (!cellName.equals(expectedHeaders[i]))
                return false;
        }
        return true;
    }

    public String chequeAmount(String amount, int rowNum, String loan) {
        String errorMsg = "";
        try {
            double chequeAmount = Double.parseDouble(amount);
            if (chequeAmount <= 0) {
                errorMsg = loan + " Amount is not in the correct format in the file at row no " + (rowNum + 1);
            }
        } catch (Exception e) {
            System.out.println(e);
            logger.info(loan + " Amount is not in the correct format in the file at row no " + (rowNum + 1));
            errorMsg = loan + " Amount is not in the correct format in the file at row no " + (rowNum + 1);
        }
        return errorMsg;
    }

    public String checkSheetDuplicateBranchCod(List<BranchMaster> branchCodesList, String branchCode, int rowNum, List<BranchMaster> branchMasters) {
        String errorMsg = "";
        for (BranchMaster sheetBranchCode : branchCodesList) {
            errorMsg = (sheetBranchCode.getBranchCode().equalsIgnoreCase(branchCode)) ? "Duplicate branch code '" + branchCode + "' found in the file at row no " + (rowNum + 1) : "";
            if (!errorMsg.isEmpty()) break;
        }
        if (errorMsg.isEmpty()) {
            for (BranchMaster exitingBranchMaster : branchMasters) {
                errorMsg = (exitingBranchMaster.getBranchCode().equalsIgnoreCase(branchCode)) ? "Branch code '" + branchCode + "' already exists." : "";
                if (!errorMsg.isEmpty()) break;
            }
        }
        return errorMsg;
    }

    public double decimalFormat(String amount) {
        double chequeAmount = Double.parseDouble(amount);
        BigDecimal bd = new BigDecimal(chequeAmount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        chequeAmount = bd.doubleValue();
        return chequeAmount;
    }

    public String chequeNumberFormat(String chequeNumber, List<ApplicationDetails> applicationDetails, int rowNum) {
        String errorMsg = "";

        if (!chequeNumber.matches("\\d{6}")) {
            errorMsg = "Cheque number is not in the correct format in the file at row no " + (rowNum + 1);
        }

        if (errorMsg.isEmpty()) {
            for (ApplicationDetails sheetChequeNo : applicationDetails) {
                errorMsg = (sheetChequeNo.getChequeNumber().longValue()==Long.valueOf(chequeNumber)) ? "Duplicate Cheque number '" + chequeNumber + "' found in the file at row no " + (rowNum + 1) : "";
                if (!errorMsg.isEmpty()) break;
            }
            if (errorMsg.isEmpty()) {
                errorMsg = (applicationDetailsRepo.checkChequeNumber(Long.valueOf(chequeNumber))>0) ? "Cheque number '" + chequeNumber + "' already exists." : "";
            }
        }
        return errorMsg;
    }
}

