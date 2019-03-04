package com.gsoft.framework.core.web.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * pdf导出
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class PdfWebExporter implements WebExporter {
	
	private final static int PAGE_SIZE_A4 = 20;
	private final static int PAGE_SIZE_A3 = 20*2;
	private final static int PAGE_SIZE_A2 = 20*3;
	private final static int PAGE_SIZE_A1 = 20*4;
	
	private PdfPTable pdfPTable;
	private OutputStream outputStream;
	private static Font fontChinese;
	private static Font bold_fontChinese;
	private Object[] headers = null;

	public PdfWebExporter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void writeLine(int rowIndex, Object[] rowData) {
		if (rowIndex == 0) {
			this.pdfPTable = new PdfPTable(rowData.length);
			this.headers = rowData;
		}

		for (Object data : rowData) {
			Phrase phrase = new Phrase(data.toString(), (rowIndex == 0) ? bold_fontChinese : fontChinese);
			PdfPCell pdfPCell = new PdfPCell(phrase);
			pdfPCell.setHorizontalAlignment(1);
			this.pdfPTable.addCell(pdfPCell);
		}
	}

	@Override
	public void close() {
		Rectangle pageSize = PageSize.A4;
		if (this.headers != null) {
			int minColumnWidth = 4;
			int totalWidths = 0;
			int[] headerWidths = new int[this.headers.length];

			int i = 0;
			for (Object header : this.headers) {
				int width = Math.max(minColumnWidth, header.toString().length());
				headerWidths[(i++)] = width;
				totalWidths += width;
			}

			if (totalWidths <= PAGE_SIZE_A4) {
				pageSize = PageSize.A4;
			} else if (totalWidths <= PAGE_SIZE_A3) {
				pageSize = PageSize.A3;
			} else if (totalWidths <= PAGE_SIZE_A2) {
				pageSize = PageSize.A2;
			} else if (totalWidths <= PAGE_SIZE_A1) {
				pageSize = PageSize.A1;
			} else {
				pageSize = PageSize.A0;
			}
		}

		Document document = new Document(pageSize, 10.0F, 10.0F, 20.0F, 20.0F);
		try {
			PdfWriter.getInstance(document, this.outputStream);
			document.open();
			try {
				document.add(this.pdfPTable);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if (this.outputStream != null) {
				try {
					this.outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (document.isOpen()) {
				document.close();
			}
		}
	}

	@Override
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-disposition", "attachment; filename=\"grid.pdf\"");
		return headers;
	}

	static {
		try {
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

			fontChinese = new Font(bfChinese, 10.0F);
			bold_fontChinese = new Font(bfChinese, 10.0F, 1);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}