${parameters.tagUtil.writeScript("/struts/simple/resource/jquery.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery-ui.min.js")?string}

${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.ui.widget.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/jquery.ui.date.js")?string}
${parameters.tagUtil.writeScript("/struts/simple/resource/plugins/WdatePicker/WdatePicker.js")?string}

  <script>
  $(function() {
    $( "#${parameters.id?html}").date({
	     readOnly : ${parameters.readOnly?html},   <#-- 是否只读  --> 
	     isShowWeek : ${parameters.isShowWeek?html},   <#-- 是否显示周  -->
	     skin : '${parameters.skin?html}'   <#-- 日期组件皮肤  -->
	     <#if parameters.dateFmt?exists> 
	        ,dateFmt : '${parameters.dateFmt?html}'  <#-- 显示日期的格式  -->
	     </#if>
	     <#if parameters.minDate?exists> 
	        ,minDate : '${parameters.minDate?html}'  <#-- 最小日期  -->
	     </#if>
	     <#if parameters.maxDate?exists> 
	        ,maxDate : '${parameters.maxDate?html}'  <#-- 最大日期  -->
	     </#if>
	     <#if parameters.lang?exists> 
	        ,lang : '${parameters.lang?html}'  <#-- 语言  -->
	     </#if>
	     <#if parameters.triggerEvent?exists>
		,triggerEvent:${parameters.triggerEvent?html}
		</#if>
  });
  });
  </script>


<input type="text"
<#if parameters.id?exists>
 id="${parameters.id?html}"   <#-- 日期组件ID  -->
<#elseif parameters.name?exists>
 id="${parameters.name?html}"    <#-- 日期组件ID  -->
</#if>  
<#if parameters.name?exists>
 name="${parameters.name?html}"    <#-- 日期组件名称  -->
</#if>
<#if parameters.disabled?exists && (parameters.disabled?trim?lower_case="true" ||parameters.disabled?trim?lower_case="disabled")> 
 disabled="disabled"  <#-- 禁止更改日期  -->
</#if>     
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.script?exists>
${parameters.script?html}
</#if>
 style = "width : ${parameters.width?html};"   <#-- 日期组件宽度  -->
 class="Wdate"
>
