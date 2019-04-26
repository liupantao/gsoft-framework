package com.gsoft.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

/**
 * xls读取工具类
 * 
 * @author liupantao
 * @date 2017年10月20日
 * 
 */
public class XlsReader {
	
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	private FormulaEvaluator evaluator;
	
	private String dateFormate = "yyyy-MM-dd";

	/**
	 * 创建一个新的实例 XlsReader.
	 * 
	 * @throws IOException
	 * 
	 */
	public XlsReader(InputStream is, int sheetIndex) throws IOException {
		fs = new POIFSFileSystem(is);
		wb = new HSSFWorkbook(fs);
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
		sheet = wb.getSheetAt(sheetIndex);
	}

	/** 
	 * 读取表头
	 * @param titleRowIndex
	 * @return 
	 */
	public String[] readExcelTitle(int titleRowIndex) {
		row = sheet.getRow(titleRowIndex);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getStringCellValue(row.getCell(i));
		}
		return title;
	}

	/** 
	 * 读取Excel数据内容
	 * @param startRowIndex
	 * @return 
	 */
	public Map<Integer, List<String>> readExcelContent(int startRowIndex) {
		Map<Integer, List<String>> content = new HashMap<>();
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = startRowIndex; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			List<String> rowValues = new ArrayList<>();
			while (j < colNum) {
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				rowValues.add(getStringCellValue(row.getCell(j)).trim());
				j++;
			}
			content.put(i, rowValues);
		}
		return content;
	}

	/** 
	 * 获取单元格数据内容为字符串类型的数据
	 * @param cell
	 * @return 
	 */
	private String getStringCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_FORMULA) { // 表达式类型
			cellType = evaluator.evaluate(cell).getCellType();
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) { // 判断日期类型
				strCell = DateUtils.formatDate(cell.getDateCellValue(), dateFormate);
			} else { // 否
				strCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		return strCell;
	}

	/**
	 * @return the dateFormate
	 */
	public String getDateFormate() {
		return dateFormate;
	}

	/**
	 * @param dateFormate the dateFormate to set
	 */
	public void setDateFormate(String dateFormate) {
		this.dateFormate = dateFormate;
	}

	public static void main(String[] args) throws IOException {
		try {
			// 对读取Excel表格标题测试
			InputStream is = new FileInputStream("C:\\Users\\hasee\\Downloads\\报文列表.xls");
			XlsReader excelReader = new XlsReader(is, 0);
			String[] title = excelReader.readExcelTitle(0);
			System.out.println("获得Excel表格的标题:");
			for (String s : title) {
				System.out.print(s + " ");
			}
			System.out.println("");
			// 对读取Excel表格内容测试
			Map<Integer, List<String>> map = excelReader.readExcelContent(1);
			System.out.println("获得Excel表格的内容:");
			for (int i = 1; i <= map.size(); i++) {
				System.out.println(map.get(i));
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}
}