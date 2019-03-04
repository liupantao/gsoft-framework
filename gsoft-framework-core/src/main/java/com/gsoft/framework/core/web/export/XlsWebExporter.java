package com.gsoft.framework.core.web.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gsoft.framework.core.exception.BusException;

/**
 * 导出excel
 * @author liupantao
 *
 */
public class XlsWebExporter implements WebExporter {
	private OutputStream outputStream;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle cellStyle;

	public XlsWebExporter(OutputStream outputStream) {
		this.outputStream = outputStream;

		this.workbook = new HSSFWorkbook();
		this.sheet = this.workbook.createSheet();

		this.cellStyle = this.workbook.createCellStyle();
		this.cellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);

		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));

		this.headerStyle = this.workbook.createCellStyle();
		this.headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		this.headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}

	/* (非 Javadoc)  
	 * <p>Title: writeLine</p>  
	 * <p>Description: </p>  
	 * @param rowIndex
	 * @param rowData  
	 * @see com.gsoft.framework.core.web.export.WebExporter#writeLine(int, java.lang.Object[])  
	 */  
	@Override
	public void writeLine(int rowIndex, Object[] rowData) {
		HSSFRow row = this.sheet.createRow(rowIndex);
		if (rowIndex == 0) {
			for (int i = 0; i < rowData.length; i++) {
				this.sheet.setColumnWidth(i, 10000);
			}
		}
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
			cell.setCellStyle(this.headerStyle);
		else {
			cell.setCellStyle(this.cellStyle);
		}

		this.cellStyle.setBorderLeft(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderTop(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderRight(CellStyle.SOLID_FOREGROUND);
		this.cellStyle.setBorderBottom(CellStyle.SOLID_FOREGROUND);

		return cell;
	}

	@Override
	public void close() {
		try {
			this.workbook.write(this.outputStream);
		} catch (FileNotFoundException e) {
			throw new BusException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new BusException(" 写入Excel文件出错! ", e);
		} finally {
			if (this.outputStream != null) {
				try {
					this.outputStream.flush();
					this.outputStream.close();
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