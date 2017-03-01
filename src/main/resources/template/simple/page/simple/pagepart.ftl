${parameters.tagUtil.writeScript("/struts/simple/page/simple/resource/js/script.js")?string}
${parameters.tagUtil.writeStyle("/struts/simple/page/simple/resource/skin/"+parameters.skin+"/css/css.css")?string}
${parameters.tagUtil.writeScript("/struts/simple/page/simple/resource/js/divcreat.js")?string}



<#assign pageFormStr="" />
<#if !parameters.haveForm?default(false)>
<#assign pageFormStr=pageFormStr+"<form id=page_form_"+parameters.id+" name=page_form_"+parameters.id+" method=post >" />
</#if>

<#if parameters.savePara && stack.getContext().get("${parameters.id}")?exists>
<#assign pageFormStr=pageFormStr+stack.getContext().get("${parameters.id}").condition?string />
</#if>



<#if !parameters.haveForm?default(false)>
	<#assign isPringForm = parameters.tagUtil.checkByKey("page_form_end_"+parameters.id)/>
	<#if isPringForm> 
	${pageFormStr?string}
	</#if>
</#if>
<INPUT type="hidden" value="${stack.getContext().get("${parameters.id?html}").currentPage}" id="page_currentPage_${parameters.id}" name="page_currentPage_${parameters.id}">
<INPUT type="hidden" id="page_queryflag_${parameters.id}" name="page_queryflag_${parameters.id}"> 
<INPUT type="hidden" value="${stack.getContext().get("${parameters.id?html}").pageRecord}" id="page_selectPerPage_${parameters.id}" name="page_selectPerPage_${parameters.id}"> 
<input type="hidden" value="" name="title${parameters.id}" id="title${parameters.id}"/>
<input type="hidden" value="" name="excelcolumn${parameters.id}" id="excelcolumn${parameters.id}"/>
<input type="hidden" value="" name="startIndex${parameters.id}" id="startIndex${parameters.id}"/>
<input type="hidden" value="" name="method${parameters.id}" id="method${parameters.id}"/>
<input type="hidden" value="" name="excelTitle${parameters.id}" id="excelTitle${parameters.id}"/>
<input type="hidden" value="" name="exportMode${parameters.id}" id="exportMode${parameters.id}"/>
<input type="hidden" value="<#if parameters.actionurl?exists>${parameters.actionurl}</#if>" name="queryStringInfo${parameters.id}" id="queryStringInfo${parameters.id}"/>


