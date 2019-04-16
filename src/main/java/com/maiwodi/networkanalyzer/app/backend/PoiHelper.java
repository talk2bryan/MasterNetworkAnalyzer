package com.maiwodi.networkanalyzer.app.backend;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiHelper {
	public static void main(String[] args) {

		List<String> strs = new ArrayList<String>();

		strs.add("str");
		strs.add("str2");
		strs.add("str3");
		strs.add("str3");
		strs.add("str4");
		strs.add("str5");
		strs.add("str6");
		strs.add("str7");
		strs.add("str8");

		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();

		// create a new row in the current sheet
		// Row row = sheet.createRow(0);
		//
		// Cell cell = row.createCell(0);
		// cell.setCellValue(strs.get(0));

		for (int i = 0; i < strs.size(); i++) {

			Row tempRow = sheet.createRow(i);
			Cell tempCell = tempRow.createCell(0);
			tempCell.setCellValue(strs.get(i));
		}

		try {
			FileOutputStream outputStream = new FileOutputStream("Test.xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeFile(XSSFWorkbook workbook, String fileName) {
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
