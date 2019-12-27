<%@ include file="/WEB-INF/pages/include.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<youi:page i18n="i18n.gsoft.codemap">
	<!-- 页面描述： -->
	<!--**********************************子页面**********************************-->

	<!--**********************************子页面**********************************-->

	<!--**********************************页面内容********************************-->
	<!-- grid-代码集列表-->
	<youi:grid id="grid_codemap" idKeys="codemapId" styleClass="page-height col-sm-7" dataFormId="form_codemap" caption="i18n.gridCaption"
		src="/codemapManager/getPagerCodemaps.json" editSrc="/codemapManager/getCodemap.json" showCheckbox="true"
		panel="false" removeSrc="/codemapManager/removeCodemap.json">
		<youi:fieldLayout>
			<youi:fieldText property="code" operator="LIKE" caption="i18n.code" />
			<youi:fieldText property="caption" operator="LIKE" caption="i18n.caption" />
		</youi:fieldLayout>

		<youi:gridCol property="code" width="20%" caption="i18n.code" />
		<youi:gridCol property="caption" width="40%" caption="i18n.caption" />
		<youi:gridCol property="type" width="20%" convert="codemapType" caption="i18n.type" />
		<youi:gridCol property="expression" width="20%" caption="i18n.expression" />

		<youi:button name="refreshCached" caption="i18n.refreshCache" active="1"  />
		<youi:button name="exportSql" caption="i18n.viewSql" active="2" submitAction="/codemapManager/exportCodemapToSql.json" submitType="2" />
	</youi:grid>

	<!-- grid-代码项列表-->
	<youi:grid id="grid_codeitem" idKeys="itemId" styleClass="page-height col-sm-5" dataFormId="form_codeitem" caption="i18n.codeItem.gridCapion"
		src="/codeitemManager/getPagerCodeitems.json" panel="false" editSrc="/codeitemManager/getCodeitem.json" submit="NOT"
		reset="NOT" removeSrc="/codeitemManager/removeCodeitem.json" showNum="true" parentId="grid_codemap" parentAttr="codemap">
		<youi:fieldLayout>
			<youi:fieldText property="itemValue" operator="LIKE" caption="i18n.codeItem.value" />
			<youi:fieldText property="itemCaption" operator="LIKE" caption="i18n.codeItem.caption" />
		</youi:fieldLayout>
		<youi:gridCol width="40%" property="itemValue" caption="i18n.codeItem.value" />
		<youi:gridCol width="40%" property="itemCaption" caption="i18n.codeItem.caption" />
		<youi:gridCol width="20%" property="itemGroup" caption="i18n.codeItem.group" />
	</youi:grid>

	<!-- form-代码集编辑 -->
	<youi:form dialog="true" caption="i18n.caption" id="form_codemap" action="/codemapManager/saveCodemap.json">
		<youi:fieldLayout prefix="record">
			<youi:fieldHidden property="codemapId" />
			<youi:fieldSelect convert="codemapType" property="type" caption="i18n.type" />
			<youi:fieldText property="code" caption="i18n.code" />
			<youi:fieldText column="2" width="602" property="caption" caption="i18n.caption" />
			<youi:fieldText column="2" width="602" property="expression" caption="i18n.expression" />
		</youi:fieldLayout>
	</youi:form>

	<!-- form-代码项编辑 -->
	<youi:form dialog="true" caption="i18n.codeItem.caption" id="form_codeitem" action="/codeitemManager/saveCodeitem.json">
		<youi:fieldLayout prefix="record" columns="1">
			<youi:fieldHidden property="itemId" />
			<youi:fieldHidden property="codemap.codemapId" caption="代码集ID" />
			<youi:fieldLabel column="2" width="602" property="codemap.caption" caption="i18n.caption" />
			<youi:fieldText column="2" width="602" property="itemValue" caption="i18n.codeItem.value" />
			<youi:fieldText column="2" width="602" property="itemCaption" caption="i18n.codeItem.caption" />
			<youi:fieldText column="2" width="602" property="itemGroup" caption="i18n.codeItem.group" />
		</youi:fieldLayout>
	</youi:form>


	<youi:form dialog="true" id="form_view_exportSql" caption="i18n.viewSql" submit="NOT" reset="NOT">
		<youi:fieldLayout columns="1" labelWidths="1">
			<youi:fieldArea property="html" caption="" rows="20" width="800" />
		</youi:fieldLayout>
	</youi:form>
	<!--**********************************页面内容********************************-->

	<!--**********************************页面函数********************************-->

	<youi:func name="func_grid_exportSql" params="result">
		var sqlFormElem = $elem('form_view_exportSql',pageId);
		sqlFormElem.form('fieldReset').form('fillRecord',result.record).form('open');
	</youi:func>
	
	<youi:ajaxUrl name="refreshCached" src="/convertManager/refreshCached.json"></youi:ajaxUrl>
	<youi:func name="func_grid_refreshCached" urls="refreshCached">
		var record = $elem('grid_codemap',pageId).grid('getSelectedRecord');
		if(record){
			$.youi.ajaxUtil.ajax({
				url:funcUrls.refreshCached,
				data:{name:record.code},
				success:function(result){
					$.youi.messageUtils.showMessage('缓存同步完成!');
				}
			});
		}
	</youi:func>
	<!--**********************************页面函数********************************-->
</youi:page>