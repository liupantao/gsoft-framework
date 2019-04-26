package com.gsoft.framework.core.orm;

import org.springframework.data.domain.PageRequest;

/**
 * 分页参数
 * 
 * @author liupantao
 * 
 */
public class Pager extends PageRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8676370649225208176L;

	/**
	 * @Fields DEFALUT_PAGESIZE : 默认页面记录条数
	 */
	public static int DEFALUT_PAGESIZE = 20;

	private static int DEFALUT_PAGEINDEX = 1;
	//
	/**
	 * @Fields EXPORT_TYPE_XLS : 导出excel
	 */
	public final static String EXPORT_TYPE_XLS = "xls";

	/**
	 * @Fields EXPORT_TYPE_PDF : 导出pdf 
	 */
	public final static String EXPORT_TYPE_PDF = "pdf";

	/**
	 * @Fields EXPORT_TYPE_PRINT : 打印
	 */
	public final static String EXPORT_TYPE_PRINT = "print";

	/**
	 * @Fields startIndex : 开始index
	 */
	private int startIndex;

	/**
	 * @Fields pagerProperties : 查询属性
	 */
	private String[] pagerProperties;

	/**
	 * @Fields export : 导出标识
	 */
	private String export;

	/**
	 * @Fields exportHeaders : 导出表头
	 */
	private String[] exportHeaders;

	/**
	 * @Fields exportProperties : 导出属性 
	 */
	private String[] exportProperties;

	private String[] exportConverts;

	public Pager(int pageIndex, int pageSize, Sort sort) {
		super(pageIndex, pageSize, sort);
		this.initPager();
	}

	public Pager(int pageIndex, int pageSize) {
		super(pageIndex, pageSize);
		this.initPager();
	}

	public Pager(String pageIndex,String pageSize) {
		this(parseIndex(pageIndex, DEFALUT_PAGEINDEX) - 1,parseIndex(pageSize, DEFALUT_PAGESIZE));
	}

	private static int parseIndex(String value, int defaultValue) {
		int intValue = defaultValue;
		try {
			intValue = new Integer(value).intValue();
		} catch (Exception e) {
		}
		return intValue;
	}

	/**
	 * 
	 * @param pageSize
	 * @param pageIndex
	 */
	private void initPager() {
		this.startIndex = super.getPageNumber() * super.getPageSize();
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the export
	 */
	public String getExport() {
		return export;
	}

	/**
	 * @param export
	 *            the export to set
	 */
	public void setExport(String export) {
		this.export = export;
	}

	/**
	 * @return the exportHeaders
	 */
	public String[] getExportHeaders() {
		return exportHeaders;
	}

	/**
	 * @param exportHeaders
	 *            the exportHeaders to set
	 */
	public void setExportHeaders(String[] exportHeaders) {
		this.exportHeaders = exportHeaders;
	}

	/**
	 * @return the exportProperties
	 */
	public String[] getExportProperties() {
		return exportProperties;
	}

	/**
	 * @param exportProperties
	 *            the exportProperties to set
	 */
	public void setExportProperties(String[] exportProperties) {
		this.exportProperties = exportProperties;
	}

	/**
	 * @return the pagerProperties
	 */
	public String[] getPagerProperties() {
		return pagerProperties;
	}

	/**
	 * @param pagerProperties
	 *            the pagerProperties to set
	 */
	public void setPagerProperties(String[] pagerProperties) {
		this.pagerProperties = pagerProperties;
	}

	public String[] getExportConverts() {
		return exportConverts;
	}

	public void setExportConverts(String[] exportConverts) {
		this.exportConverts = exportConverts;
	}

}