<#if stack.getContext().get("${parameters.id}")?exists>
	<#if (stack.getContext().get("${parameters.id?html}").totalRecord==0)>
			${tag.getSqlDebugIcon()?default('')}<@s.property value=""/>0<@s.property value="%{getText('rainbow.ui.record')}"/>&nbsp;&nbsp;1/0<@s.property value="%{getText('rainbow.ui.page')}"/>
			<label title="<@s.property value="%{getText('rainbow.ui.firstpage')}"/>"><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/first${localeName?default('')}.gif" align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
			<label title="<@s.property value="%{getText('rainbow.ui.previewpage')}"/>"><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/preview${localeName?default('')}.gif" align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>		
			<label title="<@s.property value="%{getText('rainbow.ui.nextpage')}"/>"><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/next${localeName?default('')}.gif" align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
			<label title="<@s.property value="%{getText('rainbow.ui.lastpage')}"/>"><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/last${localeName?default('')}.gif" align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
			<input type="text" value="1" size="3" disabled /><br>
		
		<#else>
			<#if parameters.forGridId?exists>
				<#if parameters.selectCol>
					<div id="${parameters.id?html}_colcon" style="display:none" >
						<div style="width:300px;height:180px;background:#fee;overflow:auto;">
							<div onMousedown="StartDrag(this,event)" onMouseup="StopDrag(this,event)" onMousemove="Drag(this,event)">
								<div style="float:left;width:85%;height:18px;overflow:hidden;"><@s.property value="%{getText('rainbow.ui.selectCol')}"/></div>
								<div style="float:right;width:15%;"><span onclick="closeLayer()" style="cursor:pointer;"><@s.property value="%{getText('rainbow.ui.close')}"/></span></div>
							</div>
							<div style="width:100%;clear:both;" id="${parameters.id?html}_coltab">
							</div>
						</div>
					</div>
				</#if> 
				<#if (stack.getContext().get("${parameters.id?html}").totalRecord<parameters.exportPageRecords)>
					<script type="text/javascript">
						function qdownload${parameters.id?html}(){
							<#if parameters.forGridId?exists>
								<#if parameters.columnName?exists>
									document.getElementById('title${parameters.id}').value="${parameters.columnName?string}";
								<#else>
									document.getElementById('title${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('title');
								</#if>
								<#if parameters.databaseColumnName?exists>
									document.getElementById('excelcolumn${parameters.id}').value="${parameters.databaseColumnName?string}";
								<#else>
									document.getElementById('excelcolumn${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('column');
								</#if>
								<#if parameters.excelMethod?exists>
									document.getElementById('method${parameters.id}').value="${parameters.excelMethod?html}";
								</#if>
								<#if parameters.excelTitle?exists>
									document.getElementById('excelTitle${parameters.id}').value="${parameters.excelTitle?html}";
								</#if>
								document.getElementById('startIndex${parameters.id}').value=0;
								<#if parameters.exportMode?exists>
									document.getElementById('exportMode${parameters.id}').value="${parameters.exportMode?html}";
								<#else>
									document.getElementById('exportMode${parameters.id}').value="simple";
								</#if>
								var ${parameters.id?html}formobj=getMyDefinedFormName(document.getElementById('page_queryflag_${parameters.id}'));
								${parameters.id?html}formobj.action="${base}/comm/servlet.shtml?servlet_name=savepar&pageobj=${parameters.id}";
								${parameters.id?html}formobj.target="_blank"; 
								${parameters.id?html}formobj.submit();
								${parameters.id?html}formobj.action='';
								${parameters.id?html}formobj.target='_self';
							</#if>
						}
					</script>
				<#else>
					<div id="${parameters.id?html}_con" style="display:none" title="<@s.property value="%{getText('rainbow.ui.container')}"/>">
						<div style="width:300px;height:180px;background:#fee;overflow:auto;">
							<div onMousedown="StartDrag(this,event)" onMouseup="StopDrag(this,event)" onMousemove="Drag(this,event)">
								<div style="float:left;width:85%;height:18px;overflow:hidden;"><#if parameters.excelTitle?exists>${parameters.excelTitle?html}</#if><@s.property value="%{getText('rainbow.ui.download')}"/></div>
								<div style="float:right;width:15%;"><span onclick="closeLayer()" style="cursor:pointer;"><@s.property value="%{getText('rainbow.ui.close')}"/></span></div>
							</div>
							<div style="width:100%;clear:both;">
								<table width="100%">
									<tr align="center">
										<td><@s.property value="%{getText('rainbow.ui.selectexport')}"/></td>
									</tr>
									<script>
										function download${parameters.id?html}(obj,current){
											<#if parameters.forGridId?exists>
												<#if parameters.columnName?exists>
													document.getElementById('title${parameters.id}').value="${parameters.columnName?string}";
												<#else>
													document.getElementById('title${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('title');
												</#if>
												<#if parameters.databaseColumnName?exists>
													document.getElementById('excelcolumn${parameters.id}').value="${parameters.databaseColumnName?string}";
												<#else>
													document.getElementById('excelcolumn${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('column');
												</#if>
												<#if parameters.excelMethod?exists>
													document.getElementById('method${parameters.id}').value="${parameters.excelMethod?html}";
												</#if>
												<#if parameters.excelTitle?exists>
													document.getElementById('excelTitle${parameters.id}').value="${parameters.excelTitle?html}";
												</#if>
												document.getElementById('startIndex${parameters.id}').value=obj;
												<#if parameters.exportMode?exists>
												document.getElementById('exportMode${parameters.id}').value="${parameters.exportMode?html}";
												<#else>
												document.getElementById('exportMode${parameters.id}').value="simple";
												</#if>
												var ${parameters.id?html}formobj=getMyDefinedFormName(document.getElementById('page_queryflag_${parameters.id}'));
												${parameters.id?html}formobj.action="${base}/comm/servlet.shtml?servlet_name=savepar&pageobj=${parameters.id}&current${parameters.id}="+current+"&pageRecord${parameters.id}=${parameters.exportPageRecords}";
												${parameters.id?html}formobj.target="_blank"; 
												${parameters.id?html}formobj.submit();
												${parameters.id?html}formobj.action='';
												${parameters.id?html}formobj.target='_self';
											</#if>
										}
									</script>
									<#assign totalCord=stack.getContext().get("${parameters.id?html}").totalRecord>
									<#assign pageCord=parameters.exportPageRecords>
									<#assign forTime=(((totalCord + pageCord) - 1) / pageCord)-1>
									<#macro repeat count>   
									  <#list 0..count as x>   
									    <#nested x, x/2, x<count>   
									  </#list>   
									</#macro>   
									<@repeat count=forTime ; c, halfc, last>   
									  <#if ((c+1)*pageCord<totalCord)>
									  	<tr align="center">
											<td><a href="javascript:;" onclick="download${parameters.id?html}(${c*pageCord},${c+1})" ><#if parameters.excelTitle?exists>${parameters.excelTitle?html}</#if>(<@s.property value="%{getText('rainbow.ui.no')}"/>${c*pageCord+1}<@s.property value="%{getText('rainbow.ui.to')}"/>${(c+1)*pageCord})</a></td>
										</tr>
									  <#else>
									  	<tr align="center">
											<td><a href="javascript:;" onclick="download${parameters.id?html}(${c*pageCord},${c+1})" ><#if parameters.excelTitle?exists>${parameters.excelTitle?html}</#if>(<@s.property value="%{getText('rainbow.ui.no')}"/>${c*pageCord+1}<@s.property value="%{getText('rainbow.ui.to')}"/>${totalCord})</a></td>
										</tr>
									  </#if>
									</@repeat> 
								</table>
							</div>
						</div>
					</div>
					
				</#if>
			</#if>
			<#if parameters.exportCurrentPage>
				<script>
					function downCurrentPage${parameters.id?html}(){
						<#if parameters.forGridId?exists>
							<#if parameters.columnName?exists>
								document.getElementById('title${parameters.id}').value="${parameters.columnName?string}";
							<#else>
								document.getElementById('title${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('title');
							</#if>
							<#if parameters.databaseColumnName?exists>
								document.getElementById('excelcolumn${parameters.id}').value="${parameters.databaseColumnName?string}";
							<#else>
								document.getElementById('excelcolumn${parameters.id}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('column');
							</#if>
							<#if parameters.excelMethod?exists>
								document.getElementById('method${parameters.id}').value="${parameters.excelMethod?html}";
							</#if>
							<#if parameters.excelTitle?exists>
								document.getElementById('excelTitle${parameters.id}').value="${parameters.excelTitle?html}";
							</#if>
							document.getElementById('startIndex${parameters.id}').value=${stack.getContext().get("${parameters.id?html}").pageStart};
							<#if parameters.exportMode?exists>
							document.getElementById('exportMode${parameters.id}').value="${parameters.exportMode?html}";
							<#else>
							document.getElementById('exportMode${parameters.id}').value="simple";
							</#if>
							var ${parameters.id?html}formobj=getMyDefinedFormName(document.getElementById('page_queryflag_${parameters.id}'));
							${parameters.id?html}formobj.action="${base}/comm/servlet.shtml?servlet_name=savepar&pageobj=${parameters.id}<#if parameters.exportCurrentPage>&pageRecord${parameters.id}=${stack.getContext().get("${parameters.id?html}").pageRecord}</#if>";
							${parameters.id?html}formobj.target="_blank"; 
							${parameters.id?html}formobj.submit();
							${parameters.id?html}formobj.action='';
							${parameters.id?html}formobj.target='_self';
						</#if>
					}
				</script>
			</#if>
			<div class="${parameters.skin}">
			${tag.getSqlDebugIcon()?default('')}
			<#if parameters.exportCurrentPage>
				<a href="javascript:;" onclick="downCurrentPage${parameters.id?html}();"><img border=0 src="${base}/struts/simple/page/simple/resource/skin/blue/images/ico_file_excel.png" alt="<#if parameters.inWorkingHoursMsg?exists>${parameters.inWorkingHoursMsg}<#else><@s.property value="%{getText('rainbow.ui.exportpage')}"/></#if>"/></a>
			</#if>
			<#if !parameters.isInWorkingHours?exists || !parameters.isInWorkingHours> 
				<#if parameters.forGridId?exists>
					<a id="${parameters.id?html}myhref" href="javascript:;" 
					<#if parameters.selectCol>
						<#if (stack.getContext().get("${parameters.id?html}").totalRecord<parameters.exportPageRecords)>
							onclick="setTableCheckbox('${parameters.forGridId?html}_forpage','${parameters.id?html}_coltab','${parameters.id?html}',0);openLayer('${parameters.id?html}myhref','${parameters.id?html}_colcon');"
						<#else>
							onclick="setTableCheckbox('${parameters.forGridId?html}_forpage','${parameters.id?html}_coltab','${parameters.id?html}',1);openLayer('${parameters.id?html}myhref','${parameters.id?html}_colcon');"
						</#if>
					<#else>
						<#if (stack.getContext().get("${parameters.id?html}").totalRecord<parameters.exportPageRecords)>
							onclick="qdownload${parameters.id?html}()"
						<#else>
							onclick="openLayer('${parameters.id?html}myhref','${parameters.id?html}_con');"
						</#if>
					</#if>
					>
					
						<img border=0 src="${base}/struts/simple/page/simple/resource/skin/blue/images/ico_file_excel.png" alt="<@s.property value="%{getText('rainbow.ui.exportexcel')}"/>"/>
					
					</a>
				</#if>
			</#if>
			 <@s.property value=""/>${stack.getContext().get("${parameters.id?html}").totalRecord}<@s.property value="%{getText('rainbow.ui.record')}"/>&nbsp;&nbsp;${stack.getContext().get("${parameters.id?html}").currentPage}/${stack.getContext().get("${parameters.id?html}").totalPage}<@s.property value="%{getText('rainbow.ui.page')}"/>
${(stack.getContext().get("${parameters.id?html}").currentPage-1)*stack.getContext().get("${parameters.id?
html}").pageRecord+1}-<#if (stack.getContext().get("${parameters.id?html}").currentPage==stack.getContext().get
("${parameters.id?html}").totalPage)>${(stack.getContext().get("${parameters.id?html}").currentPage-1)*stack.getContext
().get("${parameters.id?html}").pageRecord+stack.getContext().get("${parameters.id?html}").totalRecord-stack.getContext
().get("${parameters.id?html}").pageRecord*(stack.getContext().get("${parameters.id?html}").currentPage-1)}
<#else>${(stack.getContext().get("${parameters.id?html}").currentPage-1)*stack.getContext().get("${parameters.id?
html}").pageRecord+stack.getContext().get("${parameters.id?html}").pageRecord}</#if>&nbsp;<@s.property value="%{getText('rainbow.ui.perpage')}"/>
		<#if parameters.showSelect?exists && parameters.showSelect && (parameters.selectPerPage?exists==false||(parameters.selectPerPage?exists && parameters.selectPerPage==""))>
			<script>
				function setexcel${parameters.id?html}(){
					<#if parameters.forGridId?exists>
						<#if parameters.columnName?exists>
							document.getElementById('title${parameters.id?html}').value="${parameters.columnName?string}";
						<#else>
							document.getElementById('title${parameters.id?html}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('title');
						</#if>
						<#if parameters.databaseColumnName?exists>
							document.getElementById('excelcolumn${parameters.id?html}').value="${parameters.databaseColumnName?string}";
						<#else>
							document.getElementById('excelcolumn${parameters.id?html}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('column');
						</#if>
						<#if parameters.excelMethod?exists>
							document.getElementById('method${parameters.id?html}').value="${parameters.excelMethod?html}";
						</#if>
						<#if parameters.excelTitle?exists>
							document.getElementById('excelTitle${parameters.id?html}').value="${parameters.excelTitle?html}";
						</#if>
						<#if parameters.exportMode?exists>
						document.getElementById('exportMode${parameters.id?html}').value="${parameters.exportMode?html}";
						<#else>
						document.getElementById('exportMode${parameters.id?html}').value="simple";
						</#if>
					</#if>
				}
			</script>
			<select onchange="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_selectRecord(this.value,'1','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')">
				<option value="10" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==10)>selected</#if>>10</option>
				<option value="20" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==20)>selected</#if>>20</option>
				<option value="30" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==30)>selected</#if>>30</option>
				<option value="40" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==40)>selected</#if>>40</option>
				<option value="50" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==50)>selected</#if>>50</option>
			</select>
			<@s.property value="%{getText('rainbow.ui.record')}"/>
		</#if>
		<#if parameters.showSelect?exists && parameters.showSelect && parameters.selectPerPage?exists && parameters.selectlist?exists >
			<script>
				function setexcel${parameters.id?html}(){
					<#if parameters.forGridId?exists>
						<#if parameters.columnName?exists>
							document.getElementById('title${parameters.id?html}').value="${parameters.columnName?string}";
						<#else>
							document.getElementById('title${parameters.id?html}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('title');
						</#if>
						<#if parameters.databaseColumnName?exists>
							document.getElementById('excelcolumn${parameters.id?html}').value="${parameters.databaseColumnName?string}";
						<#else>
							document.getElementById('excelcolumn${parameters.id?html}').value=document.getElementById('${parameters.forGridId?html}_forpage').getAttribute('column');
						</#if>
						<#if parameters.excelMethod?exists>
							document.getElementById('method${parameters.id?html}').value="${parameters.excelMethod?html}";
						</#if>
						<#if parameters.excelTitle?exists>
							document.getElementById('excelTitle${parameters.id?html}').value="${parameters.excelTitle?html}";
						</#if>
						<#if parameters.exportMode?exists>
						document.getElementById('exportMode${parameters.id?html}').value="${parameters.exportMode?html}";
						<#else>
						document.getElementById('exportMode${parameters.id?html}').value="simple";
						</#if>
					</#if>
				}
			</script>
			<select onchange="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_selectRecord(this.value,'1','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')">
				<#list parameters.selectlist as e>
					<option value="${e}" <#if (stack.getContext().get("${parameters.id?html}").pageRecord==e?number)>selected</#if>>${e}</option>
				</#list>
			</select>
			<@s.property value="%{getText('rainbow.ui.record')}"/>
		</#if>
		<#if parameters.showSelect?exists==false>
			${stack.getContext().get("${parameters.id?html}").pageRecord}<@s.property value="%{getText('rainbow.ui.record')}"/>
		</#if>
			<#if (stack.getContext().get("${parameters.id?html}").currentPage==1)>
				<label title="<@s.property value="%{getText('rainbow.ui.firstpage')}"/>" ><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/first${localeName?default('')}.gif" 
align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
				<label title="<@s.property value="%{getText('rainbow.ui.previewpage')}"/>" ><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/preview${localeName?default('')}.gif" 
align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
			<#else>
				<label title="<@s.property value="%{getText('rainbow.ui.firstpage')}"/>" ><img onclick="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_turnPage('1','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')" 
src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/tofirst${localeName?default('')}.gif" onmouseover="this.src='${base}/	struts/simple/page/simple/resource/skin/${parameters.skin}/images/tofirst_over${localeName?default('')}.gif'" onmouseout="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/tofirst${localeName?default('')}.gif'" align="absmiddle"></label>
				<label title="<@s.property value="%{getText('rainbow.ui.previewpage')}"/>" ><img onclick="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_turnPage('${stack.getContext().get("${parameters.id?
html}").currentPage-1}','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')" src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/previewto${localeName?default('')}.gif"  onmouseover="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/previewto_over${localeName?default('')}.gif'" onmouseout="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/previewto${localeName?default('')}.gif'" align="absmiddle"></label>
			</#if>
			<#if (stack.getContext().get("${parameters.id?html}").currentPage==stack.getContext().get
("${parameters.id?html}").totalPage)>
				<label title="<@s.property value="%{getText('rainbow.ui.nextpage')}"/>" ><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/next${localeName?default('')}.gif" 
align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
				<label title="<@s.property value="%{getText('rainbow.ui.lastpage')}"/>" ><img src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/last${localeName?default('')}.gif" 
align="absmiddle" style="color:gray;cursor:default;opacity:.1;-moz-opacity:.1;filter:alpha(opacity=10);"></label>
			<#else>
				<label title="<@s.property value="%{getText('rainbow.ui.nextpage')}"/>" ><img onclick="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_turnPage('${stack.getContext().get("${parameters.id?
html}").currentPage+1}','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')" src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/nextto${localeName?default('')}.gif" onmouseover="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/nextto_over${localeName?default('')}.gif'" onmouseout="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/nextto${localeName?default('')}.gif'" align="absmiddle"></label>
				<label title="<@s.property value="%{getText('rainbow.ui.lastpage')}"/>" ><img onclick="<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>fn_turnPage('${stack.getContext().get("${parameters.id?
html}").totalPage}','${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord}')" src="${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/tolast${localeName?default('')}.gif" onmouseover="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/tolast_over${localeName?default('')}.gif'" onmouseout="this.src='${base}/struts/simple/page/simple/resource/skin/${parameters.skin}/images/tolast${localeName?default('')}.gif'" align="absmiddle"></label>
			</#if>
			<input id="goto_${parameters.RamCodeId?html}" type="text" value="${stack.getContext().get("${parameters.id?html}").currentPage.toString()}" size="3" 
onKeyPress="fn_goPage(this.value,'${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord.toString()}','<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>')"/>
			<#if parameters.showGotoButton?default(false)>
			<input type="button" value="<@s.property value="%{getText('rainbow.ui.goto')}"/>" onclick="fn_goPage_submit(document.getElementById('goto_${parameters.RamCodeId?html}').value,'${parameters.id?html}','${stack.getContext().get("${parameters.id?html}").totalRecord.toString()}','<#if parameters.showSelect?exists && parameters.showSelect>setexcel${parameters.id?html}();</#if>')">
			</#if>
		</div>
		</#if>
</#if>
<#if !parameters.haveForm?default(false)>
	<#if isPringForm> 
	</form>
	</#if>
</#if>