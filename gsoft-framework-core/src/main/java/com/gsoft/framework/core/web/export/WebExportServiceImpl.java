package com.gsoft.framework.core.web.export;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.core.convert.IConvertProviderFactory;
import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.util.PropertyUtils;
import com.gsoft.framework.util.StringUtils;

/**
 * 通用导出
 * 
 * @author liupantao
 * 
 */
@Component
public class WebExportServiceImpl implements WebExportService,
		ApplicationContextAware {

	@Autowired(required = false)
	private IConvertProviderFactory convertProviderFactory;
	private List<WebExportAdapter<WebExporter>> webExportAdapters;

	@SuppressWarnings("rawtypes")
	@Override
	public HttpHeaders writePagerRecords(OutputStream outputStream,
			String type, PagerRecords pagerRecords) {
		WebExportAdapter<WebExporter> webExportAdapter = null;
		if (this.webExportAdapters != null) {
			for (WebExportAdapter<WebExporter> adapter : this.webExportAdapters) {
				if (adapter.supports(type)) {
					webExportAdapter = adapter;
					break;
				}
			}
		}

		if (webExportAdapter != null) {
			List<ExportCol> cols = parseColModels(pagerRecords.getPager());

			WebExporter webExport = null;
			try {
				webExport = webExportAdapter.openExporter(outputStream, type);

				webExport.writeLine(0, pagerRecords.getPager()
						.getExportHeaders());

				List records = pagerRecords.getRecords();
				int index = 1;
				Iterator i = records.iterator();
				while (i.hasNext()) {
					Object record = i.next();
					webExport.writeLine(index, parseRowData(cols, record));
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (webExport != null) {
					webExport.close();
				}
			}

			return webExport.getHttpHeaders();
		}

		return null;
	}

	private Object[] parseRowData(List<ExportCol> cols, Object record) {
		List<String> values = new ArrayList<String>();
		for (ExportCol col : cols) {
			Object value = PropertyUtils
					.getPropertyValue(record, col.getName());
			values.add(value == null ? "" : convertValue(col, value));
		}

		return values.toArray(new Object[values.size()]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String convertValue(ExportCol col, Object value) {
		if ((this.convertProviderFactory != null)
				&& (StringUtils.isNotEmpty(col.getConvert()))) {
			IConvert convert = this.convertProviderFactory.getConvert(
					col.getConvert(), null);
			if (convert != null) {
				Object convertValue = convert.get(value.toString());
				if (convertValue != null) {
					return convertValue.toString();
				}
			}
		}
		return value.toString();
	}

	private List<ExportCol> parseColModels(Pager pager) {
		List<ExportCol> exportCols = new ArrayList<ExportCol>();
		int i = 0;
		for (String property : pager.getExportProperties()) {
			exportCols.add(createExportCol(
					property,
					pager.getExportHeaders()[i],
					pager.getExportConverts() != null ? pager
							.getExportConverts()[i] : null));

			i++;
		}

		return exportCols;
	}

	private ExportCol createExportCol(String property, String caption,
			String convert) {
		ExportCol exportCol = new ExportCol();
		exportCol.setName(property);
		exportCol.setCaption(caption);
		String noConvert = "noConvert";
		if (!noConvert.equals(convert)) {
			exportCol.setConvert(convert);
		}
		return exportCol;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if (this.webExportAdapters == null) {
			initWebExportAdapters(applicationContext);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initWebExportAdapters(ApplicationContext applicationContext) {
		Map<String, WebExportAdapter> matchingBeans = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext,
						WebExportAdapter.class, true, false);

		if (!matchingBeans.isEmpty()) {
			this.webExportAdapters = new ArrayList(matchingBeans.values());
			OrderComparator.sort(this.webExportAdapters);
			ConfigRegisterLog.registeAdapters("导出适配器", webExportAdapters.toString(), this);
		}
	}
}