package com.gsoft.framework.core.web.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gsoft.framework.core.exception.BusException;

/**
 * 根据模板导出excel
 * 
 * @author liupantao
 * 
 */
public class TemplateXlsWebExporter implements WebExporter {

	private OutputStream outputStream;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle cellStyle;
	private int rowNum;

	public TemplateXlsWebExporter(OutputStream outputStream, InputStream loadExportTemplate) {
		try {
			this.outputStream = outputStream;
			POIFSFileSystem fs = new POIFSFileSystem(loadExportTemplate);
			workbook = new HSSFWorkbook(fs);
			sheet = workbook.getSheetAt(0);
			rowNum = sheet.getLastRowNum();

			cellStyle = workbook.createCellStyle();
			cellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);

			HSSFDataFormat format = workbook.createDataFormat();
			cellStyle.setDataFormat(format.getFormat("@"));

			headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeLine(int rowIndex, Object[] rowData) {
		if (rowIndex == 0) {
			return;
		}
		HSSFRow row = sheet.createRow(rowNum + rowIndex);
		int i = 0;
		for (Object value : rowData) {
			addCell(row, i++, value);
		}
	}

	private HSSFCell addCell(HSSFRow row, int index, Object value) {
		HSSFCell cell = row.createCell(index);
		cell.setCellType(1);
		cell.setCellValue(value != null ? value.toString() : "");

		if (row.getRowNum() == 0)
			cell.setCellStyle(headerStyle);
		else {
			cell.setCellStyle(cellStyle);
		}

		cellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);

		return cell;
	}

	@Override
	public void close() {
		try {
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			throw new BusException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new BusException(" 写入Excel文件出错! ", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					throw new BusException("excel导出异常", e);
				}
			}
		}
	}

	@Override
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-disposition", "attachment; filename=\"grid.xls\"");
		return headers;
	}
}