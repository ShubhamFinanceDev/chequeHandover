package cheque.handover.services.Utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class ExcelUtilityValidation {
    public boolean ExcelFileFormat(Row headerRow) {
            String[] expectedHeaders = {"Applicant Name", "Branch Name", "Region", "Hub Name", "Application Number", "Product Name", "Loan Amount","Sanction Date","Disbursal date","Cheque Amount"};

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

    }

