package com.example.store.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.*;

public class ExcelReader {

    public static Object[][] readExcel(InputStream is, Integer sheetIndex) throws Exception {
        List<Object[]> resultList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Iterator<Row> rows = sheet.iterator();

        // Lấy số cột lớn nhất trong sheet để đảm bảo chiều dài của mỗi dòng
        int maxColumnCount = getMaxColumnCount(sheet);

        // Duyệt qua các dòng trong sheet (từ dòng thứ 2 trở đi)
        while (rows.hasNext()) {
            Row row = rows.next();
            Object[] rowData = new Object[maxColumnCount];  // Đảm bảo có đủ chỗ cho tất cả các cột

            // Duyệt qua tất cả các cột trong mỗi dòng
            for (int i = 0; i < maxColumnCount; i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    rowData[i] = getCellValue(cell); // Lấy giá trị của ô nếu có
                } else {
                    rowData[i] = null; // Nếu ô trống, gán null
                }
            }

            resultList.add(rowData);
        }

        workbook.close();

        // Chuyển đổi từ List<Object[]> thành Object[][]
        return resultList.toArray(new Object[0][]);
    }

    // Hàm lấy giá trị của ô
    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }

    // Hàm lấy số cột lớn nhất trong sheet
    private static int getMaxColumnCount(Sheet sheet) {
        int maxColumns = 0;
        for (Row row : sheet) {
            int lastColumn = row.getLastCellNum(); // Lấy số cột trong mỗi dòng
            maxColumns = Math.max(maxColumns, lastColumn);
        }
        return maxColumns;
    }
}