package com.baitian.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Read from excel file
 * @author zhuyuanbiao
 *
 */
public class ExcelReader {
	
	/**
	 * Restore all rows of a sheet
	 */
	private List<Map<String, Object>> rows = new ArrayList<>();
	
	/**
	 * First row as field name
	 */
	private Map<Integer, String> headers = new HashMap<>();
	
	/**
	 * Rows of per sheet. The key represents sheet name, the value represents rows in a sheet
	 */
	private Map<String, List<Map<String, Object>>> rowsOfSheet = new HashMap<>();
	
	public Map<String, List<Map<String, Object>>> getRowsOfSheets() {
		return Collections.unmodifiableMap(rowsOfSheet);
	}
	
	/**
	 * Read from excel file
	 * @param path the Excel file path
	 * @return
	 */
	public void readExcel(String path) {
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			readSheet(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Read all sheets from Excel
	 * @param workbook
	 */
	private void readSheet(XSSFWorkbook workbook) {
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			readRow(sheet);
			rowsOfSheet.put(sheet.getSheetName(), new ArrayList<>(rows));
			rows.clear();
		}
	}

	/**
	 * Read all rows from Excel(ignore first row)
	 * @param sheet
	 */
	private void readRow(XSSFSheet sheet) {
		int tmpCellNum = 0;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow xssfRow = sheet.getRow(i);
			if (i == 1) {
				tmpCellNum = xssfRow.getLastCellNum();
			}
			readCell(xssfRow, tmpCellNum);
		}
	}

	/**
	 * Read all cells from a row
	 * @param xssfRow
	 */
	private void readCell(XSSFRow xssfRow, int cellNum) {
		Map<String, Object> cell = new HashMap<>();
		for (int i = 0; i < cellNum; i++) {
			XSSFCell xssfCell = xssfRow.getCell(i);
			if (xssfRow.getRowNum() == 1) {
				String cellValue = xssfCell.getStringCellValue();
				if (!"".equals(cellValue)) {
					headers.put(xssfCell.getColumnIndex(), cellValue);
				}
				continue;
			}
			String fieldName = headers.get(i);
			if (fieldName != null && !"".equals(fieldName)) {
				cell.put(fieldName, xssfCell == null ? "" : getCellValue(xssfCell));
			}
		}
		if (xssfRow.getRowNum() > 1) {
			rows.add(cell);
		}
	}
	
	/**
	 * Receive value of a cell
	 * @param xssfCell
	 * @return
	 */
	private Object getCellValue(XSSFCell xssfCell) {
		int cellType = xssfCell.getCellType();
		if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {
			return xssfCell.getNumericCellValue();
		} else if (cellType == XSSFCell.CELL_TYPE_STRING) {
			return xssfCell.getStringCellValue();
		} else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {
			return xssfCell.getBooleanCellValue();
		}
		return "";
	}

}
