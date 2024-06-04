package cheque.handover.services.Utility;

import cheque.handover.services.Controller.Admin;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExcelUtilityValidation {

    private final Logger logger = LoggerFactory.getLogger(Admin.class);

    public boolean ExcelFileFormat(Row headerRow) {
        String[] expectedHeaders = {"Applicant Name", "Branch Name", "Region", "Hub Name", "Application Number", "Product Name", "Loan Amount", "Sanction Date", "Disbursal date", "Cheque Amount"};

        for (int i = 0; i < 10; i++) {
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
        boolean match = true;
        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || cell.getCellType() == CellType.BLANK)
                return match = false;

            String cellName = cell.toString();
            if (!cellName.equals(expectedHeaders[i]))
                return match = false;
        }
        return match;
    }

    public String chequeAmount(String amount, int rowNum, String loan) {
        String errorMsg="";
        try {
            double chequeAmount= Double.parseDouble(amount);
            if(chequeAmount<=0){
                errorMsg=loan+" Amount is not in the correct format in the file at row no "+(rowNum+1);
            }
        }
        catch (Exception e){
            System.out.println(e);
            logger.info(loan+" Amount is not in the correct format in the file at row no "+(rowNum+1));
            errorMsg=loan+" Amount is not in the correct format in the file at row no "+(rowNum+1);
        }
        return errorMsg;
    }
}

