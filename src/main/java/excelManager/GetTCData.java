package excelManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import readObject.ReadObject;
import testCase.StepAPI;

public class GetTCData {
	
	String ruta;
		
	public static List<StepAPI> getStepAPI(String path, String fileName, String sheetName) throws IOException {
		String description = null;
		String step;
		String keyword;
		String url;
		String uri;;
		String parameters;
		JSONObject valueAPI;
		String statusCode;
		String TestCaseName = null;
		
		List<StepAPI> steps = new ArrayList<>();
		ReadExcelFile excel = new ReadExcelFile();
		Sheet sheet = excel.readExcel(path, fileName, sheetName);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = rowIterator.next();
		while(rowIterator.hasNext()) {
			row = rowIterator.next();
			int rowsize = row.getLastCellNum();
			String contenido;
			String contenidoStep;
			Cell cel;
			try {
				cel = row.getCell(0);
				contenido = row.getCell(0).toString();
				}
			catch(NullPointerException e) {
				contenido = "";
			}
			try {
				cel = row.getCell(1);
				contenidoStep = row.getCell(1).toString();
			}
			catch(NullPointerException e) {
				contenidoStep = "";
			}
			if(contenido.length() != 0) {
				TestCaseName = stripString(row.getCell(0).toString());
				if (TestCaseName.equals("ENDCASE"))
					break;
				description = row.getCell(0).toString();
			}
			else if (contenido.length() == 0 && contenidoStep.length()>0){
				step = row.getCell(1).toString();
				keyword = row.getCell(2).toString();
				url = row.getCell(3).toString();
				uri = row.getCell(4).toString();
				parameters = row.getCell(5).toString();
				valueAPI = new JSONObject(row.getCell(6).toString());
				statusCode = row.getCell(7).toString();
				steps.add(new StepAPI(description, step, keyword, url, uri, parameters,valueAPI, statusCode));
			}
		}
		return steps;
	}
	public static String stripString(String value) {
		return value.replaceFirst("^\\s++", "").replaceFirst("\\s++$", "");
	}
	
	/** This method return the Sheet names*/
	public String[] getSheetsNames(String URL) throws IOException {
		 
		 FileInputStream fis = new FileInputStream(new File(URL));

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			
			 int Hojas = workbook.getNumberOfSheets(); 
			 String[] Sheetname = new String[Hojas];	
				
			 for(int i = 0;i<Hojas;i++) {
				 Sheetname[i]= workbook.getSheetName(i);
			 }
		 return Sheetname;
	 }
}
