package task.utility;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.FileInputStream;
import java.util.Iterator;


public class CurrencyCodesExcelReader {


    private static XSSFSheet countriesSheet;

    public static String getCurrencyCode(String countryName) {
        String currencyCode = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("currency_codes.xlsx");
            XSSFWorkbook currencyCodeExcelFile = new XSSFWorkbook(fileInputStream);
            countriesSheet = currencyCodeExcelFile.getSheet("countries");
            Iterator<Row> rowIterator = countriesSheet.rowIterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getCell(0).getStringCellValue().equalsIgnoreCase(countryName)){
                    currencyCode = row.getCell(2).getStringCellValue();
                    break;
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            try {
                fileInputStream.close();
            }catch (Exception exception){
                //
            }
            e.printStackTrace();
            Assert.fail("Couldn't read the file");
        }
        return currencyCode;
    }
}
